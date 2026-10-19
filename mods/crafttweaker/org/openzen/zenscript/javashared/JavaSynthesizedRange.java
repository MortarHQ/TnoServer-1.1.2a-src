package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.type.TypeID;

public class JavaSynthesizedRange {
   public final JavaClass cls;
   public final TypeParameter[] typeParameters;
   public final TypeID baseType;

   public JavaSynthesizedRange(JavaClass cls, TypeParameter[] typeParameters, TypeID baseType) {
      this.cls = cls;
      this.typeParameters = typeParameters;
      this.baseType = baseType;
   }
}
