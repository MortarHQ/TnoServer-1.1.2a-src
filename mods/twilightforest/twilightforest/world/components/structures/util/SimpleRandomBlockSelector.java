package twilightforest.world.components.structures.util;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.StructurePiece.BlockSelector;
import twilightforest.util.WorldUtil;

public class SimpleRandomBlockSelector extends BlockSelector {
   protected final List<Pair<BlockState, Float>> blockStatesAndWeights;
   protected final List<BlockState> blockStates;

   public SimpleRandomBlockSelector(List<Pair<BlockState, Float>> blockStatesAndWeights) {
      this.blockStatesAndWeights = blockStatesAndWeights;
      this.blockStates = blockStatesAndWeights.stream().<BlockState>map(Pair::getFirst).toList();
   }

   public void next(RandomSource random, int x, int y, int z, boolean wall) {
      this.next(random, wall);
   }

   public void next(RandomSource random, boolean wall) {
      this.next = wall ? WorldUtil.getRandomElementWithWeights(this.blockStatesAndWeights, random) : Blocks.AIR.defaultBlockState();
   }

   public List<BlockState> getStates() {
      return this.blockStates;
   }
}
