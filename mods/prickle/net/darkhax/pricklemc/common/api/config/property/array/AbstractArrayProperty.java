package net.darkhax.pricklemc.common.api.config.property.array;

import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.pricklemc.common.api.config.PropertyResolver;
import net.darkhax.pricklemc.common.api.config.comment.IComment;
import net.darkhax.pricklemc.common.api.config.property.ObjectProperty;
import org.slf4j.Logger;

public abstract class AbstractArrayProperty<T> extends ObjectProperty<T> {
   public static final Set<Class<?>> BASIC_TYPES = Set.of(
      Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, String.class
   );
   private final ArraySettings settings;

   public AbstractArrayProperty(Field field, Object parent, T defaultValue, Value valueMeta, ArraySettings meta, IComment comment) {
      super(field, parent, defaultValue, valueMeta, comment);
      this.settings = meta;
   }

   public abstract boolean isOverInlineThreshold(T var1);

   public abstract boolean isComplex(T var1);

   public abstract boolean isEmpty(T var1);

   public abstract void writeArrayValues(T var1, JsonWriter var2, PropertyResolver var3, Logger var4);

   public ArraySettings settings() {
      return this.settings;
   }

   @Override
   public void writeValue(T value, JsonWriter out, PropertyResolver resolver, Logger log) throws IOException {
      if (this.isOverInlineThreshold(value) || !this.settings.inlineComplex() && this.isComplex(value)) {
         out.beginArray();
         this.writeArrayValues(value, out, resolver, log);
         out.endArray();
      } else {
         out.beginArray();
         out.setIndent("");
         this.writeArrayValues(value, out, resolver, log);
         out.endArray();
         out.setIndent("  ");
      }
   }

   @Override
   public void writeAdditionalComments(JsonWriter out, PropertyResolver resolver, Logger log) throws IOException {
      if (!this.settings.allowEmpty()) {
         out.name("//empty-allowed");
         out.value(false);
      }
   }

   @Override
   public boolean validate(T value) throws IllegalArgumentException {
      if (this.isEmpty(value) && !this.settings.allowEmpty()) {
         throw new IllegalArgumentException("Value must not be empty, at least one entry is required!");
      } else {
         return true;
      }
   }
}
