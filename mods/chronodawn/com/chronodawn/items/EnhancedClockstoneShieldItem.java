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

public class EnhancedClockstoneShieldItem extends ShieldItem implements ChronoShieldMarker {
   public EnhancedClockstoneShieldItem(Properties properties) {
      super(properties.durability(ChronoShieldTier.T2.durability));
   }

   @Override
   public ChronoShieldTier getChronoShieldTier() {
      return ChronoShieldTier.T2;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
      tooltipComponents.add(Component.translatable("item.chronodawn.enhanced_clockstone_shield.tooltip.1"));
      tooltipComponents.add(Component.translatable("item.chronodawn.enhanced_clockstone_shield.tooltip.2"));
   }
}
