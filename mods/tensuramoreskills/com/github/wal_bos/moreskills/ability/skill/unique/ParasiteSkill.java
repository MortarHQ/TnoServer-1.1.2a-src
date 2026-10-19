package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.ParasiteData;
import com.github.wal_bos.moreskills.handler.ParasiteHandler;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class ParasiteSkill extends Skill {
   public ParasiteSkill() {
      super(SkillType.UNIQUE);
      ParasiteHandler.init();
   }

   private static MoreSkillsConfigs.ParasiteConfig cfg() {
      return MoreSkillsConfigs.GRAND.parasite;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/parasite.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)cfg().acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)cfg().acquirementMastery.get();
   }

   public int getMaxMastery() {
      return (Integer)cfg().maxMastery.get();
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 2 : mode - 1;
      } else {
         return mode >= 2 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "parasite.merger_bond";
         case 1 -> "parasite.hostile_bond";
         case 2 -> "parasite.leave";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0, 1 -> cfg().bondCost.get();
         case 2 -> cfg().leaveCost.get();
         default -> 0.0;
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide() && !ParasiteData.isParasite(entity)) {
         ParasiteHandler.setParasiteRace(entity);
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      if (!entity.level().isClientSide() && ParasiteData.isParasite(entity)) {
         ParasiteHandler.leave(entity, false, false);
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (mode == 2) {
            if (!ParasiteData.isParasite(entity)) {
               this.fail(entity, "tensuramoreskills.message.parasite.not_bonded");
            } else if (!ParasiteHandler.leave(entity, false, false)) {
               this.fail(entity, "tensuramoreskills.message.parasite.not_bonded");
            } else {
               ParasiteHandler.loseBodySkills(entity);
               instance.addMasteryPoint(entity);
            }
         } else if (ParasiteData.hasBond(entity) || ParasiteData.tempControlActive(entity)) {
            this.fail(entity, "tensuramoreskills.message.parasite.bond_failed");
         } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg().bondRange.get(), false);
            if (target != null && target != entity && target.isAlive()) {
               if (ParasiteData.hasBodySkills(entity) || ParasiteData.hasBodyStats(entity)) {
                  ParasiteHandler.loseBodySkills(entity);
                  ParasiteHandler.setParasiteRace(entity);
               }

               int plan = mode == 0 ? 1 : 2;
               if (!ParasiteHandler.bond(entity, target, plan)) {
                  this.fail(entity, "tensuramoreskills.message.parasite.bond_failed");
               } else {
                  entity.level()
                     .playSound(
                        null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.8F
                     );
                  instance.addMasteryPoint(entity);
               }
            } else {
               this.fail(entity, "tensuramoreskills.message.parasite.no_target");
            }
         }
      }
   }

   private void fail(LivingEntity entity, String key) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable(key).withStyle(ChatFormatting.RED), true);
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }
}
