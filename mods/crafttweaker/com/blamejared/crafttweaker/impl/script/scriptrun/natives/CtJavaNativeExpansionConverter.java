package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeExpansionConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zencode.shared.logging.IZSLogger;

final class CtJavaNativeExpansionConverter extends JavaNativeExpansionConverter {
   private final Map<Class<?>, String> expansionTargets;

   CtJavaNativeExpansionConverter(
      JavaNativeTypeConverter typeConverter,
      IZSLogger logger,
      JavaNativePackageInfo packageInfo,
      JavaNativeMemberConverter memberConverter,
      JavaNativeTypeConversionContext typeConversionContext,
      JavaNativeHeaderConverter headerConverter,
      IScriptRunInfo info,
      IZenClassRegistry registry
   ) {
      super(typeConverter, logger, packageInfo, memberConverter, typeConversionContext, headerConverter);
      this.expansionTargets = buildExpansionTargetsFrom(info, registry);
   }

   private static Map<Class<?>, String> buildExpansionTargetsFrom(IScriptRunInfo info, IZenClassRegistry registry) {
      return registry.getClassData(info.loader()).expansions().entries().stream().collect(Collectors.toMap(Entry::getValue, Entry::getKey));
   }

   @Override
   protected String getExpandedName(Class<?> cls) {
      if (!cls.isAnnotationPresent(NativeTypeRegistration.class) && !cls.isAnnotationPresent(TypedExpansion.class)) {
         return super.getExpandedName(cls);
      }

      String name = this.expansionTargets.get(cls);
      return name == null ? super.getExpandedName(cls) : name;
   }

   @Override
   protected boolean doesClassNotHaveAnnotation(Class<?> cls) {
      return !cls.isAnnotationPresent(NativeTypeRegistration.class) && !cls.isAnnotationPresent(TypedExpansion.class) && super.doesClassNotHaveAnnotation(cls);
   }
}
