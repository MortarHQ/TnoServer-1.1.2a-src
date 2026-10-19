package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.data.ESConfiguredFeatures;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CradlewoodSaplingBlock extends Block implements BonemealableBlock {
   public static final MapCodec<CradlewoodSaplingBlock> CODEC = simpleCodec(CradlewoodSaplingBlock::new);
   public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
   protected static final VoxelShape SHAPE = Block.box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);

   public CradlewoodSaplingBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(STAGE, 0));
   }

   public MapCodec<CradlewoodSaplingBlock> codec() {
      return CODEC;
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN) && !level.getBlockState(pos.above()).is(BlockTags.LOGS);
   }

   protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      return !state.canSurvive(level, currentPos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
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
            .getHolder(ESConfiguredFeatures.CRADLEWOOD);
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
}
