package team.creative.creativecore.client.sound;

import java.util.concurrent.CompletableFuture;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.resources.ResourceLocation;

public interface SpecialSoundInstance extends SoundInstance {
   CompletableFuture<AudioStream> getAudioStream(SoundBufferLibrary var1, ResourceLocation var2, boolean var3);

   default CompletableFuture<AudioStream> getStream(SoundBufferLibrary soundBuffers, Sound sound, boolean looping) {
      return this.getAudioStream(soundBuffers, sound.getPath(), looping);
   }
}
