package com.finderfeed.fdlib.systems.music.music_areas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Pre;

@EventBusSubscriber(modid = "fdlib")
public class FDMusicAreasHandler {
   private static final HashMap<UUID, FDMusicArea> MUSIC_AREAS = new HashMap<>();

   @SubscribeEvent
   public static void removeAreas(ServerStoppedEvent event) {
      MUSIC_AREAS.clear();
   }

   @SubscribeEvent
   public static void tickAreas(Pre event) {
      Iterator<Entry<UUID, FDMusicArea>> iterator = MUSIC_AREAS.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, FDMusicArea> pair = iterator.next();
         FDMusicArea area = pair.getValue();
         if (area.shouldBeDeleted()) {
            area.onRemoval(event.getServer(), 20);
            iterator.remove();
         } else {
            ServerLevel level = event.getServer().getLevel(area.getDimension());
            if (level != null) {
               area.tick(level);
            }

            area.tickDeletionTicker(level == null);
         }
      }
   }

   public static void removeArea(MinecraftServer server, UUID uuid, int fadeOutTime) {
      FDMusicArea area = getMusicArea(uuid);
      if (area != null) {
         area.onRemoval(server, fadeOutTime);
         MUSIC_AREAS.remove(uuid);
      }
   }

   public static void addArea(UUID uuid, FDMusicArea musicArea) {
      if (!MUSIC_AREAS.containsKey(uuid)) {
         MUSIC_AREAS.put(uuid, musicArea);
      }
   }

   public static FDMusicArea getMusicArea(UUID uuid) {
      return MUSIC_AREAS.get(uuid);
   }

   public static boolean hasMusicArea(UUID uuid) {
      return MUSIC_AREAS.containsKey(uuid);
   }
}
