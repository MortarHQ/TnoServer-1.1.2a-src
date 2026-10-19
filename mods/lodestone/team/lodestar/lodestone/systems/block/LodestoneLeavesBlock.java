package team.lodestar.lodestone.systems.block;

import java.awt.Color;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import team.lodestar.lodestone.systems.easing.Easing;

public abstract class LodestoneLeavesBlock extends LeavesBlock {
   public final Color minColor;
   public final Color maxColor;

   public LodestoneLeavesBlock(Properties properties, Color minColor, Color maxColor) {
      super(properties);
      this.minColor = minColor;
      this.maxColor = maxColor;
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(this.getColorProperty(), 0));
   }

   public abstract IntegerProperty getColorProperty();

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{DISTANCE, PERSISTENT, WATERLOGGED, this.getColorProperty()});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)super.getStateForPlacement(context).setValue(this.getColorProperty(), 0);
   }

   public static void registerSimpleGradientColors(BlockColors blockColors, LodestoneLeavesBlock leavesBlock) {
      blockColors.register((s, l, p, c) -> {
         IntegerProperty colorProperty = leavesBlock.getColorProperty();
         float colorMax = colorProperty.getPossibleValues().size();
         float color = ((Integer)s.getValue(colorProperty)).intValue();
         float pct = colorMax - color / colorMax;
         float value = Easing.SINE_IN_OUT.ease(pct, 0.0F, 1.0F, 1.0F);
         int red = (int)Mth.lerp(value, leavesBlock.minColor.getRed(), leavesBlock.maxColor.getRed());
         int green = (int)Mth.lerp(value, leavesBlock.minColor.getGreen(), leavesBlock.maxColor.getGreen());
         int blue = (int)Mth.lerp(value, leavesBlock.minColor.getBlue(), leavesBlock.maxColor.getBlue());
         return red << 16 | green << 8 | blue;
      }, new Block[]{leavesBlock});
   }
}
