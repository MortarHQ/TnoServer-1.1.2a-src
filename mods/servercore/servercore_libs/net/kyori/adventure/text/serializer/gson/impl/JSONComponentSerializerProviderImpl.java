package servercore_libs.net.kyori.adventure.text.serializer.gson.impl;

import com.google.auto.service.AutoService;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import servercore_libs.net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import servercore_libs.net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import servercore_libs.net.kyori.adventure.util.Services;

@Internal
@AutoService(JSONComponentSerializer.Provider.class)
public final class JSONComponentSerializerProviderImpl implements JSONComponentSerializer.Provider, Services.Fallback {
   @NotNull
   @Override
   public JSONComponentSerializer instance() {
      return GsonComponentSerializer.gson();
   }

   @NotNull
   @Override
   public Supplier<JSONComponentSerializer.Builder> builder() {
      return GsonComponentSerializer::builder;
   }

   @Override
   public String toString() {
      return "JSONComponentSerializerProviderImpl[GsonComponentSerializer]";
   }
}
