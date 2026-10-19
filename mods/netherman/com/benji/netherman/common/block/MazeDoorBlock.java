package com.benji.netherman.common.block;

import com.benji.netherman.common.block.entity.MazeDoorBlockEntity;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MazeDoorBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   public static final MapCodec<MazeDoorBlock> CODEC = simpleCodec(MazeDoorBlock::new);

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public MazeDoorBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(OPEN, false));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, OPEN});
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      super.setPlacedBy(level, pos, state, placer, stack);
      Direction right = ((Direction)state.getValue(FACING)).getClockWise();

      for (int w = 0; w < 6; w++) {
         for (int h = 0; h < 5; h++) {
            if (w != 0 || h != 0) {
               BlockPos partPos = pos.relative(right, w).above(h);
               if (level.getBlockState(partPos).canBeReplaced()) {
                  level.setBlock(
                     partPos,
                     (BlockState)((BlockState)((Block)ModBlocks.GRAND_DOOR_PART.get()).defaultBlockState().setValue(FACING, (Direction)state.getValue(FACING)))
                        .setValue(OPEN, false),
                     3
                  );
               }
            }
         }
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
   ) {
      if (level.getBlockEntity(pos) instanceof MazeDoorBlockEntity entity) {
         if (entity.getDoorState() != 0) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
         }

         if (!level.isClientSide()) {
            boolean foundPuzzle = false;
            int scanRadius = 7;

            for (BlockPos checkPos : BlockPos.betweenClosed(pos.offset(-scanRadius, -scanRadius, -scanRadius), pos.offset(scanRadius, scanRadius, scanRadius))) {
               BlockState checkState = level.getBlockState(checkPos);
               if (checkState.is((Block)ModBlocks.STATUE_STAND.get())
                  || checkState.is((Block)ModBlocks.FACE_PUZZLE_LEFT_DOWN.get())
                  || checkState.is((Block)ModBlocks.TOTEMUS_HOLE.get())) {
                  foundPuzzle = true;
                  break;
               }
            }

            entity.syncRequiresKey(foundPuzzle);
            if (foundPuzzle) {
               if (stack.is((Item)ModItems.MAZE_KEY.get())) {
                  stack.shrink(1);
                  level.playSound(null, pos, (SoundEvent)ModSounds.HIRRING.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                  entity.openTemporary();
               } else {
                  level.playSound(null, pos, SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS, 1.0F, 0.5F);
                  player.displayClientMessage(Component.translatable("message.netherman.door_locked"), true);
               }
            } else {
               entity.openTemporary();
            }
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!state.is(newState.getBlock())) {
         Direction right = ((Direction)state.getValue(FACING)).getClockWise();

         for (int w = 0; w < 6; w++) {
            for (int h = 0; h < 5; h++) {
               if (w != 0 || h != 0) {
                  BlockPos partPos = pos.relative(right, w).above(h);
                  if (level.getBlockState(partPos).is((Block)ModBlocks.GRAND_DOOR_PART.get())) {
                     level.setBlock(partPos, Blocks.AIR.defaultBlockState(), 3);
                  }
               }
            }
         }

         super.onRemove(state, level, pos, newState, isMoving);
      }
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new MazeDoorBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return type == ModBlockEntities.MAZE_DOOR.get() ? (lvl, p, st, be) -> MazeDoorBlockEntity.tick(lvl, p, st, (MazeDoorBlockEntity)be) : null;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }
}
