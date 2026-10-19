package dev.ftb.mods.ftbultimine;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbultimine.config.FTBUltimineServerConfig;
import dev.ftb.mods.ftbultimine.net.SyncUltimineTimePacket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class CooldownTracker {
   private static final CooldownTracker clientInstance = new CooldownTracker();
   private static final CooldownTracker serverInstance = new CooldownTracker();
   private final Map<UUID, Long> lastUltimineTime = new HashMap<>();
   private static long ultimineCooldownClient;

   public static long getLastUltimineTime(Player player) {
      CooldownTracker instance = player.level().isClientSide ? clientInstance : serverInstance;
      return instance.lastUltimineTime.getOrDefault(player.getUUID(), 0L);
   }

   public static void setLastUltimineTime(Player player, long when) {
      CooldownTracker instance = player.level().isClientSide ? clientInstance : serverInstance;
      instance.lastUltimineTime.put(player.getUUID(), when);
      if (player instanceof ServerPlayer sp) {
         NetworkManager.sendToPlayer(sp, new SyncUltimineTimePacket(when, SyncUltimineTimePacket.TimeType.LAST_USED));
      }
   }

   public static float getCooldownRemaining(Player player) {
      long coolDown = getUltimineCooldown(player);
      if (coolDown == 0L) {
         return 1.0F;
      }

      long tickDelta = (System.currentTimeMillis() - getLastUltimineTime(player)) / 50L;
      return Mth.clamp((float)tickDelta / (float)coolDown, 0.0F, 1.0F);
   }

   public static boolean isOnCooldown(Player player) {
      long coolDown = getUltimineCooldown(player);
      if (coolDown == 0L) {
         return false;
      }

      long coolDownMs = coolDown * 50L;
      return coolDownMs > 0L && System.currentTimeMillis() - getLastUltimineTime(player) < coolDownMs;
   }

   private static long getUltimineCooldown(Player player) {
      return player instanceof ServerPlayer sp ? FTBUltimineServerConfig.getUltimineCooldown(sp) : ultimineCooldownClient;
   }

   public static void setClientCooldownTime(long cooldown) {
      ultimineCooldownClient = cooldown;
   }
}
