package net.p3pp3rf1y.sophisticatedcore.data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.crafting.UpgradeClearRecipe;

public class SCRecipeProvider extends RecipeProvider {
   public SCRecipeProvider(PackOutput packOutput, CompletableFuture<Provider> registries) {
      super(packOutput, registries);
   }

   protected void buildRecipes(RecipeOutput recipeOutput) {
      SpecialRecipeBuilder.special(UpgradeClearRecipe::new).save(recipeOutput, SophisticatedCore.getRegistryName("upgrade_clear"));
   }
}
