package twilightforest.inventory;

import java.util.Iterator;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;

public interface UncraftingPlaceRecipe<C> extends PlaceRecipe<C> {
   int matrixOffset = 11;

   default void placeRecipe(int width, int height, int outputSlot, RecipeHolder<?> recipe, Iterator<C> ingredients, int maxAmount) {
      int widthModified = width;
      int heightModified = height;
      if (recipe.value() instanceof ShapedRecipe shapedRecipe) {
         widthModified = shapedRecipe.getWidth();
         heightModified = shapedRecipe.getHeight();
      }

      int slotIndex = 11;

      for (int gridY = 0; gridY < height; gridY++) {
         boolean yOverfitted = heightModified < height / 2.0F;
         int rad = Mth.floor(height / 2.0F - heightModified / 2.0F);
         if (yOverfitted && rad > gridY) {
            slotIndex += width;
            gridY++;
         }

         for (int gridX = 0; gridX < width; gridX++) {
            if (!ingredients.hasNext()) {
               return;
            }

            yOverfitted = widthModified < width / 2.0F;
            rad = Mth.floor(width / 2.0F - widthModified / 2.0F);
            int o = widthModified;
            boolean xOverfitted = gridX < widthModified;
            if (yOverfitted) {
               o = rad + widthModified;
               xOverfitted = rad <= gridX && gridX < rad + widthModified;
            }

            if (xOverfitted) {
               this.addItemToSlot(ingredients.next(), slotIndex, maxAmount, gridY, gridX);
            } else if (o == gridX) {
               slotIndex += width - gridX;
               break;
            }

            slotIndex++;
         }
      }
   }
}
