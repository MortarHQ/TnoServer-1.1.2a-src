package twilightforest.client;

import java.util.List;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import twilightforest.inventory.UncraftingPlaceRecipe;

public class UncraftingRecipeBookComponent extends RecipeBookComponent implements UncraftingPlaceRecipe<Ingredient> {
   public void setupGhostRecipe(RecipeHolder<?> recipe, List<Slot> slots) {
      ItemStack itemstack = recipe.value().getResultItem(this.minecraft.level.registryAccess());
      this.ghostRecipe.setRecipe(recipe);
      this.ghostRecipe.addIngredient(Ingredient.of(new ItemStack[]{itemstack}), slots.get(1).x, slots.get(1).y);
      this.placeRecipe(
         this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), recipe, recipe.value().getIngredients().iterator(), 0
      );
   }
}
