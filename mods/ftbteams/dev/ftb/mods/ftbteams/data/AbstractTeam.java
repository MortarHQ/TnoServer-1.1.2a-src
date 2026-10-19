package dev.ftb.mods.ftbteams.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftblibrary.snbt.SNBT;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftblibrary.util.TextComponentUtils;
import dev.ftb.mods.ftbteams.FTBTeams;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamMessage;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.api.event.PlayerChangedTeamEvent;
import dev.ftb.mods.ftbteams.api.event.PlayerJoinedPartyTeamEvent;
import dev.ftb.mods.ftbteams.api.event.PlayerLeftPartyTeamEvent;
import dev.ftb.mods.ftbteams.api.event.TeamCreatedEvent;
import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import dev.ftb.mods.ftbteams.api.event.TeamPropertiesChangedEvent;
import dev.ftb.mods.ftbteams.api.property.TeamProperty;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyCollection;
import dev.ftb.mods.ftbteams.net.SendMessageResponseMessage;
import dev.ftb.mods.ftbteams.net.UpdatePropertiesResponseMessage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTeam extends AbstractTeamBase {
   protected final TeamManagerImpl manager;
   private boolean shouldSave;

   public AbstractTeam(TeamManagerImpl manager, UUID id) {
      super(id);
      this.manager = manager;
      this.properties.collectProperties();
   }

   @Override
   public void markDirty() {
      this.shouldSave = true;
      this.manager.nameMap = null;
   }

   public List<ServerPlayer> getOnlineRanked(TeamRank rank) {
      List<ServerPlayer> list = new ArrayList<>();

      for (UUID id : this.getPlayersByRank(rank).keySet()) {
         ServerPlayer player = FTBTUtils.getPlayerByUUID(this.manager.getServer(), id);
         if (player != null) {
            list.add(player);
         }
      }

      return list;
   }

   public List<ServerPlayer> getOnlineMembers() {
      return this.getOnlineRanked(TeamRank.MEMBER);
   }

   void onCreated(@Nullable ServerPlayer player, @NotNull UUID playerId) {
      if (player != null) {
         ((Consumer)TeamEvent.CREATED.invoker()).accept(new TeamCreatedEvent(this, player, playerId));
      }

      this.markDirty();
      this.manager.markDirty();
      this.manager.saveNow();
   }

   void updateCommands(ServerPlayer player) {
      player.getServer().getPlayerList().sendPlayerPermissionLevel(player);
   }

   void onPlayerChangeTeam(@Nullable Team prev, UUID player, @Nullable ServerPlayer p, boolean deleted) {
      ((Consumer)TeamEvent.PLAYER_CHANGED.invoker()).accept(new PlayerChangedTeamEvent(this, prev, player, p));
      if (prev instanceof PartyTeam && this instanceof PlayerTeam) {
         ((Consumer)TeamEvent.PLAYER_LEFT_PARTY.invoker()).accept(new PlayerLeftPartyTeamEvent(prev, this, player, p, deleted));
      } else if (prev instanceof PlayerTeam && p != null) {
         ((Consumer)TeamEvent.PLAYER_JOINED_PARTY.invoker()).accept(new PlayerJoinedPartyTeamEvent(this, prev, p));
      }

      if (deleted && prev != null) {
         ((Consumer)TeamEvent.DELETED.invoker()).accept(new TeamEvent(prev));
      }

      if (p != null) {
         this.updateCommands(p);
      }
   }

   public SNBTCompoundTag serializeNBT(Provider provider) {
      SNBTCompoundTag tag = new SNBTCompoundTag();
      tag.putString("id", this.getId().toString());
      tag.putString("type", this.getType().getSerializedName());
      this.serializeExtraNBT(tag);
      SNBTCompoundTag ranksNBT = new SNBTCompoundTag();

      for (Entry<UUID, TeamRank> entry : this.ranks.entrySet()) {
         ranksNBT.putString(entry.getKey().toString(), entry.getValue().getSerializedName());
      }

      tag.put("ranks", ranksNBT);
      tag.put("properties", this.properties.write(new SNBTCompoundTag()));
      ListTag messageHistoryTag = new ListTag();

      for (TeamMessage msg : this.getMessageHistory()) {
         messageHistoryTag.add(TeamMessageImpl.toNBT(msg, provider));
      }

      tag.put("message_history", messageHistoryTag);
      ((Consumer)TeamEvent.SAVED.invoker()).accept(new TeamEvent(this));
      tag.put("extra", this.extraData);
      return tag;
   }

   protected void serializeExtraNBT(CompoundTag tag) {
   }

   public void deserializeNBT(CompoundTag tag, Provider provider) {
      this.ranks.clear();
      CompoundTag ranksNBT = tag.getCompound("ranks");

      for (String s : ranksNBT.getAllKeys()) {
         this.ranks.put(UUID.fromString(s), (TeamRank)TeamRank.NAME_MAP.get(ranksNBT.getString(s)));
      }

      this.properties.read(tag.getCompound("properties"));
      this.extraData = tag.getCompound("extra");
      this.messageHistory.clear();
      ListTag messageHistoryTag = tag.getList("message_history", 10);

      for (int i = 0; i < messageHistoryTag.size(); i++) {
         this.addMessage(TeamMessageImpl.fromNBT(messageHistoryTag.getCompound(i), provider));
      }

      ((Consumer)TeamEvent.LOADED.invoker()).accept(new TeamEvent(this));
   }

   public <T> int settings(CommandSourceStack source, TeamProperty<T> key, String value) {
      MutableComponent keyc = Component.translatable(key.getTranslationKey("ftbteamsconfig")).withStyle(ChatFormatting.YELLOW);
      if (value.isEmpty()) {
         Component valuec = Component.literal(key.toString(this.getProperty(key))).withStyle(ChatFormatting.AQUA);
         source.sendSuccess(() -> keyc.append(" = ").append(valuec), true);
      } else if (key.isPlayerEditable()) {
         Optional<T> optional = key.fromString(value);
         if (!optional.isPresent()) {
            source.sendFailure(Component.translatable("ftbteams.message.parse_failed", new Object[]{value}));
            return 0;
         }

         TeamPropertyCollection old = this.properties.copy();
         this.setProperty(key, optional.get());
         Component valuec = Component.literal(value).withStyle(ChatFormatting.AQUA);
         source.sendSuccess(() -> Component.translatable("ftbteams.message.set_property", new Object[]{keyc, valuec}), true);
         ((Consumer)TeamEvent.PROPERTIES_CHANGED.invoker()).accept(new TeamPropertiesChangedEvent(this, old));
         this.syncOnePropertyToAll(source.getServer(), key, optional.get());
      } else {
         source.sendFailure(Component.translatable("ftbteams.message.property_not_editable", new Object[]{keyc}));
      }

      return 1;
   }

   public int declineInvitation(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      if (this.getRankForPlayer(player.getUUID()) == TeamRank.INVITED) {
         this.ranks.put(player.getUUID(), TeamRank.ALLY);
         source.sendSuccess(() -> Component.translatable("ftbteams.message.declined"), true);
         this.markDirty();
         this.manager.syncToAll(this);
         return 1;
      } else {
         FTBTeams.LOGGER.warn("ignore invitation decline for player {} to team {} (not invited)", player.getUUID(), this.getId());
         return 0;
      }
   }

   @Override
   public List<Component> getTeamInfo() {
      List<Component> res = new ArrayList<>();
      res.add(
         Component.literal("== ")
            .append(this.getName())
            .append(Component.literal(" [" + this.getType().getSerializedName() + "]").withStyle(this.getType().getColor()))
            .append(" ==")
      );
      res.add(
         Component.translatable("ftbteams.info.id", new Object[]{FTBTUtils.makeCopyableComponent(this.getId().toString()).withStyle(ChatFormatting.YELLOW)})
      );
      res.add(
         Component.translatable("ftbteams.info.short_id", new Object[]{FTBTUtils.makeCopyableComponent(this.getShortName()).withStyle(ChatFormatting.YELLOW)})
      );
      if (this.isPartyTeam()) {
         res.add(
            this.getOwner().equals(Util.NIL_UUID)
               ? Component.translatable("ftbteams.info.owner", new Object[]{Component.translatable("ftbteams.info.owner.none").withStyle(ChatFormatting.GRAY)})
               : Component.translatable("ftbteams.info.owner", new Object[]{this.playerWithId(this.getOwner())})
         );
         res.add(Component.translatable("ftbteams.info.members"));
         if (this.getMembers().isEmpty()) {
            res.add(Component.literal("- ").append(Component.translatable("ftbteams.info.members.none")).withStyle(ChatFormatting.GRAY));
         } else {
            for (UUID member : this.getMembers()) {
               res.add(Component.literal("- ").append(this.playerWithId(member)));
            }
         }
      }

      return res;
   }

   private Component playerWithId(UUID member) {
      return this.manager
         .getPlayerName(member)
         .copy()
         .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(Action.SHOW_TEXT, Component.literal(member.toString()))));
   }

   @Override
   public UUID getOwner() {
      return Util.NIL_UUID;
   }

   @Override
   public void sendMessage(UUID senderId, String message) {
      this.sendMessage(senderId, TextComponentUtils.withLinks(message));
   }

   @Override
   public void sendMessage(UUID from, Component text) {
      this.addMessage(FTBTeamsAPI.api().createMessage(from, text));
      MutableComponent component = Component.literal("<");
      component.append(this.manager.getPlayerName(from));
      component.append(" @");
      component.append(this.getName());
      component.append("> ");
      component.append(text);

      for (ServerPlayer p : this.getOnlineMembers()) {
         p.displayClientMessage(component, false);
         NetworkHelper.sendTo(p, new SendMessageResponseMessage(from, text));
      }

      this.markDirty();
   }

   public void updatePropertiesFrom(TeamPropertyCollection newProperties) {
      TeamPropertyCollection oldProperties = this.properties.copy();
      this.properties.updateFrom(newProperties);
      ((Consumer)TeamEvent.PROPERTIES_CHANGED.invoker()).accept(new TeamPropertiesChangedEvent(this, oldProperties));
      this.markDirty();
   }

   void saveIfNeeded(Path directory, Provider provider) {
      if (this.shouldSave) {
         SNBT.write(directory.resolve(this.getType().getSerializedName() + "/" + this.getId() + ".snbt"), this.serializeNBT(provider));
         this.shouldSave = false;
      }
   }

   void copyExtraData(Team from) {
      this.extraData = from.getExtraData().copy();
   }

   @Override
   public <T> void syncOnePropertyToAll(MinecraftServer server, TeamProperty<T> property, T value) {
      if (property.shouldSyncToAll()) {
         NetworkHelper.sendToAll(server, UpdatePropertiesResponseMessage.oneProperty(this.getId(), property, value));
      }
   }

   @Override
   public <T> void syncOnePropertyToTeam(TeamProperty<T> property, T value) {
      this.getOnlineMembers().forEach(sp -> NetworkHelper.sendTo(sp, UpdatePropertiesResponseMessage.oneProperty(this.getId(), property, value)));
   }
}
