package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;

final class EnumBracketParserRegistrationManager {
   void attemptRegistration(Class<?> clazz, String loader, IBracketParserRegistrationHandler handler) {
      if (clazz.isEnum()) {
         this.tryEnumRegistration(clazz, loader, handler);
      }

      if (clazz.isAnnotationPresent(NativeTypeRegistration.class)) {
         this.tryNativeRegistration(clazz, loader, handler);
      }
   }

   private void tryEnumRegistration(Class<?> clazz, String loader, IBracketParserRegistrationHandler handler) {
      if (clazz.isAnnotationPresent(BracketEnum.class)) {
         this.tryRegistration(clazz, loader, clazz.getAnnotation(BracketEnum.class).value(), handler);
      }
   }

   private void tryNativeRegistration(Class<?> clazz, String loader, IBracketParserRegistrationHandler handler) {
      if (clazz.isAnnotationPresent(BracketEnum.class)) {
         NativeTypeRegistration ntr = clazz.getAnnotation(NativeTypeRegistration.class);
         Class<?> nativeType = ntr.value();
         if (nativeType.isEnum()) {
            this.tryRegistration(nativeType, loader, clazz.getAnnotation(BracketEnum.class).value(), handler);
         }
      }
   }

   private void tryRegistration(Class<?> clazz, String loader, String value, IBracketParserRegistrationHandler handler) {
      ResourceLocation id;
      try {
         id = ResourceLocation.parse(value);
      } catch (ResourceLocationException e) {
         throw new IllegalArgumentException("Provided resource location '" + value + "' for enum " + clazz.getName() + " is invalid", e);
      }

      handler.registerEnumForBracket(loader, id, GenericUtil.uncheck(clazz));
   }
}
