package com.faboslav.friendsandfoes.common.world.processor;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureProcessorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public final class CitadelBottomProcessor extends StructureProcessor {
   public static final MapCodec<CitadelBottomProcessor> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            BlockState.CODEC.fieldOf("target_block").forGetter(config -> config.targetBlock),
            BlockState.CODEC.fieldOf("target_block_output").forGetter(config -> config.targetBlockOutput),
            Direction.CODEC.optionalFieldOf("direction", Direction.DOWN).forGetter(processor -> processor.direction),
            Codec.INT.optionalFieldOf("pillar_length", -1).forGetter(config -> config.length)
         )
         .apply(instance, instance.stable(CitadelBottomProcessor::new))
   );
   public final BlockState targetBlock;
   public final BlockState targetBlockOutput;
   public final Direction direction;
   public final int length;

   private CitadelBottomProcessor(BlockState targetBlock, BlockState targetBlockOutput, Direction direction, int length) {
      this.targetBlock = targetBlock;
      this.targetBlockOutput = targetBlockOutput;
      this.direction = direction;
      this.length = length;
   }

   public StructureBlockInfo processBlock(
      LevelReader worldView,
      BlockPos pos,
      BlockPos pivot,
      StructureBlockInfo blockInfoLocal,
      StructureBlockInfo blockInfoGlobal,
      StructurePlaceSettings structurePlacementData
   ) {
      if (blockInfoGlobal.state().is(this.targetBlock.getBlock())) {
         if (worldView instanceof WorldGenRegion chunkRegion && !chunkRegion.getCenter().equals(new ChunkPos(blockInfoGlobal.pos()))) {
            return blockInfoGlobal;
         }

         blockInfoGlobal = new StructureBlockInfo(blockInfoGlobal.pos(), this.targetBlockOutput, blockInfoGlobal.nbt());
         MutableBlockPos mutable = blockInfoGlobal.pos().mutable().move(Direction.DOWN);
         BlockState currentBlockState = worldView.getBlockState(mutable);
         RandomSource random = structurePlacementData.getRandom(blockInfoGlobal.pos());
         int worldBottomY = worldView.getMinBuildHeight();

         for (int worldTopY = worldView.getMaxBuildHeight();
            mutable.getY() > worldBottomY && mutable.getY() < worldTopY && (currentBlockState.isAir() || !worldView.getFluidState(mutable).isEmpty());
            currentBlockState = worldView.getBlockState(mutable)
         ) {
            worldView.getChunk(mutable).setBlockState(mutable, this.targetBlockOutput, false);
            mutable.move(Direction.DOWN);
         }
      }

      return blockInfoGlobal;
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)FriendsAndFoesStructureProcessorTypes.CITADEL_BOTTOM_PROCESSOR.get();
   }
}
