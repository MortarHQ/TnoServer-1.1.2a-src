package nadiendev.constructionwand.crafting;

import javax.annotation.Nonnull;
import nadiendev.constructionwand.api.IWandUpgrade;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.wand.ItemWand;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RecipeWandUpgrade extends CustomRecipe {
   public RecipeWandUpgrade(CraftingBookCategory category) {
      super(category);
   }

   public boolean matches(@Nonnull CraftingInput inv, @Nonnull Level worldIn) {
      ItemStack wand = null;
      IWandUpgrade upgrade = null;

      for (int i = 0; i < inv.size(); i++) {
         ItemStack stack = inv.getItem(i);
         if (!stack.isEmpty()) {
            if (wand == null && stack.getItem() instanceof ItemWand) {
               wand = stack;
            } else {
               if (upgrade != null || !(stack.getItem() instanceof IWandUpgrade)) {
                  return false;
               }

               upgrade = (IWandUpgrade)stack.getItem();
            }
         }
      }

      return wand != null && upgrade != null
         ? !new WandOptions(wand).hasUpgrade(upgrade) && ConfigServer.getWandProperties(wand.getItem()).isUpgradeable()
         : false;
   }

   @Nonnull
   public ItemStack assemble(@Nonnull CraftingInput input, Provider registries) {
      ItemStack wand = null;
      IWandUpgrade upgrade = null;

      for (int i = 0; i < input.size(); i++) {
         ItemStack stack = input.getItem(i);
         if (!stack.isEmpty()) {
            if (stack.getItem() instanceof ItemWand) {
               wand = stack;
            } else if (stack.getItem() instanceof IWandUpgrade) {
               upgrade = (IWandUpgrade)stack.getItem();
            }
         }
      }

      if (wand != null && upgrade != null) {
         ItemStack newWand = wand.copy();
         new WandOptions(newWand).addUpgrade(upgrade);
         return newWand;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public boolean canCraftInDimensions(int width, int height) {
      return width * height >= 2;
   }

   @Nonnull
   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)ModRecipes.WAND_UPGRADE.get();
   }
}
