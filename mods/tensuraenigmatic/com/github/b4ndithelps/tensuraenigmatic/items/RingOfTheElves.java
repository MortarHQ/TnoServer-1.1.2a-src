package com.github.b4ndithelps.tensuraenigmatic.items;

import com.github.b4ndithelps.tensuraenigmatic.TensuraEnigmatic;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
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

public class RingOfTheElves extends Item implements ICurioItem {
   private static final ResourceLocation ARMOR_MODIFIER_ID = TensuraEnigmatic.location("ring_of_the_elves_armor_bonus");
   private static final ResourceLocation HEALTH_MODIFIER_ID = TensuraEnigmatic.location("ring_of_the_elves_health_bonus");

   public RingOfTheElves() {
      super(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
   }

   public static double getArmorBonus() {
      return ((Integer)TensuraEnigmaticConfig.RING_OF_THE_ELVES_ARMOR_BONUS.get()).intValue();
   }

   public static double getHealthBonus() {
      return ((Integer)TensuraEnigmaticConfig.RING_OF_THE_ELVES_HEALTH_BONUS.get()).intValue();
   }

   public static int getMagiculeRecovery() {
      return (Integer)TensuraEnigmaticConfig.RING_OF_THE_ELVES_MAGICULE_BONUS.get();
   }

   public static int getProtectionLevel() {
      return (Integer)TensuraEnigmaticConfig.RING_OF_THE_ELVES_PROTECTION_LEVEL.get();
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.empty());
      if (Screen.hasShiftDown()) {
         tooltip.add(Component.translatable("tooltip.tensuraenigmatic.ring_of_the_elves.passive_effects").withStyle(ChatFormatting.LIGHT_PURPLE));
         tooltip.add(
            Component.translatable("tooltip.tensuraenigmatic.ring_of_the_elves.armor_bonus", new Object[]{(int)getArmorBonus()})
               .withStyle(ChatFormatting.DARK_PURPLE)
         );
         tooltip.add(
            Component.translatable("tooltip.tensuraenigmatic.ring_of_the_elves.health_bonus", new Object[]{(int)getHealthBonus()})
               .withStyle(ChatFormatting.DARK_PURPLE)
         );
         tooltip.add(
            Component.translatable("tooltip.tensuraenigmatic.ring_of_the_elves.magicule_recovery", new Object[]{getMagiculeRecovery()})
               .withStyle(ChatFormatting.DARK_PURPLE)
         );
         tooltip.add(Component.translatable("tooltip.tensuraenigmatic.ring_of_the_elves.protection_effect").withStyle(ChatFormatting.DARK_PURPLE));
         tooltip.add(Component.empty());
      } else {
         tooltip.add(Component.translatable("tooltip.tensuraenigmatic.ring_of_the_elves.description").withStyle(ChatFormatting.GOLD));
         tooltip.add(Component.empty());
         tooltip.add(Component.translatable("tooltip.tensuraenigmatic.hold_shift").withStyle(ChatFormatting.GRAY));
         tooltip.add(Component.empty());
      }
   }

   public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext context, ResourceLocation id, ItemStack stack) {
      Multimap<Holder<Attribute>, AttributeModifier> attributes = HashMultimap.create();
      attributes.put(Attributes.ARMOR, new AttributeModifier(ARMOR_MODIFIER_ID, getArmorBonus(), Operation.ADD_VALUE));
      attributes.put(Attributes.MAX_HEALTH, new AttributeModifier(HEALTH_MODIFIER_ID, getHealthBonus(), Operation.ADD_VALUE));
      return attributes;
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      tooltips.clear();
      return tooltips;
   }

   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return this.canEquip(slotContext, stack);
   }

   public boolean canEquip(SlotContext slotContext, ItemStack stack) {
      return true;
   }

   public SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
      LivingEntity entity = slotContext.entity();
      return new SoundInfo((SoundEvent)SoundEvents.ARMOR_EQUIP_GOLD.value(), 1.0F, entity.getRandom().nextFloat() * 0.1F + 0.95F);
   }
}
