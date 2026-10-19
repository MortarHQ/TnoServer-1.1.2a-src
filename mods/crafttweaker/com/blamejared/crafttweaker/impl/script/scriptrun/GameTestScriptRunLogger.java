package com.blamejared.crafttweaker.impl.script.scriptrun;

import java.util.OptionalInt;
import java.util.function.Function;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.shared.SourceFile;

final class GameTestScriptRunLogger extends ScriptRunLogger {
   GameTestScriptRunLogger(Logger logger, Function<SourceFile, OptionalInt> priorityGetter) {
      super(logger, priorityGetter);
   }

   @Override
   public void logSourceFile(SourceFile file) {
   }
}
