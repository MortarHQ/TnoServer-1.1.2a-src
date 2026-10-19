package com.mortal_cultivation.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class FastingTalismanItem extends Item {
   public static final int MAX_DAMAGE = 25;

   public FastingTalismanItem(Properties properties) {
      super(properties.durability(25));
   }

   public Component getName(ItemStack stack) {
      return Component.translatable(this.getDescriptionId(stack)).withStyle(ChatFormatting.LIGHT_PURPLE);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(Component.translatable("item.mortal_cultivation.fasting_talisman.desc").withStyle(ChatFormatting.GRAY));
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
