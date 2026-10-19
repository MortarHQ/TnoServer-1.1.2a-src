package net.unusual.block_factorys_bosses.init;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BossesRisePOI {
   public static final DeferredRegister<PoiType> REGISTRY = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, "block_factorys_bosses");
   public static final DeferredHolder<PoiType, PoiType> BOSS_SPAWNER = register(
      "boss_spawner", () -> new HashSet<>(((Block)BossesRiseBlocks.BOSS_SPAWNER.get()).getStateDefinition().getPossibleStates())
   );
   public static final DeferredHolder<PoiType, PoiType> KRAKEN_SPAWNER = register(
      "kraken_spawner", () -> new HashSet<>(((Block)BossesRiseBlocks.KRAKEN_SPAWNER.get()).getStateDefinition().getPossibleStates())
   );

   private static DeferredHolder<PoiType, PoiType> register(String registryname, Supplier<Set<BlockState>> states) {
      return REGISTRY.register(registryname, () -> new PoiType(states.get(), 1, 32));
   }
}
