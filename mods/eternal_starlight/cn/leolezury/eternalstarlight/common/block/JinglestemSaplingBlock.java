package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.data.ESConfiguredFeatures;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class JinglestemSaplingBlock extends BushBlock implements BonemealableBlock, LiquidBlockContainer {
   public static final MapCodec<JinglestemSaplingBlock> CODEC = simpleCodec(JinglestemSaplingBlock::new);
   public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
   protected static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 12.0, 11.0);

   public JinglestemSaplingBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(STAGE, 0));
   }

   public MapCodec<JinglestemSaplingBlock> codec() {
      return CODEC;
   }

   protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
      return super.mayPlaceOn(blockState, blockGetter, blockPos)
         || blockState.is((Block)ESBlocks.TWILIGHT_SAND.get())
         || blockState.is((Block)ESBlocks.DUSTED_GRAVEL.get())
         || blockState.is(ESBlocks.MOSSY_DUSTED_GRAVEL.get())
         || blockState.is(ESBlocks.GLOWING_MOSSY_DUSTED_GRAVEL.get());
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor levelAccessor, BlockPos pos, BlockPos blockPos) {
      BlockState result = super.updateShape(state, direction, blockState, levelAccessor, pos, blockPos);
      if (!result.isAir()) {
         levelAccessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
      }

      return result;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
      return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 ? super.getStateForPlacement(context) : null;
   }

   protected FluidState getFluidState(BlockState state) {
      return Fluids.WATER.getSource(false);
   }

   protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9 && randomSource.nextInt(7) == 0) {
         this.advanceTree(serverLevel, blockPos, blockState, randomSource);
      }
   }

   public void advanceTree(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, RandomSource randomSource) {
      if ((Integer)blockState.getValue(STAGE) == 0) {
         serverLevel.setBlock(blockPos, (BlockState)blockState.cycle(STAGE), 4);
      } else {
         Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = serverLevel.registryAccess()
            .registryOrThrow(Registries.CONFIGURED_FEATURE)
            .getHolder(ESConfiguredFeatures.JINGLESTEM_PLANTED);
         if (optional.isPresent()) {
            serverLevel.removeBlock(blockPos, false);
            if (!((ConfiguredFeature)optional.get().value()).place(serverLevel, serverLevel.getChunkSource().getGenerator(), randomSource, blockPos)) {
               serverLevel.setBlock(blockPos, blockState, 3);
            }
         }
      }
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return true;
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      return level.random.nextFloat() < 0.45;
   }

   public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      this.advanceTree(serverLevel, blockPos, blockState, randomSource);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{STAGE});
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public boolean canPlaceLiquid(@Nullable Player player, BlockGetter getter, BlockPos pos, BlockState state, Fluid fluid) {
      return false;
   }

   public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
      return false;
   }
}
