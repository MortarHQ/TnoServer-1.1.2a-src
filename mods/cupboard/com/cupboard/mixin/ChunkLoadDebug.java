package com.cupboard.mixin;

import com.cupboard.Cupboard;
import java.util.concurrent.CompletableFuture;
import net.minecraft.server.level.ChunkResult;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerChunkCache.class)
public abstract class ChunkLoadDebug {
   @Inject(
      method = "getChunkFutureMainThread",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerChunkCache;runDistanceManagerUpdates()Z")
   )
   private void cupboard$logChunkLoading(
      int chunkX, int chunkZ, ChunkStatus requiredStatus, boolean load, CallbackInfoReturnable<CompletableFuture<ChunkResult<ChunkAccess>>> cir
   ) {
      if (Cupboard.config.getCommonConfig().debugChunkloadAttempts && requiredStatus == ChunkStatus.FULL) {
         Cupboard.LOGGER.warn("Trying to load chunk at blockpos X:" + (chunkX << 4) + " Z:" + (chunkZ << 4), new Exception("Chunk load debug"));
      }
   }
}
