package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class BinaryRotatedBlock extends Block {
   public static final MapCodec<BinaryRotatedBlock> CODEC = simpleCodec(BinaryRotatedBlock::new);
   public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");

   public BinaryRotatedBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<BinaryRotatedBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ROTATED});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(ROTATED, context.getHorizontalDirection().getAxis() == Axis.Z);
   }

   protected BlockState rotate(BlockState state, Rotation rotation) {
      if (rotation != Rotation.NONE && rotation != Rotation.CLOCKWISE_180) {
         boolean rotated = (Boolean)state.getValue(ROTATED);
         return (BlockState)state.setValue(ROTATED, !rotated);
      } else {
         return state;
      }
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      if (mirror == Mirror.NONE) {
         return state;
      }

      boolean rotated = (Boolean)state.getValue(ROTATED);
      return (BlockState)state.setValue(ROTATED, !rotated);
   }
}
