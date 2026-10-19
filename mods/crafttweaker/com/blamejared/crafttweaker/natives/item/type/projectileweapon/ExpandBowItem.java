package com.blamejared.crafttweaker.natives.item.type.projectileweapon;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.BowItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/type/projectileweapon/BowItem")
@NativeTypeRegistration(value = BowItem.class, zenCodeName = "crafttweaker.api.item.type.projectileweapon.BowItem")
public class ExpandBowItem {
   @ZenCodeType.StaticExpansionMethod
   public static float getPowerForTime(int charge) {
      return BowItem.getPowerForTime(charge);
   }
}
