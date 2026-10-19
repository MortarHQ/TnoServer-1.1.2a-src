package com.faboslav.friendsandfoes.common.util.world.processor;

import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;

public final class IllusionerShackItemFrameProcessorHelper {
   public static StructureEntityInfo processEntity(StructureEntityInfo globalEntityInfo, StructurePlaceSettings structurePlacementData) {
      if (!globalEntityInfo.nbt.getString("id").equals("minecraft:item_frame")) {
         return globalEntityInfo;
      }

      RandomSource random = structurePlacementData.getRandom(globalEntityInfo.blockPos);
      CompoundTag newNbt = globalEntityInfo.nbt.copy();
      CompoundTag itemNbt = VersionedNbt.getCompound(newNbt, "Item");
      itemNbt.putString("id", "minecraft:potion");
      itemNbt.put("tag", (Tag)Util.make(new CompoundTag(), potionTag -> potionTag.putString("Potion", "minecraft:water")));
      int randomRotation = random.nextInt(8);
      newNbt.putByte("ItemRotation", (byte)randomRotation);
      return new StructureEntityInfo(globalEntityInfo.pos, globalEntityInfo.blockPos, newNbt);
   }
}
