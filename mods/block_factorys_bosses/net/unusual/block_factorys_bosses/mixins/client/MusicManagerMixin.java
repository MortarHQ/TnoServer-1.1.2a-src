package net.unusual.block_factorys_bosses.mixins.client;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.unusual.block_factorys_bosses.util.BossHandling;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public class MusicManagerMixin {
   @Shadow
   @Nullable
   private SoundInstance currentMusic;
   @Shadow
   @Final
   private Minecraft minecraft;
   @Shadow
   private int nextSongDelay;

   @Inject(method = "startPlaying", at = @At("HEAD"), cancellable = true)
   private void atStartPlaying(Music selector, CallbackInfo ci) {
      if (selector instanceof BossHandling.ClientHandler.BossMusic) {
         ci.cancel();
         this.currentMusic = new SimpleSoundInstance(
            ((SoundEvent)selector.getEvent().value()).getLocation(),
            SoundSource.MUSIC,
            1.0F,
            1.0F,
            SoundInstance.createUnseededRandom(),
            true,
            0,
            Attenuation.NONE,
            0.0,
            0.0,
            0.0,
            true
         );
         if (this.currentMusic.getSound() != SoundManager.EMPTY_SOUND) {
            this.minecraft.getSoundManager().play(this.currentMusic);
         }

         this.nextSongDelay = Integer.MAX_VALUE;
      }
   }
}
