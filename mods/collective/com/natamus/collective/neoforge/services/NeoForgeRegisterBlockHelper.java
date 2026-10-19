package com.natamus.collective.neoforge.services;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective_common_neoforge.services.helpers.RegisterBlockHelper;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;

public class NeoForgeRegisterBlockHelper implements RegisterBlockHelper {
   private static final HashMap<String, Blocks> deferredBlockRegisterMap = new HashMap<>();
   private static final HashMap<ResourceLocation, DeferredBlock<Block>> registeredBlocksWithoutItem = new HashMap<>();
   private static final HashMap<ResourceLocation, Pair<DeferredBlock<Block>, DeferredItem<Item>>> registeredBlockWithItemPairs = new HashMap<>();

   @Override
   public <T extends Block> void registerBlockWithoutItem(
      Object modEventBusObject, ResourceLocation resourceLocation, Supplier<T> blockSupplier, boolean lastBlock
   ) {
      staticRegisterBlock(modEventBusObject, resourceLocation, blockSupplier, null, lastBlock, false);
   }

   @Override
   public Block getRegisteredBlockWithoutItem(ResourceLocation resourceLocation) {
      return (Block)registeredBlocksWithoutItem.get(resourceLocation).get();
   }

   @Override
   public <T extends Block> void registerBlockWithItem(
      Object modEventBusObject,
      ResourceLocation resourceLocation,
      Supplier<T> blockSupplier,
      ResourceKey<CreativeModeTab> creativeModeTabResourceKey,
      boolean lastBlock
   ) {
      staticRegisterBlock(modEventBusObject, resourceLocation, blockSupplier, creativeModeTabResourceKey, lastBlock, true);
   }

   @Override
   public Block getRegisteredBlockWithItem(ResourceLocation resourceLocation) {
      return (Block)((DeferredBlock)registeredBlockWithItemPairs.get(resourceLocation).getFirst()).get();
   }

   @Override
   public Pair<Block, BlockItem> getRegisteredBlockWithItemPair(ResourceLocation resourceLocation) {
      Pair<DeferredBlock<Block>, DeferredItem<Item>> deferredPair = registeredBlockWithItemPairs.get(resourceLocation);
      return Pair.of((Block)((DeferredBlock)deferredPair.getFirst()).get(), (BlockItem)((DeferredItem)deferredPair.getSecond()).get());
   }

   @Override
   public void setRegisteredBlockWithItemPair(
      ResourceLocation resourceLocation, Class<?> blockClass, String blockFieldName, Class<?> blockItemClass, String blockItemFieldName
   ) {
      Pair<DeferredBlock<Block>, DeferredItem<Item>> deferredPair = registeredBlockWithItemPairs.get(resourceLocation);

      try {
         Field blockField = blockClass.getDeclaredField(blockFieldName);
         blockField.setAccessible(true);
         blockField.set(null, ((DeferredBlock)deferredPair.getFirst()).get());
         Field blockItemField = blockItemClass.getDeclaredField(blockItemFieldName);
         blockItemField.setAccessible(true);
         blockItemField.set(null, ((DeferredItem)deferredPair.getSecond()).get());
      } catch (NoSuchFieldException | IllegalAccessException e) {
         e.printStackTrace();
      }
   }

   public static <T extends Block> void staticRegisterBlock(
      Object modEventBusObject,
      ResourceLocation resourceLocation,
      Supplier<T> blockSupplier,
      ResourceKey<CreativeModeTab> creativeModeTabResourceKey,
      boolean lastBlock,
      boolean registerAsItem
   ) {
      String namespace = resourceLocation.getNamespace();
      if (!deferredBlockRegisterMap.containsKey(namespace)) {
         Blocks deferredBlockRegister = DeferredRegister.createBlocks(namespace);
         deferredBlockRegisterMap.put(namespace, deferredBlockRegister);
      }

      DeferredBlock<Block> deferredBlockObject = deferredBlockRegisterMap.get(namespace).register(resourceLocation.getPath(), blockSupplier);
      if (registerAsItem) {
         DeferredItem<Item> deferredItemObject = NeoForgeRegisterItemHelper.staticRegisterItem(
            modEventBusObject,
            resourceLocation,
            () -> (T)(new BlockItem((Block)deferredBlockObject.get(), new Properties())),
            creativeModeTabResourceKey,
            lastBlock
         );
         registeredBlockWithItemPairs.put(resourceLocation, Pair.of(deferredBlockObject, deferredItemObject));
      } else {
         registeredBlocksWithoutItem.put(resourceLocation, deferredBlockObject);
      }

      if (lastBlock) {
         deferredBlockRegisterMap.get(namespace).register((IEventBus)modEventBusObject);
      }
   }
}
