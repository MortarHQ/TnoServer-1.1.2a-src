package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.capabilities.EntityCapability;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/EntityCapability")
@NativeTypeRegistration(value = EntityCapability.class, zenCodeName = "crafttweaker.neoforge.api.capability.EntityCapability")
public class ExpandEntityCapability {
   @ZenCodeType.Method
   public static <T, U> @ZenCodeType.Nullable T getCapability(EntityCapability internal, Entity entity, U context) {
      return GenericUtil.uncheck(internal.getCapability(entity, context));
   }
}
