package dev.ftb.mods.ftbteams.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.snbt.SNBT;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbteams.FTBTeams;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamManager;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.api.event.PlayerLoggedInAfterTeamEvent;
import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import dev.ftb.mods.ftbteams.api.event.TeamManagerEvent;
import dev.ftb.mods.ftbteams.api.property.TeamProperties;
import dev.ftb.mods.ftbteams.config.ServerConfig;
import dev.ftb.mods.ftbteams.net.SyncMessageHistoryMessage;
import dev.ftb.mods.ftbteams.net.SyncTeamsMessage;
import dev.ftb.mods.ftbteams.net.ToggleChatResponseMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import org.jetbrains.annotations.Nullable;

public class TeamManagerImpl implements TeamManager {
   public static final LevelResource FOLDER_NAME = new LevelResource("ftbteams");
   public static TeamManagerImpl INSTANCE;
   private final MinecraftServer server;
   private UUID id;
   private boolean shouldSave;
   private final Map<UUID, PlayerTeam> knownPlayers;
   private final Map<UUID, AbstractTeam> teamMap;
   private final Set<UUID> chatRedirected;
   Map<String, Team> nameMap;
   private CompoundTag extraData;

   public TeamManagerImpl(MinecraftServer s) {
      this.server = s;
      this.knownPlayers = new LinkedHashMap<>();
      this.teamMap = new LinkedHashMap<>();
      this.extraData = new CompoundTag();
      this.chatRedirected = new HashSet<>();
   }

   @Override
   public MinecraftServer getServer() {
      return this.server;
   }

   @Override
   public UUID getId() {
      if (this.id == null) {
         this.id = UUID.randomUUID();
      }

      return this.id;
   }

   @Override
   public Map<UUID, ? extends Team> getKnownPlayerTeams() {
      return Collections.unmodifiableMap(this.knownPlayers);
   }

   public Map<UUID, AbstractTeam> getTeamMap() {
      return this.teamMap;
   }

   @Override
   public Collection<Team> getTeams() {
      return Collections.unmodifiableCollection(this.teamMap.values());
   }

   public Map<String, Team> getTeamNameMap() {
      if (this.nameMap == null) {
         this.nameMap = new HashMap<>();

         for (AbstractTeam team : this.teamMap.values()) {
            this.nameMap.put(team.getShortName(), team);
         }
      }

      return this.nameMap;
   }

   @Override
   public Optional<Team> getTeamByID(UUID teamId) {
      return Optional.ofNullable(this.teamMap.get(teamId));
   }

   @Override
   public Optional<Team> getTeamByName(String name) {
      return Optional.ofNullable(this.getTeamNameMap().get(name));
   }

   @Override
   public Optional<Team> getPlayerTeamForPlayerID(UUID uuid) {
      return Optional.ofNullable(this.getPersonalTeamForPlayerID(uuid));
   }

   public PlayerTeam getPersonalTeamForPlayerID(UUID uuid) {
      return this.knownPlayers.get(uuid);
   }

   @Override
   public Optional<Team> getTeamForPlayerID(UUID uuid) {
      PlayerTeam t = this.knownPlayers.get(uuid);
      return t == null ? Optional.empty() : Optional.ofNullable(t.getEffectiveTeam());
   }

   @Override
   public Optional<Team> getTeamForPlayer(ServerPlayer player) {
      return this.getTeamForPlayerID(player.getUUID());
   }

   @Override
   public boolean arePlayersInSameTeam(UUID id1, UUID id2) {
      return this.getTeamForPlayerID(id1)
         .map(team1 -> this.getTeamForPlayerID(id2).map(team2 -> team1.getId().equals(team2.getId())).orElse(false))
         .orElse(false);
   }

