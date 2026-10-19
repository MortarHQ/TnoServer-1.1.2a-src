package com.blamejared.crafttweaker.natives.item.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.world.item.component.ResolvableProfile;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/component/ResolvableProfile")
@NativeTypeRegistration(value = ResolvableProfile.class, zenCodeName = "crafttweaker.api.item.component.ResolvableProfile")
public class ExpandResolvableProfile {
   @ZenCodeType.Getter("id")
   public static @ZenCodeType.Nullable UUID id(ResolvableProfile internal) {
      return (UUID)internal.id().orElse(null);
   }

   @ZenCodeType.Getter("isResolved")
   public static boolean isResolved(ResolvableProfile internal) {
      return internal.isResolved();
   }

   @ZenCodeType.Getter("name")
   public static @ZenCodeType.Nullable String name(ResolvableProfile internal) {
      return (String)internal.name().orElse(null);
   }

   @ZenCodeType.Getter("gameProfile")
   public static GameProfile gameProfile(ResolvableProfile internal) {
      return internal.gameProfile();
   }
}
