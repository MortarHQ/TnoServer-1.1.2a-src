package org.anti_ad.mc.ipn.features.scrolling;

// $VF: synthetic class
public final class ScrollingUtils$WhenMappings {
   static {
      int[] var0 = new int[ScrollDirection.values().length];

      try {
         var0[ScrollDirection.TO_PLAYER.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         var0[ScrollDirection.TO_CHEST.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         var0[ScrollDirection.BOTH.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
