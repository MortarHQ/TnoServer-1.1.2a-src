package com.mortal_cultivation.compat.jei;

import com.mortal_cultivation.recipe.AlchemyFurnaceRecipe;
import com.mortal_cultivation.registry.BlockRegistry;
import java.util.List;
import java.util.Map;
import mezz.jei.api.constants.VanillaTypes;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class AlchemyFurnaceCategory implements IRecipeCategory<AlchemyFurnaceRecipe> {
   public static final RecipeType<AlchemyFurnaceRecipe> RECIPE_TYPE = RecipeType.create("mortal_cultivation", "alchemy_furnace", AlchemyFurnaceRecipe.class);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/gui/alchemy_furnace_table.png");
   private final IDrawable background;
   private final IDrawable icon;
   private final Component localizedName;
   private static final int[][] MATERIAL_SLOTS = new int[][]{{25, 25}, {43, 25}, {61, 25}};
   private static final int[][] RESULT_SLOTS = new int[][]{{133, 32}, {124, 50}, {142, 50}};

   public AlchemyFurnaceCategory(IGuiHelper guiHelper) {
      this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 176, 85);
      this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack((ItemLike)BlockRegistry.ALCHEMY_FURNACE.get()));
      this.localizedName = Component.translatable("gui.mortal_cultivation.category.alchemy_furnace");
   }

   public RecipeType<AlchemyFurnaceRecipe> getRecipeType() {
      return RECIPE_TYPE;
   }

   public Component getTitle() {
      return this.localizedName;
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, AlchemyFurnaceRecipe recipe, IFocusGroup focuses) {
      List<Ingredient> ingredients = recipe.getIngredients();
      Map<Ingredient, Integer> ingredientCounts = recipe.getIngredientCounts();
      int slotIndex = 0;

      for (Ingredient ingredient : ingredients) {
         if (slotIndex >= MATERIAL_SLOTS.length) {
            break;
         }

         int count = ingredientCounts.getOrDefault(ingredient, 1);
         ItemStack[] stacks = ingredient.getItems();

         for (int i = 0; i < stacks.length; i++) {
            stacks[i] = stacks[i].copy();
            stacks[i].setCount(count);
         }

         builder.addSlot(RecipeIngredientRole.INPUT, MATERIAL_SLOTS[slotIndex][0], MATERIAL_SLOTS[slotIndex][1]).addItemStacks(List.of(stacks));
         slotIndex++;
      }

      List<ItemStack> results = recipe.getResults();

      for (int i = 0; i < results.size() && i < RESULT_SLOTS.length; i++) {
         builder.addSlot(RecipeIngredientRole.OUTPUT, RESULT_SLOTS[i][0], RESULT_SLOTS[i][1]).addItemStack(results.get(i));
      }
   }

   public void draw(AlchemyFurnaceRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
      int cookingTime = recipe.getCookingTime();
      int minutes = cookingTime / 1200;
      int seconds = cookingTime % 1200 / 20;
      String minutesKey = "gui.mortal_cultivation.alchemy_furnace.minutes";
      String secondsKey = "gui.mortal_cultivation.alchemy_furnace.seconds";
      Component timeText;
      if (minutes > 0) {
         if (seconds > 0) {
            timeText = Component.translatable(minutesKey, new Object[]{minutes}).append(" ").append(Component.translatable(secondsKey, new Object[]{seconds}));
         } else {
            timeText = Component.translatable(minutesKey, new Object[]{minutes});
         }
      } else {
         timeText = Component.translatable(secondsKey, new Object[]{seconds});
      }

      Component finalText = Component.translatable("gui.mortal_cultivation.alchemy_furnace.cooking_time").append(": ").append(timeText);
      graphics.drawString(Minecraft.getInstance().font, finalText, 95, 72, 16777215, true);
   }
}
