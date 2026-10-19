package twilightforest.item.recipe;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import twilightforest.init.TFItems;
import twilightforest.init.TFRecipes;

public class MoonwormQueenRepairRecipe extends CustomRecipe {
   public MoonwormQueenRepairRecipe(CraftingBookCategory category) {
      super(category);
   }

   public boolean matches(CraftingInput input, Level level) {
      ItemStack queen = null;
      List<ItemStack> berries = new ArrayList<>();

      for (int i = 0; i < input.size(); i++) {
         ItemStack stackInQuestion = input.getItem(i);
         if (!stackInQuestion.isEmpty()) {
            if (stackInQuestion.is((Item)TFItems.MOONWORM_QUEEN.get()) && stackInQuestion.isDamaged()) {
               if (queen != null) {
                  return false;
               }

               queen = stackInQuestion;
            } else {
               if (!stackInQuestion.is((Item)TFItems.TORCHBERRIES.get())) {
                  return false;
               }

               berries.add(stackInQuestion);
            }
         }
      }

      return queen != null && !berries.isEmpty();
   }

   public ItemStack assemble(CraftingInput input, Provider access) {
      List<Item> berries = new ArrayList<>();
      ItemStack queen = null;

      for (int i = 0; i < input.size(); i++) {
         ItemStack itemstack = input.getItem(i);
         if (!itemstack.isEmpty()) {
            if (itemstack.is((Item)TFItems.MOONWORM_QUEEN.get())) {
               if (queen != null) {
                  return ItemStack.EMPTY;
               }

               queen = itemstack;
            }

            if (itemstack.is((Item)TFItems.TORCHBERRIES.get())) {
               berries.add(itemstack.getItem());
            }
         }
      }

      if (!berries.isEmpty() && queen != null && queen.isDamaged()) {
         ItemStack newQueen = ((Item)TFItems.MOONWORM_QUEEN.get()).getDefaultInstance();
         newQueen.setDamageValue(queen.getDamageValue() - berries.size() * 64);
         return newQueen;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public boolean canCraftInDimensions(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)TFRecipes.MOONWORM_QUEEN_REPAIR_RECIPE.get();
   }
}
