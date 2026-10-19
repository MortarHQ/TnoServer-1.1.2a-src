package com.mortal_cultivation.client;

import com.mortal_cultivation.registry.SoundRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.SelectMusicEvent;

@EventBusSubscriber(modid = "mortal_cultivation", value = Dist.CLIENT)
public final class SpiritForestMusicEvents {
   private SpiritForestMusicEvents() {
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void selectMusic(SelectMusicEvent event) {
      Music music = event.getMusic();
      if (music != null) {
         if (music == Musics.CREATIVE) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level != null && minecraft.player != null) {
               Holder<Biome> biome = minecraft.level.getBiome(minecraft.player.blockPosition());
               if (biome.is(WorldgenRegistry.SPIRIT_FORESTS)) {
                  music = ((Biome)biome.value()).getBackgroundMusic().orElse(music);
               }
            }
         }

         SoundInstance playing = event.getPlayingMusic();
         ResourceLocation spiritMusic = SoundRegistry.SPIRIT_FOREST_MUSIC.getId();
         if (playing instanceof SpiritForestMusicSound forestMusic) {
            boolean leaving = !((SoundEvent)music.getEvent().value()).getLocation().equals(spiritMusic);
            forestMusic.setFadingOut(leaving);
            if (leaving && music.replaceCurrentMusic()) {
               music = new Music(music.getEvent(), music.getMinDelay(), music.getMaxDelay(), false);
            }
         }

         event.setMusic(music);
      }
   }
}
