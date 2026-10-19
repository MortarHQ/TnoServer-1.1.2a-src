package org.anti_ad.mc.ipnext.event.autorefill;

import org.anti_ad.mc.ipnext.config.ToolReplaceVisualNotification;

// $VF: synthetic class
public final class AutoRefillHandler$ItemSlotMonitor$WhenMappings {
   static {
      int[] var0 = new int[ToolReplaceVisualNotification.values().length];

      try {
         var0[ToolReplaceVisualNotification.SUBTITLE.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         var0[ToolReplaceVisualNotification.HOTBAR.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         var0[ToolReplaceVisualNotification.CHAT.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
