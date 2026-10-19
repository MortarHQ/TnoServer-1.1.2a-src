package com.faboslav.friendsandfoes.common.world.processor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;

public abstract class StructureEntityProcessor extends StructureProcessor {
   public abstract StructureEntityInfo processEntity(
      ServerLevelAccessor var1, BlockPos var2, BlockPos var3, StructureEntityInfo var4, StructureEntityInfo var5, StructurePlaceSettings var6
   );
}
