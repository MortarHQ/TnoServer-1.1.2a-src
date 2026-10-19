package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.entity.ActionSetNameTag;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.entity.INameTagFunction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.entity.EntityType<crafttweaker.api.entity.Entity>")
public class ExpandEntityTypeNeoForge {
   @ZenCodeType.Method
   public static void setNameTag(EntityType<Entity> internal, INameTagFunction function) {
      CraftTweakerAPI.apply(new ActionSetNameTag(entity -> entity.getType().equals(internal), function));
   }
}
