package servercore_libs.net.kyori.adventure.text.serializer.gson;

import com.google.gson.TypeAdapter;
import servercore_libs.net.kyori.adventure.text.event.HoverEvent;

final class HoverEventActionSerializer {
   static final TypeAdapter<HoverEvent.Action<?>> INSTANCE = IndexedSerializer.lenient("hover action", HoverEvent.Action.NAMES);

   private HoverEventActionSerializer() {
   }
}
