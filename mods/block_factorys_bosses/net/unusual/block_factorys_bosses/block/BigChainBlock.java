package net.unusual.block_factorys_bosses.block;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BigChainBlock extends Block {
   private static final VoxelShape UNROTATED_BOX = Shapes.or(box(7.0, -2.0, 4.0, 9.0, 18.0, 12.0), box(7.0, 0.0, 6.0, 9.0, 16.0, 10.0));
   private static final VoxelShape ROTATED_BOX = Shapes.or(box(4.0, -2.0, 7.0, 12.0, 18.0, 9.0), box(6.0, 0.0, 7.0, 10.0, 16.0, 9.0));
   public static final IntegerProperty ROTATION = IntegerProperty.create("blockstate", 1, 2);
   public static final int UNROTATED = 1;
   public static final int ROTATED = 2;

   public BigChainBlock() {
      super(
         Properties.of()
            .sound(SoundType.HEAVY_CORE)
            .strength(1.2F, 10.0F)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .isRedstoneConductor((bs, br, bp) -> false)
      );
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return state.getValue(ROTATION) == 1 ? UNROTATED_BOX : ROTATED_BOX;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ROTATION});
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
      if (facing == Direction.UP && facingState.is(this)) {
         int rot = (Integer)state.getValue(ROTATION);
         if (((Integer)facingState.getValue(ROTATION)).equals(rot)) {
            return (BlockState)state.setValue(ROTATION, rot == 1 ? 2 : 1);
         }
      }

      return state;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      Level level = context.getLevel();
      int pref = 0;
      BlockState above = level.getBlockState(pos.above());
      if (above.is(this)) {
         pref = above.getValue(ROTATION) == 1 ? 2 : 1;
      }

      if (pref == 0) {
         BlockState below = level.getBlockState(pos.below());
         if (below.is(this)) {
            pref = below.getValue(ROTATION) == 1 ? 2 : 1;
         }
      }

      return pref != 0
         ? (BlockState)this.defaultBlockState().setValue(ROTATION, pref)
         : (BlockState)this.defaultBlockState().setValue(ROTATION, context.getHorizontalDirection().getAxis() == Axis.Z ? 2 : 1);
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return rot != Rotation.CLOCKWISE_180 && rot != Rotation.NONE ? (BlockState)state.setValue(ROTATION, state.getValue(ROTATION) == 2 ? 1 : 2) : state;
   }
}
