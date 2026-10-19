package net.p3pp3rf1y.sophisticatedbackpacks.crafting;

import java.util.List;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.crafting.StorageDyeRecipeBase;
import net.p3pp3rf1y.sophisticatedcore.util.ColorHelper;

public class BackpackDyeRecipe extends StorageDyeRecipeBase {
   public BackpackDyeRecipe(CraftingBookCategory category) {
      super(category);
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)ModItems.BACKPACK_DYE_RECIPE_SERIALIZER.get();
   }

   protected boolean isDyeableStorageItem(ItemStack stack) {
      return stack.getItem() instanceof BackpackItem;
   }

   protected void applyColors(ItemStack coloredStorage, List<DyeColor> mainDyes, List<DyeColor> trimDyes) {
      IBackpackWrapper coloredWrapper = BackpackWrapper.fromStack(coloredStorage);
      coloredWrapper.setColors(
         ColorHelper.calculateColor(coloredWrapper.getMainColor(), -3382982, mainDyes),
         ColorHelper.calculateColor(coloredWrapper.getAccentColor(), -10342886, trimDyes)
      );
   }
}
