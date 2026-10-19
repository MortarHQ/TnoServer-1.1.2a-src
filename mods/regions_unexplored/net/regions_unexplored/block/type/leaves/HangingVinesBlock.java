package net.regions_unexplored.block.type.leaves;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.block.RUBlockUtils;
import net.regions_unexplored.world.level.block.state.properties.RuBlockStateProperties;

public class HangingVinesBlock extends Block implements BonemealableBlock {
   public static final MapCodec<HangingVinesBlock> CODEC = simpleCodec(HangingVinesBlock::new);
   private static final VoxelShape SHAPE_BASE = RUBlockUtils.column(14.0, 0.0, 16.0);
   private static final VoxelShape SHAPE_TIP = RUBlockUtils.column(14.0, 6.0, 16.0);
   public static final BooleanProperty TIP = RuBlockStateProperties.TIP;

   public MapCodec<HangingVinesBlock> codec() {
      return CODEC;
   }

   public HangingVinesBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(TIP, true));
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return state.getValue(TIP) ? SHAPE_TIP : SHAPE_BASE;
   }

   protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return this.canStayAtPosition(level, pos);
   }

   private boolean canStayAtPosition(BlockGetter level, BlockPos pos) {
      BlockPos neighbourPos = pos.relative(Direction.UP);
      BlockState blockState = level.getBlockState(neighbourPos);
      return MultifaceBlock.canAttachTo(level, Direction.UP, neighbourPos, blockState) || blockState.is(this);
   }

   protected BlockState updateShape(
      BlockState state, Direction directionToNeighbour, BlockState neighbourState, LevelAccessor level, BlockPos pos, BlockPos neighbourPos
   ) {
      if (!this.canStayAtPosition(level, pos)) {
         level.scheduleTick(pos, this, 1);
      }

      return (BlockState)state.setValue(TIP, !level.getBlockState(pos.below()).is(this));
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!this.canStayAtPosition(level, pos)) {
         level.destroyBlock(pos, true);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{TIP});
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      BlockPos growPos = this.getTip(level, pos).below();
      return this.canGrowInto(level.getBlockState(growPos)) && !level.isOutsideBuildHeight(growPos);
   }

   private boolean canGrowInto(BlockState state) {
      return state.isAir();
   }

   public BlockPos getTip(BlockGetter level, BlockPos pos) {
      MutableBlockPos forwardPos = pos.mutable();

      BlockState forwardState;
      do {
         forwardPos.move(Direction.DOWN);
         forwardState = level.getBlockState(forwardPos);
      } while (forwardState.is(this));

      return forwardPos.relative(Direction.UP).immutable();
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      BlockPos tipPos = this.getTip(level, pos).below();
      if (this.canGrowInto(level.getBlockState(tipPos))) {
         level.setBlockAndUpdate(tipPos, (BlockState)state.setValue(TIP, true));
      }
   }
}
