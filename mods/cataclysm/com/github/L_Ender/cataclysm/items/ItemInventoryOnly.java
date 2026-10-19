package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.util.CustomTabBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.item.Item.Properties;

public class ItemInventoryOnly extends Item implements CustomTabBehavior {
   public ItemInventoryOnly(Properties properties) {
      super(properties);
   }

   @Override
   public void fillItemCategory(Output contents) {
   }
}
