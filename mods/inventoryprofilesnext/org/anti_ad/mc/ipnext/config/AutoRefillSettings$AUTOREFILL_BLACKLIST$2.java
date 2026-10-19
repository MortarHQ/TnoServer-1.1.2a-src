package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.anti_ad.mc.ipnext.event.autorefill.AutoRefillHandler;

// $VF: synthetic class
final class AutoRefillSettings$AUTOREFILL_BLACKLIST$2 extends FunctionReferenceImpl implements Function0 {
   AutoRefillSettings$AUTOREFILL_BLACKLIST$2(Object var1) {
      super(0, var1, AutoRefillHandler.class, "blackListChanged", "blackListChanged()V", 0);
   }

   public final void invoke() {
      ((AutoRefillHandler)this.receiver).blackListChanged();
   }
}
