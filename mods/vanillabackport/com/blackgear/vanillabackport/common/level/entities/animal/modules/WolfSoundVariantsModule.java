package com.blackgear.vanillabackport.common.level.entities.animal.modules;

import com.blackgear.vanillabackport.common.api.wolf.WolfSoundVariantHolder;
import com.blackgear.vanillabackport.core.VanillaBackport;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Wolf;

public class WolfSoundVariantsModule {
   public static <T extends Wolf & WolfSoundVariantHolder> SoundEvent getAmbientSound(T wolf) {
      if (!(Boolean)VanillaBackport.COMMON_CONFIG.hasWolfSoundVariants.get()) {
         return null;
      } else if (wolf.isAngry()) {
         return (SoundEvent)wolf.getSoundVariant().growlSound().value();
      } else if (wolf.getRandom().nextInt(3) != 0) {
         return (SoundEvent)wolf.getSoundVariant().ambientSound().value();
      } else {
         return wolf.isTame() && wolf.getHealth() < 20.0F
            ? (SoundEvent)wolf.getSoundVariant().whineSound().value()
            : (SoundEvent)wolf.getSoundVariant().pantSound().value();
      }
   }

   public static <T extends Wolf & WolfSoundVariantHolder> SoundEvent getHurtSound(T wolf) {
      return !VanillaBackport.COMMON_CONFIG.hasWolfSoundVariants.get() ? null : (SoundEvent)wolf.getSoundVariant().hurtSound().value();
   }

   public static <T extends Wolf & WolfSoundVariantHolder> SoundEvent getDeathSound(T wolf) {
      return !VanillaBackport.COMMON_CONFIG.hasWolfSoundVariants.get() ? null : (SoundEvent)wolf.getSoundVariant().deathSound().value();
   }
}
