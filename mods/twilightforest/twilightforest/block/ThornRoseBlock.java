package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.init.TFBlocks;

public class ThornRoseBlock extends BushBlock {
   public static final MapCodec<ThornRoseBlock> CODEC = simpleCodec(ThornRoseBlock::new);
   private static final float RADIUS = 0.4F;
   private static final VoxelShape AABB = Shapes.create(new AABB(0.099999994F, 0.099999994F, 0.099999994F, 0.9F, 0.9F, 0.9F));

   public ThornRoseBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(DirectionalBlock.FACING, Direction.UP));
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockState blockstate = level.getBlockState(pos.relative(((Direction)state.getValue(DirectionalBlock.FACING)).getOpposite()));
      return blockstate.is(TFBlocks.BROWN_THORNS)
         || blockstate.is(TFBlocks.GREEN_THORNS)
         || blockstate.isFaceSturdy(level, pos, (Direction)state.getValue(DirectionalBlock.FACING));
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return AABB;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{DirectionalBlock.FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(DirectionalBlock.FACING, context.getClickedFace());
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(DirectionalBlock.FACING, rotation.rotate((Direction)state.getValue(DirectionalBlock.FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(DirectionalBlock.FACING)));
   }
}
