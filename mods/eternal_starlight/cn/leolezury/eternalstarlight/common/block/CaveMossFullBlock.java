package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.data.ESConfiguredFeatures;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BonemealableBlock.Type;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class CaveMossFullBlock extends Block implements BonemealableBlock {
   public static final MapCodec<CaveMossFullBlock> CODEC = simpleCodec(CaveMossFullBlock::new);
   public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");

   public MapCodec<CaveMossFullBlock> codec() {
      return CODEC;
   }

   public CaveMossFullBlock(Properties properties) {
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

      return (BlockState)state.setValue(BOTTOM, !level.getBlockState(blockPos.below()).is(this) && sturdy);
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos blockPos, BlockPos blockPos2) {
      boolean sturdy = true;

      for (Direction dir : Arrays.stream(Direction.values()).filter(dirx -> dirx.getAxis() != Axis.Y).toList()) {
         sturdy &= level.getBlockState(blockPos.below()).isFaceSturdy(level, blockPos.below(), dir);
      }

      return (BlockState)state.setValue(BOTTOM, !level.getBlockState(blockPos.below()).is(this) && sturdy);
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return levelReader.getBlockState(blockPos.above()).isAir();
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      return true;
   }

   public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      serverLevel.registryAccess()
         .registry(Registries.CONFIGURED_FEATURE)
         .flatMap(registry -> registry.getHolder(ESConfiguredFeatures.CAVE_MOSS_PATCH_BONEMEAL))
         .ifPresent(
            reference -> ((ConfiguredFeature)reference.value()).place(serverLevel, serverLevel.getChunkSource().getGenerator(), randomSource, blockPos.above())
         );
   }

   public Type getType() {
      return Type.NEIGHBOR_SPREADER;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{BOTTOM});
   }
}
