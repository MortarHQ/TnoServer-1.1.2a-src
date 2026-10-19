package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.google.common.reflect.TypeToken;

public interface IEventRegistrationHandler {
   <T> void registerEventMapping(TypeToken<T> var1, IEventBus<T> var2);
}
