package bigchadguys.sellingbin.data.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Optional;

public interface IJsonAdapter<T, J extends JsonElement, C> extends JsonSerializer<T>, JsonDeserializer<T> {
   Optional<J> writeJson(T var1, C var2);

   Optional<T> readJson(J var1, C var2);

   default JsonElement serialize(T value, Type source, JsonSerializationContext context) {
      throw new UnsupportedOperationException();
   }

   default T deserialize(JsonElement json, Type source, JsonDeserializationContext context) {
      throw new UnsupportedOperationException();
   }
}
