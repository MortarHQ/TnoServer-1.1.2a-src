package dev.ftb.mods.ftbteams.data;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamMessage;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.api.property.TeamProperties;
import dev.ftb.mods.ftbteams.api.property.TeamProperty;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTeamBase implements Team {
   protected final UUID id;
   protected final TeamPropertyCollectionImpl properties;
   protected final Map<UUID, TeamRank> ranks;
   protected CompoundTag extraData;
   protected final List<TeamMessage> messageHistory;
   private boolean valid;

   protected AbstractTeamBase(UUID id) {
      this(id, new TeamPropertyCollectionImpl());
   }

   protected AbstractTeamBase(UUID id, TeamPropertyCollection properties) {
      this.id = id;
      this.properties = properties instanceof TeamPropertyCollectionImpl p ? p : new TeamPropertyCollectionImpl();
      this.ranks = new HashMap<>();
      this.extraData = new CompoundTag();
      this.messageHistory = new LinkedList<>();
      this.valid = true;
   }

   @Override
   public UUID getId() {
      return this.id;
   }

   @Override
   public UUID getTeamId() {
      return this.id;
   }

   @Override
   public TeamPropertyCollection getProperties() {
      return this.properties;
   }

   public abstract TeamType getType();

   @Override
   public int hashCode() {
      return this.id.hashCode();
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return o instanceof AbstractTeam ? this.id.equals(((AbstractTeam)o).getId()) : false;
      }
   }

   @Override
   public String toString() {
      return this.getShortName();
   }

   @Override
   public CompoundTag getExtraData() {
      return this.extraData;
   }

   @Override
   public <T> T getProperty(TeamProperty<T> property) {
      return this.properties.get(property);
   }

   @Override
   public <T> void setProperty(TeamProperty<T> property, T value) {
      this.properties.set(property, value);
      this.markDirty();
   }

   public String getDisplayName() {
      return this.getProperty(TeamProperties.DISPLAY_NAME);
   }

   public String getDescription() {
      return this.getProperty(TeamProperties.DESCRIPTION);
   }

   public int getColor() {
      return this.getProperty(TeamProperties.COLOR).rgb();
   }

   public boolean isFreeToJoin() {
      return this.getProperty(TeamProperties.FREE_TO_JOIN);
   }

   public int getMaxMessageHistorySize() {
      return this.getProperty(TeamProperties.MAX_MSG_HISTORY_SIZE);
   }

   @Override
   public String getShortName() {
      String s = this.getDisplayName().replaceAll("\\W", "_");
      return (s.length() > 50 ? s.substring(0, 50) : s) + "#" + this.getId().toString().substring(0, 8);
   }

   @Override
   public Component getName() {
      return Component.literal(this.getDisplayName())
         .withStyle(
            Style.EMPTY
               .withColor(this.getType().getColor())
               .withClickEvent(new ClickEvent(Action.RUN_COMMAND, "/ftbteams info " + this.getShortName()))
               .withHoverEvent(new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT, Component.translatable("ftbteams.click_show_info")))
         );
   }

   @Override
   public Component getColoredName() {
      return Component.literal(this.getDisplayName())
         .withStyle(
            this.getProperty(TeamProperties.COLOR)
               .toStyle()
               .withClickEvent(new ClickEvent(Action.RUN_COMMAND, "/ftbteams info " + this.getShortName()))
               .withHoverEvent(new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT, Component.translatable("ftbteams.click_show_info")))
         );
   }

   @Override
   public void markDirty() {
   }

   @Override
   public TeamRank getRankForPlayer(UUID playerId) {
      TeamRank rank = this.ranks.get(playerId);
      if (rank != null) {
         return rank;
      } else {
         return this.isFreeToJoin() ? TeamRank.INVITED : TeamRank.NONE;
      }
   }

   public boolean isMember(UUID uuid) {
      return this.getRankForPlayer(uuid).isMemberOrBetter();
   }

   @Override
   public Map<UUID, TeamRank> getPlayersByRank(TeamRank minRank) {
      if (minRank == TeamRank.NONE) {
         return Collections.unmodifiableMap(this.ranks);
      }

      Map<UUID, TeamRank> map = new HashMap<>();
      this.ranks.forEach((id, rank) -> {
         if (rank.isAtLeast(minRank)) {
            map.put(id, rank);
         }
      });
      return Collections.unmodifiableMap(map);
   }

   @Override
   public String getTypeTranslationKey() {
      return "ftbteams.team_type." + this.getType().getSerializedName();
   }

   @Override
   public Set<UUID> getMembers() {
      return this.getPlayersByRank(TeamRank.MEMBER).keySet();
   }

   @Override
   public Team createParty(String description, @Nullable Color4I color) {
      if (this instanceof PlayerTeam playerTeam) {
         UUID var7 = playerTeam.getId();
         FTBTeamsAPI.api().getManager().getTeamForPlayerID(var7).ifPresent(team -> {
            if (team instanceof PartyTeam) {
               throw new IllegalStateException("player " + var7 + " is already in a party team: " + team.getTeamId());
            }
         });
         if (color == null) {
            color = FTBTUtils.randomColor();
         }

         MinecraftServer server = TeamManagerImpl.INSTANCE.getServer();
         ServerPlayer player = server.getPlayerList().getPlayer(var7);
         return playerTeam.createParty(var7, player, FTBTUtils.getDefaultPartyName(server, var7, player), description, color.rgb(), Set.of());
      } else {
         throw new IllegalStateException("team is not a player team: " + this.getTeamId());
      }
   }

   public boolean isAllyOrBetter(UUID profile) {
      return this.getRankForPlayer(profile).isAllyOrBetter();
   }

   public boolean isOfficerOrBetter(UUID profile) {
      return this.getRankForPlayer(profile).isOfficerOrBetter();
   }

   public boolean isInvited(UUID profile) {
      return this.getRankForPlayer(profile).isInvitedOrBetter();
   }

   public void addMessage(TeamMessage message) {
      this.addMessages(List.of(message));
   }

   public void addMessages(Collection<TeamMessage> messages) {
      this.messageHistory.addAll(messages);

      while (this.messageHistory.size() > this.getMaxMessageHistorySize()) {
         this.messageHistory.removeFirst();
      }

      this.markDirty();
   }

   @Override
   public List<TeamMessage> getMessageHistory() {
      return Collections.unmodifiableList(this.messageHistory);
   }

   public void addMember(UUID id, TeamRank rank) {
      this.ranks.put(id, rank);
   }

   public void removeMember(UUID id) {
      this.ranks.remove(id);
   }

   @Override
   public boolean isValid() {
      return this.valid;
   }

   public void invalidateTeam() {
      this.valid = false;
   }
}
