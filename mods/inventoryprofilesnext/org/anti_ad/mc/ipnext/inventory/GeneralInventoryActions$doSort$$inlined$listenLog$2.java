package org.anti_ad.mc.ipnext.inventory;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.LogBase.LogLevel;
import org.anti_ad.mc.common.LogBase.LogMessage;

public final class GeneralInventoryActions$doSort$$inlined$listenLog$2 implements Function1 {
   public GeneralInventoryActions$doSort$$inlined$listenLog$2(LogLevel var1, Function1 var2) {
      this.$level = var1;
      this.$listener = var2;
   }

   public final void invoke(LogMessage var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (var1.getLevel().compareTo((Enum)this.$level) >= 0) {
         this.$listener.invoke(var1);
      }
   }
}
