package org.anti_ad.mc.ipnext.inventory;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.LogBase.LogMessage;

public final class GeneralInventoryActions$doSort$$inlined$listenLog$1 implements Function1 {
   public static final GeneralInventoryActions$doSort$$inlined$listenLog$1 INSTANCE = new GeneralInventoryActions$doSort$$inlined$listenLog$1();

   public final void invoke(LogMessage var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      TellPlayer.INSTANCE.chat(var1.getMessage());
   }
}
