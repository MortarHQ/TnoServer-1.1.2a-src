package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class PolishedToxiteBlock extends Block {
   public static final MapCodec<PolishedToxiteBlock> CODEC = simpleCodec(PolishedToxiteBlock::new);
   public static final EnumProperty<PolishedToxiteBlock.Part> PART = EnumProperty.create("part", PolishedToxiteBlock.Part.class);

   public PolishedToxiteBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(PART, PolishedToxiteBlock.Part.FULL));
   }

   protected MapCodec<PolishedToxiteBlock> codec() {
      return CODEC;
   }

   protected BlockState updateShape(
      BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2
   ) {
      boolean above = levelAccessor.getBlockState(blockPos.above()).is(this);
      boolean below = levelAccessor.getBlockState(blockPos.below()).is(this);
      if (!above && !below) {
         return (BlockState)blockState.setValue(PART, PolishedToxiteBlock.Part.FULL);
      } else if (above && below) {
         return (BlockState)blockState.setValue(PART, PolishedToxiteBlock.Part.MIDDLE);
      } else {
         return above
            ? (BlockState)blockState.setValue(PART, PolishedToxiteBlock.Part.LOWER)
            : (BlockState)blockState.setValue(PART, PolishedToxiteBlock.Part.UPPER);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{PART});
   }

   public enum Part implements StringRepresentable {
      FULL("full"),
      UPPER("upper"),
      MIDDLE("middle"),
      LOWER("lower");

      private final String name;

      Part(String name) {
         this.name = name;
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
