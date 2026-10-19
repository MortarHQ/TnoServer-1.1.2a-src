package dev.ftb.mods.ftbultimine.integration.acceldecay;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.world.entity.player.Player;

public enum LogBreakTracker {
   INSTANCE;

   private final Object2LongMap<UUID> lastLogBroken = new Object2LongOpenHashMap();

   public void playerBrokeLog(Player player) {
      this.lastLogBroken.put(player.getUUID(), Util.getMillis());
   }

   public boolean playerRecentlyBrokeLog(Player player, long deltaMS) {
      return Util.getMillis() - this.lastLogBroken.getOrDefault(player.getUUID(), 0L) < deltaMS;
   }
}
