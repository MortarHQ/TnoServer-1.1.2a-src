package com.mortal_cultivation.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class CinnabarBloodInkItem extends Item {
   public static final int MAX_DAMAGE = 20;

   public CinnabarBloodInkItem(Properties properties) {
      super(properties.durability(20));
   }

   public Component getName(ItemStack stack) {
      return Component.translatable(this.getDescriptionId(stack)).withStyle(ChatFormatting.LIGHT_PURPLE);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(Component.translatable("item.mortal_cultivation.localized_fantasy_term.desc").withStyle(ChatFormatting.GRAY));
   }

   public boolean isFoil(ItemStack stack) {
      return false;
   }

   public static boolean damageItem(ItemStack stack, int amount) {
      if (!stack.isDamageableItem()) {
         return false;
      }

      int currentDamage = stack.getDamageValue();
      int newDamage = currentDamage + amount;
      stack.setDamageValue(newDamage);
      return newDamage >= stack.getMaxDamage();
   }
}