   public void load() {
      this.id = null;
      Path directory = this.server.getWorldPath(FOLDER_NAME);
      if (!Files.notExists(directory) && Files.isDirectory(directory)) {
         CompoundTag dataFileTag = SNBT.read(directory.resolve("ftbteams.snbt"));
         if (dataFileTag != null) {
            if (dataFileTag.contains("id")) {
               this.id = UUID.fromString(dataFileTag.getString("id"));
            }

            this.extraData = dataFileTag.getCompound("extra");
            ((Consumer)TeamManagerEvent.LOADED.invoker()).accept(new TeamManagerEvent(this));
            this.chatRedirected.clear();
            dataFileTag.getList("chat_redirected", 8).forEach(tag -> {
               try {
                  this.chatRedirected.add(UUID.fromString(tag.getAsString()));
               } catch (IllegalArgumentException e) {
                  FTBTeams.LOGGER.error("invalid uuid {} in 'chat_redirection', ignoring", tag.getAsString());
               }
            });
         }

         for (TeamType type : TeamType.values()) {
            Path dir = directory.resolve(type.getSerializedName());
            if (Files.exists(dir) && Files.isDirectory(dir)) {
               try (Stream<Path> s = Files.list(dir)) {
                  s.filter(path -> path.getFileName().toString().endsWith(".snbt")).forEach(file -> {
                     CompoundTag nbt = SNBT.read(file);
                     if (nbt != null) {
                        AbstractTeam team = type.createTeam(this, UUID.fromString(nbt.getString("id")));
                        this.teamMap.put(team.id, team);
                        team.deserializeNBT(nbt, this.server.registryAccess());
                     }
                  });
               } catch (Exception ex) {
                  FTBTeams.LOGGER.error("can't list directory {}: {}", dir, ex.getMessage());
               }
            }
         }

         for (AbstractTeam team : this.teamMap.values()) {
            if (team instanceof PlayerTeam) {
               this.knownPlayers.put(team.id, (PlayerTeam)team);
            }
         }

         for (AbstractTeam team : this.teamMap.values()) {
            if (team instanceof PartyTeam) {
               for (UUID member : team.getMembers()) {
                  PlayerTeam t = this.knownPlayers.get(member);
                  if (t != null) {
                     t.setEffectiveTeam(team);
                  }
               }
            }
         }

         FTBTeams.LOGGER.info("loaded team data: {} known players, {} teams total", this.knownPlayers.size(), this.teamMap.size());
      }
   }

   @Override
   public void markDirty() {
      this.shouldSave = true;
      this.nameMap = null;
   }

   public void saveNow() {
      Path directory = this.server.getWorldPath(FOLDER_NAME);
      if (!Files.exists(directory)) {
         this.tryCreateDir(directory);

         for (TeamType type : TeamType.values()) {
            this.tryCreateDir(directory.resolve(type.getSerializedName()));
         }
      }

      if (this.shouldSave) {
         ((Consumer)TeamManagerEvent.SAVED.invoker()).accept(new TeamManagerEvent(this));
         SNBT.write(directory.resolve("ftbteams.snbt"), this.serializeNBT());
         this.shouldSave = false;
      }

      for (AbstractTeam team : this.teamMap.values()) {
         team.saveIfNeeded(directory, this.server.registryAccess());
      }
   }

   private void tryCreateDir(Path path) {
      try {
         Files.createDirectories(path);
      } catch (Exception ex) {
         FTBTeams.LOGGER.error("can't create directory {}: {} {}", path, ex.getClass().getName(), ex.getMessage());
      }
   }

   public SNBTCompoundTag serializeNBT() {
      SNBTCompoundTag nbt = new SNBTCompoundTag();
      nbt.putString("id", this.getId().toString());
      nbt.put("extra", this.extraData);
      nbt.put("chat_redirected", (Tag)Util.make(new ListTag(), l -> this.chatRedirected.forEach(id -> l.add(StringTag.valueOf(id.toString())))));
      return nbt;
   }

   private PartyTeam createPartyTeamInternal(UUID playerId, @Nullable ServerPlayer player, String name) {
      PartyTeam team = new PartyTeam(this, UUID.randomUUID());
      team.owner = playerId;
      this.teamMap.put(team.id, team);
      team.setProperty(TeamProperties.DISPLAY_NAME, name.isEmpty() ? FTBTUtils.getDefaultPartyName(this.server, playerId, player) : name);
      team.setProperty(TeamProperties.COLOR, FTBTUtils.randomColor());
      team.onCreated(player, playerId);
      return team;
   }

   private PlayerTeam createPlayerTeam(UUID playerId, String playerName) {
      PlayerTeam team = new PlayerTeam(this, playerId);
      team.setPlayerName(playerName);
      team.setProperty(TeamProperties.DISPLAY_NAME, playerName);
      team.setProperty(TeamProperties.COLOR, FTBTUtils.randomColor());
      team.addMember(playerId, TeamRank.OWNER);
      return team;
   }

