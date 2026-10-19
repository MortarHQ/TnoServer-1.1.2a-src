package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeClassConverter;
import org.openzen.zencode.java.module.converters.JavaNativeConverter;
import org.openzen.zencode.java.module.converters.JavaNativeExpansionConverter;
import org.openzen.zencode.java.module.converters.JavaNativeGlobalConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;

final class CtJavaNativeConverter extends JavaNativeConverter {
   CtJavaNativeConverter(
      JavaNativeTypeConverter typeConverter,
      JavaNativeHeaderConverter headerConverter,
      JavaNativeMemberConverter memberConverter,
      JavaNativeClassConverter classConverter,
      JavaNativeGlobalConverter globalConverter,
      JavaNativeExpansionConverter expansionConverter,
      JavaNativeTypeConversionContext typeConversionContext
   ) {
      super(typeConverter, headerConverter, memberConverter, classConverter, globalConverter, expansionConverter, typeConversionContext);
   }

   @Override
   public HighLevelDefinition addClass(Class<?> cls) {
      try {
         if (cls.isAnnotationPresent(NativeTypeRegistration.class)) {
            return this.expansionConverter.convertExpansion(cls);
         } else {
            return cls.isAnnotationPresent(TypedExpansion.class) ? this.expansionConverter.convertExpansion(cls) : super.addClass(cls);
         }
      } catch (Throwable e) {
         CommonLoggers.zenCode().error("Error while registering class: '{}', this is most likely a compatibility issue:", cls.getName(), e);
         return null;
      }
   }
}
