package com.github.L_Ender.cataclysm.jei;

import com.github.L_Ender.cataclysm.crafting.AltarOfAmethystRecipe;
import com.github.L_Ender.cataclysm.crafting.WeaponfusionRecipe;
import com.github.L_Ender.cataclysm.init.ModRecipeTypes;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

public class CMRecipes {
   private final RecipeManager recipeManager;

   public CMRecipes() {
      Minecraft minecraft = Minecraft.getInstance();
      ClientLevel level = minecraft.level;
      if (level != null) {
         this.recipeManager = level.getRecipeManager();
      } else {
         throw new NullPointerException("minecraft world must not be null.");
      }
   }

   public List<WeaponfusionRecipe> getWeaponfusionRecipes() {
      return this.recipeManager.getAllRecipesFor((RecipeType)ModRecipeTypes.WEAPON_FUSION.get()).stream().<WeaponfusionRecipe>map(RecipeHolder::value).toList();
   }

   public List<AltarOfAmethystRecipe> getAmethystBlessRecipes() {
      return this.recipeManager
         .getAllRecipesFor((RecipeType)ModRecipeTypes.AMETHYST_BLESS.get())
         .stream()
         .<AltarOfAmethystRecipe>map(RecipeHolder::value)
         .toList();
   }
}
