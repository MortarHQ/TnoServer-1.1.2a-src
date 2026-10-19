package com.chronodawn.items;

import com.chronodawn.items.shield.ChronoShieldMarker;
import com.chronodawn.items.shield.ChronoShieldTier;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class ClockstoneShieldItem extends ShieldItem implements ChronoShieldMarker {
   public ClockstoneShieldItem(Properties properties) {
      super(properties.durability(ChronoShieldTier.T1.durability));
   }

   @Override
   public ChronoShieldTier getChronoShieldTier() {
      return ChronoShieldTier.T1;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
      tooltipComponents.add(Component.translatable("item.chronodawn.clockstone_shield.tooltip.1"));
      tooltipComponents.add(Component.translatable("item.chronodawn.clockstone_shield.tooltip.2"));
   }
}
