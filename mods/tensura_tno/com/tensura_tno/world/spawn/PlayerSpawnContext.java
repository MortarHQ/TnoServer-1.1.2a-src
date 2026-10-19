package com.tensura_tno.world.spawn;

import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

interface PlayerSpawnContext {
   boolean isEnabled();

   boolean isTensuraLoaded();

   boolean isOverworld();

   ResourceLocation dimensionId();

   boolean isPersistentFlagSet();

   void setPersistentFlag();

   boolean hasRespawnPosition();

   BlockPos blockPosition();

   void teleportTo(double var1, double var3, double var5);

   ResourceLocation biomeIdAt(BlockPos var1);

   Set<ResourceLocation> blacklist();

   Optional<BlockPos> findSafeSpawn(BlockPos var1);

   int searchRadius();

   String playerName();
}
