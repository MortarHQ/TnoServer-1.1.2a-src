package com.chronodawn.items.equipment;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;

public class ClockstoneArmorItem extends ArmorItem {
   public ClockstoneArmorItem(Type type, Properties properties) {
      super(ClockstoneArmorMaterial.CLOCKSTONE, type, properties);
   }

   public static Properties createProperties(Type type) {
      return new Properties().stacksTo(1).durability(type.getDurability(20));
   }
}
