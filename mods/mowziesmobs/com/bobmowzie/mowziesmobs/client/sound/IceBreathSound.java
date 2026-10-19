package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.client.model.tools.ControlledAnimation;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class IceBreathSound extends AbstractTickableSoundInstance {
   private final Entity iceBreath;
   int ticksExisted = 0;
   ControlledAnimation volumeControl;
   boolean active = true;

   public IceBreathSound(Entity icebreath) {
      super((SoundEvent)MMSounds.ENTITY_FROSTMAW_ICEBREATH.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
      this.iceBreath = icebreath;
      this.volume = 3.0F;
      this.pitch = 1.0F;
      this.x = (float)icebreath.getX();
      this.y = (float)icebreath.getY();
      this.z = (float)icebreath.getZ();
      this.volumeControl = new ControlledAnimation(10);
      this.looping = true;
   }

   public void tick() {
      if (this.active) {
         this.volumeControl.increaseTimer();
      } else {
         this.volumeControl.decreaseTimer();
      }

      this.volume = this.volumeControl.getAnimationFraction();
      if (this.volumeControl.getAnimationFraction() <= 0.05) {
         this.stop();
      }

      if (this.iceBreath != null) {
         this.active = true;
         this.x = (float)this.iceBreath.getX();
         this.y = (float)this.iceBreath.getY();
         this.z = (float)this.iceBreath.getZ();
         if (!this.iceBreath.isAlive()) {
            this.active = false;
         }
      } else {
         this.active = false;
      }

      this.ticksExisted++;
   }
}
