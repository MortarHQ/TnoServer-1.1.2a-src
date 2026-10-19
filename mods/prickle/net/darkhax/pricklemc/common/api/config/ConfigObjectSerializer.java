package net.darkhax.pricklemc.common.api.config;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.pricklemc.common.api.config.property.IConfigProperty;
import org.slf4j.Logger;

public class ConfigObjectSerializer<T> {
   private final PropertyResolver propertyResolver;
   private final Logger log;
   private final Map<String, ConfigObjectSerializer.SchemaEntry> properties;

   public ConfigObjectSerializer(PropertyResolver propertyResolver, T dataObj) {
      this.propertyResolver = propertyResolver;
      this.log = propertyResolver.logger();
      this.properties = this.mapSchema(dataObj);
   }

   private Map<String, ConfigObjectSerializer.SchemaEntry> mapSchema(T dataObj) {
      Map<String, ConfigObjectSerializer.SchemaEntry> schema = new LinkedHashMap<>();

      for (Field field : dataObj.getClass().getDeclaredFields()) {
         Value valueMeta = field.getAnnotation(Value.class);
         if (valueMeta != null) {
            field.setAccessible(true);
            String propertyName = valueMeta.name() != null && !valueMeta.name().isBlank() ? valueMeta.name() : field.getName();
            if (schema.containsKey(propertyName)) {
               throw new IllegalStateException(
                  "The property name '"
                     + propertyName
                     + "' has already been found to property '"
                     + schema.get(propertyName)
                     + "'. Check "
                     + dataObj.getClass().getName()
                     + "#"
                     + field.getName()
               );
            }

            try {
               IConfigProperty<?> property = this.propertyResolver.toProperty(field, dataObj, valueMeta);
               schema.put(propertyName, new ConfigObjectSerializer.SchemaEntry(field, propertyName, valueMeta, property));
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }

      if (schema.isEmpty()) {
         throw new RuntimeException("Invalid cfg class!");
      } else {
         return Collections.unmodifiableMap(schema);
      }
   }

   public void write(JsonWriter out) throws IOException {
      out.beginObject();

      for (Entry<String, ConfigObjectSerializer.SchemaEntry> entry : this.properties.entrySet()) {
         out.name(entry.getKey());
         entry.getValue().property().write(out, this.propertyResolver, this.log);
      }

      out.endObject();
   }

   public void read(JsonReader in) throws IOException {
      in.beginObject();

      while (in.hasNext()) {
         String propertyKey = in.nextName();
         ConfigObjectSerializer.SchemaEntry entry = this.properties.get(propertyKey);
         if (entry != null) {
            entry.field().setAccessible(true);
            entry.property().read(in, this.propertyResolver, this.log);
         } else {
            this.log.warn("Skipping unknown property '{}' found in user JSON.", propertyKey);
            in.skipValue();
         }
      }

      in.endObject();
   }

   private record SchemaEntry(Field field, String serializedName, Value valueMeta, IConfigProperty<?> property) {
   }
}
