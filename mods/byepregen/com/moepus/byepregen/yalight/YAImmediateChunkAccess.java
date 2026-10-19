package com.moepus.byepregen.yalight;

import javax.annotation.Nullable;
import net.minecraft.world.level.chunk.ChunkAccess;

public interface YAImmediateChunkAccess {
   @Nullable
   ChunkAccess byepregen$getAnyChunkNow(int var1, int var2);
}
