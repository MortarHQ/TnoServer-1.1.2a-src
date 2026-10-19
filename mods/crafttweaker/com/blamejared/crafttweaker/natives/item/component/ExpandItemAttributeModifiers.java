package com.blamejared.crafttweaker.natives.item.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/component/ItemAttributeModifiers")
@NativeTypeRegistration(value = ItemAttributeModifiers.class, zenCodeName = "crafttweaker.api.item.component.ItemAttributeModifiers")
public class ExpandItemAttributeModifiers {
   @ZenCodeType.StaticExpansionMethod
   public static ItemAttributeModifiers of(List<Entry> modifiers, boolean showInTooltip) {
      return new ItemAttributeModifiers(modifiers, showInTooltip);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Builder builder() {
      return ItemAttributeModifiers.builder();
   }

   @ZenCodeType.Method
   public static ItemAttributeModifiers withTooltip(ItemAttributeModifiers internal, boolean showInTooltip) {
      return internal.withTooltip(showInTooltip);
   }

   @ZenCodeType.Method
   public static void forEach(ItemAttributeModifiers internal, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
      internal.forEach(slot, (attributeHolder, attributeModifier) -> consumer.accept((Attribute)attributeHolder.value(), attributeModifier));
   }

   @ZenCodeType.Method
   public static ItemAttributeModifiers withModifierAdded(
      ItemAttributeModifiers internal, Attribute attribute, AttributeModifier modifier, EquipmentSlotGroup slot
   ) {
      return internal.withModifierAdded(Services.REGISTRY.holderOrThrow(Registries.ATTRIBUTE, attribute), modifier, slot);
   }

   @ZenCodeType.Getter("modifiers")
   public static List<Entry> modifiers(ItemAttributeModifiers internal) {
      return internal.modifiers();
   }

   @ZenCodeType.Method
   public static double compute(ItemAttributeModifiers internal, double baseValue, EquipmentSlot slot) {
      return internal.compute(baseValue, slot);
   }

   @ZenCodeType.Getter("showInTooltip")
   public static boolean showInTooltip(ItemAttributeModifiers internal) {
      return internal.showInTooltip();
   }

   @ZenRegister
   @Document("vanilla/api/item/component/ItemAttributeModifiersBuilder")
   @NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.item.component.ItemAttributeModifiersBuilder")
   public static class ExpandItemAttributeModifiersBuilder {
      @ZenCodeType.Method
      public static Builder add(Builder internal, Attribute attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
         return internal.add(Services.REGISTRY.holderOrThrow(Registries.ATTRIBUTE, attribute), modifier, slot);
      }

      @ZenCodeType.Method
      public static ItemAttributeModifiers build(Builder internal) {
         return internal.build();
      }
   }

   @ZenRegister
   @Document("vanilla/api/item/component/ItemAttributeModifiersEntry")
   @NativeTypeRegistration(value = Entry.class, zenCodeName = "crafttweaker.api.item.component.ItemAttributeModifiersEntry")
   public static class ExpandItemAttributeModifiersEntry {
      @ZenCodeType.StaticExpansionMethod
      public static Entry of(Attribute attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
         return new Entry(Services.REGISTRY.holderOrThrow(Registries.ATTRIBUTE, attribute), modifier, slot);
      }

      @ZenCodeType.Getter("attribute")
      public static Attribute attribute(Entry internal) {
         return (Attribute)internal.attribute().value();
      }

      @ZenCodeType.Getter("modifier")
      public static AttributeModifier modifier(Entry internal) {
         return internal.modifier();
      }

      @ZenCodeType.Getter("slot")
      public static EquipmentSlotGroup slot(Entry internal) {
         return internal.slot();
      }
   }
}
