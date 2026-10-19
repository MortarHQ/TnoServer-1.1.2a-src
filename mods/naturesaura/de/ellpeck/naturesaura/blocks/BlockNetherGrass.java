package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockNetherGrass extends BlockImpl implements ICustomBlockState, BonemealableBlock, IPickaxeBreakable {
   public BlockNetherGrass() {
      super("nether_grass", Properties.ofFullCopy(Blocks.NETHERRACK).randomTicks());
   }

   public void randomTick(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource random) {
      BlockPos up = pos.above();
      BlockState upState = levelIn.getBlockState(up);
      if (upState.isFaceSturdy(levelIn, up, Direction.DOWN)) {
         levelIn.setBlockAndUpdate(pos, Blocks.NETHERRACK.defaultBlockState());
      }
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(
         this,
         generator.models()
            .cubeBottomTop(
               this.getBaseName(),
               generator.modLoc("block/" + this.getBaseName()),
               generator.mcLoc("block/netherrack"),
               generator.modLoc("block/" + this.getBaseName() + "_top")
            )
      );
   }

   public boolean isValidBonemealTarget(LevelReader levelIn, BlockPos pos, BlockState state) {
      return levelIn.getBlockState(pos.above()).isAir();
   }

   public boolean isBonemealSuccess(Level levelIn, RandomSource rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
      BlockPos blockpos = pos.above();
      BlockState blockstate = Blocks.GRASS_BLOCK.defaultBlockState();

      label38:
      for (int i = 0; i < 128; i++) {
         BlockPos blockpos1 = blockpos;

         for (int j = 0; j < i / 16; j++) {
            blockpos1 = blockpos1.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
            if (level.getBlockState(blockpos1.below()).getBlock() != this || level.getBlockState(blockpos1).isCollisionShapeFullBlock(level, blockpos1)) {
               continue label38;
            }
         }

         BlockState blockstate2 = level.getBlockState(blockpos1);
         if (blockstate2.getBlock() == blockstate.getBlock() && rand.nextInt(10) == 0) {
            ((BonemealableBlock)blockstate.getBlock()).performBonemeal(level, rand, blockpos1, blockstate2);
         }

         if (blockstate2.isAir() && blockstate.canSurvive(level, blockpos1)) {
            level.setBlock(blockpos1, blockstate, 3);
         }
      }
   }
}
