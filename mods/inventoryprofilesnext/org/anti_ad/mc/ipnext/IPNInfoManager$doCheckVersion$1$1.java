package org.anti_ad.mc.ipnext;

import java.util.Timer;
import kotlin.concurrent.TimersKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.moreinfo.SemVer;
import org.anti_ad.mc.ipnext.config.ModSettings;

final class IPNInfoManager$doCheckVersion$1$1 implements Function3 {
   public static final IPNInfoManager$doCheckVersion$1$1 INSTANCE = new IPNInfoManager$doCheckVersion$1$1();

   public final void invoke(SemVer var1, SemVer var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (ModSettings.INSTANCE.getENABLE_UPDATES_CHECK().getValue()) {
         Timer var4 = TimersKt.timer("versionMessage", false);
         var4.schedule(new IPNInfoManager$doCheckVersion$1$1$invoke$$inlined$timer$default$1(var1), 5000L, 10000L);
      }
   }
}
