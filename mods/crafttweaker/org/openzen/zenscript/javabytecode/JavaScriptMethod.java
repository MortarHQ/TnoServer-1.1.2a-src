package org.openzen.zenscript.javabytecode;

import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaParameterInfo;

public class JavaScriptMethod {
   public final JavaMethod method;
   public final FunctionParameter[] parameters;
   public final JavaParameterInfo[] parametersInfo;

   public JavaScriptMethod(JavaMethod method, FunctionParameter[] parameters, JavaParameterInfo[] parametersInfo) {
      this.method = method;
      this.parameters = parameters;
      this.parametersInfo = parametersInfo;
   }
}
