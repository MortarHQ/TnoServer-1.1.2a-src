package org.anti_ad.mc.ipnext.event.villagers;

import java.util.Timer;
import java.util.TimerTask;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.config.ModSettings;

public final class VillagerDataManager$saveIfDirty$task$1 implements Function1 {
   private final int maxTimesEmpty = 15;
   private int timesEmpty;

   VillagerDataManager$saveIfDirty$task$1() {
   }

   public final int getMaxTimesEmpty() {
      return this.maxTimesEmpty;
   }

   public final int getTimesEmpty() {
      return this.timesEmpty;
   }

   public final void setTimesEmpty(int var1) {
      this.timesEmpty = var1;
   }

   private final void cancel() {
      synchronized (VillagerDataManager.access$getTimerSync$p()) {
         Timer var10000 = VillagerDataManager.INSTANCE.getSaveTimer();
         if (var10000 != null) {
            var10000.cancel();
         }

         VillagerDataManager.INSTANCE.setSaveTimer(null);
      }
   }

   public final void invoke(TimerTask var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (ModSettings.INSTANCE.getVILLAGER_TRADING_ENABLE().getBooleanValue()) {
         Config var2;
         if ((var2 = VillagerDataManager.access$getConfig$p().asSanitized()).isDirty()) {
            VillagerDataManager.access$save(VillagerDataManager.INSTANCE, var2);
            VillagerDataManager.access$getConfig$p().cleanDirty();
            return;
         }

         this.timesEmpty++;
         if (this.timesEmpty <= this.maxTimesEmpty) {
            return;
         }
      }

      this.cancel();
   }
}
