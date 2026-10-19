package com.shin.tensura_kumodesu.registry.items;

import com.shin.tensura_kumodesu.registry.blocks.KumodesuBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class KumodesuItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("tensura_kumodesu", Registries.ITEM);
   public static final RegistrySupplier<Item> TARATECT_EGG_ITEM = ITEMS.register(
      "taratect_egg", () -> new BlockItem((Block)KumodesuBlocks.TARATECT_EGG.get(), new Properties())
   );

   public static void init() {
      ITEMS.register();
   }
}
