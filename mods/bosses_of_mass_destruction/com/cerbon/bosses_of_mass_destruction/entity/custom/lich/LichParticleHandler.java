package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ParticleFactories;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LichParticleHandler implements IEntityEventHandler, IEntityTick<Level> {
   private final LichEntity entity;
   private final EventScheduler eventScheduler;
   private final ClientParticleBuilder summonMissileParticleBuilder;
   private final ClientParticleBuilder teleportParticleBuilder;
   private final ClientParticleBuilder summonCometParticleBuilder;
   private final ClientParticleBuilder flameRingFactory;
   private final ClientParticleBuilder minionSummonParticleBuilder;
   private final ClientParticleBuilder thresholdParticleBuilder;
   private final ClientParticleBuilder summonRingFactory;
   private final ClientParticleBuilder summonRingCompleteFactory;
   private final ClientParticleBuilder deathParticleFactory;
   private final ClientParticleBuilder idleParticles;

   public LichParticleHandler(LichEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.summonMissileParticleBuilder = ParticleFactories.soulFlame().age(2).colorVariation(0.5);
      this.teleportParticleBuilder = new ClientParticleBuilder((ParticleOptions)BMDParticles.DISAPPEARING_SWIRL.get())
         .color(Vec3Colors.TELEPORT_PURPLE)
         .age(10, 15)
         .brightness(15728880);
      this.summonCometParticleBuilder = ParticleFactories.cometTrail().colorVariation(0.5);
      this.flameRingFactory = ParticleFactories.soulFlame()
         .color(t -> MathUtils.lerpVec(t, Vec3Colors.WHITE, Vec3Colors.WHITE.multiply(0.5, 0.5, 0.5)))
         .age(0, 7);
      this.minionSummonParticleBuilder = ParticleFactories.soulFlame().color(Vec3Colors.WHITE);
      this.thresholdParticleBuilder = ParticleFactories.soulFlame().age(20).scale(0.5F);
      this.summonRingFactory = ParticleFactories.soulFlame().color(LichUtils.blueColorFade).colorVariation(0.5).age(10);
      this.summonRingCompleteFactory = ParticleFactories.soulFlame().color(Vec3Colors.WHITE).age(20, 30);
      this.deathParticleFactory = ParticleFactories.soulFlame().color(LichUtils.blueColorFade).age(40, 80).colorVariation(0.5).scale(t -> 0.5F - t * 0.3F);
      this.idleParticles = ParticleFactories.soulFlame().color(LichUtils.blueColorFade).age(30, 40).colorVariation(0.5).scale(t -> 0.25F - t * 0.1F);
   }

   @Override
   public void tick(Level level) {
      if (this.entity.getRandom().nextDouble() > 0.9) {
         this.idleParticles.build(this.entity.position().subtract(VecUtils.yAxis).add(RandomUtils.randVec().scale(0.2)), this.entity.getDeltaMovement());
      }
   }

   @Override
   public void handleEntityEvent(byte status) {
      switch (status) {
         case 3:
            this.deathEffect();
         case 4:
         default:
            break;
         case 5:
            this.cometEffect();
            break;
         case 6:
            this.volleyEffect();
            break;
         case 7:
            this.minionEffect();
            break;
         case 8:
            this.minionRageEffect();
            break;
         case 9:
            this.teleportEffect();
            break;
         case 10:
            this.endTeleportEffect();
            break;
         case 11:
            this.volleyRageEffect();
            break;
         case 12:
            this.cometRageEffect();
            break;
         case 13:
            this.hpThresholdEffect();
      }
   }

   private void cometEffect() {
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> this.summonCometParticleBuilder.build(MobUtils.eyePos(this.entity).add(CometAction.getCometLaunchOffset()), Vec3.ZERO),
               15,
               45,
               this::shouldCancelParticles
            )
         );
   }

   private void volleyEffect() {
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         for (Vec3 offset : VolleyAction.getMissileLaunchOffsets(this.entity)) {
            this.summonMissileParticleBuilder.build(MobUtils.eyePos(this.entity).add(offset), Vec3.ZERO);
         }
      }, 16, 30, this::shouldCancelParticles));
   }

   private void minionEffect() {
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> this.minionSummonParticleBuilder
                  .build(
                     MobUtils.eyePos(this.entity)
                        .add(VecUtils.yAxis.scale(1.0))
                        .add(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).normalize().scale(this.entity.getRandom().nextGaussian())),
                     VecUtils.yAxis.scale(RandomUtils.randDouble(0.2))
                  ),
               40,
               30,
               this::shouldCancelParticles
            )
         );
   }

   private void minionRageEffect() {
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         this.animatedParticleMagicCircle(3.0, 30, 12, 0.0F);
         this.animatedParticleMagicCircle(6.0, 60, 24, 120.0F);
         this.animatedParticleMagicCircle(9.0, 90, 36, 240.0F);
      }, 10, 1, this::shouldCancelParticles));
   }

   private void teleportEffect() {
      this.eventScheduler.addEvent(new TimedEvent(this::spawnTeleportParticles, 15, 10, this::shouldCancelParticles));
   }

   private void endTeleportEffect() {
      this.eventScheduler.addEvent(new TimedEvent(this::spawnTeleportParticles, 1, 10, this::shouldCancelParticles));
   }

   private void cometRageEffect() {
      int numComets = CometRageAction.getRageCometOffsets(this.entity).size();

      for (int i = 0; i < numComets; i++) {
         int i1 = i;
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            Vec3 cometOffset = CometRageAction.getRageCometOffsets(this.entity).get(i1);
            this.summonCometParticleBuilder.build(cometOffset.add(MobUtils.eyePos(this.entity)), Vec3.ZERO);
         }, i * 30, 60, this::shouldCancelParticles));
      }

      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> MathUtils.circleCallback(
                  3.0, 72, this.entity.getLookAngle(), vec3 -> this.flameRingFactory.build(vec3.add(MobUtils.eyePos(this.entity)), Vec3.ZERO)
               ),
               0,
               240,
               this::shouldCancelParticles
            )
         );
   }

   private void volleyRageEffect() {
      int numVolleys = VolleyRageAction.getRageMissileVolleys(this.entity).size();

      for (int i = 0; i < numVolleys; i++) {
         int i1 = i;
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            for (Vec3 offset : VolleyRageAction.getRageMissileVolleys(this.entity).get(i1)) {
               this.summonMissileParticleBuilder.build(MobUtils.eyePos(this.entity).add(offset), Vec3.ZERO);
            }
         }, 30 + i1 * 30, 30, this::shouldCancelParticles));
      }
   }

   private void hpThresholdEffect() {
      for (int i = 0; i < 20; i++) {
         this.thresholdParticleBuilder.build(MobUtils.eyePos(this.entity), RandomUtils.randVec());
      }
   }

   private void deathEffect() {
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         for (int i = 0; i <= 4; i++) {
            this.deathParticleFactory.build(MobUtils.eyePos(this.entity), RandomUtils.randVec());
         }
      }, 0, 10, () -> false));
   }

   private void spawnTeleportParticles() {
      this.teleportParticleBuilder.build(MobUtils.eyePos(this.entity).add(RandomUtils.randVec().scale(3.0)), Vec3.ZERO);
   }

   private void animatedParticleMagicCircle(double radius, int points, int time, float rotationDegrees) {
      Vec3 spellPos = this.entity.position();
      List<Vec3> circlePoints = MathUtils.circlePoints(radius, points, this.entity.getLookAngle()).stream().toList();
      float timeScale = (float)time / points;
      IntStream.range(0, circlePoints.size()).forEach(index -> {
         Vec3 off = circlePoints.get(index);
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            off.yRot(rotationDegrees);
            this.summonRingFactory.build(off.add(spellPos), Vec3.ZERO);
         }, (int)(index * timeScale)));
      });
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> circlePoints.stream().peek(vec3 -> this.summonRingCompleteFactory.build(vec3.add(spellPos), Vec3.ZERO)), (int)(points * timeScale)
            )
         );
   }

   private boolean shouldCancelParticles() {
      return !this.entity.isAlive();
   }
}
