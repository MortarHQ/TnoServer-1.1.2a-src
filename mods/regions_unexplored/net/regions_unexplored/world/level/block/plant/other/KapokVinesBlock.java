package net.regions_unexplored.world.level.block.plant.other;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.RUBlocks;

public class KapokVinesBlock extends GrowingPlantHeadBlock {
   public static final MapCodec<? extends KapokVinesBlock> CODEC = simpleCodec(KapokVinesBlock::new);
   protected static final VoxelShape SHAPE = Block.box(3.0, 3.0, 3.0, 13.0, 16.0, 13.0);

   public KapokVinesBlock(Properties properties) {
      super(properties, Direction.DOWN, SHAPE, false, 0.1);
   }

   protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
      return CODEC;
   }

   protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
      return NetherVines.getBlocksToGrowWhenBonemealed(random);
   }

   protected Block getBodyBlock() {
      return RUBlocks.KAPOK_VINES_PLANT.get();
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockPos = pos.relative(this.growthDirection.getOpposite());
      BlockState blockState = level.getBlockState(blockPos);
      return !this.canAttachTo(blockState)
         ? false
         : blockState.is(this.getHeadBlock()) || blockState.is(this.getBodyBlock()) || blockState.is(BlockTags.LOGS) || blockState.is(BlockTags.LEAVES);
   }

   protected boolean canGrowInto(BlockState state) {
      return NetherVines.isValidGrowthState(state);
   }
}
