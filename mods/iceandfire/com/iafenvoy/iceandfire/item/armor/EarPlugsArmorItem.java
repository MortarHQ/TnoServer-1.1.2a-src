package com.iafenvoy.iceandfire.item.armor;

import com.iafenvoy.iceandfire.registry.IafArmorMaterials;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class EarPlugsArmorItem extends ArmorItem {
   public EarPlugsArmorItem() {
      super(IafArmorMaterials.EARPLUGS, Type.HELMET, new Properties().durability(55));
   }

   private static boolean isAprilFool() {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      return calendar.get(2) + 1 == 4 && calendar.get(5) == 1;
   }

   public String getDescriptionId(ItemStack stack) {
      return isAprilFool() ? String.format(Locale.ROOT, "item.%s.air_pods", "iceandfire") : super.getDescriptionId(stack);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      if (isAprilFool()) {
         tooltip.add(Component.translatable("item.iceandfire.air_pods.desc").withStyle(ChatFormatting.GREEN));
      }
   }
}
