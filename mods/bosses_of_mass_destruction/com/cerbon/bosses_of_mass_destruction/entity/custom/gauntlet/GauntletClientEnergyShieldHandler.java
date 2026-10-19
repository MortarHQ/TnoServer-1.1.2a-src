package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.entity.util.IDataAccessorHandler;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.cerbons_api.api.general.event.Event;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.phys.Vec3;

public class GauntletClientEnergyShieldHandler implements IDataAccessorHandler {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private float energizedRenderAlpha = 0.0F;
   private final ClientParticleBuilder energizedParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.SPARKLES.get())
      .brightness(15728880)
      .color(Vec3Colors.LASER_RED)
      .colorVariation(0.2)
      .scale(0.25F);

   public GauntletClientEnergyShieldHandler(GauntletEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   public float getRenderAlpha() {
      return this.energizedRenderAlpha;
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
      if (GauntletEntity.isEnergized == data && (Boolean)this.entity.getEntityData().get(GauntletEntity.isEnergized) && this.entity.level().isClientSide()) {
         this.eventScheduler.addEvent(new TimedEvent(() -> this.energizedRenderAlpha += 0.1F, 0, 10, () -> false));
         this.eventScheduler
            .addEvent(
               new Event(
                  () -> true, this::spawnParticles, () -> !this.entity.isAlive() || !(Boolean)this.entity.getEntityData().get(GauntletEntity.isEnergized)
               )
            );
      } else {
         this.energizedRenderAlpha = 0.0F;
      }
   }

   private void spawnParticles() {
      Vec3 look = this.entity.getLookAngle();
      Vec3 cross = look.cross(VecUtils.yAxis);
      Vec3 rotatedOffset = VecUtils.rotateVector(cross, look, RandomUtils.range(0, 359));
      Vec3 particlePos = MobUtils.eyePos(this.entity).add(rotatedOffset);
      Vec3 particleVel = VecUtils.rotateVector(rotatedOffset, look, 90.0).scale(0.1);
      this.energizedParticles.build(particlePos, particleVel);
   }

   public void initDataTracker() {
      this.entity.getEntityData().set(GauntletEntity.isEnergized, false);
   }
}
