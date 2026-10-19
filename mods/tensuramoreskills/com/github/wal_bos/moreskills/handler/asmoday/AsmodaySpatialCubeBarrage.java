package com.github.wal_bos.moreskills.handler.asmoday;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public final class AsmodaySpatialCubeBarrage implements AsmodayAttack {
   @Override
   public String modeId() {
      return "asmoday.spatial_cube_barrage";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return instance.isMastered(entity) ? (Double)cfg().spatialCubeBarrageCostMastered.get() : (Double)cfg().spatialCubeBarrageCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         double range = instance.isMastered(entity) ? (Double)cfg().spatialCubeBarrageRangeMastered.get() : (Double)cfg().spatialCubeBarrageRange.get();
         Entity selected = ObjectSelectionHelper.getTargetingEntity(entity, range, false, true);
         if (selected instanceof LivingEntity target && AsmodaySpatialCubeShot.validEnemy(entity, target)) {
            boolean shifted = entity.isShiftKeyDown();
            AsmodaySpatialCubeShot.fire(entity, target, instance.isMastered(entity), shifted);
            instance.addMasteryPoint(entity);
            instance.setCoolDown(
               (Integer)cfg().spatialCubeBarrageCooldownTicks.get() + (shifted ? (Integer)cfg().spatialCubeBarrageShiftCooldownBonusTicks.get() : 0), mode
            );
         } else {
            entity.sendSystemMessage(Component.literal("No valid entity selected.").withStyle(ChatFormatting.RED));
         }
      }
   }

   private static MoreSkillsConfigs.AsmodayConfig cfg() {
      return MoreSkillsConfigs.GRAND.asmoday;
   }
}
