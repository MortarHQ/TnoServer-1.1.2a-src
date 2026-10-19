package com.benji.netherman.init;

import com.benji.netherman.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class ModJukeboxSongs {
   public static final ResourceKey<JukeboxSong> BOSS_SONG = ResourceKey.create(Registries.JUKEBOX_SONG, NetherExp.location("boss_fight"));
   public static final ResourceKey<JukeboxSong> CAVE_AMBIENT = ResourceKey.create(Registries.JUKEBOX_SONG, NetherExp.location("cave_ambient"));
   public static final ResourceKey<JukeboxSong> CITY_AMBIENT = ResourceKey.create(Registries.JUKEBOX_SONG, NetherExp.location("city_ambient"));
   public static final ResourceKey<JukeboxSong> CHURCH_AMBIENT = ResourceKey.create(Registries.JUKEBOX_SONG, NetherExp.location("church_ambient"));
   public static final ResourceKey<JukeboxSong> MAZE_AMBIENT = ResourceKey.create(Registries.JUKEBOX_SONG, NetherExp.location("maze_ambient"));
   public static final ResourceKey<JukeboxSong> AZAZEL_SONG = ResourceKey.create(Registries.JUKEBOX_SONG, NetherExp.location("azazel_fight"));
}
