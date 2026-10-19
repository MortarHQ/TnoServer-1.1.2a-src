package mezz.jei.gui.input;

import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.gui.overlay.elements.IElement;
import net.minecraft.world.item.ItemStack;

public interface IClickableIngredientInternal<T> {
   ITypedIngredient<T> getTypedIngredient();

   IElement<T> getElement();

   boolean isMouseOver(double var1, double var3);

   ItemStack getCheatItemStack(IIngredientManager var1);

   boolean canClickToFocus();
}
