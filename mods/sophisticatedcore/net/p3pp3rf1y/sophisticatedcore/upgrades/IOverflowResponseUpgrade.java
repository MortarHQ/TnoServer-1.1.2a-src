package net.p3pp3rf1y.sophisticatedcore.upgrades;

import net.minecraft.world.item.ItemStack;

public interface IOverflowResponseUpgrade {
   FilterLogic getFilterLogic();

   boolean worksInGui();

   ItemStack onSlotOverflow(ItemStack var1);

   ItemStack onStorageOverflow(ItemStack var1);

   boolean stackMatchesFilter(ItemStack var1);
}
