package com.natamus.collective_common_neoforge.services.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface TeleportHelper {
   boolean teleportEntity(Entity var1, ServerLevel var2, Vec3 var3);

   boolean teleportEntity(Entity var1, ServerLevel var2, BlockPos var3);

   boolean teleportEntity(Entity var1, ResourceKey<Level> var2, Vec3 var3);

   boolean teleportEntity(Entity var1, ResourceKey<Level> var2, BlockPos var3);
}
