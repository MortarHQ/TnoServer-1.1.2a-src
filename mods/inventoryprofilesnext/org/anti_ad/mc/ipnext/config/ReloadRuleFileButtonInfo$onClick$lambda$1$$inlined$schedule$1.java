package org.anti_ad.mc.ipnext.config;

import java.util.TimerTask;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.gui.widgets.CustomButtonWidget;

@SourceDebugExtension(
   "SMAP\nTimer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Timer.kt\nkotlin/concurrent/TimersKt$timerTask$1\n+ 2 CustomConfigButton.kt\norg/anti_ad/mc/ipnext/config/ReloadRuleFileButtonInfo\n*L\n1#1,147:1\n77#2,3:148\n*E\n"
)
public final class ReloadRuleFileButtonInfo$onClick$lambda$1$$inlined$schedule$1 extends TimerTask {
   public ReloadRuleFileButtonInfo$onClick$lambda$1$$inlined$schedule$1(CustomButtonWidget var1) {
      this.$widget$inlined = var1;
   }

   @Override
   public final void run() {
      this.$widget$inlined.setText(ReloadRuleFileButtonInfo.INSTANCE.getButtonText());
      this.$widget$inlined.setActive(true);
   }
}
