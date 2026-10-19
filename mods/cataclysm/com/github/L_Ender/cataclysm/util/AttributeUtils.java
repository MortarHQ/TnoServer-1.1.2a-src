package com.github.L_Ender.cataclysm.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentPatch.Builder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;

public class AttributeUtils {
   public static void mergeAttributes(Builder builder, Item item, ItemAttributeModifiers newModifiers) {
      ItemAttributeModifiers existingModifiers = (ItemAttributeModifiers)item.components()
         .getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
      net.minecraft.world.item.component.ItemAttributeModifiers.Builder combinedBuilder = ItemAttributeModifiers.builder();

      for (Entry existingEntry : existingModifiers.modifiers()) {
         boolean shouldReplace = false;

         for (Entry newEntry : newModifiers.modifiers()) {
            if (existingEntry.attribute().equals(newEntry.attribute()) && existingEntry.slot().equals(newEntry.slot())) {
               shouldReplace = true;
               break;
            }
         }

         if (!shouldReplace) {
            combinedBuilder.add(existingEntry.attribute(), existingEntry.modifier(), existingEntry.slot());
         }
      }

      for (Entry newEntry : newModifiers.modifiers()) {
         combinedBuilder.add(newEntry.attribute(), newEntry.modifier(), newEntry.slot());
      }

      builder.set(DataComponents.ATTRIBUTE_MODIFIERS, combinedBuilder.build());
   }

   public static float OriginDamage(LivingEntity living, ItemStack itemStack) {
      double totalDamage = living.getAttributeValue(Attributes.ATTACK_DAMAGE);
      if (living.getMainHandItem() == itemStack) {
         return (float)totalDamage;
      }

      ItemStack mainHandStack = living.getMainHandItem();
      if (!mainHandStack.isEmpty()) {
         ItemAttributeModifiers modifiers = (ItemAttributeModifiers)mainHandStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);

         for (Entry entry : modifiers.modifiers()) {
            if (entry.attribute().is(Attributes.ATTACK_DAMAGE)
               && entry.slot().test(EquipmentSlot.MAINHAND)
               && entry.modifier().operation() == Operation.ADD_VALUE) {
               totalDamage -= entry.modifier().amount();
            }
         }
      }

      ItemAttributeModifiers shredderModifiers = (ItemAttributeModifiers)itemStack.getOrDefault(
         DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY
      );

      for (Entry entry : shredderModifiers.modifiers()) {
         if (entry.attribute().is(Attributes.ATTACK_DAMAGE) && entry.slot().test(EquipmentSlot.MAINHAND) && entry.modifier().operation() == Operation.ADD_VALUE
            )
          {
            totalDamage += entry.modifier().amount();
         }
      }

      return (float)totalDamage;
   }
}
