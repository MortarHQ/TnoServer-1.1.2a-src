package org.anti_ad.mc.ipnext.event.autorefill;

import org.anti_ad.mc.ipnext.config.AutoRefillNbtMatchType;
import org.anti_ad.mc.ipnext.config.ThresholdUnit;

// $VF: synthetic class
public final class AutoRefillHandler$ItemSlotMonitor$Companion$WhenMappings {
   static {
      int[] var0 = new int[AutoRefillNbtMatchType.values().length];

      try {
         var0[AutoRefillNbtMatchType.CAN_HAVE_EXTRA.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
      }

      try {
         var0[AutoRefillNbtMatchType.EXACT.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
      }

      $EnumSwitchMapping$0 = var0;
      var0 = new int[ThresholdUnit.values().length];

      try {
         var0[ThresholdUnit.ABSOLUTE.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
      }

      try {
         var0[ThresholdUnit.PERCENTAGE.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
      }

      $EnumSwitchMapping$1 = var0;
   }
}
