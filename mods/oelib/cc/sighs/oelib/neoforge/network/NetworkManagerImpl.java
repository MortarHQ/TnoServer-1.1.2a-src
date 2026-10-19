package cc.sighs.oelib.neoforge.network;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.neoforge.data.DataManager;
import cc.sighs.oelib.network.api.INetworkPacket;
import cc.sighs.oelib.network.api.NetworkAutoRegistration;
import cc.sighs.oelib.network.api.NetworkPacket;
import cc.sighs.oelib.network.api.NetworkPacketTypes;
import cc.sighs.oelib.network.api.Side;
import cc.sighs.oelib.network.chunk.GenericChunkPacket;
import cc.sighs.oelib.network.serialization.NetworkSerialization;
import cc.sighs.oelib.network.spi.INetworkManager;
import cc.sighs.oelib.network.util.NetworkUtil;
import io.netty.buffer.Unpooled;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.connection.ConnectionType;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "oelib")
public class NetworkManagerImpl implements INetworkManager {
   private static final String PROTOCOL_VERSION = "1";
   private static final Map<Type<?>, NetworkUtil.PacketInfo<?>> registeredPackets = new ConcurrentHashMap<>();

   @SubscribeEvent
   public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("oelib").versioned("1");
      NetworkManagerImpl impl = new NetworkManagerImpl();

