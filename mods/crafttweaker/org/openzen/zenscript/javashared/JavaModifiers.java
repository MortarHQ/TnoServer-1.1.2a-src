package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.Modifiers;

public class JavaModifiers {
   public static final int PUBLIC = 1;
   public static final int PRIVATE = 2;
   public static final int PROTECTED = 4;
   public static final int STATIC = 8;
   public static final int FINAL = 16;
   public static final int SUPER = 32;
   public static final int SYNCHRONIZED = 32;
   public static final int OPEN = 32;
   public static final int TRANSITIVE = 32;
   public static final int VOLATILE = 64;
   public static final int BRIDGE = 64;
   public static final int STATIC_PHASE = 64;
   public static final int VARARGS = 128;
   public static final int TRANSIENT = 128;
   public static final int NATIVE = 256;
   public static final int INTERFACE = 512;
   public static final int ABSTRACT = 1024;
   public static final int STRICT = 2048;
   public static final int SYNTHETIC = 4096;
   public static final int ANNOTATION = 8192;
   public static final int ENUM = 16384;
   public static final int MANDATED = 32768;
   public static final int MODULE = 32768;

   private JavaModifiers() {
   }

   public static int getJavaModifiers(int modifiers) {
      int out = 0;
      if (Modifiers.isStatic(modifiers)) {
         out |= 8;
      }

      if (Modifiers.isFinal(modifiers)) {
         out |= 16;
      }

      if (Modifiers.isPublic(modifiers)) {
         out |= 1;
      }

      if (Modifiers.isPrivate(modifiers)) {
         out |= 2;
      }

      if (Modifiers.isProtected(modifiers)) {
         out |= 4;
      }

      if (Modifiers.isAbstract(modifiers)) {
         out |= 1024;
      }

      return out;
   }

   public static boolean isStatic(int modifiers) {
      return (modifiers & 8) > 0;
   }
}
