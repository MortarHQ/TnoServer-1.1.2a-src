package com.chronodawn.items;

import com.chronodawn.items.shield.ChronoShieldMarker;
import com.chronodawn.items.shield.ChronoShieldTier;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class EntropyCrystalShieldItem extends ShieldItem implements ChronoShieldMarker {
   public EntropyCrystalShieldItem(Properties properties) {
      super(properties.durability(ChronoShieldTier.T3.durability).rarity(Rarity.RARE));
   }

   @Override
   public ChronoShieldTier getChronoShieldTier() {
      return ChronoShieldTier.T3;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
      tooltipComponents.add(Component.translatable("item.chronodawn.entropy_crystal_shield.tooltip.1"));
      tooltipComponents.add(Component.translatable("item.chronodawn.entropy_crystal_shield.tooltip.2"));
   }
}
