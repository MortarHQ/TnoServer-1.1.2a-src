package com.benji.netherman.common.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.block.Block;

public class AzazelTrophyItem extends BlockItem implements Equipable {
   public AzazelTrophyItem(Block block, Properties properties) {
      super(block, properties.fireResistant().stacksTo(1));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      Component trophy = Component.translatable("tooltip.netherman.trophy").withStyle(ChatFormatting.YELLOW);
      tooltipComponents.add(Component.translatable("tooltip.netherman.trophy.line1", new Object[]{trophy}).withStyle(ChatFormatting.GOLD));
      tooltipComponents.add(trophy);
   }

   public EquipmentSlot getEquipmentSlot() {
      return EquipmentSlot.HEAD;
   }
}
