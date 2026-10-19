package org.openzen.zenscript.javashared;

public class JavaImplementation {
   public final boolean inline;
   public final JavaClass implementationClass;

   public JavaImplementation(boolean inline, JavaClass implementationClass) {
      this.inline = inline;
      this.implementationClass = implementationClass;
   }
}
