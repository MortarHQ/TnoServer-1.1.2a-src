package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class Azure_sea_Shield extends ShieldItem {
   public Azure_sea_Shield(Properties properties) {
      super(properties);
   }

   public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
      return repair.is((Item)ModItems.LACRIMA.get()) || !repair.is(ItemTags.PLANKS) && super.isValidRepairItem(toRepair, repair);
   }

   public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
      return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(itemAbility);
   }
}
