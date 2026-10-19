package nosferatu.eldergrove;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ElderwoodSaplingBlock extends SaplingBlock {
   public ElderwoodSaplingBlock(TreeGrower treeGrower, Properties properties) {
      super(treeGrower, properties);
   }

   public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
      EldergroveTreeGenerator.grow(level, pos, random);
   }
}
