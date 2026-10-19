package com.bobmowzie.mowziesmobs.server.world.feature.structure.processor;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class BaseProcessor extends StructureProcessor {
   public static final BaseProcessor INSTANCE = new BaseProcessor();
   public static final MapCodec<BaseProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)ProcessorHandler.BASE_PROCESSOR.value();
   }

   public StructureBlockInfo process(
      LevelReader levelReader,
      BlockPos jigsawPiecePos,
      BlockPos jigsawPieceBottomCenterPos,
      StructureBlockInfo blockInfoLocal,
      StructureBlockInfo blockInfoGlobal,
      StructurePlaceSettings structurePlacementData,
      StructureTemplate template
   ) {
      if (blockInfoGlobal.state().is(Blocks.COBBLED_DEEPSLATE)) {
         if (levelReader instanceof WorldGenRegion worldGenRegion && !worldGenRegion.getCenter().equals(new ChunkPos(blockInfoGlobal.pos()))) {
            return blockInfoGlobal;
         }

         MutableBlockPos mutable = blockInfoGlobal.pos().mutable().move(Direction.DOWN);
         BlockState currBlockState = levelReader.getBlockState(mutable);
         RandomSource random = structurePlacementData.getRandom(blockInfoGlobal.pos());
         blockInfoGlobal = new StructureBlockInfo(blockInfoGlobal.pos(), this.chooseRandomState(random), blockInfoGlobal.nbt());

         while (mutable.getY() > levelReader.getMinBuildHeight() && mutable.getY() < levelReader.getMaxBuildHeight() && !currBlockState.isSolid()) {
            levelReader.getChunk(mutable).setBlockState(mutable, this.chooseRandomState(random), false);
            mutable.move(Direction.DOWN);
            currBlockState = levelReader.getBlockState(mutable);
         }
      }

      return blockInfoGlobal;
   }

   public BlockState chooseRandomState(RandomSource random) {
      float v = random.nextFloat();
      return v > 0.7 ? Blocks.POLISHED_DEEPSLATE.defaultBlockState() : Blocks.COBBLED_DEEPSLATE.defaultBlockState();
   }
}
