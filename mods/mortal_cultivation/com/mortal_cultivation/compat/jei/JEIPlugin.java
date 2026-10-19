package com.mortal_cultivation.compat.jei;

import com.mortal_cultivation.MortalCultivation;
import com.mortal_cultivation.data.recipe.TalismanRecipe;
import com.mortal_cultivation.recipe.AlchemyFurnaceRecipe;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.RecipeRegistry;
import com.mortal_cultivation.registry.item.CultivationItems;
import java.util.List;
import java.util.Objects;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
   private static final ResourceLocation PLUGIN_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "jei_plugin");

   public ResourceLocation getPluginUid() {
      return PLUGIN_ID;
   }

   public void registerCategories(IRecipeCategoryRegistration registration) {
      IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
      registration.addRecipeCategories(new IRecipeCategory[]{new TalismanMakingCategory(guiHelper)});
      registration.addRecipeCategories(new IRecipeCategory[]{new AlchemyFurnaceCategory(guiHelper)});
   }

   public void registerRecipes(IRecipeRegistration registration) {
      this.addItemInformation(registration);
      RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
      List<TalismanRecipe> talismanRecipes = recipeManager.getAllRecipesFor((RecipeType)RecipeRegistry.TALISMAN_TYPE.get())
         .stream()
         .<TalismanRecipe>map(RecipeHolder::value)
         .toList();
      MortalCultivation.LOGGER.info("Registering {} talisman recipes to JEI", talismanRecipes.size());
      registration.addRecipes(TalismanMakingCategory.RECIPE_TYPE, talismanRecipes);
      List<AlchemyFurnaceRecipe> alchemyFurnaceRecipes = recipeManager.getAllRecipesFor((RecipeType)RecipeRegistry.ALCHEMY_FURNACE_TYPE.get())
         .stream()
         .<AlchemyFurnaceRecipe>map(RecipeHolder::value)
         .toList();
      MortalCultivation.LOGGER.info("Registering {} alchemy furnace recipes to JEI", alchemyFurnaceRecipes.size());
      registration.addRecipes(AlchemyFurnaceCategory.RECIPE_TYPE, alchemyFurnaceRecipes);
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      registration.addRecipeCatalyst(
         new ItemStack((ItemLike)CultivationItems.TALISMAN_PAPER.get()), new mezz.jei.api.recipe.RecipeType[]{TalismanMakingCategory.RECIPE_TYPE}
      );
      registration.addRecipeCatalyst(
         new ItemStack((ItemLike)BlockRegistry.ALCHEMY_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[]{AlchemyFurnaceCategory.RECIPE_TYPE}
      );
   }

   private void addItemInformation(IRecipeRegistration registration) {
      registration.addIngredientInfo(
         new ItemStack((ItemLike)CultivationItems.CINNABAR_BLOOD_INK.get()),
         VanillaTypes.ITEM_STACK,
         new Component[]{Component.translatable("jei.mortal_cultivation.localized_fantasy_term.info")}
      );
      registration.addIngredientInfo(
         new ItemStack((ItemLike)CultivationItems.BRUSH.get()),
         VanillaTypes.ITEM_STACK,
         new Component[]{Component.translatable("jei.mortal_cultivation.brush.info")}
      );
      registration.addIngredientInfo(
         new ItemStack((ItemLike)CultivationItems.TALISMAN_PAPER.get()),
         VanillaTypes.ITEM_STACK,
         new Component[]{Component.translatable("jei.mortal_cultivation.talisman_paper.info")}
      );
   }
}
