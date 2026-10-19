package com.blamejared.crafttweaker.natives.entity.attribute;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/attribute/AttributeOperation")
@NativeTypeRegistration(value = Operation.class, zenCodeName = "crafttweaker.api.entity.attribute.AttributeOperation")
@BracketEnum("minecraft:attribute/operation")
public class ExpandAttributeOperation {
   @ZenCodeType.Getter("id")
   public static int id(Operation internal) {
      return internal.id();
   }
}
