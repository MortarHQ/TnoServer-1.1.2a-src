package com.chronodawn.items.equipment;

import com.chronodawn.registry.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;

public class TemporalAmberArmorItem extends ArmorItem {
   private static final int REPAIR_INTERVAL_TICKS = 60;
   private static final int COMBAT_COOLDOWN_TICKS = 200;
   private static final int REPAIR_AMOUNT_PER_PIECE = 5;

   public TemporalAmberArmorItem(Type type, Properties properties) {
      super(TemporalAmberArmorMaterial.TEMPORAL_AMBER, type, properties);
   }

   public static Properties createProperties(Type type) {
      return new Properties().stacksTo(1).durability(type.getDurability(35));
   }

   public static boolean isWearingFullSet(Player player) {
      return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof TemporalAmberArmorItem
         && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof TemporalAmberArmorItem
         && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof TemporalAmberArmorItem
         && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof TemporalAmberArmorItem;
   }

   public static void tryAutoRepair(Player player) {
      if (isWearingFullSet(player)) {
         if (player.tickCount % 60 == 0) {
            if (player.tickCount - player.getLastHurtByMobTimestamp() >= 200) {
               ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
               ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
               ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
               ItemStack feet = player.getItemBySlot(EquipmentSlot.FEET);
               boolean anyDamaged = head.getDamageValue() > 0 || chest.getDamageValue() > 0 || legs.getDamageValue() > 0 || feet.getDamageValue() > 0;
               if (anyDamaged) {
                  int dustSlot = findDustSlot(player);
                  if (dustSlot != -1) {
                     player.getInventory().getItem(dustSlot).shrink(1);
                     repairPiece(head);
                     repairPiece(chest);
                     repairPiece(legs);
                     repairPiece(feet);
                  }
               }
            }
         }
      }
   }

   private static int findDustSlot(Player player) {
      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack item = player.getInventory().getItem(i);
         if (item.is((Item)ModItems.TEMPORAL_AMBER_DUST.get())) {
            return i;
         }
      }

      return -1;
   }

   private static void repairPiece(ItemStack stack) {
      int currentDamage = stack.getDamageValue();
      if (currentDamage > 0) {
         stack.setDamageValue(Math.max(0, currentDamage - 5));
      }
   }
}
