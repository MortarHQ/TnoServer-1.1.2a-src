package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMaps;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

final class JavaNativeIntegrationRegistrationHandler implements IJavaNativeIntegrationRegistrationHandler {
   private final Map<String, IPreprocessor> preprocessors = new HashMap<>();
   private final List<JavaNativeIntegrationRegistrationHandler.NativeClassRequest> nativeClassRequests = new ArrayList<>();
   private final Object2BooleanMap<JavaNativeIntegrationRegistrationHandler.ZenClassRequest> zenClassRequests = new Object2BooleanOpenHashMap();

   private JavaNativeIntegrationRegistrationHandler() {
   }

   static JavaNativeIntegrationRegistrationHandler of(Consumer<IJavaNativeIntegrationRegistrationHandler> consumer) {
      JavaNativeIntegrationRegistrationHandler handler = new JavaNativeIntegrationRegistrationHandler();
      consumer.accept(handler);
      return handler;
   }

   @Override
   public void registerNativeType(String loader, Class<?> clazz, NativeTypeInfo info) {
      this.nativeClassRequests.add(new JavaNativeIntegrationRegistrationHandler.NativeClassRequest(loader, info));
      this.zenClassRequests.put(new JavaNativeIntegrationRegistrationHandler.ZenClassRequest(loader, clazz, ZenTypeInfo.from(info)), false);
   }

   @Override
   public void registerZenClass(String loader, Class<?> clazz, ZenTypeInfo info) {
      this.zenClassRequests.put(new JavaNativeIntegrationRegistrationHandler.ZenClassRequest(loader, clazz, info), false);
   }

   @Override
   public void registerGlobalsIn(String loader, Class<?> clazz, ZenTypeInfo info) {
      if (info.kind() == ZenTypeInfo.TypeKind.EXPANSION) {
         throw new IllegalArgumentException("Unable to register globals for " + clazz.getName() + " because it is an expansion: use a normal class instead");
      }

      JavaNativeIntegrationRegistrationHandler.ZenClassRequest request = new JavaNativeIntegrationRegistrationHandler.ZenClassRequest(loader, clazz, info);
      if (!this.zenClassRequests.containsKey(request)) {
         throw new IllegalArgumentException("Unable to register globals for " + clazz.getName() + " because it is unknown: register it first");
      }

      this.zenClassRequests.put(request, true);
   }

   @Override
   public void registerPreprocessor(IPreprocessor preprocessor) {
      String name = preprocessor.name();
      IPreprocessor other = this.preprocessors.get(name);
      if (other != null) {
         throw new IllegalArgumentException("A preprocessor with the name '" + name + "' is already registered: old " + other + ", new " + preprocessor);
      }

      this.preprocessors.put(name, preprocessor);
   }

   Collection<IPreprocessor> preprocessors() {
      return Collections.unmodifiableCollection(this.preprocessors.values());
   }

   List<JavaNativeIntegrationRegistrationHandler.NativeClassRequest> nativeClassRequests() {
      return Collections.unmodifiableList(this.nativeClassRequests);
   }

   Object2BooleanMap<JavaNativeIntegrationRegistrationHandler.ZenClassRequest> zenClassRequests() {
      return Object2BooleanMaps.unmodifiable(this.zenClassRequests);
   }

   record NativeClassRequest(String loader, NativeTypeInfo info) {
   }

   record ZenClassRequest(String loader, Class<?> clazz, ZenTypeInfo info) {
   }
}
