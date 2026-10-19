package com.benji.netherman.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;

public class ZoneAmbientSoundInstance extends AbstractTickableSoundInstance {
   private final Player player;
   private final Holder<MobEffect> requiredEffect;

   public ZoneAmbientSoundInstance(SoundEvent soundEvent, Player player, Holder<MobEffect> requiredEffect, boolean isLooping) {
      super(soundEvent, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
      this.player = player;
      this.requiredEffect = requiredEffect;
      this.looping = isLooping;
      this.delay = 0;
      this.volume = 1.0F;
      this.pitch = 1.0F;
      this.relative = true;
      this.attenuation = Attenuation.NONE;
   }

   public void tick() {
      if (this.player.isRemoved() || !this.player.isAlive() || !this.player.hasEffect(this.requiredEffect)) {
         this.stop();
      }
   }
}
