package com.finderfeed.fdlib.init;

import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FDItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems("fdlib");
   public static final Supplier<Item> TEST_MULTIBLOCK = ITEMS.register("test_multiblock", () -> new BlockItem(FDBlocks.TEST_MULTIBLOCK.get(), new Properties()));
}
