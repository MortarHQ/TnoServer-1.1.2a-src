package com.iafenvoy.iceandfire.compat.jei;

import com.iafenvoy.iceandfire.recipe.DragonForgeRecipe;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafRecipes;
import com.iafenvoy.iceandfire.screen.gui.bestiary.BestiaryScreen;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class IceAndFireJeiPlugin implements IModPlugin {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("iceandfire", "iceandfire");
   public static final RecipeType<DragonForgeRecipe> FIRE = RecipeType.create("minecraft", "firedragonforge", DragonForgeRecipe.class);
   public static final RecipeType<DragonForgeRecipe> ICE = RecipeType.create("minecraft", "icedragonforge", DragonForgeRecipe.class);
   public static final RecipeType<DragonForgeRecipe> LIGHTNING = RecipeType.create("minecraft", "lightningdragonforge", DragonForgeRecipe.class);

   @NotNull
   public ResourceLocation getPluginUid() {
      return ID;
   }

   public void registerCategories(IRecipeCategoryRegistration registration) {
      IJeiHelpers jeiHelpers = registration.getJeiHelpers();
      IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
      registration.addRecipeCategories(new IRecipeCategory[]{new FireDragonForgeRecipeCategory(guiHelper)});
      registration.addRecipeCategories(new IRecipeCategory[]{new IceDragonForgeRecipeCategory(guiHelper)});
      registration.addRecipeCategories(new IRecipeCategory[]{new LightningDragonForgeRecipeCategory(guiHelper)});
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      registration.addRecipeCatalyst((ItemLike)IafBlocks.DRAGONFORGE_FIRE_CORE.get(), new RecipeType[]{FIRE});
      registration.addRecipeCatalyst((ItemLike)IafBlocks.DRAGONFORGE_ICE_CORE.get(), new RecipeType[]{ICE});
      registration.addRecipeCatalyst((ItemLike)IafBlocks.DRAGONFORGE_LIGHTNING_CORE.get(), new RecipeType[]{LIGHTNING});
   }

   public void registerRecipes(@NotNull IRecipeRegistration registration) {
      RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
      List<RecipeHolder<DragonForgeRecipe>> recipeList = recipeManager.getAllRecipesFor(
         (net.minecraft.world.item.crafting.RecipeType)IafRecipes.DRAGON_FORGE_TYPE.get()
      );
      List<DragonForgeRecipe> FIRE_RECIPES = new ArrayList<>();
      List<DragonForgeRecipe> ICE_RECIPES = new ArrayList<>();
      List<DragonForgeRecipe> LIGHTNING_RECIPES = new ArrayList<>();

      for (RecipeHolder<DragonForgeRecipe> recipe : recipeList) {
         switch (((DragonForgeRecipe)recipe.value()).getDragonType()) {
            case "fire":
               FIRE_RECIPES.add((DragonForgeRecipe)recipe.value());
               break;
            case "ice":
               ICE_RECIPES.add((DragonForgeRecipe)recipe.value());
               break;
            case "lightning":
               LIGHTNING_RECIPES.add((DragonForgeRecipe)recipe.value());
         }
      }

      registration.addRecipes(FIRE, FIRE_RECIPES);
      registration.addRecipes(ICE, ICE_RECIPES);
      registration.addRecipes(LIGHTNING, LIGHTNING_RECIPES);
   }

   public void registerGuiHandlers(IGuiHandlerRegistration registration) {
      registration.addGuiScreenHandler(BestiaryScreen.class, screen -> new IGuiProperties() {
         @NotNull
         public Class<? extends Screen> screenClass() {
            return BestiaryScreen.class;
         }

         public int guiLeft() {
            return 0;
         }

         public int guiTop() {
            return 0;
         }

         public int guiXSize() {
            return screen.width;
         }

         public int guiYSize() {
            return screen.height;
         }

         public int screenWidth() {
            return screen.width;
         }

         public int screenHeight() {
            return screen.height;
         }
      });
   }
}
