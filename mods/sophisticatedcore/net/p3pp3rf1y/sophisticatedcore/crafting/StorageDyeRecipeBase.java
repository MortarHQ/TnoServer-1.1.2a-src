package net.p3pp3rf1y.sophisticatedcore.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags.Items;

public abstract class StorageDyeRecipeBase extends CustomRecipe {
   protected StorageDyeRecipeBase(CraftingBookCategory category) {
      super(category);
   }

   public boolean matches(CraftingInput inv, Level worldIn) {
      boolean storagePresent = false;
      boolean dyePresent = false;

      for (int slot = 0; slot < inv.size(); slot++) {
         ItemStack slotStack = inv.getItem(slot);
         if (!slotStack.isEmpty()) {
            if (this.isDyeableStorageItem(slotStack)) {
               if (storagePresent) {
                  return false;
               }

               storagePresent = true;
            } else {
               if (!slotStack.is(Items.DYES)) {
                  return false;
               }

               dyePresent = true;
            }
         }
      }

      return storagePresent && dyePresent;
   }

   public ItemStack assemble(CraftingInput inv, Provider registries) {
      Map<Integer, List<DyeColor>> columnDyes = new HashMap<>();
      Tuple<Integer, ItemStack> columnStorage = null;

      for (int slot = 0; slot < inv.size(); slot++) {
         ItemStack slotStack = inv.getItem(slot);
         if (!slotStack.isEmpty()) {
            int column = slot % inv.width();
            if (this.isDyeableStorageItem(slotStack)) {
               if (columnStorage != null) {
                  return ItemStack.EMPTY;
               }

               columnStorage = new Tuple(column, slotStack);
            } else {
               if (!slotStack.is(Items.DYES)) {
                  return ItemStack.EMPTY;
               }

               DyeColor dyeColor = DyeColor.getColor(slotStack);
               if (dyeColor == null) {
                  return ItemStack.EMPTY;
               }

               columnDyes.computeIfAbsent(column, c -> new ArrayList<>()).add(dyeColor);
            }
         }
      }

      if (columnStorage == null) {
         return ItemStack.EMPTY;
      }

      ItemStack coloredStorage = ((ItemStack)columnStorage.getB()).copy();
      coloredStorage.setCount(1);
      int storageColumn = (Integer)columnStorage.getA();
      this.applyTintColors(columnDyes, coloredStorage, storageColumn);
      return coloredStorage;
   }

   protected abstract boolean isDyeableStorageItem(ItemStack var1);

   private void applyTintColors(Map<Integer, List<DyeColor>> columnDyes, ItemStack coloredStorage, int storageColumn) {
      List<DyeColor> mainDyes = new ArrayList<>();
      List<DyeColor> trimDyes = new ArrayList<>();

      for (Entry<Integer, List<DyeColor>> entry : columnDyes.entrySet()) {
         if (entry.getKey() <= storageColumn) {
            mainDyes.addAll(entry.getValue());
         }

         if (entry.getKey() >= storageColumn) {
            trimDyes.addAll(entry.getValue());
         }
      }

      this.applyColors(coloredStorage, mainDyes, trimDyes);
   }

   protected abstract void applyColors(ItemStack var1, List<DyeColor> var2, List<DyeColor> var3);

   public boolean canCraftInDimensions(int width, int height) {
      return width >= 2 && height >= 1;
   }
}
