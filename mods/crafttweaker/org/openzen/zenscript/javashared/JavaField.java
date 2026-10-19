package org.openzen.zenscript.javashared;

public class JavaField {
   public final JavaClass cls;
   public final String name;
   public final String descriptor;
   public final String signature;

   public JavaField(JavaClass cls, String name, String descriptor) {
      this(cls, name, descriptor, null);
   }

   public JavaField(JavaClass cls, String name, String descriptor, String signature) {
      this.cls = cls;
      this.name = name;
      this.descriptor = descriptor;
      this.signature = signature;
   }

   public String getMapping(JavaClass definition) {
      StringBuilder result = new StringBuilder();
      result.append(this.name);
      result.append(':');
      result.append(this.descriptor);
      if (!this.cls.internalName.equals(definition.internalName)) {
         result.append(definition.internalName);
      }

      return result.toString();
   }
}
