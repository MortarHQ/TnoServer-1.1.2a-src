package com.blamejared.crafttweaker.api.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessSmithingTrimRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.SmithingRecipeManager")
@Document("vanilla/api/recipe/manager/SmithingRecipeManager")
public class SmithingRecipeManager implements IRecipeManager<SmithingRecipe> {
   @ZenCodeGlobals.Global("smithing")
   public static final SmithingRecipeManager INSTANCE = new SmithingRecipeManager();

   private SmithingRecipeManager() {
   }

   @ZenCodeType.Method
   public void addTrimRecipe(String recipeName, IIngredient template, IIngredient base, IIngredient addition) {
      SmithingRecipe recipe = AccessSmithingTrimRecipe.crafttweaker$createSmithingTrimRecipe(
         template.asVanillaIngredient(), base.asVanillaIngredient(), addition.asVanillaIngredient()
      );
      CraftTweakerAPI.apply(new ActionAddRecipe(this, this.createHolder(this.fixRecipeId(recipeName), recipe), "trim"));
   }

   @ZenCodeType.Method
   public void addTransformRecipe(String recipeName, IItemStack result, IIngredient template, IIngredient base, IIngredient addition) {
      SmithingRecipe recipe = new SmithingTransformRecipe(
         template.asVanillaIngredient(), base.asVanillaIngredient(), addition.asVanillaIngredient(), result.getInternal()
      );
      CraftTweakerAPI.apply(new ActionAddRecipe(this, this.createHolder(this.fixRecipeId(recipeName), recipe), "transform"));
   }

   @Override
   public RecipeType<SmithingRecipe> getRecipeType() {
      return RecipeType.SMITHING;
   }
}
