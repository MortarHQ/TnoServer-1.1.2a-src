package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AbyssalKelpBlock extends GrowingPlantHeadBlock implements LiquidBlockContainer, AbyssalKelp {
   public static final MapCodec<AbyssalKelpBlock> CODEC = simpleCodec(AbyssalKelpBlock::new);
   protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

   public AbyssalKelpBlock(Properties properties) {
      super(properties, Direction.UP, SHAPE, true, 0.14);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(AGE, 0)).setValue(BERRIES, false));
   }

   public MapCodec<AbyssalKelpBlock> codec() {
      return CODEC;
   }

   protected Block getBodyBlock() {
      return (Block)ESBlocks.ABYSSAL_KELP_PLANT.get();
   }

   protected BlockState updateBodyAfterConvertedFromHead(BlockState state, BlockState blockState) {
      return (BlockState)blockState.setValue(BERRIES, (Boolean)state.getValue(BERRIES));
   }

   protected BlockState getGrowIntoState(BlockState state, RandomSource randomSource) {
      return (BlockState)super.getGrowIntoState(state, randomSource).setValue(BERRIES, randomSource.nextFloat() < 0.11F);
   }

   public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos pos, BlockState state) {
      return new ItemStack((ItemLike)ESItems.ABYSSAL_FRUIT.get());
   }

   protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
      return AbyssalKelp.use(blockState, level, blockPos);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{BERRIES});
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return !(Boolean)blockState.getValue(BERRIES);
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
      serverLevel.setBlock(pos, (BlockState)state.setValue(BERRIES, true), 2);
   }

   protected boolean canGrowInto(BlockState blockState) {
      return blockState.is(Blocks.WATER);
   }

   public boolean canAttachTo(BlockState blockState) {
      return !blockState.is(Blocks.MAGMA_BLOCK);
   }

   public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
      return false;
   }

   public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
      return false;
   }

   protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
      return 1;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
      FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
      return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 ? super.getStateForPlacement(blockPlaceContext) : null;
   }

   public FluidState getFluidState(BlockState blockState) {
      return Fluids.WATER.getSource(false);
   }
}
