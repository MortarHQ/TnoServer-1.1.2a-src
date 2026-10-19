package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.generic.TypeParameter;

public class JavaSynthesizedFunction {
   public final JavaClass cls;
   public final TypeParameter[] typeParameters;
   public final FunctionHeader header;
   public final String method;

   public JavaSynthesizedFunction(JavaClass cls, TypeParameter[] parameters, FunctionHeader header, String method) {
      this.cls = cls;
      this.typeParameters = parameters;
      this.header = header;
      this.method = method;
   }
}
