package earth.terrarium.handcrafted.common.blocks.misc;

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

public class StackableJarBlock extends SimpleBlock {
   public static final MapCodec<StackableJarBlock> CODEC = simpleCodec(StackableJarBlock::new);
   public static final IntegerProperty JARS = IntegerProperty.create("jars", 1, 3);
   public static final VoxelShape SHAPE_1 = Block.box(0.0, 0.0, 0.0, 9.0, 9.0, 9.0);
   public static final VoxelShape SHAPE_2 = Block.box(0.0, 0.0, 0.0, 9.0, 9.0, 9.0);
   public static final VoxelShape SHAPE_3 = Block.box(0.0, 0.0, 0.0, 9.0, 16.0, 9.0);

   public StackableJarBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, Direction.NORTH)).setValue(JARS, 1)).setValue(WATERLOGGED, false)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
      return !useContext.isSecondaryUseActive() && useContext.getItemInHand().is(this.asItem()) && (Integer)state.getValue(JARS) < 3
         || super.canBeReplaced(state, useContext);
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      VoxelShape shape = switch (state.getValue(JARS)) {
         case 2 -> SHAPE_2;
         case 3 -> SHAPE_3;
         default -> SHAPE_1;
      };
      return shape.move(0.21875, 0.0, 0.21875);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState state = context.getLevel().getBlockState(context.getClickedPos());
      if (state.is(this)) {
         return (BlockState)state.setValue(JARS, Math.min(3, (Integer)state.getValue(JARS) + 1));
      }

      FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
      BlockState placementState = super.getStateForPlacement(context);
      return placementState == null ? null : (BlockState)placementState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, JARS, WATERLOGGED});
   }
}
