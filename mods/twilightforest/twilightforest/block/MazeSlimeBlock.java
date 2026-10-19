package twilightforest.block;

import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFBlocks;

public class MazeSlimeBlock extends SlimeBlock {
   public MazeSlimeBlock(Properties properties) {
      super(properties);
   }

   public boolean canStickTo(BlockState state, BlockState other) {
      return other.is(TFBlocks.MAZE_SLIME_BLOCK) || other.is(BlockTagGenerator.MAZESTONE);
   }

   public boolean isStickyBlock(BlockState state) {
      return true;
   }
}
