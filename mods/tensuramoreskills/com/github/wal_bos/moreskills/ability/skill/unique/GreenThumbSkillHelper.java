package com.github.wal_bos.moreskills.ability.skill.unique;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public final class GreenThumbSkillHelper {
   private GreenThumbSkillHelper() {
   }

   public static boolean isFood(ItemStack stack) {
      return !stack.isEmpty() && stack.get(DataComponents.FOOD) != null;
   }
}
