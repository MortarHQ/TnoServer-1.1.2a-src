package com.chronodawn.items.artifacts;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;

public class TimeTyrantMailItem extends ArmorItem {
   public TimeTyrantMailItem(Properties properties) {
      super(TimeTyrantArmorMaterial.MATERIAL, Type.CHESTPLATE, properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(600).rarity(Rarity.EPIC);
   }
}
