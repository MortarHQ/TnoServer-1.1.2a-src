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
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.NotNull;

public class MonasteryStairsProcessor extends StructureProcessor {
   public static final MonasteryStairsProcessor INSTANCE = new MonasteryStairsProcessor();
   public static final MapCodec<MonasteryStairsProcessor> CODEC = MapCodec.unit(() -> INSTANCE);
   private static final BlockState andesiteStairs = Blocks.ANDESITE_STAIRS.defaultBlockState();
   private static final BlockState cobbledDeepslate = Blocks.COBBLED_DEEPSLATE.defaultBlockState();
   private static final BlockState cobbledDeepslateWall = Blocks.COBBLED_DEEPSLATE_WALL.defaultBlockState();
   private static final BlockState air = Blocks.AIR.defaultBlockState();
   private static final BlockState[] STAIR = new BlockState[]{Blocks.AIR.defaultBlockState(), andesiteStairs};
   private static final BlockState[] RAIL = new BlockState[]{cobbledDeepslateWall, cobbledDeepslate};

   public StructureBlockInfo process(
      LevelReader levelReader,
      BlockPos jigsawPiecePos,
      BlockPos jigsawPieceBottomCenterPos,
      StructureBlockInfo blockInfoLocal,
      StructureBlockInfo blockInfoGlobal,
      StructurePlaceSettings structurePlacementData,
      StructureTemplate template
   ) {
      BlockState startingState = blockInfoGlobal.state();
      if (startingState.is(Blocks.END_STONE_BRICK_STAIRS) || startingState.is(Blocks.NETHER_BRICK_STAIRS)) {
         Direction facing = ((Direction)blockInfoGlobal.state().getValue(StairBlock.FACING)).getOpposite();
         facing = structurePlacementData.getRotation().rotate(facing);
         RandomSource random = structurePlacementData.getRandom(blockInfoGlobal.pos());
         BlockState[] blocksToPlace;
         if (startingState.is(Blocks.END_STONE_BRICK_STAIRS)) {
            blocksToPlace = STAIR;
            blockInfoGlobal = new StructureBlockInfo(
               blockInfoGlobal.pos(), (BlockState)andesiteStairs.setValue(StairBlock.FACING, facing), blockInfoGlobal.nbt()
            );
         } else {
            blocksToPlace = RAIL;
            blockInfoGlobal = new StructureBlockInfo(blockInfoGlobal.pos(), cobbledDeepslate, blockInfoGlobal.nbt());
         }

         if (levelReader instanceof WorldGenRegion worldGenRegion && !worldGenRegion.getCenter().equals(new ChunkPos(blockInfoGlobal.pos()))) {
            return blockInfoGlobal;
         }

         for (int i = 0; i < 20; i++) {
            MutableBlockPos mutable = blockInfoGlobal.pos().mutable().move(Direction.DOWN);
            BlockState currBlockState = levelReader.getBlockState(mutable);
            int j = 0;

            while (mutable.getY() > levelReader.getMinBuildHeight() && mutable.getY() < levelReader.getMaxBuildHeight() && !currBlockState.isSolid()) {
               BlockState newState;
               if (j < blocksToPlace.length) {
                  newState = blocksToPlace[j];
                  j++;
               } else {
                  newState = this.chooseRandomState(random);
               }

               levelReader.getChunk(mutable).setBlockState(mutable, newState, false);
               mutable.move(Direction.DOWN);
               mutable.move(facing);
               currBlockState = levelReader.getBlockState(mutable);
            }
         }
      }

      return blockInfoGlobal;
   }

   public BlockState chooseRandomState(RandomSource random) {
      float v = random.nextFloat();
      return v > 0.7 ? Blocks.POLISHED_DEEPSLATE.defaultBlockState() : Blocks.COBBLED_DEEPSLATE.defaultBlockState();
   }

   @NotNull
   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)ProcessorHandler.STAIRS_PROCESSOR.value();
   }
}
