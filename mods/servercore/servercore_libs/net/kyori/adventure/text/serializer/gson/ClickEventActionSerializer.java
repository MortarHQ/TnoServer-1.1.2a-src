package servercore_libs.net.kyori.adventure.text.serializer.gson;

import com.google.gson.TypeAdapter;
import servercore_libs.net.kyori.adventure.text.event.ClickEvent;

final class ClickEventActionSerializer {
   static final TypeAdapter<ClickEvent.Action> INSTANCE = IndexedSerializer.lenient("click action", ClickEvent.Action.NAMES);

   private ClickEventActionSerializer() {
   }
}
