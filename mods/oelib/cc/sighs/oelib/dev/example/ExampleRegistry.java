package cc.sighs.oelib.dev.example;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import cc.sighs.oelib.registry.extra.FuelRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class ExampleRegistry {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "oelib");
   public static final RegisterSupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new ExampleItem(new Properties()));
   private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, "oelib");
   public static final RegisterSupplier<Block> EXAMPLE_BLOCK = BLOCKS.register(
      "example_block",
      () -> new ExampleBlock(
         net.minecraft.world.level.block.state.BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .requiresCorrectToolForDrops()
            .strength(2.0F)
            .sound(SoundType.LANTERN)
            .lightLevel(_ignored -> 15)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
      )
   );
   public static final RegisterSupplier<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.register(
      "example_block_item", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Properties())
   );

   public static void init() {
      BLOCKS.register();
      ITEMS.register();
      OELib.LOGGER.info("Queued example registration: {}", EXAMPLE_ITEM.id() + " " + EXAMPLE_BLOCK.id());
   }

   public static void registerFuel() {
      FuelRegister.register(20, EXAMPLE_BLOCK_ITEM);
   }
}
