package net.jadenxgamer.netherexp.core.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WallGrowingWartBlock extends Block {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
   private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 16.0),
         Direction.SOUTH,
         Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 10.0),
         Direction.WEST,
         Block.box(6.0, 0.0, 0.0, 16.0, 16.0, 16.0),
         Direction.EAST,
         Block.box(0.0, 0.0, 0.0, 10.0, 16.0, 16.0)
      )
   );

   public WallGrowingWartBlock(Properties properties) {
      super(properties);
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
      return getShape(state);
   }

   public static VoxelShape getShape(BlockState state) {
      return SHAPES.get(state.getValue(FACING));
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      Direction direction = (Direction)state.getValue(FACING);
      BlockPos supportPos = pos.relative(direction.getOpposite());
      BlockState supportState = level.getBlockState(supportPos);
      return supportState.is(Blocks.SOUL_SAND) || supportState.is(net.jadenxgamer.elysium_api.api.tags.ElysiumTags.Blocks.NETHER_WART_PLANTABLE_ON);
   }

   public boolean isRandomlyTicking(BlockState state) {
      return (Integer)state.getValue(AGE) < 3;
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      int age = (Integer)state.getValue(AGE);
      if (age < 3 && random.nextInt(10) == 0) {
         state = (BlockState)state.setValue(AGE, age + 1);
         level.setBlock(pos, state, 2);
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState state = this.defaultBlockState();
      LevelReader levelReader = context.getLevel();
      BlockPos pos = context.getClickedPos();
      Direction[] directions = context.getNearestLookingDirections();

      for (Direction direction : directions) {
         if (direction.getAxis().isHorizontal()) {
            state = (BlockState)state.setValue(FACING, direction.getOpposite());
            if (state.canSurvive(levelReader, pos)) {
               return state;
            }
         }
      }

      return null;
   }

   @NotNull
   public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
   }

   @NotNull
   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   @NotNull
   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, AGE});
   }
}
