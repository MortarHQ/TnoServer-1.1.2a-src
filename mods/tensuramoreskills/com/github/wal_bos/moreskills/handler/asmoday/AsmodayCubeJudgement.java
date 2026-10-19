package com.github.wal_bos.moreskills.handler.asmoday;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public final class AsmodayCubeJudgement implements AsmodayAttack {
   @Override
   public String modeId() {
      return "asmoday.cube_judgement";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return entity.isShiftKeyDown() ? (Double)cfg().cubeJudgementShiftCost.get() : (Double)cfg().cubeJudgementNormalCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      boolean shifted = entity.isShiftKeyDown();
      boolean mastered = instance.isMastered(entity);
      if (instance.getCoolDown(mode) > 0) {
         entity.sendSystemMessage(Component.literal("Cube Judgement is still on cooldown.").withStyle(ChatFormatting.RED));
      } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         if (shifted) {
            AsmodayFallingCubeJudgement.rain(entity, mastered);
            int reduction = mastered ? (Integer)cfg().cubeJudgementShiftMasteredCooldownReductionTicks.get() : 0;
            instance.setCoolDown(Math.max(0, (Integer)cfg().cubeJudgementShiftCooldownTicks.get() - reduction), mode);
            instance.addMasteryPoint(entity);
         } else {
            double range = mastered ? (Double)cfg().cubeJudgementTargetRangeMastered.get() : (Double)cfg().cubeJudgementTargetRange.get();
            Entity selected = ObjectSelectionHelper.getTargetingEntity(entity, range, false, true);
            if (selected instanceof LivingEntity target && AsmodayFallingCubeJudgement.validEnemy(entity, target)) {
               AsmodayFallingCubeJudgement.single(entity, target, mastered);
               int reduction = mastered ? (Integer)cfg().cubeJudgementNormalMasteredCooldownReductionTicks.get() : 0;
               instance.setCoolDown(Math.max(0, (Integer)cfg().cubeJudgementNormalCooldownTicks.get() - reduction), mode);
               instance.addMasteryPoint(entity);
            } else {
               entity.sendSystemMessage(Component.literal("No valid space exists to collapse.").withStyle(ChatFormatting.RED));
            }
         }
      }
   }

   private static MoreSkillsConfigs.AsmodayConfig cfg() {
      return MoreSkillsConfigs.GRAND.asmoday;
   }
}
