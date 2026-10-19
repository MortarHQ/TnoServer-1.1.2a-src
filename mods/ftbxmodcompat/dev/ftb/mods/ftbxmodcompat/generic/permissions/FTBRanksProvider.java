package dev.ftb.mods.ftbxmodcompat.generic.permissions;

import dev.ftb.mods.ftblibrary.integration.permissions.PermissionProvider;
import dev.ftb.mods.ftbranks.api.FTBRanksAPI;
import net.minecraft.server.level.ServerPlayer;

public class FTBRanksProvider implements PermissionProvider {
   public int getIntegerPermission(ServerPlayer player, String nodeName, int def) {
      return FTBRanksAPI.getPermissionValue(player, nodeName).asInteger().orElse(def);
   }

   public boolean getBooleanPermission(ServerPlayer player, String nodeName, boolean def) {
      return FTBRanksAPI.getPermissionValue(player, nodeName).asBoolean().orElse(def);
   }

   public String getStringPermission(ServerPlayer player, String nodeName, String def) {
      return FTBRanksAPI.getPermissionValue(player, nodeName).asString().orElse(def);
   }

   public String getName() {
      return "FTB Ranks";
   }
}
