package mezz.jei.library.plugins.debug;

import java.util.Collection;
import java.util.Iterator;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.common.gui.textures.Textures;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ObnoxiouslyLargeCategory extends AbstractRecipeCategory<ObnoxiouslyLargeRecipe> {
   public static final RecipeType<ObnoxiouslyLargeRecipe> TYPE = RecipeType.create("jei", "obnoxiously_large_recipe", ObnoxiouslyLargeRecipe.class);
   private static final int GRID_WIDTH = 300;
   private static final int GRID_HEIGHT = 300;
   private final IIngredientManager ingredientManager;
   private final IDrawable slotBackground;

   public ObnoxiouslyLargeCategory(IGuiHelper helper, Textures textures, IIngredientManager ingredientManager) {
      super(TYPE, Component.literal("Obnoxiously Large Recipe Category"), textures.getFlameIcon(), 300 + 2 * helper.getSlotDrawable().getWidth(), 300);
      this.ingredientManager = ingredientManager;
      this.slotBackground = helper.getSlotDrawable();
   }

   public void setRecipe(IRecipeLayoutBuilder builder, ObnoxiouslyLargeRecipe recipe, IFocusGroup focuses) {
      int slotWidth = this.slotBackground.getWidth();
      int slotHeight = this.slotBackground.getHeight();
      int xCount = 300 / slotWidth;
      int yCount = 300 / slotHeight;
      int xOffset = (300 - xCount * slotWidth) / 2;
      int yOffset = (300 - yCount * slotHeight) / 2;
      Collection<ItemStack> allItems = this.ingredientManager.getAllItemStacks();
      Iterator<ItemStack> iterator = allItems.iterator();

      for (int x = 0; x < xCount; x++) {
         for (int y = 0; y < yCount; y++) {
            int xPos = xOffset + x * slotWidth;
            int yPos = yOffset + y * slotHeight;
            ItemStack stack = iterator.next();
            builder.addInputSlot(xPos + 1, yPos + 1).setStandardSlotBackground().addItemStack(stack);
         }
      }

      builder.addOutputSlot(300 + slotWidth, 150).setStandardSlotBackground().addItemStack(iterator.next());
   }

   public ResourceLocation getRegistryName(ObnoxiouslyLargeRecipe recipe) {
      return recipe.getRecipeId();
   }
}
