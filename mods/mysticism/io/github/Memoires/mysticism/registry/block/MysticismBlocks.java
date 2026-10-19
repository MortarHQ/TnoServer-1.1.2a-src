package io.github.Memoires.mysticism.registry.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.block.ElementalRealmPortal;
import io.github.manasmods.tensura.item.misc.SimpleBlockItem;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class MysticismBlocks {
   private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create("mysticism", Registries.BLOCK);
   public static final RegistrySupplier<Block> ELEMENTAL_REALM_PORTAL = BLOCKS.register("elemental_realm_portal", ElementalRealmPortal::new);
   public static final RegistrySupplier<Block> ICE_ORE = BLOCKS.register(
      "ice_ore",
      () -> new DropExperienceBlock(
         UniformInt.of(3, 7),
         Properties.of()
            .mapColor(MapColor.ICE)
            .lightLevel(blockState -> 5)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 3.0F)
      )
   );

   public static void init() {
      BLOCKS.register();
      MysticismBlocks.Items.ITEMS.register();
   }

   public static class Items {
      private static final DeferredRegister<Item> ITEMS = DeferredRegister.create("mysticism", Registries.ITEM);
      public static final RegistrySupplier<Item> ELEMENTAL_REALM_PORTAL = labyrinthBlockItem(MysticismBlocks.ELEMENTAL_REALM_PORTAL);
      public static final RegistrySupplier<Item> ICE_ORE = simpleBlockItem(MysticismBlocks.ICE_ORE);

      public static <T extends Block> RegistrySupplier<Item> labyrinthBlockItem(RegistrySupplier<T> block) {
         return fireResistedBlockItem(block, TensuraCreativeTabs.DUNGEON_BLOCKS);
      }

      public static <T extends Block> RegistrySupplier<Item> fireResistedBlockItem(RegistrySupplier<T> block, DeferredSupplier<CreativeModeTab> tab) {
         return ITEMS.register(
            block.getId().getPath(),
            () -> new SimpleBlockItem((Block)block.get(), new net.minecraft.world.item.Item.Properties().arch$tab(tab).fireResistant())
         );
      }

      public static <T extends Block> RegistrySupplier<Item> simpleBlockItem(RegistrySupplier<T> block) {
         return ITEMS.register(block.getId().getPath(), () -> new SimpleBlockItem((Block)block.get()));
      }

      public static <T extends Block> RegistrySupplier<Item> simpleBlockItem(RegistrySupplier<T> block, CreativeModeTab tab) {
         return ITEMS.register(block.getId().getPath(), () -> new SimpleBlockItem((Block)block.get(), tab));
      }

      public static <T extends Block> RegistrySupplier<Item> simpleBlockItem(RegistrySupplier<T> block, DeferredSupplier<CreativeModeTab> tab) {
         return ITEMS.register(block.getId().getPath(), () -> new SimpleBlockItem((Block)block.get(), tab));
      }
   }
}
