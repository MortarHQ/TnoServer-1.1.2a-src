package earth.terrarium.handcrafted.common.blocks;

import com.mojang.serialization.MapCodec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StackableBookBlock extends HorizontalDirectionalBlock {
   public static final MapCodec<StackableBookBlock> CODEC = simpleCodec(StackableBookBlock::new);
   public static final IntegerProperty BOOKS = IntegerProperty.create("books", 1, 4);
   public static final IntegerProperty SEED = IntegerProperty.create("seed", 1, 256);
   public static final VoxelShape SHAPE_1 = Block.box(3.0, 0.0, 2.0, 13.0, 4.0, 14.0);
   public static final VoxelShape SHAPE_2 = Shapes.join(Block.box(3.0, 0.0, 2.0, 13.0, 4.0, 14.0), Block.box(3.0, 4.0, 2.0, 13.0, 8.0, 14.0), BooleanOp.OR);
   public static final VoxelShape SHAPE_3 = Stream.of(
         Block.box(3.0, 0.0, 2.0, 13.0, 4.0, 14.0), Block.box(3.0, 4.0, 2.0, 13.0, 8.0, 14.0), Block.box(3.0, 8.0, 2.0, 13.0, 12.0, 14.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape SHAPE_4 = Stream.of(
         Block.box(3.0, 0.0, 2.0, 13.0, 4.0, 14.0),
         Block.box(3.0, 4.0, 2.0, 13.0, 8.0, 14.0),
         Block.box(3.0, 8.0, 2.0, 13.0, 12.0, 14.0),
         Block.box(3.0, 12.0, 2.0, 13.0, 16.0, 14.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();

   public StackableBookBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, Direction.NORTH)).setValue(BOOKS, 1)).setValue(SEED, 1)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, BOOKS, SEED});
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch (state.getValue(BOOKS)) {
         case 2 -> SHAPE_2;
         case 3 -> SHAPE_3;
         case 4 -> SHAPE_4;
         default -> SHAPE_1;
      };
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
      return !useContext.isSecondaryUseActive() && useContext.getItemInHand().is(this.asItem()) && (Integer)state.getValue(BOOKS) < 4
         || super.canBeReplaced(state, useContext);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState state = context.getLevel().getBlockState(context.getClickedPos());
      if (state.is(this)) {
         return (BlockState)state.setValue(BOOKS, Math.min(4, (Integer)state.getValue(BOOKS) + 1));
      }

      BlockState placementState = super.getStateForPlacement(context);
      if (placementState == null) {
         return null;
      }

      Random seededRandom = new Random(context.getClickedPos().asLong());
      return (BlockState)((BlockState)placementState.setValue(FACING, Direction.from2DDataValue(seededRandom.nextInt(4))))
         .setValue(SEED, seededRandom.nextInt(256) + 1);
   }

   public static String bookName(BlockState state) {
      return switch (state.getValue(BOOKS)) {
         case 2 -> "double";
         case 3 -> "triple";
         case 4 -> "quadruple";
         default -> "single";
      };
   }

   protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
      return false;
   }
}
