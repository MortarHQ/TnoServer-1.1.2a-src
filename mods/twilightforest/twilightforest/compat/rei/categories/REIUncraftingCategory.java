package twilightforest.compat.rei.categories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import twilightforest.compat.RecipeViewerConstants;
import twilightforest.compat.rei.TFREIServerPlugin;
import twilightforest.compat.rei.displays.REIUncraftingDisplay;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.item.recipe.UncraftingRecipe;

public class REIUncraftingCategory implements DisplayCategory<REIUncraftingDisplay> {
   private final Renderer icon = EntryStacks.of(TFBlocks.UNCRAFTING_TABLE);
   private final Component localizedName = Component.translatable("gui.twilightforest.uncrafting_jei");

   public CategoryIdentifier<REIUncraftingDisplay> getCategoryIdentifier() {
      return TFREIServerPlugin.UNCRAFTING;
   }

   public Component getTitle() {
      return this.localizedName;
   }

   public int getDisplayHeight() {
      return 64;
   }

   public int getDisplayWidth(REIUncraftingDisplay display) {
      return 126;
   }

   public Renderer getIcon() {
      return this.icon;
   }

   public List<Widget> setupDisplay(REIUncraftingDisplay display, Rectangle origin) {
      List<Widget> widgets = new ArrayList<>();
      widgets.add(Widgets.createRecipeBase(origin));
      Rectangle bounds = origin.getBounds();
      bounds.translate(5, 5);
      widgets.add(Widgets.createArrow(new Point(bounds.getX() + 29, bounds.getY() + 18)));
      List<Ingredient> outputs = new ArrayList<>(display.getRecipe().getIngredients());
      outputs.replaceAll(
         ingredient -> Ingredient.of(
            Arrays.stream(ingredient.getItems()).filter(o -> !o.is(ItemTagGenerator.BANNED_UNCRAFTING_INGREDIENTS)).filter(o -> !o.hasCraftingRemainingItem())
         )
      );
      CraftingRecipe recipe = display.getRecipe();
      List<Slot> inputSlots = new ArrayList<>();

      for (int y = 0; y < 3; y++) {
         for (int x = 0; x < 3; x++) {
            inputSlots.add(Widgets.createSlot(new Point(bounds.getX() + x * 18 + 63, bounds.getY() + y * 18 + 1)).markOutput());
         }
      }

      int j = 0;

      for (int k = 0; j - k < outputs.size() && j < 9; j++) {
         int x = j % 3;
         int y = j / 3;
         if (recipe.canCraftInDimensions(x, 3) | recipe.canCraftInDimensions(3, y) && !(recipe instanceof ShapelessRecipe)) {
            k++;
         } else {
            inputSlots.get(RecipeViewerConstants.getCraftingIndex(recipe, j - k)).entries(EntryIngredients.ofIngredient(outputs.get(j - k)));
         }
      }

      widgets.addAll(inputSlots);
      widgets.add(Widgets.createResultSlotBackground(new Point(bounds.getX() + 5, bounds.getY() + 19)));
      widgets.add(
         Widgets.createSlot(new Point(bounds.getX() + 5, bounds.getY() + 19))
            .markInput()
            .disableBackground()
            .entries((Collection)display.getInputEntries().getFirst())
      );
      j = display.getRecipe() instanceof UncraftingRecipe ur
         ? ur.getCost()
         : RecipeViewerConstants.getRecipeCost(
            display.getOutputEntries()
               .stream()
               .filter(ingredient -> !ingredient.isEmpty())
               .map(ingredient -> (ItemStack)((EntryStack)ingredient.getFirst()).castValue())
               .toList()
         );
      if (j > 0) {
         String costStr = j + "";
         widgets.add(
            Widgets.createLabel(new Point(bounds.getX() + 45 - Minecraft.getInstance().font.width(costStr), bounds.getY() + 22), Component.literal(costStr))
               .color(RecipeViewerConstants.getXPColor(j))
         );
      }

      return widgets;
   }
}
