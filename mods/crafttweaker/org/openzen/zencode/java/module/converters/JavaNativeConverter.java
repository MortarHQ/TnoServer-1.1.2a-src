package org.openzen.zencode.java.module.converters;

import java.lang.reflect.Modifier;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.parser.BracketExpressionParser;

public class JavaNativeConverter {
   public final JavaNativeTypeConverter typeConverter;
   public final JavaNativeHeaderConverter headerConverter;
   public final JavaNativeMemberConverter memberConverter;
   public final JavaNativeClassConverter classConverter;
   public final JavaNativeGlobalConverter globalConverter;
   public final JavaNativeExpansionConverter expansionConverter;
   private final JavaNativeTypeConversionContext typeConversionContext;

   public JavaNativeConverter(
      JavaNativeTypeConverter typeConverter,
      JavaNativeHeaderConverter headerConverter,
      JavaNativeMemberConverter memberConverter,
      JavaNativeClassConverter classConverter,
      JavaNativeGlobalConverter globalConverter,
      JavaNativeExpansionConverter expansionConverter,
      JavaNativeTypeConversionContext typeConversionContext
   ) {
      this.typeConverter = typeConverter;
      this.headerConverter = headerConverter;
      this.memberConverter = memberConverter;
      this.classConverter = classConverter;
      this.globalConverter = globalConverter;
      this.expansionConverter = expansionConverter;
      this.typeConversionContext = typeConversionContext;
   }

   public void registerBEP(BracketExpressionParser bep) {
      this.headerConverter.setBEP(bep);
      this.typeConverter.setBEP(bep);
   }

   public HighLevelDefinition addClass(Class<?> cls) {
      if (this.typeConversionContext.definitionByClass.containsKey(cls)) {
         return this.typeConversionContext.definitionByClass.get(cls);
      } else if (!Modifier.isPublic(cls.getModifiers())) {
         throw new IllegalArgumentException("Class \" " + cls.getName() + "\" must be public");
      } else {
         return cls.isAnnotationPresent(ZenCodeType.Expansion.class) ? this.expansionConverter.convertExpansion(cls) : this.classConverter.convertClass(cls);
      }
   }
}
