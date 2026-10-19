package fzzyhmstrs.emi_loot.neoforge.util;

import dev.emi.emi.api.recipe.EmiRecipe;
import fzzyhmstrs.emi_loot.client.ClientBuiltPool;
import fzzyhmstrs.emi_loot.util.IconGroupEmiWidget;
import net.minecraft.client.gui.GuiGraphics;

public class IconGroupEmiWidgetImpl extends IconGroupEmiWidget {
   public IconGroupEmiWidgetImpl(int x, int y, ClientBuiltPool pool, EmiRecipe recipe) {
      super(x, y, pool, recipe);
   }

   public void render(GuiGraphics matrices, int mouseX, int mouseY, float delta) {
      this.renderInternal(matrices, mouseX, mouseY, delta);
   }
}
