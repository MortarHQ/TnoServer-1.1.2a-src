package dev.ftbqlang.client;

public final class SelectorSession {
   private boolean requested;
   private Object host;

   public void requestOpen() {
      this.requested = true;
   }

   public boolean shouldAttach(Object currentHost) {
      return this.requested && currentHost != null && this.host != currentHost;
   }

   public boolean isAttachedTo(Object currentHost) {
      return this.requested && this.host == currentHost;
   }

   public void attached(Object currentHost) {
      this.host = currentHost;
   }

   public void dismissed(Object closingHost) {
      if (this.host == closingHost) {
         this.reset();
      }
   }

   public void leftBook() {
      if (this.host != null) {
         this.reset();
      }
   }

   public void reset() {
      this.requested = false;
      this.host = null;
   }
}
