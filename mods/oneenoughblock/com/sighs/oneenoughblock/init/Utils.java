package com.sighs.oneenoughblock.init;

import com.sighs.oneenoughblock.Oneenoughblock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class Utils {
   public static <T extends Comparable<T>> BlockState saveState(BlockState from, BlockState to) {
      if (OEBConfig.get().extendedBlockProperty()) {
         return to;
      }

      for (Entry<Property<?>, Comparable<?>> entry : from.getValues().entrySet()) {
         to = (BlockState)to.trySetValue(entry.getKey(), entry.getValue());
      }

      return to;
   }

   public static String getBlockRegistryName(Block block) {
      if (block == null) {
         return null;
      }

      ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
      return id.toString();
   }

   public static Block getBlockById(String id) {
      if (id != null && !id.isEmpty()) {
         try {
            return (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse(id));
         } catch (Exception e) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static boolean isTagExists(ResourceLocation tagId, RegistryLookup<Block> registryLookup) {
      TagKey<Block> tagKey = TagKey.create(Registries.BLOCK, tagId);
      return registryLookup.get(tagKey).isPresent();
   }

   public static Collection<Block> getBlocksOfTag(ResourceLocation tagId, RegistryLookup<Block> registryLookup) {
      TagKey<Block> tagKey = TagKey.create(Registries.BLOCK, tagId);
      Collection<Block> result = new HashSet<>();
      Oneenoughblock.LOGGER.debug("Attempting to resolve tag: {}", tagId);
      Optional<Named<Block>> tagOptional = registryLookup.get(tagKey);
      if (tagOptional.isPresent()) {
         for (Holder<Block> holder : tagOptional.get()) {
            result.add((Block)holder.value());
         }

         Oneenoughblock.LOGGER.debug("Tag {} resolved to {} blocks: {}", tagId, result.size(), result.stream().map(Utils::getBlockRegistryName).toList());
      } else {
         Oneenoughblock.LOGGER.warn("Tag {} not found in registry lookup", tagId);
      }

      return result;
   }

   public static List<Block> resolveBlockList(List<String> identifiers, RegistryLookup<Block> registryLookup) {
      List<Block> result = new ArrayList<>();

      for (String id : identifiers) {
         if (id != null && !id.isEmpty()) {
            if (id.startsWith("#")) {
               ResourceLocation tagId = ResourceLocation.tryParse(id.substring(1));
               if (tagId == null) {
                  Oneenoughblock.LOGGER.warn("Invalid tag ID format: {}", id);
               } else {
                  Collection<Block> tagBlocks = getBlocksOfTag(tagId, registryLookup);
                  if (tagBlocks.isEmpty()) {
                     Oneenoughblock.LOGGER.warn("Tag {} is empty or not found", tagId);
                  } else {
                     result.addAll(tagBlocks);
                  }
               }
            } else {
               Block block = getBlockById(id);
               if (block != null) {
                  result.add(block);
               } else {
                  Oneenoughblock.LOGGER.warn("Block ID not found: {}", id);
               }
            }
         }
      }

      return result;
   }
}
