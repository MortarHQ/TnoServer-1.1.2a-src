package servercore_libs.net.kyori.adventure.text.serializer.json;

import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.event.HoverEvent;
import servercore_libs.net.kyori.adventure.util.Codec;

public interface LegacyHoverEventSerializer {
   HoverEvent.@NotNull ShowItem deserializeShowItem(@NotNull Component input) throws IOException;

   @NotNull
   Component serializeShowItem(HoverEvent.@NotNull ShowItem input) throws IOException;

   HoverEvent.@NotNull ShowEntity deserializeShowEntity(@NotNull Component input, Codec.Decoder<Component, String, ? extends RuntimeException> componentDecoder) throws IOException;

   @NotNull
   Component serializeShowEntity(HoverEvent.@NotNull ShowEntity input, Codec.Encoder<Component, String, ? extends RuntimeException> componentEncoder) throws IOException;
}
