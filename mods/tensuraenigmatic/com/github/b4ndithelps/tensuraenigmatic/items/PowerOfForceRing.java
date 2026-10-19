package com.github.b4ndithelps.tensuraenigmatic.items;

import com.github.b4ndithelps.tensuraenigmatic.config.TensuraEnigmaticConfig;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;

public class PowerOfForceRing extends Item implements ICurioItem {
   public PowerOfForceRing() {
      super(new Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant());
   }

   public static double getAuraRecoveryPercentage() {
      return (Double)TensuraEnigmaticConfig.POWER_OF_FORCE_AURA_RECOVERY_PERCENT.get();
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("tooltip.tensuraenigmatic.power_of_force.description").withStyle(ChatFormatting.GOLD));
      if (Screen.hasShiftDown()) {
         tooltip.add(Component.translatable("tooltip.tensuraenigmatic.power_of_force.passive_effects").withStyle(ChatFormatting.LIGHT_PURPLE));
         tooltip.add(
            Component.translatable("tooltip.tensuraenigmatic.power_of_force.aura_recovery", new Object[]{getAuraRecoveryPercentage()})
               .withStyle(ChatFormatting.DARK_PURPLE)
         );
      } else {
         tooltip.add(Component.translatable("tooltip.tensuraenigmatic.hold_shift").withStyle(ChatFormatting.GRAY));
      }
   }

   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return true;
   }

   public boolean canEquip(SlotContext slotContext, ItemStack stack) {
      return true;
   }

   public SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
      LivingEntity entity = slotContext.entity();
      return new SoundInfo((SoundEvent)SoundEvents.ARMOR_EQUIP_GOLD.value(), 1.0F, entity.getRandom().nextFloat() * 0.1F + 0.95F);
   }
}
