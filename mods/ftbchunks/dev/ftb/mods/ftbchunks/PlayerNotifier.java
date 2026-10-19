package dev.ftb.mods.ftbchunks;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class PlayerNotifier {
   private static final Object2LongMap<UUID> lastNotified = new Object2LongOpenHashMap();

   public static void notifyWithCooldown(Player player, Component msg, long cooldownMS) {
      long now = System.currentTimeMillis();
      if (now - lastNotified.getOrDefault(player.getUUID(), 0L) > cooldownMS) {
         player.displayClientMessage(msg, true);
         lastNotified.put(player.getUUID(), now);
      }
   }
}
