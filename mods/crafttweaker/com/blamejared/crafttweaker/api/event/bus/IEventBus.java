package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.IEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.Phase;
import com.google.common.reflect.TypeToken;
import java.util.Objects;
import java.util.function.Consumer;

public interface IEventBus<T> {
   static <T> IEventBus<T> direct(Class<T> clazz, IEventBusWire wire) {
      Objects.requireNonNull(clazz, "class");
      Objects.requireNonNull(wire, "wire");
      return direct(TypeToken.of(clazz), wire);
   }

   static <T> IEventBus<T> direct(TypeToken<T> token, IEventBusWire wire) {
      Objects.requireNonNull(token, "token");
      Objects.requireNonNull(wire, "wire");
      return DirectEventBus.of(token, wire);
   }

   static <T> IEventBus<T> cancelable(Class<T> clazz, IEventBusWire wire, IEventCancellationCarrier<T> carrier) {
      Objects.requireNonNull(clazz, "class");
      Objects.requireNonNull(wire, "wire");
      Objects.requireNonNull(carrier, "carrier");
      return cancelable(TypeToken.of(clazz), wire, carrier);
   }

   static <T> IEventBus<T> cancelable(TypeToken<T> token, IEventBusWire wire, IEventCancellationCarrier<T> carrier) {
      Objects.requireNonNull(token, "token");
      Objects.requireNonNull(wire, "wire");
      Objects.requireNonNull(carrier, "carrier");
      return CancelingEventBus.of(token, wire, carrier);
   }

   IHandlerToken<T> registerHandler(Consumer<T> var1);

   IHandlerToken<T> registerHandler(Phase var1, Consumer<T> var2);

   IHandlerToken<T> registerHandler(boolean var1, Consumer<T> var2);

   IHandlerToken<T> registerHandler(Phase var1, boolean var2, Consumer<T> var3);

   void unregisterHandler(IHandlerToken<T> var1);

   T post(T var1);

   T post(Phase var1, T var2);

   T postCatching(T var1, Consumer<BusHandlingException> var2);

   T postCatching(Phase var1, T var2, Consumer<BusHandlingException> var3);

   TypeToken<T> eventType();
}
