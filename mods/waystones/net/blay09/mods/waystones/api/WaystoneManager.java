package net.blay09.mods.waystones.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;

public interface WaystoneManager {
   Optional<Waystone> getWaystoneAt(BlockGetter var1, BlockPos var2);

   Optional<Waystone> getWaystoneById(UUID var1);

   Optional<Waystone> findWaystoneByName(String var1);

   Stream<Waystone> getWaystones();

   Stream<Waystone> getWaystonesByType(ResourceLocation var1);

   List<Waystone> getGlobalWaystones();
}
