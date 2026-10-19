package org.anti_ad.mc.ipnext.inventory;

import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.world.inventory.Slot;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;

final class ContainerClicker$executeQClicks$3$1 implements Runnable {
   ContainerClicker$executeQClicks$3$1(Iterator var1, ContainerClicker$Highlight var2) {
      this.$iterator = var1;
      this.$highlight = var2;
   }

   @Override
   public final void run() {
      Entry var1 = (Entry)this.$iterator.next();
      this.$highlight.setId(((Number)var1.getKey()).intValue());
      LockSlotsHandler.INSTANCE.setLastMouseClickSlot((Slot)var1.getValue());
      ContainerClicker.INSTANCE.qClick(((Number)var1.getKey()).intValue());
      LockSlotsHandler.INSTANCE.setLastMouseClickSlot(null);
   }
}
