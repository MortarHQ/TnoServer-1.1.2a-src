package net.regions_unexplored.registry;

import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.regions_unexplored.module.platform.Registrar;
import net.regions_unexplored.worldgen.stateprovider.RandomizedGroundCoverStateProvider;

public interface RUBlockStateProviderTypes {
   Supplier<BlockStateProviderType<RandomizedGroundCoverStateProvider>> RANDOMIZED_GROUND_COVER = register(
      "randomized_ground_cover", RandomizedGroundCoverStateProvider.TYPE
   );

   static <T extends BlockStateProvider> Supplier<BlockStateProviderType<T>> register(String name, BlockStateProviderType<T> type) {
      Registrar.register(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, name, () -> type);
      return () -> type;
   }

   static void init() {
   }
}
