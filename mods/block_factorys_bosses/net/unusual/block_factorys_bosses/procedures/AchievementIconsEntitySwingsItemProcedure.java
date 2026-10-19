package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class AchievementIconsEntitySwingsItemProcedure {
   public static void execute(ItemStack itemstack) {
      if (((CustomData)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getDouble("icon_index") > 11.0) {
         String _tagName = "icon_index";
         double _tagValue = 0.0;
         CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("icon_index", 0.0));
      } else {
         String _tagName = "icon_index";
         double _tagValue = ((CustomData)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getDouble("icon_index") + 1.0;
         CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("icon_index", _tagValue));
      }
   }
}
