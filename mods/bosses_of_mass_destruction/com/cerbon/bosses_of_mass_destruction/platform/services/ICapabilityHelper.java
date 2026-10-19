package com.cerbon.bosses_of_mass_destruction.platform.services;

import com.cerbon.bosses_of_mass_destruction.capability.ChunkBlockCache;
import java.util.List;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface ICapabilityHelper {
   Optional<ChunkBlockCache> getChunkBlockCache(Level var1);

   List<Vec3> getPlayerPositions(ServerPlayer var1);
}
