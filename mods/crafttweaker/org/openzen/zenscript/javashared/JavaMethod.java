package org.openzen.zenscript.javashared;

public class JavaMethod {
   public final JavaClass cls;
   public final JavaMethod.Kind kind;
   public final String name;
   public final boolean compile;
   public final JavaNativeTranslation translation;
   public final String descriptor;
   public final int modifiers;
   public final boolean genericResult;
   public final boolean[] typeParameterArguments;

   public JavaMethod(JavaClass cls, JavaMethod.Kind kind, String name, boolean compile, String descriptor, int modifiers, boolean genericResult) {
      this(cls, kind, name, compile, descriptor, modifiers, genericResult, new boolean[0]);
   }

   public JavaMethod(
      JavaClass cls,
      JavaMethod.Kind kind,
      String name,
      boolean compile,
      String descriptor,
      int modifiers,
      boolean genericResult,
      boolean[] typeParameterArguments
   ) {
      if (descriptor.contains("<")) {
         throw new IllegalArgumentException("Invalid descriptor!");
      }

      if (cls.isInterface() && !JavaModifiers.isStatic(modifiers)) {
         kind = JavaMethod.Kind.INTERFACE;
      }

      this.cls = cls;
      this.kind = kind;
      this.name = name;
      this.compile = compile;
      this.translation = null;
      this.descriptor = descriptor;
      this.modifiers = modifiers;
      this.genericResult = genericResult;
      this.typeParameterArguments = typeParameterArguments;
   }

   public JavaMethod(JavaNativeTranslation<?> translation) {
      this.cls = null;
      this.kind = JavaMethod.Kind.COMPILED;
      this.name = null;
      this.compile = false;
      this.translation = translation;
      this.descriptor = "";
      this.modifiers = 0;
      this.genericResult = false;
      this.typeParameterArguments = new boolean[0];
   }

   public static JavaMethod getConstructor(JavaClass cls, String descriptor, int modifiers) {
      return new JavaMethod(cls, JavaMethod.Kind.CONSTRUCTOR, "<init>", true, descriptor, modifiers, false);
   }

   public static JavaMethod getNativeConstructor(JavaClass cls, String descriptor) {
      return new JavaMethod(cls, JavaMethod.Kind.CONSTRUCTOR, "<init>", false, descriptor, 1, false);
   }

   public static JavaMethod getDestructor(JavaClass cls, int modifiers) {
      return new JavaMethod(cls, JavaMethod.Kind.INSTANCE, "close", true, "()V", modifiers, false);
   }

   public static JavaMethod getStatic(JavaClass cls, String name, String descriptor, int modifiers) {
      return getStatic(cls, name, descriptor, modifiers, false);
   }

   public static JavaMethod getStatic(JavaClass cls, String name, String descriptor, int modifiers, boolean genericResult) {
      return new JavaMethod(cls, JavaMethod.Kind.STATIC, name, true, descriptor, modifiers | 8, genericResult);
   }

   public static JavaMethod getNativeStatic(JavaClass cls, String name, String descriptor) {
      return new JavaMethod(cls, JavaMethod.Kind.STATIC, name, false, descriptor, 9, false);
   }

   public static JavaMethod getVirtual(JavaClass cls, String name, String descriptor, int modifiers) {
      return getVirtual(cls, name, descriptor, modifiers, false);
   }

   public static JavaMethod getVirtual(JavaClass cls, String name, String descriptor, int modifiers, boolean genericResult) {
      return new JavaMethod(cls, JavaMethod.Kind.INSTANCE, name, true, descriptor, modifiers, genericResult);
   }

   public static JavaMethod getNativeVirtual(JavaClass cls, String name, String descriptor) {
      return new JavaMethod(cls, JavaMethod.Kind.INSTANCE, name, false, descriptor, 1, false);
   }

   public static JavaMethod getInterface(JavaClass cls, String name, String descriptor) {
      return getInterface(cls, name, descriptor, false);
   }

   public static JavaMethod getInterface(JavaClass cls, String name, String descriptor, boolean genericResult) {
      return new JavaMethod(cls, JavaMethod.Kind.INTERFACE, name, false, descriptor, 1, genericResult);
   }

   public static JavaMethod getNativeExpansion(JavaClass cls, String name, String descriptor) {
      return new JavaMethod(cls, JavaMethod.Kind.EXPANSION, name, false, descriptor, 9, false);
   }

   public String getMapping(JavaClass definition) {
      if (this.cls == null) {
         return "!TODO";
      }

      StringBuilder result = new StringBuilder();
      result.append(this.name);
      result.append(this.descriptor);
      if (definition == null || !definition.internalName.equals(this.cls.internalName)) {
         result.append('@').append(this.cls.internalName);
      }

      return result.toString();
   }

   public boolean isAbstract() {
      return (this.modifiers & 1024) > 0;
   }

   public enum Kind {
      STATIC,
      STATICINIT,
      INSTANCE,
      INTERFACE,
      EXPANSION,
      CONSTRUCTOR,
      COMPILED;
   }
}
