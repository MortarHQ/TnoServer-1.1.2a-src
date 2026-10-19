package net.darkhax.pricklemc.common.api.config.property.array;

import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import net.darkhax.pricklemc.common.api.annotations.Array;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.pricklemc.common.api.config.PropertyResolver;
import net.darkhax.pricklemc.common.api.config.comment.IComment;
import net.darkhax.pricklemc.common.api.config.property.IPropertyAdapter;
import org.slf4j.Logger;

public class CollectionArrayProperty<T extends Collection<?>> extends AbstractArrayProperty<T> {
   public static final CollectionArrayProperty.Adapter ADAPTER = new CollectionArrayProperty.Adapter();
   private final ParameterizedType paramType;

   private CollectionArrayProperty(
      Field field, Object parent, T defaultValue, ParameterizedType paramType, Value valueMeta, ArraySettings meta, IComment comment
   ) {
      super(field, parent, defaultValue, valueMeta, meta, comment);
      this.paramType = paramType;
   }

   public boolean isOverInlineThreshold(T value) {
      return value.size() > this.settings().inlineCount();
   }

   public boolean isComplex(T value) {
      for (Object entry : value) {
         if (!AbstractArrayProperty.BASIC_TYPES.contains(entry.getClass())) {
            return true;
         }
      }

      return false;
   }

   public boolean isEmpty(T value) {
      return value.isEmpty();
   }

   public void writeArrayValues(T value, JsonWriter out, PropertyResolver resolver, Logger log) {
      for (Object o : value) {
         resolver.gson().toJson(o, this.paramType.getActualTypeArguments()[0], out);
      }
   }

   private static class Adapter implements IPropertyAdapter<CollectionArrayProperty<?>> {
      public CollectionArrayProperty<?> toValue(PropertyResolver resolver, Field field, Object parent, Object value, Value valueMeta) throws IOException {
         if (value instanceof Collection<?> collection && field.getGenericType() instanceof ParameterizedType paramType) {
            Array arrayMeta = field.getAnnotation(Array.class);
            ArraySettings settings = arrayMeta != null ? new ArraySettings(arrayMeta) : ArraySettings.DEFAULT;
            return new CollectionArrayProperty<>(field, parent, collection, paramType, valueMeta, settings, resolver.toComment(field, value, valueMeta));
         } else {
            return null;
         }
      }
   }
}
