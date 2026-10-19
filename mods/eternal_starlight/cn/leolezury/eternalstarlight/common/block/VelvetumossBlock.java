package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class VelvetumossBlock extends Block implements BonemealableBlock {
   public static final MapCodec<VelvetumossBlock> CODEC = simpleCodec(VelvetumossBlock::new);
   public static final BooleanProperty NORTH = PipeBlock.NORTH;
   public static final BooleanProperty EAST = PipeBlock.EAST;
   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
   public static final BooleanProperty WEST = PipeBlock.WEST;
   public static final BooleanProperty UP = PipeBlock.UP;
   public static final BooleanProperty DOWN = PipeBlock.DOWN;
   private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;

   public MapCodec<VelvetumossBlock> codec() {
      return CODEC;
   }

   public VelvetumossBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(NORTH, true))
                        .setValue(EAST, true))
                     .setValue(SOUTH, true))
                  .setValue(WEST, true))
               .setValue(UP, true))
            .setValue(DOWN, true)
      );
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockGetter blockgetter = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState()
                        .setValue(DOWN, !blockgetter.getBlockState(blockpos.below()).is(this)))
                     .setValue(UP, !blockgetter.getBlockState(blockpos.above()).is(this)))
                  .setValue(NORTH, !blockgetter.getBlockState(blockpos.north()).is(this)))
               .setValue(EAST, !blockgetter.getBlockState(blockpos.east()).is(this)))
            .setValue(SOUTH, !blockgetter.getBlockState(blockpos.south()).is(this)))
         .setValue(WEST, !blockgetter.getBlockState(blockpos.west()).is(this));
   }

   protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      return facingState.is(this)
         ? (BlockState)state.setValue((Property)PROPERTY_BY_DIRECTION.get(facing), false)
         : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
   }

   protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      if (randomSource.nextInt(12) == 0) {
         for (Direction direction : Direction.values()) {
            BlockPos growPos = blockPos.relative(direction);
            if (serverLevel.getBlockState(growPos).is(Blocks.WATER) && randomSource.nextInt(5) == 0) {
               serverLevel.setBlockAndUpdate(
                  growPos,
                  (BlockState)((BlockState)ESBlocks.VELVETUMOSS_VILLI.get().defaultBlockState().setValue(DirectionalBudBlock.WATERLOGGED, true))
                     .setValue(DirectionalBudBlock.FACING, direction)
               );
            }
         }
      }
   }

   public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
      super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
      if (!EnchantmentHelper.hasTag(itemStack, EnchantmentTags.PREVENTS_ICE_MELTING)) {
         if (level.dimensionType().ultraWarm()) {
            level.removeBlock(blockPos, false);
            return;
         }

         level.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
      }
   }

   protected BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)state.setValue(
                        (Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.NORTH)), (Boolean)state.getValue(NORTH)
                     ))
                     .setValue((Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.SOUTH)), (Boolean)state.getValue(SOUTH)))
                  .setValue((Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.EAST)), (Boolean)state.getValue(EAST)))
               .setValue((Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.WEST)), (Boolean)state.getValue(WEST)))
            .setValue((Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.UP)), (Boolean)state.getValue(UP)))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.DOWN)), (Boolean)state.getValue(DOWN));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)state.setValue(
                        (Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.NORTH)), (Boolean)state.getValue(NORTH)
                     ))
                     .setValue((Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.SOUTH)), (Boolean)state.getValue(SOUTH)))
                  .setValue((Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.EAST)), (Boolean)state.getValue(EAST)))
               .setValue((Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.WEST)), (Boolean)state.getValue(WEST)))
            .setValue((Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.UP)), (Boolean)state.getValue(UP)))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.DOWN)), (Boolean)state.getValue(DOWN));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{UP, DOWN, NORTH, EAST, SOUTH, WEST});
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return Arrays.stream(Direction.values()).anyMatch(direction -> levelReader.getBlockState(blockPos.relative(direction)).is(Blocks.WATER));
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      return Arrays.stream(Direction.values()).anyMatch(direction -> level.getBlockState(blockPos.relative(direction)).is(Blocks.WATER));
   }

   public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      for (Direction direction : Direction.values()) {
         BlockPos growPos = blockPos.relative(direction);
         if (serverLevel.getBlockState(growPos).is(Blocks.WATER) && randomSource.nextBoolean()) {
            serverLevel.setBlockAndUpdate(
               growPos,
               (BlockState)((BlockState)ESBlocks.VELVETUMOSS_VILLI.get().defaultBlockState().setValue(DirectionalBudBlock.WATERLOGGED, true))
                  .setValue(DirectionalBudBlock.FACING, direction)
            );
         }
      }
   }
}
