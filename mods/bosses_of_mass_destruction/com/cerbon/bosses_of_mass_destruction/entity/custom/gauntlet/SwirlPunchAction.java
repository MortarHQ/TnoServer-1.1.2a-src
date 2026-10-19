package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.config.mob.GauntletConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.Vec3;

public class SwirlPunchAction implements IActionWithCooldown {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private final GauntletConfig mobConfig;
   private final Supplier<Boolean> cancelAction;
   private final ServerLevel serverLevel;
   private double previousSpeed = 0.0;

   public SwirlPunchAction(
      GauntletEntity entity, EventScheduler eventScheduler, GauntletConfig mobConfig, Supplier<Boolean> cancelAction, ServerLevel serverLevel
   ) {
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

      Vec3 targetDirection = MathUtils.unNormedDirection(MobUtils.eyePos(this.entity), target.getBoundingBox().getCenter());
      Vec3 targetPos = MobUtils.eyePos(this.entity).add(targetDirection.scale(1.2));
      int accelerateStartTime = 30;
      int unclenchTime = 60;
      int closeFistAnimationTime = 7;
      this.entity.push(0.0, 0.7, 0.0);
      SoundUtils.playSound(
         this.serverLevel, this.entity.position(), (SoundEvent)BMDSounds.GAUNTLET_SPIN_PUNCH.get(), SoundSource.HOSTILE, 2.0F, 1.0F, 64.0, null
      );
      this.entity.getEntityData().set(GauntletEntity.isEnergized, true);
      this.eventScheduler.addEvent(new TimedEvent(this.entity.hitboxHelper::setClosedFistHitbox, closeFistAnimationTime, 1, this.cancelAction));
      AtomicReference<Double> velocityStack = new AtomicReference<>(0.6);
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         PunchAction.accelerateTowardsTarget(this.entity, targetPos, velocityStack.get());
         velocityStack.set(0.4);
      }, accelerateStartTime, 15, () -> this.entity.position().distanceToSqr(targetPos) < 9.0 || this.cancelAction.get()));
      this.eventScheduler.addEvent(new TimedEvent(this::whilePunchActive, accelerateStartTime, unclenchTime - accelerateStartTime, this.cancelAction));
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         this.entity.hitboxHelper.setOpenHandHitbox();
         this.entity.getEntityData().set(GauntletEntity.isEnergized, false);
      }, unclenchTime));
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
         if ((Boolean)this.entity.getEntityData().get(GauntletEntity.isEnergized)) {
            this.entity.level().explode(this.entity, pos.x, pos.y, pos.z, (float)this.mobConfig.energizedPunchExplosionSize, true, ExplosionInteraction.MOB);
            this.entity.getEntityData().set(GauntletEntity.isEnergized, false);
         } else {
            this.entity
               .level()
               .explode(this.entity, pos.x, pos.y, pos.z, (float)(this.previousSpeed * this.mobConfig.normalPunchExplosionMultiplier), ExplosionInteraction.MOB);
         }
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
}
