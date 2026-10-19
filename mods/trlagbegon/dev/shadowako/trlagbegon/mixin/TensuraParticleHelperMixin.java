package dev.shadowako.trlagbegon.mixin;

import dev.shadowako.trlagbegon.util.TensuraParticleInRangeUtil;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import java.util.function.Predicate;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TensuraParticleHelper.class, remap = false)
public class TensuraParticleHelperMixin {
   @Inject(
      method = "spawnServerParticles(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDDZ)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void trlag$rangeSpawn1(
      Level level,
      ParticleOptions particle,
      double x,
      double y,
      double z,
      int count,
      double deltaX,
      double deltaY,
      double deltaZ,
      double speed,
      boolean force,
      CallbackInfo ci
   ) {
      if (level instanceof ServerLevel sl) {
         TensuraParticleInRangeUtil.sendToPlayersInRange(sl, particle, force, x, y, z, count, deltaX, deltaY, deltaZ, speed, null);
         ci.cancel();
      }
   }

   @Inject(
      method = "spawnServerParticles(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDDZLjava/util/function/Predicate;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void trlag$rangeSpawn2(
      Level level,
      ParticleOptions particle,
      double x,
      double y,
      double z,
      int count,
      double deltaX,
      double deltaY,
      double deltaZ,
      double speed,
      boolean force,
      Predicate<ServerPlayer> predicate,
      CallbackInfo ci
   ) {
      if (level instanceof ServerLevel sl) {
         TensuraParticleInRangeUtil.sendToPlayersInRange(sl, particle, force, x, y, z, count, deltaX, deltaY, deltaZ, speed, predicate);
         ci.cancel();
      }
   }

   @Inject(
      method = "addServerParticlesAroundPos(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/core/particles/ParticleOptions;D)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void trlag$aroundPos(RandomSource random, Level level, Vec3 pos, ParticleOptions particle, double randomScale, CallbackInfo ci) {
      if (level instanceof ServerLevel sl) {
         for (int i = 0; i < 5; i++) {
            double d0 = random.nextGaussian() * 0.02;
            double d1 = random.nextGaussian() * 0.02;
            double d2 = random.nextGaussian() * 0.02;
            double x = pos.x + (2.0 * random.nextDouble() - 1.0) * randomScale;
            double y = pos.y + (2.0 * random.nextDouble() - 1.0) * randomScale;
            double z = pos.z + (2.0 * random.nextDouble() - 1.0) * randomScale;
            TensuraParticleInRangeUtil.sendToPlayersInRange(sl, particle, false, x, y, z, 0, d0, d1, d2, 1.0, null);
         }

         ci.cancel();
      }
   }

   @Inject(
      method = "addServerParticlesAroundSelf(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/particles/ParticleOptions;DI)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void trlag$aroundSelf(Entity entity, ParticleOptions particle, double randomScale, int amount, CallbackInfo ci) {
      if (entity.level() instanceof ServerLevel sl) {
         RandomSource random = entity.getRandom();

         for (int i = 0; i < amount; i++) {
            double d0 = random.nextGaussian() * 0.02;
            double d1 = random.nextGaussian() * 0.02;
            double d2 = random.nextGaussian() * 0.02;
            TensuraParticleInRangeUtil.sendToPlayersInRange(
               sl, particle, false, entity.getRandomX(randomScale), entity.getRandomY(), entity.getRandomZ(randomScale), 0, d0, d1, d2, 1.0, null
            );
         }

         ci.cancel();
      }
   }

   @Inject(
      method = "addServerParticlesAroundSelfToPredicatePlayer(Ljava/util/function/Predicate;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/particles/ParticleOptions;D)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void trlag$predicate(Predicate<ServerPlayer> predicate, Entity entity, ParticleOptions particle, double randomScale, CallbackInfo ci) {
      if (entity.level() instanceof ServerLevel sl) {
         RandomSource random = entity.getRandom();

         for (int i = 0; i < 5; i++) {
            double d0 = random.nextGaussian() * 0.02;
            double d1 = random.nextGaussian() * 0.02;
            double d2 = random.nextGaussian() * 0.02;
            TensuraParticleInRangeUtil.sendToPlayersInRange(
               sl, particle, true, entity.getRandomX(randomScale), entity.getRandomY(), entity.getRandomZ(randomScale), 0, d0, d1, d2, 1.0, predicate
            );
         }

         ci.cancel();
      }
   }

   @Inject(
      method = "serverParticleCloud(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void trlag$serverCloud(
      Level level, RandomSource random, ParticleOptions p, double xPos, double yPos, double zPos, double ySpeed, double xzSpeed, double radius, CallbackInfo ci
   ) {
      if (level instanceof ServerLevel sl) {
         int n = Mth.ceil((float) Math.PI * radius * radius);

         for (int j = 0; j < n; j++) {
            float angle = random.nextFloat() * (float) (Math.PI * 2);
            double randomRad = Mth.sqrt(random.nextFloat()) * radius;
            double x = xPos + Mth.cos(angle) * randomRad;
            double z = zPos + Mth.sin(angle) * randomRad;
            double xSpeed = (0.5 - random.nextDouble()) * xzSpeed;
            double zSpeed = (0.5 - random.nextDouble()) * xzSpeed;
            TensuraParticleInRangeUtil.sendToPlayersInRange(sl, p, false, x, yPos, z, 0, xSpeed, ySpeed, zSpeed, 1.0, null);
         }

         ci.cancel();
      }
   }
}
