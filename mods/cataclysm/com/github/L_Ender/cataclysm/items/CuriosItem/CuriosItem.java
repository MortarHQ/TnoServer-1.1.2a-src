package com.github.L_Ender.cataclysm.items.CuriosItem;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.common.NeoForge;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosItem extends Item implements ICurioItem {
   String attributeSlot = "";
   Function<Integer, Multimap<Holder<Attribute>, AttributeModifier>> attributes = null;
   Map<String, Integer> slotModifiers = new HashMap<>();

   public CuriosItem(Properties properties) {
      super(properties);
   }

   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return true;
   }

   public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
      Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();
      if (slotContext.identifier().equals(this.attributeSlot) && this.attributes != null) {
         modifiers.putAll(this.attributes.apply(slotContext.index()));
      }

      if (!this.slotModifiers.isEmpty()) {
         this.slotModifiers.forEach((slotId, amount) -> CuriosApi.addSlotModifier(modifiers, slotId, id, amount.intValue(), Operation.ADD_VALUE));
      }

      CurioAttributeModifierEvent evt = new CurioAttributeModifierEvent(stack, slotContext, id, modifiers);
      NeoForge.EVENT_BUS.post(evt);
      return modifiers;
   }

   public CuriosItem withAttributes(String slot, AttributeContainer... attributes) {
      this.attributeSlot = slot;
      this.attributes = index -> {
         Builder<Holder<Attribute>, AttributeModifier> builder = ImmutableMultimap.builder();

         for (AttributeContainer holder : attributes) {
            String name = String.format("%s_%s", this.attributeSlot, index);
            builder.put(holder.attribute(), holder.createModifier(name));
         }

         return builder.build();
      };
      return this;
   }

   public CuriosItem withSlotModifier(String slotToModify, int amount) {
      this.slotModifiers.put(slotToModify, amount);
      return this;
   }
}
