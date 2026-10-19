package com.github.wal_bos.moreutils.util;

import com.github.wal_bos.moreutils.data.MoreUtilsData;
import com.github.wal_bos.moreutils.data.SkillBanType;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public final class MoreUtilsPurge {
   private static final ThreadLocal<Boolean> REFUNDING_MAX_MAGICULES = ThreadLocal.withInitial(() -> false);

   private MoreUtilsPurge() {
   }

   public static boolean isRefundingMaxMagicules() {
      return REFUNDING_MAX_MAGICULES.get();
   }

   public static int purgeSkillOnline(MinecraftServer server, ResourceLocation skillId) {
      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
      if (skill == null) {
         return 0;
      }

      int removed = 0;

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         if (purgeSkillFromEntity(player, skill)) {
            removed++;
         }
      }

      return removed;
   }

   public static int purgeAllBannedSkillsOnline(MinecraftServer server) {
      int removed = 0;
      if (!MoreUtilsData.hasOwningSkillBans()) {
         return 0;
      }

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         removed += purgeAllBannedSkillsFromEntity(player);
      }

      return removed;
   }

   public static int purgeAllBlacklistedSkillsOnline(MinecraftServer server) {
      int removed = 0;
      if (!MoreUtilsData.hasBlacklistedSkills()) {
         return 0;
      }

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         removed += purgeAllBlacklistedSkillsFromEntity(player);
      }

      return removed;
   }

   public static int purgeAllBannedSkillsFromEntity(LivingEntity entity) {
      Skills skills = SkillAPI.getSkillsFrom(entity);
      List<ManasSkill> toRemove = new ArrayList<>();

      for (ManasSkillInstance instance : new ArrayList(skills.getLearnedSkills())) {
         ResourceLocation id = instance.getSkillId();
         SkillBanType type = MoreUtilsData.getSkillBan(id);
         if (type != null && type.blocksOwning()) {
            ManasSkill skill = instance.getSkill();
            if (skill != null) {
               toRemove.add(skill);
            }
         }
      }

      int removed = 0;

      for (ManasSkill skill : toRemove) {
         if (purgeSkillFromEntity(entity, skill)) {
            removed++;
         }
      }

      return removed;
   }

   public static int purgeAllBlacklistedSkillsFromEntity(LivingEntity entity) {
      Skills skills = SkillAPI.getSkillsFrom(entity);
      List<ManasSkill> toRemove = new ArrayList<>();

      for (ManasSkillInstance instance : new ArrayList(skills.getLearnedSkills())) {
         ResourceLocation id = instance.getSkillId();
         SkillBanType type = MoreUtilsData.getSkillBan(id);
         if (type != null && type.isBlacklist()) {
            ManasSkill skill = instance.getSkill();
            if (skill != null) {
               toRemove.add(skill);
            }
         }
      }

      int removed = 0;

      for (ManasSkill skill : toRemove) {
         if (purgeSkillFromEntity(entity, skill)) {
            removed++;
         }
      }

      return removed;
   }

   public static boolean purgeSkillFromEntity(LivingEntity entity, ManasSkill skill) {
      Skills skills = SkillAPI.getSkillsFrom(entity);
      Optional<ManasSkillInstance> optional = skills.getSkill(skill);
      boolean changed = false;
      if (optional.isPresent()) {
         ManasSkillInstance instance = optional.get();
         refundSkill(entity, instance);
         if (instance.isToggled()) {
            instance.setToggled(false);
            instance.onToggleOff(entity);
         }

         instance.onForgetSkill(entity);
         skills.forgetSkill(skill);
         skills.markDirty();
         changed = true;
      }

      if (entity instanceof ServerPlayer player) {
         if (clearAbilitySlots(player, skill)) {
            changed = true;
         }

         if (changed) {
            syncPlayer(player);
         }
      }

      return changed;
   }

   private static void refundSkill(LivingEntity entity, ManasSkillInstance instance) {
      if (entity instanceof ServerPlayer player) {
         ManasSkill skill = instance.getSkill();
         if (skill != null) {
            double refund = getSkillRefundAmount(skill);
            if (!(refund <= 0.0) && !Double.isNaN(refund) && !Double.isInfinite(refund)) {
               REFUNDING_MAX_MAGICULES.set(true);

               try {
                  AttributeInstance maxMagicule = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
                  if (maxMagicule != null) {
                     maxMagicule.setBaseValue(maxMagicule.getBaseValue() + refund);
                  }

                  IExistence existence = TensuraStorages.getExistenceFrom(player);
                  existence.setMagicule(existence.getMagicule() + refund);
                  existence.markDirty();
               } finally {
                  REFUNDING_MAX_MAGICULES.set(false);
               }

               player.sendSystemMessage(Component.literal("Refunded " + formatRefund(refund) + " max magicules for removed skill: " + instance.getSkillId()));
            }
         }
      }
   }

   private static double getSkillRefundAmount(ManasSkill skill) {
      try {
         Method method = skill.getClass().getMethod("getDefaultAcquiringMagiculeCost");
         if (method.invoke(skill) instanceof Number number) {
            return Math.max(0.0, number.doubleValue());
         }
      } catch (Exception var4) {
      }

      return 0.0;
   }

   private static String formatRefund(double amount) {
      if (amount >= 9.223372E18F) {
         return String.valueOf(amount);
      }

      long whole = (long)amount;
      return whole == amount ? String.valueOf(whole) : String.valueOf(amount);
   }

   public static boolean clearAbilitySlots(ServerPlayer player, ManasSkill skill) {
      IAbility ability = TensuraStorages.getAbilityFrom(player);
      boolean changed = false;

      for (int preset = 0; preset < 10; preset++) {
         for (int slot = 0; slot < 10; slot++) {
            try {
               if (ability.getAbilitySlot(preset, slot).getSkill() == skill) {
                  ability.setAbilitySlot(preset, slot, null, 0);
                  changed = true;
               }
            } catch (Exception var7) {
            }
         }
      }

      if (changed) {
         ability.markDirty();
      }

      return changed;
   }

   public static int purgeEngravingOnline(MinecraftServer server, ResourceLocation engravingId) {
      int changed = 0;

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         changed += purgeEngravingFromPlayer(player, engravingId);
      }

      return changed;
   }

   public static int purgeAllBannedEngravingsOnline(MinecraftServer server) {
      int changed = 0;
      if (!MoreUtilsData.hasEngravingBans()) {
         return 0;
      }

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         changed += purgeAllBannedEngravingsFromPlayer(player);
      }

      return changed;
   }

   public static int purgeEngravingFromPlayer(ServerPlayer player, ResourceLocation engravingId) {
      int changed = 0;

      for (ItemStack stack : allPlayerStacks(player)) {
         if (removeEngraving(player.registryAccess(), stack, engravingId)) {
            changed++;
         }
      }

      return changed;
   }

   public static int purgeAllBannedEngravingsFromPlayer(ServerPlayer player) {
      if (!MoreUtilsData.hasEngravingBans()) {
         return 0;
      }

      int changed = 0;

      for (ItemStack stack : allPlayerStacks(player)) {
         boolean changedStack = false;

         for (ResourceLocation id : MoreUtilsData.engravingBans()) {
            if (removeEngraving(player.registryAccess(), stack, id)) {
               changedStack = true;
            }
         }

         if (changedStack) {
            changed++;
         }
      }

      return changed;
   }

   private static List<ItemStack> allPlayerStacks(ServerPlayer player) {
      List<ItemStack> stacks = new ArrayList<>();

      for (ItemStack stack : player.getInventory().items) {
         stacks.add(stack);
      }

      for (ItemStack stack : player.getInventory().armor) {
         stacks.add(stack);
      }

      for (ItemStack stack : player.getInventory().offhand) {
         stacks.add(stack);
      }

      for (EquipmentSlot slot : EquipmentSlot.values()) {
         ItemStack stack = player.getItemBySlot(slot);
         if (!stacks.contains(stack)) {
            stacks.add(stack);
         }
      }

      return stacks;
   }

   public static boolean removeEngraving(RegistryAccess access, ItemStack stack, ResourceLocation engravingId) {
      if (stack.isEmpty()) {
         return false;
      }

      ResourceKey<Enchantment> key = ResourceKey.create(Registries.ENCHANTMENT, engravingId);
      Optional<Reference<Enchantment>> optional = access.registryOrThrow(Registries.ENCHANTMENT).getHolder(key);
      if (optional.isEmpty()) {
         return false;
      }

      Holder<Enchantment> target = (Holder<Enchantment>)optional.get();
      if (EnchantmentHelper.getItemEnchantmentLevel(target, stack) <= 0) {
         return false;
      }

      EnchantmentHelper.updateEnchantments(stack, mutable -> mutable.set(target, 0));
      return true;
   }

   public static void syncPlayer(ServerPlayer player) {
      try {
         Method method = player.getClass().getMethod("manasCore$sync");
         method.invoke(player);
      } catch (Exception var3) {
         try {
            Method method = player.getClass().getMethod("manasCore$sync", ServerPlayer.class);
            method.invoke(player, player);
         } catch (Exception var2) {
         }
      }
   }
}
