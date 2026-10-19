package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.config.mob.GauntletConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PunchAction implements IActionWithCooldown {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private final GauntletConfig mobConfig;
   private final Supplier<Boolean> cancelAction;
   private final ServerLevel serverLevel;
   private double previousSpeed = 0.0;

   public PunchAction(GauntletEntity entity, EventScheduler eventScheduler, GauntletConfig mobConfig, Supplier<Boolean> cancelAction, ServerLevel serverLevel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.mobConfig = mobConfig;
      this.cancelAction = cancelAction;
      this.serverLevel = serverLevel;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.getTarget();
      if (target == null) {
         return 40;
      }

      Vec3 targetPos = MobUtils.eyePos(this.entity).add(MathUtils.unNormedDirection(MobUtils.eyePos(this.entity), target.position()).scale(1.2));
      int accelerateStartTime = 16;
      int unclenchTime = 56;
      BlockPos breakBoundCenter = BlockPos.containing(this.entity.position().add(this.entity.getLookAngle()));
      AABB breakBounds = new AABB(VecUtils.asVec3(breakBoundCenter.subtract(new BlockPos(1, 1, 1))), VecUtils.asVec3(breakBoundCenter.offset(1, 2, 1)));
      VanillaCopiesServer.destroyBlocks(this.entity, breakBounds);
      this.entity.push(0.0, 0.7, 0.0);
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> SoundUtils.playSound(
                  this.serverLevel,
                  this.entity.position(),
                  (SoundEvent)BMDSounds.GAUNTLET_CLINK.get(),
                  SoundSource.HOSTILE,
                  2.0F,
                  SoundUtils.randomPitch(this.entity.getRandom()) * 0.8F,
                  32.0,
                  null
               ),
               12,
               1,
               this.cancelAction
            )
         );
      AtomicReference<Double> velocityStack = new AtomicReference<>(0.6);
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         accelerateTowardsTarget(this.entity, targetPos, velocityStack.get());
         velocityStack.set(0.32);
      }, accelerateStartTime, 15, () -> this.entity.position().distanceToSqr(targetPos) < 9.0 || this.cancelAction.get()));
      this.eventScheduler.addEvent(new TimedEvent(this::whilePunchActive, accelerateStartTime, unclenchTime - accelerateStartTime, this.cancelAction));
      int closeFistAnimationTime = 7;
      this.eventScheduler.addEvent(new TimedEvent(this.entity.hitboxHelper::setClosedFistHitbox, closeFistAnimationTime, 1, this.cancelAction));
      this.eventScheduler.addEvent(new TimedEvent(() -> this.entity.level().broadcastEntityEvent(this.entity, (byte)5), unclenchTime, 1, this.cancelAction));
      this.eventScheduler.addEvent(new TimedEvent(this.entity.hitboxHelper::setOpenHandHitbox, unclenchTime + 8));
      return 80;
   }

   private void whilePunchActive() {
      this.testBlockPhysicalImpact();
      this.testEntityImpact();
      this.previousSpeed = this.entity.getDeltaMovement().length();
   }

   private void testBlockPhysicalImpact() {
      if ((this.entity.horizontalCollision || this.entity.verticalCollision) && this.previousSpeed > 0.55F) {
         Vec3 pos = this.entity.position();
         this.entity
            .level()
            .explode(this.entity, pos.x, pos.y, pos.z, (float)(this.previousSpeed * this.mobConfig.normalPunchExplosionMultiplier), ExplosionInteraction.MOB);
      }
   }

   private void testEntityImpact() {
      for (LivingEntity target : this.entity
         .level()
         .getEntitiesOfClass(LivingEntity.class, this.entity.getBoundingBox(), livingEntity -> livingEntity != this.entity)) {
         this.entity.doHurtTarget(target);
         target.addDeltaMovement(this.entity.getDeltaMovement().scale(0.5));
      }
   }

   public static void accelerateTowardsTarget(Entity entity, Vec3 target, double velocity) {
      Vec3 dir = MathUtils.unNormedDirection(MobUtils.eyePos(entity), target).normalize();
      Vec3 velocityCorrection = VecUtils.planeProject(entity.getDeltaMovement(), dir);
      entity.addDeltaMovement(dir.subtract(velocityCorrection).scale(velocity));
   }
}
