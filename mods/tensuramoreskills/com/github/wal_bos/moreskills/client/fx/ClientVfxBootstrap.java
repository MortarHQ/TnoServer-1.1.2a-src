package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.TensuraMoreSkills;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.common.NeoForge;

public final class ClientVfxBootstrap {
   private static final Map<String, Runnable> STARTERS = new LinkedHashMap<>();
   private static final Map<String, Boolean> LAST_ALLOWED = new LinkedHashMap<>();
   private static final Set<String> STARTED = new HashSet<>();
   private static boolean initialized;
   private static boolean booted;
   private static int stateCheckTicks;

   private ClientVfxBootstrap() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ClientVfxBootstrap::onClientTick);
      }
   }

   public static void start(String id, Runnable starter) {
      String normalized = ClientVfxIds.normalize(id);
      if (normalized != null) {
         STARTERS.put(normalized, starter);
      }
   }

   public static void startAllowedAfterChange(String id) {
      String normalized = ClientVfxIds.normalize(id);
      if (normalized != null) {
         applyState(normalized, ClientVfxGate.allowed(normalized));
      }
   }

   public static void refreshAllAllowed() {
      for (String id : ClientVfxIds.ALL) {
         applyState(id, ClientVfxGate.allowed(id));
      }
   }

   private static void onClientTick(Post event) {
      if (!booted) {
         booted = true;
         refreshAllAllowed();
      } else {
         stateCheckTicks++;
         if (stateCheckTicks >= 20) {
            stateCheckTicks = 0;

            for (String id : ClientVfxIds.ALL) {
               boolean allowed = ClientVfxGate.allowed(id);
               Boolean previous = LAST_ALLOWED.get(id);
               if (previous == null || previous != allowed) {
                  applyState(id, allowed);
               }
            }
         }
      }
   }

   private static void applyState(String id, boolean allowed) {
      String normalized = ClientVfxIds.normalize(id);
      if (normalized != null) {
         LAST_ALLOWED.put(normalized, allowed);
         if (allowed) {
            startNow(normalized);
         } else {
            ClientVfxClassPurger.purge(normalized);
         }
      }
   }

   private static void startNow(String id) {
      Runnable starter = STARTERS.get(id);
      if (starter != null) {
         if (STARTED.add(id)) {
            try {
               starter.run();
            } catch (Throwable throwable) {
               STARTED.remove(id);
               TensuraMoreSkills.LOGGER.error("Failed to start client VFX " + id, throwable);
            }
         }
      }
   }
}
