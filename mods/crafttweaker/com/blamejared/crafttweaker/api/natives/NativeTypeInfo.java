package com.blamejared.crafttweaker.api.natives;

public record NativeTypeInfo(String name, Class<?> targetedType, NativeTypeInfo.Constructor[] constructors, NativeTypeInfo.Method... methods) {
   public NativeTypeInfo(String name, Class<?> targetedType, NativeTypeInfo.Constructor... constructors) {
      this(name, targetedType, constructors);
   }

   public record Constructor(NativeTypeInfo.Parameter... parameters) {
   }

   public record Method(String name, String getter, String setter, NativeTypeInfo.Parameter... parameters) {
      public Method(String name, String getter, NativeTypeInfo.Parameter... parameters) {
         this(name, getter, "", parameters);
      }

      public Method(String name, NativeTypeInfo.Parameter... parameters) {
         this(name, "", parameters);
      }
   }

   public record Parameter(Class<?> type, String name) {
   }
}
