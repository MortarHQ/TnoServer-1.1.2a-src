package com.mortal_cultivation.item;

import com.mortal_cultivation.item.tier.CultivationTiers;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class RuyiJinguBangItem extends SwordItem {
   public RuyiJinguBangItem(Properties properties) {
      super(CultivationTiers.RUYI_JINGU_BANG, properties.attributes(SwordItem.createAttributes(CultivationTiers.RUYI_JINGU_BANG, 0, -2.4F)));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("item.mortal_cultivation.ruyi_jingu_bang.tooltip1").withStyle(ChatFormatting.GOLD));
      tooltip.add(Component.translatable("item.mortal_cultivation.ruyi_jingu_bang.tooltip2").withStyle(ChatFormatting.GRAY));
      super.appendHoverText(stack, context, tooltip, flag);
   }

   public Rarity getRarity(ItemStack stack) {
      return Rarity.EPIC;
   }

   public boolean isFoil(ItemStack stack) {
      return true;
   }
}
