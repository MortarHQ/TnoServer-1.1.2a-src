package com.github.b4ndithelps.tennogamenolife.registry.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.JukeboxSong;

public final class TenreincarnationJukeboxSongs {
   public static final ResourceKey<JukeboxSong> GAME_OF_LIFE = create("game_of_life");

   private TenreincarnationJukeboxSongs() {
   }

   private static ResourceKey<JukeboxSong> create(String name) {
      return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath("tennogamenolife", name));
   }
}
