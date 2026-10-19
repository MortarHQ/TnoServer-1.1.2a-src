package com.blamejared.crafttweaker.api.item.attribute;

import com.google.common.collect.Multimap;
import java.util.Collection;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public interface ItemAttributeModifierBase {
   Multimap<Attribute, AttributeModifier> getModifiers();

   Multimap<Attribute, AttributeModifier> getOriginalModifiers();

   boolean addModifier(Attribute var1, AttributeModifier var2);

   boolean removeModifier(Attribute var1, AttributeModifier var2);

   Collection<AttributeModifier> removeAttribute(Attribute var1);

   void clearModifiers();

   EquipmentSlot getSlotType();

   ItemStack getItemStack();
}
