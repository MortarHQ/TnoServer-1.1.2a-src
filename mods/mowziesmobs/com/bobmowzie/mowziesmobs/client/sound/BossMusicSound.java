package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.ilexiconn.llibrary.client.model.tools.ControlledAnimation;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class BossMusicSound extends AbstractTickableSoundInstance {
   private MowzieEntity boss;
   private BossMusic music;
   private final SoundEvent soundEvent;
   ControlledAnimation volumeControl;
   private boolean shouldPlay;

   public BossMusicSound(SoundEvent sound, MowzieEntity boss, BossMusic music) {
      this(sound, boss, music, true);
   }

   public BossMusicSound(SoundEvent sound, MowzieEntity boss, BossMusic music, boolean looping) {
      super(sound, SoundSource.MUSIC, SoundInstance.createUnseededRandom());
      this.soundEvent = sound;
      this.boss = boss;
      this.music = music;
      this.attenuation = Attenuation.NONE;
      this.looping = looping;
      this.delay = 0;
      this.x = boss.getX();
      this.y = boss.getY();
      this.z = boss.getZ();
      this.volumeControl = new ControlledAnimation(5);
      this.volumeControl.setTimer(3);
      this.volume = this.volumeControl.getAnimationFraction() * music.volumeControl.getAnimationFraction();
      this.shouldPlay = true;
   }

   public boolean canPlaySound() {
      return true;
   }

   public void tick() {
      if (this.shouldPlay) {
         this.volumeControl.increaseTimer();
      } else {
         this.volumeControl.decreaseTimer();
      }

      if (this.volume < 0.025) {
         this.stop();
      }

      this.volume = this.volumeControl.getAnimationFraction() * this.music.volumeControl.getAnimationFraction();
   }

   public void setBoss(MowzieEntity boss) {
      this.boss = boss;
   }

   public MowzieEntity getBoss() {
      return this.boss;
   }

   public SoundEvent getSoundEvent() {
      return this.soundEvent;
   }

   public void doStop() {
      this.stop();
   }

   public void fadeOut() {
      this.shouldPlay = false;
   }

   public void fadeIn() {
      this.shouldPlay = true;
   }

   public void cutIn() {
      this.shouldPlay = true;
      this.volumeControl.setTimer(40);
   }

   public void cutOut() {
      this.shouldPlay = false;
      this.volumeControl.setTimer(0);
   }

   public boolean canStartSilent() {
      return true;
   }
}
