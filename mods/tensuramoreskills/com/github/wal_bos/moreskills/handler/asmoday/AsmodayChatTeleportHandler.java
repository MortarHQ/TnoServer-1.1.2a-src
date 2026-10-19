package com.github.wal_bos.moreskills.handler.asmoday;

import com.mojang.authlib.GameProfile;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ServerChatEvent;

public final class AsmodayChatTeleportHandler {
   private static final Map<UUID, AsmodayChatTeleportHandler.PendingTeleport> PENDING = new ConcurrentHashMap<>();
   private static boolean initialized = false;

   private AsmodayChatTeleportHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.register(AsmodayChatTeleportHandler.class);
      }
   }

   public static void requestPlayer(ServerPlayer player, ManasSkillInstance instance, int mode) {
      PENDING.put(
         player.getUUID(),
         new AsmodayChatTeleportHandler.PendingTeleport(instance, mode, AsmodayChatTeleportHandler.TeleportType.PLAYER, player.tickCount + 600)
      );
   }

   public static void requestDimension(ServerPlayer player, ManasSkillInstance instance, int mode) {
      PENDING.put(
         player.getUUID(),
         new AsmodayChatTeleportHandler.PendingTeleport(instance, mode, AsmodayChatTeleportHandler.TeleportType.DIMENSION, player.tickCount + 600)
      );
   }

   @SubscribeEvent
   public static void onChat(ServerChatEvent event) {
      ServerPlayer player = event.getPlayer();
      AsmodayChatTeleportHandler.PendingTeleport pending = PENDING.get(player.getUUID());
      if (pending != null) {
         event.setCanceled(true);
         String message = event.getMessage().getString().trim();
         player.server.execute(() -> handleChat(player, pending, message));
      }
   }

   private static void handleChat(ServerPlayer player, AsmodayChatTeleportHandler.PendingTeleport pending, String message) {
      if (player.isAlive() && !player.hasDisconnected()) {
         AsmodayChatTeleportHandler.PendingTeleport current = PENDING.get(player.getUUID());
         if (current == pending) {
            if (message.equalsIgnoreCase("cancel")) {
               PENDING.remove(player.getUUID());
               player.sendSystemMessage(Component.literal("Space Embodiment closed.").withStyle(ChatFormatting.GRAY));
            } else if (player.tickCount > pending.expiresAt()) {
               PENDING.remove(player.getUUID());
               player.sendSystemMessage(Component.literal("Space Embodiment collapsed before you chose a target.").withStyle(ChatFormatting.RED));
            } else if (pending.type() == AsmodayChatTeleportHandler.TeleportType.PLAYER) {
               handlePlayerTeleport(player, pending, message);
            } else {
               handleDimensionTeleport(player, pending, message);
            }
         }
      } else {
         PENDING.remove(player.getUUID());
      }
   }

   private static void handlePlayerTeleport(ServerPlayer player, AsmodayChatTeleportHandler.PendingTeleport pending, String message) {
      MinecraftServer server = player.server;
      if (message.equalsIgnoreCase("list")) {
         String names = server.getPlayerList()
            .getPlayers()
            .stream()
            .filter(targetx -> targetx != player)
            .<GameProfile>map(Player::getGameProfile)
            .map(profile -> profile.getName())
            .collect(Collectors.joining(", "));
         player.sendSystemMessage(Component.literal(names.isEmpty() ? "No valid online player targets." : "Targets: " + names).withStyle(ChatFormatting.AQUA));
      } else {
         ServerPlayer target = server.getPlayerList().getPlayerByName(message);
         if (target == null) {
            player.sendSystemMessage(Component.literal("No online player named '" + message + "'. Type list or cancel.").withStyle(ChatFormatting.RED));
         } else if (target == player) {
            player.sendSystemMessage(Component.literal("You are already occupying your own space.").withStyle(ChatFormatting.RED));
         } else {
            double lacked = EnergyHelper.isOutOfMagiculeConsuming(player, 2500.0);
            if (lacked > 0.0) {
               player.sendSystemMessage(Component.literal("Not enough magicules to collapse distance.").withStyle(ChatFormatting.RED));
               PENDING.remove(player.getUUID());
            } else {
               if (AsmodayTeleportService.teleportToPlayer(player, target)) {
                  pending.instance().addMasteryPoint(player);
                  pending.instance().setCoolDown(4, pending.mode());
                  pending.instance().markDirty();
                  PENDING.remove(player.getUUID());
               } else {
                  player.sendSystemMessage(Component.literal("Space refused the target location.").withStyle(ChatFormatting.RED));
               }
            }
         }
      }
   }

   private static void handleDimensionTeleport(ServerPlayer player, AsmodayChatTeleportHandler.PendingTeleport pending, String message) {
      MinecraftServer server = player.server;

      ResourceLocation location;
      try {
         location = ResourceLocation.parse(message);
      } catch (Exception exception) {
         player.sendSystemMessage(Component.literal("Invalid dimension id. Use namespace:path, like minecraft:the_end.").withStyle(ChatFormatting.RED));
         return;
      }

      ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, location);
      if (server.getLevel(key) == null) {
         String dimensions = server.levelKeys()
            .stream()
            .map(ResourceKey::location)
            .<CharSequence>map(ResourceLocation::toString)
            .collect(Collectors.joining(", "));
         player.sendSystemMessage(Component.literal("That dimension does not exist. Dimensions: " + dimensions).withStyle(ChatFormatting.RED));
      } else {
         double lacked = EnergyHelper.isOutOfMagiculeConsuming(player, 8000.0);
         if (lacked > 0.0) {
            player.sendSystemMessage(Component.literal("Not enough magicules to tear into that dimension.").withStyle(ChatFormatting.RED));
            PENDING.remove(player.getUUID());
         } else {
            if (AsmodayTeleportService.teleportToDimension(player, key)) {
               pending.instance().addMasteryPoint(player);
               pending.instance().setCoolDown(10, pending.mode());
               pending.instance().markDirty();
               PENDING.remove(player.getUUID());
            } else {
               player.sendSystemMessage(Component.literal("Space refused that dimension.").withStyle(ChatFormatting.RED));
            }
         }
      }
   }

   private record PendingTeleport(ManasSkillInstance instance, int mode, AsmodayChatTeleportHandler.TeleportType type, int expiresAt) {
   }

   private enum TeleportType {
      PLAYER,
      DIMENSION;
   }
}
