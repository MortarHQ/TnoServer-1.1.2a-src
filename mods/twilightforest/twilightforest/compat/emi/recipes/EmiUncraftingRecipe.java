package twilightforest.compat.emi.recipes;

import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.jetbrains.annotations.Nullable;
import twilightforest.compat.RecipeViewerConstants;
import twilightforest.compat.emi.TFEmiCategories;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.item.recipe.UncraftingRecipe;

public class EmiUncraftingRecipe<T extends CraftingRecipe> extends TFEmiRecipe<T> {
   @Nullable
   private List<EmiIngredient> displayedOutputs;

   public EmiUncraftingRecipe(RecipeHolder<T> recipe) {
      super(TFEmiCategories.UNCRAFTING, recipe, "/uncrafting/", 118, 54);
   }

   @Override
   protected void addAdditionalInputs(List<EmiIngredient> inputs) {
      if (this.getRecipe().value() instanceof UncraftingRecipe uncraftingRecipe) {
         inputs.add(EmiIngredient.of(uncraftingRecipe.getInput(), uncraftingRecipe.getCount()));
      } else {
         inputs.add(EmiStack.of(((CraftingRecipe)this.getRecipe().value()).getResultItem(Minecraft.getInstance().level.registryAccess())));
      }
   }

   @Override
   protected void addAdditionalOutputs(List<EmiStack> finalOutput) {
      this.displayedOutputs = new ArrayList<>();
      List<Ingredient> outputs = new ArrayList<>(((CraftingRecipe)this.getRecipe().value()).getIngredients());
      outputs.replaceAll(
         ingredientx -> Ingredient.of(
            Arrays.stream(ingredientx.getItems())
               .filter(o -> !o.is(ItemTagGenerator.BANNED_UNCRAFTING_INGREDIENTS))
               .filter(o -> o.getCraftingRemainingItem().isEmpty())
         )
      );
      int index = 0;

      for (int offset = 0; index - offset < outputs.size() && index < 9; index++) {
         int x = index % 3;
         int y = index / 3;
         if (((CraftingRecipe)this.getRecipe().value()).canCraftInDimensions(x, 3) | ((CraftingRecipe)this.getRecipe().value()).canCraftInDimensions(3, y)
            && !(this.getRecipe().value() instanceof ShapelessRecipe)) {
            offset++;
            this.displayedOutputs.add(EmiStack.EMPTY);
         } else {
            Ingredient ingredient = outputs.get(index - offset);
            this.displayedOutputs.add(EmiIngredient.of(ingredient));

            for (ItemStack output : ingredient.getItems()) {
               finalOutput.add(EmiStack.of(output));
            }
         }
      }

      while (this.displayedOutputs.size() < 9) {
         this.displayedOutputs.add(EmiStack.EMPTY);
      }
   }

   public void addWidgets(WidgetHolder widgets) {
      widgets.addTexture(EmiTexture.EMPTY_ARROW, 35, 18);

      for (int i = 0; i < this.displayedOutputs.size(); i++) {
         int x = i % 3;
         int y = i / 3;
         widgets.addSlot(this.displayedOutputs.get(i), x * 18 + 63, y * 18);
      }

      if (this.getRecipe().value() instanceof UncraftingRecipe uncraftingRecipe) {
         ItemStack[] stacks = uncraftingRecipe.getInput().getItems();
         ItemStack[] stackedStacks = new ItemStack[stacks.length];

         for (int i = 0; i < stacks.length; i++) {
            stackedStacks[i] = new ItemStack(stacks[0].getItem(), uncraftingRecipe.getCount());
         }

         widgets.addSlot(EmiIngredient.of(Stream.of(stackedStacks).map(EmiStack::of).toList(), uncraftingRecipe.getCount()), 5, 19);
      } else {
         widgets.addSlot(EmiStack.of(((CraftingRecipe)this.getRecipe().value()).getResultItem(Minecraft.getInstance().level.registryAccess())), 5, 14)
            .large(true)
            .recipeContext(this);
      }

      int cost = this.getRecipe().value() instanceof UncraftingRecipe ur
         ? ur.getCost()
         : RecipeViewerConstants.getRecipeCost(
            this.displayedOutputs.stream().map(ingredient -> ((EmiStack)ingredient.getEmiStacks().getFirst()).getItemStack()).toList()
         );
      if (cost > 0) {
         String costStr = cost + "";
         widgets.addText(Component.literal(costStr), 48 - Minecraft.getInstance().font.width(costStr), 22, RecipeViewerConstants.getXPColor(cost), true);
      }
   }

   public boolean supportsRecipeTree() {
      return false;
   }
}
