package com.foxablazeultimate.item;

import com.foxablazeultimate.ability.skill.ciel.CielState;
import com.foxablazeultimate.config.FoxAblazeUltimateConfig;
import com.foxablazeultimate.event.WisdomCrystalSyncHandler;
import com.foxablazeultimate.network.ShowWisdomCrystalEasterEggPayload;
import com.foxablazeultimate.registry.FoxAblazeUltimateItems;
import com.foxablazeultimate.registry.FoxAblazeUltimateMobEffects;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class WisdomCrystalItem extends Item {
   public static final int WISDOM_BUFF_DURATION_TICKS = 12000;
   public static final double WISDOM_TARGET_MASTERY_GAIN_RATIO = 0.1;
   public static final double WISDOM_FORTUNA_MASTERY_GAIN_RATIO = 0.05;
   private final Supplier<ManasSkill> targetSkill;
   private final List<WisdomCrystalItem.MasteryBoost> masteryBoosts;
   private final double targetMasteryBoostRatio;
   private final int buffDurationTicks;

   public WisdomCrystalItem(Properties properties, Supplier<ManasSkill> targetSkill) {
      this(properties, targetSkill, List.of(), 0.0, 0);
   }

   public WisdomCrystalItem(Properties properties, Supplier<ManasSkill> targetSkill, List<WisdomCrystalItem.MasteryBoost> masteryBoosts) {
      this(properties, targetSkill, masteryBoosts, 0.0, 0);
   }

   public WisdomCrystalItem(
      Properties properties,
      Supplier<ManasSkill> targetSkill,
      List<WisdomCrystalItem.MasteryBoost> masteryBoosts,
      double targetMasteryBoostRatio,
      int buffDurationTicks
   ) {
      super(properties.rarity(Rarity.RARE).food(new Builder().nutrition(0).saturationModifier(0.0F).alwaysEdible().build()));
      this.targetSkill = targetSkill;
      this.masteryBoosts = List.copyOf(masteryBoosts);
      this.targetMasteryBoostRatio = Math.max(0.0, targetMasteryBoostRatio);
      this.buffDurationTicks = Math.max(0, buffDurationTicks);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
      ItemStack result = super.finishUsingItem(stack, level, living);
      if (level.isClientSide) {
         return result;
      } else if (!(living instanceof ServerPlayer player)) {
         return result;
      } else {
         if (this == FoxAblazeUltimateItems.WISDOM_CRYSTAL.get() && CrystalLockState.recordWisdomCrystalForEasterEgg(player)) {
            NetworkManager.sendToPlayer(player, new ShowWisdomCrystalEasterEggPayload());
         }

         SkillStorage skills = SkillAPI.getSkillsFrom(player);
         ManasSkill effectiveTarget = this.resolveTargetSkill(player);
         boolean wisdomCrystal = this == FoxAblazeUltimateItems.WISDOM_CRYSTAL.get();
         if (this.targetMasteryBoostRatio > 0.0) {
            addMasteryByRatio(player, skills, effectiveTarget, this.targetMasteryBoostRatio);
         }

         ManasSkill fortuna = (ManasSkill)FoxAblazeUltimateSkills.FORTUNA.get();
         boolean hasFortuna = wisdomCrystal && hasPermanentSkill(skills, fortuna);
         if (hasFortuna) {
            addMasteryByRatio(player, skills, fortuna, 0.05);
         }

         for (WisdomCrystalItem.MasteryBoost boost : this.masteryBoosts) {
            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(boost.skillId());
            addMasteryByRatio(player, skills, skill, boost.ratio());
         }

         if (this.buffDurationTicks > 0) {
            player.addEffect(new MobEffectInstance(FoxAblazeUltimateMobEffects.WISDOM_BUFF, this.buffDurationTicks, 0, false, true, true));
         }

         boolean lockStateChanged = false;
         ResourceLocation primaryLockId = resolveSkillId(effectiveTarget);
         if (primaryLockId != null && !FoxAblazeUltimateConfig.disableCrystalSkillLocking(primaryLockId)) {
            this.migrateWisdomFamilyProgress(player, primaryLockId);
            lockStateChanged |= recordCrystalLockProgress(player, primaryLockId);
         }

         ResourceLocation fortunaId = hasFortuna ? resolveSkillId(fortuna) : null;
         if (fortunaId != null && !fortunaId.equals(primaryLockId)) {
            lockStateChanged |= recordCrystalLockProgress(player, fortunaId);
         }

         if (lockStateChanged) {
            WisdomCrystalSyncHandler.syncTo(player);
         }

         return result;
      }
   }

   private static boolean hasPermanentSkill(SkillStorage skills, ManasSkill skill) {
      return skill == null
         ? false
         : skills.getSkill(skill)
            .filter(instance -> instance.getMastery() >= 0.0)
            .filter(instance -> !instance.isTemporarySkill() && !instance.isSubInstance())
            .isPresent();
   }

   private static boolean recordCrystalLockProgress(ServerPlayer player, ResourceLocation skillId) {
      if (skillId != null && !FoxAblazeUltimateConfig.disableCrystalSkillLocking(skillId)) {
         int eaten = CrystalLockState.incrementEaten(player, skillId);
         return eaten >= 10 && CrystalLockState.addCrystalLock(player, skillId);
      } else {
         return false;
      }
   }

   private static void addMasteryByRatio(ServerPlayer player, SkillStorage skills, ManasSkill skill, double ratio) {
      if (skill != null) {
         Optional<ManasSkillInstance> opt = skills.getSkill(skill);
         if (!opt.isEmpty()) {
            ManasSkillInstance instance = opt.get();
            if (!(instance.getMastery() < 0.0)) {
               if (!instance.isMastered(player)) {
                  double max = instance.getMaxMastery();
                  if (!(max <= 0.0)) {
                     double next = Math.min(max, instance.getMastery() + max * ratio);
                     instance.setMastery(next);
                     instance.markDirty();
                     skills.markDirty();
                  }
               }
            }
         }
      }
   }

   private ManasSkill resolveTargetSkill(ServerPlayer player) {
      ManasSkill skill = this.targetSkill.get();
      ResourceLocation id = resolveSkillId(skill);
      return id != null && id.equals(FoxAblazeUltimateSkills.RAPHAEL.getId()) && CielState.isAwakened(player)
         ? (ManasSkill)FoxAblazeUltimateSkills.CIEL.get()
         : skill;
   }

   private static ResourceLocation resolveSkillId(ManasSkill skill) {
      if (skill == null) {
         return null;
      }

      ResourceLocation id = skill.getRegistryName();
      return id != null ? id : Optional.ofNullable(SkillAPI.getSkillRegistry().getId(skill)).orElse(null);
   }

   private void migrateWisdomFamilyProgress(ServerPlayer player, ResourceLocation lockId) {
      if (lockId.equals(FoxAblazeUltimateSkills.CIEL.getId())) {
         CrystalLockState.migrateSkillState(player, FoxAblazeUltimateSkills.RAPHAEL.getId(), FoxAblazeUltimateSkills.CIEL.getId());
      }
   }

   public record MasteryBoost(ResourceLocation skillId, double ratio) {
   }
}
