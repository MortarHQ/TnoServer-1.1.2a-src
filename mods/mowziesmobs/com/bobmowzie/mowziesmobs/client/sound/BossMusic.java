package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.ilexiconn.llibrary.client.model.tools.ControlledAnimation;
import net.minecraft.sounds.SoundEvent;

public class BossMusic<T extends MowzieEntity> {
   protected T boss;
   protected SoundEvent soundEvent;
   protected BossMusicSound sound;
   protected boolean isPlaying;
   protected int ticksPlaying = 0;
   protected int timeUntilFade;
   ControlledAnimation volumeControl;

   public BossMusic(SoundEvent soundEvent) {
      this.soundEvent = soundEvent;
      this.timeUntilFade = 80;
      this.volumeControl = new ControlledAnimation(40);
      this.volumeControl.setTimer(20);
   }

   public void tick() {
      if (this.boss != null && this.boss.isAlive() && !this.boss.isSilent() && !this.boss.isRemoved()) {
         this.volumeControl.increaseTimer();
         this.timeUntilFade = 60;
      } else {
         if (this.boss != null && !this.boss.isAlive()) {
            this.timeUntilFade = 0;
         }

         this.boss = null;
         if (this.timeUntilFade > 0) {
            this.timeUntilFade--;
         } else {
            this.volumeControl.decreaseTimer();
         }
      }

      if (this.volumeControl.getAnimationFraction() < 0.025) {
         this.stop();
      }

      if (this.ticksPlaying % 100 == 0) {
         MMCommon.PROXY.stopMusic();
      }

      this.ticksPlaying++;
   }

   public void play() {
      this.volumeControl.setTimer(20);
      this.isPlaying = true;
      this.ticksPlaying = 0;
      if (this.soundEvent != null) {
         this.sound = new BossMusicSound(this.soundEvent, this.getBoss(), this);
         MMCommon.PROXY.playMusic(this.sound);
      }
   }

   public void stop() {
      if (this.sound != null) {
         this.sound.doStop();
      }

      this.isPlaying = false;
      BossMusicPlayer.currentMusic = null;
      this.ticksPlaying = 0;
      this.sound = null;
   }

   public boolean isPlaying() {
      return this.isPlaying;
   }

   public T getBoss() {
      return this.boss;
   }

   public void setBoss(T boss) {
      this.boss = boss;
   }

   public SoundEvent getSoundEvent() {
      return this.soundEvent;
   }
}
