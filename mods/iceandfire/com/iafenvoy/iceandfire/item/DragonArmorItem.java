package com.iafenvoy.iceandfire.item;

import com.iafenvoy.iceandfire.data.DragonArmorMaterial;
import com.iafenvoy.iceandfire.data.DragonArmorPart;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class DragonArmorItem extends Item {
   public final DragonArmorMaterial type;
   public final DragonArmorPart dragonSlot;
   public String name;
   private Pattern baseName = Pattern.compile("[a-z]+_[a-z]+");

   public DragonArmorItem(DragonArmorMaterial type, DragonArmorPart dragonSlot) {
      super(type.fireProof() ? new Properties().fireResistant() : new Properties());
      this.type = type;
      this.dragonSlot = dragonSlot;
      if (type.dragonSteel()) {
         this.baseName = Pattern.compile("[a-z]+_[a-z]+_[a-z]+_[a-z]+");
      }
   }

   public String getDescriptionId() {
      String fullName = BuiltInRegistries.ITEM.getKey(this).getPath();
      Matcher matcher = this.baseName.matcher(fullName);
      this.name = matcher.find() ? matcher.group() : fullName;
      return "item.iceandfire." + this.name;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      String words = "dragon.armor_" + this.dragonSlot.name().toLowerCase(Locale.ROOT);
      tooltip.add(Component.translatable(words).withStyle(ChatFormatting.GRAY));
   }
}
