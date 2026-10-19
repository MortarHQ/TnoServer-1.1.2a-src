package it.hurts.sskirillss.relics.init;

import it.hurts.sskirillss.relics.tiles.ResearchingTableTile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TileRegistry {
   private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "relics");
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ResearchingTableTile>> RESEARCHING_TABLE = TILES.register(
      "researching_table", () -> Builder.of(ResearchingTableTile::new, new Block[]{(Block)BlockRegistry.RESEARCHING_TABLE.get()}).build(null)
   );

   public static void register(IEventBus bus) {
      TILES.register(bus);
   }
}
