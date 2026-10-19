package dev.ftb.mods.ftbteams.data;

import dev.ftb.mods.ftbteams.FTBTeams;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.client.ClientTeamManager;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import dev.ftb.mods.ftbteams.api.property.TeamProperties;
import dev.ftb.mods.ftbteams.client.KnownClientPlayerNet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ClientTeamManagerImpl implements ClientTeamManager {
   private static ClientTeamManagerImpl INSTANCE;
   public static final StreamCodec<RegistryFriendlyByteBuf, ClientTeamManagerImpl> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC,
      m -> m.managerId,
      ByteBufCodecs.map(HashMap::new, UUIDUtil.STREAM_CODEC, ClientTeam.STREAM_CODEC),
      m -> m.teamMap,
      ByteBufCodecs.map(HashMap::new, UUIDUtil.STREAM_CODEC, KnownClientPlayerNet.STREAM_CODEC),
      m -> m.knownPlayers,
      ClientTeamManagerImpl::new
   );
   private final UUID managerId;
   private final Map<UUID, ClientTeam> teamMap;
   private final Map<UUID, KnownClientPlayer> knownPlayers;
   private UUID selfTeamId = Util.NIL_UUID;
   private boolean valid;
   private ClientTeam selfTeam;
   private KnownClientPlayer selfKnownPlayer;

   public static ClientTeamManagerImpl getInstance() {
      return INSTANCE;
   }

   public static void ifPresent(Consumer<ClientTeamManagerImpl> mgr) {
      if (INSTANCE != null) {
         mgr.accept(INSTANCE);
      }
   }

   private ClientTeamManagerImpl(UUID managerId) {
      this(managerId, new HashMap<>(), new HashMap<>());
   }

   private ClientTeamManagerImpl(UUID managerId, Map<UUID, ClientTeam> teamMap, Map<UUID, KnownClientPlayer> knownPlayers) {
      this.managerId = managerId;
      this.teamMap = teamMap;
      this.knownPlayers = knownPlayers;
      this.valid = true;
   }

   public UUID getSelfTeamId() {
      return this.selfTeamId;
   }

   public ClientTeamManagerImpl setSelfTeamId(UUID selfTeamId) {
      this.selfTeamId = selfTeamId;
      return this;
   }

   public static ClientTeamManagerImpl forSyncing(TeamManagerImpl manager, Collection<? extends Team> teams) {
      ClientTeamManagerImpl clientManager = new ClientTeamManagerImpl(manager.getId());

      for (Team team : teams) {
         if (team instanceof AbstractTeam abstractTeam) {
            ClientTeam clientTeam = manager.getTeamMap().containsKey(team.getId()) ? ClientTeam.copyOf(abstractTeam) : ClientTeam.invalidTeam(abstractTeam);
            clientTeam.setFullSyncRequired(() -> clientManager.getSelfTeamId().equals(clientTeam.getId()));
            clientManager.addTeam(clientTeam);
         }

         if (team instanceof PlayerTeam playerTeam) {
            clientManager.knownPlayers.put(team.getId(), playerTeam.createClientPlayer());
         }
      }

      return clientManager;
   }

   @Override
   public UUID getManagerId() {
      return this.managerId;
   }

   @Override
   public boolean isValid() {
      return this.valid;
   }

   @Override
   public Collection<KnownClientPlayer> knownClientPlayers() {
      return Collections.unmodifiableCollection(this.knownPlayers.values());
   }

   @Override
   public Collection<Team> getTeams() {
      return Collections.unmodifiableCollection(this.teamMap.values());
   }

   @Override
   public Optional<Team> getTeamByID(UUID teamId) {
      return Optional.ofNullable(this.teamMap.get(teamId));
   }

   @Override
   public Optional<Team> getTeamForPlayer(Player player) {
      return this.getKnownPlayer(player.getUUID()).flatMap(kcp -> this.getTeamByID(kcp.teamId()));
   }

   public ClientTeam selfTeam() {
      return this.selfTeam;
   }

   @Override
   public KnownClientPlayer self() {
      return this.selfKnownPlayer;
   }

   public void initSelfDetails(UUID selfTeamID) {
      this.selfTeam = this.teamMap.get(selfTeamID);
      UUID userId = Minecraft.getInstance().getUser().getProfileId();
      this.selfKnownPlayer = this.knownPlayers.get(userId);
      if (this.selfKnownPlayer == null) {
         FTBTeams.LOGGER
            .error(
               "Local player id {} was not found in the known players list [{}]! FTB Teams will not be able to function correctly!",
               userId,
               String.join(",", this.knownPlayers.keySet().stream().map(UUID::toString).toList())
            );
      }
   }

   @Override
   public Optional<KnownClientPlayer> getKnownPlayer(UUID id) {
      return Optional.ofNullable(this.knownPlayers.get(id));
   }

   public Optional<ClientTeam> getTeam(UUID id) {
      return Optional.ofNullable(this.teamMap.get(id));
   }

   @Override
   public Component formatName(@Nullable UUID id) {
      if (id != null && !id.equals(Util.NIL_UUID)) {
         KnownClientPlayer p = this.knownPlayers.get(id);
         return Component.literal(p == null ? "Unknown" : p.name()).withStyle(ChatFormatting.YELLOW);
      } else {
         return Component.literal("System").withStyle(ChatFormatting.LIGHT_PURPLE);
      }
   }

   public void addTeam(ClientTeam team) {
      this.teamMap.put(team.getId(), team);
   }

   private void invalidate() {
      this.teamMap.clear();
      this.valid = false;
   }

   public static void syncFromServer(ClientTeamManagerImpl syncedData, UUID selfTeamID, boolean fullSync) {
      if (fullSync) {
         syncedData.initSelfDetails(selfTeamID);
         if (INSTANCE != null) {
            INSTANCE.invalidate();
         }

         INSTANCE = syncedData;
      } else if (INSTANCE != null) {
         syncedData.teamMap.forEach((teamID, clientTeam) -> {
            if (clientTeam.toBeRemoved()) {
               FTBTeams.LOGGER.debug("remove {} from client team map", teamID);
               INSTANCE.teamMap.remove(teamID);
            } else {
               ClientTeam existing = INSTANCE.teamMap.get(teamID);
               if (existing != null) {
                  FTBTeams.LOGGER.debug("update {} in client team map", teamID);
               } else {
                  FTBTeams.LOGGER.debug("insert {} into client team map", teamID);
               }

               INSTANCE.teamMap.put(teamID, clientTeam);
            }
         });
         INSTANCE.knownPlayers.putAll(syncedData.knownPlayers);
         INSTANCE.initSelfDetails(selfTeamID);
      }
   }

   public void updatePresence(KnownClientPlayer newPlayer) {
      KnownClientPlayer existing = INSTANCE.knownPlayers.get(newPlayer.id());
      KnownClientPlayer toUpdate = existing == null ? newPlayer : this.updateFrom(existing.id(), newPlayer);
      this.knownPlayers.put(toUpdate.id(), newPlayer);
      FTBTeams.LOGGER.debug("Updated presence of {}", newPlayer.name());
   }

   private KnownClientPlayer updateFrom(UUID id, KnownClientPlayer other) {
      return new KnownClientPlayer(id, other.name(), other.online(), other.teamId(), other.profile(), other.extraData());
   }

   public void updateDisplayName(UUID teamId, String newName) {
      Team team = this.teamMap.get(teamId);
      if (team != null) {
         team.setProperty(TeamProperties.DISPLAY_NAME, newName);
         FTBTeams.LOGGER.debug("Updated display name of {} to {}", teamId, newName);
      }
   }
}
