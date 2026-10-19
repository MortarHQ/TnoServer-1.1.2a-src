package com.tensuraskilldisabled.handler;

import com.tensuraskilldisabled.TensuraSkillDisabled;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

final class SkillRemovalHelper {
   private static final Field SKILL_INSTANCES_FIELD = findField("skillInstances");
   private static final Field HAS_REMOVED_SKILLS_FIELD = findField("hasRemovedSkills");

   private SkillRemovalHelper() {
   }

   static SkillRemovalHelper.RemovalResult removeBlacklistedSkill(ServerPlayer player, ResourceLocation skillId) {
      clearExternalState(player, skillId);
      SkillStorage storage = SkillAPI.getSkillsFrom(player);
      Optional<ManasSkillInstance> before = storage.getSkill(skillId);
      if (before.isEmpty()) {
         removeFromPresets(player, null, skillId);
         return SkillRemovalHelper.RemovalResult.notPresent();
      } else {
         storage.forgetSkill(skillId);
         if (storage.getSkill(skillId).isEmpty()) {
            removeFromPresets(player, before.get().getSkill(), skillId);
            sync(player);
            return SkillRemovalHelper.RemovalResult.removed(false);
         } else if (forceRemove(storage, player, skillId)) {
            sync(player);
            return SkillRemovalHelper.RemovalResult.removed(true);
         } else {
            return SkillRemovalHelper.RemovalResult.failure();
         }
      }
   }

   static void clearExternalState(ServerPlayer player, ResourceLocation skillId) {
      clearSTExtrasLocks(player, skillId);
      removeFromPresets(player, null, skillId);
   }

   private static boolean forceRemove(SkillStorage storage, ServerPlayer player, ResourceLocation skillId) {
      try {
         Map<ResourceLocation, ManasSkillInstance> skillInstances = (Map<ResourceLocation, ManasSkillInstance>)SKILL_INSTANCES_FIELD.get(storage);
         ManasSkillInstance instance = skillInstances.remove(skillId);
         if (instance == null) {
            return false;
         }

         instance.onForgetSkill(player);
         instance.markDirty();
         HAS_REMOVED_SKILLS_FIELD.setBoolean(storage, true);
         storage.markDirty();
         removeFromPresets(player, instance.getSkill(), skillId);
         TensuraSkillDisabled.LOGGER.debug("Force removed blacklisted skill {}", skillId);
         return true;
      } catch (ReflectiveOperationException | RuntimeException e) {
         TensuraSkillDisabled.LOGGER.error("Failed to force remove blacklisted skill {}", skillId, e);
         return false;
      }
   }

   private static void removeFromPresets(ServerPlayer player, ManasSkill skill, ResourceLocation skillId) {
      try {
         IAbility ability = TensuraStorages.getAbilityFrom(player);
         boolean changed = false;
         if (skill != null) {
            changed = ability.removeSkillFromPresets(skill);
         }

         if (!changed) {
            changed = ability.removeSkillFromPresets(slot -> slot != null && slot.getSkill() != null && skillId.equals(slot.getSkill().getRegistryName()));
         }

         if (changed) {
            ability.markDirty();
         }
      } catch (RuntimeException e) {
         TensuraSkillDisabled.LOGGER.debug("Failed to remove {} from Tensura ability presets", skillId, e);
      }
   }

   private static void clearSTExtrasLocks(ServerPlayer player, ResourceLocation skillId) {
      try {
         Class<?> accessClass = Class.forName("org.crypticdev.stextras.storage.player.ISTExtrasPlayer");
         if (!accessClass.isInstance(player)) {
            return;
         }

         Method removeLockedSkill = accessClass.getMethod("removeLockedSkill", ResourceLocation.class);
         Method removeAdminLockedSkill = accessClass.getMethod("removeAdminLockedSkill", ResourceLocation.class);
         boolean removedNormal = Boolean.TRUE.equals(removeLockedSkill.invoke(player, skillId));
         boolean removedAdmin = Boolean.TRUE.equals(removeAdminLockedSkill.invoke(player, skillId));
         if (removedNormal || removedAdmin) {
            TensuraSkillDisabled.LOGGER.debug("Cleared STExtExtras locks for blacklisted skill {}", skillId);
         }
      } catch (ClassNotFoundException var7) {
      } catch (ReflectiveOperationException | RuntimeException e) {
         TensuraSkillDisabled.LOGGER.debug("Failed to clear STExtExtras locks for {}", skillId, e);
      }
   }

   private static void sync(ServerPlayer player) {
      ((StorageHolder)player).manasCore$sync(true);
   }

   private static Field findField(String name) {
      try {
         Field field = SkillStorage.class.getDeclaredField(name);
         field.setAccessible(true);
         return field;
      } catch (ReflectiveOperationException e) {
         throw new IllegalStateException("ManasCore SkillStorage field not found: " + name, e);
      }
   }

   record RemovalResult(boolean removed, boolean forced, boolean failed) {
      static SkillRemovalHelper.RemovalResult removed(boolean forced) {
         return new SkillRemovalHelper.RemovalResult(true, forced, false);
      }

      static SkillRemovalHelper.RemovalResult notPresent() {
         return new SkillRemovalHelper.RemovalResult(false, false, false);
      }

      static SkillRemovalHelper.RemovalResult failure() {
         return new SkillRemovalHelper.RemovalResult(false, false, true);
      }
   }
}
