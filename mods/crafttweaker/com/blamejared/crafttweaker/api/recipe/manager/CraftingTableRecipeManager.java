package com.blamejared.crafttweaker.api.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction1D;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipe;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipe;
import com.blamejared.crafttweaker.api.util.RecipeUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Arrays;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.CraftingTableRecipeManager")
@Document("vanilla/api/recipe/manager/CraftingTableRecipeManager")
public class CraftingTableRecipeManager implements IRecipeManager<CraftingRecipe> {
   @ZenCodeGlobals.Global("craftingTable")
   public static final CraftingTableRecipeManager INSTANCE = new CraftingTableRecipeManager();

   private CraftingTableRecipeManager() {
   }

   @ZenCodeType.Method
   public void addShaped(String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional RecipeFunction2D recipeFunction) {
      CraftTweakerAPI.apply(
         new ActionAddRecipe(
            this, this.createHolder(this.fixRecipeId(recipeName), new CTShapedRecipe(output, ingredients, MirrorAxis.NONE, recipeFunction)), "shaped"
         )
      );
   }

   @ZenCodeType.Method
   public void addShapedPattern(
      String recipeName, IItemStack output, String[] pattern, Map<String, IIngredient> keys, @ZenCodeType.Optional RecipeFunction2D recipeFunction
   ) {
      int height = pattern.length;
      int width = Arrays.stream(pattern).mapToInt(String::length).max().orElse(0);
      IIngredient[][] ingredients = RecipeUtil.dissolvePattern(pattern, keys, width, height);
      CraftTweakerAPI.apply(
         new ActionAddRecipe(
            this, this.createHolder(this.fixRecipeId(recipeName), new CTShapedRecipe(output, ingredients, MirrorAxis.NONE, recipeFunction)), "shaped"
         )
      );
   }

   @ZenCodeType.Method
   public void addShapedMirrored(
      String recipeName, MirrorAxis mirrorAxis, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional RecipeFunction2D recipeFunction
   ) {
      CraftTweakerAPI.apply(
         new ActionAddRecipe(
            this, this.createHolder(this.fixRecipeId(recipeName), new CTShapedRecipe(output, ingredients, mirrorAxis, recipeFunction)), "mirroring shaped"
         )
      );
   }

   @ZenCodeType.Method
   public void addShapeless(String recipeName, IItemStack output, IIngredient[] ingredients, @ZenCodeType.Optional RecipeFunction1D recipeFunction) {
      ResourceLocation name = this.fixRecipeId(recipeName);
      CTShapelessRecipe.checkEmptyIngredient(name, ingredients);
      CraftTweakerAPI.apply(new ActionAddRecipe(this, this.createHolder(name, new CTShapelessRecipe(output, ingredients, recipeFunction)), "shapeless"));
   }

   @Override
   public RecipeType<CraftingRecipe> getRecipeType() {
      return RecipeType.CRAFTING;
   }
}
