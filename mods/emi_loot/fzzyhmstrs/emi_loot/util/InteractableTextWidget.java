package fzzyhmstrs.emi_loot.util;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.TextWidget;
import fzzyhmstrs.emi_loot.util.stack.EmiStackWithTitle;

public class InteractableTextWidget extends TextWidget {
   SlotWidget slotWidget;

   public InteractableTextWidget(EmiStackWithTitle stack, int x, int y, int color, boolean shadow) {
      super(stack.getTrimmedName(), x, y, color, shadow);
      this.slotWidget = new SlotWidget(stack, x, y);
   }

   public InteractableTextWidget recipeContext(EmiRecipe recipe) {
      this.slotWidget.recipeContext(recipe);
      return this;
   }

   public boolean mouseClicked(int mouseX, int mouseY, int button) {
      return this.slotWidget.mouseClicked(mouseX, mouseY, button);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return this.slotWidget.keyPressed(keyCode, scanCode, modifiers);
   }
}
