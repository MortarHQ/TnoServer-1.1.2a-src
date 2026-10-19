package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import java.util.Collections;
import java.util.List;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.SemanticModule;

final class ExecutingScriptRunner extends ScriptRunner {
   ExecutingScriptRunner(IScriptRunInfo runInfo, List<SourceFile> sources, ScriptingEngineLogger logger) {
      super(runInfo, sources, logger);
   }

   @Override
   protected void executeRunAction(SemanticModule module) {
      this.engine().registerCompiled(module);
      this.engine().run(Collections.emptyMap(), ScriptRunner.class.getClassLoader());
   }
}