   public void playerLoggedIn(@Nullable ServerPlayer player, UUID id, String name) {
      PlayerTeam team = this.knownPlayers.get(id);
      boolean syncToAll = false;
      FTBTeams.LOGGER.debug("player {} logged in, player team = {}", id, team);
      if (team == null) {
         FTBTeams.LOGGER.debug("creating new player team for player {}", id);
         team = this.createPlayerTeam(id, name);
         this.teamMap.put(id, team);
         this.knownPlayers.put(id, team);
         team.onCreated(player, id);
         syncToAll = true;
         team.onPlayerChangeTeam(null, id, player, false);
         FTBTeams.LOGGER.debug("  - team created");
      } else if (!team.getPlayerName().equals(name)) {
         FTBTeams.LOGGER.debug("updating player name: {} -> {}", team.getPlayerName(), name);
         team.setPlayerName(name);
         team.markDirty();
         this.markDirty();
         syncToAll = true;
      }

      FTBTeams.LOGGER.debug("syncing player team data, all = {}", syncToAll);
      if (player != null) {
         this.syncAllToPlayer(player, team.getEffectiveTeam());
      }

      if (syncToAll) {
         this.syncToAll(team.getEffectiveTeam());
      }

      FTBTeams.LOGGER.debug("updating team presence");
      team.setOnline(true);
      team.updatePresence();
      if (player != null) {
         FTBTeams.LOGGER.debug("sending team login event for {}...", player.getUUID());
         ((Consumer)TeamEvent.PLAYER_LOGGED_IN.invoker()).accept(new PlayerLoggedInAfterTeamEvent(team.getEffectiveTeam(), player));
         FTBTeams.LOGGER.debug("team login event for {} sent", player.getUUID());
      }
   }

   public void playerLoggedOut(ServerPlayer player) {
      PlayerTeam team = this.knownPlayers.get(player.getUUID());
      if (team != null) {
         team.setOnline(false);
         team.updatePresence();
      }
   }

   public void syncAllToPlayer(ServerPlayer player, AbstractTeam selfTeam) {
      ClientTeamManagerImpl manager = ClientTeamManagerImpl.forSyncing(this, this.teamMap.values());
      NetworkHelper.sendTo(player, new SyncTeamsMessage(manager.setSelfTeamId(selfTeam.id), selfTeam.getTeamId(), true));
      NetworkHelper.sendTo(player, SyncMessageHistoryMessage.forTeam(selfTeam));
      NetworkHelper.sendTo(player, new ToggleChatResponseMessage(this.isChatRedirected(player)));
      this.server.getPlayerList().sendPlayerPermissionLevel(player);
   }

   public void syncToAll(Team... teams) {
      if (teams.length != 0) {
         ClientTeamManagerImpl manager = ClientTeamManagerImpl.forSyncing(this, Arrays.stream(teams).toList());

         for (ServerPlayer player : this.server.getPlayerList().getPlayers()) {
            this.getTeamForPlayer(player).ifPresent(selfTeam -> {
               NetworkHelper.sendTo(player, new SyncTeamsMessage(manager.setSelfTeamId(selfTeam.getTeamId()), selfTeam.getTeamId(), false));
               if (teams.length > 1) {
                  NetworkHelper.sendTo(player, SyncMessageHistoryMessage.forTeam(selfTeam));
               }
            });
         }
      }
   }

   @Override
   public Team createPartyTeam(ServerPlayer player, String name, @Nullable String description, @Nullable Color4I color) throws CommandSyntaxException {
      return this.createParty(player.getUUID(), player, name, description, color);
   }

   @Override
   public Team createServerTeam(CommandSourceStack source, String name, @Nullable String description, @Nullable Color4I color, @Nullable UUID teamUUID) throws CommandSyntaxException {
      if (name.length() < 3) {
         throw TeamArgument.NAME_TOO_SHORT.create();
      }

      if (teamUUID != null && this.getTeamByID(teamUUID).isPresent()) {
         throw TeamArgument.TEAM_ALREADY_EXISTS.create(teamUUID.toString());
      }

      ServerPlayer player = source.getPlayer();
      UUID ownerId = player == null ? Util.NIL_UUID : player.getUUID();
      ServerTeam team = new ServerTeam(this, Objects.requireNonNullElse(teamUUID, UUID.randomUUID()));
      this.teamMap.put(team.id, team);
      team.setProperty(TeamProperties.DISPLAY_NAME, name);
      if (description != null) {
         team.setProperty(TeamProperties.DESCRIPTION, description);
      }

      if (color != null) {
         team.setProperty(TeamProperties.COLOR, color);
      }

      team.onCreated(player, ownerId);
      source.sendSuccess(() -> Component.translatable("ftbteams.message.created_server_team", new Object[]{team.getName()}), true);
      this.syncToAll(team);
      return team;
   }

