package com.chronodawn.anvil;

import com.chronodawn.registry.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AnvilRepairHandler {
   public static final float TIME_CRYSTAL_REPAIR_PERCENTAGE = 0.5F;

   public static ItemStack calculateTimeCrystalRepair(ItemStack leftInput, ItemStack rightInput) {
      if (!rightInput.is((Item)ModItems.TIME_CRYSTAL.get())) {
         return null;
      }

      if (!leftInput.isDamageableItem()) {
         return null;
      }

      if (!leftInput.getItem().isValidRepairItem(leftInput, rightInput)) {
         return null;
      }

      int maxDamage = leftInput.getMaxDamage();
      int repairAmount = Math.round(maxDamage * 0.5F);
      ItemStack result = leftInput.copy();
      int currentDamage = result.getDamageValue();
      int newDamage = Math.max(0, currentDamage - repairAmount);
      result.setDamageValue(newDamage);
      return result;
   }

   public static int calculateRepairCost(ItemStack leftInput, ItemStack result) {
      int durabilityRestored = leftInput.getDamageValue() - result.getDamageValue();
      int maxDamage = leftInput.getMaxDamage();
      int cost = Math.max(1, durabilityRestored * 10 / maxDamage);
      int leftWorkCost = (Integer)leftInput.getOrDefault(DataComponents.REPAIR_COST, 0);
      return cost + leftWorkCost;
   }
}
