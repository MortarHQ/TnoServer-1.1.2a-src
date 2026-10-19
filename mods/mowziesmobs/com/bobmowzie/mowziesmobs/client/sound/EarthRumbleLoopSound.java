package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.client.model.tools.ControlledAnimation;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class EarthRumbleLoopSound extends AbstractTickableSoundInstance {
   private final IGeomancyRumbler rumbler;
   int ticksExisted = 0;
   ControlledAnimation volumeControl;
   boolean active = false;

   public EarthRumbleLoopSound(IGeomancyRumbler rumbler) {
      super((SoundEvent)MMSounds.EFFECT_GEOMANCY_RUMBLE_LOOP.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
      this.rumbler = rumbler;
      this.volume = 0.0F;
      this.pitch = 1.0F;
      this.x = rumbler.getRumblerX();
      this.y = rumbler.getRumblerY();
      this.z = rumbler.getRumblerZ();
      this.volumeControl = new ControlledAnimation(10);
      this.volumeControl.setTimer(0);
      this.looping = true;
   }

   public void tick() {
      if (this.active) {
         this.volumeControl.increaseTimer();
      } else {
         this.volumeControl.decreaseTimer();
      }

      this.volume = this.volumeControl.getAnimationFraction() * 3.0F * this.rumbler.getRumbleVolume();
      this.pitch = this.rumbler.getRumblePitch();
      if (this.volumeControl.getAnimationFraction() <= 0.05 && (this.rumbler == null || this.rumbler.isFinishedRumbling())) {
         this.stop();
      }

      if (this.rumbler != null && this.rumbler.isRumbling()) {
         this.active = true;
         this.x = this.rumbler.getRumblerX();
         this.y = this.rumbler.getRumblerY();
         this.z = this.rumbler.getRumblerZ();
         if (!this.rumbler.isRumbling()) {
            this.active = false;
         }
      } else {
         this.active = false;
      }

      this.ticksExisted++;
   }

   public boolean canStartSilent() {
      return true;
   }
}
