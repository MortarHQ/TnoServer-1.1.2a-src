package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.FluidState;

public class StarcoreBlock extends Block {
   public static final MapCodec<StarcoreBlock> CODEC = simpleCodec(StarcoreBlock::new);
   private static final Direction[] ALL_DIRECTIONS = Direction.values();

   public MapCodec<StarcoreBlock> codec() {
      return CODEC;
   }

   public StarcoreBlock(Properties properties) {
      super(properties);
   }

   protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!oldState.is(state.getBlock())) {
         this.tryAbsorbLava(level, pos);
      }
   }

   protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      this.tryAbsorbLava(level, pos);
      super.neighborChanged(state, level, pos, block, fromPos, isMoving);
   }

   protected void tryAbsorbLava(Level level, BlockPos pos) {
      if (this.removeLavaBreadthFirstSearch(level, pos)) {
         level.setBlock(pos, ESBlocks.BLAZING_STARCORE_BLOCK.get().defaultBlockState(), 2);
      }
   }

   private boolean removeLavaBreadthFirstSearch(Level level, BlockPos pos) {
      return BlockPos.breadthFirstTraversal(pos, 6, 65, (blockPos, consumer) -> {
         for (Direction direction : ALL_DIRECTIONS) {
            consumer.accept(blockPos.relative(direction));
         }
      }, foundPos -> {
         if (foundPos.equals(pos)) {
            return true;
         } else {
            BlockState blockState = level.getBlockState(foundPos);
            FluidState fluidState = level.getFluidState(foundPos);
            if (!fluidState.is(FluidTags.LAVA)) {
               return false;
            } else if (blockState.getBlock() instanceof BucketPickup bucketPickup && !bucketPickup.pickupBlock(null, level, foundPos, blockState).isEmpty()) {
               return true;
            } else {
               if (blockState.getBlock() instanceof LiquidBlock) {
                  level.setBlock(foundPos, Blocks.AIR.defaultBlockState(), 3);
               }

               return true;
            }
         }
      }) > 1;
   }
}
