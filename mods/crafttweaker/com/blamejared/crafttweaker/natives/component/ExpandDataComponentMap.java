package com.blamejared.crafttweaker.natives.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import java.util.Set;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.component.DataComponentMap.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/component/DataComponentMap")
@NativeTypeRegistration(value = DataComponentMap.class, zenCodeName = "crafttweaker.api.component.DataComponentMap")
public class ExpandDataComponentMap {
   @ZenCodeType.Getter("size")
   public static int size(DataComponentMap internal) {
      return internal.size();
   }

   @ZenCodeType.Method
   public static <T> T getOrDefault(DataComponentMap internal, Class<T> tClass, DataComponentType<T> type, T defaultValue) {
      return (T)internal.getOrDefault(type, defaultValue);
   }

   @ZenCodeType.Method
   public static <T> @ZenCodeType.Nullable TypedDataComponent<T> getTyped(DataComponentMap internal, Class<T> tClass, DataComponentType<T> type) {
      return internal.getTyped(type);
   }

   @ZenCodeType.Method
   public static <T> @ZenCodeType.Nullable T getComponent(DataComponentMap internal, Class<T> tClass, DataComponentType<T> type) {
      return (T)internal.get(type);
   }

   @ZenCodeType.Getter("list")
   public static List<TypedDataComponent<?>> list(DataComponentMap internal) {
      return internal.stream().toList();
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
   public static boolean has(DataComponentMap internal, DataComponentType<?> type) {
      return internal.has(type);
   }

   @ZenCodeType.Getter("keySet")
   public static Set<DataComponentType<?>> keySet(DataComponentMap internal) {
      return internal.keySet();
   }

   @ZenCodeType.Getter("isEmpty")
   public static boolean isEmpty(DataComponentMap internal) {
      return internal.isEmpty();
   }

   @ZenCodeType.StaticExpansionMethod
   public static DataComponentMap composite(DataComponentMap a, DataComponentMap b) {
      return DataComponentMap.composite(a, b);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Builder builder() {
      return DataComponentMap.builder();
   }

   @ZenRegister
   @Document("vanilla/api/component/DataComponentMapBuilder")
   @NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.component.DataComponentMapBuilder")
   public static class ExpandDataComponentMapBuilder {
      @ZenCodeType.Method
      public static <T> Builder setComponent(Builder internal, Class<T> tClass, DataComponentType<T> type, @ZenCodeType.Nullable T value) {
         return internal.set(type, value);
      }

      @ZenCodeType.Method
      public static DataComponentMap build(Builder internal) {
         return internal.build();
      }

      @ZenCodeType.Method
      public static Builder addAll(Builder internal, DataComponentMap other) {
         return internal.addAll(other);
      }
   }
}
