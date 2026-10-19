package com.mafuyu404.oneenoughitem.util;

import com.mafuyu404.oneenoughitem.Oneenoughitem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class Utils {
   public static String getItemRegistryName(Item item) {
      if (item == null) {
         return null;
      }

      ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(item);
      return registryName.toString();
   }

   public static Item getItemById(String registryName) {
      if (registryName != null && !registryName.isEmpty()) {
         try {
            ResourceLocation resourceLocation = ResourceLocation.parse(registryName);
            return !BuiltInRegistries.ITEM.containsKey(resourceLocation) ? null : (Item)BuiltInRegistries.ITEM.get(resourceLocation);
         } catch (Exception e) {
            Oneenoughitem.LOGGER.debug("getItemById: Exception for {}", registryName, e);
            return null;
         }
      } else {
         return null;
      }
   }

   public static Collection<Item> getItemsOfTag(ResourceLocation tagId, RegistryLookup<Item> registryLookup) {
      TagKey<Item> tagKey = TagKey.create(Registries.ITEM, tagId);
      Collection<Item> result = new HashSet<>();
      Oneenoughitem.LOGGER.debug("Attempting to resolve tag: {}", tagId);
      Optional<Named<Item>> tagOptional = registryLookup.get(tagKey);
      if (tagOptional.isPresent()) {
         for (Holder<Item> holder : tagOptional.get()) {
            result.add((Item)holder.value());
         }

         Oneenoughitem.LOGGER.debug("Tag {} resolved to {} items: {}", tagId, result.size(), result.stream().map(Utils::getItemRegistryName).toList());
      } else {
         Oneenoughitem.LOGGER.warn("Tag {} not found in registry lookup", tagId);
      }

      return result;
   }

   public static boolean isTagExists(ResourceLocation tagId, RegistryLookup<Item> registryLookup) {
      TagKey<Item> tagKey = TagKey.create(Registries.ITEM, tagId);
      return registryLookup.get(tagKey).isPresent();
   }

   public static List<Item> resolveItemList(List<String> identifiers, RegistryLookup<Item> registryLookup) {
      List<Item> result = new ArrayList<>();

      for (String id : identifiers) {
         if (id != null && !id.isEmpty()) {
            if (id.startsWith("#")) {
               ResourceLocation tagId = ResourceLocation.tryParse(id.substring(1));
               if (tagId == null) {
                  Oneenoughitem.LOGGER.warn("Invalid tag ID format: {}", id);
               } else {
                  Collection<Item> tagItems = getItemsOfTag(tagId, registryLookup);
                  if (tagItems.isEmpty()) {
                     Oneenoughitem.LOGGER.warn("Tag {} is empty or not found", tagId);
                  } else {
                     result.addAll(tagItems);
                  }
               }
            } else {
               Item item = getItemById(id);
               if (item != null) {
                  result.add(item);
               } else {
                  Oneenoughitem.LOGGER.warn("Item ID not found: {}", id);
               }
            }
         }
      }

      return result;
   }

   public static boolean isItemIdEmpty(String id) {
      return id == null || id.equals("minecraft:air");
   }
}
