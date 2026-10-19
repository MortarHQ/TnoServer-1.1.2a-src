package org.crypticdev.ancient_artifacts;

public interface PlayerFabric {
   default long ancient_artifacts$getLastCombatTick() {
      throw new RuntimeException();
   }

   default void ancient_artifacts$setLastCombatTick(long mymod$lastCombatTick) {
   }

   default long ancient_artifacts$getFirstCombatTick() {
      throw new RuntimeException();
   }

   default void ancient_artifacts$setFirstCombatTick(long firstCombatTick) {
   }
}
