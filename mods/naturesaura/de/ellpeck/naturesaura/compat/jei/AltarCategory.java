package de.ellpeck.naturesaura.compat.jei;

import de.ellpeck.naturesaura.blocks.ModBlocks;
import de.ellpeck.naturesaura.recipes.AltarRecipe;
import java.util.Arrays;
import java.util.Collections;
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

public class AltarCategory implements IRecipeCategory<AltarRecipe> {
   private final IDrawable background;
   private final ItemStack altar = new ItemStack(ModBlocks.NATURE_ALTAR);

   public AltarCategory(IGuiHelper helper) {
      this.background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath("naturesaura", "textures/gui/jei/altar.png"), 0, 0, 103, 57);
   }

   public RecipeType<AltarRecipe> getRecipeType() {
      return JEINaturesAuraPlugin.ALTAR;
   }

   public Component getTitle() {
      return Component.translatable("container." + JEINaturesAuraPlugin.ALTAR.getUid() + ".name");
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return null;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, AltarRecipe recipe, IFocusGroup focuses) {
      builder.addSlot(RecipeIngredientRole.INPUT, 1, 19).addItemStacks(Arrays.asList(recipe.input.getItems()));
      builder.addSlot(RecipeIngredientRole.OUTPUT, 81, 19).addItemStack(recipe.output);
      builder.addSlot(RecipeIngredientRole.CATALYST, 38, 19)
         .addItemStacks(recipe.catalyst == Ingredient.EMPTY ? Collections.singletonList(this.altar) : Arrays.asList(recipe.catalyst.getItems()));
   }
}
