package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class VelvetumossVilliBlock extends DirectionalBudBlock implements BonemealableBlock {
   public static final MapCodec<VelvetumossVilliBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("full_block").forGetter(block -> block.fullBlock), propertiesCodec())
         .apply(instance, VelvetumossVilliBlock::new)
   );
   public static final IntegerProperty AGE = BlockStateProperties.AGE_25;
   private final Holder<Block> fullBlock;

   public VelvetumossVilliBlock(Holder<Block> fullBlock, Properties properties) {
      super(properties);
      this.fullBlock = fullBlock;
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(AGE, 0));
   }

   @Override
   protected MapCodec<VelvetumossVilliBlock> codec() {
      return CODEC;
   }

   protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      if ((Integer)blockState.getValue(AGE) == 25) {
         serverLevel.setBlockAndUpdate(blockPos, ((Block)this.fullBlock.value()).defaultBlockState());
      } else if (randomSource.nextInt(5) == 0) {
         serverLevel.setBlockAndUpdate(
            blockPos, (BlockState)blockState.setValue(AGE, Math.min((Integer)blockState.getValue(AGE) + randomSource.nextInt(3), 25))
         );
      }
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{AGE});
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return true;
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      return true;
   }

   public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      if ((Integer)blockState.getValue(AGE) == 25) {
         serverLevel.setBlockAndUpdate(blockPos, ((Block)this.fullBlock.value()).defaultBlockState());
      } else {
         serverLevel.setBlockAndUpdate(
            blockPos, (BlockState)blockState.setValue(AGE, Math.min((Integer)blockState.getValue(AGE) + randomSource.nextInt(5) + 1, 25))
         );
      }
   }
}
