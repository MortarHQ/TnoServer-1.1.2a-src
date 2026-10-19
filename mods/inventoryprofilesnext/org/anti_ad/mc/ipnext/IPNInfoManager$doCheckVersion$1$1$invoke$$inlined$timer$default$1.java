package org.anti_ad.mc.ipnext;

import java.util.TimerTask;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.moreinfo.SemVer;
import org.anti_ad.mc.common.vanilla.Vanilla;

@SourceDebugExtension(
   "SMAP\nTimer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Timer.kt\nkotlin/concurrent/TimersKt$timerTask$1\n+ 2 IPNInfoManager.kt\norg/anti_ad/mc/ipnext/IPNInfoManager$doCheckVersion$1$1\n*L\n1#1,147:1\n68#2,6:148\n*E\n"
)
public final class IPNInfoManager$doCheckVersion$1$1$invoke$$inlined$timer$default$1 extends TimerTask {
   public IPNInfoManager$doCheckVersion$1$1$invoke$$inlined$timer$default$1(SemVer var1) {
      this.$new$inlined = var1;
   }

   @Override
   public final void run() {
      TimerTask var1 = this;
      Vanilla.INSTANCE.queueForMainThread(new IPNInfoManager$doCheckVersion$1$1$1$1(this.$new$inlined));
      var1.cancel();
   }
}
