package com.blackgear.platform.core.util.config;

import com.electronwill.nightconfig.core.EnumGetMethod;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus.Internal;

public interface ConfigBuilder {
   Splitter DOT_SPLITTER = Splitter.on(".");

   @Internal
   static List<String> split(String path) {
      return Lists.newArrayList(DOT_SPLITTER.split(path));
   }

   default <T> ConfigBuilder.ConfigValue<T> define(String path, T defaultValue) {
      return this.define(split(path), defaultValue);
   }

   default <T> ConfigBuilder.ConfigValue<T> define(List<String> path, T defaultValue) {
      return this.define(path, defaultValue, o -> o != null && defaultValue.getClass().isAssignableFrom(o.getClass()));
   }

   default <T> ConfigBuilder.ConfigValue<T> define(String path, T defaultValue, Predicate<Object> validator) {
      return this.define(split(path), defaultValue, validator);
   }

   default <T> ConfigBuilder.ConfigValue<T> define(List<String> path, T defaultValue, Predicate<Object> validator) {
      Objects.requireNonNull(defaultValue, "Default value can not be null");
      return this.define(path, () -> defaultValue, validator);
   }

   default <T> ConfigBuilder.ConfigValue<T> define(String path, Supplier<T> defaultSupplier, Predicate<Object> validator) {
      return this.define(split(path), defaultSupplier, validator);
   }

   default <T> ConfigBuilder.ConfigValue<T> define(List<String> path, Supplier<T> defaultSupplier, Predicate<Object> validator) {
      return this.define(path, defaultSupplier, validator, Object.class);
   }

   <T> ConfigBuilder.ConfigValue<T> define(List<String> var1, Supplier<T> var2, Predicate<Object> var3, Class<?> var4);

   default <V extends Comparable<? super V>> ConfigBuilder.ConfigValue<V> defineInRange(String path, V defaultValue, V min, V max, Class<V> clazz) {
      return this.defineInRange(split(path), defaultValue, min, max, clazz);
   }

   default <V extends Comparable<? super V>> ConfigBuilder.ConfigValue<V> defineInRange(List<String> path, V defaultValue, V min, V max, Class<V> clazz) {
      return this.defineInRange(path, () -> defaultValue, min, max, clazz);
   }

   default <V extends Comparable<? super V>> ConfigBuilder.ConfigValue<V> defineInRange(String path, Supplier<V> defaultSupplier, V min, V max, Class<V> clazz) {
      return this.defineInRange(split(path), defaultSupplier, min, max, clazz);
   }

   <V extends Comparable<? super V>> ConfigBuilder.ConfigValue<V> defineInRange(List<String> var1, Supplier<V> var2, V var3, V var4, Class<V> var5);

   default <T> ConfigBuilder.ConfigValue<T> defineInList(String path, T defaultValue, Collection<? extends T> acceptableValues) {
      return this.defineInList(split(path), defaultValue, acceptableValues);
   }

   default <T> ConfigBuilder.ConfigValue<T> defineInList(String path, Supplier<T> defaultSupplier, Collection<? extends T> acceptableValues) {
      return this.defineInList(split(path), defaultSupplier, acceptableValues);
   }

   default <T> ConfigBuilder.ConfigValue<T> defineInList(List<String> path, T defaultValue, Collection<? extends T> acceptableValues) {
      return this.defineInList(path, () -> defaultValue, acceptableValues);
   }

   default <T> ConfigBuilder.ConfigValue<T> defineInList(List<String> path, Supplier<T> defaultSupplier, Collection<? extends T> acceptableValues) {
      return this.define(path, defaultSupplier, acceptableValues::contains);
   }

   default <T> ConfigBuilder.ConfigValue<List<? extends T>> defineList(String path, List<? extends T> defaultValue, Predicate<Object> elementValidator) {
      return this.defineList(split(path), defaultValue, elementValidator);
   }

