package com.github.L_Ender.cataclysm.client.sound;

import com.github.L_Ender.cataclysm.client.tool.ControlledAnimation;
import com.github.L_Ender.cataclysm.config.CMClientConfig;
import com.github.L_Ender.cataclysm.entity.etc.Animation_Monsters;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class BossMusicSound extends AbstractTickableSoundInstance {
   private Animation_Monsters boss;
   private int ticksExisted = 0;
   private int timeUntilFade;
   private final SoundEvent soundEvent;
   ControlledAnimation volumeControl;

   public BossMusicSound(SoundEvent sound, Animation_Monsters boss) {
      super(sound, SoundSource.RECORDS, boss.getRandom());
      this.boss = boss;
      this.soundEvent = sound;
      this.attenuation = Attenuation.NONE;
      this.looping = true;
      this.delay = 0;
      this.x = boss.getX();
      this.y = boss.getY();
      this.z = boss.getZ();
      this.volumeControl = new ControlledAnimation(40);
      this.volumeControl.setTimer(20);
      this.volume = this.volumeControl.getAnimationFraction();
      this.timeUntilFade = 80;
   }

   public boolean canPlaySound() {
      return BossMusicPlayer.bossMusic == this;
   }

   public void tick() {
      if (this.boss != null && this.boss.isAlive() && !this.boss.isSilent()) {
         this.volumeControl.increaseTimer();
         this.timeUntilFade = 20;
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
         BossMusicPlayer.bossMusic = null;
      }

      this.volume = this.volumeControl.getAnimationFraction() / CMClientConfig.BossMusicVolume;
      if (this.ticksExisted % 100 == 0) {
         Minecraft.getInstance().getMusicManager().stopPlaying();
      }

      this.ticksExisted++;
   }

   public void setBoss(Animation_Monsters boss) {
      this.boss = boss;
   }

   public Animation_Monsters getBoss() {
      return this.boss;
   }

   public SoundEvent getSoundEvent() {
      return this.soundEvent;
   }
}
