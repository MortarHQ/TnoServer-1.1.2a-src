package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;

public interface IJavaNativeIntegrationRegistrationHandler {
   void registerNativeType(String var1, Class<?> var2, NativeTypeInfo var3);

   void registerZenClass(String var1, Class<?> var2, ZenTypeInfo var3);

   void registerGlobalsIn(String var1, Class<?> var2, ZenTypeInfo var3);

   void registerPreprocessor(IPreprocessor var1);
}
