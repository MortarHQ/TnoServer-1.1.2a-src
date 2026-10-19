package org.openzen.zenscript.javabytecode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zencode.shared.logging.IZSLogger;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.Module;
import org.openzen.zenscript.javashared.JavaCompiledModule;

public class JavaBytecodeModule extends JavaCompiledModule {
   private final Map<String, byte[]> classes = new HashMap<>();
   private final List<JavaScriptMethod> scripts = new ArrayList<>();
   private final IZSLogger logger;

   public JavaBytecodeModule(Module module, FunctionParameter[] parameters, IZSLogger logger) {
      super(module, parameters);
      this.logger = logger;
   }

   public void addClass(String name, byte[] bytecode) {
      if (bytecode != null) {
         if (name.startsWith("java")) {
            this.logger.trace("Warning: Invalid name " + name);
         } else if (this.classes.containsKey(name)) {
            this.logger.warning("Trying to register " + name + " a 2nd time");
         } else {
            this.classes.put(name, bytecode);
         }
      }
   }

   public void addScript(JavaScriptMethod method) {
      this.scripts.add(method);
   }

   public Map<String, byte[]> getClasses() {
      return this.classes;
   }

   public List<JavaScriptMethod> getScripts() {
      return this.scripts;
   }
}
