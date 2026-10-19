package org.openzen.zencode.java.logger;

import java.io.PrintStream;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.validator.ValidationLogEntry;

public class ScriptingEngineStreamLogger implements ScriptingEngineLogger {
   private final PrintStream infoStream;
   private final PrintStream debugStream;
   private final PrintStream warningStream;
   private final PrintStream errorStream;
   private final PrintStream traceStream;

   public ScriptingEngineStreamLogger(
      PrintStream traceStream, PrintStream debugStream, PrintStream infoStream, PrintStream warningStream, PrintStream errorStream
   ) {
      this.infoStream = infoStream;
      this.debugStream = debugStream;
      this.warningStream = warningStream;
      this.errorStream = errorStream;
      this.traceStream = traceStream;
   }

   public ScriptingEngineStreamLogger(PrintStream normalStream, PrintStream errorStream) {
      this(normalStream, normalStream, normalStream, normalStream, errorStream);
   }

   public ScriptingEngineStreamLogger() {
      this(System.out, System.err);
   }

   @Override
   public void info(String message) {
      this.infoStream.println("INFO: " + message);
      this.infoStream.flush();
   }

   @Override
   public void debug(String message) {
      this.debugStream.println("DEBUG:   " + message);
      this.debugStream.flush();
   }

   @Override
   public void warning(String message) {
      this.warningStream.println("WARNING: " + message);
      this.warningStream.flush();
   }

   @Override
   public void error(String message) {
      this.errorStream.println("ERROR:   " + message);
   }

   @Override
   public void throwingErr(String message, Throwable throwable) {
      this.errorStream.println("ERROR:   " + message);
      throwable.printStackTrace(this.errorStream);
      this.errorStream.flush();
   }

   @Override
   public void throwingWarn(String message, Throwable throwable) {
      this.warningStream.println("WARNING: " + message);
      throwable.printStackTrace(this.warningStream);
      this.warningStream.flush();
   }

   @Override
   public void trace(String message) {
      this.traceStream.println("TRACE: " + message);
      this.traceStream.flush();
   }

   @Override
   public void logCompileException(CompileException exception) {
      this.throwingErr("Compile Exception:", exception);
   }

   @Override
   public void logSourceFile(SourceFile file) {
      this.info("Loading File: " + file.getFilename());
   }

   @Override
   public void logValidationError(ValidationLogEntry errorEntry) {
      this.error(errorEntry.toString());
   }

   @Override
   public void logValidationWarning(ValidationLogEntry warningEntry) {
      this.warning(warningEntry.toString());
   }
}
