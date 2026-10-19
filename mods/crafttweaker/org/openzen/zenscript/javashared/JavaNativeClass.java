package org.openzen.zenscript.javashared;

import java.util.HashMap;
import java.util.Map;

public class JavaNativeClass {
   public final JavaClass cls;
   public final boolean nonDestructible;
   private final Map<String, JavaMethod> methods = new HashMap<>();

   public JavaNativeClass(JavaClass cls) {
      this(cls, false);
   }

   public JavaNativeClass(JavaClass cls, boolean nonDestructible) {
      this.cls = cls;
      this.nonDestructible = nonDestructible;
   }

   public void addMethod(String key, JavaMethod method) {
      this.methods.put(key, method);
   }

   public void addConstructor(String key, String descriptor) {
      this.methods.put(key, this.createMethod("<init>", descriptor, JavaMethod.Kind.CONSTRUCTOR));
   }

   public void addInstanceMethod(String key, String name, String descriptor) {
      this.addInstanceMethod(key, name, descriptor, false);
   }

   public void addInstanceMethod(String key, String name, String descriptor, boolean genericReturnType) {
      this.methods.put(key, this.createMethod(name, descriptor, JavaMethod.Kind.INSTANCE, genericReturnType));
   }

   public JavaMethod createMethod(String name, String descriptor, JavaMethod.Kind instance) {
      return this.createMethod(name, descriptor, instance, false);
   }

   public JavaMethod createMethod(String name, String descriptor, JavaMethod.Kind instance, boolean genericReturnType) {
      return new JavaMethod(this.cls, instance, name, false, descriptor, 1, genericReturnType);
   }

   public JavaMethod createInstanceMethod(String name, String descriptor) {
      return this.createMethod(name, descriptor, JavaMethod.Kind.INSTANCE);
   }

   public JavaMethod getMethod(String name) {
      return this.methods.get(name);
   }
}
