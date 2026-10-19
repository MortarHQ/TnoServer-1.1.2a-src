package com.foxablazeultimate.event;

import com.foxablazeultimate.ability.skill.beelzebub.BeelzebubFusion;
import com.foxablazeultimate.ability.skill.ciel.CielAwakening;
import com.foxablazeultimate.ability.skill.ciel.CielState;
import com.foxablazeultimate.ability.skill.raphael.RaphaelFusion;
import com.foxablazeultimate.compat.stextras.STExtrasCompat;
import com.foxablazeultimate.config.FoxAblazeUltimateConfig;
import com.foxablazeultimate.item.CrystalLockState;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.registry.LegacySkillIds;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.PlayerEvent.PlayerJoin;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.RemoveSkillEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.UnlockSkillEvent;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.subclass.ISubAbilityModeHolder;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public final class UltimateSkillProtector {
   private static final ThreadLocal<UltimateSkillProtector.RemovalAllowance> EVOLUTION_REMOVAL = new ThreadLocal<>();
   private static final ThreadLocal<UltimateSkillProtector.RemovalAllowance> INACTIVITY_REMOVAL = new ThreadLocal<>();
   private static final ThreadLocal<UUID> CIEL_ACQUISITION = new ThreadLocal<>();
   private static final Map<UUID, Set<ResourceLocation>> UNIQUE_CONFLICT_NOTIFICATIONS = new HashMap<>();

   private UltimateSkillProtector() {
   }

   public static void init() {
      SkillEvents.UNLOCK_SKILL
         .register(
            (UnlockSkillEvent)(instance, owner, unlockMessage) -> {
               if (instance == null) {
                  return EventResult.pass();
               }

               ResourceLocation skillId = instance.getSkillId();
               if (!isCiel(skillId)
                  || owner instanceof ServerPlayer player && !instance.isTemporarySkill() && !instance.isSubInstance() && isCielAcquisitionAllowed(player)) {
                  if (owner instanceof ServerPlayer player) {
                     if (FoxAblazeUltimateSkills.RAPHAEL.getId().equals(skillId)
                        && !instance.isTemporarySkill()
                        && !instance.isSubInstance()
                        && !CielState.isAwakened(player)
                        && SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.CIEL.get()).isEmpty()) {
                        CielState.resetAcquisitionProgress(player);
                     }

                     if (FoxAblazeUltimateSkills.RAPHAEL.getId().equals(skillId)
                        && (
                           CielState.isAwakened(player)
                              || SkillAPI.getSkillsFrom(player)
                                 .getSkill((ManasSkill)FoxAblazeUltimateSkills.CIEL.get())
                                 .filter(existing -> !existing.isTemporarySkill() && !existing.isSubInstance())
                                 .isPresent()
                        )) {
                        return EventResult.interrupt(false);
                     }

                     if (isFoxUltimateSkill(skillId) && instance.isSubInstance()) {
                        removeRejectedSubInstance(player, instance);
                        player.displayClientMessage(
                           Component.translatable("foxablazeultimate.skill.unique.copy_forbidden", new Object[]{instance.getChatDisplayName(false)}), true
                        );
                        return EventResult.interrupt(false);
                     }

                     if (!isTrulyUniqueEnabled(player, skillId)) {
                        clearUniqueConflictNotification(player, skillId);
                        return EventResult.pass();
                     }

                     if (instance.isTemporarySkill()) {
                        player.displayClientMessage(
                           Component.translatable("foxablazeultimate.skill.unique.copy_forbidden", new Object[]{instance.getChatDisplayName(false)}), true
                        );
                        return EventResult.interrupt(false);
                     }

                     if (!isOwnedByOther(player, skillId)) {
                        clearUniqueConflictNotification(player, skillId);
                        return EventResult.pass();
                     }

                     if (markUniqueConflictNotified(player, skillId)) {
                        player.displayClientMessage(
                           Component.translatable("foxablazeultimate.skill.unique.taken", new Object[]{instance.getChatDisplayName(false)}), true
                        );
                     }

                     return EventResult.interrupt(false);
                  } else {
                     return EventResult.pass();
                  }
               } else {
                  if (owner instanceof ServerPlayer player) {
                     player.displayClientMessage(Component.translatable("foxablazeultimate.skill.ciel.acquisition_forbidden"), true);
                  }

                  return EventResult.interrupt(false);
               }
            }
         );
      PlayerEvent.PLAYER_JOIN.register((PlayerJoin)player -> {
         LegacySkillIdMigrationHandler.migratePlayerLocks(player);
         removeLegacyUltimateCopies(player);
         reconcileUniqueOwnership(player);
      });
      SkillEvents.REMOVE_SKILL
         .register(
            (RemoveSkillEvent)(instance, owner, forgetMessage) -> {
               if (instance != null && owner != null) {
                  ResourceLocation skillId = instance.getSkillId();
                  if (isInactivityRemoval(owner, skillId)) {
                     return EventResult.pass();
                  }

                  UltimateSkillProtector.RemovalAllowance allowance = EVOLUTION_REMOVAL.get();
                  if (allowance != null && owner.getUUID().equals(allowance.owner()) && skillId != null && allowance.skills().contains(skillId)) {
                     return EventResult.pass();
                  }

                  if (isCiel(skillId)) {
                     if (owner instanceof ServerPlayer player
                        && CielState.isAwakened(player)
                        && instance.isTemporarySkill()
                        && CielAwakening.isSoulBoundInstance(instance)) {
                        instance.setRemoveTime(-1);
                        return EventResult.interrupt(false);
                     } else if (owner instanceof ServerPlayer player && isCielResetProtected(player)) {
                        retainWisdomUniqueOwnership(player);
                        return EventResult.interrupt(false);
                     } else {
                        return EventResult.pass();
                     }
                  } else if (!isShubNiggurath(instance)) {
                     if (skillId != null
                        && owner instanceof ServerPlayer player
                        && !FoxAblazeUltimateConfig.disableCrystalSkillLocking(skillId)
                        && CrystalLockState.isCrystalLocked(player, skillId)
                        && !STExtrasLoadedSafe()) {
                        retainUniqueOwnership(player, skillId);
                        return EventResult.interrupt(false);
                     } else {
                        if (!isGlobalUltimateResetProtectionEnabled(owner)) {
                           return EventResult.pass();
                        }

                        if (!isProtectedUltimate(instance.getSkill())) {
                           return EventResult.pass();
                        }

                        if (owner instanceof ServerPlayer player) {
                           retainUniqueOwnership(player, skillId);
                        }

                        return EventResult.interrupt(false);
                     }
                  } else if (!(
                     owner instanceof ServerPlayer player && (isGlobalUltimateResetProtectionEnabled(player) || isLockedShubNiggurath(player, skillId))
                  )) {
                     return EventResult.pass();
                  } else {
                     retainShubUniqueOwnership(player, skillId);
                     return EventResult.interrupt(false);
                  }
               } else {
                  return EventResult.pass();
               }
            }
         );
      TensuraSkillEvents.SKILL_PLUNDER
         .register(
            (SkillPlunderEvent)(attacker, target, death, selectedSkill) -> {
               ManasSkill selected = selectedSkill == null ? null : (ManasSkill)selectedSkill.get();
               boolean yogSothoth = selected != null
                  && FoxAblazeUltimateSkills.yogSothothHolder()
                     .map(holder -> selected.getRegistryName() != null && selected.getRegistryName().equals(holder.getId()))
                     .orElse(false);
               return selected != FoxAblazeUltimateSkills.CIEL.get() && !yogSothoth ? EventResult.pass() : EventResult.interrupt(false);
            }
         );
   }

   private static void removeRejectedSubInstance(ServerPlayer player, ManasSkillInstance rejected) {
      ManasSkill parentSkill = rejected.getParentSkill();
      if (parentSkill != null) {
         SkillAPI.getSkillsFrom(player).getSkill(parentSkill).ifPresent(parent -> {
            if (parent.getSubInstances().remove(rejected.getSkill(), rejected)) {
               parent.markDirty();
               SkillAPI.getSkillsFrom(player).markDirty();
            }
         });
      }
   }

   private static boolean isShubNiggurath(ManasSkillInstance instance) {
      ResourceLocation id = instance.getSkillId();
      ResourceLocation shubId = FoxAblazeUltimateSkills.SHUB_NIGGURATH.getId();
      return id != null && id.equals(shubId);
   }

   private static boolean isOwnedByOther(ServerPlayer player, ResourceLocation skillId) {
      if (skillId == null) {
         return false;
      }

      MinecraftServer server = player.getServer();
      if (server == null) {
         return false;
      }

      ServerLevel overworld = server.overworld();
      if (overworld == null) {
         return false;
      }

      ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
      return !isWisdomFamily(skillId)
         ? isOwnedByOther(unique, player, skillId)
         : isOwnedByOther(unique, player, FoxAblazeUltimateSkills.RAPHAEL.getId()) || isOwnedByOther(unique, player, FoxAblazeUltimateSkills.CIEL.getId());
   }

   private static boolean isOwnedByOther(ITrulyUnique unique, ServerPlayer player, ResourceLocation skillId) {
      LegacySkillIds.migrateUniqueOwnership(unique);
      if (LegacySkillIds.hasConflictingLegacyOwner(unique, skillId, player.getUUID())) {
         return true;
      }

      UUID owner = skillId == null ? null : unique.getOwner(skillId);
      return owner != null && !owner.equals(player.getUUID());
   }

   private static boolean markUniqueConflictNotified(ServerPlayer player, ResourceLocation skillId) {
      return skillId == null ? false : UNIQUE_CONFLICT_NOTIFICATIONS.computeIfAbsent(player.getUUID(), ignored -> new HashSet<>()).add(skillId);
   }

   private static void clearUniqueConflictNotification(ServerPlayer player, ResourceLocation skillId) {
      if (skillId != null) {
         Set<ResourceLocation> notifiedSkills = UNIQUE_CONFLICT_NOTIFICATIONS.get(player.getUUID());
         if (notifiedSkills != null) {
            notifiedSkills.remove(skillId);
            if (notifiedSkills.isEmpty()) {
               UNIQUE_CONFLICT_NOTIFICATIONS.remove(player.getUUID());
            }
         }
      }
   }

   private static boolean isFoxUltimateSkill(ResourceLocation skillId) {
      return skillId == null
         ? false
         : skillId.equals(FoxAblazeUltimateSkills.RAPHAEL.getId())
            || skillId.equals(FoxAblazeUltimateSkills.CIEL.getId())
            || skillId.equals(FoxAblazeUltimateSkills.BEELZEBUB.getId())
            || skillId.equals(FoxAblazeUltimateSkills.URIEL.getId())
            || skillId.equals(FoxAblazeUltimateSkills.VELDORA.getId())
            || skillId.equals(FoxAblazeUltimateSkills.SATANAEL.getId())
            || skillId.equals(FoxAblazeUltimateSkills.SHUB_NIGGURATH.getId())
            || skillId.equals(FoxAblazeUltimateSkills.FORTUNA.getId())
            || skillId.equals(FoxAblazeUltimateSkills.MAMMON.getId())
            || skillId.equals(FoxAblazeUltimateSkills.HELL_SNIPER.getId())
            || skillId.equals(FoxAblazeUltimateSkills.VELGRYND.getId())
            || FoxAblazeUltimateSkills.yogSothothHolder().map(holder -> skillId.equals(holder.getId())).orElse(false);
   }

   private static boolean isTrulyUniqueEnabled(ServerPlayer player, ResourceLocation skillId) {
      if (skillId == null) {
         return false;
      } else {
         Level level = player.level();
         if (isWisdomFamily(skillId)) {
            return level.getGameRules().getBoolean(FoxAblazeGameRules.RAPHAEL_TRULY_UNIQUE);
         } else if (skillId.equals(FoxAblazeUltimateSkills.BEELZEBUB.getId())) {
            return level.getGameRules().getBoolean(FoxAblazeGameRules.BEELZEBUB_TRULY_UNIQUE);
         } else if (skillId.equals(FoxAblazeUltimateSkills.VELDORA.getId())) {
            return level.getGameRules().getBoolean(FoxAblazeGameRules.VELDORA_TRULY_UNIQUE);
         } else if (skillId.equals(FoxAblazeUltimateSkills.SATANAEL.getId())) {
            return level.getGameRules().getBoolean(FoxAblazeGameRules.SATANAEL_TRULY_UNIQUE);
         } else if (skillId.equals(FoxAblazeUltimateSkills.SHUB_NIGGURATH.getId())) {
            return level.getGameRules().getBoolean(FoxAblazeGameRules.SHUB_NIGGURATH_TRULY_UNIQUE);
         } else if (skillId.equals(FoxAblazeUltimateSkills.FORTUNA.getId())) {
            return level.getGameRules().getBoolean(FoxAblazeGameRules.FORTUNA_TRULY_UNIQUE);
         } else if (skillId.equals(FoxAblazeUltimateSkills.MAMMON.getId())) {
            return level.getGameRules().getBoolean(FoxAblazeGameRules.MAMMON_TRULY_UNIQUE);
         } else if (skillId.equals(FoxAblazeUltimateSkills.HELL_SNIPER.getId())) {
            return level.getGameRules().getBoolean(FoxAblazeGameRules.HELL_SNIPER_TRULY_UNIQUE);
         } else {
            return FoxAblazeUltimateSkills.yogSothothHolder().map(holder -> skillId.equals(holder.getId())).orElse(false)
               ? level.getGameRules().getBoolean(FoxAblazeGameRules.YOG_SOTHOTH_TRULY_UNIQUE)
               : false;
         }
      }
   }

   private static void removeLegacyUltimateCopies(ServerPlayer player) {
      boolean changed = false;

      for (ManasSkillInstance parent : List.copyOf(SkillAPI.getSkillsFrom(player).getLearnedSkills())) {
         if (parent.getSkill() instanceof ISubAbilityModeHolder holder) {
            List<AbilitySlot> forbiddenSlots = ISubAbilityModeHolder.getSubSlots(parent)
               .stream()
               .filter(slotx -> slotx.getSkill() != null)
               .filter(slotx -> isFoxUltimateSkill(slotx.getSkill().getRegistryName()))
               .toList();
            Set<ManasSkill> forbiddenSkills = new HashSet<>();
            parent.getSubInstances()
               .keySet()
               .stream()
               .filter(skill -> skill != null && isFoxUltimateSkill(skill.getRegistryName()))
               .forEach(forbiddenSkills::add);
            forbiddenSlots.stream().map(AbilitySlot::getSkill).forEach(forbiddenSkills::add);
            if (!forbiddenSlots.isEmpty() || !forbiddenSkills.isEmpty()) {
               for (AbilitySlot slot : forbiddenSlots) {
                  holder.removeSubSkill(parent, player, slot.getSkill(), slot.getMode());
               }

               forbiddenSkills.forEach(parent.getSubInstances()::remove);
               parent.markDirty();
               changed = true;
            }
         }
      }

      if (changed) {
         SkillAPI.getSkillsFrom(player).markDirty();
         ((StorageHolder)player).manasCore$sync(player);
         player.displayClientMessage(Component.translatable("foxablazeultimate.skill.unique.legacy_copies_removed"), false);
      }
   }

   public static Set<ResourceLocation> captureResetProtectedUniqueSkills(ServerPlayer player) {
      if (player == null) {
         return Set.of();
      }

      Set<ResourceLocation> protectedSkills = new HashSet<>();

      for (ManasSkillInstance instance : List.copyOf(SkillAPI.getSkillsFrom(player).getLearnedSkills())) {
         if (isPermanentFoxUltimate(instance)) {
            ResourceLocation skillId = instance.getSkillId();
            if (isTrulyUniqueEnabled(player, skillId) && isProtectedDuringReset(player, skillId) && !isOwnedByOther(player, skillId)) {
               protectedSkills.add(skillId);
            }
         }
      }

      return Set.copyOf(protectedSkills);
   }

   public static void restoreResetUniqueOwnership(ServerPlayer player, Collection<ResourceLocation> protectedSkills) {
      if (player != null) {
         if (protectedSkills != null) {
            protectedSkills.forEach(skillId -> retainUniqueOwnership(player, skillId));
         }

         reconcileUniqueOwnership(player);
      }
   }

   public static void reconcileUniqueOwnership(ServerPlayer player) {
      if (player != null) {
         for (ManasSkillInstance instance : List.copyOf(SkillAPI.getSkillsFrom(player).getLearnedSkills())) {
            if (isPermanentFoxUltimate(instance)) {
               retainUniqueOwnership(player, instance.getSkillId());
            }
         }
      }
   }

   public static boolean canAcquireUnique(ServerPlayer player, ResourceLocation skillId) {
      if (player != null && skillId != null) {
         try {
            return !isTrulyUniqueEnabled(player, skillId) || !isOwnedByOther(player, skillId);
         } catch (RuntimeException ignored) {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean isPermanentFoxUltimate(ManasSkillInstance instance) {
      return instance != null
         && instance.getMastery() >= 0.0
         && !instance.isTemporarySkill()
         && !instance.isSubInstance()
         && isFoxUltimateSkill(instance.getSkillId())
         && isProtectedUltimate(instance.getSkill());
   }

   private static boolean isProtectedDuringReset(ServerPlayer player, ResourceLocation skillId) {
      if (isGlobalUltimateResetProtectionEnabled(player)) {
         return true;
      } else {
         return STExtrasLoadedSafe()
            ? STExtrasCompat.isSkillLocked(player, skillId)
            : !FoxAblazeUltimateConfig.disableCrystalSkillLocking(skillId) && CrystalLockState.isCrystalLocked(player, skillId);
      }
   }

   private static void retainUniqueOwnership(ServerPlayer player, ResourceLocation skillId) {
      if (skillId != null && isTrulyUniqueEnabled(player, skillId) && !isOwnedByOther(player, skillId)) {
         if (isWisdomFamily(skillId)) {
            retainWisdomUniqueOwnership(player);
         } else if (skillId.equals(FoxAblazeUltimateSkills.SHUB_NIGGURATH.getId())) {
            retainShubUniqueOwnership(player, skillId);
         } else {
            MinecraftServer server = player.getServer();
            if (server != null) {
               ServerLevel overworld = server.overworld();
               if (overworld != null) {
                  ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
                  UUID currentOwner = unique.getOwner(skillId);
                  if (currentOwner == null || currentOwner.equals(player.getUUID())) {
                     unique.addSkill(skillId, player.getUUID());
                     unique.markDirty();
                  }
               }
            }
         }
      }
   }

   private static boolean isLockedShubNiggurath(ServerPlayer player, ResourceLocation skillId) {
      return isCrystalLockedForReset(player, skillId);
   }

   private static boolean isCrystalLockedForReset(ServerPlayer player, ResourceLocation skillId) {
      return skillId != null
         && !FoxAblazeUltimateConfig.disableCrystalSkillLocking(skillId)
         && CrystalLockState.isCrystalLocked(player, skillId)
         && (!STExtrasLoadedSafe() || STExtrasCompat.isSkillLocked(player, skillId));
   }

   public static boolean isCielResetProtected(ServerPlayer player) {
      return player != null && (isGlobalUltimateResetProtectionEnabled(player) || isCrystalLockedForReset(player, FoxAblazeUltimateSkills.CIEL.getId()));
   }

   private static boolean isGlobalUltimateResetProtectionEnabled(LivingEntity owner) {
      Level level = owner == null ? null : owner.level();
      return level != null && level.getGameRules().getBoolean(FoxAblazeGameRules.PROTECT_ULTIMATE_ON_RESET);
   }

   private static void retainShubUniqueOwnership(ServerPlayer player, ResourceLocation skillId) {
      if (skillId != null && player.level().getGameRules().getBoolean(FoxAblazeGameRules.SHUB_NIGGURATH_TRULY_UNIQUE)) {
         MinecraftServer server = player.getServer();
         if (server != null) {
            ServerLevel overworld = server.overworld();
            if (overworld != null) {
               ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
               UUID currentOwner = unique.getOwner(skillId);
               if (currentOwner == null || currentOwner.equals(player.getUUID())) {
                  unique.addSkill(skillId, player.getUUID());
                  unique.markDirty();
               }
            }
         }
      }
   }

   private static void retainWisdomUniqueOwnership(ServerPlayer player) {
      if (player.level().getGameRules().getBoolean(FoxAblazeGameRules.RAPHAEL_TRULY_UNIQUE)) {
         MinecraftServer server = player.getServer();
         if (server != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(server.overworld());
            boolean changed = false;

            for (ResourceLocation id : List.of(FoxAblazeUltimateSkills.RAPHAEL.getId(), FoxAblazeUltimateSkills.CIEL.getId())) {
               UUID owner = unique.getOwner(id);
               if (owner == null || owner.equals(player.getUUID())) {
                  unique.addSkill(id, player.getUUID());
                  changed = true;
               }
            }

            if (changed) {
               unique.markDirty();
            }
         }
      }
   }

   public static void withEvolutionRemoval(ServerPlayer owner, Collection<ResourceLocation> skills, Runnable action) {
      withRemovalAllowance(EVOLUTION_REMOVAL, owner, skills, action);
   }

   public static boolean isEvolutionRemoval(LivingEntity owner, ResourceLocation skillId) {
      if (owner != null && skillId != null) {
         UltimateSkillProtector.RemovalAllowance allowance = EVOLUTION_REMOVAL.get();
         return allowance != null && owner.getUUID().equals(allowance.owner()) && allowance.skills().contains(skillId);
      } else {
         return false;
      }
   }

   public static boolean withCielAcquisition(ServerPlayer owner, BooleanSupplier action) {
      UUID previous = CIEL_ACQUISITION.get();
      CIEL_ACQUISITION.set(owner.getUUID());

      try {
         return action.getAsBoolean();
      } finally {
         if (previous == null) {
            CIEL_ACQUISITION.remove();
         } else {
            CIEL_ACQUISITION.set(previous);
         }
      }
   }

   private static boolean isCielAcquisitionAllowed(ServerPlayer owner) {
      UUID allowed = CIEL_ACQUISITION.get();
      return allowed != null && allowed.equals(owner.getUUID());
   }

   public static void withInactivityRemoval(ServerPlayer owner, Collection<ResourceLocation> skills, Runnable action) {
      Set<ResourceLocation> allowed = immutableSkillSet(skills);
      UltimateSkillProtector.RemovalAllowance previous = INACTIVITY_REMOVAL.get();
      INACTIVITY_REMOVAL.set(new UltimateSkillProtector.RemovalAllowance(owner.getUUID(), allowed));

      try {
         for (ResourceLocation skillId : allowed) {
            STExtrasCompat.removeSkillLocks(owner, skillId);
         }

         action.run();
      } finally {
         try {
            cleanupInactivityRemoval(owner, allowed);
         } finally {
            restoreRemovalAllowance(INACTIVITY_REMOVAL, previous);
         }
      }
   }

   public static boolean isInactivityRemoval(LivingEntity owner, ResourceLocation skillId) {
      if (owner != null && skillId != null) {
         UltimateSkillProtector.RemovalAllowance allowance = INACTIVITY_REMOVAL.get();
         return allowance != null && owner.getUUID().equals(allowance.owner()) && allowance.skills().contains(skillId);
      } else {
         return false;
      }
   }

   private static void cleanupInactivityRemoval(ServerPlayer owner, Set<ResourceLocation> skills) {
      IAbility ability = TensuraStorages.getAbilityFrom(owner);
      ITensuraPlayer playerData = TensuraStorages.getPlayerDataFrom(owner);
      boolean abilityChanged = false;
      boolean playerDataChanged = false;

      for (ResourceLocation skillId : skills) {
         if (!SkillAPI.getSkillsFrom(owner).getSkill(skillId).isPresent()) {
            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
            if (skill != null) {
               abilityChanged |= ability.removeSkillFromPresets(skill);
            }

            if (playerData.getLockedSkills().contains(skillId)) {
               playerData.removeLockedSkill(skillId);
               playerDataChanged = true;
            }

            CrystalLockState.removeCrystalLock(owner, skillId);
            STExtrasCompat.removeSkillLocks(owner, skillId);
            if (skillId.equals(FoxAblazeUltimateSkills.RAPHAEL.getId())) {
               RaphaelFusion.markInactivityReclaimed(owner);
            } else if (skillId.equals(FoxAblazeUltimateSkills.BEELZEBUB.getId())) {
               BeelzebubFusion.markInactivityReclaimed(owner);
            } else if (skillId.equals(FoxAblazeUltimateSkills.SATANAEL.getId())) {
               SatanaelAcquisitionHandler.markInactivityReclaimed(owner);
            } else if (skillId.equals(FoxAblazeUltimateSkills.VELDORA.getId())) {
               VeldoraAcquisitionHandler.markInactivityReclaimed(owner);
            }
         }
      }

      if (abilityChanged) {
         ability.markDirty();
      }

      if (playerDataChanged) {
         playerData.markDirty();
      }
   }

   private static void withRemovalAllowance(
      ThreadLocal<UltimateSkillProtector.RemovalAllowance> context, ServerPlayer owner, Collection<ResourceLocation> skills, Runnable action
   ) {
      UltimateSkillProtector.RemovalAllowance previous = context.get();
      context.set(new UltimateSkillProtector.RemovalAllowance(owner.getUUID(), immutableSkillSet(skills)));

      try {
         action.run();
      } finally {
         restoreRemovalAllowance(context, previous);
      }
   }

   private static Set<ResourceLocation> immutableSkillSet(Collection<ResourceLocation> skills) {
      return skills.stream().filter(Objects::nonNull).collect(Collectors.toUnmodifiableSet());
   }

   private static void restoreRemovalAllowance(ThreadLocal<UltimateSkillProtector.RemovalAllowance> context, UltimateSkillProtector.RemovalAllowance previous) {
      if (previous == null) {
         context.remove();
      } else {
         context.set(previous);
      }
   }

   private static boolean STExtrasLoadedSafe() {
      try {
         return STExtrasCompat.isLoaded();
      } catch (Throwable t) {
         return false;
      }
   }

   private static boolean isCiel(ResourceLocation skillId) {
      return skillId != null && skillId.equals(FoxAblazeUltimateSkills.CIEL.getId());
   }

   private static boolean isWisdomFamily(ResourceLocation skillId) {
      return skillId != null && (skillId.equals(FoxAblazeUltimateSkills.RAPHAEL.getId()) || skillId.equals(FoxAblazeUltimateSkills.CIEL.getId()));
   }

   private static boolean isProtectedUltimate(ManasSkill skill) {
      if (skill == null) {
         return false;
      } else {
         ResourceLocation id = skill.getRegistryName();
         if (id == null || !"foxablazeultimate".equals(id.getNamespace())) {
            return false;
         } else {
            return skill instanceof Skill tSkill ? tSkill.getType() == SkillType.ULTIMATE : false;
         }
      }
   }

   private record RemovalAllowance(UUID owner, Set<ResourceLocation> skills) {
   }
}
