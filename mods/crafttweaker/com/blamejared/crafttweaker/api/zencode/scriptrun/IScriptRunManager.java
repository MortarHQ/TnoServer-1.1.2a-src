package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.action.base.IAction;
import java.nio.file.Path;
import java.util.List;
import org.openzen.zencode.shared.SourceFile;

public interface IScriptRunManager {
   IScriptRun createScriptRun(ScriptRunConfiguration var1);

   IScriptRun createScriptRun(Path var1, ScriptRunConfiguration var2);

   IScriptRun createScriptRun(Path var1, ScriptDiscoveryConfiguration var2, ScriptRunConfiguration var3);

   IScriptRun createScriptRun(Path var1, List<Path> var2, ScriptRunConfiguration var3);

   IScriptRun createScriptRun(List<SourceFile> var1, ScriptRunConfiguration var2);

   IScriptRunInfo currentRunInfo();

   void applyAction(IAction var1);
}
