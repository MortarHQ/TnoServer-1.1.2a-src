package servercore_libs.net.kyori.adventure.text.serializer.gson;

import com.google.gson.TypeAdapter;
import servercore_libs.net.kyori.adventure.text.format.TextDecoration;

final class TextDecorationSerializer {
   static final TypeAdapter<TextDecoration> INSTANCE = IndexedSerializer.strict("text decoration", TextDecoration.NAMES);

   private TextDecorationSerializer() {
   }
}
