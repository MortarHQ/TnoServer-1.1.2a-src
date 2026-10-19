package org.anti_ad.mc.ipnext;

import java.net.URL;
import java.util.TimerTask;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.vanilla.Vanilla;

@SourceDebugExtension(
   "SMAP\nTimer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Timer.kt\nkotlin/concurrent/TimersKt$timerTask$1\n+ 2 IPNInfoManager.kt\norg/anti_ad/mc/ipnext/IPNInfoManager\n*L\n1#1,147:1\n61#2,5:148\n77#2:153\n*E\n"
)
public final class IPNInfoManager$doCheckVersion$$inlined$timer$default$1 extends TimerTask {
   @Override
   public final void run() {
      TimerTask var1 = this;
      if (Vanilla.INSTANCE.playerNullable() != null && !Intrinsics.areEqual(IPNInfoManager.INSTANCE.getVersion(), "null")) {
         String var2 = IPNInfoManager.access$getPlayerId$p().invoke() + "; " + IPNInfoManager.access$getSession$p();
         var1.cancel();
         IPNInfoManager var10000 = IPNInfoManager.INSTANCE;
         URL var10001 = InventoryProfilesKt.getVersionCheckUrl();
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         var10000.checkVersion(var10001, "IPN", var2, IPNInfoManager$doCheckVersion$1$1.INSTANCE);
      }
   }
}
