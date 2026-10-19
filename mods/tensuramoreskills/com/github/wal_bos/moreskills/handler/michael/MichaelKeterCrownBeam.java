package com.github.wal_bos.moreskills.handler.michael;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.MichaelKeterVfxPayload;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class MichaelKeterCrownBeam implements MichaelAttack {
   @Override
   public String modeId() {
      return "michael_angel_of_locks.keter";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 0.0;
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide() && instance.getCoolDown(mode) <= 0) {
         MichaelKeterVfxPayload.sendCharge(entity, 1, (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.keterCastTicks.get());
      }
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide()) {
         return true;
      }

      int cast = (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.keterCastTicks.get();
      float range = instance.isMastered(entity)
         ? ((Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.keterRangeMastered.get()).floatValue()
         : ((Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.keterRange.get()).floatValue();
      if (heldTicks <= cast && heldTicks % 2 == 0) {
         MichaelKeterVfxPayload.sendCharge(entity, heldTicks, 14);
      }

      if (heldTicks >= cast) {
         if ((heldTicks - cast) % 20 == 0
            && EnergyHelper.isOutOfEnergy(entity, ((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.keterCostPerSecond.get()).intValue(), 0.0)) {
            return false;
         }

         if ((heldTicks - cast) % 10 == 0) {
            damageBeam(entity, range);
            MichaelKeterVfxPayload.sendBeam(entity, range, 18);
            instance.addMasteryPoint(entity);
         }
      }

      return true;
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (heldTicks >= (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.keterCastTicks.get()) {
         instance.setCoolDown(60, mode);
      }
   }

   private static void damageBeam(LivingEntity owner, float range) {
      if (owner.level() instanceof ServerLevel level) {
         Vec3 var14 = owner.getEyePosition();
         Vec3 look = owner.getLookAngle().normalize();
         Vec3 end = var14.add(look.scale(range));
         AABB box = new AABB(var14, end).inflate(2.2);

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, targetx -> MichaelActions.validEnemy(owner, targetx))) {
            Vec3 to = target.getEyePosition().subtract(var14);
            double along = to.dot(look);
            if (!(along < 0.0) && !(along > range)) {
               double side = to.subtract(look.scale(along)).length();
               if (side <= 2.4) {
                  target.hurt(
                     MichaelActions.damage(owner, TensuraDamageTypes.GRAVITY_PRESS),
                     ((Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.keterDamagePerHalfSecond.get()).floatValue()
                  );
               }
            }
         }
      }
   }
}
