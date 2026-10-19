package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.client.model.tools.ControlledAnimation;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public class SunblockSound extends AbstractTickableSoundInstance {
   private final LivingEntity entity;
   int ticksExisted = 0;
   ControlledAnimation volumeControl;
   boolean active = true;

   public SunblockSound(LivingEntity entity) {
      super((SoundEvent)MMSounds.ENTITY_UMVUTHANA_HEAL_LOOP.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
      this.entity = entity;
      this.volume = 4.0F;
      this.pitch = 1.0F;
      this.x = (float)entity.getX();
      this.y = (float)entity.getY();
      this.z = (float)entity.getZ();
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

      if (this.entity != null) {
         this.active = true;
         this.x = (float)this.entity.getX();
         this.y = (float)this.entity.getY();
         this.z = (float)this.entity.getZ();
         boolean umvuthanaHealing = false;
         if (this.entity instanceof EntityUmvuthana umvuthana) {
            umvuthanaHealing = umvuthana.getActiveAbilityType() == EntityUmvuthana.HEAL_ABILITY;
         }

         boolean hasSunblock = this.entity.hasEffect(EffectHandler.SUNBLOCK);
         this.active = umvuthanaHealing || hasSunblock;
         if (!this.entity.isAlive()) {
            this.active = false;
         }
      } else {
         this.active = false;
      }

      this.ticksExisted++;
   }
}
