package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class AchievementIconsPropertyValueProviderProcedure {
   public static double execute(ItemStack itemstack) {
      return ((CustomData)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getDouble("icon_index");
   }
}
