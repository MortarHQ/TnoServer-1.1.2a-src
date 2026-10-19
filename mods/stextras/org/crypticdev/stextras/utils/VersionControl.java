package org.crypticdev.stextras.utils;

import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientPlayerEvent.ClientPlayerJoin;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.PlayerEvent.PlayerQuit;
import dev.architectury.event.events.common.TickEvent.Server;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import io.github.manasmods.manascore.config.ConfigRegistry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.config.STGeneralConfig;
import org.crypticdev.stextras.net.STNetworkCompat;

public final class VersionControl {
   private static final long DEFAULT_VERSION_REPLY_TIMEOUT_MS = 30000L;
   private static final Map<UUID, Long> PENDING_CLIENT_VERSIONS = new HashMap<>();

   public static void registerCommon() {
      VersionControl.ServerVersionPacket.registerCommon();
      VersionControl.ClientVersionPacket.registerServer();
      PlayerEvent.PLAYER_JOIN.register(VersionControl::beginHandshake);
      PlayerEvent.PLAYER_QUIT.register((PlayerQuit)player -> PENDING_CLIENT_VERSIONS.remove(player.getUUID()));
      TickEvent.SERVER_POST.register((Server)server -> {
         long now = System.currentTimeMillis();
         Iterator<Entry<UUID, Long>> iterator = PENDING_CLIENT_VERSIONS.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, Long> entry = iterator.next();
            if (entry.getValue() <= now) {
               ServerPlayer player = server.getPlayerList().getPlayer(entry.getKey());
               iterator.remove();
               if (player != null) {
                  disconnectServer(player, timeoutMessage());
               }
            }
         }
      });
   }

   public static void registerClient() {
      VersionControl.ClientHooks.register();
   }

   public static String currentVersion() {
      return Platform.getOptionalMod("stextras").<String>map(Mod::getVersion).orElse("unknown");
   }

   private static void beginHandshake(ServerPlayer player) {
      if (shouldCheckVersion(player)) {
         if (!NetworkManager.canPlayerReceive(player, VersionControl.ServerVersionPacket.TYPE)) {
            disconnectServer(player, missingClientMessage());
         } else {
            PENDING_CLIENT_VERSIONS.put(player.getUUID(), System.currentTimeMillis() + versionReplyTimeoutMs());
         }
      }
   }

   private static void handleClientVersion(ServerPlayer player, String clientVersion) {
      if (shouldCheckVersion(player)) {
         PENDING_CLIENT_VERSIONS.remove(player.getUUID());
         String serverVersion = currentVersion();
         if (!versionsMatch(serverVersion, clientVersion)) {
            disconnectServer(player, mismatchMessage(serverVersion, clientVersion));
         }
      }
   }

   private static boolean shouldCheckVersion(ServerPlayer player) {
      return player.server.isDedicatedServer() && versionControlEnabled();
   }

   private static boolean versionControlEnabled() {
      STGeneralConfig config = generalConfig();
      return config == null || config.versionControl;
   }

   private static long versionReplyTimeoutMs() {
      STGeneralConfig config = generalConfig();
      return config != null && config.versionCheckTimeOut > 0L ? config.versionCheckTimeOut : 30000L;
   }

   private static STGeneralConfig generalConfig() {
      return (STGeneralConfig)ConfigRegistry.getConfig(STGeneralConfig.class);
   }

   private static boolean versionsMatch(String expected, String received) {
      return normalize(expected).equals(normalize(received));
   }

   private static String normalize(String version) {
      return version == null ? "" : version.trim();
   }

   private static Component mismatchMessage(String serverVersion, String clientVersion) {
      return Component.literal("STExtras version mismatch. Server: " + serverVersion + ", Client: " + clientVersion + ".");
   }

   private static Component missingClientMessage() {
      return Component.literal("STExtras version check failed. Install the matching client version: " + currentVersion() + ".");
   }

   private static Component timeoutMessage() {
      return Component.literal("STExtras version check timed out. Expected client version: " + currentVersion() + ".");
   }

   private static void disconnectServer(ServerPlayer player, Component reason) {
      player.connection.disconnect(reason);
   }

   private VersionControl() {
   }

   private static final class ClientHooks {
      private static void register() {
         VersionControl.ServerVersionPacket.registerClient();
         ClientPlayerEvent.CLIENT_PLAYER_JOIN.register((ClientPlayerJoin)player -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (!minecraft.hasSingleplayerServer() && NetworkManager.canServerReceive(VersionControl.ClientVersionPacket.TYPE)) {
               VersionControl.ClientVersionPacket.sendToServer(VersionControl.currentVersion());
            }
         });
      }

      private static void disconnectClient(Component reason) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.getConnection() != null) {
            minecraft.getConnection().getConnection().disconnect(reason);
         }
      }
   }

   private record ClientVersionPacket(String version) implements CustomPacketPayload {
      private static final Type<VersionControl.ClientVersionPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("stextras", "client_version"));
      private static final StreamCodec<FriendlyByteBuf, VersionControl.ClientVersionPacket> STREAM_CODEC = StreamCodec.of(
         (buf, packet) -> buf.writeUtf(packet.version()), buf -> new VersionControl.ClientVersionPacket(buf.readUtf())
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }

      private static void registerServer() {
         NetworkManager.registerReceiver(NetworkManager.c2s(), TYPE, STREAM_CODEC, (payload, context) -> {
            if (context.getPlayer() instanceof ServerPlayer player) {
               context.queue(() -> VersionControl.handleClientVersion(player, payload.version()));
            }
         });
      }

      private static void sendToServer(String version) {
         NetworkManager.sendToServer(new VersionControl.ClientVersionPacket(version));
      }
   }

   private record ServerVersionPacket(String version) implements CustomPacketPayload {
      private static final Type<VersionControl.ServerVersionPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("stextras", "server_version"));
      private static final StreamCodec<FriendlyByteBuf, VersionControl.ServerVersionPacket> STREAM_CODEC = StreamCodec.of(
         (buf, packet) -> buf.writeUtf(packet.version()), buf -> new VersionControl.ServerVersionPacket(buf.readUtf())
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }

      private static void registerCommon() {
         STNetworkCompat.registerS2CType(TYPE, STREAM_CODEC);
      }

      private static void registerClient() {
         NetworkManager.registerReceiver(NetworkManager.s2c(), TYPE, STREAM_CODEC, (payload, context) -> context.queue(() -> {
            String clientVersion = VersionControl.currentVersion();
            String serverVersion = payload.version();
            if (!VersionControl.versionsMatch(serverVersion, clientVersion)) {
               VersionControl.ClientHooks.disconnectClient(VersionControl.mismatchMessage(serverVersion, clientVersion));
            } else {
               VersionControl.ClientVersionPacket.sendToServer(clientVersion);
            }
         }));
      }
   }
}
