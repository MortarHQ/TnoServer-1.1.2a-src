package org.openzen.zenscript.javashared;

import java.util.HashMap;
import java.util.Map;
import org.openzen.zenscript.codemodel.Module;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;

public class SimpleJavaCompileSpace implements JavaCompileSpace {
   private final GlobalTypeRegistry registry;
   private final Map<Module, JavaCompiledModule> modules = new HashMap<>();

   public SimpleJavaCompileSpace(GlobalTypeRegistry registry) {
      this.registry = registry;
   }

   @Override
   public void register(JavaCompiledModule module) {
      this.modules.put(module.module, module);
   }

   @Override
   public GlobalTypeRegistry getRegistry() {
      return this.registry;
   }

   @Override
   public JavaCompiledModule getCompiled(Module module) {
      return this.modules.get(module);
   }
}
