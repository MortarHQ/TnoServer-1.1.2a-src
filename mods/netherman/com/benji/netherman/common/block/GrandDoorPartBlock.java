package com.benji.netherman.common.block;

import com.benji.netherman.init.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GrandDoorPartBlock extends HorizontalDirectionalBlock {
   public static final MapCodec<GrandDoorPartBlock> CODEC = simpleCodec(GrandDoorPartBlock::new);
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   private static final VoxelShape SHAPE_NS = Block.box(0.0, 0.0, 3.0, 16.0, 16.0, 13.0);
   private static final VoxelShape SHAPE_EW = Block.box(3.0, 0.0, 0.0, 13.0, 16.0, 16.0);

   public GrandDoorPartBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(OPEN, false));
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, OPEN});
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if ((Boolean)state.getValue(OPEN)) {
         return Shapes.empty();
      } else {
         return ((Direction)state.getValue(FACING)).getAxis() == Axis.X ? SHAPE_EW : SHAPE_NS;
      }
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.INVISIBLE;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      for (int y = 0; y <= 11; y++) {
         for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
               BlockPos checkPos = pos.offset(x, -y, z);
               if (level.getBlockState(checkPos).is((Block)ModBlocks.GRAND_DOOR.get()) || level.getBlockState(checkPos).is((Block)ModBlocks.MAZE_DOOR.get())) {
                  return level.getBlockState(checkPos)
                     .useWithoutItem(level, player, new BlockHitResult(hit.getLocation(), hit.getDirection(), checkPos, hit.isInside()));
               }
            }
         }
      }

      return InteractionResult.PASS;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
   ) {
      for (int y = 0; y <= 11; y++) {
         for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
               BlockPos checkPos = pos.offset(x, -y, z);
               BlockState checkState = level.getBlockState(checkPos);
               if (checkState.is((Block)ModBlocks.GRAND_DOOR.get()) || checkState.is((Block)ModBlocks.MAZE_DOOR.get())) {
                  return checkState.useItemOn(stack, level, player, hand, new BlockHitResult(hit.getLocation(), hit.getDirection(), checkPos, hit.isInside()));
               }
            }
         }
      }

      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!state.is(newState.getBlock())) {
         for (int y = 0; y <= 11; y++) {
            for (int x = -5; x <= 5; x++) {
               for (int z = -5; z <= 5; z++) {
                  BlockPos checkPos = pos.offset(x, -y, z);
                  if (level.getBlockState(checkPos).is((Block)ModBlocks.GRAND_DOOR.get())) {
                     level.destroyBlock(checkPos, false);
                  }
               }
            }
         }

         super.onRemove(state, level, pos, newState, isMoving);
      }
   }
}
