package com.tensura_guild.data;

import com.mojang.authlib.GameProfile;
import com.tensura_guild.network.GuildPartyStatePayload;
import com.tensura_guild.registry.GuildAttachments;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.GameProfileCache;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public final class GuildPartyService {
   private static final int MAX_MESSAGES = 50;
   private static final Map<UUID, GuildPartyService.Party> PARTIES = new HashMap<>();
   private static final Map<UUID, UUID> PLAYER_PARTIES = new HashMap<>();
   private static MinecraftServer loadedServer;

   private GuildPartyService() {
   }

   public static void createParty(ServerPlayer player, String requestedName) {
      ensureLoaded(player.server);
      if (PLAYER_PARTIES.containsKey(player.getUUID())) {
         syncToPlayer(player);
      } else {
         String partyName = sanitizePartyName(requestedName);
         if (!partyName.isBlank() && !findPartyByName(partyName).isPresent()) {
            GuildPartyService.Party party = new GuildPartyService.Party(player.getUUID(), partyName);
            party.members.add(player.getUUID());
            PARTIES.put(player.getUUID(), party);
            PLAYER_PARTIES.put(player.getUUID(), player.getUUID());
            save(player.server);
            syncParty(player.server, party);
         } else {
            syncToPlayer(player);
         }
      }
   }

   public static void requestJoin(ServerPlayer player, String partyName) {
      ensureLoaded(player.server);
      if (PLAYER_PARTIES.containsKey(player.getUUID())) {
         syncToPlayer(player);
      } else {
         String requestedName = sanitizePartyName(partyName);
         if (requestedName.isBlank()) {
            syncToPlayer(player);
         } else {
            Optional<GuildPartyService.Party> party = findPartyByName(requestedName);
            if (!party.isEmpty() && party.get().members.size() < 4) {
               party.get().members.add(player.getUUID());
               PLAYER_PARTIES.put(player.getUUID(), party.get().leaderId);
               save(player.server);
               syncParty(player.server, party.get());
            } else {
               syncToPlayer(player);
            }
         }
      }
   }

   public static void leaveParty(ServerPlayer player) {
      leaveParty(player, true);
   }

   public static boolean isInParty(ServerPlayer player) {
      ensureLoaded(player.server);
      return PLAYER_PARTIES.containsKey(player.getUUID());
   }

   public static void resetPlayer(ServerPlayer player) {
      leaveParty(player, true);
   }

   public static boolean isPartyLeader(ServerPlayer player) {
      ensureLoaded(player.server);
      UUID leaderId = PLAYER_PARTIES.get(player.getUUID());
      return leaderId != null && leaderId.equals(player.getUUID());
   }

   public static boolean arePartyMembers(ServerPlayer first, ServerPlayer second) {
      if (first != null && second != null && first.server == second.server && !first.getUUID().equals(second.getUUID())) {
         ensureLoaded(first.server);
         UUID firstLeaderId = PLAYER_PARTIES.get(first.getUUID());
         UUID secondLeaderId = PLAYER_PARTIES.get(second.getUUID());
         return firstLeaderId != null && firstLeaderId.equals(secondLeaderId);
      } else {
         return false;
      }
   }

   public static Optional<UUID> partyLeaderId(ServerPlayer player) {
      ensureLoaded(player.server);
      return Optional.ofNullable(PLAYER_PARTIES.get(player.getUUID()));
   }

   public static Optional<String> partyName(MinecraftServer server, UUID leaderId) {
      ensureLoaded(server);
      GuildPartyService.Party party = leaderId == null ? null : PARTIES.get(leaderId);
      return party != null && !party.name.isBlank() ? Optional.of(party.name) : Optional.empty();
   }

   public static boolean partyExists(MinecraftServer server, UUID leaderId) {
      ensureLoaded(server);
      return leaderId != null && PARTIES.containsKey(leaderId);
   }

   public static List<ServerPlayer> onlinePartyMembers(ServerPlayer player) {
      ensureLoaded(player.server);
      UUID leaderId = PLAYER_PARTIES.get(player.getUUID());
      return leaderId == null ? List.of() : onlinePartyMembers(player.server, leaderId);
   }

   public static int onlinePartyMemberCount(ServerPlayer player) {
      return onlinePartyMembers(player).size();
   }

   public static GuildLevelData highestOnlinePartyLevel(ServerPlayer player) {
      return onlinePartyMembers(player)
         .stream()
         .map(member -> (GuildLevelData)member.getData(GuildAttachments.GUILD_LEVEL))
         .max(Comparator.comparingInt(GuildLevelData::levelIndex))
         .orElseGet(() -> (GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL));
   }

   public static List<ServerPlayer> onlinePartyMembers(MinecraftServer server, UUID leaderId) {
      ensureLoaded(server);
      GuildPartyService.Party party = leaderId == null ? null : PARTIES.get(leaderId);
      if (party == null) {
         return List.of();
      }

      List<ServerPlayer> members = new ArrayList<>();

      for (UUID memberId : party.members) {
         ServerPlayer member = server.getPlayerList().getPlayer(memberId);
         if (member != null) {
            members.add(member);
         }
      }

      return members;
   }

   public static void kickMember(ServerPlayer player, UUID targetId) {
      ensureLoaded(player.server);
      UUID leaderId = PLAYER_PARTIES.get(player.getUUID());
      if (leaderId != null && leaderId.equals(player.getUUID()) && targetId != null && !targetId.equals(player.getUUID())) {
         GuildPartyService.Party party = PARTIES.get(leaderId);
         if (party != null && party.members.remove(targetId)) {
            PLAYER_PARTIES.remove(targetId);
            save(player.server);
            ServerPlayer target = player.server.getPlayerList().getPlayer(targetId);
            if (target != null) {
               GuildTaskService.invalidatePartyTasks(target, leaderId);
               syncToPlayer(target);
            }

            syncParty(player.server, party);
         } else {
            syncToPlayer(player);
         }
      } else {
         syncToPlayer(player);
      }
   }

   public static void sendChat(ServerPlayer player, String content) {
      ensureLoaded(player.server);
      UUID leaderId = PLAYER_PARTIES.get(player.getUUID());
      GuildPartyService.Party party = leaderId == null ? null : PARTIES.get(leaderId);
      if (party != null && party.members.contains(player.getUUID())) {
         String message = sanitizeChat(content);
         if (!message.isBlank()) {
            party.messages.add(new GuildPartyState.Message(player.getUUID(), player.getGameProfile().getName(), message, System.currentTimeMillis()));

            while (party.messages.size() > 50) {
               party.messages.remove(0);
            }

            save(player.server);
            syncParty(player.server, party);
         }
      } else {
         syncToPlayer(player);
      }
   }

   public static void syncToPlayer(ServerPlayer player) {
      ensureLoaded(player.server);
      UUID leaderId = PLAYER_PARTIES.get(player.getUUID());
      GuildTaskService.invalidateOtherPartyTasks(player, Optional.ofNullable(leaderId));
      GuildPartyService.Party party = leaderId == null ? null : PARTIES.get(leaderId);
      PacketDistributor.sendToPlayer(player, new GuildPartyStatePayload(toState(player, party)), new CustomPacketPayload[0]);
   }

   public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
   }

   private static void leaveParty(ServerPlayer player, boolean syncLeavingPlayer) {
      ensureLoaded(player.server);
      UUID leaderId = PLAYER_PARTIES.remove(player.getUUID());
      if (leaderId == null) {
         if (syncLeavingPlayer) {
            syncToPlayer(player);
         }
      } else {
         GuildPartyService.Party party = PARTIES.get(leaderId);
         if (party == null) {
            if (syncLeavingPlayer) {
               syncToPlayer(player);
            }
         } else if (!leaderId.equals(player.getUUID())) {
            party.members.remove(player.getUUID());
            GuildTaskService.invalidatePartyTasks(player, leaderId);
            save(player.server);
            if (syncLeavingPlayer) {
               syncToPlayer(player);
            }

            syncParty(player.server, party);
         } else {
            PARTIES.remove(leaderId);

            for (UUID memberId : List.copyOf(party.members)) {
               PLAYER_PARTIES.remove(memberId);
               ServerPlayer member = player.server.getPlayerList().getPlayer(memberId);
               if (member != null) {
                  GuildTaskService.invalidatePartyTasks(member, leaderId);
                  if (syncLeavingPlayer || !memberId.equals(player.getUUID())) {
                     syncToPlayer(member);
                  }
               }
            }

            save(player.server);
         }
      }
   }

   private static void syncParty(MinecraftServer server, GuildPartyService.Party party) {
      ensureLoaded(server);

      for (UUID memberId : party.members) {
         ServerPlayer member = server.getPlayerList().getPlayer(memberId);
         if (member != null) {
            syncToPlayer(member);
         }
      }
   }

   private static GuildPartyState toState(ServerPlayer viewer, GuildPartyService.Party party) {
      if (party == null) {
         return GuildPartyState.EMPTY;
      }

      List<GuildPartyState.Member> members = new ArrayList<>();

      for (UUID memberId : party.members) {
         String name = displayNameForMember(viewer.server, memberId);
         members.add(new GuildPartyState.Member(memberId, name, memberId.equals(party.leaderId)));
      }

      return new GuildPartyState(true, party.leaderId, party.name, members, party.messages);
   }

   private static String displayNameForMember(MinecraftServer server, UUID memberId) {
      if (server != null && memberId != null) {
         ServerPlayer online = server.getPlayerList().getPlayer(memberId);
         if (online != null) {
            return online.getGameProfile().getName();
         }

         GameProfileCache cache = server.getProfileCache();
         if (cache != null) {
            Optional<GameProfile> profile = cache.get(memberId);
            if (profile.isPresent() && profile.get().getName() != null && !profile.get().getName().isBlank()) {
               return profile.get().getName();
            }
         }

         String id = memberId.toString();
         return "Offline-" + id.substring(0, Math.min(8, id.length()));
      } else {
         return "Offline";
      }
   }

   private static void ensureLoaded(MinecraftServer server) {
      if (loadedServer != server) {
         PARTIES.clear();
         PLAYER_PARTIES.clear();
         loadedServer = server;

         for (GuildPartyWorldData.SavedParty savedParty : GuildPartyWorldData.get(server).parties()) {
            GuildPartyService.Party party = new GuildPartyService.Party(savedParty.leaderId(), savedParty.name());

            for (UUID memberId : savedParty.members()) {
               if (party.members.size() >= 4) {
                  break;
               }

               if (!party.members.contains(memberId)) {
                  party.members.add(memberId);
               }
            }

            if (!party.members.contains(party.leaderId)) {
               party.members.add(0, party.leaderId);
            }

            party.messages.addAll(savedParty.messages());

            while (party.messages.size() > 50) {
               party.messages.remove(0);
            }

            PARTIES.put(party.leaderId, party);

            for (UUID memberId : party.members) {
               PLAYER_PARTIES.put(memberId, party.leaderId);
            }
         }
      }
   }

   private static void save(MinecraftServer server) {
      GuildPartyWorldData.get(server)
         .replaceParties(
            PARTIES.values().stream().map(party -> new GuildPartyWorldData.SavedParty(party.leaderId, party.name, party.members, party.messages)).toList()
         );
   }

   private static String sanitizePartyName(String value) {
      return sanitize(value, 32);
   }

   private static Optional<GuildPartyService.Party> findPartyByName(String partyName) {
      return PARTIES.values().stream().filter(party -> party.name.equalsIgnoreCase(partyName)).findFirst();
   }

   private static String sanitizeChat(String value) {
      return sanitize(value, 160);
   }

   private static String sanitize(String value, int maxLength) {
      String trimmed = value == null ? "" : value.trim();
      return trimmed.length() <= maxLength ? trimmed : trimmed.substring(0, maxLength);
   }

   private static final class Party {
      private final UUID leaderId;
      private final String name;
      private final List<UUID> members = new ArrayList<>(4);
      private final List<GuildPartyState.Message> messages = new ArrayList<>();

      private Party(UUID leaderId, String name) {
         this.leaderId = leaderId;
         this.name = name;
      }
   }
}
