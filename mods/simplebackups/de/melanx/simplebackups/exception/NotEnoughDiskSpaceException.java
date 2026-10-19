package de.melanx.simplebackups.exception;

import java.io.IOException;

public class NotEnoughDiskSpaceException extends IOException {
   private static final long serialVersionUID = 20251031L;

   public NotEnoughDiskSpaceException() {
      super("Not enough disk space available to complete the operation.");
   }

   public NotEnoughDiskSpaceException(String message) {
      super(message);
   }

   public NotEnoughDiskSpaceException(String message, Throwable cause) {
      super(message, cause);
   }

   public NotEnoughDiskSpaceException(Throwable cause) {
      super(cause);
   }
}
