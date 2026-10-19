package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DarkLeavesBlock extends LeavesBlock {
   public DarkLeavesBlock(Properties properties) {
      super(properties);
   }

   public VoxelShape getBlockSupportShape(BlockState state, BlockGetter getter, BlockPos pos) {
      return Shapes.block();
   }

   public int getLightBlock(BlockState state, BlockGetter getter, BlockPos pos) {
      return 15;
   }
}
