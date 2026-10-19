package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;

public class ClientDeathEffectHandler implements IEntityEventHandler {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final ClientParticleBuilder deathParticle = new ClientParticleBuilder((ParticleOptions)BMDParticles.FLUFF.get())
      .color(Vec3Colors.DARK_GREY)
      .colorVariation(0.1)
      .age(20, 30)
      .scale(0.3F);

   public ClientDeathEffectHandler(VoidBlossomEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   @Override
   public void handleEntityEvent(byte status) {
      if (status == 3) {
         int delay = 3;
         Vec3 fallDirection = VecUtils.planeProject(this.entity.getForward(), VecUtils.yAxis).yRot(180.0F);
         Vec3 originPos = this.entity.position().add(VecUtils.yAxis.scale(2.0));
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            Vec3 pos = originPos.add(RandomUtils.randVec().scale(5.0)).add(fallDirection.scale(RandomUtils.randDouble(6.0) + 6.0));
            Vec3 vel = RandomUtils.randVec().add(VecUtils.yAxis).scale(0.05);
            this.deathParticle.build(pos, vel);
         }, delay, 70 - delay, () -> false));
      }
   }
}
