package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.List;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class GauntletBlindnessIndicatorParticles implements IEntityEventHandler {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private final ClientParticleBuilder particleBuilder = new ClientParticleBuilder((ParticleOptions)BMDParticles.EYE_OPEN.get())
      .brightness(15728880)
      .color(f -> MathUtils.lerpVec(f, Vec3Colors.WHITE, Vec3Colors.LASER_RED));
   private final ClientParticleBuilder gauntletParticleBuilder = new ClientParticleBuilder((ParticleOptions)BMDParticles.SOUL_FLAME.get())
      .color(Vec3Colors.DARK_RED)
      .brightness(15728880)
      .scale(0.25F)
      .age(20);

   public GauntletBlindnessIndicatorParticles(GauntletEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   public void handlePlayerEffects(List<Player> players) {
      for (Player player : players) {
         this.spawnRotatingParticles(player, this.particleBuilder);
      }
   }

   private void spawnRotatingParticles(Entity player, ClientParticleBuilder particleBuilder) {
      for (int i = 0; i <= 20; i++) {
         int i1 = i;
         int startingRotation = new Random().nextInt(360);
         particleBuilder.continuousPosition(simpleParticle -> this.calculatePositions(player, i1, simpleParticle.getAge(), startingRotation))
            .build(this.calculatePositions(player, i1, 0, startingRotation), Vec3.ZERO);
      }
   }

   private Vec3 calculatePositions(Entity player, int i, int age, int startingRotation) {
      return player.position().add(VecUtils.yAxis.scale(i * 0.1)).add(VecUtils.xAxis.yRot((float)Math.toRadians(age * 2 + startingRotation)));
   }

   @Override
   public void handleEntityEvent(byte status) {
      if (status == 11) {
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            for (int i = 0; i <= 3; i++) {
               Vec3 particlePos = MobUtils.eyePos(this.entity).add(RandomUtils.randVec().normalize().scale(2.0));
               Vec3 particleVel = RandomUtils.randVec().scale(0.05).add(VecUtils.yAxis.scale(0.05));
               this.gauntletParticleBuilder.build(particlePos, particleVel);
            }
         }, 0, 10, () -> false));
      }
   }
}
