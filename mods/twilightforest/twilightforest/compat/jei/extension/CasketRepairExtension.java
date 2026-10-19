package twilightforest.compat.jei.extension;

import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.item.recipe.CasketRepairRecipe;

public class CasketRepairExtension implements ICraftingCategoryExtension<CasketRepairRecipe> {
   public void setRecipe(
      RecipeHolder<CasketRepairRecipe> recipeHolder, IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses
   ) {
      ItemStack casket = new ItemStack(
         TFItems.KEEPSAKE_CASKET, 1, DataComponentPatch.builder().set((DataComponentType)TFDataComponents.CASKET_DAMAGE.get(), 2).build()
      );
      List<List<ItemStack>> inputs = new ArrayList<>();
      inputs.add(List.of(casket));
      inputs.add(List.of(TFItems.CHARM_OF_KEEPING_3.toStack()));
      craftingGridHelper.createAndSetInputs(builder, inputs, 0, 0);
      builder.setShapeless();
      ItemStack repairedScepter = new ItemStack(
         TFItems.KEEPSAKE_CASKET, 1, DataComponentPatch.builder().set((DataComponentType)TFDataComponents.CASKET_DAMAGE.get(), 1).build()
      );
      craftingGridHelper.createAndSetOutputs(builder, List.of(repairedScepter));
   }
}
