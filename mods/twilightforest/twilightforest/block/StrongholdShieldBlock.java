package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import twilightforest.util.entities.EntityUtil;

public class StrongholdShieldBlock extends DirectionalBlock {
   public static final MapCodec<StrongholdShieldBlock> CODEC = simpleCodec(StrongholdShieldBlock::new);

   public StrongholdShieldBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.DOWN));
   }

   protected MapCodec<? extends DirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction facing = context.getNearestLookingDirection();
      if (facing.getAxis().equals(Axis.Y)) {
         facing = facing.getOpposite();
      }

      return (BlockState)this.defaultBlockState().setValue(FACING, facing);
   }

   public float getDestroyProgress(BlockState state, Player player, BlockGetter getter, BlockPos pos) {
      BlockHitResult ray = EntityUtil.rayTrace(player, range -> range + 1.0);
      Direction hitFace = ray.getDirection();
      boolean upOrDown = state.getValue(DirectionalBlock.FACING) == Direction.UP || state.getValue(DirectionalBlock.FACING) == Direction.DOWN;
      Direction sideFace = ((Direction)state.getValue(DirectionalBlock.FACING)).getOpposite();
      Direction upFace = (Direction)state.getValue(DirectionalBlock.FACING);
      return hitFace == (upOrDown ? upFace : sideFace)
         ? player.getDigSpeed(Blocks.STONE.defaultBlockState(), pos) / 1.5F / 100.0F
         : super.getDestroyProgress(state, player, getter, pos);
   }

   public boolean canEntityDestroy(BlockState state, BlockGetter getter, BlockPos pos, Entity entity) {
      return false;
   }
}
