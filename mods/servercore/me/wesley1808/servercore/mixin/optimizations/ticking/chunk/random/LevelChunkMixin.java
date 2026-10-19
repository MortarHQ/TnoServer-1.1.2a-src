package me.wesley1808.servercore.mixin.optimizations.ticking.chunk.random;

import me.wesley1808.servercore.common.interfaces.chunk.ILevelChunk;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.chunk.LevelChunk.PostLoadProcessor;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.ticks.LevelChunkTicks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelChunk.class)
public class LevelChunkMixin implements ILevelChunk {
   @Unique
   private int servercore$lightningTick;

   @Override
   public final int servercore$shouldDoLightning(RandomSource randomSource, int thunderChance) {
      if (this.servercore$lightningTick-- <= 0) {
         this.servercore$lightningTick = randomSource.nextInt(thunderChance) << 1;
         return 0;
      } else {
         return -1;
      }
   }

   @Inject(
      method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/world/level/chunk/UpgradeData;Lnet/minecraft/world/ticks/LevelChunkTicks;Lnet/minecraft/world/ticks/LevelChunkTicks;J[Lnet/minecraft/world/level/chunk/LevelChunkSection;Lnet/minecraft/world/level/chunk/LevelChunk$PostLoadProcessor;Lnet/minecraft/world/level/levelgen/blending/BlendingData;)V",
      at = @At("RETURN")
   )
   private void servercore$initLightingTick(
      Level level,
      ChunkPos chunkPos,
      UpgradeData upgradeData,
      LevelChunkTicks<?> levelChunkTicks,
      LevelChunkTicks<?> levelChunkTicks2,
      long l,
      LevelChunkSection[] levelChunkSections,
      PostLoadProcessor postLoadProcessor,
      BlendingData blendingData,
      CallbackInfo ci
   ) {
      this.servercore$lightningTick = level.threadSafeRandom.nextInt(100000) << 1;
   }
}
