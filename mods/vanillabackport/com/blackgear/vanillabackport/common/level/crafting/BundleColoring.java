package com.blackgear.vanillabackport.common.level.crafting;

import com.blackgear.vanillabackport.common.api.bundle.BundleFeatures;
import com.blackgear.vanillabackport.common.registries.ModRecipeSerializers;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class BundleColoring extends CustomRecipe {
   public BundleColoring(CraftingBookCategory category) {
      super(category);
   }

   public boolean matches(CraftingInput input, Level level) {
      int bundles = 0;
      int dyes = 0;

      for (int slot = 0; slot < input.size(); slot++) {
         ItemStack stack = input.getItem(slot);
         if (!stack.isEmpty()) {
            if (isBundle(stack.getItem())) {
               bundles++;
            } else {
               if (!(stack.getItem() instanceof DyeItem)) {
                  return false;
               }

               dyes++;
            }

            if (dyes > 1 || bundles > 1) {
               return false;
            }
         }
      }

      return bundles == 1 && dyes == 1;
   }

   public ItemStack assemble(CraftingInput input, Provider registries) {
      ItemStack bundle = ItemStack.EMPTY;
      DyeItem dye = (DyeItem)Items.WHITE_DYE;

      for (int slot = 0; slot < input.size(); slot++) {
         ItemStack stack = input.getItem(slot);
         if (!stack.isEmpty()) {
            Item item = stack.getItem();
            if (isBundle(item)) {
               bundle = stack;
            } else if (item instanceof DyeItem) {
               dye = (DyeItem)item;
            }
         }
      }

      Item result = BundleFeatures.getByColor(dye.getDyeColor());
      return bundle.transmuteCopy(result, 1);
   }

   public boolean canCraftInDimensions(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> getSerializer() {
      return ModRecipeSerializers.BUNDLE_COLORING.get();
   }

   private static boolean isBundle(Item item) {
      return item == Items.BUNDLE || BundleFeatures.BUNDLES_BY_DYE.containsValue(item);
   }
}
