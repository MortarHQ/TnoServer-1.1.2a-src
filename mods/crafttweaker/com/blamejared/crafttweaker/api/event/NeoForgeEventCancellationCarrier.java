package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.neoforged.bus.api.ICancellableEvent;

public final class NeoForgeEventCancellationCarrier<T extends ICancellableEvent> implements IEventCancellationCarrier<T> {
   private static final Supplier<NeoForgeEventCancellationCarrier<?>> INSTANCE = Suppliers.memoize(NeoForgeEventCancellationCarrier::new);

   private NeoForgeEventCancellationCarrier() {
   }

   public static <T> IEventCancellationCarrier<T> of() {
      return GenericUtil.uncheck(INSTANCE.get());
   }

   public boolean isCanceled(T event) {
      return event.isCanceled();
   }

   public void cancel(T event) {
      event.setCanceled(true);
   }
}
