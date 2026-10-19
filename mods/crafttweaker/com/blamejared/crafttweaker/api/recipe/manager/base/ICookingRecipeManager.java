package com.blamejared.crafttweaker.api.recipe.manager.base;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByOutputInput;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.ICookingRecipeManager")
@Document("vanilla/api/recipe/manager/ICookingRecipeManager")
public interface ICookingRecipeManager<T extends AbstractCookingRecipe> extends IRecipeManager<T> {
   @ZenCodeType.Method
   default void addRecipe(String name, IItemStack output, IIngredient input, float xp, int cookTime) {
      name = this.fixRecipeName(name);
      CraftTweakerAPI.apply(new ActionAddRecipe(this, this.makeRecipe(name, CookingBookCategory.MISC, output, input, xp, cookTime)));
   }

   @ZenCodeType.Method
   default void addRecipe(String name, CookingBookCategory category, IItemStack output, IIngredient input, float xp, int cookTime) {
      name = this.fixRecipeName(name);
      CraftTweakerAPI.apply(new ActionAddRecipe(this, this.makeRecipe(name, category, output, input, xp, cookTime)));
   }

   @ZenCodeType.Method
   default void removeRecipe(IItemStack output, IIngredient input) {
      CraftTweakerAPI.apply(new ActionRemoveRecipeByOutputInput(this, output, input));
   }

   RecipeHolder<T> makeRecipe(String var1, CookingBookCategory var2, IItemStack var3, IIngredient var4, float var5, int var6);
}
