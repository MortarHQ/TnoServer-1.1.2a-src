package com.blamejared.crafttweaker.api.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.StoneCutterManager")
@Document("vanilla/api/recipe/manager/StoneCutterManager")
public class StoneCutterManager implements IRecipeManager<StonecutterRecipe> {
   @ZenCodeGlobals.Global("stoneCutter")
   public static final StoneCutterManager INSTANCE = new StoneCutterManager();

   private StoneCutterManager() {
   }

   @ZenCodeType.Method
   public void addRecipe(String recipeName, IItemStack output, IIngredient input) {
      CraftTweakerAPI.apply(
         new ActionAddRecipe(
            this, this.createHolder(this.fixRecipeId(recipeName), new StonecutterRecipe("", input.asVanillaIngredient(), output.getInternal()))
         )
      );
   }

   @Override
   public RecipeType<StonecutterRecipe> getRecipeType() {
      return RecipeType.STONECUTTING;
   }
}
