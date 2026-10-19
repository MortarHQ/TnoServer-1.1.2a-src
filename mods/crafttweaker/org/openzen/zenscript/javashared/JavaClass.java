package org.openzen.zenscript.javashared;

import java.util.Arrays;
import stdlib.Strings;

public class JavaClass implements Comparable<JavaClass> {
   public static final JavaClass CLASS = new JavaClass("java.lang", "Class", JavaClass.Kind.CLASS);
   public static final JavaClass ENUM = new JavaClass("java.lang", "Enum", JavaClass.Kind.CLASS);
   public static final JavaClass OBJECT = new JavaClass("java.lang", "Object", JavaClass.Kind.CLASS);
   public static final JavaClass STRING = new JavaClass("java.lang", "String", JavaClass.Kind.CLASS);
   public static final JavaClass CLOSEABLE = new JavaClass("java.lang", "AutoCloseable", JavaClass.Kind.INTERFACE);
   public static final JavaClass MAP = new JavaClass("java.util", "Map", JavaClass.Kind.INTERFACE);
   public static final JavaClass HASHMAP = new JavaClass("java.util", "HashMap", JavaClass.Kind.CLASS);
   public static final JavaClass ITERATOR = new JavaClass("java.util", "Iterator", JavaClass.Kind.INTERFACE);
   public static final JavaClass ITERABLE = new JavaClass("java.lang", "Iterable", JavaClass.Kind.INTERFACE);
   public static final JavaClass ARRAYS = new JavaClass("java.util", "Arrays", JavaClass.Kind.CLASS);
   public static final JavaClass BOOLEAN = new JavaClass("java.lang", "Boolean", JavaClass.Kind.CLASS);
   public static final JavaClass BYTE = new JavaClass("java.lang", "Byte", JavaClass.Kind.CLASS);
   public static final JavaClass SHORT = new JavaClass("java.lang", "Short", JavaClass.Kind.CLASS);
   public static final JavaClass INTEGER = new JavaClass("java.lang", "Integer", JavaClass.Kind.CLASS);
   public static final JavaClass LONG = new JavaClass("java.lang", "Long", JavaClass.Kind.CLASS);
   public static final JavaClass FLOAT = new JavaClass("java.lang", "Float", JavaClass.Kind.CLASS);
   public static final JavaClass DOUBLE = new JavaClass("java.lang", "Double", JavaClass.Kind.CLASS);
   public static final JavaClass CHARACTER = new JavaClass("java.lang", "Character", JavaClass.Kind.CLASS);
   public static final JavaClass COLLECTION = new JavaClass("java.util", "Collection", JavaClass.Kind.INTERFACE);
   public static final JavaClass COLLECTIONS = new JavaClass("java.util", "Collections", JavaClass.Kind.CLASS);
   public static final JavaClass SHARED = new JavaClass("zsynthetic", "Shared", JavaClass.Kind.CLASS);
   public final JavaClass outer;
   public final String pkg;
   public final String fullName;
   public final String internalName;
   public final JavaClass.Kind kind;
   private final String[] classNameParts;
   public boolean empty = false;
   public boolean membersPrepared = false;

   private JavaClass(String pkg, String internalName, JavaClass.Kind kind, String[] classNameParts) {
      if (classNameParts.length > 1) {
         String[] outerParts = Arrays.copyOf(classNameParts, classNameParts.length - 1);
         this.outer = new JavaClass(pkg, internalName.substring(0, internalName.lastIndexOf(36)), kind, outerParts);
      } else {
         this.outer = null;
      }

      this.pkg = pkg;
      this.fullName = String.join(".", classNameParts);
      this.internalName = internalName;
      this.kind = kind;
      this.classNameParts = classNameParts;
   }

   public JavaClass(String pkg, String name, JavaClass.Kind kind) {
      this.pkg = pkg;
      this.classNameParts = new String[]{name};
      this.fullName = pkg + "." + name;
      this.internalName = pkg.isEmpty() ? name : pkg.replace('.', '/') + "/" + name;
      this.kind = kind;
      this.outer = this;
   }

   public JavaClass(JavaClass outer, String name, JavaClass.Kind kind) {
      this.pkg = outer.pkg;
      this.classNameParts = Arrays.copyOf(outer.classNameParts, outer.classNameParts.length + 1);
      this.classNameParts[outer.classNameParts.length] = name;
      this.fullName = outer.fullName + "." + name;
      this.internalName = outer.internalName + "$" + name;
      this.kind = kind;
      this.outer = outer.outer;
   }

   public static JavaClass fromInternalName(String internalName, JavaClass.Kind kind) {
      if (kind == JavaClass.Kind.ARRAY) {
         return new JavaClass("", internalName, kind, new String[0]);
      }

      int lastSlash = internalName.lastIndexOf(47);
      String pkg = lastSlash < 0 ? "" : internalName.substring(0, lastSlash);
      String className = lastSlash < 0 ? internalName : internalName.substring(lastSlash + 1);
      String[] nameParts = Strings.split(className, '$');
      return new JavaClass(pkg, internalName, kind, nameParts);
   }

   public static String getNameFromFile(String filename) {
      return filename.indexOf(46) > 0 ? filename.substring(0, filename.lastIndexOf(46)) : filename;
   }

   public String getName() {
      return this.classNameParts[this.classNameParts.length - 1];
   }

   public String getClassName() {
      return this.fullName.substring(this.pkg.length() + 1);
   }

   public int compareTo(JavaClass o) {
      return this.fullName.compareTo(o.fullName);
   }

   public boolean isEnum() {
      return this.kind == JavaClass.Kind.ENUM;
   }

   public boolean isInterface() {
      return this.kind == JavaClass.Kind.INTERFACE;
   }

   public enum Kind {
      CLASS,
      INTERFACE,
      ENUM,
      ARRAY;
   }
}
