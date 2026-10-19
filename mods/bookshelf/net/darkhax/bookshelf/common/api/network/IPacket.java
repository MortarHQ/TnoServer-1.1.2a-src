package net.darkhax.bookshelf.common.api.network;

import net.darkhax.bookshelf.common.api.PhysicalSide;
import net.darkhax.bookshelf.common.api.annotation.OnlyFor;
import net.darkhax.bookshelf.common.api.service.Services;
import net.darkhax.bookshelf.common.impl.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.jetbrains.annotations.Nullable;

public interface IPacket<T extends CustomPacketPayload> {
   Type<T> type();

   StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();

   Destination destination();

   void handle(@Nullable ServerPlayer var1, boolean var2, T var3);

   default void toPlayer(ServerPlayer recipient, T payload) {
      if (!this.destination().handledByClient()) {
         Constants.LOG
            .error(
               "Attempted to send invalid packet {} to client! Class: {} Destination: {} Payload: {}",
               new Object[]{this.type().id(), this.getClass(), this.destination(), payload.toString()}
            );
         throw new IllegalStateException("Attempted to send invalid packet " + this.type().id() + " to client!");
      }

      Services.NETWORK.sendToPlayer(recipient, payload);
   }

   default void toAllPlayers(ServerLevel level, T payload) {
      this.toAllPlayers(level.getServer(), payload);
   }

   default void toAllPlayers(MinecraftServer server, T payload) {
      this.toAllPlayers(server.getPlayerList(), payload);
   }

   default void toAllPlayers(PlayerList playerList, T payload) {
      for (ServerPlayer player : playerList.getPlayers()) {
         this.toPlayer(player, payload);
      }
   }

   @OnlyFor(PhysicalSide.CLIENT)
   default void toServer(T payload) {
      if (!this.destination().handledByServer()) {
         Constants.LOG
            .error(
               "Attempted to send invalid packet {} to server! Class: {} Destination: {} Payload: {}",
               new Object[]{this.type().id(), this.getClass(), this.destination(), payload.toString()}
            );
         throw new IllegalStateException("Attempted to send invalid packet " + this.type().id() + " to server!");
      }

      if (Minecraft.getInstance().getConnection() == null) {
         Constants.LOG.error("Attempted to send packet {} before a connection to a server has been established!", this.type().id());
         throw new IllegalStateException("Attempted to send packet " + this.type().id() + " before being connected to a server!");
      }

      Services.NETWORK.sendToServer(payload);
   }
}
