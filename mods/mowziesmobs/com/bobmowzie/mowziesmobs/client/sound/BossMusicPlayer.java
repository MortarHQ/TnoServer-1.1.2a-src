package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class BossMusicPlayer {
   public static BossMusic currentMusic;
   public static final BossMusic FERROUS_WROUGHTNAUT_MUSIC = new BossMusic((SoundEvent)MMSounds.MUSIC_FERROUS_WROUGHTNAUT_THEME.get());
   public static final BossMusic UMVUTHI_MUSIC = new BossMusic((SoundEvent)MMSounds.MUSIC_UMVUTHI_THEME.get());
   public static final BossMusic FROSTMAW_MUSIC = new BossMusic((SoundEvent)MMSounds.MUSIC_FROSTMAW_THEME.get());
   public static final BossMusic SCULPTOR_MUSIC = new SculptorBossMusic();
   private static final BossMusic[] BOSS_MUSICS = new BossMusic[]{FERROUS_WROUGHTNAUT_MUSIC, UMVUTHI_MUSIC, FROSTMAW_MUSIC, SCULPTOR_MUSIC};

   public static void requestBossMusic(MowzieEntity entity) {
      if ((Boolean)ConfigHandler.CLIENT.playBossMusic.get()) {
         BossMusic<? extends MowzieEntity> requestedMusic = (BossMusic<? extends MowzieEntity>)entity.getBossMusic();
         if (requestedMusic != null && entity.isAlive()) {
            Player player = MMCommon.PROXY.getLocalPlayer();
            if (player != null && currentMusic != null) {
               float f2 = Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.MUSIC);
               if (f2 <= 0.0F) {
                  currentMusic = null;
               } else if (currentMusic.getBoss() == entity && !entity.canPlayerHearMusic(player)) {
                  currentMusic.setBoss(null);
               } else if (currentMusic.getBoss() == null && currentMusic == requestedMusic) {
                  currentMusic.setBoss(entity);
               }
            } else if (entity.canPlayerHearMusic(player)) {
               currentMusic = requestedMusic;
               currentMusic.setBoss(entity);
            }

            if (currentMusic != null && !currentMusic.isPlaying()) {
               currentMusic.play();
            }
         }
      }
   }

   public static void stopBossMusic(MowzieEntity entity) {
      if ((Boolean)ConfigHandler.CLIENT.playBossMusic.get()) {
         if (currentMusic != null && currentMusic.getBoss() == entity) {
            currentMusic.setBoss(null);
         }
      }
   }

   public static void tick() {
      for (BossMusic music : BOSS_MUSICS) {
         if (music.isPlaying()) {
            music.tick();
         }
      }
   }
}
