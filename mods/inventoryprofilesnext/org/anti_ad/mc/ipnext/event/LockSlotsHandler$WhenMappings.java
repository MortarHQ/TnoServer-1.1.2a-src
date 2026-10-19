package org.anti_ad.mc.ipnext.event;

import org.anti_ad.mc.ipnext.config.SwitchType;

// $VF: synthetic class
public final class LockSlotsHandler$WhenMappings {
   static {
      int[] var0 = new int[SwitchType.values().length];

      try {
         var0[SwitchType.TOGGLE.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
      }

      try {
         var0[SwitchType.HOLD.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
