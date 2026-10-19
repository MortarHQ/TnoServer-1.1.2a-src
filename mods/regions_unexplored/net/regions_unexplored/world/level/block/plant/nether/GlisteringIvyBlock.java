package net.regions_unexplored.world.level.block.plant.nether;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.RUBlocks;

public class GlisteringIvyBlock extends GrowingPlantHeadBlock {
   public static final MapCodec<? extends GlisteringIvyBlock> CODEC = simpleCodec(GlisteringIvyBlock::new);
   protected static final VoxelShape SHAPE = Block.box(1.0, 1.0, 1.0, 15.0, 16.0, 15.0);

   public GlisteringIvyBlock(Properties properties) {
      super(properties, Direction.DOWN, SHAPE, false, 0.1);
   }

   protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
      return CODEC;
   }

   protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
      return NetherVines.getBlocksToGrowWhenBonemealed(random);
   }

   protected Block getBodyBlock() {
      return RUBlocks.GLISTERING_IVY_PLANT.get();
   }

   protected boolean canGrowInto(BlockState state) {
      return NetherVines.isValidGrowthState(state);
   }
}
