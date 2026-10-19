package com.blamejared.crafttweaker.natives.entity.equipment;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Locale;
import net.minecraft.world.entity.EquipmentSlot.Type;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/equipment/EquipmentSlotType")
@NativeTypeRegistration(value = Type.class, zenCodeName = "crafttweaker.api.entity.equipment.EquipmentSlotType")
@BracketEnum("minecraft:equipmentslot/type")
public class ExpandEquipmentSlotType {
   @ZenCodeType.Method
   @ZenCodeType.Getter("commandString")
   public static String getCommandString(Type internal) {
      return "<constant:minecraft:equipmentslot/type:" + internal.name().toLowerCase(Locale.ROOT) + ">";
   }
}
