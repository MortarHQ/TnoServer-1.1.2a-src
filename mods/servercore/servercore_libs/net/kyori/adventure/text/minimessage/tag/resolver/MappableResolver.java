package servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;

interface MappableResolver {
   boolean contributeToMap(@NotNull final Map<String, Tag> map);
}
