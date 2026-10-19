package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.IEventCancellationCarrier;
import com.google.common.reflect.TypeToken;
import java.util.Objects;
import java.util.function.Consumer;

final class CancelingEventBus<T> extends PhasedEventBus<T> {
   private final IEventCancellationCarrier<T> carrier;

   private CancelingEventBus(TypeToken<T> token, IEventBusWire wire, IEventCancellationCarrier<T> carrier) {
      super(token, wire);
      this.carrier = Objects.requireNonNull(carrier, "carrier");
   }

   static <T> IEventBus<T> of(TypeToken<T> token, IEventBusWire wire, IEventCancellationCarrier<T> carrier) {
      CancelingEventBus<T> bus = new CancelingEventBus<>(token, wire, carrier);
      bus.wire();
      return bus;
   }

   @Override
   protected IHandlerToken<T> registerPhased(ArrayBackedDispatcher<T> phasedDispatcher, boolean listenToCanceled, Consumer<T> handler) {
      IEventDispatcher<T> dispatcher = new CancelingEventBus.CancelingEventDispatcher<>(handler, listenToCanceled, this.carrier);
      return phasedDispatcher.register(dispatcher);
   }

   private record CancelingEventDispatcher<T>(Consumer<T> delegate, boolean allowCanceled, IEventCancellationCarrier<T> carrier) implements IEventDispatcher<T> {
      @Override
      public void dispatch(T event) {
         if (!this.carrier().isCanceled(event) || this.allowCanceled()) {
            this.delegate().accept(event);
         }
      }
   }
}
