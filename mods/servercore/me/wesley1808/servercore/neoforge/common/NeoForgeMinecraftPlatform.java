package me.wesley1808.servercore.neoforge.common;

import me.wesley1808.servercore.common.services.platform.MinecraftPlatform;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContext;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import servercore_libs.net.kyori.adventure.text.minimessage.MiniMessage;
import servercore_libs.net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class NeoForgeMinecraftPlatform implements MinecraftPlatform {
   @Override
   public boolean hasPermission(CommandSourceStack source, String node, int level) {
      if (source.hasPermission(level)) {
         return true;
      }

      ServerPlayer player = source.getPlayer();
      PermissionNode<Boolean> permission = NeoForgePermissions.getPermissionNode(node);
      return player != null && permission != null ? (Boolean)PermissionAPI.getPermission(player, permission, new PermissionDynamicContext[0]) : false;
   }

   @Override
   public boolean shouldForceChunkTicks(ChunkMap chunkMap, ChunkPos pos) {
      return chunkMap.getDistanceManager().shouldForceTicks(pos.toLong());
   }

   @Override
   public Component parseText(MinecraftServer server, String input) {
      return Serializer.fromJson(GsonComponentSerializer.gson().serialize(MiniMessage.miniMessage().deserialize(input)), server.registryAccess());
   }
}
