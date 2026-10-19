package twilightforest.compat.jei.extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.item.recipe.travellers.TravellersGearModifierRecipe;

public class TravellersGearModifierExtension implements ICraftingCategoryExtension<TravellersGearModifierRecipe> {
   public void setRecipe(
      RecipeHolder<TravellersGearModifierRecipe> recipeHolder,
      @NotNull IRecipeLayoutBuilder builder,
      ICraftingGridHelper craftingGridHelper,
      @NotNull IFocusGroup focuses
   ) {
      TravellersGearModifierRecipe recipe = (TravellersGearModifierRecipe)recipeHolder.value();
      List<List<ItemStack>> inputs = new ArrayList<>(
         recipe.getIngredients().stream().map(ingredientx -> Arrays.stream(ingredientx.getItems()).toList()).toList()
      );
      List<ItemStack> outputs = new ArrayList<>();

      for (Ingredient ingredient : recipe.getIngredients()) {
         for (ItemStack stack : ingredient.getItems()) {
            if (stack.has(TFDataComponents.IS_TRAVELLERS_GEAR) && !stack.is(TFItems.TRAVELLERS_BELT)) {
               outputs.add(recipe.applyModifier(Minecraft.getInstance().level.registryAccess(), stack.copy(), recipe.getIngredients()));
            }
         }
      }

      outputs.stream().filter(ItemStack::isEmpty).forEach(outputs::remove);
      if (!outputs.isEmpty()) {
         if (recipe.isShapeless()) {
            builder.setShapeless();
         }

         craftingGridHelper.createAndSetInputs(builder, inputs, recipe.getWidth(), recipe.getHeight());
         builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 95, 19).setOutputSlotBackground().addItemStacks(outputs);
      }
   }
}
