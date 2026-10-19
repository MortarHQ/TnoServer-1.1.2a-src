package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WarpedWartBlock extends BushBlock {
   public static final MapCodec<WarpedWartBlock> CODEC = simpleCodec(WarpedWartBlock::new);
   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
   public static final VoxelShape AGE_0 = Block.box(5.0, 7.0, 5.0, 11.0, 16.0, 11.0);
   public static final VoxelShape AGE_1 = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
   public static final VoxelShape AGE_2 = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
   public static final VoxelShape AGE_3_TOP = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
   public static final VoxelShape AGE_3_BOTTOM = Block.box(2.0, 5.0, 2.0, 14.0, 16.0, 14.0);

   public WarpedWartBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(AGE, 0)).setValue(HALF, DoubleBlockHalf.UPPER));
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch (state.getValue(AGE)) {
         case 1 -> AGE_1;
         case 2 -> AGE_2;
         case 3 -> state.getValue(HALF) == DoubleBlockHalf.LOWER ? AGE_3_BOTTOM : AGE_3_TOP;
         default -> AGE_0;
      };
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockState above = level.getBlockState(pos.above());
      return state.getValue(HALF) != DoubleBlockHalf.LOWER ? this.canPlantBelow(above) : above.is(this) && above.getValue(HALF) == DoubleBlockHalf.UPPER;
   }

   protected boolean canPlantBelow(BlockState ceiling) {
      return ceiling.is(Blocks.SOUL_SAND) || ceiling.is(net.jadenxgamer.elysium_api.api.tags.ElysiumTags.Blocks.NETHER_WART_PLANTABLE_ON);
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      DoubleBlockHalf half = (DoubleBlockHalf)state.getValue(HALF);
      if (half == DoubleBlockHalf.UPPER && direction == Direction.DOWN) {
         if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.LOWER) {
            return Blocks.AIR.defaultBlockState();
         }
      } else if (half == DoubleBlockHalf.LOWER
         && direction == Direction.UP
         && (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.UPPER)) {
         return Blocks.AIR.defaultBlockState();
      }

      return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (random.nextInt(10) == 0) {
         int age = (Integer)state.getValue(AGE);
         level.setBlock(pos, (BlockState)state.setValue(AGE, age + 1), 3);
         if (age == 2) {
            level.setBlock(pos.below(), (BlockState)((BlockState)state.setValue(AGE, age + 1)).setValue(HALF, DoubleBlockHalf.LOWER), 3);
         }
      }
   }

   public boolean isRandomlyTicking(BlockState state) {
      return (Integer)state.getValue(AGE) < 3;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AGE, HALF});
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }
}
