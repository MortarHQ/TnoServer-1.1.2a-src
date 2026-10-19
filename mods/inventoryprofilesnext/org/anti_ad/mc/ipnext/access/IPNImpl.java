package org.anti_ad.mc.ipnext.access;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import org.anti_ad.mc.ipn.api.access.IContainerClicker;
import org.anti_ad.mc.ipn.api.access.IPN;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.jetbrains.annotations.NotNull;

public final class IPNImpl extends IPN {
   @NotNull
   public static final IPNImpl$Companion Companion = new IPNImpl$Companion(null);
   @NotNull
   private final IContainerClicker containerClicker = new PContainerClicker();
   @NotNull
   private static final List actions = new ArrayList();

   @NotNull
   @Override
   public final IContainerClicker getContainerClicker() {
      return this.containerClicker;
   }

   @NotNull
   @Override
   public final List getLockedSlots() {
      return CollectionsKt.toList(LockSlotsHandler.INSTANCE.getLockedInvSlots());
   }
}
