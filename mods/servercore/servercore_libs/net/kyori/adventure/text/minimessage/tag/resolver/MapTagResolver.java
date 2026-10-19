package servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver;

import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;

final class MapTagResolver implements TagResolver.WithoutArguments, MappableResolver {
   private final Map<String, ? extends Tag> tagMap;

   MapTagResolver(@NotNull final Map<String, ? extends Tag> placeholderMap) {
      this.tagMap = placeholderMap;
   }

   @Nullable
   @Override
   public Tag resolve(@NotNull final String name) {
      return this.tagMap.get(name);
   }

   @Override
   public boolean contributeToMap(@NotNull final Map<String, Tag> map) {
      map.putAll(this.tagMap);
      return true;
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof MapTagResolver)) {
         return false;
      }

      MapTagResolver that = (MapTagResolver)other;
      return Objects.equals(this.tagMap, that.tagMap);
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.tagMap);
   }
}
