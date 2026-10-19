package twilightforest.item.recipe;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFItems;
import twilightforest.init.TFRecipes;

public class EssenceRepairRecipe extends CustomRecipe {
   public EssenceRepairRecipe(CraftingBookCategory category) {
      super(category);
   }

   public boolean matches(CraftingInput input, Level level) {
      boolean scepter = false;
      boolean essence = false;

      for (int i = 0; i < input.size(); i++) {
         ItemStack stackInQuestion = input.getItem(i);
         if (!stackInQuestion.isEmpty()) {
            if (stackInQuestion.is(ItemTagGenerator.SCEPTERS) && stackInQuestion.isDamaged()) {
               if (scepter) {
                  return false;
               }

               scepter = true;
            } else {
               if (!stackInQuestion.is((Item)TFItems.EXANIMATE_ESSENCE.get())) {
                  return false;
               }

               if (essence) {
                  return false;
               }

               essence = true;
            }
         }
      }

      return scepter && essence;
   }

   public ItemStack assemble(CraftingInput input, Provider access) {
      ItemStack scepter = null;

      for (int i = 0; i < input.size(); i++) {
         ItemStack itemstack = input.getItem(i);
         if (!itemstack.isEmpty() && itemstack.is(ItemTagGenerator.SCEPTERS)) {
            if (scepter != null) {
               return ItemStack.EMPTY;
            }

            scepter = itemstack;
         }
      }

      if (scepter != null && scepter.isDamaged()) {
         ItemStack repaired = scepter.copy();
         repaired.setDamageValue(0);
         return repaired;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public boolean canCraftInDimensions(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)TFRecipes.ESSENCE_REPAIR_RECIPE.get();
   }
}
