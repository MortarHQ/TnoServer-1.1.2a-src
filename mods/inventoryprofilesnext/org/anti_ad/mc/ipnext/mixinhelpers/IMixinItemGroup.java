package org.anti_ad.mc.ipnext.mixinhelpers;

public interface IMixinItemGroup {
   default int getIPNPriorityIndex() {
      throw new AssertionError("Not implemented");
   }

   default void setIPNPriorityIndex(int var1) {
      throw new AssertionError("Not implemented");
   }
}
