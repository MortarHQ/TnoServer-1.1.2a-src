package twilightforest.compat.jei.extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFItems;
import twilightforest.item.recipe.EssenceRepairRecipe;

public class ExanimateEssenceRepairExtension implements ICraftingCategoryExtension<EssenceRepairRecipe> {
   public void setRecipe(
      RecipeHolder<EssenceRepairRecipe> recipeHolder, IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses
   ) {
      List<List<ItemStack>> inputs = new ArrayList<>();
      List<ItemStack> scepters = Arrays.stream(Ingredient.of(ItemTagGenerator.SCEPTERS).getItems()).toList();
      craftingGridHelper.createAndSetOutputs(builder, scepters);
      scepters.forEach(stack -> stack.setDamageValue(stack.getMaxDamage()));
      inputs.add(scepters);
      inputs.add(List.of(TFItems.EXANIMATE_ESSENCE.toStack()));
      craftingGridHelper.createAndSetInputs(builder, inputs, 0, 0);
      builder.setShapeless();
   }

   public void onDisplayedIngredientsUpdate(RecipeHolder<EssenceRepairRecipe> recipeHolder, List<IRecipeSlotDrawable> recipeSlots, IFocusGroup focuses) {
      if (!focuses.getFocuses(RecipeIngredientRole.OUTPUT).toList().isEmpty()) {
         ItemStack damaged = ((ItemStack)focuses.getFocuses(RecipeIngredientRole.OUTPUT)
               .findFirst()
               .orElseGet(() -> (IFocus)focuses.getFocuses(RecipeIngredientRole.CATALYST).findFirst().orElseThrow())
               .getTypedValue()
               .getItemStack()
               .orElseThrow())
            .copy();
         damaged.setDamageValue(damaged.getMaxDamage());
         recipeSlots.get(1).createDisplayOverrides().addItemStack(damaged);
      }

      recipeSlots.getFirst().createDisplayOverrides().addItemStack(new ItemStack(recipeSlots.get(1).getDisplayedItemStack().orElse(ItemStack.EMPTY).getItem()));
   }
}
