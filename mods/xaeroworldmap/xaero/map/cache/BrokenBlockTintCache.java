package xaero.map.cache;

import java.util.Set;
import net.minecraft.world.level.block.state.BlockState;

public class BrokenBlockTintCache {
   private final Set<BlockState> brokenStates;

   public BrokenBlockTintCache(Set<BlockState> brokenStates) {
      this.brokenStates = brokenStates;
   }

   public void setBroken(BlockState state) {
      this.brokenStates.add(state);
   }

   public boolean isBroken(BlockState state) {
      return this.brokenStates.contains(state);
   }

   public int getSize() {
      return this.brokenStates.size();
   }
}
