package com.iafenvoy.iceandfire.item.armor;

import com.iafenvoy.iceandfire.data.TrollType;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class TrollArmorItem extends ArmorItem {
   private final TrollType trollType;

   public TrollArmorItem(TrollType trollType, Type type) {
      super(trollType.getMaterial(), type, new Properties().durability(switch (type) {
         case HELMET -> 220;
         case CHESTPLATE -> 320;
         case LEGGINGS -> 300;
         case BOOTS -> 260;
         case BODY -> 0;
         default -> throw new MatchException(null, null);
      }));
      this.trollType = trollType;
   }

   public static String getName(TrollType trollType, Type type) {
      return String.format(Locale.ROOT, "%s_troll_leather_%s", trollType.getName(), type.getName());
   }

   public Holder<ArmorMaterial> getMaterial() {
      return this.trollType.getMaterial();
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(
         Component.translatable(String.format(Locale.ROOT, "item.%s.troll_leather_armor_%s.desc", "iceandfire", this.type.getName()))
            .withStyle(ChatFormatting.GREEN)
      );
   }
}