      for (Class<? extends INetworkPacket<?>> packetClass : NetworkAutoRegistration.findAllAnnotatedPackets()) {
         impl.registerAnnotated(packetClass, registrar);
      }
   }

   public static int getRegisteredPacketCount() {
      return registeredPackets.size();
   }

   public static Set<Type<?>> getRegisteredPacketTypes() {
      return new HashSet<>(registeredPackets.keySet());
   }

   private static RegistryFriendlyByteBuf createNeoForgeBuffer(RegistryAccess registries) {
      return new RegistryFriendlyByteBuf(Unpooled.buffer(), registries, ConnectionType.NEOFORGE);
   }

   private <T extends INetworkPacket<T>> void handle(T packet, IPayloadContext context) {
      context.enqueueWork(() -> packet.handle(new NeoForgeNetworkContext(context)));
   }

   private <T extends INetworkPacket<T> & CustomPacketPayload> void registerAnnotated(Class<? extends INetworkPacket<?>> rawClass, PayloadRegistrar registrar) {
      Class<T> clazz = (Class<T>)rawClass;
      NetworkPacket meta = clazz.getAnnotation(NetworkPacket.class);
      if (meta != null && clazz.isRecord()) {
         Type<T> type = NetworkPacketTypes.typeOf(clazz);
         StreamCodec<RegistryFriendlyByteBuf, T> codec = NetworkSerialization.autoCodec(clazz);
         registeredPackets.put(type, new NetworkUtil.PacketInfo(type, codec));
         Side side = meta.side();
         OELib.LOGGER.debug("Registering packet: {} | Side: {} | Type ID: {}", new Object[]{clazz.getSimpleName(), side, type.id()});
         switch (side) {
            case CLIENT:
               registrar.playToClient(type, codec, (x$0, x$1) -> this.handle((T)x$0, x$1));
               break;
            case SERVER:
               registrar.playToServer(type, codec, (x$0, x$1) -> this.handle((T)x$0, x$1));
               break;
            case BOTH:
               registrar.playBidirectional(type, codec, (x$0, x$1) -> this.handle((T)x$0, x$1));
         }
      }
   }

   @Override
   public <T extends INetworkPacket<T> & CustomPacketPayload> void sendToPlayer(T packet, ServerPlayer player) {
      int threshold = NetworkAutoRegistration.getChunkThreshold(packet.getClass());
      if (threshold > 0) {
         this.sendWithChunking(packet, Collections.singletonList(player), threshold);
      } else {
         PacketDistributor.sendToPlayer(player, packet, new CustomPacketPayload[0]);
      }
   }

   @Override
   public <T extends INetworkPacket<T> & CustomPacketPayload> void sendToServer(T packet) {
      int threshold = NetworkAutoRegistration.getChunkThreshold(packet.getClass());
      if (threshold <= 0) {
         PacketDistributor.sendToServer(packet, new CustomPacketPayload[0]);
      } else {
         NetworkUtil.PacketInfo<T> info = (NetworkUtil.PacketInfo<T>)registeredPackets.get(packet.type());
         if (info == null) {
            PacketDistributor.sendToServer(packet, new CustomPacketPayload[0]);
         } else {
            Minecraft client = Minecraft.getInstance();
            if (client.level == null) {
               PacketDistributor.sendToServer(packet, new CustomPacketPayload[0]);
            } else {
               RegistryFriendlyByteBuf buf = createNeoForgeBuffer(client.level.registryAccess());
               NetworkUtil.sendWithChunking(
                  packet,
                  info,
                  buf,
                  threshold,
                  () -> PacketDistributor.sendToServer(packet, new CustomPacketPayload[0]),
                  data -> NetworkUtil.sendChunkedPacketToServer(data, packet.type().id(), threshold)
               );
            }
         }
      }
   }

   @Override
   public <T extends INetworkPacket<T> & CustomPacketPayload> void sendToAll(T packet) {
      MinecraftServer server = DataManager.getCurrentServer();
      int threshold = NetworkAutoRegistration.getChunkThreshold(packet.getClass());
      if (threshold > 0 && server != null) {
         NetworkUtil.PacketInfo<T> info = (NetworkUtil.PacketInfo<T>)registeredPackets.get(packet.type());
         if (info == null) {
            return;
         }

         RegistryFriendlyByteBuf buf = createNeoForgeBuffer(server.registryAccess());

         try {
            byte[] data = NetworkUtil.encodePacket(packet, info, buf);
            NetworkUtil.sendChunkedPacketToAll(data, packet.type().id(), threshold);
         } finally {
            buf.release();
         }
      } else {
         PacketDistributor.sendToAllPlayers(packet, new CustomPacketPayload[0]);
      }
   }

   @Override
   public <T extends INetworkPacket<T> & CustomPacketPayload> void sendToWorld(T packet, ServerLevel level) {
      int threshold = NetworkAutoRegistration.getChunkThreshold(packet.getClass());
      if (threshold <= 0) {
         PacketDistributor.sendToPlayersInDimension(level, packet, new CustomPacketPayload[0]);
      } else {
         NetworkUtil.PacketInfo<T> info = (NetworkUtil.PacketInfo<T>)registeredPackets.get(packet.type());
         if (info == null) {
            PacketDistributor.sendToPlayersInDimension(level, packet, new CustomPacketPayload[0]);
         } else {
            this.broadcastWithChunking(
               packet,
               info,
               level.registryAccess(),
               threshold,
               () -> PacketDistributor.sendToPlayersInDimension(level, packet, new CustomPacketPayload[0]),
               p -> PacketDistributor.sendToPlayersInDimension(level, p, new CustomPacketPayload[0])
            );
         }
      }
   }

   @Override
   public <T extends INetworkPacket<T> & CustomPacketPayload> void sendToNear(T packet, ServerLevel level, Vec3 pos, double radius) {
      int threshold = NetworkAutoRegistration.getChunkThreshold(packet.getClass());
      if (threshold <= 0) {
         PacketDistributor.sendToPlayersNear(level, null, pos.x, pos.y, pos.z, radius, packet, new CustomPacketPayload[0]);
      } else {
         NetworkUtil.PacketInfo<T> info = (NetworkUtil.PacketInfo<T>)registeredPackets.get(packet.type());
         if (info == null) {
            PacketDistributor.sendToPlayersNear(level, null, pos.x, pos.y, pos.z, radius, packet, new CustomPacketPayload[0]);
         } else {
            this.broadcastWithChunking(
               packet,
               info,
               level.registryAccess(),
               threshold,
               () -> PacketDistributor.sendToPlayersNear(level, null, pos.x, pos.y, pos.z, radius, packet, new CustomPacketPayload[0]),
               p -> PacketDistributor.sendToPlayersNear(level, null, pos.x, pos.y, pos.z, radius, p, new CustomPacketPayload[0])
            );
         }
      }
   }

   @Override
   public <T extends INetworkPacket<T> & CustomPacketPayload> void sendToNearExcept(T packet, ServerLevel level, Vec3 pos, double radius, ServerPlayer excluded) {
      int threshold = NetworkAutoRegistration.getChunkThreshold(packet.getClass());
      if (threshold <= 0) {
         PacketDistributor.sendToPlayersNear(level, excluded, pos.x, pos.y, pos.z, radius, packet, new CustomPacketPayload[0]);
      } else {
         NetworkUtil.PacketInfo<T> info = (NetworkUtil.PacketInfo<T>)registeredPackets.get(packet.type());
         if (info == null) {
            PacketDistributor.sendToPlayersNear(level, excluded, pos.x, pos.y, pos.z, radius, packet, new CustomPacketPayload[0]);
         } else {
            this.broadcastWithChunking(
               packet,
               info,
               level.registryAccess(),
               threshold,
               () -> PacketDistributor.sendToPlayersNear(level, excluded, pos.x, pos.y, pos.z, radius, packet, new CustomPacketPayload[0]),
               p -> PacketDistributor.sendToPlayersNear(level, excluded, pos.x, pos.y, pos.z, radius, p, new CustomPacketPayload[0])
            );
         }
      }
   }

   @Override
   public <T extends INetworkPacket<T> & CustomPacketPayload> void sendToTrackingEntity(T packet, Entity entity) {
      int threshold = NetworkAutoRegistration.getChunkThreshold(packet.getClass());
      if (threshold <= 0) {
         PacketDistributor.sendToPlayersTrackingEntity(entity, packet, new CustomPacketPayload[0]);
      } else {
         NetworkUtil.PacketInfo<T> info = (NetworkUtil.PacketInfo<T>)registeredPackets.get(packet.type());
         if (info == null) {
            PacketDistributor.sendToPlayersTrackingEntity(entity, packet, new CustomPacketPayload[0]);
         } else {
            this.broadcastWithChunking(
               packet,
               info,
               entity.level().registryAccess(),
               threshold,
               () -> PacketDistributor.sendToPlayersTrackingEntity(entity, packet, new CustomPacketPayload[0]),
               p -> PacketDistributor.sendToPlayersTrackingEntity(entity, p, new CustomPacketPayload[0])
            );
         }
      }
   }

   @Override
   public <T extends INetworkPacket<T> & CustomPacketPayload> void sendToTrackingEntityAndSelf(T packet, Entity entity) {
      int threshold = NetworkAutoRegistration.getChunkThreshold(packet.getClass());
      if (threshold <= 0) {
         PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, packet, new CustomPacketPayload[0]);
      } else {
         NetworkUtil.PacketInfo<T> info = (NetworkUtil.PacketInfo<T>)registeredPackets.get(packet.type());
         if (info == null) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, packet, new CustomPacketPayload[0]);
         } else {
            this.broadcastWithChunking(
               packet,
               info,
               entity.level().registryAccess(),
               threshold,
               () -> PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, packet, new CustomPacketPayload[0]),
               p -> PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, p, new CustomPacketPayload[0])
            );
         }
      }
   }

   @Override
   public <T extends INetworkPacket<T> & CustomPacketPayload> void sendToTrackingChunk(T packet, ServerLevel level, ChunkPos chunkPos) {
      int threshold = NetworkAutoRegistration.getChunkThreshold(packet.getClass());
      if (threshold <= 0) {
         PacketDistributor.sendToPlayersTrackingChunk(level, chunkPos, packet, new CustomPacketPayload[0]);
      } else {
         NetworkUtil.PacketInfo<T> info = (NetworkUtil.PacketInfo<T>)registeredPackets.get(packet.type());
         if (info == null) {
            PacketDistributor.sendToPlayersTrackingChunk(level, chunkPos, packet, new CustomPacketPayload[0]);
         } else {
            this.broadcastWithChunking(
               packet,
               info,
               level.registryAccess(),
               threshold,
               () -> PacketDistributor.sendToPlayersTrackingChunk(level, chunkPos, packet, new CustomPacketPayload[0]),
               p -> PacketDistributor.sendToPlayersTrackingChunk(level, chunkPos, p, new CustomPacketPayload[0])
            );
         }
      }
   }

   private <T extends INetworkPacket<T> & CustomPacketPayload> void sendWithChunking(T packet, Collection<ServerPlayer> players, int threshold) {
      if (!players.isEmpty()) {
         NetworkUtil.PacketInfo<T> info = (NetworkUtil.PacketInfo<T>)registeredPackets.get(packet.type());
         if (info != null) {
            ServerPlayer firstPlayer = players.iterator().next();
            RegistryFriendlyByteBuf buf = createNeoForgeBuffer(firstPlayer.registryAccess());
            NetworkUtil.sendWithChunking(
               packet,
               info,
               buf,
               threshold,
               () -> players.forEach(p -> PacketDistributor.sendToPlayer(p, packet, new CustomPacketPayload[0])),
               data -> NetworkUtil.sendChunkedPacket(data, packet.type().id(), players, threshold)
            );
         }
      }
   }

   private <T extends INetworkPacket<T> & CustomPacketPayload> void broadcastWithChunking(
      T packet, NetworkUtil.PacketInfo<T> info, RegistryAccess registries, int threshold, Runnable directSender, Consumer<CustomPacketPayload> chunkSender
   ) {
      RegistryFriendlyByteBuf buf = createNeoForgeBuffer(registries);

      try {
         byte[] data = NetworkUtil.encodePacket(packet, info, buf);
         if (data.length <= threshold) {
            directSender.run();
         } else {
            UUID sessionId = UUID.randomUUID();
            int totalChunks = (int)Math.ceil((double)data.length / threshold);

            for (int i = 0; i < totalChunks; i++) {
               int start = i * threshold;
               int end = Math.min(start + threshold, data.length);
               byte[] chunkData = Arrays.copyOfRange(data, start, end);
               GenericChunkPacket chunk = new GenericChunkPacket(sessionId, data.length, (short)i, (short)totalChunks, packet.type().id(), chunkData);
               chunkSender.accept(chunk);
            }
         }
      } finally {
         buf.release();
      }
   }
}
