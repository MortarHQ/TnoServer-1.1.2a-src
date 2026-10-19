package org.anti_ad.mc.ipnext.inventory;

import java.util.Iterator;
import java.util.TimerTask;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.Ref.ObjectRef;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;

@SourceDebugExtension(
   "SMAP\nTimer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Timer.kt\nkotlin/concurrent/TimersKt$timerTask$1\n+ 2 ContainerClicker.kt\norg/anti_ad/mc/ipnext/inventory/ContainerClicker\n*L\n1#1,147:1\n229#2,36:148\n*E\n"
)
public final class ContainerClicker$executeSwapClicks$$inlined$timer$default$1 extends TimerTask {
   public ContainerClicker$executeSwapClicks$$inlined$timer$default$1(
      AbstractContainerMenu var1, ContainerClicker$Highlight var2, ContainerClicker$Highlight var3, ObjectRef var4, Iterator var5
   ) {
      this.$currentContainer$inlined = var1;
      this.$firstHighlight$inlined = var2;
      this.$secondHighlight$inlined = var3;
      this.$currentScreen$inlined = var4;
      this.$iterator$inlined = var5;
   }

   @Override
   public final void run() {
      TimerTask var1 = this;
      if (!Intrinsics.areEqual(Vanilla.INSTANCE.container(), this.$currentContainer$inlined)) {
         var1.cancel();
         ContainerClicker.access$getHighlights$p().remove(this.$firstHighlight$inlined);
         ContainerClicker.access$getHighlights$p().remove(this.$secondHighlight$inlined);
         Log.INSTANCE.debug("Click cancelled due to container changed");
      } else {
         if (ModSettings.INSTANCE.getSTOP_AT_SCREEN_CLOSE().getBooleanValue()
            && !Intrinsics.areEqual(Vanilla.INSTANCE.screen(), this.$currentScreen$inlined.element)) {
            if (this.$currentScreen$inlined.element != null) {
               var1.cancel();
               ContainerClicker.access$getHighlights$p().remove(this.$firstHighlight$inlined);
               ContainerClicker.access$getHighlights$p().remove(this.$secondHighlight$inlined);
               Log.INSTANCE.debug("Click cancelled due to screen closed");
               LockSlotsHandler.INSTANCE.setLastMouseClickSlot(null);
               return;
            }

            this.$currentScreen$inlined.element = Vanilla.INSTANCE.screen();
         }

         if (this.$iterator$inlined.hasNext()) {
            Vanilla.INSTANCE
               .mc()
               .execute(new ContainerClicker$executeSwapClicks$3$1(this.$iterator$inlined, this.$firstHighlight$inlined, this.$secondHighlight$inlined));
         } else {
            var1.cancel();
            ContainerClicker.access$getHighlights$p().remove(this.$firstHighlight$inlined);
            ContainerClicker.access$getHighlights$p().remove(this.$secondHighlight$inlined);
            LockSlotsHandler.INSTANCE.setLastMouseClickSlot(null);
         }
      }
   }
}
