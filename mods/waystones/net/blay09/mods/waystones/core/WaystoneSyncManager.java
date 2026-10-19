package net.blay09.mods.waystones.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.network.message.KnownWaystonesMessage;
import net.blay09.mods.waystones.network.message.PlayerWaystoneCooldownsMessage;
import net.blay09.mods.waystones.network.message.SortingIndexMessage;
import net.blay09.mods.waystones.network.message.UpdateWaystoneMessage;
import net.blay09.mods.waystones.network.message.WaystoneRemovedMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class WaystoneSyncManager {
   public static void sendWaystoneUpdateToAll(@Nullable MinecraftServer server, Waystone waystone) {
      if (server != null) {
         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            sendWaystoneUpdate(player, waystone);
            sendActivatedWaystones(player);
         }
      }
   }

   public static void sendWaystoneRemovalToAll(@Nullable MinecraftServer server, Waystone waystone, boolean wasDestroyed) {
      if (server != null) {
         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            sendWaystoneRemoval(player, waystone, wasDestroyed);
         }
      }
   }

   public static void sendSortingIndex(Player player) {
      List<UUID> sortingIndex = PlayerWaystoneManager.getSortingIndex(player);
      Balm.getNetworking().sendTo(player, new SortingIndexMessage(sortingIndex));
   }

   public static void sendActivatedWaystones(Player player) {
      Collection<Waystone> waystones = PlayerWaystoneManager.getActivatedWaystones(player);
      Balm.getNetworking().sendTo(player, new KnownWaystonesMessage(WaystoneTypes.WAYSTONE, waystones));
   }

   public static void sendWaystonesOfType(ResourceLocation waystoneType, ServerPlayer player) {
      List<Waystone> warpPlates = WaystoneManagerImpl.get(player.server).getWaystonesByType(waystoneType).collect(Collectors.toList());
      Balm.getNetworking().sendTo(player, new KnownWaystonesMessage(waystoneType, warpPlates));
   }

   public static void sendWaystoneUpdate(Player player, Waystone waystone) {
      if (!waystone.getWaystoneType().equals(WaystoneTypes.WAYSTONE) || PlayerWaystoneManager.isWaystoneActivated(player, waystone)) {
         Balm.getNetworking().sendTo(player, new UpdateWaystoneMessage(waystone));
      }
   }

   public static void sendWaystoneRemoval(Player player, Waystone waystone, boolean wasDestroyed) {
      if (!waystone.getWaystoneType().equals(WaystoneTypes.WAYSTONE) || PlayerWaystoneManager.isWaystoneActivated(player, waystone)) {
         Balm.getNetworking().sendTo(player, new WaystoneRemovedMessage(waystone.getWaystoneType(), waystone.getWaystoneUid(), wasDestroyed));
      }
   }

   public static void sendWaystoneCooldowns(Player player) {
      Map<ResourceLocation, Long> cooldowns = PlayerWaystoneManager.getCooldowns(player);
      Balm.getNetworking().sendTo(player, new PlayerWaystoneCooldownsMessage(cooldowns));
   }
}
