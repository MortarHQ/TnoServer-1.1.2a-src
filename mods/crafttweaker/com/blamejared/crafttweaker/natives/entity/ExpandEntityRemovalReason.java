package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity.RemovalReason;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/RemovalReason")
@NativeTypeRegistration(value = RemovalReason.class, zenCodeName = "crafttweaker.api.entity.RemovalReason")
@BracketEnum("minecraft:entity/removalreason")
public class ExpandEntityRemovalReason {
   @ZenCodeType.Method
   @ZenCodeType.Getter("shouldDestroy")
   public static boolean shouldDestroy(RemovalReason internal) {
      return internal.shouldDestroy();
   }
}
