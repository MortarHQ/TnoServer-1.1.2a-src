package com.benji.netherman.common.block;

import com.benji.netherman.common.block.entity.GhastlyNestBlockEntity;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class GhastlyNestBlock extends BaseEntityBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty HAS_HONEY = BooleanProperty.create("has_honey");
   public static final MapCodec<GhastlyNestBlock> CODEC = simpleCodec(GhastlyNestBlock::new);

   public GhastlyNestBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(HAS_HONEY, false));
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, HAS_HONEY});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new GhastlyNestBlockEntity(pos, state);
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
   ) {
      if (stack.is(Items.GLASS_BOTTLE) && (Boolean)state.getValue(HAS_HONEY)) {
         if (!level.isClientSide()) {
            if (!player.getAbilities().instabuild) {
               stack.shrink(1);
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
            ItemStack honeyBottle = new ItemStack((ItemLike)ModItems.CRIMSON_HONEY_BOTTLE.get());
            if (stack.isEmpty()) {
               player.setItemInHand(hand, honeyBottle);
            } else if (!player.getInventory().add(honeyBottle)) {
               player.drop(honeyBottle, false);
            }

            level.setBlock(pos, (BlockState)state.setValue(HAS_HONEY, false), 3);
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hit);
      }
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
      return level.isClientSide
         ? null
         : createTickerHelper(blockEntityType, (BlockEntityType)ModBlockEntities.GHASTLY_NEST.get(), GhastlyNestBlockEntity::serverTick);
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!state.is(newState.getBlock())) {
         if (level.getBlockEntity(pos) instanceof GhastlyNestBlockEntity nest) {
            nest.releaseAllGhastlies();
         }

         super.onRemove(state, level, pos, newState, isMoving);
      }
   }
}
