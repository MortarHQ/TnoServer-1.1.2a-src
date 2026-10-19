package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SolarBeamSound extends AbstractTickableSoundInstance {
   private final EntitySolarBeam solarBeam;
   boolean endLocation = false;

   public SolarBeamSound(EntitySolarBeam solarBeam, boolean endLocation) {
      super((SoundEvent)MMSounds.LASER.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
      this.solarBeam = solarBeam;
      this.volume = 2.0F;
      this.pitch = 1.0F;
      this.endLocation = endLocation;
   }

   public void tick() {
      if (!this.solarBeam.isAlive()) {
         this.stop();
      }

      if (this.endLocation && this.solarBeam.hasDoneRaytrace()) {
         this.x = (float)this.solarBeam.collidePosX;
         this.y = (float)this.solarBeam.collidePosY;
         this.z = (float)this.solarBeam.collidePosZ;
      } else {
         this.x = (float)this.solarBeam.getX();
         this.y = (float)this.solarBeam.getY();
         this.z = (float)this.solarBeam.getZ();
      }
   }
}
