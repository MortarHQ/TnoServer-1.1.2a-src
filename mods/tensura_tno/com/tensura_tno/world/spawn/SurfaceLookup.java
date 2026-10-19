package com.tensura_tno.world.spawn;

import net.minecraft.core.BlockPos;

public interface SurfaceLookup {
   BlockPos surfaceAt(int var1, int var2);

   int minBuildHeight();

   int maxBuildHeight();

   boolean isStandable(BlockPos var1);
}
