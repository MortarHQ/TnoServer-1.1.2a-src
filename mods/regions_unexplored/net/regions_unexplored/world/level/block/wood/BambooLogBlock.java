package net.regions_unexplored.world.level.block.wood;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.world.level.block.state.properties.RuBlockStateProperties;

public class BambooLogBlock extends Block implements BonemealableBlock, SimpleWaterloggedBlock {
   public static final BooleanProperty LEAVES = RuBlockStateProperties.LEAVES;
   public static final EnumProperty<Axis> AXIS = BlockStateProperties.AXIS;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public BambooLogBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(AXIS, Axis.Y)).setValue(LEAVES, false))
            .setValue(WATERLOGGED, false)
      );
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public int getLightBlock(BlockState state, BlockGetter getter, BlockPos pos) {
      return 0;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return switch ((Axis)state.getValue(AXIS)) {
         case X -> box(0.0, 4.0, 4.0, 16.0, 12.0, 12.0);
         case Y -> box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
         case Z -> box(4.0, 4.0, 0.0, 12.0, 12.0, 16.0);
         default -> throw new MatchException(null, null);
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AXIS, LEAVES, WATERLOGGED});
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return state;
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return !(Boolean)state.getValue(LEAVES);
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      level.setBlock(pos, (BlockState)state.setValue(LEAVES, true), 2);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
      return (BlockState)((BlockState)this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()))
         .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) {
         if (state.getValue(AXIS) == Axis.X) {
            return (BlockState)state.setValue(AXIS, Axis.Z);
         }

         if (state.getValue(AXIS) == Axis.Z) {
            return (BlockState)state.setValue(AXIS, Axis.X);
         }
      }

      return state;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult
   ) {
      if (playerHasShieldUseIntent(player, interactionHand)) {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }

      if (stack.getItem() instanceof AxeItem) {
         BlockState newBlockState = this.evaluateStrippedState(level, pos, player, state);
         if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, stack);
         }

         level.setBlock(pos, newBlockState, 11);
         level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, newBlockState));
         stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else if (stack.getItem() instanceof HoeItem && (Boolean)state.getValue(LEAVES)) {
         BlockState newBlockState = this.evaluateTilledState(level, pos, player, state);
         if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, stack);
         }

         level.setBlock(pos, newBlockState, 11);
         level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, newBlockState));
         stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
   }

   private BlockState evaluateStrippedState(Level level, BlockPos pos, @Nullable Player player, BlockState state) {
      boolean isWaterlogged = (Boolean)state.getValue(WATERLOGGED);
      Axis direction = (Axis)state.getValue(AXIS);
      BlockState strippedState = (BlockState)((BlockState)RUBlocks.STRIPPED_BAMBOO_LOG.get().defaultBlockState().setValue(AXIS, direction))
         .setValue(WATERLOGGED, isWaterlogged);
      level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
      return strippedState;
   }

   private BlockState evaluateTilledState(Level level, BlockPos pos, @Nullable Player player, BlockState state) {
      boolean isWaterlogged = (Boolean)state.getValue(WATERLOGGED);
      Axis direction = (Axis)state.getValue(AXIS);
      BlockState tilledState = (BlockState)((BlockState)((BlockState)RUBlocks.BAMBOO_LOG.get().defaultBlockState().setValue(LEAVES, false))
            .setValue(AXIS, direction))
         .setValue(WATERLOGGED, isWaterlogged);
      level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
      return tilledState;
   }

   private static boolean playerHasShieldUseIntent(Player player, InteractionHand interactionHand) {
      return interactionHand.equals(InteractionHand.MAIN_HAND) && player.getOffhandItem().is(Items.SHIELD) && !player.isSecondaryUseActive();
   }
}
