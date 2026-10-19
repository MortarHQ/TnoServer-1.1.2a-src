package mezz.jei.gui.input.focus;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;

public class GuiEventListenerFocusHandler implements IFocusHandler {
   private final GuiEventListener guiEventListener;

   public static IFocusHandler create(GuiEventListener guiEventListener) {
      return guiEventListener instanceof EditBox editBox ? new EditBoxFocusHandler(editBox) : new GuiEventListenerFocusHandler(guiEventListener);
   }

   private GuiEventListenerFocusHandler(GuiEventListener guiEventListener) {
      this.guiEventListener = guiEventListener;
   }

   @Override
   public void unFocus() {
      if (this.guiEventListener.isFocused()) {
         this.guiEventListener.setFocused(false);
      }
   }

   @Override
   public void focus() {
      if (!this.guiEventListener.isFocused()) {
         this.guiEventListener.setFocused(true);
      }
   }
}
