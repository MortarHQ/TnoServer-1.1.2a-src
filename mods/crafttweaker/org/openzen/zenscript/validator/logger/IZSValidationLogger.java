package org.openzen.zenscript.validator.logger;

import org.openzen.zenscript.validator.ValidationLogEntry;

public interface IZSValidationLogger {
   default void logValidationLogEntry(ValidationLogEntry entry) {
      switch (entry.kind) {
         case ERROR:
            this.logValidationError(entry);
            break;
         case WARNING:
            this.logValidationWarning(entry);
      }
   }

   void logValidationError(ValidationLogEntry var1);

   void logValidationWarning(ValidationLogEntry var1);
}
