package com.teamresourceful.resourcefullib.common.network;

import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.Networking;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.neoforge.NetworkImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;

public class Network implements Networking {
   private final Networking networking;
   private final boolean optional;

   public Network(ResourceLocation channel, int protocolVersion) {
      this(channel, protocolVersion, false);
   }

   public Network(ResourceLocation channel, int protocolVersion, boolean optional) {
      this.networking = getNetwork(channel, protocolVersion, optional);
      this.optional = optional;
   }

   @Deprecated
   @ScheduledForRemoval(inVersion = "22.0")
   public Network(String modid, int protocolVersion, String channel) {
      this(modid, protocolVersion, channel, false);
   }

   @Deprecated
   @ScheduledForRemoval(inVersion = "22.0")
   public Network(String modid, int protocolVersion, String channel, boolean optional) {
      this.networking = getNetwork(ResourceLocation.fromNamespaceAndPath(modid, channel), protocolVersion, optional);
      this.optional = optional;
   }

   @Override
   public final <T extends Packet<T>> void register(ClientboundPacketType<T> type) {
      this.networking.register(type);
   }

   @Override
   public final <T extends Packet<T>> void register(ServerboundPacketType<T> type) {
      this.networking.register(type);
   }

   @Override
   public final <T extends Packet<T>> void sendToServer(T message) {
      this.networking.sendToServer(message);
   }

   @Override
   public final <T extends Packet<T>> void sendToPlayer(T message, ServerPlayer player) {
      if (!this.optional || this.canSendToPlayer(player, message.type())) {
         this.networking.sendToPlayer(message, player);
      }
   }

   public final <T extends Packet<T>> void sendToPlayer(T message, Player player) {
      if (player instanceof ServerPlayer serverPlayer) {
         this.sendToPlayer(message, serverPlayer);
      }
   }

   public final <T extends Packet<T>> void sendToPlayers(T message, Collection<? extends Player> players) {
      players.forEach(player -> this.sendToPlayer(message, player));
   }

   public final <T extends Packet<T>> void sendToAllPlayers(T message, MinecraftServer server) {
      this.sendToPlayers(message, server.getPlayerList().getPlayers());
   }

   public final <T extends Packet<T>> void sendToPlayersInLevel(T message, Level level) {
      this.sendToPlayers(message, level.players());
   }

   public final <T extends Packet<T>> void sendToAllLoaded(T message, Level level, BlockPos pos) {
      LevelChunk chunk = level.getChunkAt(pos);
      if (chunk != null && level.getChunkSource() instanceof ServerChunkCache serverCache) {
         serverCache.chunkMap.getPlayers(chunk.getPos(), false).forEach(player -> this.sendToPlayer(message, player));
      }
   }

   public final <T extends Packet<T>> void sendToPlayersInRange(T message, Level level, BlockPos pos, double range) {
      for (Player player : level.players()) {
         if (player.blockPosition().distSqr(pos) <= range) {
            this.sendToPlayer(message, player);
         }
      }
   }

   @Override
   public final boolean canSendToPlayer(ServerPlayer player, PacketType<?> type) {
      return this.networking.canSendToPlayer(player, type);
   }

   public final boolean canSendToPlayer(Player player, PacketType<?> type) {
      return player instanceof ServerPlayer serverPlayer && this.canSendToPlayer(serverPlayer, type);
   }

   @ExpectPlatform
   @Internal
   @Contract(pure = true)
   @Transformed
   public static Networking getNetwork(ResourceLocation channel, int protocolVersion, boolean optional) {
      return NetworkImpl.getNetwork(channel, protocolVersion, optional);
   }
}
