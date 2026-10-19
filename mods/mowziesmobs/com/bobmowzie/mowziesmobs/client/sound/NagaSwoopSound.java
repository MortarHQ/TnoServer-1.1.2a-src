package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class NagaSwoopSound extends AbstractTickableSoundInstance {
   private final Entity naga;
   int ticksExisted = 0;
   boolean active = true;

   public NagaSwoopSound(Entity naga) {
      super((SoundEvent)MMSounds.ENTITY_NAGA_SWOOP.get(), SoundSource.HOSTILE, SoundInstance.createUnseededRandom());
      this.naga = naga;
      this.volume = 2.0F;
      this.pitch = 1.2F;
      this.x = (float)naga.getX();
      this.y = (float)naga.getY();
      this.z = (float)naga.getZ();
      this.looping = false;
   }

   public void tick() {
      if (this.naga != null) {
         this.active = true;
         this.x = (float)this.naga.getX();
         this.y = (float)this.naga.getY();
         this.z = (float)this.naga.getZ();
         if (!this.naga.isAlive()) {
            this.active = false;
            this.stop();
         }
      }

      this.ticksExisted++;
   }
}
