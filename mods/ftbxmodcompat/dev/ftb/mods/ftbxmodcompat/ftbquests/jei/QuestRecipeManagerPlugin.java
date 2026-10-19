package dev.ftb.mods.ftbxmodcompat.ftbquests.jei;

import dev.ftb.mods.ftbquests.registry.ModItems;
import dev.ftb.mods.ftbxmodcompat.ftbquests.recipemod_common.WrappedQuestCache;
import java.util.List;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.advanced.IRecipeManagerPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.ItemStack;

public enum QuestRecipeManagerPlugin implements IRecipeManagerPlugin {
   INSTANCE;

   private final WrappedQuestCache cache = new WrappedQuestCache();

   public void refresh() {
      this.cache.clear();
   }

   public <T, V> List<T> getRecipes(IRecipeCategory<T> recipeCategory, IFocus<V> focus) {
      if (!(recipeCategory instanceof QuestCategory && focus.getTypedValue().getIngredient() instanceof ItemStack stack)) {
         return List.of();
      }

      if (stack.getItem() == ModItems.BOOK.get() && focus.getRole() == RecipeIngredientRole.CATALYST) {
         return (List<T>)this.cache.getCachedItems();
      }

      return switch (focus.getRole()) {
         case INPUT -> this.cache.findQuestsWithInput(stack);
         case OUTPUT -> this.cache.findQuestsWithOutput(stack);
         default -> List.of();
      };
   }

   public <T> List<T> getRecipes(IRecipeCategory<T> recipeCategory) {
      return (List<T>)(recipeCategory instanceof QuestCategory ? this.cache.getCachedItems() : List.of());
   }

   public <V> List<RecipeType<?>> getRecipeTypes(IFocus<V> focus) {
      return !(
            focus.getTypedValue().getIngredient() instanceof ItemStack stack
               && (
                  focus.getRole() == RecipeIngredientRole.INPUT && (stack.getItem() == ModItems.BOOK.get() || !this.cache.findQuestsWithInput(stack).isEmpty())
                     || focus.getRole() == RecipeIngredientRole.OUTPUT && !this.cache.findQuestsWithOutput(stack).isEmpty()
               )
         )
         ? List.of()
         : List.of(JEIRecipeTypes.QUEST);
   }
}
