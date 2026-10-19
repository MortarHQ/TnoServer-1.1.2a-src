package com.chronodawn.compat;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemDurabilityHandler121 implements ItemDurabilityHandler {
   @Override
   public void damageItem(ItemStack stack, int amount, Player player, EquipmentSlot slot) {
      stack.hurtAndBreak(amount, player, slot);
   }
}
