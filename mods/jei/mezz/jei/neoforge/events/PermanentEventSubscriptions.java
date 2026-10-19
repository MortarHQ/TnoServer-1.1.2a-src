package mezz.jei.neoforge.events;

import java.util.function.Consumer;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.IModBusEvent;

public class PermanentEventSubscriptions {
   private final IEventBus eventBus;
   private final IEventBus modEventBus;

   public PermanentEventSubscriptions(IEventBus eventBus, IEventBus modEventBus) {
      this.eventBus = eventBus;
      this.modEventBus = modEventBus;
   }

   public <T extends Event> void register(Class<T> eventType, Consumer<T> listener) {
      if (IModBusEvent.class.isAssignableFrom(eventType)) {
         this.modEventBus.addListener(EventPriority.NORMAL, false, eventType, listener);
      } else {
         this.eventBus.addListener(EventPriority.NORMAL, false, eventType, listener);
      }
   }

   public IEventBus getModEventBus() {
      return this.modEventBus;
   }
}
