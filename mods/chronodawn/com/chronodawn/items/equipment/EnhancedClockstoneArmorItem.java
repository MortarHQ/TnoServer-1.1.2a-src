package com.chronodawn.items.equipment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;

public class EnhancedClockstoneArmorItem extends ArmorItem {
   public EnhancedClockstoneArmorItem(Type type, Properties properties) {
      super(EnhancedClockstoneArmorMaterial.ENHANCED_CLOCKSTONE, type, properties);
   }

   public static Properties createProperties(Type type) {
      return new Properties().stacksTo(1).durability(type.getDurability(28));
   }

   public static boolean isWearingFullSet(Player player) {
      return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof EnhancedClockstoneArmorItem
         && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof EnhancedClockstoneArmorItem
         && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof EnhancedClockstoneArmorItem
         && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof EnhancedClockstoneArmorItem;
   }
}
