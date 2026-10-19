package net.unusual.block_factorys_bosses.block;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ShipLanternBlock extends Block {
   private static final VoxelShape SHAPE = box(2.0, 0.0, 2.0, 14.0, 15.0, 14.0);

   public ShipLanternBlock() {
      super(Properties.of().sound(SoundType.LANTERN).strength(3.5F, 6.0F).lightLevel(state -> 14).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
      return 0;
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP, SupportType.CENTER);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
      return facing == Direction.DOWN && !this.canSurvive(state, level, pos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(state, facing, facingState, level, pos, facingPos);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return !this.canSurvive(this.defaultBlockState(), context.getLevel(), context.getClickedPos()) ? null : super.getStateForPlacement(context);
   }
}
