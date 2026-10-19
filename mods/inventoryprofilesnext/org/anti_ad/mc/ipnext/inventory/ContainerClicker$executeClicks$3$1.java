package org.anti_ad.mc.ipnext.inventory;

final class ContainerClicker$executeClicks$3$1 implements Runnable {
   ContainerClicker$executeClicks$3$1(ContainerClicker$Highlight var1, int var2, int var3) {
      this.$highlight = var1;
      this.$slotId = var2;
      this.$button = var3;
   }

   @Override
   public final void run() {
      this.$highlight.setId(this.$slotId);
      ContainerClicker.INSTANCE.click(this.$slotId, this.$button);
   }
}
