package twilightforest.block;

import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.templates.VoidFluidHandler;
import twilightforest.block.entity.BrazierBlockEntity;
import twilightforest.enums.BrazierLight;
import twilightforest.init.TFBlockEntities;

public class BrazierBlock extends BaseEntityBlock {
   public static final MapCodec<BrazierBlock> CODEC = simpleCodec(BrazierBlock::new);
   private static final VoxelShape UPPER_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
   private static final VoxelShape LOWER_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
   public static final EnumProperty<BrazierLight> LIGHT = EnumProperty.create("light", BrazierLight.class);
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

   public BrazierBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(LIGHT, BrazierLight.OFF)).setValue(HALF, DoubleBlockHalf.LOWER)
      );
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return state.getValue(HALF) == DoubleBlockHalf.LOWER ? new BrazierBlockEntity(pos, state) : null;
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)TFBlockEntities.BRAZIER.get(), BrazierBlockEntity::tick);
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPE : LOWER_SHAPE;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LIGHT, HALF});
   }

   protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      DoubleBlockHalf half = (DoubleBlockHalf)state.getValue(HALF);
      if (facing.getAxis() == Axis.Y && half == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
         return facingState.getBlock() instanceof BrazierBlock && facingState.getValue(HALF) != half
            ? (BlockState)facingState.setValue(HALF, half)
            : Blocks.AIR.defaultBlockState();
      } else {
         return half == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
      }
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide && (player.isCreative() || !player.hasCorrectToolForDrops(state, level, pos))) {
         DoubleBlockHalf half = (DoubleBlockHalf)state.getValue(HALF);
         if (half == DoubleBlockHalf.UPPER) {
            BlockPos below = pos.below();
            BlockState blockstate = level.getBlockState(below);
            if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
               BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
               level.setBlock(below, blockstate1, 35);
               level.levelEvent(player, 2001, below, Block.getId(blockstate));
            }
         }
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos blockpos = context.getClickedPos();
      Level level = context.getLevel();
      return blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context)
         ? (BlockState)this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER)
         : null;
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      level.setBlock(pos.above(), (BlockState)state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos below = pos.below();
      BlockState blockstate = level.getBlockState(below);
      return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(level, below, Direction.UP) : blockstate.is(this);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result
   ) {
      if (state.is(this) && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
         if (state.getValue(LIGHT) != BrazierLight.FULL && (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE))) {
            level.setBlock(pos, (BlockState)state.cycle(LIGHT), 11);
            level.getBlockState(pos.below()).cycle(LIGHT);
            if (stack.is(Items.FLINT_AND_STEEL)) {
               stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            } else {
               stack.consume(1, player);
            }

            level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }

         if (((BrazierLight)state.getValue(LIGHT)).isLit()
            && FluidUtil.getFluidContained(stack).isPresent()
            && ((FluidStack)FluidUtil.getFluidContained(stack).get()).is(Fluids.WATER)) {
            if (FluidUtil.tryEmptyContainer(stack, new VoidFluidHandler(), 1000, player, true).isSuccess()) {
               level.setBlock(pos, (BlockState)state.setValue(LIGHT, BrazierLight.OFF), 11);
               level.getBlockState(pos.below()).setValue(LIGHT, BrazierLight.OFF);
               level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);
               player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
               return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }

            return ItemInteractionResult.FAIL;
         }
      }

      return super.useItemOn(stack, state, level, pos, player, hand, result);
   }
}
