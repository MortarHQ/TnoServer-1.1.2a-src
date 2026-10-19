package com.mortal_cultivation.spiritroot;

import java.util.Optional;

public final class SpiritRootClientState {
   private static Optional<SpiritRootType> currentRoot = Optional.empty();

   private SpiritRootClientState() {
   }

   public static Optional<SpiritRootType> getCurrentRoot() {
      return currentRoot;
   }

   public static void setCurrentRootId(String rootId) {
      currentRoot = SpiritRootType.byId(rootId);
   }

   public static void clear() {
      currentRoot = Optional.empty();
   }
}
