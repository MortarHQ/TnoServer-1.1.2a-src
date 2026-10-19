package twilightforest.compat.rei.displays;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.DefaultSmithingDisplay;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import twilightforest.item.recipe.NoTemplateSmithingRecipe;

public class REINoTemplateDisplay {
   public static List<DefaultSmithingDisplay> noTemplate(RecipeHolder<NoTemplateSmithingRecipe> recipe) {
      RegistryAccess registryAccess = BasicDisplay.registryAccess();
      List<DefaultSmithingDisplay> displays = new ArrayList<>();

      for (ItemStack additionStack : ((NoTemplateSmithingRecipe)recipe.value()).getAddition().getItems()) {
         for (ItemStack baseStack : ((NoTemplateSmithingRecipe)recipe.value()).getBase().getItems()) {
            EntryIngredient output = EntryIngredients.of(
               ((NoTemplateSmithingRecipe)recipe.value()).assemble(new SmithingRecipeInput(ItemStack.EMPTY, baseStack, additionStack), registryAccess)
            );
            displays.add(
               new DefaultSmithingDisplay(
                  List.of(EntryIngredients.of(ItemStack.EMPTY), EntryIngredients.of(baseStack), EntryIngredients.of(additionStack)),
                  List.of(output),
                  Optional.of(recipe.id())
               )
            );
         }
      }

      return displays;
   }
}
