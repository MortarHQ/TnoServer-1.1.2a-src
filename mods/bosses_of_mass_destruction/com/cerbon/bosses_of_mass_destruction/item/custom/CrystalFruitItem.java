package com.cerbon.bosses_of_mass_destruction.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class CrystalFruitItem extends Item {
   public CrystalFruitItem(Properties pProperties) {
      super(pProperties);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      tooltipComponents.add(Component.translatable("item.bosses_of_mass_destruction.crystal_fruit.tooltip").withStyle(ChatFormatting.DARK_GRAY));
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
   }
}
