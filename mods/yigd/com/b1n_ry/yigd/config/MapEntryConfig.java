package com.b1n_ry.yigd.config;

public abstract class MapEntryConfig<T> {
   public String key;
   public T value;

   public MapEntryConfig() {
      this.key = "";
   }

   public MapEntryConfig(String key) {
      this.key = key;
   }

   public static class IntType extends MapEntryConfig<Integer> {
      public IntType() {
         this.value = 0;
      }

      public IntType(String key, int value) {
         super(key);
         this.value = value;
      }
   }

   public static class StringType extends MapEntryConfig<String> {
      public StringType() {
         this.value = "";
      }

      public StringType(String key, String value) {
         super(key);
         this.value = value;
      }
   }
}
