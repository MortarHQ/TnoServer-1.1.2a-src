package com.blamejared.crafttweaker.api.event.bus;

import com.google.common.reflect.TypeToken;
import java.util.function.Consumer;

final class DirectEventBus<T> extends PhasedEventBus<T> {
   private DirectEventBus(TypeToken<T> eventType, IEventBusWire wire) {
      super(eventType, wire);
   }

   static <T> IEventBus<T> of(TypeToken<T> eventType, IEventBusWire wire) {
      DirectEventBus<T> bus = new DirectEventBus<>(eventType, wire);
      bus.wire();
      return bus;
   }

   @Override
   protected IHandlerToken<T> registerPhased(ArrayBackedDispatcher<T> phasedDispatcher, boolean listenToCanceled, Consumer<T> handler) {
      IEventDispatcher<T> dispatcher = new DirectEventBus.DirectEventDispatcher<>(handler);
      return phasedDispatcher.register(dispatcher);
   }

   private record DirectEventDispatcher<T>(Consumer<T> delegate) implements IEventDispatcher<T> {
      @Override
      public void dispatch(T event) {
         this.delegate().accept(event);
      }
   }
}
