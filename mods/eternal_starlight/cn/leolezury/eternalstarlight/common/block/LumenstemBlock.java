package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LumenstemBlock extends GrowingPlantHeadBlock implements LiquidBlockContainer {
   public static final MapCodec<LumenstemBlock> CODEC = simpleCodec(LumenstemBlock::new);
   public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
   public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

   public LumenstemBlock(Properties properties) {
      super(properties, Direction.UP, SHAPE, false, 0.02);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(BOTTOM, true));
   }

   protected MapCodec<LumenstemBlock> codec() {
      return CODEC;
   }

   protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
      return 1;
   }

   protected boolean canGrowInto(BlockState state) {
      return state.is(Blocks.WATER);
   }

   protected Block getBodyBlock() {
      return (Block)ESBlocks.LUMENSTEM_PLANT.get();
   }

   public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
      return false;
   }

   public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
      return false;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState state = super.getStateForPlacement(context);
      if (state != null) {
         state = (BlockState)state.setValue(
            BOTTOM, !context.getLevel().getBlockState(context.getClickedPos().relative(this.growthDirection.getOpposite())).is(this.getBodyBlock())
         );
      }

      FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
      return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 ? state : null;
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      BlockState newState = super.updateShape(state, direction, neighborState, level, pos, neighborPos);
      return newState.hasProperty(BOTTOM)
         ? (BlockState)newState.setValue(BOTTOM, !level.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(this.getBodyBlock()))
         : newState;
   }

   public FluidState getFluidState(BlockState blockState) {
      return Fluids.WATER.getSource(false);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{BOTTOM});
   }
}
