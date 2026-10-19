package org.openzen.zenscript.javashared;

public class JavaTypeParameterInfo {
   public final int parameterIndex;
   public final JavaField field;

   public JavaTypeParameterInfo(int parameterIndex) {
      this.parameterIndex = parameterIndex;
      this.field = null;
   }

   public JavaTypeParameterInfo(JavaField field) {
      this.parameterIndex = -1;
      this.field = field;
   }

   public JavaTypeParameterInfo(int parameterIndex, JavaField field) {
      this.parameterIndex = parameterIndex;
      this.field = field;
   }
}
