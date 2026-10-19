package de.keksuccino.konkrete.gui.screens.popup;

import de.keksuccino.konkrete.gui.content.AdvancedButton;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

@Deprecated(forRemoval = true)
public abstract class Popup {
   private boolean displayed = false;
   private int alpha;
   private List<AdvancedButton> buttons = new ArrayList<>();

   @Deprecated
   public Popup(int backgroundAlpha) {
      this.alpha = backgroundAlpha;
   }

   @Deprecated
   public void render(GuiGraphics graphics, int mouseX, int mouseY, Screen renderIn) {
   }

   @Deprecated
   public boolean isDisplayed() {
      return false;
   }

   @Deprecated
   public void setDisplayed(boolean b) {
   }

   @Deprecated
   public List<AdvancedButton> getButtons() {
      return this.buttons;
   }

   @Deprecated
   protected void addButton(AdvancedButton b) {
   }

   @Deprecated
   protected void removeButton(AdvancedButton b) {
   }

   @Deprecated
   protected void renderButtons(GuiGraphics graphics, int mouseX, int mouseY) {
   }

   @Deprecated
   protected void colorizePopupButton(AdvancedButton b) {
   }
}
