package net.jadenxgamer.netherexp.core.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class NonConsumableItem extends Item {
   public NonConsumableItem(Properties properties) {
      super(properties);
   }

   public boolean hasCraftingRemainingItem(ItemStack stack) {
      return true;
   }

   public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
      return new ItemStack(this);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
      tooltip.add(Component.translatable("item.netherexp.non_consumable.tooltip").withStyle(ChatFormatting.GRAY));
      super.appendHoverText(stack, context, tooltip, tooltipFlag);
   }
}
