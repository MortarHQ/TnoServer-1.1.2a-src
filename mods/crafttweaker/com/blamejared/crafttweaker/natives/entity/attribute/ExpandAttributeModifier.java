package com.blamejared.crafttweaker.natives.entity.attribute;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/attribute/AttributeModifier")
@NativeTypeRegistration(value = AttributeModifier.class, zenCodeName = "crafttweaker.api.entity.attribute.AttributeModifier")
public class ExpandAttributeModifier {
   @ZenCodeType.StaticExpansionMethod
   public static AttributeModifier create(ResourceLocation id, double amount, Operation operation) {
      return new AttributeModifier(id, amount, operation);
   }

   @ZenCodeType.Getter("id")
   public static ResourceLocation id(AttributeModifier internal) {
      return internal.id();
   }

   @ZenCodeType.Getter("amount")
   public static double amount(AttributeModifier internal) {
      return internal.amount();
   }

   @ZenCodeType.Getter("operation")
   public static Operation operation(AttributeModifier internal) {
      return internal.operation();
   }
}
