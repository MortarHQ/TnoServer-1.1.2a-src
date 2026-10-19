package com.iafenvoy.iceandfire.item.armor;

import com.iafenvoy.iceandfire.data.DragonColor;
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

public class DragonScaleArmorItem extends ArmorItem {
   private final DragonColor color;

   public DragonScaleArmorItem(DragonColor color, Type slot) {
      super(color.getMaterial(), slot, new Properties().durability(switch (slot) {
         case HELMET -> 397;
         case CHESTPLATE -> 577;
         case LEGGINGS -> 541;
         case BOOTS -> 469;
         case BODY -> 0;
         default -> throw new MatchException(null, null);
      }));
      this.color = color;
   }

   public String getDescriptionId() {
      return String.format(Locale.ROOT, "item.%s.dragon_%s", "iceandfire", this.type.getName());
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      tooltip.add(Component.translatable("dragon." + this.color.getName().toLowerCase(Locale.ROOT)).withStyle(this.color.getColorFormatting()));
      tooltip.add(Component.translatable("item.dragonscales_armor.desc").withStyle(ChatFormatting.GRAY));
   }

   public DragonColor getColor() {
      return this.color;
   }
}
