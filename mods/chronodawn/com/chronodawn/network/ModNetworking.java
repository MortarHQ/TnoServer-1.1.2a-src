package com.chronodawn.network;

import com.chronodawn.ChronoDawn;
import com.chronodawn.client.shield.ClientShieldCooldowns;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.Side;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.server.level.ServerPlayer;

public final class ModNetworking {
   private ModNetworking() {
   }

   public static void register() {
      if (Platform.getEnvironment() == Env.CLIENT) {
         NetworkManager.registerReceiver(
            Side.S2C,
            S2CShieldCooldownPayload.TYPE,
            S2CShieldCooldownPayload.STREAM_CODEC,
            (payload, context) -> context.queue(() -> ClientShieldCooldowns.applyFromPayload(payload))
         );
      } else {
         NetworkManager.registerS2CPayloadType(S2CShieldCooldownPayload.TYPE, S2CShieldCooldownPayload.STREAM_CODEC);
      }

      ChronoDawn.LOGGER.debug("Registered S2C shield-cooldown payload (env={})", Platform.getEnvironment());
   }

   public static void sendShieldSpeedCooldown(ServerPlayer player, int durationTicks) {
      try {
         NetworkManager.sendToPlayer(player, new S2CShieldCooldownPayload((byte)0, durationTicks));
      } catch (Throwable var3) {
      }
   }

   public static void sendShieldEchoCooldown(ServerPlayer player, int durationTicks) {
      try {
         NetworkManager.sendToPlayer(player, new S2CShieldCooldownPayload((byte)1, durationTicks));
      } catch (Throwable var3) {
      }
   }
}
