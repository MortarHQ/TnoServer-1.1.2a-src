package artifacts.config.value;

import artifacts.config.value.type.ValueType;
import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;

public interface Value<T> extends Supplier<T> {
   static <T> Value<T> of(T v) {
      return new Value.Constant<>(v);
   }

   final class ConfigValue<T> implements Value<T>, StringRepresentable {
      private final ValueType<T, ?> type;
      private final String id;
      private final T defaultValue;
      private final boolean requiresRestart;
      private T value;

      public ConfigValue(ValueType<T, ?> type, String id, T defaultValue) {
         this(type, id, defaultValue, false);
      }

      public ConfigValue(ValueType<T, ?> type, String id, T defaultValue, boolean requiresRestart) {
         this.type = type;
         this.id = id;
         this.requiresRestart = requiresRestart;
         this.defaultValue = this.value = defaultValue;
      }

      public String getId() {
         return this.id;
      }

      public String getSerializedName() {
         return this.getId();
      }

      @Override
      public T get() {
         return this.value;
      }

      public void set(T value) {
         this.value = value;
      }

      public T getDefaultValue() {
         return this.defaultValue;
      }

      public ValueType<T, ?> type() {
         return this.type;
      }

      public boolean requiresRestart() {
         return this.requiresRestart;
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else {
            return !(o instanceof Value.ConfigValue<?> that)
               ? false
               : this.type.equals(that.type) && this.getId().equals(that.getId()) && this.getDefaultValue().equals(that.getDefaultValue());
         }
      }

      @Override
      public int hashCode() {
         int result = this.type.hashCode();
         result = 31 * result + this.getId().hashCode();
         return 31 * result + this.getDefaultValue().hashCode();
      }
   }

   record Constant<T>(T get) implements Value<T> {
   }
}
