package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SpiralKelpBlock extends GrowingPlantHeadBlock implements LiquidBlockContainer {
   public static final MapCodec<SpiralKelpBlock> CODEC = simpleCodec(SpiralKelpBlock::new);
   public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

   public SpiralKelpBlock(Properties properties) {
      super(properties, Direction.UP, SHAPE, false, 0.02);
   }

   protected MapCodec<SpiralKelpBlock> codec() {
      return CODEC;
   }

   protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
      return 1;
   }

   protected boolean canGrowInto(BlockState state) {
      return state.is(Blocks.WATER);
   }

   protected Block getBodyBlock() {
      return (Block)ESBlocks.SPIRAL_KELP_PLANT.get();
   }

   public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
      return false;
   }

   public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
      return false;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
      FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
      return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 ? super.getStateForPlacement(blockPlaceContext) : null;
   }

   public FluidState getFluidState(BlockState blockState) {
      return Fluids.WATER.getSource(false);
   }

   public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
      super.animateTick(blockState, level, blockPos, randomSource);
      if (randomSource.nextInt(10) == 0) {
         BlockPos blockPos2 = blockPos.below();
         BlockState blockState2 = level.getBlockState(blockPos2);
         if (!isFaceFull(blockState2.getCollisionShape(level, blockPos2), Direction.UP)) {
            ParticleUtils.spawnParticleBelow(level, blockPos, randomSource, (ParticleOptions)ESParticles.SPIRAL_KELP_LEAVES.get());
         }
      }
   }
}
