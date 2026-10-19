package cc.sighs.oelib.network.util;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.data.DataManager;
import cc.sighs.oelib.network.api.INetworkPacket;
import cc.sighs.oelib.network.chunk.GenericChunkPacket;
import io.netty.buffer.Unpooled;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class NetworkUtil {
   private NetworkUtil() {
   }

   public static boolean isLogicalServer() {
      return DataManager.getServer() != null;
   }

   public static void sendChunkedPacket(byte[] data, ResourceLocation typeId, Iterable<ServerPlayer> players, int chunkSize) {
      try {
         forEachChunk(data, typeId, chunkSize, chunk -> {
            for (ServerPlayer player : players) {
               chunk.sendTo(player);
            }
         });
      } catch (Exception e) {
         OELib.LOGGER.error("Failed to send chunked packet {}: {}", new Object[]{typeId, e.getMessage(), e});
      }
   }

   public static void sendChunkedPacketToAll(byte[] data, ResourceLocation typeId, int chunkSize) {
      try {
         forEachChunk(data, typeId, chunkSize, INetworkPacket::sendToAll);
      } catch (Exception e) {
         OELib.LOGGER.error("Failed broadcast chunked {}: {}", new Object[]{typeId, e.getMessage(), e});
      }
   }

   public static void sendChunkedPacketToServer(byte[] data, ResourceLocation typeId, int chunkSize) {
      try {
         forEachChunk(data, typeId, chunkSize, INetworkPacket::sendToServer);
      } catch (Exception e) {
         OELib.LOGGER.error("Failed client chunked {}: {}", new Object[]{typeId, e.getMessage(), e});
      }
   }

   public static <T extends INetworkPacket<T>> byte[] encodePacket(T packet, NetworkUtil.PacketInfo<T> packetInfo, RegistryFriendlyByteBuf buf) {
      packetInfo.codec().encode(buf, packet);
      byte[] data = new byte[buf.readableBytes()];
      buf.readBytes(data);
      return data;
   }

   public static <T extends INetworkPacket<T>> void sendWithChunking(
      T packet, NetworkUtil.PacketInfo<T> packetInfo, RegistryFriendlyByteBuf buf, int threshold, Runnable directSendAction, Consumer<byte[]> chunkedSendAction
   ) {
      try {
         byte[] data = encodePacket(packet, packetInfo, buf);
         if (data.length <= threshold) {
            directSendAction.run();
         } else {
            chunkedSendAction.accept(data);
         }
      } finally {
         buf.release();
      }
   }

   public static RegistryFriendlyByteBuf createClientBuffer() {
      Minecraft client = Minecraft.getInstance();
      if (client.level == null) {
         throw new IllegalStateException("Cannot create buffer: client level is null");
      } else {
         return new RegistryFriendlyByteBuf(Unpooled.buffer(), client.level.registryAccess());
      }
   }

   public static RegistryFriendlyByteBuf createServerBuffer(ServerPlayer player) {
      return new RegistryFriendlyByteBuf(Unpooled.buffer(), player.registryAccess());
   }

   public static RegistryFriendlyByteBuf createBufferFromFirstPlayer(Collection<ServerPlayer> players) {
      if (players.isEmpty()) {
         throw new IllegalStateException("Cannot create buffer: player collection is empty");
      }

      ServerPlayer firstPlayer = players.iterator().next();
      return createServerBuffer(firstPlayer);
   }

   public static void forEachChunk(byte[] data, ResourceLocation typeId, int chunkSize, Consumer<GenericChunkPacket> consumer) {
      UUID sessionId = UUID.randomUUID();
      int totalChunks = (int)Math.ceil((double)data.length / chunkSize);
      OELib.LOGGER.info("Chunking {} into {} chunks for session {} ({} bytes)", new Object[]{typeId, totalChunks, sessionId, data.length});

      for (int i = 0; i < totalChunks; i++) {
         int start = i * chunkSize;
         int end = Math.min(start + chunkSize, data.length);
         byte[] chunkData = Arrays.copyOfRange(data, start, end);
         GenericChunkPacket chunk = new GenericChunkPacket(sessionId, data.length, (short)i, (short)totalChunks, typeId, chunkData);
         consumer.accept(chunk);
      }
   }

   public record PacketInfo<T extends INetworkPacket<T> & CustomPacketPayload>(Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
   }
}
