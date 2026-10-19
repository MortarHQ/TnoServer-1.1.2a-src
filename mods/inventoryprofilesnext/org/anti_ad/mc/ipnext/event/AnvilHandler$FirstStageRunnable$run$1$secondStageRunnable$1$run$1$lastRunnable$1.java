package org.anti_ad.mc.ipnext.event;

import org.anti_ad.mc.ipnext.Log;

public final class AnvilHandler$FirstStageRunnable$run$1$secondStageRunnable$1$run$1$lastRunnable$1 implements Runnable {
   AnvilHandler$FirstStageRunnable$run$1$secondStageRunnable$1$run$1$lastRunnable$1(AnvilHandler$FirstStageRunnable var1) {
      this.this$0 = var1;
   }

   @Override
   public final void run() {
      AnvilHandler$FirstStageRunnable var2 = this.this$0;
      synchronized (AnvilHandler.INSTANCE.getSync()) {
         if (AnvilHandler.INSTANCE.getTicksAfterLastPacket() >= 2) {
            Log.INSTANCE.trace("Allowing next rename");
            AnvilHandler.access$setStillProcessingLast$p(false);
            if (AnvilHandler$FirstStageRunnable.access$getDoRenameClick$p(var2)) {
               AnvilHandler.INSTANCE.addOnTickRunnable(AnvilHandler$FirstStageRunnable$run$1$secondStageRunnable$1$run$1$lastRunnable$1::run$lambda$1$lambda$0);
            }

            return;
         }

         int var3 = AnvilHandler.INSTANCE.getTicksAfterLastPacket();
         AnvilHandler.INSTANCE.setTicksAfterLastPacket(var3 + 1);
         Log.INSTANCE.trace("Restarting because of a packet processing " + AnvilHandler.INSTANCE.getTicksAfterLastPacket());
         AnvilHandler$FirstStageRunnable.access$setDoRenameClick$p(var2, true);
         AnvilHandler.INSTANCE.addOnTickRunnable(var2);
      }
   }

   private static final void run$lambda$1$lambda$0() {
      Log.INSTANCE.trace("NOT Clicking rename");
   }
}
