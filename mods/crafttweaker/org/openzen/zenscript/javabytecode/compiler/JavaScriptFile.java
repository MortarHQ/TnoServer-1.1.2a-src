package org.openzen.zenscript.javabytecode.compiler;

import java.util.ArrayList;
import java.util.List;
import org.openzen.zenscript.javabytecode.JavaScriptMethod;

public class JavaScriptFile {
   public final JavaClassWriter classWriter;
   public final List<JavaScriptMethod> scriptMethods;

   public JavaScriptFile(JavaClassWriter classWriter) {
      this.classWriter = classWriter;
      this.scriptMethods = new ArrayList<>();
   }
}
