package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BouldershroomRootsBlock extends GrowingPlantHeadBlock {
   public static final MapCodec<BouldershroomRootsBlock> CODEC = simpleCodec(BouldershroomRootsBlock::new);
   public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public BouldershroomRootsBlock(Properties properties) {
      super(properties, Direction.DOWN, SHAPE, false, 0.02);
   }

   protected MapCodec<BouldershroomRootsBlock> codec() {
      return CODEC;
   }

   protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
      return 1;
   }

   protected boolean canGrowInto(BlockState state) {
      return state.isAir();
   }

   protected Block getBodyBlock() {
      return (Block)ESBlocks.BOULDERSHROOM_ROOTS_PLANT.get();
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return false;
   }
}
