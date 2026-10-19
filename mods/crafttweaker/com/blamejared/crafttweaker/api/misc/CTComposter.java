package com.blamejared.crafttweaker.api.misc;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.misc.ActionSetCompostable;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.misc.Composter")
@Document("vanilla/api/misc/Composter")
public final class CTComposter {
   @ZenCodeGlobals.Global("composter")
   public static final CTComposter INSTANCE = new CTComposter();

   private CTComposter() {
   }

   @ZenCodeType.Method
   public void setValue(IItemStack stack, float amount) {
      CraftTweakerAPI.apply(new ActionSetCompostable(stack, amount));
   }
}
