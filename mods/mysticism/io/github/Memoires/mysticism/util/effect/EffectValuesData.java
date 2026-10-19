package io.github.Memoires.mysticism.util.effect;

import java.util.HashMap;
import java.util.Map;

public class EffectValuesData {
   private final Map<String, Object> values = new HashMap<>();

   public void set(String key, Object value) {
      if (value == null) {
         this.values.remove(key);
      } else {
         this.values.put(key, value);
      }
   }

   public Object get(String key) {
      return this.values.get(key);
   }

   public boolean has(String key) {
      return this.values.containsKey(key);
   }

   public void remove(String key) {
      this.values.remove(key);
   }

   public boolean isEmpty() {
      return this.values.isEmpty();
   }

   public Float getFloat(String key) {
      return this.values.get(key) instanceof Number number ? number.floatValue() : null;
   }

   public Double getDouble(String key) {
      return this.values.get(key) instanceof Number number ? number.doubleValue() : null;
   }

   public Integer getInt(String key) {
      return this.values.get(key) instanceof Number number ? number.intValue() : null;
   }

   public Long getLong(String key) {
      return this.values.get(key) instanceof Number number ? number.longValue() : null;
   }

   public Boolean getBoolean(String key) {
      return this.values.get(key) instanceof Boolean bool ? bool : null;
   }

   public String getString(String key) {
      return this.values.get(key) instanceof String str ? str : null;
   }
}
