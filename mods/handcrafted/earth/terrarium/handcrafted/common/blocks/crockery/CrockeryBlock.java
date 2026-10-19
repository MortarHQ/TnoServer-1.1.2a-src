package earth.terrarium.handcrafted.common.blocks.crockery;

import com.mojang.serialization.MapCodec;
import earth.terrarium.handcrafted.common.blocks.base.SimpleBlock;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrockeryBlock extends SimpleBlock {
   public static final MapCodec<CrockeryBlock> CODEC = simpleCodec(CrockeryBlock::new);
   public static final IntegerProperty PIECES = IntegerProperty.create("pieces", 1, 6);
   public static final VoxelShape SHAPE_1 = Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
   public static final VoxelShape SHAPE_2 = Block.box(3.0, 0.0, 3.0, 13.0, 2.0, 13.0);
   public static final VoxelShape SHAPE_3 = Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0);
   public static final VoxelShape SHAPE_4 = Block.box(3.0, 0.0, 3.0, 13.0, 4.0, 13.0);
   public static final VoxelShape SHAPE_5 = Block.box(3.0, 0.0, 3.0, 13.0, 5.0, 13.0);
   public static final VoxelShape SHAPE_6 = Block.box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);

   public CrockeryBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(PIECES, 1)).setValue(FACING, Direction.NORTH)).setValue(WATERLOGGED, false)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, PIECES, WATERLOGGED});
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
      return !useContext.isSecondaryUseActive() && useContext.getItemInHand().is(this.asItem()) && (Integer)state.getValue(PIECES) < 6
         || super.canBeReplaced(state, useContext);
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch (state.getValue(PIECES)) {
         case 2 -> SHAPE_2;
         case 3 -> SHAPE_3;
         case 4 -> SHAPE_4;
         case 5 -> SHAPE_5;
         case 6 -> SHAPE_6;
         default -> SHAPE_1;
      };
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState state = context.getLevel().getBlockState(context.getClickedPos());
      if (state.is(this)) {
         return (BlockState)state.setValue(PIECES, Math.min(6, (Integer)state.getValue(PIECES) + 1));
      }

      FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
      boolean bl = fluidState.getType() == Fluids.WATER;
      BlockState placementState = super.getStateForPlacement(context);
      return placementState == null ? null : (BlockState)placementState.setValue(WATERLOGGED, bl);
   }

   public static String piecesName(BlockState state) {
      return switch (state.getValue(PIECES)) {
         case 2 -> "double";
         case 3 -> "triple";
         case 4 -> "quadruple";
         case 5 -> "quintuple";
         case 6 -> "sextuple";
         default -> "single";
      };
   }
}
