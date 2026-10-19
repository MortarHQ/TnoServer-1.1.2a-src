package com.finderfeed.fdlib.systems.music;

import com.finderfeed.fdlib.FDClientHelpers;
import com.finderfeed.fdlib.systems.music.data.FDMusicData;
import com.finderfeed.fdlib.systems.music.data.FDMusicPartData;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;

public class FDMusic {
   private FDMusicData fdMusicData;
   private List<FDMusicPart> musicParts = new ArrayList<>();
   private int currentlyTickingFrom;
   private int currentlyTickingTo;
   private boolean finishedPlaying = false;
   private int inactiveTime = 0;
   private int currentFadeTime = -1;
   private int fadeInTicker = -1;
   private int fadeOutTicker = -1;
   private float oldVolume = 1.0F;
   private float currentVolume = 1.0F;
   private float volumeStamp = 1.0F;
   private boolean finishOnFadeOut;

   public FDMusic(FDMusicData fdMusicData) {
      this.fdMusicData = fdMusicData;

      for (FDMusicPartData musicPartData : fdMusicData.getMusicPartDatas()) {
         this.musicParts.add(new FDMusicPart(musicPartData, this));
      }

      this.currentlyTickingFrom = fdMusicData.getStartFrom();
      this.currentlyTickingTo = this.currentlyTickingFrom + 1;
      if (fdMusicData.getStartFadeInTime() != 0) {
         this.volumeStamp = 0.0F;
         this.oldVolume = 0.0F;
         this.currentVolume = 0.0F;
         this.fadeIn(fdMusicData.getStartFadeInTime());
      }
   }

   public void tick() {
      for (int i = this.currentlyTickingFrom; i < this.currentlyTickingTo; i++) {
         FDMusicPart fdMusicPart = this.musicParts.get(i);
         fdMusicPart.tick();
      }

      if (this.currentVolume == 0.0F && this.oldVolume == 0.0F) {
         this.inactiveTime++;
      } else {
         this.inactiveTime = 0;
      }

      if (this.inactiveTime >= this.fdMusicData.getInactiveDeleteTime()) {
         this.finishedPlaying = true;
      }

      this.tickFades();
   }

   public void renderTick(float pticks) {
      for (int i = this.currentlyTickingFrom; i < this.currentlyTickingTo; i++) {
         FDMusicPart fdMusicPart = this.musicParts.get(i);
         fdMusicPart.setAllSoundInstancesVolume(this.getVolume(pticks) * FDClientHelpers.getCurrentMusicVolume());
         fdMusicPart.renderTick(pticks);
         if (!this.finishOnFadeOut && i == this.currentlyTickingTo - 1 && fdMusicPart.hasFinished()) {
            if (this.currentlyTickingTo < this.musicParts.size()) {
               this.currentlyTickingTo++;
            } else {
               this.finishedPlaying = true;
            }
         }
      }
   }

   private void tickFades() {
      this.oldVolume = this.currentVolume;
      if (this.fadeInTicker != -1) {
         float p = (float)this.fadeInTicker / this.currentFadeTime;
         this.currentVolume = FDMathUtil.lerp(this.volumeStamp, 1.0F, 1.0F - p);
         this.fadeInTicker--;
      } else if (this.fadeOutTicker != -1) {
         float p = (float)this.fadeOutTicker / this.currentFadeTime;
         this.currentVolume = FDMathUtil.lerp(this.volumeStamp, 0.0F, 1.0F - p);
         this.fadeOutTicker--;
         if (this.fadeOutTicker == -1 && this.finishOnFadeOut) {
            this.finishedPlaying = true;
         }
      }
   }

   public void triggerMusicEnd(int fadeOutTime) {
      if (fadeOutTime > 0) {
         this.fadeOut(fadeOutTime, true);
      } else {
         this.immediateShutdown();
      }

      this.finishOnFadeOut = true;
   }

   public boolean hasFinishedPlaying() {
      return this.finishedPlaying;
   }

   public void fadeOut(int fadeOutTime, boolean finishOnFadeOut) {
      this.volumeStamp = this.currentVolume;
      this.fadeOutTicker = fadeOutTime;
      this.fadeInTicker = -1;
      this.currentFadeTime = fadeOutTime;
      this.finishOnFadeOut = finishOnFadeOut;
   }

   public void fadeIn(int fadeInTime) {
      if (!this.finishOnFadeOut) {
         this.volumeStamp = this.currentVolume;
         this.fadeOutTicker = -1;
         this.fadeInTicker = fadeInTime;
         this.currentFadeTime = fadeInTime;
      }
   }

   public List<SoundInstance> allSoundInstances() {
      List<SoundInstance> soundInstances = new ArrayList<>();

      for (FDMusicPart musicPart : this.musicParts) {
         soundInstances.addAll(musicPart.getSoundInstances());
      }

      return soundInstances;
   }

   public void immediateShutdown() {
      for (SoundInstance soundInstance : this.allSoundInstances()) {
         FDClientHelpers.getSoundManager().stop(soundInstance);
      }

      this.finishedPlaying = true;
   }

   public FDMusicData getFdMusicData() {
      return this.fdMusicData;
   }

   public float getCurrentVolume() {
      return this.currentVolume;
   }

   public float getVolume(float pticks) {
      return FDMathUtil.lerp(this.oldVolume, this.currentVolume, pticks);
   }

   public void onFinishPlaying() {
      for (SoundInstance instance : this.allSoundInstances()) {
         SoundManager soundManager = FDClientHelpers.getSoundManager();
         soundManager.stop(instance);
      }
   }
}
