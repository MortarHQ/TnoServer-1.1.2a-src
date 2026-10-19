package com.sennecools.tablist;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.sennecools.tablist.config.TabListConfig;
import com.sennecools.tablist.metrics.ServerMetricsResolver;
import com.sennecools.tablist.platform.Services;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team.Visibility;

public class TabListUpdater {
   public static TabListUpdater INSTANCE;
   private static final String TEAM_PREFIX = "tl_";
   private int ticksSinceLastUpdate = 0;
   private final Map<UUID, String> lastSentContent = new ConcurrentHashMap<>();
   private String lastSortMode = null;
   private int headerFrameIndex = 0;
   private int footerFrameIndex = 0;
   private int animationTickCounter = 0;
   private final Map<UUID, double[]> lastPlayerPositions = new ConcurrentHashMap<>();
   private final Map<UUID, Long> lastActivityTime = new ConcurrentHashMap<>();

   public TabListUpdater() {
      INSTANCE = this;
   }

   public void resetState() {
      ServerMetricsResolver.reset();
      this.lastSentContent.clear();
      this.headerFrameIndex = 0;
      this.footerFrameIndex = 0;
      this.animationTickCounter = 0;
      this.ticksSinceLastUpdate = 0;
      this.lastSortMode = null;
   }

   public LiteralArgumentBuilder<CommandSourceStack> buildReloadCommand() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("tablist").requires(source -> source.hasPermission(2)))
         .then(Commands.literal("reload").executes(context -> {
            TabListConfig.load();
            this.resetState();
            ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("§aTabList config reloaded."), true);
            return 1;
         }));
   }

   public void onServerTick(MinecraftServer server) {
      if (server != null) {
         this.ticksSinceLastUpdate++;
         int updateIntervalTicks = TabListConfig.updateInterval / 50;
         if (this.ticksSinceLastUpdate >= updateIntervalTicks) {
            this.ticksSinceLastUpdate = 0;
            ServerMetricsResolver.reset();
            this.animationTickCounter++;
            if (this.animationTickCounter >= TabListConfig.animationInterval) {
               this.animationTickCounter = 0;
               List<String> headerFrames = TabListConfig.headerFrames;
               List<String> footerFrames = TabListConfig.footerFrames;
               if (headerFrames != null && headerFrames.size() > 1) {
                  this.headerFrameIndex = (this.headerFrameIndex + 1) % headerFrames.size();
               }

               if (footerFrames != null && footerFrames.size() > 1) {
                  this.footerFrameIndex = (this.footerFrameIndex + 1) % footerFrames.size();
               }
            }

            if (TabListConfig.afkEnabled) {
               this.updateAFKTracking(server);
            }

            this.updateSorting(server);
            server.getPlayerList().getPlayers().forEach(this::refreshPlayerTab);
         }
      }
   }

   private void updateAFKTracking(MinecraftServer server) {
      long now = System.currentTimeMillis();

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         UUID uuid = player.getUUID();
         double x = player.getX();
         double z = player.getZ();
         double yRot = player.getYRot();
         double[] lastPos = this.lastPlayerPositions.get(uuid);
         if (lastPos == null || lastPos[0] != x || lastPos[1] != z || lastPos[2] != yRot) {
            this.lastPlayerPositions.put(uuid, new double[]{x, z, yRot});
            this.lastActivityTime.put(uuid, now);
         } else if (!this.lastActivityTime.containsKey(uuid)) {
            this.lastActivityTime.put(uuid, now);
         }
      }
   }

   public boolean isPlayerAFK(ServerPlayer player) {
      if (!TabListConfig.afkEnabled) {
         return false;
      }

      Long lastActive = this.lastActivityTime.get(player.getUUID());
      return lastActive == null ? false : System.currentTimeMillis() - lastActive >= TabListConfig.afkTimeout * 1000L;
   }

   public void onPlayerChat(ServerPlayer player) {
      if (player != null) {
         this.lastActivityTime.put(player.getUUID(), System.currentTimeMillis());
      }
   }

   private void refreshPlayerTab(ServerPlayer player) {
      List<String> headerFrames = TabListConfig.headerFrames;
      List<String> footerFrames = TabListConfig.footerFrames;
      String headerTemplate = headerFrames != null && !headerFrames.isEmpty() ? headerFrames.get(this.headerFrameIndex % headerFrames.size()) : "";
      String footerTemplate = footerFrames != null && !footerFrames.isEmpty() ? footerFrames.get(this.footerFrameIndex % footerFrames.size()) : "";
      String header = TabListVariables.tablistChars(headerTemplate, player);
      String footer = TabListVariables.tablistChars(footerTemplate, player);
      String combined = header + "\u0000" + footer;
      String previous = this.lastSentContent.put(player.getUUID(), combined);
      if (!combined.equals(previous)) {
         ClientboundTabListPacket packet = new ClientboundTabListPacket(TabListVariables.parseColoredText(header), TabListVariables.parseColoredText(footer));
         player.connection.send(packet);
      }

      Services.PLATFORM.refreshDisplayName(player);
   }

   private void updateSorting(MinecraftServer server) {
      String mode = TabListConfig.sortMode;
      if (mode == null) {
         mode = "NONE";
      }

      mode = mode.toUpperCase();
      Scoreboard scoreboard = server.getScoreboard();
      if ("NONE".equals(mode)) {
         if (this.lastSortMode != null && !"NONE".equals(this.lastSortMode)) {
            this.removeAllTlTeams(scoreboard);
         }

         if (this.lastSortMode == null) {
            this.removeAllTlTeams(scoreboard);
         }

         this.lastSortMode = mode;
      } else {
         if (this.lastSortMode == null) {
            this.removeAllTlTeams(scoreboard);
         }

         List<ServerPlayer> players = new ArrayList<>(server.getPlayerList().getPlayers());
         Map<UUID, Integer> rankPowers = new HashMap<>();
         if ("RANK".equals(mode)) {
            for (ServerPlayer player : players) {
               rankPowers.put(player.getUUID(), TabListVariables.getPlayerRankPower(player));
            }
         }

         Comparator<ServerPlayer> comparator;
         if ("RANK".equals(mode)) {
            comparator = Comparator.<ServerPlayer>comparingInt(p -> rankPowers.get(p.getUUID()))
               .reversed()
               .thenComparing(p -> p.getGameProfile().getName(), String.CASE_INSENSITIVE_ORDER);
         } else {
            comparator = Comparator.comparing(p -> p.getGameProfile().getName(), String.CASE_INSENSITIVE_ORDER);
         }

         players.sort(comparator);
         int index = 0;
         List<String> neededTeams = new ArrayList<>();

         for (ServerPlayer player : players) {
            String playerName = player.getGameProfile().getName();
            PlayerTeam currentTeam = scoreboard.getPlayersTeam(playerName);
            if (currentTeam == null || currentTeam.getName().startsWith("tl_")) {
               String teamName = String.format("%s%05d", "tl_", ++index);
               neededTeams.add(teamName);
               if (currentTeam == null || !currentTeam.getName().equals(teamName)) {
                  if (currentTeam != null) {
                     scoreboard.removePlayerFromTeam(playerName, currentTeam);
                  }

                  PlayerTeam team = scoreboard.getPlayerTeam(teamName);
                  if (team == null) {
                     team = scoreboard.addPlayerTeam(teamName);
                     team.setNameTagVisibility(Visibility.ALWAYS);
                     team.setPlayerPrefix(Component.empty());
                     team.setPlayerSuffix(Component.empty());
                  }

                  scoreboard.addPlayerToTeam(playerName, team);
               }
            }
         }

         for (PlayerTeam team : List.copyOf(scoreboard.getPlayerTeams())) {
            if (team.getName().startsWith("tl_") && !neededTeams.contains(team.getName())) {
               scoreboard.removePlayerTeam(team);
            }
         }

         this.lastSortMode = mode;
      }
   }

   private void removeAllTlTeams(Scoreboard scoreboard) {
      for (PlayerTeam team : List.copyOf(scoreboard.getPlayerTeams())) {
         if (team.getName().startsWith("tl_")) {
            scoreboard.removePlayerTeam(team);
         }
      }
   }

   public void onPlayerLogout(ServerPlayer player) {
      UUID uuid = player.getUUID();
      this.lastSentContent.remove(uuid);
      this.lastPlayerPositions.remove(uuid);
      this.lastActivityTime.remove(uuid);
      MinecraftServer server = player.getServer();
      if (server != null) {
         Scoreboard scoreboard = server.getScoreboard();
         String playerName = player.getGameProfile().getName();
         PlayerTeam team = scoreboard.getPlayersTeam(playerName);
         if (team != null && team.getName().startsWith("tl_")) {
            scoreboard.removePlayerFromTeam(playerName, team);
            if (team.getPlayers().isEmpty()) {
               scoreboard.removePlayerTeam(team);
            }
         }
      }
   }
}
