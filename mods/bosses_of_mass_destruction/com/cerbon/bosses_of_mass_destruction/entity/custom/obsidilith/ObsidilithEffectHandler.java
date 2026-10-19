package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ObsidilithEffectHandler {
   private final LivingEntity entity;
   private final EventScheduler eventScheduler;
   private final ClientParticleBuilder burstParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.ENCHANT.get())
      .color(Vec3Colors.ORANGE)
      .colorVariation(0.2);
   private final ClientParticleBuilder waveParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.ENCHANT.get())
      .color(Vec3Colors.RED)
      .colorVariation(0.2);
   private final ClientParticleBuilder spikeParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.ENCHANT.get())
      .color(Vec3Colors.COMET_BLUE)
      .colorVariation(0.2);
   private final ClientParticleBuilder anvilParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.ENCHANT.get())
      .color(Vec3Colors.ENDER_PURPLE)
      .colorVariation(0.2);
   private final ClientParticleBuilder teleportFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.DOWNSPARKLE.get())
      .color(Vec3Colors.ENDER_PURPLE)
      .brightness(15728880)
      .age(() -> RandomUtils.range(25, 30))
      .colorVariation(0.2);
   private static final ClientParticleBuilder deathParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.DOWNSPARKLE.get())
      .color(age -> MathUtils.lerpVec(age, Vec3Colors.ENDER_PURPLE, Vec3Colors.WHITE))
      .colorVariation(0.2)
      .brightness(15728880)
      .age(RandomUtils.range(35, 40))
      .scale(f -> (float)(Math.sin(f.floatValue() * Math.PI * 0.5) + 1.0) * 0.1F);

   public ObsidilithEffectHandler(LivingEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   public void handleStatus(byte status) {
      switch (status) {
         case 3:
            this.deathEffect();
         case 4:
         default:
            break;
         case 5:
            this.burstEffect();
            break;
         case 6:
            this.waveEffect();
            break;
         case 7:
            this.spikeEffect();
            break;
         case 8:
            this.anvilEffect();
      }
   }

   private void burstEffect() {
      Vec3 entityPos = MobUtils.eyePos(this.entity);

      for (int i = 0; i <= 50; i++) {
         Vec3 pos = entityPos.add(RandomUtils.randVec().normalize().scale(3.0));
         Vec3 vel = MathUtils.unNormedDirection(pos, entityPos).cross(VecUtils.yAxis).scale(0.1);
         this.burstParticleFactory.build(pos, vel);
      }
   }

   private void waveEffect() {
      Vec3 entityPos = this.entity.position();

      for (int i = 0; i <= 50; i++) {
         Vec3 randomYOffset = VecUtils.yAxis.scale(this.entity.getRandom().nextDouble());
         Vec3 randomYVel = VecUtils.yAxis.scale(this.entity.getRandom().nextDouble());
         Vec3 pos = entityPos.add(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).normalize().scale(3.0)).add(randomYOffset);
         this.waveParticleFactory
            .continuousVelocity(
               simpleParticle -> MathUtils.unNormedDirection(simpleParticle.getPos(), entityPos).cross(VecUtils.yAxis).reverse().add(randomYVel).scale(0.1)
            )
            .build(pos, Vec3.ZERO);
      }
   }

   private void spikeEffect() {
      Vec3 entityPos = this.entity.position();

      for (int i = 0; i <= 50; i++) {
         Vec3 randomYOffset = VecUtils.yAxis.scale(this.entity.getRandom().nextDouble());
         Vec3 randomYVel = VecUtils.yAxis.scale(this.entity.getRandom().nextDouble());
         Vec3 pos = entityPos.add(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).normalize().scale(3.0)).add(randomYOffset);
         this.spikeParticleFactory
            .continuousVelocity(
               simpleParticle -> MathUtils.unNormedDirection(simpleParticle.getPos(), entityPos).cross(VecUtils.yAxis).add(randomYVel).scale(0.1)
            )
            .build(pos, Vec3.ZERO);
      }
   }

   private void anvilEffect() {
      Vec3 entityPos = MobUtils.eyePos(this.entity);

      for (int i = 0; i <= 50; i++) {
         Vec3 pos = entityPos.add(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).normalize().multiply(3.0, 3.0, 3.0));
         this.anvilParticleFactory
            .continuousVelocity(
               simpleParticle -> MathUtils.unNormedDirection(simpleParticle.getPos(), entityPos)
                  .cross(VecUtils.yAxis)
                  .add(VecUtils.yAxis.multiply(0.4, 0.4, 0.4))
                  .multiply(0.1, 0.1, 0.1)
            )
            .build(pos, Vec3.ZERO);
      }

      this.eventScheduler.addEvent(new TimedEvent(() -> {
         Vec3 particlePos = this.entity.position().add(RandomUtils.randVec().scale(3.0));
         Vec3 vel = this.entity.getDeltaMovement().scale(0.7);
         this.teleportFactory.build(particlePos, vel);
      }, 0, 80, () -> !this.entity.isAlive()));
   }

   private void deathEffect() {
      Vec3 entityPos = VecUtils.asVec3(this.entity.blockPosition()).add(0.5, 0.5, 0.5);
      spawnPillarParticles(entityPos, this.eventScheduler);
   }

   public static void spawnPillarParticles(Vec3 entityPos, EventScheduler eventScheduler) {
      for (int i = 0; i <= 15; i++) {
         int i1 = i;
         eventScheduler.addEvent(
            new TimedEvent(
               () -> MathUtils.circleCallback(
                  3.0, 30, VecUtils.yAxis, vec3 -> deathParticleFactory.build(entityPos.add(vec3).add(VecUtils.yAxis.scale(i1)), Vec3.ZERO)
               ),
               i * 5
            )
         );
      }
   }
}
