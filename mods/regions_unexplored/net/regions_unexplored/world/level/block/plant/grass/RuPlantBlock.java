package net.regions_unexplored.world.level.block.plant.grass;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.RUBlocks;

public class RuPlantBlock extends BushBlock implements BonemealableBlock {
   public static final MapCodec<? extends RuPlantBlock> CODEC = simpleCodec(RuPlantBlock::new);
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

   public RuPlantBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return true;
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      if (state.is(RUBlocks.STEPPE_GRASS.get()) || state.is(RUBlocks.STEPPE_SHRUB.get())) {
         Block steppe_tall_grass = RUBlocks.STEPPE_TALL_GRASS.get();
         if (steppe_tall_grass instanceof DoublePlantBlock tallSteppeGrass
            && tallSteppeGrass.defaultBlockState().canSurvive(level, pos)
            && level.isEmptyBlock(pos.above())) {
            DoublePlantBlock.placeAt(level, tallSteppeGrass.defaultBlockState(), pos, 2);
         }
      }
   }
}
