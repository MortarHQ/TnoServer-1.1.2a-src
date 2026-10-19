package servercore_libs.net.kyori.adventure.text.serializer.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import servercore_libs.net.kyori.adventure.text.event.DataComponentValue;

@NonExtendable
public interface GsonDataComponentValue extends DataComponentValue {
   static GsonDataComponentValue gsonDataComponentValue(@NotNull final JsonElement data) {
      return data instanceof JsonNull
         ? GsonDataComponentValueImpl.RemovedGsonComponentValueImpl.INSTANCE
         : new GsonDataComponentValueImpl(Objects.requireNonNull(data, "data"));
   }

   @NotNull
   JsonElement element();
}
