package com.mortal_cultivation.compat.emi;

import com.mortal_cultivation.data.recipe.TalismanRecipe;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class TalismanMakingEmiRecipe extends BasicEmiRecipe {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/gui/talisman_making_jei.png");
   private static final int TEXTURE_U = 8;
   private static final int TEXTURE_V = 8;
   private static final int[][] INPUT_SLOTS = new int[][]{{58, 20}, {85, 11}, {112, 20}, {124, 48}, {103, 74}, {65, 74}, {44, 48}};

   public TalismanMakingEmiRecipe(RecipeHolder<TalismanRecipe> holder) {
      super(MortalCultivationEmiPlugin.TALISMAN_CATEGORY, holder.id(), 186, 102);
      TalismanRecipe recipe = (TalismanRecipe)holder.value();
      recipe.getIngredients().forEach(ingredient -> this.inputs.add(EmiIngredient.of(ingredient)));
      this.outputs.add(EmiStack.of(recipe.getOutput()));
   }

   public void addWidgets(WidgetHolder widgets) {
      widgets.addTexture(TEXTURE, 0, 0, 186, 102, 8, 8);

      for (int index = 0; index < this.inputs.size() && index < INPUT_SLOTS.length; index++) {
         widgets.addSlot((EmiIngredient)this.inputs.get(index), INPUT_SLOTS[index][0], INPUT_SLOTS[index][1]).drawBack(false);
      }

      widgets.addSlot((EmiIngredient)this.outputs.get(0), 84, 46).drawBack(false).recipeContext(this);
   }
}
