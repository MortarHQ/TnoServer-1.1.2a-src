package net.regions_unexplored.world.level.block.plant.other;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.RUBlocks;

public class KapokVinesPlantBlock extends GrowingPlantBodyBlock {
   public static final MapCodec<? extends KapokVinesPlantBlock> CODEC = simpleCodec(KapokVinesPlantBlock::new);
   public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public KapokVinesPlantBlock(Properties properties) {
      super(properties, Direction.DOWN, SHAPE, false);
   }

   protected GrowingPlantHeadBlock getHeadBlock() {
      return (GrowingPlantHeadBlock)RUBlocks.KAPOK_VINES.get();
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockPos = pos.relative(this.growthDirection.getOpposite());
      BlockState blockState = level.getBlockState(blockPos);
      return !this.canAttachTo(blockState)
         ? false
         : blockState.is(this.getHeadBlock()) || blockState.is(this.getBodyBlock()) || blockState.is(BlockTags.LOGS) || blockState.is(BlockTags.LEAVES);
   }

   protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
      return CODEC;
   }
}
