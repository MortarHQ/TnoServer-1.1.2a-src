package com.mortal_cultivation.compat.emi;

import com.mortal_cultivation.recipe.AlchemyFurnaceRecipe;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.emi.emi.api.widget.TextWidget.Alignment;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

public class AlchemyFurnaceEmiRecipe extends BasicEmiRecipe {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/gui/alchemy_furnace_table.png");
   private static final int[][] INPUT_SLOTS = new int[][]{{24, 24}, {42, 24}, {60, 24}};
   private static final int[][] OUTPUT_SLOTS = new int[][]{{132, 31}, {123, 49}, {141, 49}};
   private final AlchemyFurnaceRecipe recipe;

   public AlchemyFurnaceEmiRecipe(RecipeHolder<AlchemyFurnaceRecipe> holder) {
      super(MortalCultivationEmiPlugin.ALCHEMY_FURNACE_CATEGORY, holder.id(), 176, 85);
      this.recipe = (AlchemyFurnaceRecipe)holder.value();
      Map<Ingredient, Integer> ingredientCounts = this.recipe.getIngredientCounts();

      for (Ingredient ingredient : this.recipe.getIngredients()) {
         this.inputs.add(createSizedIngredient(ingredient, ingredientCounts.getOrDefault(ingredient, 1)));
      }

      for (ItemStack result : this.recipe.getResults()) {
         this.outputs.add(EmiStack.of(result));
      }
   }

   public void addWidgets(WidgetHolder widgets) {
      widgets.addTexture(TEXTURE, 0, 0, 176, 85, 0, 0);

      for (int index = 0; index < this.inputs.size() && index < INPUT_SLOTS.length; index++) {
         widgets.addSlot((EmiIngredient)this.inputs.get(index), INPUT_SLOTS[index][0], INPUT_SLOTS[index][1]).drawBack(false);
      }

      for (int index = 0; index < this.outputs.size() && index < OUTPUT_SLOTS.length; index++) {
         widgets.addSlot((EmiIngredient)this.outputs.get(index), OUTPUT_SLOTS[index][0], OUTPUT_SLOTS[index][1]).drawBack(false).recipeContext(this);
      }

      widgets.addText(formatCookingTime(this.recipe.getCookingTime()), 88, 60, 8289918, false).horizontalAlign(Alignment.CENTER);
   }

   private static EmiIngredient createSizedIngredient(Ingredient ingredient, int count) {
      List<EmiStack> stacks = Arrays.stream(ingredient.getItems())
         .<ItemStack>map(ItemStack::copy)
         .peek(stack -> stack.setCount(count))
         .<EmiStack>map(EmiStack::of)
         .toList();
      return EmiIngredient.of(stacks);
   }

   private static Component formatCookingTime(int cookingTime) {
      int minutes = cookingTime / 1200;
      int seconds = cookingTime % 1200 / 20;
      if (minutes > 0 && seconds > 0) {
         return Component.translatable("gui.mortal_cultivation.alchemy_furnace.minutes", new Object[]{minutes})
            .append(" ")
            .append(Component.translatable("gui.mortal_cultivation.alchemy_furnace.seconds", new Object[]{seconds}));
      } else {
         return minutes > 0
            ? Component.translatable("gui.mortal_cultivation.alchemy_furnace.minutes", new Object[]{minutes})
            : Component.translatable("gui.mortal_cultivation.alchemy_furnace.seconds", new Object[]{seconds});
      }
   }
}
