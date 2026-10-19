package org.anti_ad.mc.ipnext.inventory;

import java.util.Iterator;
import java.util.Map.Entry;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;

final class ContainerClicker$executeSwapClicks$3$1 implements Runnable {
   ContainerClicker$executeSwapClicks$3$1(Iterator var1, ContainerClicker$Highlight var2, ContainerClicker$Highlight var3) {
      this.$iterator = var1;
      this.$firstHighlight = var2;
      this.$secondHighlight = var3;
   }

   @Override
   public final void run() {
      Entry var1 = (Entry)this.$iterator.next();
      this.$firstHighlight.setId(((Number)var1.getKey()).intValue());
      this.$secondHighlight.setId(((Number)var1.getValue()).intValue());
      LockSlotsHandler.INSTANCE.setLastMouseClickSlot(Vanilla.INSTANCE.container().getSlot(((Number)var1.getKey()).intValue()));
      ContainerClicker.access$swapClick(ContainerClicker.INSTANCE, ((Number)var1.getKey()).intValue(), ((Number)var1.getValue()).intValue());
   }
}
