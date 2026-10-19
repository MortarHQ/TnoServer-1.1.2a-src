package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class ShadegrieveBlock extends Block {
   public static final MapCodec<ShadegrieveBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.BOOL.fieldOf("particles").forGetter(block -> block.particles), propertiesCodec()).apply(instance, ShadegrieveBlock::new)
   );
   public static final BooleanProperty TOP = BooleanProperty.create("top");
   private final boolean particles;

   public ShadegrieveBlock(boolean particles, Properties properties) {
      super(properties);
      this.particles = particles;
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(TOP, false));
   }

   protected MapCodec<ShadegrieveBlock> codec() {
      return CODEC;
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
         sturdy &= level.getBlockState(blockPos.above()).isFaceSturdy(level, blockPos.above(), dir);
      }

      return (BlockState)state.setValue(TOP, !level.getBlockState(blockPos.above()).is(this) && sturdy);
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos blockPos, BlockPos blockPos2) {
      boolean sturdy = true;

      for (Direction dir : Arrays.stream(Direction.values()).filter(dirx -> dirx.getAxis() != Axis.Y).toList()) {
         sturdy &= level.getBlockState(blockPos.above()).isFaceSturdy(level, blockPos.above(), dir);
      }

      return (BlockState)state.setValue(TOP, !level.getBlockState(blockPos.above()).is(this) && sturdy);
   }

   public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
      if (this.particles && randomSource.nextInt(5) == 0) {
         ParticleUtils.spawnParticlesOnBlockFaces(level, blockPos, (ParticleOptions)ESParticles.SHADEGRIEVE_LEAVES.get(), ConstantInt.of(1));
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{TOP});
   }
}
