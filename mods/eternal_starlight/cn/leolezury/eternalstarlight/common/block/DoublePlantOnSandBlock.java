package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class DoublePlantOnSandBlock extends DoublePlantBlock {
   public static final MapCodec<DoublePlantOnSandBlock> CODEC = simpleCodec(DoublePlantOnSandBlock::new);

   public DoublePlantOnSandBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<DoublePlantOnSandBlock> codec() {
      return CODEC;
   }

   protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
      return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(BlockTags.SAND);
   }
}
