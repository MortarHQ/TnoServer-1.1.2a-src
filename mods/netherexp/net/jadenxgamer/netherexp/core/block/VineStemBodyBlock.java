package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VineStemBodyBlock extends GrowingPlantBodyBlock {
   public static final MapCodec<VineStemBodyBlock> CODEC = simpleCodec(VineStemBodyBlock::new);
   public static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);

   public VineStemBodyBlock(Properties properties) {
      super(properties, Direction.UP, SHAPE, false);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos growthOppositePos = pos.relative(this.growthDirection.getOpposite());
      BlockState growthOppositeState = level.getBlockState(growthOppositePos);
      return growthOppositeState.is(this.getHeadBlock())
         || growthOppositeState.is(this.getBodyBlock())
         || growthOppositeState.is(JNETags.Blocks.SOUL_CROP_MUTATION_BLOCKS);
   }

   protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
      return CODEC;
   }

   protected GrowingPlantHeadBlock getHeadBlock() {
      return (GrowingPlantHeadBlock)JNEBlocks.SORROWSQUASH_STEM.get();
   }
}
