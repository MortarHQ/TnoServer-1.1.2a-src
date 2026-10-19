package com.blamejared.crafttweaker.impl.registry.natives;

import com.blamejared.crafttweaker.api.natives.IBakedTypeInfo;
import com.blamejared.crafttweaker.api.natives.IExecutableReferenceInfo;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

record BakedTypeInfo(String zenName, Class<?> nativeClass, Map<String, ExecutableReferenceGroupInfo> executables) implements IBakedTypeInfo {
   @Override
   public Optional<IExecutableReferenceInfo> findMethod(Constructor<?> method) {
      return this.getMethod("<init>", method.getParameterTypes());
   }

   @Override
   public Optional<IExecutableReferenceInfo> findMethod(Method method) {
      return this.getMethod(method.getName(), method.getParameterTypes());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         BakedTypeInfo that = (BakedTypeInfo)o;
         return this.zenName().equals(that.zenName());
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.zenName().hashCode();
   }

   private Optional<IExecutableReferenceInfo> getMethod(String name, Class<?>... parameterTypes) {
      return Optional.ofNullable(this.executables().get(name)).flatMap(it -> it.findSignature(parameterTypes));
   }
}
