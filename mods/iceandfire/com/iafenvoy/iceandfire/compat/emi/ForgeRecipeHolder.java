package com.iafenvoy.iceandfire.compat.emi;

import com.iafenvoy.iceandfire.recipe.DragonForgeRecipe;
import com.iafenvoy.iceandfire.registry.IafRecipes;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class ForgeRecipeHolder {
   private final String dragonType;
   private final EmiTexture texture;
   private final EmiStack workstation;
   private final EmiRecipeCategory category;

   public ForgeRecipeHolder(ResourceLocation id, String dragonType, EmiTexture texture, EmiStack workstation) {
      this.dragonType = dragonType;
      this.texture = texture;
      this.workstation = workstation;
      this.category = new EmiRecipeCategory(id, workstation, texture);
   }

   public void register(EmiRegistry registry) {
      registry.addCategory(this.category);
      registry.addWorkstation(this.category, this.workstation);
      List<RecipeHolder<DragonForgeRecipe>> forgeRecipeList = registry.getRecipeManager().getAllRecipesFor((RecipeType)IafRecipes.DRAGON_FORGE_TYPE.get());

      for (RecipeHolder<DragonForgeRecipe> recipeEntry : forgeRecipeList.stream()
         .filter(entry -> ((DragonForgeRecipe)entry.value()).getDragonType().equals(this.dragonType))
         .toList()) {
         registry.addRecipe(new ForgeRecipeHolder.DragonForgeEmiRecipe(recipeEntry, this.category));
      }
   }

   public class DragonForgeEmiRecipe implements EmiRecipe {
      private final RecipeHolder<DragonForgeRecipe> entry;
      private final EmiRecipeCategory category;

      public DragonForgeEmiRecipe(RecipeHolder<DragonForgeRecipe> entry, EmiRecipeCategory category) {
         this.entry = entry;
         this.category = category;
      }

      public EmiRecipeCategory getCategory() {
         return this.category;
      }

      @Nullable
      public ResourceLocation getId() {
         return this.entry.id();
      }

      public List<EmiIngredient> getInputs() {
         return List.of(
            EmiIngredient.of(((DragonForgeRecipe)this.entry.value()).getInput()), EmiIngredient.of(((DragonForgeRecipe)this.entry.value()).getBlood())
         );
      }

      public List<EmiStack> getOutputs() {
         return List.of(EmiStack.of(((DragonForgeRecipe)this.entry.value()).getResultItem()));
      }

      public int getDisplayWidth() {
         return 176;
      }

      public int getDisplayHeight() {
         return 1120;
      }

      public void addWidgets(WidgetHolder widgets) {
         widgets.addTexture(ForgeRecipeHolder.this.texture, 3, 4);
         widgets.addSlot(EmiIngredient.of(((DragonForgeRecipe)this.entry.value()).getInput()), 67, 33);
         widgets.addSlot(EmiIngredient.of(((DragonForgeRecipe)this.entry.value()).getBlood()), 85, 33);
         widgets.addSlot(EmiStack.of(((DragonForgeRecipe)this.entry.value()).getResultItem()), 143, 30).large(true).recipeContext(this);
      }
   }
}
