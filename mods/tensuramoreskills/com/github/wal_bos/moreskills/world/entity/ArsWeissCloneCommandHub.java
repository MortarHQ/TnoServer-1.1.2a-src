package com.github.wal_bos.moreskills.world.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public final class ArsWeissCloneCommandHub {
   private static final AtomicLong NEXT_ID = new AtomicLong(1L);
   private static final Map<UUID, ArsWeissCloneCommandHub.Command> COMMANDS = new HashMap<>();

   private ArsWeissCloneCommandHub() {
   }

   public static void publish(UUID owner, String command, String query, int amount) {
      publishAndGet(owner, command, query, amount);
   }

   public static ArsWeissCloneCommandHub.Command publishAndGet(UUID owner, String command, String query, int amount) {
      if (owner != null && command != null && !command.isBlank()) {
         ArsWeissCloneCommandHub.Command value = new ArsWeissCloneCommandHub.Command(
            NEXT_ID.getAndIncrement(), command.toLowerCase().trim(), query == null ? "" : query.trim(), Math.max(1, amount)
         );
         COMMANDS.put(owner, value);
         return value;
      } else {
         return null;
      }
   }

   public static ArsWeissCloneCommandHub.Command latest(UUID owner) {
      return owner == null ? null : COMMANDS.get(owner);
   }

   public record Command(long id, String command, String query, int amount) {
   }
}
