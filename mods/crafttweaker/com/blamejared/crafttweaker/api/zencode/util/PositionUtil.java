package com.blamejared.crafttweaker.api.zencode.util;

import java.util.Arrays;
import javax.annotation.Nonnull;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.VirtualSourceFile;

public final class PositionUtil {
   private PositionUtil() {
   }

   public static CodePosition getZCScriptPositionFromStackTrace() {
      StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
      return getZCScriptPositionFromStackTrace(stackTrace);
   }

   @Nonnull
   public static CodePosition getZCScriptPositionFromStackTrace(StackTraceElement[] stackTrace) {
      StackTraceElement stackTraceElement = Arrays.stream(stackTrace)
         .filter(element -> element.getFileName() != null)
         .filter(element -> element.getFileName().endsWith(".zs"))
         .findAny()
         .orElse(null);
      if (stackTraceElement == null) {
         return CodePosition.UNKNOWN;
      }

      String fileName = stackTraceElement.getFileName();
      int lineNumber = stackTraceElement.getLineNumber();
      VirtualSourceFile virtualSourceFile = new VirtualSourceFile(fileName);
      return new CodePosition(virtualSourceFile, lineNumber, 0, lineNumber, 0);
   }
}
