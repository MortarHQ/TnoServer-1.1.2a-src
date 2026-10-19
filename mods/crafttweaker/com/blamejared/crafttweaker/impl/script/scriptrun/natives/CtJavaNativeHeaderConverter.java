package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.NullExpression;
import org.openzen.zenscript.codemodel.type.TypeID;

final class CtJavaNativeHeaderConverter extends JavaNativeHeaderConverter {
   private final List<DefaultedLazyValue> lazyValues = new ArrayList<>();

   CtJavaNativeHeaderConverter(JavaNativeTypeConverter typeConverter, JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext) {
      super(typeConverter, packageInfo, typeConversionContext);
   }

   @Override
   public Expression getDefaultValue(Parameter parameter, TypeID type, FunctionParameter functionParameter) {
      if (parameter.isAnnotationPresent(ZenCodeType.Optional.class)) {
         this.lazyValues.add(new DefaultedLazyValue(parameter, type, functionParameter));
         return new NullExpression(CodePosition.BUILTIN);
      } else {
         return super.getDefaultValue(parameter, type, functionParameter);
      }
   }

   void reinitializeAllLazyValues() {
      this.lazyValues.forEach(this::reinitializeLazyValue);
      this.lazyValues.clear();
   }

   private void reinitializeLazyValue(DefaultedLazyValue lazyDefaultValueValue) {
      Parameter parameter = lazyDefaultValueValue.parameter();
      TypeID typeID = lazyDefaultValueValue.typeId();
      FunctionParameter functionParameter = lazyDefaultValueValue.functionParameter();
      functionParameter.defaultValue = super.getDefaultValue(parameter, typeID, functionParameter);
   }
}
