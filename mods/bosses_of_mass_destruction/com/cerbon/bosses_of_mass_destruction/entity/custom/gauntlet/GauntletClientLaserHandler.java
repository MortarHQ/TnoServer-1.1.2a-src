package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.entity.util.IDataAccessorHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.cerbons_api.api.general.data.HistoricalData;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class GauntletClientLaserHandler implements IEntityTick<Level>, IDataAccessorHandler, IEntityEventHandler {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private LivingEntity cachedBeamTarget;
   private final HistoricalData<Pair<Vec3, Vec3>> laserRenderPositions = new HistoricalData(Pair.of(Vec3.ZERO, Vec3.ZERO), 8);
   public final ClientParticleBuilder laserChargeParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.SPARKLES.get())
      .brightness(15728880)
      .color(Vec3Colors.LASER_RED)
      .colorVariation(0.2);

   public GauntletClientLaserHandler(GauntletEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   @Override
   public void tick(Level level) {
      LivingEntity beamTarget = this.getBeamTarget();
      if (beamTarget != null) {
         Vec3 centerBoxOffset = beamTarget.getBoundingBox().getCenter().subtract(beamTarget.position());
         this.laserRenderPositions.add(Pair.of(beamTarget.position().add(centerBoxOffset), MobUtils.lastRenderPos(beamTarget).add(centerBoxOffset)));
      } else {
         this.laserRenderPositions.clear();
      }
   }

   public boolean shouldRenderLaser() {
      return this.laserRenderPositions.size() > 1;
   }

   public Pair<Vec3, Vec3> getLaserRenderPos() {
      Pair<Vec3, Vec3> laserPos = (Pair<Vec3, Vec3>)this.laserRenderPositions.getAll().get(0);
      Vec3 newPos = LaserAction.extendLaser(this.entity, (Vec3)laserPos.getFirst());
      Vec3 prevPos = LaserAction.extendLaser(this.entity, (Vec3)laserPos.getSecond());
      BlockHitResult newResult = this.entity.level().clip(new ClipContext(MobUtils.eyePos(this.entity), newPos, Block.COLLIDER, Fluid.NONE, this.entity));
      BlockHitResult prevResult = this.entity.level().clip(new ClipContext(MobUtils.eyePos(this.entity), prevPos, Block.COLLIDER, Fluid.NONE, this.entity));
      Vec3 colNewPos = newResult.getType() == Type.BLOCK ? newResult.getLocation() : newPos;
      Vec3 colPrevPos = prevResult.getType() == Type.BLOCK ? prevResult.getLocation() : prevPos;
      return Pair.of(colNewPos, colPrevPos);
   }

   private LivingEntity getBeamTarget() {
      if (!this.hasBeamTarget()) {
         return null;
      }

      if (this.entity.level().isClientSide()) {
         if (this.cachedBeamTarget != null) {
            return this.cachedBeamTarget;
         } else if (this.entity.level().getEntity((Integer)this.entity.getEntityData().get(GauntletEntity.laserTarget)) instanceof LivingEntity livingEntity) {
            this.cachedBeamTarget = livingEntity;
            return this.cachedBeamTarget;
         } else {
            return null;
         }
      } else {
         return this.entity.getTarget();
      }
   }

   private boolean hasBeamTarget() {
      return (Integer)this.entity.getEntityData().get(GauntletEntity.laserTarget) != 0;
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
      if (GauntletEntity.laserTarget == data) {
         this.cachedBeamTarget = null;
      }
   }

   public void initDataTracker() {
      this.entity.getEntityData().set(GauntletEntity.laserTarget, 0);
   }

   @Override
   public void handleEntityEvent(byte status) {
      if (status == 8) {
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            Vec3 lookVec = this.entity.getLookAngle();

            for (int i = 0; i <= 1; i++) {
               Vec3 circularOffset = VecUtils.rotateVector(lookVec.cross(VecUtils.yAxis), lookVec, RandomUtils.range(0, 359));
               Vec3 velocity = circularOffset.normalize().reverse().scale(0.07).add(this.entity.getDeltaMovement().scale(1.2));
               Vec3 position = MobUtils.eyePos(this.entity).add(circularOffset).add(lookVec.scale(0.5));
               this.laserChargeParticles.build(position, velocity);
            }
         }, 0, 85, () -> false));
      }
   }
}
