package net.regions_unexplored.world.level.block.plant.aquatic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.RUBlocks;

public class FloweringLilyBlock extends WaterlilyBlock implements BonemealableBlock {
   protected static final VoxelShape AABB = Block.box(2.0, 0.0, 2.0, 14.0, 0.5, 14.0);

   public FloweringLilyBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.stateDefinition.any());
   }

   public VoxelShape getShape(BlockState p_58169_, BlockGetter p_58170_, BlockPos p_58171_, CollisionContext p_58172_) {
      return AABB;
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter blockGetter, BlockPos pos) {
      return true;
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return this.defaultBlockState();
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return context.getItemInHand().getItem() != this.asItem();
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockpos = pos.below();
      return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos);
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
      FluidState fluidState = getter.getFluidState(pos);
      FluidState fluidState1 = getter.getFluidState(pos.above());
      return (fluidState.getType() == Fluids.WATER || state.getBlock() instanceof IceBlock) && fluidState1.getType() == Fluids.EMPTY
         || fluidState.is(FluidTags.WATER);
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return true;
   }

   public int getLightBlock(BlockState p_54460_, BlockGetter p_54461_, BlockPos p_54462_) {
      return 0;
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState p_226881_) {
      if (randomSource.nextInt(4) == 0) {
         return level.getFluidState(pos.below()).is(Fluids.WATER)
            & level.getFluidState(pos.below().west()).is(Fluids.WATER)
            & level.getFluidState(pos.below().south().west()).is(Fluids.WATER)
            & level.getFluidState(pos.below().south()).is(Fluids.WATER);
      } else if (randomSource.nextInt(4) == 1) {
         return level.getFluidState(pos.below()).is(Fluids.WATER)
            & level.getFluidState(pos.below().north()).is(Fluids.WATER)
            & level.getFluidState(pos.below().north().west()).is(Fluids.WATER)
            & level.getFluidState(pos.below().west()).is(Fluids.WATER);
      } else {
         return randomSource.nextInt(4) == 1
            ? level.getFluidState(pos.below()).is(Fluids.WATER)
               & level.getFluidState(pos.below().north()).is(Fluids.WATER)
               & level.getFluidState(pos.below().north().east()).is(Fluids.WATER)
               & level.getFluidState(pos.below().east()).is(Fluids.WATER)
            : level.getFluidState(pos.below()).is(Fluids.WATER)
               & level.getFluidState(pos.below().south()).is(Fluids.WATER)
               & level.getFluidState(pos.below().south().east()).is(Fluids.WATER)
               & level.getFluidState(pos.below().east()).is(Fluids.WATER);
      }
   }

   public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
      if (!level.isClientSide()) {
         if (randomSource.nextInt(4) == 0) {
            if (level.getBlockState(pos.west()).isAir() & level.getBlockState(pos.south().west()).isAir() & level.getBlockState(pos.south()).isAir()) {
               level.setBlock(
                  pos, (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH), 26
               );
               level.setBlock(
                  pos.west(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST), 26
               );
               level.setBlock(
                  pos.south().west(),
                  (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH),
                  26
               );
               level.setBlock(
                  pos.south(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST), 26
               );
               level.blockUpdated(pos, Blocks.AIR);
               level.blockUpdated(pos.west(), Blocks.AIR);
               level.blockUpdated(pos.south().west(), Blocks.AIR);
               level.blockUpdated(pos.south(), Blocks.AIR);
            }
         } else if (randomSource.nextInt(4) == 1) {
            if (level.getBlockState(pos.north()).isAir() & level.getBlockState(pos.north().west()).isAir() & level.getBlockState(pos.west()).isAir()) {
               level.setBlock(
                  pos, (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST), 26
               );
               level.setBlock(
                  pos.north(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH), 26
               );
               level.setBlock(
                  pos.north().west(),
                  (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST),
                  26
               );
               level.setBlock(
                  pos.west(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH), 26
               );
               level.blockUpdated(pos, Blocks.AIR);
               level.blockUpdated(pos.north(), Blocks.AIR);
               level.blockUpdated(pos.north().west(), Blocks.AIR);
               level.blockUpdated(pos.west(), Blocks.AIR);
            }
         } else if (randomSource.nextInt(4) == 1) {
            if (level.getBlockState(pos.north()).isAir() & level.getBlockState(pos.north().east()).isAir() & level.getBlockState(pos.east()).isAir()) {
               level.setBlock(
                  pos, (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH), 26
               );
               level.setBlock(
                  pos.north(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST), 26
               );
               level.setBlock(
                  pos.north().east(),
                  (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH),
                  26
               );
               level.setBlock(
                  pos.east(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST), 26
               );
               level.blockUpdated(pos, Blocks.AIR);
               level.blockUpdated(pos.north(), Blocks.AIR);
               level.blockUpdated(pos.north().east(), Blocks.AIR);
               level.blockUpdated(pos.east(), Blocks.AIR);
            }
         } else if (level.getBlockState(pos.south()).isAir() & level.getBlockState(pos.south().east()).isAir() & level.getBlockState(pos.east()).isAir()) {
            level.setBlock(pos, (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST), 26);
            level.setBlock(
               pos.south(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH), 26
            );
            level.setBlock(
               pos.south().east(),
               (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST),
               26
            );
            level.setBlock(
               pos.east(), (BlockState)RUBlocks.GIANT_LILY_PAD.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH), 26
            );
            level.blockUpdated(pos, Blocks.AIR);
            level.blockUpdated(pos.east(), Blocks.AIR);
            level.blockUpdated(pos.south().east(), Blocks.AIR);
            level.blockUpdated(pos.south(), Blocks.AIR);
         }
      }
   }
}
