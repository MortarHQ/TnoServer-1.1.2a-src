package com.blorb.morerelics.relics;

import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public abstract class CyberwareBase extends MoreRelicBase {
   public CyberwareBase(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return null;
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      ArrayList<Component> list = new ArrayList<>();
      list.add(Component.translatable("tooltip.morerelics.cyberware").withStyle(ChatFormatting.AQUA));
      return list;
   }
}
