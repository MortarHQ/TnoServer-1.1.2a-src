package team.lodestar.lodestone.systems.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;

public class LodestoneDirectionalBlock extends DirectionalBlock {
   public static final MapCodec<LodestoneDirectionalBlock> CODEC = simpleCodec(LodestoneDirectionalBlock::new);

   public LodestoneDirectionalBlock(Properties builder) {
      super(builder);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<? extends DirectionalBlock> codec() {
      return CODEC;
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction direction = context.getNearestLookingDirection();
      if (direction.getAxis().isHorizontal()) {
         direction = direction.getOpposite();
      }

      return context.getPlayer() != null && context.getPlayer().isShiftKeyDown()
         ? (BlockState)this.defaultBlockState().setValue(FACING, direction)
         : (BlockState)this.defaultBlockState().setValue(FACING, direction.getOpposite());
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return (BlockState)state.setValue(FACING, mirrorIn.mirror((Direction)state.getValue(FACING)));
   }
}
