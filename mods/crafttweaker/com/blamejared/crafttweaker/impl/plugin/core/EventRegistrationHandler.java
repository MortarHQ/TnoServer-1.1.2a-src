package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.plugin.IEventRegistrationHandler;
import com.google.common.reflect.TypeToken;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class EventRegistrationHandler implements IEventRegistrationHandler {
   private final Map<TypeToken<?>, IEventBus<?>> eventBuses = new HashMap<>();

   private EventRegistrationHandler() {
   }

   static Map<TypeToken<?>, IEventBus<?>> gather(Consumer<IEventRegistrationHandler> populatingConsumer) {
      EventRegistrationHandler handler = new EventRegistrationHandler();
      populatingConsumer.accept(handler);
      return Collections.unmodifiableMap(handler.eventBuses);
   }

   @Override
   public <T> void registerEventMapping(TypeToken<T> event, IEventBus<T> bus) {
      if (this.eventBuses.containsKey(event)) {
         throw new IllegalArgumentException("Event " + event + " was already registered with a different bus");
      }

      this.eventBuses.put(event, bus);
   }
}
