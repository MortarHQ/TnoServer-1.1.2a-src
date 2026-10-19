package com.blamejared.crafttweaker.api.event.bus;

import com.google.common.reflect.TypeToken;

public interface IEventBusWire {
   <T> void registerBusForDispatch(TypeToken<T> var1, IEventBus<T> var2);
}
