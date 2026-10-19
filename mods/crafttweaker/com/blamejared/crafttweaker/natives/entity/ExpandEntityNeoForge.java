package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.capabilities.EntityCapability;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.entity.Entity")
public class ExpandEntityNeoForge {
   @ZenCodeType.Method
   public static <T, C> @ZenCodeType.Nullable T getCapabilityWithContext(
      Entity internal, Class<T> tClass, Class<C> cClass, EntityCapability<T, C> cap, @ZenCodeType.Nullable C context
   ) {
      return (T)internal.getCapability(cap, context);
   }

   @ZenCodeType.Method
   public static <T> @ZenCodeType.Nullable T getCapability(Entity internal, Class<T> tClass, EntityCapability<T, Void> cap) {
      return (T)internal.getCapability(cap);
   }
}
