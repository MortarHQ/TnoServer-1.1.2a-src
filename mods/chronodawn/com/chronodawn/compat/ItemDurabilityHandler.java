package com.chronodawn.compat;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ItemDurabilityHandler {
   void damageItem(ItemStack var1, int var2, Player var3, EquipmentSlot var4);

   static ItemDurabilityHandler getInstance() {
      try {
         Class<?> handler121 = Class.forName("com.chronodawn.compat.ItemDurabilityHandler121");
         return (ItemDurabilityHandler)handler121.getDeclaredConstructor().newInstance();
      } catch (Exception e1) {
         try {
            Class<?> handler120 = Class.forName("com.chronodawn.compat.ItemDurabilityHandler120");
            return (ItemDurabilityHandler)handler120.getDeclaredConstructor().newInstance();
         } catch (Exception e2) {
            throw new RuntimeException("Failed to load ItemDurabilityHandler", e2);
         }
      }
   }
}
