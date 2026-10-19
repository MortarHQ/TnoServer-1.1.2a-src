package com.blamejared.crafttweaker.natives.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/component/DataComponentType")
@NativeTypeRegistration(value = DataComponentType.class, zenCodeName = "crafttweaker.api.component.DataComponentType")
public class ExpandDataComponentType {
   @ZenCodeType.Getter("isTransient")
   public static boolean isTransient(DataComponentType internal) {
      return internal.isTransient();
   }

   @ZenCodeType.Getter("registryName")
   public static ResourceLocation getRegistryName(DataComponentType internal) {
      return Services.REGISTRY.keyOrThrow(Registries.DATA_COMPONENT_TYPE, internal);
   }

   @ZenCodeType.Getter("commandString")
   public static String getCommandString(DataComponentType internal) {
      return "<componenttype:" + getRegistryName(internal) + ">";
   }
}
