package com.tensura_tno.world.spawn;

import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

interface WorldSpawnContext {
   boolean isEnabled();

   boolean isTensuraLoaded();

   boolean isOverworld();

   ResourceLocation dimensionId();

   BlockPos getSharedSpawnPos();

   void setDefaultSpawnPos(BlockPos var1, float var2);

   ResourceLocation biomeIdAt(BlockPos var1);

   Set<ResourceLocation> blacklist();

   boolean isFlagDone();

   void markFlagDone();

   Optional<BlockPos> findSafeSpawn(BlockPos var1);

   int searchRadius();
}
