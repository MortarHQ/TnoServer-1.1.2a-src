package com.github.wal_bos.moreskills.handler.michael;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.MichaelHalberdShotVfxPayload;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class MichaelLataibHalberdShot implements MichaelAttack {
   private static final double MIN_PROJECTILE_RANGE = 100.0;
   private static final double PROJECTILE_HIT_RADIUS = 1.65;

   @Override
   public String modeId() {
      return "michael_angel_of_locks.lataib_halberd_shot";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return ((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibHalberdShotCost.get()).intValue();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (!MichaelActions.isHoldingAwakened(entity)) {
            MichaelActions.fail(entity, "This lock opens only through Michael's true form.");
         } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            boolean mastered = instance.isMastered(entity);
            double configuredRange = mastered
               ? (Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibHalberdShotRangeMastered.get()
               : (Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibHalberdShotRange.get();
            double projectileRange = Math.max(100.0, configuredRange);
            Vec3 eye = entity.getEyePosition();
            Vec3 look = entity.getLookAngle().normalize();
            Vec3 end = eye.add(look.scale(projectileRange));
            AABB box = new AABB(eye, end).inflate(1.65);
            float damage = mastered
               ? ((Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibHalberdShotDamageMastered.get()).floatValue()
               : ((Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibHalberdShotDamage.get()).floatValue();

            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, box, t -> MichaelActions.validEnemy(entity, t))) {
               Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
               Vec3 to = targetCenter.subtract(eye);
               double along = to.dot(look);
               if (!(along < 0.0) && !(along > projectileRange)) {
                  Vec3 closest = eye.add(look.scale(along));
                  double allowed = 1.65 + Math.max(target.getBbWidth(), target.getBbHeight() * 0.35) * 0.5;
                  if (targetCenter.distanceToSqr(closest) <= allowed * allowed) {
                     target.hurt(MichaelActions.damage(entity, TensuraDamageTypes.SPACE_ELEMENTAL), damage);
                     target.addEffect(
                        new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SEVERANCE_BLADE), 80, 1, true, false, true), entity
                     );
                  }
               }
            }

            MichaelHalberdShotVfxPayload.send(entity, (float)projectileRange, 120);
            entity.level()
               .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_SPACE.get(), SoundSource.PLAYERS, 1.3F, 1.25F);
            entity.swing(InteractionHand.MAIN_HAND, true);
            instance.addMasteryPoint(entity);
            instance.setCoolDown(
               mastered
                  ? (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibHalberdShotCooldownMasteredTicks.get()
                  : (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibHalberdShotCooldownTicks.get(),
               mode
            );
         }
      }
   }
}
