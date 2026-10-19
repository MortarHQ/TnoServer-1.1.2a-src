package org.anti_ad.mc.ipnext;

import org.anti_ad.mc.common.LogBase;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.jetbrains.annotations.NotNull;

public final class Log extends LogBase {
   @NotNull
   public static final Log INSTANCE = new Log();

   private Log() {
      super(Log::_init_$lambda$0, Log::_init_$lambda$1);
   }

   @NotNull
   protected final String getId() {
      return "Inventory Profiles Next";
   }

   private static final boolean _init_$lambda$0() {
      return ModSettings.INSTANCE.getDEBUG().getBooleanValue();
   }

   private static final boolean _init_$lambda$1() {
      return ModSettings.INSTANCE.getDEBUG().getBooleanValue() && Debugs.INSTANCE.getTRACE_LOGS().getBooleanValue();
   }
}
