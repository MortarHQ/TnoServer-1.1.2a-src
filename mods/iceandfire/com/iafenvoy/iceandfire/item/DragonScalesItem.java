package com.iafenvoy.iceandfire.item;

import com.iafenvoy.iceandfire.data.DragonColor;
import java.util.List;
import java.util.Locale;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class DragonScalesItem extends Item {
   final DragonColor type;

   public DragonScalesItem(DragonColor type) {
      super(new Properties());
      this.type = type;
   }

   public String getDescriptionId() {
      return "item.iceandfire.dragonscales";
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(Component.translatable("dragon." + this.type.getName().toLowerCase(Locale.ROOT)).withStyle(this.type.getColorFormatting()));
   }
}
