package com.github.ysbbbbbb.kaleidoscopecookery.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class KitchenKnifeItem extends SwordItem {
   public KitchenKnifeItem(Tier tier) {
      super(tier, new Properties().attributes(SwordItem.createAttributes(tier, 0, -2.0F)));
   }

   public KitchenKnifeItem(Tier tier, Properties properties) {
      super(tier, properties.attributes(SwordItem.createAttributes(tier, 0, -2.0F)));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("tooltip.kaleidoscope_cookery.kitchen_knife").withStyle(ChatFormatting.GRAY));
   }

   public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
      return itemAbility == ItemAbilities.SWORD_DIG;
   }
}
