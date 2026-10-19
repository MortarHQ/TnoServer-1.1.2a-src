package net.regions_unexplored.world.level.block.plant.other;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.RUBlocks;

public class DropleafBlock extends GrowingPlantHeadBlock {
   public static final MapCodec<DropleafBlock> CODEC = simpleCodec(DropleafBlock::new);
   public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

   public DropleafBlock(Properties properties) {
      super(properties, Direction.DOWN, SHAPE, false, 0.1);
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      Vec3 vec3 = state.getOffset(getter, pos);
      return SHAPE.move(vec3.x, vec3.y, vec3.z);
   }

   protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
      return CODEC;
   }

   protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
      return NetherVines.getBlocksToGrowWhenBonemealed(random);
   }

   protected Block getBodyBlock() {
      return RUBlocks.DROPLEAF_PLANT.get();
   }

   protected boolean canGrowInto(BlockState state) {
      return NetherVines.isValidGrowthState(state);
   }
}
