package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import java.util.Objects;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeClassConverter;
import org.openzen.zencode.java.module.converters.JavaNativeConverter;
import org.openzen.zencode.java.module.converters.JavaNativeConverterBuilder;
import org.openzen.zencode.java.module.converters.JavaNativeExpansionConverter;
import org.openzen.zencode.java.module.converters.JavaNativeGlobalConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zencode.shared.logging.IZSLogger;

public final class CtJavaNativeConverterBuilder extends JavaNativeConverterBuilder {
   private final IScriptRunInfo runInfo;
   private final IZenClassRegistry registry;
   private CtJavaNativeHeaderConverter headerConverter;

   public CtJavaNativeConverterBuilder(IScriptRunInfo info, IZenClassRegistry registry) {
      this.runInfo = info;
      this.registry = registry;
   }

   @Override
   public JavaNativeClassConverter getClassConverter(
      JavaNativePackageInfo packageInfo,
      JavaNativeTypeConversionContext typeConversionContext,
      JavaNativeTypeConverter typeConverter,
      JavaNativeHeaderConverter headerConverter,
      JavaNativeMemberConverter memberConverter
   ) {
      return new CtJavaNativeClassConverter(packageInfo, typeConversionContext, typeConverter, headerConverter, memberConverter, this.registry, this.runInfo);
   }

   @Override
   protected JavaNativeExpansionConverter getExpansionConverter(
      JavaNativePackageInfo packageInfo,
      IZSLogger logger,
      JavaNativeTypeConversionContext typeConversionContext,
      JavaNativeTypeConverter typeConverter,
      JavaNativeHeaderConverter headerConverter,
      JavaNativeMemberConverter memberConverter
   ) {
      return new CtJavaNativeExpansionConverter(
         typeConverter, logger, packageInfo, memberConverter, typeConversionContext, headerConverter, this.runInfo, this.registry
      );
   }

   @Override
   protected JavaNativeConverter getNativeConverter(
      JavaNativeTypeConversionContext typeConversionContext,
      JavaNativeTypeConverter typeConverter,
      JavaNativeHeaderConverter headerConverter,
      JavaNativeMemberConverter memberConverter,
      JavaNativeClassConverter classConverter,
      JavaNativeGlobalConverter globalConverter,
      JavaNativeExpansionConverter expansionConverter
   ) {
      return new CtJavaNativeConverter(
         typeConverter, headerConverter, memberConverter, classConverter, globalConverter, expansionConverter, typeConversionContext
      );
   }

   @Override
   protected JavaNativeHeaderConverter getHeaderConverter(
      JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter
   ) {
      return this.headerConverter = new CtJavaNativeHeaderConverter(typeConverter, packageInfo, typeConversionContext);
   }

   public void reinitializeLazyHeaderValues() {
      Objects.requireNonNull(this.headerConverter, "Header converter is not yet available").reinitializeAllLazyValues();
   }
}
