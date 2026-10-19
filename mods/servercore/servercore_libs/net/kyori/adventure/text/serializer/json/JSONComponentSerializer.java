package servercore_libs.net.kyori.adventure.text.serializer.json;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.serializer.ComponentSerializer;
import servercore_libs.net.kyori.adventure.util.PlatformAPI;
import servercore_libs.net.kyori.option.OptionState;

public interface JSONComponentSerializer extends ComponentSerializer<Component, Component, String> {
   @NotNull
   static JSONComponentSerializer json() {
      return JSONComponentSerializerAccessor.Instances.INSTANCE;
   }

   static JSONComponentSerializer.@NotNull Builder builder() {
      return JSONComponentSerializerAccessor.Instances.BUILDER_SUPPLIER.get();
   }

   interface Builder {
      @NotNull
      JSONComponentSerializer.Builder options(@NotNull final OptionState flags);

      @NotNull
      JSONComponentSerializer.Builder editOptions(@NotNull final Consumer<OptionState.Builder> optionEditor);

      @Deprecated
      @NotNull
      JSONComponentSerializer.Builder downsampleColors();

      @NotNull
      JSONComponentSerializer.Builder legacyHoverEventSerializer(@Nullable final LegacyHoverEventSerializer serializer);

      @Deprecated
      @NotNull
      JSONComponentSerializer.Builder emitLegacyHoverEvent();

      @NotNull
      JSONComponentSerializer build();
   }

   @PlatformAPI
   @Internal
   interface Provider {
      @PlatformAPI
      @Internal
      @NotNull
      JSONComponentSerializer instance();

      @PlatformAPI
      @Internal
      @NotNull
      Supplier<JSONComponentSerializer.Builder> builder();
   }
}
