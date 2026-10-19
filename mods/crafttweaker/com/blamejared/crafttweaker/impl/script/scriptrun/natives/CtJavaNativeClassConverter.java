package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.natives.INativeTypeRegistry;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeClassConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;

final class CtJavaNativeClassConverter extends JavaNativeClassConverter {
   private final IZenClassRegistry zenClassRegistry;
   private final IScriptRunInfo info;

   CtJavaNativeClassConverter(
      JavaNativePackageInfo packageInfo,
      JavaNativeTypeConversionContext typeConversionContext,
      JavaNativeTypeConverter typeConverter,
      JavaNativeHeaderConverter headerConverter,
      JavaNativeMemberConverter memberConverter,
      IZenClassRegistry zenClassRegistry,
      IScriptRunInfo info
   ) {
      super(typeConverter, memberConverter, packageInfo, typeConversionContext, headerConverter);
      this.zenClassRegistry = zenClassRegistry;
      this.info = info;
   }

   @Override
   public String getNameForScripts(Class<?> cls) {
      return this.getNativeTypeRegistry().getZenNameFor(cls).orElseGet(() -> {
         if (cls.getCanonicalName().startsWith("net.minecraft")) {
            CommonLoggers.zenCode().trace("Minecraft type referenced but not registered: {}", cls.getCanonicalName());
         }

         return super.getNameForScripts(cls);
      });
   }

   @Override
   public boolean shouldLoadClass(Class<?> cls) {
      if (this.zenClassRegistry.isBlacklisted(cls)) {
         CommonLoggers.zenCode().info("Not loading class because of blacklist: {}", cls.getCanonicalName());
         return false;
      } else {
         return super.shouldLoadClass(cls);
      }
   }

   @Override
   protected ZenCodeType.Constructor getConstructorAnnotation(Constructor<?> constructor) {
      return this.getNativeTypeRegistry()
         .getExecutableReferenceInfoFor(constructor)
         .flatMap(it -> it.getAnnotation(ZenCodeType.Constructor.class))
         .orElseGet(() -> super.getConstructorAnnotation(constructor));
   }

   @Override
   protected <T extends Annotation> T getAnnotation(Method method, Class<T> cls) {
      return this.getNativeTypeRegistry()
         .getExecutableReferenceInfoFor(method)
         .flatMap(it -> it.getAnnotation(cls))
         .orElseGet(() -> super.getAnnotation(method, cls));
   }

   private INativeTypeRegistry getNativeTypeRegistry() {
      return this.zenClassRegistry.getNativeTypeRegistry(this.info.loader());
   }
}
