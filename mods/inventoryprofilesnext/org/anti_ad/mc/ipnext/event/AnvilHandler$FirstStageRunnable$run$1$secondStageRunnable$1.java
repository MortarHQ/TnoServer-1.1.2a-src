package org.anti_ad.mc.ipnext.event;

import org.anti_ad.mc.alias.client.gui.screen.ingame.InGameExKt;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt;

public final class AnvilHandler$FirstStageRunnable$run$1$secondStageRunnable$1 implements Runnable {
   AnvilHandler$FirstStageRunnable$run$1$secondStageRunnable$1(AnvilHandler$FirstStageRunnable var1) {
      this.this$0 = var1;
   }

   @Override
   public final void run() {
      AnvilHandler$FirstStageRunnable var2 = this.this$0;
      synchronized (AnvilHandler.INSTANCE.getSync()) {
         if (AnvilHandler.INSTANCE.getTicksAfterLastPacket() >= 2) {
            Log.INSTANCE.trace("Setting name field to " + AnvilHandler$FirstStageRunnable.access$getLastText$p(var2));
            InGameExKt.set_nameFieldText_/* $VF was: set(nameFieldText) */(
               AnvilHandler$FirstStageRunnable.access$getScreen$p(var2), AnvilHandler$FirstStageRunnable.access$getLastText$p(var2)
            );
            Runnable var6 = new AnvilHandler$FirstStageRunnable$run$1$secondStageRunnable$1$run$1$lastRunnable$1(var2);
            VanillaAccessorsKt._send_/* $VF was: (send) */(Vanilla.INSTANCE.mc(), var6);
            return;
         }

         int var3 = AnvilHandler.INSTANCE.getTicksAfterLastPacket();
         AnvilHandler.INSTANCE.setTicksAfterLastPacket(var3 + 1);
         Log.INSTANCE.trace("Restarting because of a packet processing " + AnvilHandler.INSTANCE.getTicksAfterLastPacket());
         AnvilHandler$FirstStageRunnable.access$setDoRenameClick$p(var2, true);
         AnvilHandler.INSTANCE.addOnTickRunnable(var2);
      }
   }
}
