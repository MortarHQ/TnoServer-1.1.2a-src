package dev.ftb.mods.ftbteams.data;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import dev.ftb.mods.ftbteams.net.UpdatePresenceMessage;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class PlayerTeam extends AbstractTeam {
   private String playerName = "";
   private boolean online = false;
   private AbstractTeam effectiveTeam = this;

   public PlayerTeam(TeamManagerImpl manager, UUID id) {
      super(manager, id);
   }

   @Override
   public UUID getTeamId() {
      return this.effectiveTeam.getId();
   }

   @Override
   public TeamType getType() {
      return TeamType.PLAYER;
   }

   @Override
   public boolean isPlayerTeam() {
      return true;
   }

   public String getPlayerName() {
      return this.playerName;
   }

   public void setPlayerName(String playerName) {
      this.playerName = playerName;
   }

   public boolean isOnline() {
      return this.online;
   }

   public void setOnline(boolean online) {
      this.online = online;
   }

   public AbstractTeam getEffectiveTeam() {
      return this.effectiveTeam;
   }

   public void setEffectiveTeam(AbstractTeam effectiveTeam) {
      this.effectiveTeam = effectiveTeam;
   }

   @Override
   protected void serializeExtraNBT(CompoundTag tag) {
      tag.putString("player_name", this.playerName);
   }

   @Override
   public void deserializeNBT(CompoundTag tag, Provider provider) {
      super.deserializeNBT(tag, provider);
      this.playerName = tag.getString("player_name");
   }

   @Nullable
   public ServerPlayer getPlayer() {
      return FTBTUtils.getPlayerByUUID(this.manager.getServer(), this.id);
   }

   @Override
   public TeamRank getRankForPlayer(UUID playerId) {
      return playerId.equals(this.id) ? TeamRank.OWNER : super.getRankForPlayer(playerId);
   }

   @Override
   public List<ServerPlayer> getOnlineMembers() {
      ServerPlayer p = this.getPlayer();
      return p == null ? Collections.emptyList() : Collections.singletonList(p);
   }

   public void updatePresence() {
      NetworkHelper.sendToAll(this.manager.getServer(), new UpdatePresenceMessage(this.createClientPlayer()));
   }

   public Team createParty(UUID playerId, @Nullable ServerPlayer player, String name, String description, int color, Set<GameProfile> invited) {
      try {
         PartyTeam team = this.manager.createParty(playerId, player, name, description, Color4I.rgb(color));
         if (player != null) {
            team.invite(player, invited);
         }

         return team;
      } catch (CommandSyntaxException ex) {
         if (player != null) {
            player.displayClientMessage(Component.literal(ex.getMessage()).withStyle(ChatFormatting.RED), false);
         }

         return null;
      }
   }

   public boolean hasTeam() {
      return this.effectiveTeam != this;
   }

   public KnownClientPlayer createClientPlayer() {
      return new KnownClientPlayer(
         this.getId(), this.getPlayerName(), this.isOnline(), this.getTeamId(), new GameProfile(this.getId(), this.getPlayerName()), this.getExtraData()
      );
   }
}
