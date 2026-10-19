package com.github.b4ndithelps.tensuraenigmatic.items;

import com.github.b4ndithelps.tensuraenigmatic.config.TensuraEnigmaticConfig;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

public class RingOfArcaneSource extends Item implements ICurioItem {
   public RingOfArcaneSource() {
      super(new Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant());
   }

   public static int getMagiculeRecovery() {
      return (Integer)TensuraEnigmaticConfig.RING_OF_ARCANE_SOURCE_MAGICULE_BONUS.get();
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("tooltip.tensuraenigmatic.ring_of_arcane_source.description").withStyle(ChatFormatting.GOLD));
      if (Screen.hasShiftDown()) {
         tooltip.add(Component.translatable("tooltip.tensuraenigmatic.ring_of_arcane_source.passive_effects").withStyle(ChatFormatting.LIGHT_PURPLE));
         tooltip.add(
            Component.translatable("tooltip.tensuraenigmatic.ring_of_arcane_source.magicule_recovery", new Object[]{getMagiculeRecovery()})
               .withStyle(ChatFormatting.DARK_PURPLE)
         );
      } else {
         tooltip.add(Component.translatable("tooltip.tensuraenigmatic.hold_shift").withStyle(ChatFormatting.GRAY));
      }
   }

   public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext context, ResourceLocation id, ItemStack stack) {
      return HashMultimap.create();
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      tooltips.clear();
      return tooltips;
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
