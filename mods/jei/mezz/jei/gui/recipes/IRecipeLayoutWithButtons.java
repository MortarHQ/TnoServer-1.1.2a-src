package mezz.jei.gui.recipes;

import mezz.jei.api.gui.IRecipeLayoutDrawable;
import mezz.jei.gui.input.IUserInputHandler;
import net.minecraft.client.gui.GuiGraphics;

public interface IRecipeLayoutWithButtons<R> {
   void draw(GuiGraphics var1, int var2, int var3, float var4);

   void updateBounds(int var1, int var2);

   int totalWidth();

   IUserInputHandler createUserInputHandler();

   void tick();

   IRecipeLayoutDrawable<R> getRecipeLayout();

   void drawTooltips(GuiGraphics var1, int var2, int var3);

   int getMissingCountHint();
}
