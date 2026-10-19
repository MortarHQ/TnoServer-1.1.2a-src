package com.mortal_cultivation.client;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public final class SpiritForestMusicSound extends SimpleSoundInstance implements TickableSoundInstance {
   private static final int FADE_TICKS = 100;
   private int fadeProgress;
   private boolean fadingOut;
   private boolean stopped;

   public SpiritForestMusicSound(SoundEvent sound) {
      super(sound.getLocation(), SoundSource.MUSIC, 1.0F, 1.0F, SoundInstance.createUnseededRandom(), false, 0, Attenuation.NONE, 0.0, 0.0, 0.0, true);
   }

   public void setFadingOut(boolean fadingOut) {
      this.fadingOut = fadingOut;
   }

   public void tick() {
      if (!this.stopped) {
         this.fadeProgress = Math.clamp(this.fadeProgress + (this.fadingOut ? 1 : -1), 0, 100);
         float progress = this.fadeProgress / 100.0F;
         this.volume = 1.0F - progress * progress * (3.0F - 2.0F * progress);
         this.stopped = this.fadeProgress == 100;
      }
   }

   public boolean isStopped() {
      return this.stopped;
   }
}
