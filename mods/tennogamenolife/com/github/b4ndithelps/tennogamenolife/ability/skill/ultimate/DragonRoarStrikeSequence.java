package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife")
public final class DragonRoarStrikeSequence {
   public static final int BEAM_DESCENT_DURATION_TICKS = 20;
   public static final int LASER_RAIN_DURATION_TICKS = 100;
   public static final int BEAM_STRIKE_DURATION_TICKS = 120;
   private static final int TNT_BARRAGE_INTERVAL_TICKS = 10;
   private static final List<DragonRoarStrikeSequence.PendingStrike> PENDING_STRIKES = new ArrayList<>();
   private static final List<DragonRoarStrikeSequence.PendingBarrage> PENDING_BARRAGES = new ArrayList<>();

   private DragonRoarStrikeSequence() {
   }

   public static void start(ServerLevel level, int delayTicks, Runnable detonation) {
      PENDING_STRIKES.add(new DragonRoarStrikeSequence.PendingStrike(level, detonation, level.getGameTime() + Math.max(1, delayTicks)));
   }

   public static void startImpactSequence(ServerLevel level, Vec3 center, Runnable largeExplosion) {
      startImpactSequence(level, center, 0, largeExplosion);
   }

   public static void startBeamImpactSequence(ServerLevel level, Vec3 center, Runnable largeExplosion) {
      startImpactSequence(level, center, 20, largeExplosion);
   }

   private static void startImpactSequence(ServerLevel level, Vec3 center, int impactDelayTicks, Runnable largeExplosion) {
      long startTime = level.getGameTime();
      long impactStartTime = startTime + Math.max(0, impactDelayTicks);
      int impactIndex = 0;
      long nextPulseAt = impactStartTime;
      if (impactDelayTicks <= 0) {
         triggerTntImpact(level, center, impactIndex++);
         nextPulseAt = startTime + 10L;
      }

      PENDING_BARRAGES.add(new DragonRoarStrikeSequence.PendingBarrage(level, center, nextPulseAt, impactStartTime + 100L, impactIndex, largeExplosion));
   }

   private static void triggerTntImpact(ServerLevel level, Vec3 center, int impactIndex) {
      double angle = impactIndex * 2.399963229728653;
      double radius = impactIndex == 0 ? 0.0 : Math.min(5.0, 1.25 + impactIndex * 0.45);
      Vec3 impactPos = center.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
      level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, impactPos.x, impactPos.y, impactPos.z, 1, 0.0, 0.0, 0.0, 0.0);
      level.playSound(
         null, impactPos.x, impactPos.y, impactPos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 4.0F, 0.9F + level.random.nextFloat() * 0.2F
      );
   }

   @SubscribeEvent
   public static void onServerTick(Post event) {
      if (!PENDING_STRIKES.isEmpty() || !PENDING_BARRAGES.isEmpty()) {
         List<Runnable> readyDetonations = new ArrayList<>();
         Iterator<DragonRoarStrikeSequence.PendingStrike> iterator = PENDING_STRIKES.iterator();

         while (iterator.hasNext()) {
            DragonRoarStrikeSequence.PendingStrike strike = iterator.next();
            if (strike.level().getServer() == event.getServer() && strike.level().getGameTime() >= strike.executeAt()) {
               iterator.remove();
               readyDetonations.add(strike.detonation());
            }
         }

         Iterator<DragonRoarStrikeSequence.PendingBarrage> barrageIterator = PENDING_BARRAGES.iterator();

         while (barrageIterator.hasNext()) {
            DragonRoarStrikeSequence.PendingBarrage barrage = barrageIterator.next();
            if (barrage.level.getServer() == event.getServer()) {
               long gameTime = barrage.level.getGameTime();
               if (gameTime >= barrage.endAt) {
                  barrageIterator.remove();
                  readyDetonations.add(barrage.largeExplosion);
               } else if (gameTime >= barrage.nextPulseAt) {
                  triggerTntImpact(barrage.level, barrage.center, barrage.impactIndex);
                  barrage.impactIndex++;
                  barrage.nextPulseAt = gameTime + 10L;
               }
            }
         }

         for (Runnable detonation : readyDetonations) {
            detonation.run();
         }
      }
   }

   @SubscribeEvent
   public static void onServerStopping(ServerStoppingEvent event) {
      PENDING_STRIKES.clear();
      PENDING_BARRAGES.clear();
   }

   private static final class PendingBarrage {
      private final ServerLevel level;
      private final Vec3 center;
      private final long endAt;
      private final Runnable largeExplosion;
      private long nextPulseAt;
      private int impactIndex;

      private PendingBarrage(ServerLevel level, Vec3 center, long nextPulseAt, long endAt, int impactIndex, Runnable largeExplosion) {
         this.level = level;
         this.center = center;
         this.nextPulseAt = nextPulseAt;
         this.endAt = endAt;
         this.impactIndex = impactIndex;
         this.largeExplosion = largeExplosion;
      }
   }

   private record PendingStrike(ServerLevel level, Runnable detonation, long executeAt) {
   }
}
