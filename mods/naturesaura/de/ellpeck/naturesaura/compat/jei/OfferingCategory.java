package de.ellpeck.naturesaura.compat.jei;

import de.ellpeck.naturesaura.recipes.OfferingRecipe;
import java.util.Arrays;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class OfferingCategory implements IRecipeCategory<OfferingRecipe> {
   private final IDrawable background;

   public OfferingCategory(IGuiHelper helper) {
      this.background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath("naturesaura", "textures/gui/jei/offering.png"), 0, 0, 87, 36);
   }

   public RecipeType<OfferingRecipe> getRecipeType() {
      return JEINaturesAuraPlugin.OFFERING;
   }

   public Component getTitle() {
      return Component.translatable("container." + JEINaturesAuraPlugin.OFFERING.getUid() + ".name");
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return null;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, OfferingRecipe recipe, IFocusGroup focuses) {
      builder.addSlot(RecipeIngredientRole.INPUT, 1, 15).addItemStacks(Arrays.asList(recipe.input.getItems()));
      builder.addSlot(RecipeIngredientRole.OUTPUT, 66, 15).addItemStack(recipe.output);
      builder.addSlot(RecipeIngredientRole.INPUT, 28, 1).addItemStacks(Arrays.asList(recipe.startItem.getItems()));
   }
}
