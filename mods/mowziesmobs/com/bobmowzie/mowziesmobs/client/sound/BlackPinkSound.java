package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

public final class BlackPinkSound extends AbstractTickableSoundInstance {
   private final AbstractMinecart minecart;

   public BlackPinkSound(AbstractMinecart minecart) {
      super((SoundEvent)MMSounds.MUSIC_BLACK_PINK.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
      this.minecart = minecart;
   }

   public void tick() {
      if (this.minecart.isAlive()) {
         this.x = (float)this.minecart.getX();
         this.y = (float)this.minecart.getY();
         this.z = (float)this.minecart.getZ();
      } else {
         this.stop();
      }
   }
}
