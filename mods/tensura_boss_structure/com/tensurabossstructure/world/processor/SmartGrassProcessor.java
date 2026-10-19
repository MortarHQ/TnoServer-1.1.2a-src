package com.tensurabossstructure.world.processor;

import com.mojang.serialization.MapCodec;
import com.tensurabossstructure.registry.ModProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;

public class SmartGrassProcessor extends StructureProcessor {
   public static final SmartGrassProcessor INSTANCE = new SmartGrassProcessor();
   public static final MapCodec<SmartGrassProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

   private SmartGrassProcessor() {
   }

   @Nullable
   public StructureBlockInfo process(
      LevelReader level,
      BlockPos origin,
      BlockPos centerBottom,
      StructureBlockInfo originalBlockInfo,
      StructureBlockInfo modifiedBlockInfo,
      StructurePlaceSettings settings,
      @Nullable StructureTemplate template
   ) {
      if (originalBlockInfo.state().getBlock() != Blocks.GRASS_BLOCK) {
         return modifiedBlockInfo;
      }

      if (!level.getBlockState(modifiedBlockInfo.pos()).is(BlockTags.DIRT) && level.isEmptyBlock(modifiedBlockInfo.pos().above())) {
         for (Direction direction : Plane.HORIZONTAL) {
            BlockState stateAt = level.getBlockState(modifiedBlockInfo.pos().relative(direction));
            if (stateAt.getBlock() == Blocks.PODZOL) {
               return new StructureBlockInfo(modifiedBlockInfo.pos(), Blocks.PODZOL.defaultBlockState(), null);
            }

            if (stateAt.getBlock() == Blocks.GRASS_BLOCK) {
               return modifiedBlockInfo;
            }

            if (stateAt.getBlock() == Blocks.MYCELIUM) {
               return new StructureBlockInfo(modifiedBlockInfo.pos(), Blocks.MYCELIUM.defaultBlockState(), null);
            }

            if (stateAt.getBlock() == Blocks.DIRT_PATH) {
               return new StructureBlockInfo(modifiedBlockInfo.pos(), Blocks.DIRT_PATH.defaultBlockState(), null);
            }

            if (stateAt.getBlock() == Blocks.COARSE_DIRT) {
               return new StructureBlockInfo(modifiedBlockInfo.pos(), Blocks.COARSE_DIRT.defaultBlockState(), null);
            }
         }

         return modifiedBlockInfo;
      } else {
         return null;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)ModProcessors.SMART_GRASS.get();
   }
}
