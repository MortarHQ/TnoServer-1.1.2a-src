package dev.ftb.mods.ftblibrary.api.color;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.chat.TextColor;

public record RegisterCustomColorEvent(Map<String, TextColor> colors) {
   public static final Event<Consumer<RegisterCustomColorEvent>> EVENT = EventFactory.createConsumerLoop(RegisterCustomColorEvent.class);

   public void register(String id, TextColor color) {
      this.colors.put(id, color);
   }
}
