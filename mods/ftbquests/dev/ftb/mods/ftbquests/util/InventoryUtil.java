package dev.ftb.mods.ftbquests.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftbquests.util.neoforge.InventoryUtilImpl;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InventoryUtil {
   @ExpectPlatform
   @Transformed
   public static NonNullList<ItemStack> getItemsInInventory(Level level, BlockPos pos, Direction side) {
      return InventoryUtilImpl.getItemsInInventory(level, pos, side);
   }

   @ExpectPlatform
   @Transformed
   public static boolean putItemsInInventory(List<ItemStack> items, Level level, BlockPos pos, Direction side, boolean clearFirst) {
      return InventoryUtilImpl.putItemsInInventory(items, level, pos, side, clearFirst);
   }
}
