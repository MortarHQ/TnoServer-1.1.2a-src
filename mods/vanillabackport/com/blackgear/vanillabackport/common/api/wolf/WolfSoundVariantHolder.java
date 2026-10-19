package com.blackgear.vanillabackport.common.api.wolf;

import net.minecraft.world.entity.animal.Wolf;

public interface WolfSoundVariantHolder {
   static WolfSoundVariantHolder of(Wolf wolf) {
      return wolf instanceof WolfSoundVariantHolder holder ? holder : null;
   }

   WolfSoundVariant getSoundVariant();

   void setSoundVariant(WolfSoundVariant var1);
}
