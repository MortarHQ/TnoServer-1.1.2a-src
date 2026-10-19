package com.github.L_Ender.cataclysm.world.structures.Processor;

import com.github.L_Ender.cataclysm.init.ModStructureProcessor;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class WaterlogWhenReplacingWaterProcessor extends StructureProcessor {
   public static final MapCodec<WaterlogWhenReplacingWaterProcessor> CODEC = MapCodec.unit(WaterlogWhenReplacingWaterProcessor::new);

   private WaterlogWhenReplacingWaterProcessor() {
   }

   public StructureBlockInfo processBlock(
      LevelReader levelReader, BlockPos pos, BlockPos pos2, StructureBlockInfo infoIn1, StructureBlockInfo infoIn2, StructurePlaceSettings settings
   ) {
      if (infoIn2.state().hasProperty(BlockStateProperties.WATERLOGGED)) {
         if (levelReader instanceof WorldGenRegion worldGenRegion && !worldGenRegion.getCenter().equals(new ChunkPos(infoIn2.pos()))) {
            return infoIn2;
         } else {
            BlockState blockState = levelReader.getChunk(infoIn2.pos()).getBlockState(infoIn2.pos());
            boolean isWater = blockState.getFluidState().is(FluidTags.WATER);
            if (isWater) {
               ChunkAccess chunk = levelReader.getChunk(infoIn2.pos());
               int minY = chunk.getMinBuildHeight();
               int maxY = chunk.getMaxBuildHeight();
               int currentY = infoIn2.pos().getY();
               if (currentY >= minY && currentY <= maxY) {
                  ((LevelAccessor)levelReader).scheduleTick(infoIn2.pos(), infoIn2.state().getBlock(), 0);
               }
            }

            return new StructureBlockInfo(infoIn2.pos(), (BlockState)infoIn2.state().setValue(BlockStateProperties.WATERLOGGED, isWater), infoIn2.nbt());
         }
      } else {
         return infoIn2;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)ModStructureProcessor.WATERLOGGING_WHEN_REPLACING_WATER_PROCESSOR.get();
   }
}
