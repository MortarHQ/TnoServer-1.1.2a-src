package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.type.TypeID;

public class JavaSynthesizedFunctionInstance {
   public final TypeID[] typeArguments;
   private final JavaSynthesizedFunction function;

   public JavaSynthesizedFunctionInstance(JavaSynthesizedFunction function, TypeID[] typeArguments) {
      this.function = function;
      this.typeArguments = typeArguments;
   }

   public JavaClass getCls() {
      return this.function.cls;
   }

   public String getMethod() {
      return this.function.method;
   }

   public FunctionHeader getHeader() {
      return this.function.header;
   }
}
