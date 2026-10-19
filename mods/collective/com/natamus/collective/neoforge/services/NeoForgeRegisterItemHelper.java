package com.natamus.collective.neoforge.services;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective_common_neoforge.services.helpers.RegisterItemHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class NeoForgeRegisterItemHelper implements RegisterItemHelper {
   private static final HashMap<String, Items> deferredItemRegisterMap = new HashMap<>();
   private static final HashMap<ResourceLocation, DeferredItem<Item>> registeredItems = new HashMap<>();
   private static final List<Pair<ResourceKey<CreativeModeTab>, DeferredItem<Item>>> creativeInventoryItemPairs = new ArrayList<>();

   @Override
   public <T extends Item> void registerItem(
      Object modEventBusObject,
      ResourceLocation resourceLocation,
      Supplier<T> itemSupplier,
      ResourceKey<CreativeModeTab> creativeModeTabResourceKey,
      boolean lastItem
   ) {
      staticRegisterItem(modEventBusObject, resourceLocation, itemSupplier, creativeModeTabResourceKey, lastItem);
   }

   @Override
   public Item getRegisteredItem(ResourceLocation resourceLocation) {
      return (Item)registeredItems.get(resourceLocation).get();
   }

   public static <T extends Item> DeferredItem<Item> staticRegisterItem(
      Object modEventBusObject,
      ResourceLocation resourceLocation,
      Supplier<T> itemSupplier,
      ResourceKey<CreativeModeTab> creativeModeTabResourceKey,
      boolean lastItem
   ) {
      String namespace = resourceLocation.getNamespace();
      if (!deferredItemRegisterMap.containsKey(namespace)) {
         Items deferredItemRegister = DeferredRegister.createItems(namespace);
         deferredItemRegisterMap.put(namespace, deferredItemRegister);
      }

      DeferredItem<Item> deferredItemObject = deferredItemRegisterMap.get(namespace).register(resourceLocation.getPath(), itemSupplier);
      registeredItems.put(resourceLocation, deferredItemObject);
      if (creativeModeTabResourceKey != null) {
         creativeInventoryItemPairs.add(Pair.of(creativeModeTabResourceKey, deferredItemObject));
      }

      if (lastItem) {
         deferredItemRegisterMap.get(namespace).register((IEventBus)modEventBusObject);
      }

      return deferredItemObject;
   }

   public static void addItemsToCreativeInventory(BuildCreativeModeTabContentsEvent e) {
      for (Pair<ResourceKey<CreativeModeTab>, DeferredItem<Item>> tabPair : creativeInventoryItemPairs) {
         if (e.getTabKey().equals(tabPair.getFirst())) {
            e.accept((ItemLike)((DeferredItem)tabPair.getSecond()).get());
         }
      }
   }
}
