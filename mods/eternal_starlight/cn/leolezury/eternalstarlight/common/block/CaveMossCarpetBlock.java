package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class CaveMossCarpetBlock extends CarpetBlock {
   public static final MapCodec<CaveMossCarpetBlock> CODEC = simpleCodec(CaveMossCarpetBlock::new);
   public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");

   public MapCodec<CaveMossCarpetBlock> codec() {
      return CODEC;
   }

   public CaveMossCarpetBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(BOTTOM, false));
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState state = super.getStateForPlacement(context);
      if (state == null) {
         return null;
      }

      boolean sturdy = true;
      Level level = context.getLevel();
      BlockPos blockPos = context.getClickedPos();

      for (Direction dir : Arrays.stream(Direction.values()).filter(dirx -> dirx.getAxis() != Axis.Y).toList()) {
         sturdy &= level.getBlockState(blockPos.below()).isFaceSturdy(level, blockPos.below(), dir);
      }

      return (BlockState)state.setValue(
         BOTTOM, !level.getBlockState(blockPos.below()).is(this) && !level.getBlockState(blockPos.below()).is(ESBlocks.CAVE_MOSS_BLOCK.get()) && sturdy
      );
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos blockPos, BlockPos blockPos2) {
      boolean sturdy = true;

      for (Direction dir : Arrays.stream(Direction.values()).filter(dirx -> dirx.getAxis() != Axis.Y).toList()) {
         sturdy &= level.getBlockState(blockPos.below()).isFaceSturdy(level, blockPos.below(), dir);
      }

      return super.updateShape(
         (BlockState)state.setValue(
            BOTTOM, !level.getBlockState(blockPos.below()).is(this) && !level.getBlockState(blockPos.below()).is(ESBlocks.CAVE_MOSS_BLOCK.get()) && sturdy
         ),
         direction,
         blockState2,
         level,
         blockPos,
         blockPos2
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{BOTTOM});
   }
}
