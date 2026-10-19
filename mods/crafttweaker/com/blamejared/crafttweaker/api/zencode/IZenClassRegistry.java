package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.natives.INativeTypeRegistry;
import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IZenClassRegistry {
   boolean isRegistered(IScriptLoader var1, Class<?> var2);

   Optional<String> getNameFor(IScriptLoader var1, Class<?> var2);

   <T> List<Class<? extends T>> getImplementationsOf(IScriptLoader var1, Class<T> var2);

   IZenClassRegistry.IClassData getClassData(IScriptLoader var1);

   List<Class<?>> getClassesInPackage(IScriptLoader var1, String var2);

   List<Class<?>> getGlobalsInPackage(IScriptLoader var1, String var2);

   Set<String> getRootPackages(IScriptLoader var1);

   INativeTypeRegistry getNativeTypeRegistry(IScriptLoader var1);

   boolean isBlacklisted(Class<?> var1);

   interface IClassData {
      List<Class<?>> registeredClasses();

      BiMap<String, Class<?>> globals();

      BiMap<String, Class<?>> classes();

      Multimap<String, Class<?>> expansions();
   }
}
