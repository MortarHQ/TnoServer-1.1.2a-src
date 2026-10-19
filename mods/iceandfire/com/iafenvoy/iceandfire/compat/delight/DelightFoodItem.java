package com.iafenvoy.iceandfire.compat.delight;

import dev.architectury.platform.Platform;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class DelightFoodItem extends Item {
   public DelightFoodItem(Properties settings) {
      super(settings);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      if (!Platform.isModLoaded("farmersdelight")) {
         tooltip.add(Component.translatable("item.iceandfire.tooltip.require.delight"));
      }
   }
}
