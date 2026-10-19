package team.lodestar.lodestone.systems.recipe;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class LodestoneRecipeType<T extends Recipe<?>> implements RecipeType<T> {
   public final ResourceLocation id;

   public LodestoneRecipeType(ResourceLocation id) {
      this.id = id;
   }

   @Override
   public String toString() {
      return this.id.toString();
   }

   public static <T extends RecipeInput, K extends Recipe<T>> K getRecipe(Level level, RecipeType<K> recipeType, T recipeInput) {
      return findRecipe(level, recipeType, recipe -> recipe.matches(recipeInput, level));
   }

   public static <T extends RecipeInput, K extends Recipe<T>> K findRecipe(Level level, RecipeType<K> recipeType, Predicate<K> predicate) {
      for (RecipeHolder<K> recipe : getRecipeHolders(level, recipeType)) {
         K value = (K)recipe.value();
         if (predicate.test(value)) {
            return value;
         }
      }

      return null;
   }

   public static <T extends RecipeInput, K extends Recipe<T>> List<K> getRecipes(Level level, RecipeType<K> recipeType) {
      return getRecipeHolders(level, recipeType).stream().map(RecipeHolder::value).collect(Collectors.toList());
   }

   public static <T extends RecipeInput, K extends Recipe<T>> List<RecipeHolder<K>> getRecipeHolders(Level level, RecipeType<K> recipeType) {
      return level.getRecipeManager().getAllRecipesFor(recipeType);
   }
}
