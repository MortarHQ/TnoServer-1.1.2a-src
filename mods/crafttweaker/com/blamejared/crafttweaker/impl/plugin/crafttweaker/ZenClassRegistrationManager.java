package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker_annotations.annotations.NativeMethod;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.ArrayDeque;
import java.util.Queue;
import org.openzen.zencode.java.ZenCodeType;

final class ZenClassRegistrationManager {
   private final AnnotationsToApiConverters converters = new AnnotationsToApiConverters();
   private final Table<Class<?>, String, NativeTypeInfo> foundNatives = HashBasedTable.create();
   private final Queue<ZenClassRegistrationManager.LateRegistrationCandidate> lateRegistrations = new ArrayDeque<>();

   void attemptRegistration(String loader, Class<?> clazz, IJavaNativeIntegrationRegistrationHandler handler) {
      this.attemptNativeRegistration(clazz, loader, handler);
      this.attemptZenRegistration(clazz, loader, handler);
      this.attemptPreprocessorRegistration(clazz, handler);
   }

   void attemptDeferredRegistration(IJavaNativeIntegrationRegistrationHandler handler) {
      this.lateRegistrations.forEach(it -> it.callback().callback(it.clazz(), it.loader(), handler));
      this.lateRegistrations.clear();
   }

   private void attemptNativeRegistration(Class<?> clazz, String loader, IJavaNativeIntegrationRegistrationHandler handler) {
      NativeTypeRegistration ntr = clazz.getDeclaredAnnotation(NativeTypeRegistration.class);
      if (ntr != null) {
         NativeMethod[] methods = clazz.getDeclaredAnnotationsByType(NativeMethod.class);
         NativeTypeInfo nativeTypeInfo = this.converters.toNativeTypeInfo(ntr, methods);
         NativeTypeInfo previous = (NativeTypeInfo)this.foundNatives.get(nativeTypeInfo.targetedType(), loader);
         if (previous != null) {
            CraftTweakerCommon.logger()
               .warn(
                  "Found two native expansions for the same class {} in loader {}, current {}, new {}: this will lead to issues",
                  nativeTypeInfo.targetedType().getName(),
                  loader,
                  previous,
                  nativeTypeInfo
               );
         }

         this.foundNatives.put(nativeTypeInfo.targetedType(), loader, nativeTypeInfo);
         handler.registerNativeType(loader, clazz, nativeTypeInfo);
      }
   }

   private void attemptZenRegistration(Class<?> clazz, String loader, IJavaNativeIntegrationRegistrationHandler handler) {
      this.attemptZenClassRegistration(clazz, loader, handler);
      this.attemptZenExpandRegistration(clazz, loader, handler);
      this.orFuture(clazz, loader, handler, this::attemptTypedExpandRegistration);
   }

   private void attemptZenClassRegistration(Class<?> clazz, String loader, IJavaNativeIntegrationRegistrationHandler handler) {
      ZenCodeType.Name name = clazz.getDeclaredAnnotation(ZenCodeType.Name.class);
      if (name != null) {
         ZenTypeInfo typeInfo = this.converters.toZenTypeInfo(name);
         handler.registerZenClass(loader, clazz, typeInfo);
         handler.registerGlobalsIn(loader, clazz, typeInfo);
      }
   }

   private void attemptZenExpandRegistration(Class<?> clazz, String loader, IJavaNativeIntegrationRegistrationHandler handler) {
      ZenCodeType.Expansion expansion = clazz.getDeclaredAnnotation(ZenCodeType.Expansion.class);
      if (expansion != null) {
         handler.registerZenClass(loader, clazz, this.converters.toZenTypeInfo(expansion));
      }
   }

   private void attemptTypedExpandRegistration(Class<?> clazz, String loader, IJavaNativeIntegrationRegistrationHandler handler) {
      TypedExpansion expansion = clazz.getDeclaredAnnotation(TypedExpansion.class);
      if (expansion != null) {
         Class<?> target = expansion.value();
         String targetName = this.figureOutTypedExpansionName(loader, target);
         if (targetName == null) {
            throw new IllegalStateException("Unable to register typed expansion for unknown type " + clazz.getName());
         }

         handler.registerZenClass(loader, clazz, new ZenTypeInfo(targetName, ZenTypeInfo.TypeKind.EXPANSION));
      }
   }

   private void attemptPreprocessorRegistration(Class<?> clazz, IJavaNativeIntegrationRegistrationHandler handler) {
      Preprocessor preprocessor = clazz.getDeclaredAnnotation(Preprocessor.class);
      if (preprocessor != null) {
         handler.registerPreprocessor(InstantiationUtil.getOrCreateInstance((Class<IPreprocessor>)clazz));
      }
   }

   private void orFuture(Class<?> clazz, String loader, IJavaNativeIntegrationRegistrationHandler handler, ZenClassRegistrationManager.LateCallback callback) {
      try {
         callback.callback(clazz, loader, handler);
      } catch (Exception e) {
         this.lateRegistrations.add(new ZenClassRegistrationManager.LateRegistrationCandidate(clazz, loader, callback));
      }
   }

   private String figureOutTypedExpansionName(String loader, Class<?> target) {
      NativeTypeRegistration registration = target.getDeclaredAnnotation(NativeTypeRegistration.class);
      if (registration != null) {
         return registration.zenCodeName();
      }

      ZenCodeType.Name name = target.getDeclaredAnnotation(ZenCodeType.Name.class);
      if (name != null) {
         return name.value();
      }

      NativeTypeInfo knownNative = (NativeTypeInfo)this.foundNatives.get(target, loader);
      return knownNative != null ? knownNative.name() : null;
   }

   @FunctionalInterface
   private interface LateCallback {
      void callback(Class<?> var1, String var2, IJavaNativeIntegrationRegistrationHandler var3);
   }

   private record LateRegistrationCandidate(Class<?> clazz, String loader, ZenClassRegistrationManager.LateCallback callback) {
   }
}
