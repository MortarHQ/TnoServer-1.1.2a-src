package com.github.wal_bos.moreskills.ability.skill.intrinsic;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.handler.BloodfiendData;
import com.github.wal_bos.moreskills.handler.BloodfiendHandler;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.race.RaceUtils;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class BloodArtsSkill extends Skill {
   public BloodArtsSkill() {
      super(SkillType.INTRINSIC);
   }

   private MoreskillsRaceConfig.BloodArts cfg() {
      return MoreskillsRaceConfig.get().BloodArts;
   }

   public int getMaxMastery() {
      return (Integer)this.cfg().maxMastery.get();
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/intrinsic/bloodart.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 3 : mode - 1;
      } else {
         return mode >= 3 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "blood_arts.drain_blood";
         case 1 -> "blood_arts.crimson_guard";
         case 2 -> "blood_arts.blood_brand";
         case 3 -> "blood_arts.kindred_rite";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 0.0;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return mode == 0;
   }

   public int getMaxHeldTime(ManasSkillInstance instance, LivingEntity entity) {
      return 72000;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!BloodfiendHandler.isBloodfiend(entity)) {
         message(entity, "tensuramoreskills.message.bloodfiend.no_body", ChatFormatting.RED);
      } else {
         switch (mode) {
            case 1:
               this.crimsonGuard(instance, entity, mode);
               break;
            case 2:
               this.bloodBrand(instance, entity, mode);
               break;
            case 3:
               this.kindredRite(instance, entity, mode);
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode != 0) {
         return false;
      }

      if (!BloodfiendHandler.isBloodfiend(entity)) {
         if (heldTicks == 0) {
            message(entity, "tensuramoreskills.message.bloodfiend.no_body", ChatFormatting.RED);
         }

         return false;
      } else {
         MoreskillsRaceConfig.BloodArts cfg = this.cfg();
         int interval = Math.max(1, (Integer)cfg.drainTickInterval.get());
         if (heldTicks % interval != 0) {
            return true;
         } else {
            return instance.onCoolDown(mode) ? false : this.drainBloodPulse(instance, entity, mode, heldTicks);
         }
      }
   }

   private boolean drainBloodPulse(ManasSkillInstance instance, LivingEntity entity, int mode, int heldTicks) {
      MoreskillsRaceConfig.BloodArts cfg = this.cfg();
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg.drainRange.get(), false, true);
      if (target != null && target != entity && target.isAlive() && !RaceUtils.isBloodless(target)) {
         double bloodRatio = BloodfiendHandler.getBloodRatio(entity);
         double power = BloodfiendHandler.getBloodArtPower(entity);
         float damage = (float)((Double)cfg.drainDamage.get() * (1.0 + bloodRatio * 0.35) * power);
         target.hurt(TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.MAGIC_GENERIC, entity), damage);
         int gain = (int)Math.max(1.0, Math.ceil(((Integer)cfg.drainBloodGain.get()).intValue() * power * (0.65 + bloodRatio * 0.7)));
         if (BloodfiendData.hasBloodBrand(target)) {
            gain *= cfg.bloodBrandDrainMultiplier.get();
         }

         if (target.getHealth() <= target.getMaxHealth() * (Double)cfg.lowHealthDrainBonusThreshold.get()) {
            gain = (int)Math.ceil(gain * (Double)cfg.lowHealthDrainBonusMultiplier.get());
         }

         BloodfiendData.addBloodDrainProgress(entity, gain);
         BloodfiendData.addBlood(entity, gain, BloodfiendHandler.getMaxBlood(entity));
         sync(entity);
         BloodfiendHandler.spawnDrainParticles(entity, target);
         if (entity.tickCount % 14 == 0) {
            entity.level()
               .playSound(
                  null,
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)TensuraSoundEvents.ENERGY_DRAIN.get(),
                  SoundSource.PLAYERS,
                  1.0F,
                  0.45F + entity.getRandom().nextFloat() * 0.2F
               );
            entity.swing(InteractionHand.MAIN_HAND, true);
         }

         if (entity.tickCount % 30 == 0) {
            instance.addMasteryPoint(entity);
         }

         instance.setCoolDown((Integer)cfg.drainPulseCooldown.get(), mode);
         return true;
      } else {
         if (heldTicks == 0) {
            message(entity, "tensuramoreskills.message.bloodfiend.no_blood_source", ChatFormatting.RED);
         }

         return false;
      }
   }

   private void crimsonGuard(ManasSkillInstance instance, LivingEntity entity, int mode) {
      MoreskillsRaceConfig.BloodArts cfg = this.cfg();
      int cost = BloodfiendHandler.applyKindredCostReduction(entity, (Integer)cfg.crimsonGuardCost.get());
      if (!BloodfiendData.consumeBlood(entity, cost)) {
         message(entity, "tensuramoreskills.message.bloodfiend.not_enough_blood", ChatFormatting.DARK_RED);
      } else {
         sync(entity);
         BloodfiendData.setCrimsonGuard(entity, (Integer)cfg.crimsonGuardDuration.get());
         BloodfiendHandler.spawnGuardParticles(entity);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.15F, 0.55F);
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
         instance.setCoolDown((Integer)cfg.crimsonGuardCooldown.get(), mode);
      }
   }

   private void bloodBrand(ManasSkillInstance instance, LivingEntity entity, int mode) {
      MoreskillsRaceConfig.BloodArts cfg = this.cfg();
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg.drainRange.get(), false, true);
      if (target == null || target == entity || !target.isAlive()) {
         message(entity, "tensuramoreskills.message.bloodfiend.no_brand_target", ChatFormatting.RED);
      } else if (RaceUtils.isBloodless(target)) {
         message(entity, "tensuramoreskills.message.bloodfiend.no_blood_source", ChatFormatting.RED);
      } else {
         int cost = BloodfiendHandler.applyKindredCostReduction(entity, (Integer)cfg.bloodBrandCost.get());
         if (!BloodfiendData.consumeBlood(entity, cost)) {
            message(entity, "tensuramoreskills.message.bloodfiend.not_enough_blood", ChatFormatting.DARK_RED);
         } else {
            sync(entity);
            BloodfiendData.setBloodBrand(target, (Integer)cfg.bloodBrandDuration.get());
            BloodfiendHandler.spawnBrandParticles(entity, target);
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.DEBUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.1F, 0.55F
               );
            entity.swing(InteractionHand.MAIN_HAND, true);
            instance.addMasteryPoint(entity);
            instance.setCoolDown((Integer)cfg.bloodBrandCooldown.get(), mode);
         }
      }
   }

   private void kindredRite(ManasSkillInstance instance, LivingEntity entity, int mode) {
      MoreskillsRaceConfig.BloodArts cfg = this.cfg();
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg.kindredRiteRange.get(), false, true);
      if (target != null && target != entity && target.isAlive()) {
         int cost = BloodfiendHandler.applyKindredCostReduction(entity, (Integer)cfg.kindredRiteCost.get());
         if (!BloodfiendData.consumeBlood(entity, cost)) {
            message(entity, "tensuramoreskills.message.bloodfiend.not_enough_blood", ChatFormatting.DARK_RED);
         } else {
            sync(entity);
            boolean turned = BloodfiendHandler.turnKindred(entity, target);
            if (!turned) {
               BloodfiendData.addBlood(entity, cost, BloodfiendHandler.getMaxBlood(entity));
               sync(entity);
               message(entity, "tensuramoreskills.message.bloodfiend.kindred_resisted", ChatFormatting.DARK_RED);
            } else {
               BloodfiendHandler.spawnKindredParticles(entity, target);
               entity.level()
                  .playSound(
                     null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.1F, 0.35F
                  );
               entity.swing(InteractionHand.MAIN_HAND, true);
               instance.addMasteryPoint(entity);
               instance.setCoolDown((Integer)cfg.kindredRiteCooldown.get(), mode);
            }
         }
      } else {
         message(entity, "tensuramoreskills.message.bloodfiend.no_kindred_target", ChatFormatting.RED);
      }
   }

   private static void message(LivingEntity entity, String key, ChatFormatting color) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable(key).withStyle(color), true);
      }
   }

   private static void sync(LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         BloodfiendHandler.syncBlood(player);
      }
   }
}
