package com.natamus.collective_common_forge.services.helpers;

import com.mojang.datafixers.util.Pair;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;

public interface RegisterBlockHelper {
   default <T extends Block> void registerBlockWithoutItem(Object modEventBusObject, ResourceLocation resourceLocation, Supplier<T> blockSupplier) {
      this.registerBlockWithoutItem(modEventBusObject, resourceLocation, blockSupplier, false);
   }

   <T extends Block> void registerBlockWithoutItem(Object var1, ResourceLocation var2, Supplier<T> var3, boolean var4);

   Block getRegisteredBlockWithoutItem(ResourceLocation var1);

   default <T extends Block> void registerBlockWithItem(
      Object modEventBusObject, ResourceLocation resourceLocation, Supplier<T> blockSupplier, ResourceKey<CreativeModeTab> creativeModeTabResourceKey
   ) {
      this.registerBlockWithItem(modEventBusObject, resourceLocation, blockSupplier, creativeModeTabResourceKey, false);
   }

   <T extends Block> void registerBlockWithItem(Object var1, ResourceLocation var2, Supplier<T> var3, ResourceKey<CreativeModeTab> var4, boolean var5);

   Block getRegisteredBlockWithItem(ResourceLocation var1);

   Pair<Block, BlockItem> getRegisteredBlockWithItemPair(ResourceLocation var1);

   void setRegisteredBlockWithItemPair(ResourceLocation var1, Class<?> var2, String var3, Class<?> var4, String var5);
}
