package com.iafenvoy.iceandfire.item;

import com.iafenvoy.iceandfire.registry.IafItems;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class GenericItem extends Item {
   private final int description;

   public GenericItem(int textLength) {
      super(new Properties());
      this.description = textLength;
   }

   public boolean isFoil(ItemStack stack) {
      return this == IafItems.CREATIVE_DRAGON_MEAL.get() ? true : super.isFoil(stack);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      if (this.description > 0) {
         for (int i = 0; i < this.description; i++) {
            tooltip.add(Component.translatable(this.getDescriptionId() + ".desc_" + i).withStyle(ChatFormatting.GRAY));
         }
      }
   }
}
