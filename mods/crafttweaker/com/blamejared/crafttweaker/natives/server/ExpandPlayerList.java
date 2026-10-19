package com.blamejared.crafttweaker.natives.server;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/server/PlayerList")
@NativeTypeRegistration(value = PlayerList.class, zenCodeName = "crafttweaker.api.server.PlayerList")
public class ExpandPlayerList {
   @ZenCodeType.Method
   public static @ZenCodeType.Nullable ServerPlayer getPlayerByName(PlayerList internal, String name) {
      return internal.getPlayerByName(name);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("playerCount")
   public static int getPlayerCount(PlayerList internal) {
      return internal.getPlayerCount();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("maxPlayers")
   public static int getMaxPlayers(PlayerList internal) {
      return internal.getMaxPlayers();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("viewDistance")
   public static int getViewDistance(PlayerList internal) {
      return internal.getViewDistance();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("simulationDistance")
   public static int getSimulationDistance(PlayerList internal) {
      return internal.getSimulationDistance();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("server")
   public static MinecraftServer getServer(PlayerList internal) {
      return internal.getServer();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("players")
   public static List<ServerPlayer> getPlayers(PlayerList internal) {
      return internal.getPlayers();
   }

   @ZenCodeType.Method
   public static @ZenCodeType.Nullable ServerPlayer getPlayer(PlayerList internal, UUID uuid) {
      return internal.getPlayer(uuid);
   }

   @ZenCodeType.Getter("allowCommandsForAllPlayers")
   public static boolean isAllowCommandsForAllPlayers(PlayerList internal) {
      return internal.isAllowCommandsForAllPlayers();
   }
}
