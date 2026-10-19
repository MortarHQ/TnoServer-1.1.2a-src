package cn.leolezury.eternalstarlight.common.compat.jei;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceBlock;
import cn.leolezury.eternalstarlight.common.client.gui.screen.AlloyFurnaceScreen;
import cn.leolezury.eternalstarlight.common.compat.jei.category.AlloyCategory;
import cn.leolezury.eternalstarlight.common.compat.jei.category.AlloyFurnaceCoolingCategory;
import cn.leolezury.eternalstarlight.common.compat.jei.category.DryingCategory;
import cn.leolezury.eternalstarlight.common.compat.jei.category.GeyserSmokingCategory;
import cn.leolezury.eternalstarlight.common.compat.jei.recipe.AlloyFurnaceCoolingRecipe;
import cn.leolezury.eternalstarlight.common.item.menu.AlloyFurnaceMenu;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESMenuTypes;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;

@JeiPlugin
public class ESJeiPlugin implements IModPlugin {
   public void registerCategories(IRecipeCategoryRegistration registration) {
      IJeiHelpers jeiHelpers = registration.getJeiHelpers();
      IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
      registration.addRecipeCategories(
         new IRecipeCategory[]{
            new GeyserSmokingCategory(guiHelper), new DryingCategory(guiHelper), new AlloyCategory(guiHelper), new AlloyFurnaceCoolingCategory(guiHelper)
         }
      );
   }

   public void registerRecipes(IRecipeRegistration registration) {
      if (Minecraft.getInstance().level != null) {
         RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
         registration.addRecipes(GeyserSmokingCategory.GEYSER_SMOKING, manager.getAllRecipesFor(ESRecipes.GEYSER_SMOKING.get()));
         registration.addRecipes(DryingCategory.DRYING, manager.getAllRecipesFor(ESRecipes.DRYING.get()));
         registration.addRecipes(AlloyCategory.ALLOY, manager.getAllRecipesFor(ESRecipes.ALLOY.get()));
      }

      registration.addRecipes(
         AlloyFurnaceCoolingCategory.ALLOY_FURNACE_COOLING,
         AlloyFurnaceBlock.getCoolingRegistry().entrySet().stream().map(entry -> new AlloyFurnaceCoolingRecipe(entry.getKey(), entry.getValue())).toList()
      );
   }

   public void registerGuiHandlers(IGuiHandlerRegistration registration) {
      registration.addRecipeClickArea(
         AlloyFurnaceScreen.class,
         88,
         14,
         28,
         23,
         new RecipeType[]{AlloyCategory.ALLOY, RecipeTypes.FUELING, AlloyFurnaceCoolingCategory.ALLOY_FURNACE_COOLING}
      );
   }

   public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
      registration.addRecipeTransferHandler(AlloyFurnaceMenu.class, ESMenuTypes.ALLOY_FURNACE.get(), AlloyCategory.ALLOY, 0, 9, 14, 36);
      registration.addRecipeTransferHandler(AlloyFurnaceMenu.class, ESMenuTypes.ALLOY_FURNACE.get(), RecipeTypes.FUELING, 9, 1, 14, 36);
      registration.addRecipeTransferHandler(
         AlloyFurnaceMenu.class, ESMenuTypes.ALLOY_FURNACE.get(), AlloyFurnaceCoolingCategory.ALLOY_FURNACE_COOLING, 10, 1, 14, 36
      );
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      registration.addRecipeCatalysts(
         GeyserSmokingCategory.GEYSER_SMOKING,
         new ItemLike[]{(ItemLike)ESBlocks.ABYSSAL_GEYSER.get(), (ItemLike)ESBlocks.THERMABYSSAL_GEYSER.get(), (ItemLike)ESBlocks.CRYOBYSSAL_GEYSER.get()}
      );
      registration.addRecipeCatalysts(DryingCategory.DRYING, new ItemLike[]{(ItemLike)ESBlocks.DRYING_RACK.get()});
      registration.addRecipeCatalysts(
         AlloyCategory.ALLOY,
         new ItemLike[]{(ItemLike)ESBlocks.ALLOY_FURNACE.get(), (ItemLike)ESBlocks.WAXED_ALLOY_FURNACE.get(), (ItemLike)ESBlocks.OXIDIZED_ALLOY_FURNACE.get()}
      );
      registration.addRecipeCatalysts(
         RecipeTypes.FUELING,
         new ItemLike[]{(ItemLike)ESBlocks.ALLOY_FURNACE.get(), (ItemLike)ESBlocks.WAXED_ALLOY_FURNACE.get(), (ItemLike)ESBlocks.OXIDIZED_ALLOY_FURNACE.get()}
      );
      registration.addRecipeCatalysts(
         AlloyFurnaceCoolingCategory.ALLOY_FURNACE_COOLING,
         new ItemLike[]{(ItemLike)ESBlocks.ALLOY_FURNACE.get(), (ItemLike)ESBlocks.WAXED_ALLOY_FURNACE.get(), (ItemLike)ESBlocks.OXIDIZED_ALLOY_FURNACE.get()}
      );
   }

   public ResourceLocation getPluginUid() {
      return EternalStarlight.id("jei_plugin");
   }
}
