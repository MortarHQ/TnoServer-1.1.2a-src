package com.faboslav.friendsandfoes.beekeeperhut.world.processor.neoforge;

import com.faboslav.friendsandfoes.beekeeperhut.platform.neoforge.ProcessorTypes;
import com.faboslav.friendsandfoes.beekeeperhut.util.world.processor.BeekeeperHutItemFrameProcessorHelper;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;
import org.jetbrains.annotations.Nullable;

public final class BeekeeperHutItemFrameProcessor extends StructureProcessor {
   public static final MapCodec<BeekeeperHutItemFrameProcessor> CODEC = MapCodec.unit(BeekeeperHutItemFrameProcessor::new);

   private BeekeeperHutItemFrameProcessor() {
   }

   public StructureEntityInfo processEntity(
      LevelReader world,
      BlockPos seedPos,
      StructureEntityInfo rawEntityInfo,
      StructureEntityInfo entityInfo,
      StructurePlaceSettings placementSettings,
      StructureTemplate template
   ) {
      return BeekeeperHutItemFrameProcessorHelper.processEntity(entityInfo, placementSettings);
   }

   @Nullable
   public StructureBlockInfo processBlock(
      LevelReader world, BlockPos pos, BlockPos pivot, StructureBlockInfo localEntityInfo, StructureBlockInfo globalEntityInfo, StructurePlaceSettings data
   ) {
      return globalEntityInfo;
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)ProcessorTypes.BEEKEEPER_HUT_ITEM_FRAME_PROCESSOR.get();
   }
}
