package net.unusual.block_factorys_bosses.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractDoubleBlock;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TallVaseBlock extends AbstractDoubleBlock {
   private static final VoxelShape UPPER = Shapes.or(
      box(5.0, -16.0, 5.0, 11.0, -14.0, 11.0),
      new VoxelShape[]{box(3.0, -14.0, 3.0, 13.0, 6.0, 13.0), box(6.0, 6.0, 6.0, 10.0, 11.0, 10.0), box(5.0, 11.0, 5.0, 11.0, 13.0, 11.0)}
   );
   private static final VoxelShape LOWER = Shapes.or(
      box(5.0, 0.0, 5.0, 11.0, 2.0, 11.0),
      new VoxelShape[]{box(3.0, 2.0, 3.0, 13.0, 22.0, 13.0), box(6.0, 22.0, 6.0, 10.0, 27.0, 10.0), box(5.0, 27.0, 5.0, 11.0, 29.0, 11.0)}
   );

   public TallVaseBlock() {
      super(
         Properties.of()
            .sound(SoundType.DECORATED_POT)
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
