package com.finderfeed.fdlib.systems.music;

import com.finderfeed.fdlib.FDClientHelpers;
import com.finderfeed.fdlib.FDLib;
import com.finderfeed.fdlib.systems.music.data.FDMusicData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.ChannelAccess.ChannelHandle;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import net.neoforged.neoforge.client.event.RenderFrameEvent.Post;
import org.lwjgl.openal.AL11;

@EventBusSubscriber(modid = "fdlib", value = Dist.CLIENT)
public class FDMusicSystem {
   private static final HashMap<UUID, FDMusic> ACTIVE_MUSIC = new HashMap<>();
   private static boolean musicWasPlaying = false;

   @SubscribeEvent
   public static void tickEvent(Pre event) {
      if (!Minecraft.getInstance().isPaused()) {
         muteAllMusicBesidesFDMusic();
         tickMusic();
      }
   }

   private static void muteAllMusicBesidesFDMusic() {
      float volume = -1.0F;
      if (!ACTIVE_MUSIC.isEmpty()) {
         musicWasPlaying = true;
         volume = 0.0F;
      } else if (musicWasPlaying) {
         volume = FDClientHelpers.getCurrentMusicVolume();
         musicWasPlaying = false;
      }

      if (volume != -1.0F) {
         float finalVolume = volume;
         musicWasPlaying = true;
         SoundEngine soundEngine = FDClientHelpers.getSoundEngine();
         Map<SoundInstance, ChannelHandle> instanceToChannel = soundEngine.instanceToChannel;
         List<SoundInstance> instances = getAllFDSoundInstances();

         for (Entry<SoundInstance, ChannelHandle> i : instanceToChannel.entrySet()) {
            SoundInstance soundInstance = i.getKey();
            if (soundInstance.getSource() == SoundSource.MUSIC && !instances.contains(soundInstance)) {
               ChannelHandle channelHandle = i.getValue();
               channelHandle.execute(channel -> channel.setVolume(finalVolume));
            }
         }
      }
   }

   public static FDMusic getMusic(UUID uuid) {
      return ACTIVE_MUSIC.get(uuid);
   }

   private static List<SoundInstance> getAllFDSoundInstances() {
      List<SoundInstance> instances = new ArrayList<>();

      for (FDMusic inst : ACTIVE_MUSIC.values()) {
         instances.addAll(inst.allSoundInstances());
      }

      return instances;
   }

   public static void endMusic(UUID uuid, int fadeOutTime) {
      FDMusic fdMusic = ACTIVE_MUSIC.get(uuid);
      if (fdMusic != null) {
         fdMusic.triggerMusicEnd(fadeOutTime);
      }
   }

   public static void addMusic(FDMusicData fdMusicData) {
      if (!ACTIVE_MUSIC.containsKey(fdMusicData.getMusicSourceUUID())) {
         ACTIVE_MUSIC.put(fdMusicData.getMusicSourceUUID(), new FDMusic(fdMusicData));
      }
   }

   private static void tickMusic() {
      Iterator<Entry<UUID, FDMusic>> iterator = ACTIVE_MUSIC.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, FDMusic> pair = iterator.next();
         FDMusic music = pair.getValue();
         if (music.hasFinishedPlaying()) {
            music.onFinishPlaying();
            iterator.remove();
         } else {
            music.tick();
         }
      }
   }

   @SubscribeEvent
   public static void clearSounds(LoggingOut event) {
      ACTIVE_MUSIC.clear();
   }

   @SubscribeEvent
   public static void renderTickEvent(net.neoforged.neoforge.client.event.RenderFrameEvent.Pre event) {
      musicRenderTick(event.getPartialTick().getGameTimeDeltaPartialTick(false));
   }

   public static void musicRenderTick(float pticks) {
      for (FDMusic music : ACTIVE_MUSIC.values()) {
         music.renderTick(pticks);
      }
   }

   @EventBusSubscriber(modid = "fdlib", value = Dist.CLIENT)
   public static class StreamingSourcesBufferLengthCache {
      public static final ConcurrentHashMap<Integer, Float> sourceToProcessedBufferSecondLength = new ConcurrentHashMap<>();

      @SubscribeEvent
      public static void deleteFinishedSources(Post event) {
         Iterator<Entry<Integer, Float>> iterator = sourceToProcessedBufferSecondLength.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<Integer, Float> pair = iterator.next();
            int source = pair.getKey();
            if (!AL11.alIsSource(source)) {
               iterator.remove();
            } else if (AL11.alGetSourcei(source, 4112) == 4116) {
               iterator.remove();
            }
         }
      }

      public static void onSoundEngineStop() {
         sourceToProcessedBufferSecondLength.clear();
      }

      public static void onChannelStop(int source) {
         sourceToProcessedBufferSecondLength.remove(source);
      }

      public static void onChannelHandleRelease(int source) {
         sourceToProcessedBufferSecondLength.remove(source);
      }

      public static void onProcessedBuffersRemoval(int source, int[] processedBuffers) {
         float fullSeconds = 0.0F;

         for (int buffer : processedBuffers) {
            int bytes = AL11.alGetBufferi(buffer, 8196);
            int channels = AL11.alGetBufferi(buffer, 8195);
            int bits = AL11.alGetBufferi(buffer, 8194);
            int frequency = AL11.alGetBufferi(buffer, 8193);

            try {
               int samples = bytes * 8 / (channels * bits);
               float seconds = (float)samples / frequency;
               fullSeconds += seconds;
            } catch (ArithmeticException e) {
               FDLib.LOGGER
                  .warn(
                     "Tried to cache length in seconds of a streaming sound source but failed, tell author of FDLib to fix his shit. Buffer attributes - Bytes: %d, Channels: %d, Bits: %d, Frequency: %d"
                        .formatted(bytes, channels, bits, frequency)
                  );
            }
         }

         float currentLength = sourceToProcessedBufferSecondLength.computeIfAbsent(source, i -> 0.0F);
         sourceToProcessedBufferSecondLength.put(source, fullSeconds + currentLength);
      }
   }
}
