package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.api.util.PathUtil;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import java.nio.file.Path;
import java.util.List;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.SemanticModule;

final class SyntaxCheckScriptRunner extends ScriptRunner {
   SyntaxCheckScriptRunner(IScriptRunInfo runInfo, List<SourceFile> sources, ScriptingEngineLogger logger) {
      super(runInfo, sources, logger);
   }

   @Override
   protected void executeRunAction(SemanticModule module) {
      if (this.runInfo().dumpClasses()) {
         Path classes = PathUtil.findFromGameDirectory("classes");
         this.engine().createRunUnit().dump(classes.toFile());
      }
   }
}
