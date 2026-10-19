package com.finderfeed.fdlib.systems.multiblock;

import com.finderfeed.fdlib.data_structures.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class MultiblockShape implements Iterable<Pair<BlockState, BlockPos>> {
   private List<BlockPos> partOffsets = new ArrayList<>(List.of(BlockPos.ZERO));
   private List<BlockState> states = new ArrayList<>();
   private int maxDistanceFromMainBlock;

   private MultiblockShape() {
   }

   public static MultiblockShape.Builder builder(BlockState mainstate) {
      return new MultiblockShape.Builder(mainstate);
   }

   public BlockState getMainState() {
      return this.states.getFirst();
   }

   public boolean isAPartOfShape(BlockState stateBeingChecked, BlockPos mainBlockPos, BlockPos checkPos) {
      BlockPos offset = checkPos.subtract(mainBlockPos);
      Direction direction = (Direction)stateBeingChecked.getValue(BlockStateProperties.HORIZONTAL_FACING);
      MultiblockBlock block = (MultiblockBlock)stateBeingChecked.getBlock();
      offset = block.rotateOffsetBack(offset, direction);
      return this.isAPartOfShape(offset);
   }

   public boolean isAPartOfShape(BlockPos offset) {
      return this.partOffsets.contains(offset);
   }

   public int getMaxDistanceFromMainBlock() {
      return this.maxDistanceFromMainBlock;
   }

   @NotNull
   @Override
   public Iterator<Pair<BlockState, BlockPos>> iterator() {
      return new MultiblockShape.ShapeIterator(this);
   }

   public static class Builder {
      private MultiblockShape shape = new MultiblockShape();

      public Builder(BlockState mainState) {
         this.shape.states.add(mainState);
      }

      public MultiblockShape.Builder addMultiblockPartOffset(BlockPos offset, BlockState state) {
         if (!state.is(this.shape.getMainState().getBlock())) {
            throw new RuntimeException("Cannot create a shape out of different blocks! Blowing up...");
         }

         this.shape.partOffsets.add(offset);
         this.shape.states.add((BlockState)state.setValue(MultiblockBlock.MAIN_BLOCK, false));
         this.shape.maxDistanceFromMainBlock = Math.max(
            this.shape.maxDistanceFromMainBlock, Math.max(Math.abs(offset.getX()), Math.max(Math.abs(offset.getY()), Math.abs(offset.getZ())))
         );
         return this;
      }

      public MultiblockShape build() {
         return this.shape;
      }
   }

   public static class ShapeIterator implements Iterator<Pair<BlockState, BlockPos>> {
      private MultiblockShape shape;
      private int currentIndex = 0;

      public ShapeIterator(MultiblockShape multiblockShape) {
         this.shape = multiblockShape;
      }

      @Override
      public boolean hasNext() {
         return this.currentIndex < this.shape.partOffsets.size();
      }

      public Pair<BlockState, BlockPos> next() {
         BlockState blockState = this.shape.states.get(this.currentIndex);
         BlockPos offset = this.shape.partOffsets.get(this.currentIndex);
         Pair<BlockState, BlockPos> pair = new Pair<>(blockState, offset);
         this.currentIndex++;
         return pair;
      }
   }
}
