package com.github.wal_bos.moreskills.world.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

public final class ArsWeissCloneChunkTickets {
   private static final Map<String, Map<Long, ArsWeissCloneChunkTickets.Ticket>> COUNTS = new HashMap<>();
   private static final int MAX_FORCED_CHUNKS_PER_DIMENSION = 64;
   private static final long TICKET_TTL = 900L;

   private ArsWeissCloneChunkTickets() {
   }

   public static boolean force(ServerLevel level, ChunkPos chunk) {
      if (level != null && chunk != null) {
         prune(level, level.getGameTime());
         String key = key(level);
         long packed = chunk.toLong();
         Map<Long, ArsWeissCloneChunkTickets.Ticket> map = COUNTS.computeIfAbsent(key, ignored -> new HashMap<>());
         ArsWeissCloneChunkTickets.Ticket ticket = map.get(packed);
         if (ticket == null && map.size() >= 64) {
            return false;
         }

         if (ticket == null) {
            ticket = new ArsWeissCloneChunkTickets.Ticket(level.getGameTime());
            map.put(packed, ticket);
            level.setChunkForced(chunk.x, chunk.z, true);
         }

         ticket.count++;
         ticket.lastTouch = level.getGameTime();
         return true;
      } else {
         return false;
      }
   }

   public static void touch(ServerLevel level, ChunkPos chunk) {
      if (level != null && chunk != null) {
         Map<Long, ArsWeissCloneChunkTickets.Ticket> map = COUNTS.get(key(level));
         if (map != null) {
            ArsWeissCloneChunkTickets.Ticket ticket = map.get(chunk.toLong());
            if (ticket != null) {
               ticket.lastTouch = level.getGameTime();
            }
         }
      }
   }

   public static void release(ServerLevel level, ChunkPos chunk) {
      if (level != null && chunk != null) {
         String key = key(level);
         long packed = chunk.toLong();
         Map<Long, ArsWeissCloneChunkTickets.Ticket> map = COUNTS.get(key);
         if (map == null) {
            level.setChunkForced(chunk.x, chunk.z, false);
         } else {
            ArsWeissCloneChunkTickets.Ticket ticket = map.get(packed);
            if (ticket == null) {
               level.setChunkForced(chunk.x, chunk.z, false);
            } else {
               ticket.count--;
               if (ticket.count <= 0) {
                  map.remove(packed);
                  level.setChunkForced(chunk.x, chunk.z, false);
               }

               if (map.isEmpty()) {
                  COUNTS.remove(key);
               }
            }
         }
      }
   }

   public static void prune(ServerLevel level, long gameTime) {
      if (level != null) {
         String key = key(level);
         Map<Long, ArsWeissCloneChunkTickets.Ticket> map = COUNTS.get(key);
         if (map != null) {
            Iterator<Entry<Long, ArsWeissCloneChunkTickets.Ticket>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<Long, ArsWeissCloneChunkTickets.Ticket> entry = iterator.next();
               ArsWeissCloneChunkTickets.Ticket ticket = entry.getValue();
               if (gameTime - ticket.lastTouch > 900L) {
                  ChunkPos chunk = new ChunkPos(entry.getKey());
                  level.setChunkForced(chunk.x, chunk.z, false);
                  iterator.remove();
               }
            }

            if (map.isEmpty()) {
               COUNTS.remove(key);
            }
         }
      }
   }

   private static String key(ServerLevel level) {
      return level.dimension().location().toString();
   }

   private static final class Ticket {
      private int count;
      private long lastTouch;

      private Ticket(long lastTouch) {
         this.lastTouch = lastTouch;
      }
   }
}
