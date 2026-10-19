package com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.category;

import com.github.ysbbbbbb.kaleidoscopecookery.api.recipe.soupbase.ISoupBase;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.farmersdelight.FarmersDelightCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.StockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase.SoupBaseManager;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.google.common.collect.Lists;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class StockpotRecipeCategory implements IRecipeCategory<RecipeHolder<StockpotRecipe>> {
   public static final RecipeType<RecipeHolder<StockpotRecipe>> TYPE = RecipeType.createRecipeHolderType(
      ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "stockpot")
   );
   private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "textures/gui/jei/stockpot.png");
   private static final Component TITLE = ComponentUtils.formatList(
      List.of(Component.translatable("jei.kaleidoscope_cookery.strict_recipe"), Component.translatable("block.kaleidoscope_cookery.stockpot")),
      CommonComponents.SPACE
   );
   public static final int WIDTH = 176;
   public static final int HEIGHT = 102;
   private final IDrawable bgDraw;
   private final IDrawable iconDraw;
   private final IDrawable slotDraw;

   public StockpotRecipeCategory(IGuiHelper guiHelper) {
      this.bgDraw = guiHelper.createDrawable(BG, 0, 0, 176, 102);
      this.iconDraw = guiHelper.createDrawableItemStack(((Item)ModItems.STOCKPOT.get()).getDefaultInstance());
      this.slotDraw = guiHelper.getSlotDrawable();
   }

   public static List<RecipeHolder<StockpotRecipe>> getRecipes() {
      ClientLevel level = Minecraft.getInstance().level;
      if (level == null) {
         return List.of();
      }

      List<RecipeHolder<StockpotRecipe>> stockpotRecipes = Lists.newArrayList();
      stockpotRecipes.addAll(level.getRecipeManager().getAllRecipesFor(ModRecipes.STOCKPOT_RECIPE));
      FarmersDelightCompat.getTransformRecipeForJei(level, stockpotRecipes);
      return stockpotRecipes;
   }

   public void draw(RecipeHolder<StockpotRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
      this.bgDraw.draw(guiGraphics);
      Component type = Component.translatable("jei.kaleidoscope_cookery.strict_recipe");
      this.drawCenteredString(guiGraphics, type, 88, 90);
   }

   private void drawCenteredString(GuiGraphics guiGraphics, Component text, int centerX, int y) {
      Font font = Minecraft.getInstance().font;
      FormattedCharSequence sequence = text.getVisualOrderText();
      guiGraphics.drawString(font, sequence, centerX - font.width(sequence) / 2, y, 5592405, false);
   }

   public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<StockpotRecipe> holder, IFocusGroup focuses) {
      StockpotRecipe recipe = (StockpotRecipe)holder.value();
      NonNullList<Ingredient> inputs = recipe.getIngredients();
      ItemStack output = recipe.result();

      for (int i = 0; i < inputs.size(); i++) {
         int xOffset = i % 3 * 18 + 15;
         int yOffset = i / 3 * 18 + 25;
         ((IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.INPUT, xOffset, yOffset).addIngredients((Ingredient)inputs.get(i)))
            .setBackground(this.slotDraw, -1, -1);
      }

      ISoupBase soupBase = SoupBaseManager.getSoupBase(recipe.soupBase());
      if (soupBase == null) {
         throw new RuntimeException("No soup found for " + recipe.soupBase());
      }

      ItemStack displayStack = soupBase.getDisplayStack();
      if (!displayStack.isEmpty()) {
         builder.addSlot(RecipeIngredientRole.INPUT, 72, 61).addIngredients(Ingredient.of(new ItemStack[]{displayStack}));
      }

      if (!recipe.carrier().isEmpty()) {
         builder.addSlot(RecipeIngredientRole.INPUT, 133, 18).addIngredients(recipe.carrier());
      }

      builder.addSlot(RecipeIngredientRole.OUTPUT, 143, 60).addItemStack(output);
   }

   public RecipeType<RecipeHolder<StockpotRecipe>> getRecipeType() {
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
