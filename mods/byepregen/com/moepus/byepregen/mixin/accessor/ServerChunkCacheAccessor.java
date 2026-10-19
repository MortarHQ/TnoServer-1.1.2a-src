package com.moepus.byepregen.mixin.accessor;

import java.util.concurrent.CompletableFuture;
import net.minecraft.server.level.ChunkResult;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ServerChunkCache.class, remap = false)
public interface ServerChunkCacheAccessor {
   @Invoker("getChunkFutureMainThread")
   CompletableFuture<ChunkResult<ChunkAccess>> byepregen$getChunkFutureMainThread(int var1, int var2, ChunkStatus var3, boolean var4);
}
