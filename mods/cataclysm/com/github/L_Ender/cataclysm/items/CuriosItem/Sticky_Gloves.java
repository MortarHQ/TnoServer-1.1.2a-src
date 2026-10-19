package com.github.L_Ender.cataclysm.items.CuriosItem;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;

public class Sticky_Gloves extends CuriosItem {
   public Sticky_Gloves(Properties group) {
      super(group);
   }

   public SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
      return new SoundInfo(SoundEvents.SLIME_SQUISH, 1.0F, 1.0F);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flags) {
      tooltips.add(Component.translatable("item.cataclysm.sticky_gloves.desc").withStyle(ChatFormatting.DARK_GREEN));
   }
}
