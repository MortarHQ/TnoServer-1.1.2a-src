package com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.category;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.ChoppingBoardRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.google.common.collect.Lists;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class ChoppingBoardRecipeCategory implements IRecipeCategory<RecipeHolder<ChoppingBoardRecipe>> {
   public static final RecipeType<RecipeHolder<ChoppingBoardRecipe>> TYPE = RecipeType.createRecipeHolderType(
      ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "chopping_board")
   );
   private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "textures/gui/jei/chopping_board.png");
   private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_cookery.chopping_board");
   public static final int WIDTH = 176;
   public static final int HEIGHT = 78;
   private final IDrawable bgDraw;
   private final IDrawable iconDraw;

   public ChoppingBoardRecipeCategory(IGuiHelper guiHelper) {
      this.bgDraw = guiHelper.createDrawable(BG, 0, 0, 176, 78);
      this.iconDraw = guiHelper.createDrawableItemStack(((Item)ModItems.CHOPPING_BOARD.get()).getDefaultInstance());
   }

   public static List<RecipeHolder<ChoppingBoardRecipe>> getRecipes() {
      ClientLevel level = Minecraft.getInstance().level;
      if (level == null) {
         return List.of();
      }

      List<RecipeHolder<ChoppingBoardRecipe>> choppingBoardRecipes = Lists.newArrayList();
      choppingBoardRecipes.addAll(level.getRecipeManager().getAllRecipesFor(ModRecipes.CHOPPING_BOARD_RECIPE));
      return choppingBoardRecipes;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ChoppingBoardRecipe> recipe, IFocusGroup focuses) {
      Ingredient input = ((ChoppingBoardRecipe)recipe.value()).getIngredient();
      ItemStack output = ((ChoppingBoardRecipe)recipe.value()).getResult();
      builder.addSlot(RecipeIngredientRole.INPUT, 38, 27).addIngredients(input);
      builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 30).addItemStack(output);
   }

   public void draw(RecipeHolder<ChoppingBoardRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
      this.bgDraw.draw(guiGraphics);
   }

   public RecipeType<RecipeHolder<ChoppingBoardRecipe>> getRecipeType() {
      return TYPE;
   }

   public Component getTitle() {
      return TITLE;
   }

   public int getWidth() {
      return 176;
   }

   public int getHeight() {
      return 78;
   }

   @Nullable
   public IDrawable getIcon() {
      return this.iconDraw;
   }
}
