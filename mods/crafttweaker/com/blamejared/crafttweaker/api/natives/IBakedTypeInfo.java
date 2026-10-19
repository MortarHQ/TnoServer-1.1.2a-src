package com.blamejared.crafttweaker.api.natives;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;

public interface IBakedTypeInfo {
   String zenName();

   Class<?> nativeClass();

   Optional<IExecutableReferenceInfo> findMethod(Constructor<?> var1);

   Optional<IExecutableReferenceInfo> findMethod(Method var1);
}
