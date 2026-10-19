package com.github.L_Ender.cataclysm.items;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class Necklace_Of_The_Desert extends Item {
   public Necklace_Of_The_Desert(Properties group) {
      super(group);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
      tooltip.add(Component.translatable("item.cataclysm.necklace_of_the_desert.desc").withStyle(ChatFormatting.DARK_GREEN));
   }
}
