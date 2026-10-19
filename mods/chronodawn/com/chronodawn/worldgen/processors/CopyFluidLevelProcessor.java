package com.chronodawn.worldgen.processors;

import com.chronodawn.ChronoDawn;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModStructureProcessorTypes;
import com.mojang.serialization.MapCodec;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;

public class CopyFluidLevelProcessor extends StructureProcessor {
   public static final Set<BlockPos> INTENTIONAL_WATERLOGGING = Collections.newSetFromMap(new ConcurrentHashMap<>());
   public static final MapCodec<CopyFluidLevelProcessor> CODEC = MapCodec.unit(CopyFluidLevelProcessor::new);

   @Nullable
   public StructureBlockInfo processBlock(
      LevelReader level,
      BlockPos blockPos,
      BlockPos piecePos,
      StructureBlockInfo originalBlockInfo,
      StructureBlockInfo currentBlockInfo,
      StructurePlaceSettings settings
   ) {
      BlockState state = currentBlockInfo.state();
      if (state.is((Block)ModBlocks.DECORATIVE_WATER.get())) {
         if (state.hasProperty(BlockStateProperties.LEVEL)) {
            int fluidLevel = (Integer)state.getValue(BlockStateProperties.LEVEL);
            BlockState newState = (BlockState)Blocks.WATER.defaultBlockState().setValue(BlockStateProperties.LEVEL, fluidLevel);
            return new StructureBlockInfo(currentBlockInfo.pos(), newState, currentBlockInfo.nbt());
         } else {
            return new StructureBlockInfo(currentBlockInfo.pos(), Blocks.WATER.defaultBlockState(), currentBlockInfo.nbt());
         }
      } else {
         if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            boolean currentWaterlogged = (Boolean)state.getValue(BlockStateProperties.WATERLOGGED);
            BlockState originalState = originalBlockInfo.state();
            BlockPos worldPos = currentBlockInfo.pos();
            boolean originalWaterlogged = originalState.hasProperty(BlockStateProperties.WATERLOGGED)
               && (Boolean)originalState.getValue(BlockStateProperties.WATERLOGGED);
            if (originalWaterlogged) {
               INTENTIONAL_WATERLOGGING.add(worldPos.immutable());
               ChronoDawn.LOGGER.debug("Recorded intentional waterlogging at {}: {}", worldPos, state.getBlock());
            }

            if (currentWaterlogged) {
               BlockState newState = (BlockState)state.setValue(BlockStateProperties.WATERLOGGED, false);
               return new StructureBlockInfo(currentBlockInfo.pos(), newState, currentBlockInfo.nbt());
            }
         }

         return currentBlockInfo;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)ModStructureProcessorTypes.COPY_FLUID_LEVEL.get();
   }
}
