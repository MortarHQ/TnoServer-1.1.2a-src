package com.tensura_tno.compat.oneenough;

import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

@EventBusSubscriber(modid = "tensura_tno")
public final class OneEnoughBlockCompat {
   public static final OneEnoughBlockCache<Block, RegistryLookup<Block>> CACHE = new OneEnoughBlockCache<>(new OneEnoughBlockCompat.BlockRegistry());

   private OneEnoughBlockCompat() {
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onTagsUpdated(TagsUpdatedEvent event) {
      CACHE.invalidateResults();
   }

   @SubscribeEvent
   public static void onServerStopped(ServerStoppedEvent event) {
      CACHE.invalidateResults();
   }

   private static final class BlockRegistry implements OneEnoughBlockCache.BlockRegistry<Block, RegistryLookup<Block>> {
      public String idOf(Block block) {
         return BuiltInRegistries.BLOCK.getKey(block).toString();
      }

      public Block findBlock(String id) {
         ResourceLocation location = ResourceLocation.tryParse(id);
         return location != null && BuiltInRegistries.BLOCK.containsKey(location) ? (Block)BuiltInRegistries.BLOCK.get(location) : null;
      }

      public boolean hasTag(Block block, RegistryLookup<Block> lookup, String tagId) {
         ResourceLocation location = ResourceLocation.tryParse(tagId);
         if (location == null) {
            return false;
         }

         TagKey<Block> tag = TagKey.create(Registries.BLOCK, location);
         Reference<Block> holder = block.builtInRegistryHolder();
         if (lookup == null) {
            return holder.is(tag);
         }

         Named<Block> members = (Named<Block>)lookup.get(tag).orElse(null);
         return members != null && members.contains(holder);
      }
   }
}
