package net.blay09.mods.waystones.core;

import java.util.ArrayList;
import java.util.List;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.minecraft.server.level.ServerPlayer;

public class WaystoneVisibilities {
   public static List<WaystoneVisibility> getVisibilityOptions(ServerPlayer player, Waystone waystone) {
      ArrayList<WaystoneVisibility> result = new ArrayList<>();
      WaystoneVisibility defaultVisibility = WaystonesConfig.getActive().general.defaultVisibility;
      result.add(defaultVisibility);
      WaystoneVisibility baseVisibility = WaystoneVisibility.fromWaystoneType(waystone.getWaystoneType());
      if (!result.contains(baseVisibility)) {
         result.add(baseVisibility);
      }

      if (baseVisibility == WaystoneVisibility.ACTIVATION
         && (WaystonePermissionManager.isAllowedVisibility(WaystoneVisibility.GLOBAL) || WaystonePermissionManager.skipsPermissions(player))
         && !result.contains(WaystoneVisibility.GLOBAL)) {
         result.add(WaystoneVisibility.GLOBAL);
      }

      return result;
   }
}
