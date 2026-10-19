package net.unusual.block_factorys_bosses.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractDirectionalDoubleBlock;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CorpseBlock extends AbstractDirectionalDoubleBlock {
   private static final VoxelShape UPPER = box(2.0, -16.0, 2.0, 14.0, 16.0, 14.0);
   private static final VoxelShape LOWER = box(2.0, 0.0, 2.0, 14.0, 32.0, 14.0);

   public CorpseBlock() {
      super(
         Properties.of()
            .sound(SoundType.BONE_BLOCK)
            .strength(1.0F, 10.0F)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .isRedstoneConductor((bs, br, bp) -> false)
      );
   }

   @Override
   protected VoxelShape getUpperShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return UPPER;
   }

   @Override
   protected VoxelShape getLowerShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return LOWER;
   }
}
