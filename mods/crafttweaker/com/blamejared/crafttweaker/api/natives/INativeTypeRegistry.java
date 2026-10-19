package com.blamejared.crafttweaker.api.natives;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

public interface INativeTypeRegistry {
   Optional<String> getZenNameFor(Class<?> var1);

   Collection<IBakedTypeInfo> getBakedTypeInfo();

   Optional<IBakedTypeInfo> getBakedTypeInfoFor(Class<?> var1);

   Optional<IExecutableReferenceInfo> getExecutableReferenceInfoFor(Constructor<?> var1);

   Optional<IExecutableReferenceInfo> getExecutableReferenceInfoFor(Method var1);
}
