package org.openzen.zenscript.javashared;

public class JavaParameterInfo {
   public final int index;
   public final String typeDescriptor;

   public JavaParameterInfo(int index, String typeDescriptor) {
      this.index = index;
      this.typeDescriptor = typeDescriptor;
   }
}
