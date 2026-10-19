package xaero.map.message.basic;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.server.player.ServerPlayerData;

public class HandshakePacket {
   public static final int NETWORK_COMPATIBILITY = 3;
   private final int networkVersion;

   public HandshakePacket(int networkVersion) {
      this.networkVersion = networkVersion;
   }

   public HandshakePacket() {
      this(3);
   }

   public void write(FriendlyByteBuf u) {
      u.writeInt(this.networkVersion);
   }

   public static HandshakePacket read(FriendlyByteBuf buffer) {
      return new HandshakePacket(buffer.readInt());
   }

   public static class ClientHandler implements Consumer<HandshakePacket> {
      public void accept(HandshakePacket message) {
         WorldMapSession session = WorldMapSession.getCurrentSession();
         if (session != null) {
            session.getMapProcessor().setServerModNetworkVersion(message.networkVersion);
            WorldMap.messageHandler.sendToServer(new HandshakePacket());
         }
      }
   }

   public static class ServerHandler implements BiConsumer<HandshakePacket, ServerPlayer> {
      public void accept(HandshakePacket message, ServerPlayer player) {
         ServerPlayerData playerData = ServerPlayerData.get(player);
         playerData.setClientModNetworkVersion(message.networkVersion);
      }
   }
}
