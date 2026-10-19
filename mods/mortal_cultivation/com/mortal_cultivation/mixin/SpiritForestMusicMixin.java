package com.mortal_cultivation.mixin;

import com.mortal_cultivation.client.SpiritForestMusicSound;
import com.mortal_cultivation.registry.SoundRegistry;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MusicManager.class)
public abstract class SpiritForestMusicMixin {
   @Redirect(
      method = "startPlaying",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;forMusic(Lnet/minecraft/sounds/SoundEvent;)Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;"
      ),
      remap = false
   )
   private SimpleSoundInstance mortalCultivation$createMusic(SoundEvent sound) {
      return sound.getLocation().equals(SoundRegistry.SPIRIT_FOREST_MUSIC.getId()) ? new SpiritForestMusicSound(sound) : SimpleSoundInstance.forMusic(sound);
   }
}
