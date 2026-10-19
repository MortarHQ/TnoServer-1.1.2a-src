package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CaveMossBlock extends GrowingPlantHeadBlock {
   public static final MapCodec<CaveMossBlock> CODEC = simpleCodec(CaveMossBlock::new);
   public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public CaveMossBlock(Properties properties) {
      super(properties, Direction.DOWN, SHAPE, false, 0.02);
   }

   protected MapCodec<CaveMossBlock> codec() {
      return CODEC;
   }

   protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
      return 1;
   }

   protected boolean canGrowInto(BlockState state) {
      return state.isAir();
   }

   protected Block getBodyBlock() {
      return (Block)ESBlocks.CAVE_MOSS_PLANT.get();
   }
}
