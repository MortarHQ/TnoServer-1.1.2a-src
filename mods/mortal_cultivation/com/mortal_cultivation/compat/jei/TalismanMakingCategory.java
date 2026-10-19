package com.mortal_cultivation.compat.jei;

import com.mortal_cultivation.data.recipe.TalismanRecipe;
import com.mortal_cultivation.registry.item.CultivationItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class TalismanMakingCategory implements IRecipeCategory<TalismanRecipe> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/gui/talisman_making_jei.png");
   private static final int TEXTURE_U = 8;
   private static final int TEXTURE_V = 8;
   private static final int[][] INPUT_SLOTS = new int[][]{{59, 21}, {86, 12}, {113, 21}, {125, 49}, {104, 75}, {66, 75}, {45, 49}};
   private static final int OUTPUT_SLOT_X = 85;
   private static final int OUTPUT_SLOT_Y = 47;
   public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "talisman_making");
   public static final RecipeType<TalismanRecipe> RECIPE_TYPE = RecipeType.create("mortal_cultivation", "talisman_making", TalismanRecipe.class);
   private final IDrawable background;
   private final IDrawable icon;
   private final Component title;

   public TalismanMakingCategory(IGuiHelper guiHelper) {
      this.background = guiHelper.createDrawable(TEXTURE, 8, 8, 186, 102);
      this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack((ItemLike)CultivationItems.TALISMAN_PAPER.get()));
      this.title = Component.translatable("category.mortal_cultivation.talisman_making");
   }

   public RecipeType<TalismanRecipe> getRecipeType() {
      return RECIPE_TYPE;
   }

   public Component getTitle() {
      return this.title;
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, TalismanRecipe recipe, IFocusGroup focuses) {
      for (int i = 0; i < recipe.getIngredients().size() && i < INPUT_SLOTS.length; i++) {
         builder.addSlot(RecipeIngredientRole.INPUT, INPUT_SLOTS[i][0], INPUT_SLOTS[i][1]).addIngredients((Ingredient)recipe.getIngredients().get(i));
      }

      builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 47).addItemStack(recipe.getOutput().copy());
   }
}
