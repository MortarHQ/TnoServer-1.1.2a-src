package com.benji.netherman.common.block;

import com.benji.netherman.common.block.entity.GrandDoorBlockEntity;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModSounds;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GrandDoorBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   public static final MapCodec<GrandDoorBlock> CODEC = simpleCodec(GrandDoorBlock::new);
   private static final VoxelShape SHAPE_NS = Block.box(0.0, 0.0, 3.0, 16.0, 16.0, 13.0);
   private static final VoxelShape SHAPE_EW = Block.box(3.0, 0.0, 0.0, 13.0, 16.0, 16.0);

   public GrandDoorBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(OPEN, false));
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, OPEN});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction facing = context.getHorizontalDirection().getOpposite();
      Direction right = facing.getClockWise();
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();

      for (int w = 0; w < 6; w++) {
         for (int h = 0; h < 12; h++) {
            if (w != 0 || h != 0) {
               BlockPos p = pos.relative(right, w).above(h);
               if (!level.getBlockState(p).canBeReplaced(context)) {
                  return null;
               }
            }
         }
      }

      return (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, facing)).setValue(OPEN, false);
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      Direction right = ((Direction)state.getValue(FACING)).getClockWise();

      for (int w = 0; w < 6; w++) {
         for (int h = 0; h < 12; h++) {
            if (w != 0 || h != 0) {
               BlockPos p = pos.relative(right, w).above(h);
               level.setBlock(
                  p,
                  (BlockState)((BlockState)((Block)ModBlocks.GRAND_DOOR_PART.get()).defaultBlockState().setValue(FACING, (Direction)state.getValue(FACING)))
                     .setValue(OPEN, false),
                  3
               );
            }
         }
      }
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!state.is(newState.getBlock())) {
         Direction right = ((Direction)state.getValue(FACING)).getClockWise();

         for (int w = 0; w < 6; w++) {
            for (int h = 0; h < 12; h++) {
               if (w != 0 || h != 0) {
                  BlockPos p = pos.relative(right, w).above(h);
                  if (level.getBlockState(p).is((Block)ModBlocks.GRAND_DOOR_PART.get())) {
                     level.destroyBlock(p, false);
                  }
               }
            }
         }

         super.onRemove(state, level, pos, newState, isMoving);
      }
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if ((Boolean)state.getValue(OPEN)) {
         return Shapes.empty();
      } else {
         return ((Direction)state.getValue(FACING)).getAxis() == Axis.X ? SHAPE_EW : SHAPE_NS;
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      if (!level.isClientSide() && level.getBlockEntity(pos) instanceof GrandDoorBlockEntity door && door.getDoorState() == 0) {
         door.openTemporary();
         player.displayClientMessage(Component.literal("§cGood Luck traveler!"), true);
         level.playSound(null, pos, (SoundEvent)ModSounds.GOODLUCK.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
      }

      return InteractionResult.SUCCESS;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new GrandDoorBlockEntity(pos, state);
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return type == ModBlockEntities.GRAND_DOOR.get() ? (lvl, pos, st, entity) -> GrandDoorBlockEntity.tick(lvl, pos, st, (GrandDoorBlockEntity)entity) : null;
   }
}
