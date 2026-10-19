package org.openzen.zencode.java.module.converters;

import java.util.Optional;
import org.openzen.zenscript.codemodel.Module;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import stdlib.Strings;

public class JavaNativePackageInfo {
   private final ZSPackage pkg;
   private final String basePackage;
   private final Module module;

   public JavaNativePackageInfo(ZSPackage pkg, String basePackage, Module module) {
      this.pkg = pkg;
      this.basePackage = basePackage;
      this.module = module;
   }

   public boolean isInBasePackage(String className) {
      return className.startsWith(this.module.name)
         || className.startsWith(this.basePackage + ".")
         || className.startsWith("java.lang.")
         || className.startsWith("java.util.");
   }

   public ZSPackage getPackage(String className) {
      if (!className.contains(".") || className.startsWith("java.lang")) {
         return this.pkg;
      }

      if (this.basePackage != null && !this.basePackage.isEmpty()) {
         if (className.startsWith(".")) {
            className = className.substring(1);
         } else {
            if (!className.startsWith(this.basePackage + ".")) {
               throw new IllegalArgumentException("Invalid class name: \"" + className + "\" not in the given base package: \"" + this.basePackage + "\"");
            }

            className = className.substring(this.basePackage.length() + 1);
         }

         String[] classNameParts = Strings.split(className, '.');
         ZSPackage classPkg = this.pkg;

         for (int i = 0; i < classNameParts.length - 1; i++) {
            classPkg = classPkg.getOrCreatePackage(classNameParts[i]);
         }

         return classPkg;
      } else {
         return !className.startsWith(".") && className.contains(".") ? this.getPackageFromTopLevelFor(className).orElse(this.pkg) : this.pkg;
      }
   }

   private Optional<ZSPackage> getPackageFromTopLevelFor(String className) {
      ZSPackage zsPackage = this.pkg;

      while (zsPackage.parent != null) {
         zsPackage = zsPackage.parent;
      }

      int index = className.lastIndexOf(46);
      return zsPackage.getOptionalRecursive(className.substring(0, index));
   }

   public Module getModule() {
      return this.module;
   }

   public String getBasePackage() {
      return this.basePackage;
   }

   public ZSPackage getPkg() {
      return this.pkg;
   }
}
