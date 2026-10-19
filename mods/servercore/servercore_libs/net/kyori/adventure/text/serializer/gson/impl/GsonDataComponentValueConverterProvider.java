package servercore_libs.net.kyori.adventure.text.serializer.gson.impl;

import com.google.auto.service.AutoService;
import com.google.gson.JsonNull;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import servercore_libs.net.kyori.adventure.key.Key;
import servercore_libs.net.kyori.adventure.text.event.DataComponentValue;
import servercore_libs.net.kyori.adventure.text.event.DataComponentValueConverterRegistry;
import servercore_libs.net.kyori.adventure.text.serializer.gson.GsonDataComponentValue;

@AutoService(DataComponentValueConverterRegistry.Provider.class)
@Internal
public final class GsonDataComponentValueConverterProvider implements DataComponentValueConverterRegistry.Provider {
   private static final Key ID = Key.key("adventure", "serializer/gson");

   @NotNull
   @Override
   public Key id() {
      return ID;
   }

   @NotNull
   @Override
   public Iterable<DataComponentValueConverterRegistry.Conversion<?, ?>> conversions() {
      return Collections.singletonList(
         DataComponentValueConverterRegistry.Conversion.convert(
            DataComponentValue.Removed.class, GsonDataComponentValue.class, (k, removed) -> GsonDataComponentValue.gsonDataComponentValue(JsonNull.INSTANCE)
         )
      );
   }
}
