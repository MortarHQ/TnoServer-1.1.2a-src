package twilightforest.item.recipe;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.init.TFRecipes;

public class CasketRepairRecipe extends CustomRecipe {
   public CasketRepairRecipe(CraftingBookCategory category) {
      super(category);
   }

   public boolean matches(CraftingInput input, Level level) {
      boolean casket = false;
      boolean charm = false;

      for (int i = 0; i < input.size(); i++) {
         ItemStack stackInQuestion = input.getItem(i);
         if (!stackInQuestion.isEmpty()) {
            if (stackInQuestion.is(TFItems.KEEPSAKE_CASKET) && (Integer)stackInQuestion.getOrDefault(TFDataComponents.CASKET_DAMAGE, 0) > 0) {
               if (casket) {
                  return false;
               }

               casket = true;
            } else {
               if (!stackInQuestion.is((Item)TFItems.CHARM_OF_KEEPING_3.get())) {
                  return false;
               }

               if (charm) {
                  return false;
               }

               charm = true;
            }
         }
      }

      return casket && charm;
   }

   public ItemStack assemble(CraftingInput input, Provider access) {
      ItemStack casket = null;

      for (int i = 0; i < input.size(); i++) {
         ItemStack itemstack = input.getItem(i);
         if (!itemstack.isEmpty() && itemstack.is(TFItems.KEEPSAKE_CASKET)) {
            if (casket != null) {
               return ItemStack.EMPTY;
            }

            casket = itemstack;
         }
      }

      if (casket != null && (Integer)casket.getOrDefault(TFDataComponents.CASKET_DAMAGE, 0) > 0) {
         ItemStack repaired = casket.copy();
         repaired.update(TFDataComponents.CASKET_DAMAGE, (Integer)casket.get(TFDataComponents.CASKET_DAMAGE), integer -> integer - 1);
         return repaired;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public boolean canCraftInDimensions(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)TFRecipes.CASKET_REPAIR_RECIPE.get();
   }
}
