package dev.ftb.mods.ftbteams.data;

import dev.ftb.mods.ftbteams.api.TeamMessage;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.api.event.ClientTeamPropertiesChangedEvent;
import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import dev.ftb.mods.ftbteams.api.property.TeamProperty;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyCollection;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class ClientTeam extends AbstractTeamBase {
   public static final StreamCodec<RegistryFriendlyByteBuf, ClientTeam> STREAM_CODEC = StreamCodec.of(ClientTeam::toNet, ClientTeam::fromNet);
   private final TeamType type;
   private final UUID ownerID;
   private final boolean toBeRemoved;
   private BooleanSupplier fullSyncSupplier = () -> false;

   private ClientTeam(UUID id, UUID ownerId, TeamType type, boolean toBeRemoved, TeamPropertyCollection properties) {
      super(id, properties);
      this.ownerID = ownerId;
      this.type = type;
      this.toBeRemoved = toBeRemoved;
   }

   public static ClientTeam invalidTeam(AbstractTeam team) {
      return new ClientTeam(team.getId(), Util.NIL_UUID, team.getType(), true, new TeamPropertyCollectionImpl());
   }

   public static ClientTeam copyOf(AbstractTeam team) {
      ClientTeam clientTeam = new ClientTeam(team.id, team.getOwner(), team.getType(), false, team.properties.copy());
      clientTeam.ranks.putAll(team.ranks);
      clientTeam.extraData = team.extraData == null ? null : team.extraData.copy();
      return clientTeam;
   }

   @Override
   public <T> void syncOnePropertyToAll(MinecraftServer server, TeamProperty<T> property, T value) {
   }

   @Override
   public <T> void syncOnePropertyToTeam(TeamProperty<T> property, T value) {
   }

   @Override
   public TeamType getType() {
      return this.type;
   }

   @Override
   public UUID getOwner() {
      return this.ownerID;
   }

   @Override
   public void sendMessage(UUID senderId, String message) {
   }

   @Override
   public void sendMessage(UUID senderId, Component message) {
   }

   @Override
   public List<Component> getTeamInfo() {
      return List.of();
   }

   @Override
   public boolean isClientTeam() {
      return true;
   }

   @Override
   public Collection<ServerPlayer> getOnlineMembers() {
      return List.of();
   }

   @Override
   public boolean isValid() {
      return !this.toBeRemoved();
   }

   @Override
   public boolean isPlayerTeam() {
      return this.type == TeamType.PLAYER;
   }

   @Override
   public boolean isPartyTeam() {
      return this.type == TeamType.PARTY;
   }

   @Override
   public boolean isServerTeam() {
      return this.type == TeamType.SERVER;
   }

   public void setMessageHistory(List<TeamMessage> messages) {
      this.messageHistory.clear();
      this.messageHistory.addAll(messages);
   }

   public boolean toBeRemoved() {
      return this.toBeRemoved;
   }

   public void updateProperties(TeamPropertyCollection newProps) {
      TeamPropertyCollection old = this.properties.copy();
      this.properties.updateFrom(newProps);
      ((Consumer)TeamEvent.CLIENT_PROPERTIES_CHANGED.invoker()).accept(new ClientTeamPropertiesChangedEvent(this, old));
   }

   public void setFullSyncRequired(BooleanSupplier fullSyncSupplier) {
      this.fullSyncSupplier = fullSyncSupplier;
   }

   @NotNull
   private static ClientTeam fromNet(RegistryFriendlyByteBuf buffer) {
      UUID id = buffer.readUUID();
      UUID ownerID = buffer.readBoolean() ? buffer.readUUID() : Util.NIL_UUID;
      TeamType type = (TeamType)buffer.readEnum(TeamType.class);
      boolean mustRemove = buffer.readBoolean();
      TeamPropertyCollection props = (TeamPropertyCollection)TeamPropertyCollectionImpl.STREAM_CODEC.decode(buffer);
      ClientTeam clientTeam = new ClientTeam(id, ownerID, type, mustRemove, props);
      int nMembers = buffer.readVarInt();

      for (int i = 0; i < nMembers; i++) {
         clientTeam.addMember(buffer.readUUID(), (TeamRank)buffer.readEnum(TeamRank.class));
      }

      clientTeam.extraData = buffer.readNbt();
      return clientTeam;
   }

   private static void toNet(RegistryFriendlyByteBuf buffer, ClientTeam team) {
      buffer.writeUUID(team.id);
      boolean hasOwner = !team.ownerID.equals(Util.NIL_UUID);
      buffer.writeBoolean(hasOwner);
      if (hasOwner) {
         buffer.writeUUID(team.ownerID);
      }

      buffer.writeEnum(team.type);
      buffer.writeBoolean(team.toBeRemoved);
      if (team.fullSyncSupplier.getAsBoolean()) {
         team.properties.write(buffer);
      } else {
         team.properties.writeSyncableOnly(buffer);
      }

      buffer.writeVarInt(team.ranks.size());

      for (Entry<UUID, TeamRank> entry : team.ranks.entrySet()) {
         buffer.writeUUID(entry.getKey());
         buffer.writeEnum(entry.getValue());
      }

      buffer.writeNbt(team.extraData);
   }
}
