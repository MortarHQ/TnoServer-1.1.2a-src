package team.creative.creativecore.common.gui.event;

public abstract class GuiEvent {
   private boolean canceled = false;

   public boolean isCanceled() {
      return this.canceled;
   }

   public void cancel() {
      if (this.cancelable()) {
         this.canceled = true;
      }

      throw new UnsupportedOperationException("Event cannot be canceled " + this.getClass());
   }

   public abstract boolean cancelable();
}