   @Override
   public void setChatRedirected(ServerPlayer player, boolean redirect) {
      if (redirect && this.chatRedirected.add(player.getUUID()) || !redirect && this.chatRedirected.remove(player.getUUID())) {
         NetworkHelper.sendTo(player, new ToggleChatResponseMessage(redirect));
         this.shouldSave = true;
      }
   }

   @Override
   public boolean isChatRedirected(ServerPlayer player) {
      return this.chatRedirected.contains(player.getUUID());
   }

   public PartyTeam createParty(ServerPlayer player, String name) throws CommandSyntaxException {
      return this.createParty(player.getUUID(), player, name, null, null);
   }

   public PartyTeam createParty(UUID playerId, @Nullable ServerPlayer player, String name, @Nullable String description, @Nullable Color4I color) throws CommandSyntaxException {
      if (player != null && !FTBTUtils.canPlayerUseCommand(player, "ftbteams.party.create")) {
         throw TeamArgument.NO_PERMISSION.create();
      }

      Team oldTeam = this.getTeamForPlayerID(playerId).orElseThrow(() -> TeamArgument.TEAM_NOT_FOUND.create(playerId));
      if (oldTeam instanceof PlayerTeam playerTeam) {
         PartyTeam team = this.createPartyTeamInternal(playerId, player, name);
         if (description != null) {
            team.setProperty(TeamProperties.DESCRIPTION, description);
         }

         if (color != null) {
            team.setProperty(TeamProperties.COLOR, color);
         }

         team.copyExtraData(playerTeam);
         playerTeam.setEffectiveTeam(team);
         Component playerName = (Component)(player != null ? player.getName() : Component.literal(playerId.toString()));
         team.addMember(playerId, TeamRank.OWNER);
         team.sendMessage(Util.NIL_UUID, Component.translatable("ftbteams.message.joined", new Object[]{playerName}).withStyle(ChatFormatting.YELLOW));
         team.markDirty();
         ServerConfig.limitedLives()
            .ifPresent(
               lives -> {
                  team.setProperty(TeamProperties.LIVES_REMAINING, (Integer)ServerConfig.LIMITED_LIVES.get());
                  team.sendMessage(
                     Util.NIL_UUID, Component.translatable("ftbteams.message.limited_lives", new Object[]{lives, lives}).withStyle(ChatFormatting.GOLD)
                  );
               }
            );
         playerTeam.removeMember(playerId);
         playerTeam.markDirty();
         playerTeam.updatePresence();
         this.syncToAll(team, playerTeam);
         team.onPlayerChangeTeam(playerTeam, playerId, player, false);
         return team;
      } else {
         throw TeamArgument.ALREADY_IN_PARTY.create();
      }
   }

   public Component getPlayerName(@Nullable UUID id) {
      if (id != null && !id.equals(Util.NIL_UUID)) {
         PlayerTeam team = this.knownPlayers.get(id);
         return Component.literal(team == null ? "Unknown" : team.getPlayerName()).withStyle(ChatFormatting.YELLOW);
      } else {
         return Component.literal("System").withStyle(ChatFormatting.LIGHT_PURPLE);
      }
   }

   @Override
   public CompoundTag getExtraData() {
      return this.extraData;
   }

   void deleteTeam(AbstractTeam team) {
      this.teamMap.remove(team.getId());
      this.markDirty();
      this.saveNow();
      this.tryDeleteTeamFile(team.getId() + ".snbt", team.getType().getSerializedName());
   }

   private void tryDeleteTeamFile(String teamFileName, String subfolderName) {
      Path deletedPath = this.getServer().getWorldPath(FOLDER_NAME).resolve("deleted");
      Path teamFilePath = this.getServer().getWorldPath(FOLDER_NAME).resolve(subfolderName).resolve(teamFileName);

      try {
         Files.createDirectories(deletedPath);
         Files.move(teamFilePath, deletedPath.resolve(teamFileName));
      } catch (IOException e) {
         FTBTeams.LOGGER.error("can't move {} to {}: {}", teamFileName, deletedPath, e.getMessage());

         try {
            Files.deleteIfExists(teamFilePath);
         } catch (IOException e1) {
            FTBTeams.LOGGER.error("can't delete directory {}: {}", teamFilePath, e1.getMessage());
         }
      }
   }
}
