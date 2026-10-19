package com.moepus.byepregen.gcfree;

import java.util.concurrent.CompletableFuture;
import net.minecraft.world.level.ChunkPos;

public interface RawIoWorker {
   CompletableFuture<Void> byepregen$storeRawChunkData(ChunkPos var1, RawChunkData var2);
}
