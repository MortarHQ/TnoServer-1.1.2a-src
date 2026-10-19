package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.anti_ad.mc.ipnext.event.LockedSlotKeeper;

// $VF: synthetic class
final class LockedSlotsSettings$LOCKED_SLOTS_EMPTY_HOTBAR_BLACKLIST$2 extends FunctionReferenceImpl implements Function0 {
   LockedSlotsSettings$LOCKED_SLOTS_EMPTY_HOTBAR_BLACKLIST$2(Object var1) {
      super(0, var1, LockedSlotKeeper.class, "updateIgnored", "updateIgnored()V", 0);
   }

   public final void invoke() {
      ((LockedSlotKeeper)this.receiver).updateIgnored();
   }
}
