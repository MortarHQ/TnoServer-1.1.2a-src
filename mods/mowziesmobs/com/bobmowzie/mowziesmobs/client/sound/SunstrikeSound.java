package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SunstrikeSound extends AbstractTickableSoundInstance {
   private final EntitySunstrike sunstrike;

   public SunstrikeSound(EntitySunstrike sunstrike) {
      super((SoundEvent)MMSounds.SUNSTRIKE.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
      this.sunstrike = sunstrike;
      this.volume = 1.5F;
      this.pitch = 1.1F;
      this.x = (float)sunstrike.getX();
      this.y = (float)sunstrike.getY();
      this.z = (float)sunstrike.getZ();
   }

   public void tick() {
      if (!this.sunstrike.isAlive()) {
         this.stop();
      }
   }
}
