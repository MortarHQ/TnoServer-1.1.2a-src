package com.benji.netherman.common.block;

import com.benji.netherman.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BellAttachType;

public class SamsonitBlock extends Block {
   public SamsonitBlock(Properties properties) {
      super(properties);
   }

   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
      super.onPlace(state, level, pos, oldState, isMoving);
      this.updateNearbyBells(level, pos, true);
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      super.neighborChanged(state, level, pos, block, fromPos, isMoving);
      if (level.getBlockState(fromPos).is(Blocks.BELL)) {
         this.updateNearbyBells(level, pos, true);
      }
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!state.is(newState.getBlock())) {
         this.updateNearbyBells(level, pos, false);
         super.onRemove(state, level, pos, newState, isMoving);
      }
   }

   private void updateNearbyBells(Level level, BlockPos myPos, boolean isAdding) {
      if (!level.isClientSide()) {
         for (Direction dir : Direction.values()) {
            BlockPos checkPos = myPos.relative(dir);
            BlockState targetState = level.getBlockState(checkPos);
            if (isAdding && targetState.is(Blocks.BELL)) {
               BlockState newState = (BlockState)((BlockState)((Block)ModBlocks.SAMSONITE_BELL.get())
                     .defaultBlockState()
                     .setValue(BellBlock.FACING, (Direction)targetState.getValue(BellBlock.FACING)))
                  .setValue(BellBlock.ATTACHMENT, (BellAttachType)targetState.getValue(BellBlock.ATTACHMENT));
               level.setBlock(checkPos, newState, 3);
            } else if (!isAdding && targetState.is((Block)ModBlocks.SAMSONITE_BELL.get())) {
               boolean hasSamsonit = false;

               for (Direction dir2 : Direction.values()) {
                  if (level.getBlockState(checkPos.relative(dir2)).is((Block)ModBlocks.SAMSONIT.get())) {
                     hasSamsonit = true;
                     break;
                  }
               }

               if (!hasSamsonit) {
                  BlockState newState = (BlockState)((BlockState)Blocks.BELL
                        .defaultBlockState()
                        .setValue(BellBlock.FACING, (Direction)targetState.getValue(BellBlock.FACING)))
                     .setValue(BellBlock.ATTACHMENT, (BellAttachType)targetState.getValue(BellBlock.ATTACHMENT));
                  level.setBlock(checkPos, newState, 3);
               }
            }
         }
      }
   }
}
