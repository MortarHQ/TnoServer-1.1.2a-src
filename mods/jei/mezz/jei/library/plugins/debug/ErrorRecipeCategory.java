package mezz.jei.library.plugins.debug;

import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.common.Internal;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ErrorRecipeCategory extends AbstractRecipeCategory<ErrorRecipe> {
   public static final RecipeType<ErrorRecipe> TYPE = RecipeType.create("jei", "error", ErrorRecipe.class);

   public ErrorRecipeCategory() {
      super(TYPE, Component.literal("error"), Internal.getTextures().getConfigButtonIcon(), 160, 60);
   }

   public void setRecipe(IRecipeLayoutBuilder builder, ErrorRecipe recipe, IFocusGroup focuses) {
      if (recipe.getType().equals(ErrorRecipe.CrashType.SetRecipe)) {
         throw new RuntimeException("JEI ErrorRecipe is intentionally crashing for testing purposes");
      }
   }

   public void draw(ErrorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
      if (recipe.getType().equals(ErrorRecipe.CrashType.Draw)) {
         throw new RuntimeException("JEI ErrorRecipe is intentionally crashing for testing purposes");
      }
   }

   public void createRecipeExtras(IRecipeExtrasBuilder builder, ErrorRecipe recipe, IFocusGroup focuses) {
      if (recipe.getType().equals(ErrorRecipe.CrashType.CreateRecipeExtras)) {
         throw new RuntimeException("JEI ErrorRecipe is intentionally crashing for testing purposes");
      }
   }

   public void onDisplayedIngredientsUpdate(ErrorRecipe recipe, List<IRecipeSlotDrawable> recipeSlots, IFocusGroup focuses) {
      if (recipe.getType().equals(ErrorRecipe.CrashType.OnDisplayedIngredientsUpdate)) {
         throw new RuntimeException("JEI ErrorRecipe is intentionally crashing for testing purposes");
      }
   }

   public void getTooltip(ITooltipBuilder tooltip, ErrorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
      if (recipe.getType().equals(ErrorRecipe.CrashType.GetTooltip)) {
         throw new RuntimeException("JEI ErrorRecipe is intentionally crashing for testing purposes");
      }
   }

   @Nullable
   public ResourceLocation getRegistryName(ErrorRecipe recipe) {
      ErrorRecipe.CrashType type = recipe.getType();
      return ResourceLocation.fromNamespaceAndPath("jei", "error." + type.name().toLowerCase());
   }
}
