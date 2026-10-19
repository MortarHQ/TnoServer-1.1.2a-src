package com.chronodawn.items.artifacts;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;

public class EchoingTimeBootsItem extends ArmorItem {
   public EchoingTimeBootsItem(Properties properties) {
      super(TimeTyrantArmorMaterial.MATERIAL, Type.BOOTS, properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC);
   }
}
