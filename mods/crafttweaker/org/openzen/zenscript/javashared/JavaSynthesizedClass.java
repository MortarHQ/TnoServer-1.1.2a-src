package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.type.TypeID;

public class JavaSynthesizedClass {
   public final JavaClass cls;
   public final TypeID[] typeArguments;

   public JavaSynthesizedClass(JavaClass cls, TypeID[] typeArguments) {
      this.cls = cls;
      this.typeArguments = typeArguments;
   }
}
