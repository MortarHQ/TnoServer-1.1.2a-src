package org.anti_ad.mc.ipnext.item.rule.parameter;

// $VF: synthetic class
public final class RequireNbt$WhenMappings {
   static {
      int[] var0 = new int[RequireNbt.values().length];

      try {
         var0[RequireNbt.REQUIRED.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         var0[RequireNbt.NO_NBT.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         var0[RequireNbt.NOT_REQUIRED.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
