package com.finderfeed.fdlib.systems.music;

import com.finderfeed.fdlib.FDClientHelpers;
import com.finderfeed.fdlib.systems.music.data.FDMusicPartData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.ChannelAccess.ChannelHandle;
import org.lwjgl.openal.AL11;

public class FDMusicPart {
   private FDMusicPartData data;
   private List<SoundInstance> oldSoundInstances = new ArrayList<>();
   private SoundInstance main;
   private FDMusic owner;
   private boolean finishedPlaying = false;

   public FDMusicPart(FDMusicPartData data, FDMusic owner) {
      this.data = data;
      this.owner = owner;
   }

   public void tick() {
      this.deleteEndedSoundInstances();
   }

   public void renderTick(float pticks) {
      this.manageLoopOrEndSound(pticks);
   }

   private void manageLoopOrEndSound(float pticks) {
      if (!this.finishedPlaying) {
         if (this.main == null) {
            this.startNewSound(pticks);
         }

         Map<SoundInstance, ChannelHandle> map = FDClientHelpers.getSoundEngine().instanceToChannel;
         if (!map.containsKey(this.main) && FDClientHelpers.getCurrentMusicVolume() > 0.0F && !Minecraft.getInstance().isPaused()) {
            this.startNewSound(pticks);
         }

         ChannelHandle channelHandle = map.get(this.main);
         if (channelHandle != null) {
            channelHandle.execute(channel -> {
               if (!this.finishedPlaying) {
                  int source = channel.source;
                  float currentPlaytime = AL11.alGetSourcef(source, 4132);
                  ConcurrentHashMap<Integer, Float> m = FDMusicSystem.StreamingSourcesBufferLengthCache.sourceToProcessedBufferSecondLength;
                  if (m.containsKey(source)) {
                     currentPlaytime += m.get(source);
                  }

                  float playDuration = this.data.getPlayDuration();
                  if (currentPlaytime >= playDuration) {
                     if (this.data.shouldLoop()) {
                        this.startNewSound(pticks);
                     } else {
                        this.finishedPlaying = true;
                     }
                  }
               }
            });
         }
      }
   }

   protected void setAllSoundInstancesVolume(float volume) {
      for (SoundInstance instance : this.getSoundInstances()) {
         Map<SoundInstance, ChannelHandle> map = FDClientHelpers.getSoundEngine().instanceToChannel;
         ChannelHandle channelHandle = map.get(instance);
         if (channelHandle != null) {
            channelHandle.execute(channel -> channel.setVolume(volume));
         }
      }

      Map<SoundInstance, ChannelHandle> map = FDClientHelpers.getSoundEngine().instanceToChannel;
      ChannelHandle channelHandle = map.get(this.main);
      if (channelHandle != null) {
         channelHandle.execute(channel -> channel.setVolume(volume));
      }
   }

   private void startNewSound(float pticks) {
      SoundInstance soundInstance = new FDMusicSoundInstance(this.data.getSoundEvent(), this.owner.getVolume(pticks) * FDClientHelpers.getCurrentMusicVolume());
      if (this.main != null) {
         this.oldSoundInstances.add(this.main);
      }

      this.main = soundInstance;
      SoundManager soundManager = Minecraft.getInstance().getSoundManager();
      soundManager.play(soundInstance);
   }

   private void deleteEndedSoundInstances() {
      Iterator<SoundInstance> iterator = this.oldSoundInstances.iterator();

      while (iterator.hasNext()) {
         SoundInstance soundInstance = iterator.next();
         SoundManager soundManager = FDClientHelpers.getSoundManager();
         if (!soundManager.isActive(soundInstance)) {
            iterator.remove();
         }
      }
   }

   public boolean hasFinished() {
      return this.finishedPlaying;
   }

   public FDMusicPartData getData() {
      return this.data;
   }

   public List<SoundInstance> getSoundInstances() {
      List<SoundInstance> c = new ArrayList<>(this.oldSoundInstances);
      if (this.main != null) {
         c.add(this.main);
      }

      return c;
   }
}
