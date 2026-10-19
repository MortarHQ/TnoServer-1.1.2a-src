package cn.leolezury.eternalstarlight.common.item.misc;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class BlossomOfStarsItem extends Item {
   public BlossomOfStarsItem(Properties properties) {
      super(properties);
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
      list.add(Component.translatable("tooltip.eternal_starlight.blossom_of_stars").withColor(5343172).withStyle(ChatFormatting.ITALIC));
      super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
   }
}
