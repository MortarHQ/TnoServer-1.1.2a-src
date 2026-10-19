package com.blamejared.crafttweaker.api.event.bus;

import com.google.common.reflect.TypeToken;

public final class CommonWirelessEventBusWire implements IEventBusWire {
   private static final CommonWirelessEventBusWire INSTANCE = new CommonWirelessEventBusWire();

   private CommonWirelessEventBusWire() {
   }

   public static IEventBusWire of() {
      return INSTANCE;
   }

   @Override
   public <T> void registerBusForDispatch(TypeToken<T> eventType, IEventBus<T> bus) {
   }
}
