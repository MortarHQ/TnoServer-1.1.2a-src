package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.google.common.reflect.TypeToken;

public interface IEventRegistry {
   <T> IEventBus<T> busOf(TypeToken<T> var1);

   default <T> IEventBus<T> busOf(Class<T> clazz) {
      return this.busOf(TypeToken.of(clazz));
   }
}
