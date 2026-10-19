package com.blamejared.crafttweaker.natives.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Optional;
import java.util.Set;
import net.minecraft.Optionull;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.component.DataComponentPatch.Builder;
import net.minecraft.core.component.DataComponentPatch.SplitResult;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/component/DataComponentPatch")
@NativeTypeRegistration(value = DataComponentPatch.class, zenCodeName = "crafttweaker.api.component.DataComponentPatch")
public class ExpandDataComponentPatch {
   @ZenCodeType.StaticExpansionMethod
   public static Builder builder() {
      return DataComponentPatch.builder();
   }

   @ZenCodeType.Method
   public static <T> DataComponentPatch forget(DataComponentPatch internal, Class<T> tClass, DataComponentType<T> component) {
      return internal.forget(dataComponentType -> dataComponentType == component);
   }

   @ZenCodeType.Getter("isEmpty")
   public static boolean isEmpty(DataComponentPatch internal) {
      return internal.isEmpty();
   }

   @ZenCodeType.Getter("size")
   public static int size(DataComponentPatch internal) {
      return internal.size();
   }

   @ZenCodeType.Method
   public static <T> @ZenCodeType.Nullable T getComponent(DataComponentPatch internal, DataComponentType<T> type) {
      Optional<? extends T> component = internal.get(type);
      return (T)Optionull.map(component, t -> t.orElse(null));
   }

   @ZenCodeType.Method
   public static SplitResult split(DataComponentPatch internal) {
      return internal.split();
   }

   @ZenRegister
   @Document("vanilla/api/component/DataComponentPatchBuilder")
   @NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.component.DataComponentPatchBuilder")
   public static class ExpandDataComponentPatchBuilder {
      @ZenCodeType.Method
      public static <T> Builder setComponent(Builder internal, Class<T> tClass, DataComponentType<T> type, T value) {
         return internal.set(type, value);
      }

      @ZenCodeType.Method
      public static <T> Builder remove(Builder internal, Class<T> tClass, DataComponentType<T> type) {
         return internal.remove(type);
      }

      @ZenCodeType.Method
      public static <T> Builder setComponent(Builder internal, Class<T> tClass, TypedDataComponent<T> type) {
         return internal.set(type);
      }

      @ZenCodeType.Method
      public static DataComponentPatch build(Builder internal) {
         return internal.build();
      }
   }

   @ZenRegister
   @Document("vanilla/api/component/DataComponentPatchSplitResult")
   @NativeTypeRegistration(value = SplitResult.class, zenCodeName = "crafttweaker.api.component.DataComponentPatchSplitResult")
   public static class ExpandDataComponentPatchSplitResult {
      @ZenCodeType.Getter("added")
      public static DataComponentMap added(SplitResult internal) {
         return internal.added();
      }

      @ZenCodeType.Getter("removed")
      public static Set<DataComponentType<?>> removed(SplitResult internal) {
         return internal.removed();
      }
   }
}
