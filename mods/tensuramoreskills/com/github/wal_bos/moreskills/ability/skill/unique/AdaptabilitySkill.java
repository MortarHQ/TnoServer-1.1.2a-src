package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.effect.IEffect;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class AdaptabilitySkill extends Skill {
   public static final String TAG_BLADE = "tms_extermination_blade";
   public static final String PD_ROOT = "tms_adaptability";
   public static final String PD_DR = "drStacks";
   public static final String PD_SEVER_HITS = "severHits";
   public static final String PD_SEVER_IMMUNE = "severImmune";
   public static final String PD_GLOBAL_TICK = "globalTick";
   public static final ResourceLocation SKILL_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "adaptability");

   public AdaptabilitySkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.AdaptabilityConfig cfg() {
      return MoreSkillsConfigs.GRAND.adaptability;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)cfg().obtainCost.get();
   }

   public int getMaxMastery() {
      return (Integer)cfg().maxMastery.get();
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/adaptability.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public int getModes(ManasSkillInstance instance) {
      return 1;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "adaptability.extermination_blade";
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         if (instance.isToggled()) {
            CompoundTag root = getRoot(player);
            if (root.getBoolean("severImmune")) {
               clearSeveranceDamage(player);
            }

            int tick = root.getInt("globalTick") + 1;
            if (tick >= (Integer)cfg().globalMasteryIntervalTicks.get()) {
               tick = 0;
               applyGlobalMasteryBonus(player, instance);
            }

            root.putInt("globalTick", tick);
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (!instance.onCoolDown(mode)) {
            ItemStack blade = AdaptabilitySkill.BladeUtil.createBladeStack(player, instance.isMastered(player));
            if (player.getMainHandItem().isEmpty()) {
               player.setItemInHand(InteractionHand.MAIN_HAND, blade);
            } else if (player.getOffhandItem().isEmpty()) {
               player.setItemInHand(InteractionHand.OFF_HAND, blade);
            } else if (!player.addItem(blade)) {
               player.drop(blade, false);
            }

            instance.setCoolDown((Integer)cfg().bladeCooldownTicks.get(), mode);
            addAdaptivePoints(instance, player, 1);
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.adaptability.blade_summon").withStyle(ChatFormatting.LIGHT_PURPLE), true
            );
         }
      }
   }

   public static void handleIncomingDamage(ServerPlayer player, DamageSource source, AdaptabilitySkill.MutableDamage amountHolder) {
      Optional<ManasSkillInstance> optional = getToggledInstance(player);
      if (!optional.isEmpty()) {
         ManasSkillInstance instance = optional.get();
         CompoundTag root = getRoot(player);
         boolean severanceWithEnchant = TensuraDamageHelper.isSeveranceDamage(source, player, true);
         boolean severanceWithoutEnchant = TensuraDamageHelper.isSeveranceDamage(source, player, false);
         boolean physical = TensuraDamageHelper.isPhysicalAttack(source);
         if (root.getBoolean("severImmune")) {
            if (severanceWithEnchant) {
               clearSeveranceDamage(player);
               addAdaptivePoints(instance, player, 1);
            }

            if (severanceWithoutEnchant && !physical) {
               amountHolder.amount = 0.0F;
               amountHolder.cancel = true;
               return;
            }
         }

         if (severanceWithEnchant) {
            recordSeveranceHit(instance, player, root);
         }

         int maxStacks = instance.isMastered(player) ? (Integer)cfg().maxStacksMastered.get() : (Integer)cfg().maxStacksNormal.get();
         int stacks = root.getInt("drStacks");
         if (stacks < maxStacks) {
            root.putInt("drStacks", stacks + 1);
            addAdaptivePoints(instance, player, 1);
         }

         stacks = root.getInt("drStacks");
         if (stacks > 0) {
            float multiplier = 1.0F - (float)(stacks * (Double)cfg().damageReductionPerHit.get());
            float minimumMultiplier = ((Double)cfg().minimumDamageMultiplier.get()).floatValue();
            if (multiplier < minimumMultiplier) {
               multiplier = minimumMultiplier;
            }

            amountHolder.amount *= multiplier;
         }
      }
   }

   public static void handleResistanceLearning(ManasSkillInstance learnedInstance, LivingEntity entity, Changeable<Double> pointHolder) {
      if (learnedInstance.getSkill() instanceof ResistSkill) {
         if (!(learnedInstance.getMastery() >= 0.0)) {
            Optional<ManasSkillInstance> optional = getToggledInstance(entity);
            if (!optional.isEmpty()) {
               ManasSkillInstance adaptability = optional.get();
               double oldMastery = learnedInstance.getMastery();
               double newMastery = (Double)pointHolder.get();
               double gained = newMastery - oldMastery;
               if (!(gained <= 0.0)) {
                  double multiplier = adaptability.isMastered(entity)
                     ? (Double)cfg().resistanceLearningMultiplierMastered.get()
                     : (Double)cfg().resistanceLearningMultiplier.get();
                  pointHolder.set(oldMastery + gained * multiplier);
                  addAdaptivePoints(adaptability, entity, 1);
               }
            }
         }
      }
   }

   private static Optional<ManasSkillInstance> getToggledInstance(LivingEntity entity) {
      Skills skills = SkillAPI.getSkillsFrom(entity);
      if (skills == null) {
         return Optional.empty();
      }

      Optional<ManasSkillInstance> optional = skills.getSkill(SKILL_ID);
      if (optional.isEmpty()) {
         return Optional.empty();
      }

      ManasSkillInstance instance = optional.get();
      return instance.isToggled() ? optional : Optional.empty();
   }

   private static void clearSeveranceDamage(ServerPlayer player) {
      try {
         IEffect effect = TensuraStorages.getEffectFrom(player);
         if (effect.getSeveranceAmount() > 0.0F) {
            effect.setSeveranceAmount(0.0F);
            effect.setSeveranceRemoveTime(0);
            effect.markDirty();
         }
      } catch (Throwable var2) {
      }
   }

   private static void applyGlobalMasteryBonus(ServerPlayer player, ManasSkillInstance adaptability) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      if (skills != null) {
         Collection<ManasSkillInstance> learnedLive = skills.getLearnedSkills();
         if (learnedLive != null && !learnedLive.isEmpty()) {
            double amount = adaptability.isMastered(player) ? (Double)cfg().globalMasteryBonusMastered.get() : (Double)cfg().globalMasteryBonus.get();

            for (ManasSkillInstance instance : new ArrayList<>(learnedLive)) {
               if (instance != null) {
                  try {
                     instance.addMasteryPoint(player, amount);
                  } catch (Throwable var10) {
                  }
               }
            }
         }
      }
   }

   private static void addAdaptivePoints(ManasSkillInstance adaptability, LivingEntity entity, int base) {
      double amount = base + (Double)cfg().adaptiveMasteryBaseBonus.get();
      if (adaptability.isMastered(entity)) {
         amount += cfg().adaptiveMasteryMasteredBonus.get();
      }

      amount = Math.min(amount, (Double)cfg().adaptiveMasteryMaxBonus.get());

      try {
         adaptability.addMasteryPoint(entity, amount);
      } catch (Throwable var6) {
      }
   }

   private static void recordSeveranceHit(ManasSkillInstance instance, ServerPlayer player, CompoundTag root) {
      if (!root.getBoolean("severImmune")) {
         int hits = root.getInt("severHits") + 1;
         root.putInt("severHits", hits);
         if (hits >= (Integer)cfg().severanceHitsForImmunity.get()) {
            root.putBoolean("severImmune", true);
            root.putInt("severHits", 0);
            clearSeveranceDamage(player);
            player.displayClientMessage(Component.translatable("tensuramoreskills.skill.adaptability.severance_immunity").withStyle(ChatFormatting.AQUA), true);
            addAdaptivePoints(instance, player, 5);
         }
      }
   }

   public static CompoundTag getRoot(ServerPlayer player) {
      CompoundTag persistent = player.getPersistentData();
      if (!persistent.contains("tms_adaptability")) {
         persistent.put("tms_adaptability", new CompoundTag());
      }

      return persistent.getCompound("tms_adaptability");
   }

   public static final class BladeUtil {
      private BladeUtil() {
      }

      public static ItemStack createBladeStack(ServerPlayer owner, boolean mastered) {
         ItemStack stack = new ItemStack((ItemLike)TenaddexItems.EXTERMINATION_BLADE.get());
         ExterminationBladeItem.markAsBlade(stack);
         ExterminationBladeItem.ensureEp(stack);
         ExterminationBladeItem.applyBladeEnchantments(owner.level(), stack, mastered);
         ExterminationBladeItem.applyBladeName(stack);
         return stack;
      }
   }

   public static final class MutableDamage {
      public float amount;
      public boolean cancel;

      public MutableDamage(float amount) {
         this.amount = amount;
      }
   }
}
