package org.anti_ad.mc.ipnext.inventory;

import java.util.Iterator;
import java.util.TimerTask;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.Ref.ObjectRef;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.ModSettings;

@SourceDebugExtension(
   "SMAP\nTimer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Timer.kt\nkotlin/concurrent/TimersKt$timerTask$1\n+ 2 ContainerClicker.kt\norg/anti_ad/mc/ipnext/inventory/ContainerClicker\n*L\n1#1,147:1\n299#2,30:148\n*E\n"
)
public final class ContainerClicker$executeClicks$$inlined$timer$default$1 extends TimerTask {
   public ContainerClicker$executeClicks$$inlined$timer$default$1(AbstractContainerMenu var1, ContainerClicker$Highlight var2, ObjectRef var3, Iterator var4) {
      this.$currentContainer$inlined = var1;
      this.$highlight$inlined = var2;
      this.$currentScreen$inlined = var3;
      this.$iterator$inlined = var4;
   }

   @Override
   public final void run() {
      TimerTask var1 = this;
      if (!Intrinsics.areEqual(Vanilla.INSTANCE.container(), this.$currentContainer$inlined)) {
         var1.cancel();
         ContainerClicker.access$getHighlights$p().remove(this.$highlight$inlined);
         Log.INSTANCE.debug("Click cancelled due to container changed");
      } else {
         if (ModSettings.INSTANCE.getSTOP_AT_SCREEN_CLOSE().getBooleanValue()
            && !Intrinsics.areEqual(Vanilla.INSTANCE.screen(), this.$currentScreen$inlined.element)) {
            if (this.$currentScreen$inlined.element != null) {
               var1.cancel();
               ContainerClicker.access$getHighlights$p().remove(this.$highlight$inlined);
               Log.INSTANCE.debug("Click cancelled due to screen closed");
               return;
            }

            this.$currentScreen$inlined.element = Vanilla.INSTANCE.screen();
         }

         if (this.$iterator$inlined.hasNext()) {
            Pair var3;
            int var2 = ((Number)(var3 = (Pair)this.$iterator$inlined.next()).component1()).intValue();
            int var4 = ((Number)var3.component2()).intValue();
            Vanilla.INSTANCE.mc().execute(new ContainerClicker$executeClicks$3$1(this.$highlight$inlined, var2, var4));
         } else {
            var1.cancel();
            ContainerClicker.access$getHighlights$p().remove(this.$highlight$inlined);
         }
      }
   }
}
