package twilightforest.compat.jei.categories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import twilightforest.compat.RecipeViewerConstants;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.item.recipe.UncraftingRecipe;

public class JEIUncraftingCategory implements IRecipeCategory<CraftingRecipe> {
   public static final RecipeType<CraftingRecipe> UNCRAFTING = RecipeType.create("twilightforest", "uncrafting", CraftingRecipe.class);
   private final IDrawable arrow;
   private final IDrawable icon;
   private final Component localizedName;

   public JEIUncraftingCategory(IGuiHelper guiHelper) {
      this.arrow = guiHelper.getRecipeArrow();
      this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack((ItemLike)TFBlocks.UNCRAFTING_TABLE.get()));
      this.localizedName = Component.translatable("gui.twilightforest.uncrafting_jei");
   }

   public RecipeType<CraftingRecipe> getRecipeType() {
      return UNCRAFTING;
   }

   public Component getTitle() {
      return this.localizedName;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public int getWidth() {
      return 116;
   }

   public int getHeight() {
      return 54;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, CraftingRecipe recipe, IFocusGroup focuses) {
      List<Ingredient> outputs = new ArrayList<>(recipe.getIngredients());
      outputs.replaceAll(
         ingredient -> Ingredient.of(
            Arrays.stream(ingredient.getItems())
               .filter(o -> !o.is(ItemTagGenerator.BANNED_UNCRAFTING_INGREDIENTS))
               .filter(o -> !o.getItem().hasCraftingRemainingItem(o))
         )
      );
      List<IRecipeSlotBuilder> inputSlots = new ArrayList<>();

      for (int y = 0; y < 3; y++) {
         for (int x = 0; x < 3; x++) {
            IRecipeSlotBuilder slot = builder.addInputSlot(x * 18 + 63, y * 18 + 1).setStandardSlotBackground();
            inputSlots.add(slot);
         }
      }

      int j = 0;

      for (int k = 0; j - k < outputs.size() && j < 9; j++) {
         int x = j % 3;
         int y = j / 3;
         if (recipe.canCraftInDimensions(x, y) && !(recipe instanceof ShapelessRecipe)) {
            k++;
         } else {
            inputSlots.get(RecipeViewerConstants.getCraftingIndex(recipe, j - k)).addIngredients(outputs.get(j - k));
         }
      }

      if (recipe instanceof UncraftingRecipe uncraftingRecipe) {
         ItemStack[] stacks = uncraftingRecipe.getInput().getItems();
         ItemStack[] stackedStacks = new ItemStack[stacks.length];

         for (int i = 0; i < stacks.length; i++) {
            stackedStacks[i] = new ItemStack(stacks[0].getItem(), uncraftingRecipe.getCount());
         }

         ((IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.INPUT, 5, 19).addIngredients(Ingredient.of(stackedStacks))).setOutputSlotBackground();
      } else {
         ((IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.INPUT, 5, 19)
               .addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())))
            .setOutputSlotBackground();
      }
   }

   public void draw(CraftingRecipe recipe, IRecipeSlotsView views, GuiGraphics graphics, double mouseX, double mouseY) {
      this.arrow.draw(graphics, 33, 18);
      int cost = recipe instanceof UncraftingRecipe ur
         ? ur.getCost()
         : RecipeViewerConstants.getRecipeCost(
            views.getSlotViews(RecipeIngredientRole.OUTPUT).stream().map(view -> view.getDisplayedItemStack().orElse(ItemStack.EMPTY)).toList()
         );
      if (cost > 0) {
         String costStr = cost + "";
         graphics.drawString(
            Minecraft.getInstance().font, costStr, 45 - Minecraft.getInstance().font.width(costStr), 22, RecipeViewerConstants.getXPColor(cost), true
         );
      }
   }
}
