package com.github.wal_bos.moreskills.handler.asmoday;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.AsmodayBlackHoleHandler;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public final class AsmodayEndBlackHole implements AsmodayAttack {
   @Override
   public String modeId() {
      return "asmoday.black_hole";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)cfg().endBlackHoleCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            if (!AsmodayBlackHoleHandler.hasActiveBlackHole(entity)) {
               Vec3 center = getSpawnCenter(entity);
               AsmodayBlackHoleHandler.spawn(
                  level,
                  entity,
                  center,
                  ((Double)cfg().endBlackHoleRadius.get()).floatValue(),
                  (Integer)cfg().endBlackHoleDurationTicks.get(),
                  instance.isMastered(entity)
               );
               level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 9.0F, 0.22F);
               level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_ROAR, SoundSource.PLAYERS, 8.0F, 0.28F);
               level.playSound(null, center.x, center.y, center.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 7.5F, 0.4F);
               entity.swing(InteractionHand.MAIN_HAND, true);
               instance.addMasteryPoint(entity);
               instance.setCoolDown((Integer)cfg().endBlackHoleCooldownTicks.get(), mode);
            }
         }
      }
   }

   private static Vec3 getSpawnCenter(LivingEntity entity) {
      LivingEntity target = getLookTarget(entity);
      if (target != null) {
         return target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
      }

      Vec3 eye = entity.getEyePosition();
      Vec3 look = entity.getLookAngle().normalize();
      Vec3 end = eye.add(look.scale((Double)cfg().endBlackHoleFallbackRange.get()));
      BlockHitResult hit = entity.level().clip(new ClipContext(eye, end, Block.COLLIDER, Fluid.NONE, entity));
      return hit.getType() != Type.MISS
         ? hit.getLocation().add(0.0, (Double)cfg().endBlackHoleHitYOffset.get(), 0.0)
         : eye.add(look.scale((Double)cfg().endBlackHoleFallbackMissRange.get()));
   }

   private static LivingEntity getLookTarget(LivingEntity entity) {
      Vec3 eye = entity.getEyePosition();
      Vec3 look = entity.getLookAngle().normalize();
      Vec3 end = eye.add(look.scale((Double)cfg().endBlackHoleTargetRange.get()));
      AABB area = entity.getBoundingBox()
         .expandTowards(look.scale((Double)cfg().endBlackHoleTargetRange.get()))
         .inflate((Double)cfg().endBlackHoleLookTargetInflate.get());
      LivingEntity best = null;
      double bestDistance = (Double)cfg().endBlackHoleTargetRange.get();

      for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, area, target -> isValidTarget(entity, target))) {
         Optional<Vec3> optional = living.getBoundingBox().inflate(1.2).clip(eye, end);
         if (!optional.isEmpty()) {
            double distance = eye.distanceTo(optional.get());
            if (distance < bestDistance) {
               bestDistance = distance;
               best = living;
            }
         }
      }

      return best;
   }

   private static boolean isValidTarget(LivingEntity owner, LivingEntity target) {
      return target == owner || !target.isAlive() || target.isSpectator() ? false : !target.isAlliedTo(owner) && !owner.isAlliedTo(target);
   }

   private static MoreSkillsConfigs.AsmodayConfig cfg() {
      return MoreSkillsConfigs.GRAND.asmoday;
   }
}
