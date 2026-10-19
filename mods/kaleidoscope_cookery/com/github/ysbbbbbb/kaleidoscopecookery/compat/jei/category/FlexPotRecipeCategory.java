package com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.category;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.FlexPotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.google.common.collect.Lists;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.gui.widgets.ITextWidget;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class FlexPotRecipeCategory implements IRecipeCategory<RecipeHolder<FlexPotRecipe>> {
   public static final RecipeType<RecipeHolder<FlexPotRecipe>> TYPE = RecipeType.createRecipeHolderType(
      ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "flex_pot")
   );
   private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "textures/gui/jei/pot.png");
   private static final Component TITLE = ComponentUtils.formatList(
      List.of(Component.translatable("jei.kaleidoscope_cookery.flex_recipe"), Component.translatable("block.kaleidoscope_cookery.pot")), CommonComponents.SPACE
   );
   public static final int WIDTH = 176;
   public static final int HEIGHT = 102;
   private final IDrawable bgDraw;
   private final IDrawable iconDraw;

   public FlexPotRecipeCategory(IGuiHelper guiHelper) {
      this.bgDraw = guiHelper.createDrawable(BG, 0, 0, 176, 102);
      this.iconDraw = guiHelper.createDrawableItemLike((ItemLike)ModItems.POT.get());
   }

   public static List<RecipeHolder<FlexPotRecipe>> getRecipes() {
      ClientLevel level = Minecraft.getInstance().level;
      if (level == null) {
         return List.of();
      }

      List<RecipeHolder<FlexPotRecipe>> potRecipes = Lists.newArrayList();
      potRecipes.addAll(level.getRecipeManager().getAllRecipesFor(ModRecipes.FLEX_POT_RECIPE));
      return potRecipes;
   }

   public void draw(RecipeHolder<FlexPotRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
      this.bgDraw.draw(guiGraphics);
      Component type = Component.translatable("jei.kaleidoscope_cookery.flex_recipe");
      this.drawCenteredString(guiGraphics, type, 88, 5);
      Component stirFryCount = Component.translatable(
         "jei.kaleidoscope_cookery.pot.stir_fry_count", new Object[]{((FlexPotRecipe)recipe.value()).stirFryCount()}
      );
      this.drawCenteredString(guiGraphics, stirFryCount, 88, 85);
   }

   private void drawCenteredString(GuiGraphics guiGraphics, Component text, int centerX, int y) {
      Font font = Minecraft.getInstance().font;
      FormattedCharSequence sequence = text.getVisualOrderText();
      guiGraphics.drawString(font, sequence, centerX - font.width(sequence) / 2, y, 5592405, false);
   }

   public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<FlexPotRecipe> recipe, IFocusGroup focuses) {
      NonNullList<Ingredient> inputs = ((FlexPotRecipe)recipe.value()).getIngredients();
      Component text = Component.literal("*");

      for (int i = 0; i < inputs.size(); i++) {
         if (!((Ingredient)inputs.get(i)).isEmpty()) {
            int xOffset = i % 3 * 18 + 15;
            int yOffset = i / 3 * 18 + 24;
            ((ITextWidget)builder.addText(text, 9, 9).setPosition(xOffset, yOffset)).setColor(16777215).setShadow(true);
         }
      }
   }

   public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<FlexPotRecipe> holder, IFocusGroup focuses) {
      FlexPotRecipe recipe = (FlexPotRecipe)holder.value();
      NonNullList<Ingredient> inputs = recipe.getIngredients();
      ItemStack output = recipe.result();

      for (int i = 0; i < inputs.size(); i++) {
         int xOffset = i % 3 * 18 + 15;
         int yOffset = i / 3 * 18 + 24;
         ((IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.INPUT, xOffset, yOffset).addIngredients((Ingredient)inputs.get(i)))
            .setStandardSlotBackground();
      }

      if (!recipe.carrier().isEmpty()) {
         builder.addSlot(RecipeIngredientRole.INPUT, 133, 18).addIngredients(recipe.carrier());
      }

      ((IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.OUTPUT, 143, 60).addItemStack(output)).setStandardSlotBackground();
   }

   public RecipeType<RecipeHolder<FlexPotRecipe>> getRecipeType() {
      return TYPE;
   }

   public Component getTitle() {
      return TITLE;
   }

   public int getWidth() {
      return 176;
   }

   public int getHeight() {
      return 102;
   }

   @Nullable
   public IDrawable getIcon() {
      return this.iconDraw;
   }
}
