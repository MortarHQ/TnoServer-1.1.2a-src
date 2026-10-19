package com.blamejared.crafttweaker.natives.item.type.tiered;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/type/tiered/TieredItem")
@NativeTypeRegistration(value = TieredItem.class, zenCodeName = "crafttweaker.api.item.type.tiered.TieredItem")
public class ExpandTieredItem {
   @ZenCodeType.Method
   public static Tier getTier(TieredItem internal) {
      return internal.getTier();
   }
}