   default <T> ConfigBuilder.ConfigValue<List<? extends T>> defineList(
      String path, Supplier<List<? extends T>> defaultSupplier, Predicate<Object> elementValidator
   ) {
      return this.defineList(split(path), defaultSupplier, elementValidator);
   }

   default <T> ConfigBuilder.ConfigValue<List<? extends T>> defineList(List<String> path, List<? extends T> defaultValue, Predicate<Object> elementValidator) {
      return this.defineList(path, () -> defaultValue, elementValidator);
   }

   <T> ConfigBuilder.ConfigValue<List<? extends T>> defineList(List<String> var1, Supplier<List<? extends T>> var2, Predicate<Object> var3);

   <T> ConfigBuilder.ConfigValue<List<? extends T>> defineListAllowEmpty(List<String> var1, Supplier<List<? extends T>> var2, Predicate<Object> var3);

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(String path, V defaultValue) {
      return this.defineEnum(split(path), defaultValue);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(String path, V defaultValue, EnumGetMethod converter) {
      return this.defineEnum(split(path), defaultValue, converter);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(List<String> path, V defaultValue) {
      return this.defineEnum(path, defaultValue, defaultValue.getDeclaringClass().getEnumConstants());
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(List<String> path, V defaultValue, EnumGetMethod converter) {
      return this.defineEnum(path, defaultValue, converter, defaultValue.getDeclaringClass().getEnumConstants());
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(String path, V defaultValue, V... acceptableValues) {
      return this.defineEnum(split(path), defaultValue, acceptableValues);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(String path, V defaultValue, EnumGetMethod converter, V... acceptableValues) {
      return this.defineEnum(split(path), defaultValue, converter, acceptableValues);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(List<String> path, V defaultValue, V... acceptableValues) {
      return this.defineEnum(path, defaultValue, Arrays.asList(acceptableValues));
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(List<String> path, V defaultValue, EnumGetMethod converter, V... acceptableValues) {
      return this.defineEnum(path, defaultValue, converter, Arrays.asList(acceptableValues));
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(String path, V defaultValue, Collection<V> acceptableValues) {
      return this.defineEnum(split(path), defaultValue, acceptableValues);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(String path, V defaultValue, EnumGetMethod converter, Collection<V> acceptableValues) {
      return this.defineEnum(split(path), defaultValue, converter, acceptableValues);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(List<String> path, V defaultValue, Collection<V> acceptableValues) {
      return this.defineEnum(path, defaultValue, EnumGetMethod.NAME_IGNORECASE, acceptableValues);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(
      List<String> path, V defaultValue, EnumGetMethod converter, Collection<V> acceptableValues
   ) {
      return this.defineEnum(path, defaultValue, converter, obj -> {
         if (obj == null) {
            return false;
         }

         if (obj instanceof Enum) {
            return acceptableValues.contains(obj);
         }

         try {
            return acceptableValues.contains(converter.get(obj, defaultValue.getDeclaringClass()));
         } catch (IllegalArgumentException | ClassCastException e) {
            return false;
         }
      });
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(String path, V defaultValue, Predicate<Object> validator) {
      return this.defineEnum(split(path), defaultValue, validator);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(String path, V defaultValue, EnumGetMethod converter, Predicate<Object> validator) {
      return this.defineEnum(split(path), defaultValue, converter, validator);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(List<String> path, V defaultValue, Predicate<Object> validator) {
      return this.defineEnum(path, () -> defaultValue, validator, defaultValue.getDeclaringClass());
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(List<String> path, V defaultValue, EnumGetMethod converter, Predicate<Object> validator) {
      return this.defineEnum(path, () -> defaultValue, converter, validator, defaultValue.getDeclaringClass());
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(String path, Supplier<V> defaultSupplier, Predicate<Object> validator, Class<V> clazz) {
      return this.defineEnum(split(path), defaultSupplier, validator, clazz);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(
      String path, Supplier<V> defaultSupplier, EnumGetMethod converter, Predicate<Object> validator, Class<V> clazz
   ) {
      return this.defineEnum(split(path), defaultSupplier, converter, validator, clazz);
   }

   default <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(
      List<String> path, Supplier<V> defaultSupplier, Predicate<Object> validator, Class<V> clazz
   ) {
      return this.defineEnum(path, defaultSupplier, EnumGetMethod.NAME_IGNORECASE, validator, clazz);
   }

   <V extends Enum<V>> ConfigBuilder.ConfigValue<V> defineEnum(List<String> var1, Supplier<V> var2, EnumGetMethod var3, Predicate<Object> var4, Class<V> var5);

   default ConfigBuilder.ConfigValue<Boolean> define(String path, boolean defaultValue) {
      return this.define(split(path), defaultValue);
   }

   default ConfigBuilder.ConfigValue<Boolean> define(List<String> path, boolean defaultValue) {
      return this.define(path, () -> defaultValue);
   }

   default ConfigBuilder.ConfigValue<Boolean> define(String path, Supplier<Boolean> defaultSupplier) {
      return this.define(split(path), defaultSupplier);
   }

   ConfigBuilder.ConfigValue<Boolean> define(List<String> var1, Supplier<Boolean> var2);

   default ConfigBuilder.ConfigValue<Double> defineInRange(String path, double defaultValue, double min, double max) {
      return this.defineInRange(split(path), defaultValue, min, max);
   }

   default ConfigBuilder.ConfigValue<Double> defineInRange(List<String> path, double defaultValue, double min, double max) {
      return this.defineInRange(path, () -> defaultValue, min, max);
   }

   default ConfigBuilder.ConfigValue<Double> defineInRange(String path, Supplier<Double> defaultSupplier, double min, double max) {
      return this.defineInRange(split(path), defaultSupplier, min, max);
   }

   ConfigBuilder.ConfigValue<Double> defineInRange(List<String> var1, Supplier<Double> var2, double var3, double var5);

   default ConfigBuilder.ConfigValue<Integer> defineInRange(String path, int defaultValue, int min, int max) {
      return this.defineInRange(split(path), defaultValue, min, max);
   }

   default ConfigBuilder.ConfigValue<Integer> defineInRange(List<String> path, int defaultValue, int min, int max) {
      return this.defineInRange(path, () -> defaultValue, min, max);
   }

   default ConfigBuilder.ConfigValue<Integer> defineInRange(String path, Supplier<Integer> defaultSupplier, int min, int max) {
      return this.defineInRange(split(path), defaultSupplier, min, max);
   }

   ConfigBuilder.ConfigValue<Integer> defineInRange(List<String> var1, Supplier<Integer> var2, int var3, int var4);

   default ConfigBuilder.ConfigValue<Long> defineInRange(String path, long defaultValue, long min, long max) {
      return this.defineInRange(split(path), defaultValue, min, max);
   }

   default ConfigBuilder.ConfigValue<Long> defineInRange(List<String> path, long defaultValue, long min, long max) {
      return this.defineInRange(path, () -> defaultValue, min, max);
   }

   default ConfigBuilder.ConfigValue<Long> defineInRange(String path, Supplier<Long> defaultSupplier, long min, long max) {
      return this.defineInRange(split(path), defaultSupplier, min, max);
   }

   ConfigBuilder.ConfigValue<Long> defineInRange(List<String> var1, Supplier<Long> var2, long var3, long var5);

   ConfigBuilder comment(String var1);

   ConfigBuilder comment(String... var1);

   ConfigBuilder translation(String var1);

   ConfigBuilder worldRestart();

   default ConfigBuilder push(String path) {
      return this.push(split(path));
   }

   ConfigBuilder push(List<String> var1);

   default ConfigBuilder pop() {
      return this.pop(1);
   }

   ConfigBuilder pop(int var1);

   interface ConfigValue<T> {
      List<String> getPath();

      T get();

      ConfigBuilder next();

      void save();

      void set(T var1);

      void clearCache();
   }
}
