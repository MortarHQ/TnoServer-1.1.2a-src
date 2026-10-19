package dev.latvian.mods.kubejs.core;

import dev.latvian.mods.kubejs.plugin.builtin.wrapper.UUIDWrapper;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface PlayerSelector {
   static PlayerSelector wrap(Object o) {
      if (o instanceof ServerPlayer sp) {
         return identity(sp);
      } else if (o instanceof UUID uuid) {
         return uuid(uuid);
      } else {
         String name = Objects.toString(o, "").trim().toLowerCase(Locale.ROOT);
         if (name.isEmpty()) {
            return identity(null);
         }

         UUID uuid = UUIDWrapper.fromString(name);
         return uuid != null ? uuid(uuid) : name(name).or(fuzzyName(name));
      }
   }

   @Nullable
   ServerPlayer getPlayer(MinecraftServer server);

   static PlayerSelector identity(ServerPlayer player) {
      return server -> player;
   }

   static PlayerSelector uuid(UUID uuid) {
      return server -> server.getPlayerList().getPlayer(uuid);
   }

   static PlayerSelector name(String name) {
      return server -> server.getPlayerList().getPlayerByName(name);
   }

   static PlayerSelector fuzzyName(String name) {
      return server -> {
         for (ServerPlayer p : server.getPlayerList().getPlayers()) {
            if (p.getScoreboardName().toLowerCase(Locale.ROOT).contains(name)) {
               return p;
            }
         }

         return null;
      };
   }

   default PlayerSelector or(PlayerSelector fallback) {
      return server -> {
         ServerPlayer p = this.getPlayer(server);
         return p == null ? fallback.getPlayer(server) : p;
      };
   }
}
