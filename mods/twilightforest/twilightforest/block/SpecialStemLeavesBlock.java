package twilightforest.block;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class SpecialStemLeavesBlock extends LeavesBlock {
   protected final Predicate<BlockState> stemPredicate;

   public SpecialStemLeavesBlock(Properties properties, Predicate<BlockState> stemPredicate) {
      super(properties);
      this.stemPredicate = stemPredicate;
   }

   public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
      serverLevel.setBlock(pos, this.updateDistance(state, serverLevel, pos), 3);
   }

   protected BlockState updateDistance(BlockState state, LevelAccessor levelAccessor, BlockPos pos) {
      int i = 7;
      MutableBlockPos mutableBlockPos = new MutableBlockPos();

      for (Direction direction : Direction.values()) {
         mutableBlockPos.setWithOffset(pos, direction);
         i = Math.min(i, this.getDistanceAt(levelAccessor.getBlockState(mutableBlockPos)) + 1);
         if (i == 1) {
            break;
         }
      }

      return (BlockState)state.setValue(DISTANCE, i);
   }

   protected int getDistanceAt(BlockState state) {
      return this.stemPredicate.test(state) ? 0 : (state.getBlock() instanceof LeavesBlock ? (Integer)state.getValue(DISTANCE) : 7);
   }
}
