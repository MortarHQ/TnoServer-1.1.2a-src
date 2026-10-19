package net.darkhax.pricklemc.common.api.config.property;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.pricklemc.common.api.config.PropertyResolver;
import net.darkhax.pricklemc.common.api.config.comment.IComment;
import net.darkhax.pricklemc.common.api.config.comment.WrappedComment;
import org.slf4j.Logger;

public class ObjectProperty<T> implements IConfigProperty<T> {
   public static final IPropertyAdapter<ObjectProperty<?>> FALLBACK_ADAPTER = new ObjectProperty.FallbackAdapter();
   private final Field field;
   private final Object parent;
   private final IComment comment;
   private final T defaultValue;
   private final boolean writeDefault;
   private final String reference;

   public ObjectProperty(Field field, Object parent, T defaultValue, Value valueMeta, IComment comment) {
      this.field = field;
      this.parent = parent;
      this.comment = comment;
      this.defaultValue = defaultValue;
      this.writeDefault = valueMeta.writeDefault();
      this.reference = valueMeta.reference();
   }

   public IComment comment() {
      return this.comment;
   }

   public T defaultValue() {
      return this.defaultValue;
   }

   public boolean writeDefaultValue() {
      return this.writeDefault;
   }

   @Override
   public T value() {
      try {
         return (T)this.field.get(this.parent);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public void read(JsonReader reader, PropertyResolver resolver, Logger logger) throws IOException {
      reader.beginObject();

      while (reader.hasNext()) {
         if ("value".equals(reader.nextName())) {
            T readValue = this.readValue(reader, resolver, logger);
            if (this.validate(readValue)) {
               try {
                  this.field.set(this.parent, readValue);
               } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
               }
            }
         } else {
            reader.skipValue();
         }
      }

      reader.endObject();
   }

   @Override
   public void write(JsonWriter writer, PropertyResolver resolver, Logger logger) throws IOException {
      writer.beginObject();
      if (this.comment() != null) {
         writer.name("//");
         resolver.gson().toJson(this.comment(), WrappedComment.class, writer);
      }

      this.writeAdditionalComments(writer, resolver, logger);
      if (this.reference != null && !this.reference.isBlank()) {
         writer.name("//reference");
         writer.value(this.reference);
      }

      if (this.writeDefaultValue() && this.defaultValue != null) {
         this.writeDefaultValue(writer, resolver, logger);
      }

      writer.name("value");
      this.writeValue(this.value(), writer, resolver, logger);
      writer.endObject();
   }

   public void writeValue(T value, JsonWriter writer, PropertyResolver resolver, Logger logger) throws IOException {
      resolver.gson().toJson(value, this.field.getGenericType(), writer);
   }

   public T readValue(JsonReader reader, PropertyResolver resolver, Logger logger) throws IOException {
      return (T)resolver.gson().fromJson(reader, this.field.getGenericType());
   }

   public void writeDefaultValue(JsonWriter writer, PropertyResolver resolver, Logger logger) throws IOException {
      writer.name("//default");
      this.writeValue(this.defaultValue(), writer, resolver, logger);
   }

   public void writeAdditionalComments(JsonWriter writer, PropertyResolver resolver, Logger logger) throws IOException {
   }

   @Override
   public boolean validate(T value) throws IllegalArgumentException {
      return true;
   }

   private static class FallbackAdapter implements IPropertyAdapter<ObjectProperty<?>> {
      public ObjectProperty<?> toValue(PropertyResolver resolver, Field field, Object parent, Object value, Value valueMeta) throws IOException {
         return new ObjectProperty<>(field, parent, value, valueMeta, resolver.toComment(field, value, valueMeta));
      }
   }
}
