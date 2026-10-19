package com.github.L_Ender.cataclysm.items.CuriosItem;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public record AttributeContainer(Holder<Attribute> attribute, double value, Operation operation) {
   public AttributeModifier createModifier(String slot) {
      String attributeName = ResourceLocation.parse(this.attribute.getRegisteredName()).getPath();
      return new AttributeModifier(
         ResourceLocation.fromNamespaceAndPath("cataclysm", String.format("%s_%s_modifier", slot, attributeName)), this.value, this.operation
      );
   }
}
