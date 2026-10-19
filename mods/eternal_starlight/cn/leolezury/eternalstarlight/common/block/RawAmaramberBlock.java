package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class RawAmaramberBlock extends Block {
   public static final MapCodec<RawAmaramberBlock> CODEC = simpleCodec(RawAmaramberBlock::new);
   public static final BooleanProperty TOP = BooleanProperty.create("top");

   public RawAmaramberBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(TOP, true));
   }

   protected MapCodec<RawAmaramberBlock> codec() {
      return CODEC;
   }

   protected BlockState updateShape(
      BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2
   ) {
      return (BlockState)blockState.setValue(TOP, !levelAccessor.getBlockState(blockPos.above()).is(this));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{TOP});
   }
}
