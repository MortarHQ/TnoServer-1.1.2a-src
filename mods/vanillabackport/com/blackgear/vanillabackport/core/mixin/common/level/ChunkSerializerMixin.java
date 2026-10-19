package com.blackgear.vanillabackport.core.mixin.common.level;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
   @Inject(
      method = "read(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/ai/village/poi/PoiManager;Lnet/minecraft/world/level/chunk/storage/RegionStorageInfo;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/world/level/chunk/ProtoChunk;",
      at = @At("HEAD")
   )
   private static void remapProperties(
      ServerLevel level, PoiManager poiManager, RegionStorageInfo regionStorageInfo, ChunkPos pos, CompoundTag tag, CallbackInfoReturnable<ProtoChunk> cir
   ) {
      updateLeafLitterProperties(tag);
   }

   @Unique
   private static void updateLeafLitterProperties(CompoundTag tag) {
      if (tag.contains("sections")) {
         ListTag sections = tag.getList("sections", 10);

         for (int i = 0; i < sections.size(); i++) {
            CompoundTag section = sections.getCompound(i);
            if (section.contains("block_states")) {
               CompoundTag blockStates = section.getCompound("block_states");
               if (blockStates.contains("palette")) {
                  ListTag palette = blockStates.getList("palette", 10);

                  for (int j = 0; j < palette.size(); j++) {
                     CompoundTag entry = palette.getCompound(j);
                     if ("minecraft:leaf_litter".equals(entry.getString("Name"))) {
                        CompoundTag properties = entry.getCompound("Properties");
                        if (properties.contains("flower_amount")) {
                           String value = properties.getString("flower_amount");
                           properties.remove("flower_amount");
                           properties.putString("segment_amount", value);
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
