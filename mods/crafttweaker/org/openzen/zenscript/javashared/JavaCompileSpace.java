package org.openzen.zenscript.javashared;

import org.openzen.zenscript.codemodel.Module;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;

public interface JavaCompileSpace {
   void register(JavaCompiledModule var1);

   GlobalTypeRegistry getRegistry();

   JavaCompiledModule getCompiled(Module var1);
}
