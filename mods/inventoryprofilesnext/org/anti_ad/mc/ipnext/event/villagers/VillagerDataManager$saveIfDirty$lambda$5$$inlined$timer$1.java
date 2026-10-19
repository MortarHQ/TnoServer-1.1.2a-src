package org.anti_ad.mc.ipnext.event.villagers;

import java.util.TimerTask;
import kotlin.jvm.functions.Function1;

public final class VillagerDataManager$saveIfDirty$lambda$5$$inlined$timer$1 extends TimerTask {
   public VillagerDataManager$saveIfDirty$lambda$5$$inlined$timer$1(Function1 var1) {
      this.$action = var1;
   }

   @Override
   public final void run() {
      this.$action.invoke(this);
   }
}
