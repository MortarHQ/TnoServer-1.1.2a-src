package com.benji.netherman.common.block;

import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import com.mojang.serialization.MapCodec;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
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
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class StatueStandBlock extends HorizontalDirectionalBlock {
   public static final MapCodec<StatueStandBlock> CODEC = simpleCodec(StatueStandBlock::new);
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
   public static final BooleanProperty SOLVED = BooleanProperty.create("solved");
   private static final VoxelShape SHAPE_LOWER = Shapes.or(
      Block.box(0.0, 0.0, 0.0, 16.0, 7.0, 16.0), new VoxelShape[]{Block.box(2.0, 7.0, 2.0, 14.0, 12.0, 14.0), Block.box(5.0, 12.0, 5.0, 11.0, 23.0, 11.0)}
   );
   private static final VoxelShape SHAPE_UPPER = Shapes.or(Block.box(3.0, 0.0, 3.0, 13.0, 11.0, 13.0), Block.box(5.0, 11.0, 5.0, 11.0, 23.0, 11.0));
   private static final VoxelShape SHAPE_UPPER_NORTH = Shapes.or(SHAPE_UPPER, Block.box(3.0, 11.0, 3.0, 13.0, 25.0, 7.0));
   private static final VoxelShape SHAPE_UPPER_EAST = Shapes.or(SHAPE_UPPER, Block.box(9.0, 11.0, 3.0, 13.0, 25.0, 13.0));
   private static final VoxelShape SHAPE_UPPER_SOUTH = Shapes.or(SHAPE_UPPER, Block.box(3.0, 11.0, 9.0, 13.0, 25.0, 13.0));
   private static final VoxelShape SHAPE_UPPER_WEST = Shapes.or(SHAPE_UPPER, Block.box(3.0, 11.0, 3.0, 7.0, 25.0, 13.0));

   public StatueStandBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH))
               .setValue(HALF, DoubleBlockHalf.LOWER))
            .setValue(SOLVED, false)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, HALF, SOLVED});
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      Level level = context.getLevel();
      return pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)
         ? (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())).setValue(SOLVED, false)
         : null;
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      level.setBlock(pos.above(), (BlockState)((BlockState)state.setValue(HALF, DoubleBlockHalf.UPPER)).setValue(SOLVED, false), 3);
      if (!level.isClientSide()) {
         int radius = 10;
         boolean isPuzzleZone = false;

         for (BlockPos checkPos : BlockPos.betweenClosed(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius))) {
            if (level.getBlockState(checkPos).is((Block)ModBlocks.MAZE_DOOR.get())) {
               isPuzzleZone = true;
               break;
            }
         }

         if (isPuzzleZone) {
            List<BlockPos> allStatuesInRoom = new ArrayList<>();
            boolean hasSolvedStatue = false;

            for (BlockPos checkPos : BlockPos.betweenClosed(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius))) {
               BlockState st = level.getBlockState(checkPos);
               if (st.is(this) && st.getValue(HALF) == DoubleBlockHalf.LOWER) {
                  allStatuesInRoom.add(checkPos.immutable());
                  if ((Boolean)st.getValue(SOLVED)) {
                     hasSolvedStatue = true;
                  }
               }
            }

            if (hasSolvedStatue || allStatuesInRoom.size() < 2) {
               return;
            }

            boolean allPaired = true;

            for (BlockPos statPos : allStatuesInRoom) {
               BlockState statState = level.getBlockState(statPos);
               Direction facing = (Direction)statState.getValue(FACING);
               boolean hasPartner = false;

               for (int i = 1; i <= 10; i++) {
                  BlockPos targetPos = statPos.relative(facing, i);
                  BlockState targetState = level.getBlockState(targetPos);
                  if (targetState.is(this) && targetState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                     if (targetState.getValue(FACING) == facing.getOpposite()) {
                        hasPartner = true;
                     }
                     break;
                  }

                  if (targetState.isSolidRender(level, targetPos)) {
                     break;
                  }
               }

               if (!hasPartner) {
                  allPaired = false;
                  break;
               }
            }

            if (allPaired) {
               level.playSound(null, pos, (SoundEvent)ModSounds.GIANT_BELL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
               ItemEntity key = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, new ItemStack((ItemLike)ModItems.MAZE_KEY.get()));
               level.addFreshEntity(key);

               for (BlockPos statPos : allStatuesInRoom) {
                  BlockState statState = level.getBlockState(statPos);
                  level.setBlock(statPos, (BlockState)statState.setValue(SOLVED, true), 3);
                  level.setBlock(statPos.above(), (BlockState)((BlockState)statState.setValue(HALF, DoubleBlockHalf.UPPER)).setValue(SOLVED, true), 3);
                  if (level instanceof ServerLevel serverLevel) {
                     serverLevel.sendParticles(
                        ParticleTypes.TOTEM_OF_UNDYING, statPos.getX() + 0.5, statPos.getY() + 1.0, statPos.getZ() + 0.5, 30, 0.5, 0.5, 0.5, 0.1
                     );
                  }
               }
            }
         }
      }
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      DoubleBlockHalf half = (DoubleBlockHalf)state.getValue(HALF);
      if (facing.getAxis() == Axis.Y && half == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
         return facingState.is(this) && facingState.getValue(HALF) != half ? state : Blocks.AIR.defaultBlockState();
      } else {
         return half == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
      }
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide()) {
         DoubleBlockHalf half = (DoubleBlockHalf)state.getValue(HALF);
         if (half == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
               if (player.isCreative()) {
                  level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                  level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
               } else {
                  level.destroyBlock(blockpos, true);
               }
            }
         }
      }

      super.playerWillDestroy(level, pos, state, player);
      return state;
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
         return super.canSurvive(state, level, pos);
      }

      BlockState blockstate = level.getBlockState(pos.below());
      return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      VoxelShape var10000;
      if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
         var10000 = SHAPE_LOWER;
      } else {
         switch ((Direction)state.getValue(FACING)) {
            case NORTH:
            case UP:
            case DOWN:
               var10000 = SHAPE_UPPER_NORTH;
               break;
            case SOUTH:
               var10000 = SHAPE_UPPER_SOUTH;
               break;
            case WEST:
               var10000 = SHAPE_UPPER_WEST;
               break;
            case EAST:
               var10000 = SHAPE_UPPER_EAST;
               break;
            default:
               throw new MatchException(null, null);
         }
      }

      return var10000;
   }
}
