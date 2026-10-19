package twilightforest.compat.rei.fillers;

import java.util.Collection;
import java.util.List;
import me.shedaniel.rei.api.client.entry.type.BuiltinClientEntryTypes;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.client.categories.crafting.filler.CraftingRecipeFiller;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomShapedDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import twilightforest.item.recipe.travellers.TravellersGearModifierRecipe;

public class REITravellersGearModifierRecipeFiller implements CraftingRecipeFiller<TravellersGearModifierRecipe> {
   public Collection<Display> apply(RecipeHolder<TravellersGearModifierRecipe> recipeHolder) {
      TravellersGearModifierRecipe recipe = (TravellersGearModifierRecipe)recipeHolder.value();
      List<Ingredient> ingredients = recipe.getIngredients();
      ItemStack output = TravellersGearModifierRecipe.getModifiableArmorFromIngredients(ingredients).copy();
      recipe.applyModifier(Minecraft.getInstance().level.registryAccess(), output, ingredients);
      Renderer renderer = EntryStack.of(VanillaEntryTypes.ITEM.getDefinition(), output);
      return List.of(
         new DefaultCustomShapedDisplay(
            recipeHolder,
            EntryIngredients.ofIngredients(ingredients),
            List.of(EntryIngredient.of(EntryStack.of(BuiltinClientEntryTypes.RENDERING, renderer))),
            recipe.getWidth(),
            recipe.getHeight()
         )
      );
   }

   public Class<TravellersGearModifierRecipe> getRecipeClass() {
      return TravellersGearModifierRecipe.class;
   }
}
