package com.faboslav.friendsandfoes.beekeeperhut.util.world.processor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;

public final class BeekeeperHutItemFrameProcessorHelper {
   public static StructureEntityInfo processEntity(StructureEntityInfo globalEntityInfo, StructurePlaceSettings structurePlacementData) {
      if (!globalEntityInfo.nbt.getString("id").equals("minecraft:item_frame")) {
         return globalEntityInfo;
      }

      RandomSource random = structurePlacementData.getRandom(globalEntityInfo.blockPos);
      CompoundTag newNbt = globalEntityInfo.nbt.copy();
      int randomRotation = random.nextInt(8);
      newNbt.putByte("ItemRotation", (byte)randomRotation);
      return new StructureEntityInfo(globalEntityInfo.pos, globalEntityInfo.blockPos, newNbt);
   }
}
