package net.darkhax.pricklemc.common.api.config.property;

import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import net.darkhax.pricklemc.common.api.annotations.RangedDouble;
import net.darkhax.pricklemc.common.api.annotations.RangedFloat;
import net.darkhax.pricklemc.common.api.annotations.RangedInt;
import net.darkhax.pricklemc.common.api.annotations.RangedLong;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.pricklemc.common.api.config.PropertyResolver;
import net.darkhax.pricklemc.common.api.config.comment.IComment;
import net.darkhax.pricklemc.common.api.util.NumberUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class RangedProperty<T extends Number> extends ObjectProperty<T> {
   public static final IPropertyAdapter<?> ADAPTER = new RangedProperty.Adapter();
   @Nullable
   private final T min;
   @Nullable
   private final T max;

   private RangedProperty(Field field, Object parent, T defaultValue, T min, T max, Value valueMeta, IComment comment) {
      super(field, parent, defaultValue, valueMeta, comment);
      this.min = min;
      this.max = max;
      if (!this.validate(defaultValue)) {
         String errorMessage = "Value " + defaultValue + " is not within the specified range!";
         if (min != null) {
            errorMessage = errorMessage + " min=" + min;
         }

         if (max != null) {
            errorMessage = errorMessage + " max=" + max;
         }

         throw new IllegalArgumentException(errorMessage);
      }
   }

   @Nullable
   private T min() {
      return this.min;
   }

   @Nullable
   public T max() {
      return this.max;
   }

   @Override
   public void writeAdditionalComments(JsonWriter writer, PropertyResolver resolver, Logger logger) throws IOException {
      StringBuilder range = new StringBuilder();
      if (this.min() != null) {
         range.append(">=" + this.min());
      }

      if (this.max() != null) {
         if (!range.isEmpty()) {
            range.append(" AND ");
         }

         range.append("<=" + this.max());
      }

      if (!range.isEmpty()) {
         writer.name("//range");
         writer.value(range.toString());
      }
   }

   public boolean validate(T value) throws IllegalArgumentException {
      if (value == null) {
         throw new IllegalArgumentException("Number values must not be null!");
      } else if (this.min() != null && NumberUtils.lessThan(value, this.min())) {
         throw new IllegalArgumentException("Value '" + value + "' is less than the minimum value '" + this.min() + "'.");
      } else if (this.max() != null && NumberUtils.greaterThan(value, this.max())) {
         throw new IllegalArgumentException("Value '" + value + "' is greater than the maximum value '" + this.max() + "'.");
      } else {
         return (this.min() == null || !NumberUtils.lessThan(value, this.min())) && (this.max() == null || !NumberUtils.greaterThan(value, this.max()));
      }
   }

   private static class Adapter implements IPropertyAdapter<RangedProperty<?>> {
      public RangedProperty<?> toValue(PropertyResolver resolver, Field field, Object parent, Object value, Value valueMeta) throws IOException {
         if (value instanceof Integer intVal) {
            RangedInt ranged = field.getAnnotation(RangedInt.class);
            if (ranged != null) {
               Integer min = ranged.min() != Integer.MIN_VALUE ? ranged.min() : null;
               Integer max = ranged.max() != Integer.MAX_VALUE ? ranged.max() : null;
               return new RangedProperty<>(field, parent, intVal, min, max, valueMeta, resolver.toComment(field, value, valueMeta));
            }
         }

         if (value instanceof Long longVal) {
            RangedLong ranged = field.getAnnotation(RangedLong.class);
            if (ranged != null) {
               Long min = ranged.min() != Long.MIN_VALUE ? ranged.min() : null;
               Long max = ranged.max() != Long.MAX_VALUE ? ranged.max() : null;
               return new RangedProperty<>(field, parent, longVal, min, max, valueMeta, resolver.toComment(field, value, valueMeta));
            }
         }

         if (value instanceof Float floatVal) {
            RangedFloat ranged = field.getAnnotation(RangedFloat.class);
            if (ranged != null) {
               Float min = ranged.min() != -Float.MAX_VALUE ? ranged.min() : null;
               Float max = ranged.max() != Float.MAX_VALUE ? ranged.max() : null;
               return new RangedProperty<>(field, parent, floatVal, min, max, valueMeta, resolver.toComment(field, value, valueMeta));
            }
         }

         if (value instanceof Double doubleVal) {
            RangedDouble ranged = field.getAnnotation(RangedDouble.class);
            if (ranged != null) {
               Double min = ranged.min() != -Double.MAX_VALUE ? ranged.min() : null;
               Double max = ranged.max() != Double.MAX_VALUE ? ranged.max() : null;
               return new RangedProperty<>(field, parent, doubleVal, min, max, valueMeta, resolver.toComment(field, value, valueMeta));
            }
         }

         return null;
      }
   }
}
