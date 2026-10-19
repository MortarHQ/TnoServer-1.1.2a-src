package cc.sighs.oelib.config;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Objects;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public class ConfigAccess<T> {
   private final ConfigUnit<T> unit;

   public ConfigAccess(ConfigUnit<T> unit) {
      this.unit = unit;
   }

   public <V> void set(@NotNull ConfigAccess.Accessor<T, V> getter, @NotNull V value) {
      Objects.requireNonNull(value, "Config value cannot be null");
      T current = this.unit.get();
      String property = this.extractPropertyName(getter);
      T updated = this.updateRecord(current, property, value);
      this.unit.setValue(updated);
      this.unit.save();
   }

   private String extractPropertyName(ConfigAccess.Accessor<T, ?> getter) {
      try {
         Method m = getter.getClass().getDeclaredMethod("writeReplace");
         m.setAccessible(true);
         SerializedLambda sl = (SerializedLambda)m.invoke(getter);
         return sl.getImplMethodName();
      } catch (Exception e) {
         throw new RuntimeException("If the resolving field name cannot be referenced by a method, check if it is the Record class", e);
      }
   }

   private <V> T updateRecord(T current, String property, V value) {
      Class<?> cls = current.getClass();
      if (!cls.isRecord()) {
         throw new IllegalArgumentException("ConfigAccess only the Record type is supported:" + cls.getName());
      }

      try {
         RecordComponent[] components = cls.getRecordComponents();
         Object[] args = new Object[components.length];
         Class<?>[] types = new Class[components.length];

         for (int i = 0; i < components.length; i++) {
            RecordComponent comp = components[i];
            types[i] = comp.getType();
            if (comp.getName().equals(property)) {
               args[i] = value;
            } else {
               args[i] = comp.getAccessor().invoke(current);
            }
         }

         Constructor<?> ctor = cls.getDeclaredConstructor(types);
         ctor.setAccessible(true);
         return (T)ctor.newInstance(args);
      } catch (Exception e) {
         throw new RuntimeException("Unable to build a new Record instance to update fields:" + property, e);
      }
   }

   @FunctionalInterface
   public interface Accessor<T, R> extends Function<T, R>, Serializable {
   }
}
