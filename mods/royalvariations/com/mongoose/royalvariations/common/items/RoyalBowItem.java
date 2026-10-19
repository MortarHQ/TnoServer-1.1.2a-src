package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.common.entities.projectile.RoyalArrow;
import com.mongoose.royalvariations.utils.ItemHelper;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class RoyalBowItem extends BowItem {
   public RoyalBowItem() {
      super(new Properties().durability(600));
   }

   public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
      if (arrow instanceof RoyalArrow royalArrow) {
         royalArrow.setMarking(true);
         return royalArrow;
      } else {
         return super.customArrow(arrow, projectileStack, weaponStack);
      }
   }

   public int getEnchantmentValue(ItemStack stack) {
      return 22;
   }

   public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
      return pRepair.getItem() instanceof BowItem;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      ItemHelper.addOnShift(tooltip, () -> this.addInformationAfterShift(tooltip));
   }

   public void addInformationAfterShift(List<Component> tooltip) {
      tooltip.add(Component.translatable("info.royalvariations.royal_bow"));
   }
}
