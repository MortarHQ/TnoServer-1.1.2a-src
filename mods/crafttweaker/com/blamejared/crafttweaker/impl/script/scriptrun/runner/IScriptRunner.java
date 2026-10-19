package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import java.util.List;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;

@FunctionalInterface
public interface IScriptRunner {
   static IScriptRunner of(IScriptRunInfo info, List<SourceFile> sources, ScriptingEngineLogger logger) {
      return ScriptRunner.of(info, sources, logger);
   }

   void run() throws Exception;
}
