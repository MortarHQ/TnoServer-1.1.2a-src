package dev.foxboss.network;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class MilimBossBars {
   private static final Set<UUID> TRACKED = new HashSet<>();

   public static void register(RegisterPayloadHandlersEvent event) {
      event.registrar("1").playToClient(MilimBossBarPayload.TYPE, MilimBossBarPayload.STREAM_CODEC, (payload, context) -> {
         if (payload.tracked()) {
            TRACKED.add(payload.bossId());
         } else {
            TRACKED.remove(payload.bossId());
         }
      });
   }

   public static boolean contains(UUID id) {
      return TRACKED.contains(id);
   }

   public static void clear() {
      TRACKED.clear();
   }

   private MilimBossBars() {
   }
}
