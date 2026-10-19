package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.base.Suppliers;
import com.google.common.reflect.TypeToken;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.commons.lang3.NotImplementedException;

public final class NeoForgeEventBusWire implements IEventBusWire {
   private static final Supplier<IEventBusWire> INSTANCE = Suppliers.memoize(NeoForgeEventBusWire::new);
   private static final Map<Phase, EventPriority> PRIORITIES = (Map<Phase, EventPriority>)Util.make(new EnumMap<>(Phase.class), it -> {
      it.put(Phase.EARLIEST, EventPriority.HIGHEST);
      it.put(Phase.NORMAL, EventPriority.NORMAL);
      it.put(Phase.LATEST, EventPriority.LOWEST);
   });

   private NeoForgeEventBusWire() {
   }

   public static IEventBusWire of() {
      return INSTANCE.get();
   }

   @Override
   public <T> void registerBusForDispatch(TypeToken<T> eventType, IEventBus<T> bus) {
      if (!eventType.isSubtypeOf(Event.class)) {
         throw new IllegalArgumentException("Unable to wire EventBus to NeoForge bus for type " + eventType);
      }

      this.registerBus(GenericUtil.uncheck(eventType), GenericUtil.uncheck(bus));
   }

   private <T extends Event> void registerBus(TypeToken<T> eventType, IEventBus<T> bus) {
      NeoForgeEventBusWire.ListenerRegistrationHandler<T> registrationHandler = this.discoverRegistrationHandler(eventType);
      PRIORITIES.forEach((phase, priority) -> registrationHandler.register(priority, true, e -> bus.post(phase, e)));
   }

   private <T extends Event> NeoForgeEventBusWire.ListenerRegistrationHandler<T> discoverRegistrationHandler(TypeToken<T> token) {
      net.neoforged.bus.api.IEventBus bus = this.discoverBus(token);
      Class<T> rawClass = GenericUtil.uncheck(token.getRawType());
      return (priority, receiveCanceled, handler) -> bus.addListener(priority, receiveCanceled, rawClass, handler);
   }

   private <T extends Event> net.neoforged.bus.api.IEventBus discoverBus(TypeToken<T> token) {
      if (token.isSubtypeOf(IModBusEvent.class)) {
         throw new NotImplementedException("TODO");
      } else {
         return NeoForge.EVENT_BUS;
      }
   }

   @FunctionalInterface
   private interface ListenerRegistrationHandler<E extends Event> {
      void register(EventPriority var1, boolean var2, Consumer<E> var3);
   }
}
