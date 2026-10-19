package io.github.Memoires.mysticism.neoforge.data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.jetbrains.annotations.NotNull;

public class MysticismRecipeProvider extends RecipeProvider {
   public MysticismRecipeProvider(PackOutput output, CompletableFuture<Provider> completableFuture) {
      super(output, completableFuture);
   }

   protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
      this.smithingRecipes(recipeOutput);
   }

   private void smithingRecipes(RecipeOutput recipeOutput) {
   }
}
