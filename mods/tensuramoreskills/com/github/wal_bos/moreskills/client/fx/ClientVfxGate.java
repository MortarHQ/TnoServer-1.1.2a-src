package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.configs.ClientVfxConfigs;

public final class ClientVfxGate {
   private ClientVfxGate() {
   }

   public static boolean allowed(String id) {
      String normalized = ClientVfxIds.normalize(id);
      if (normalized == null) {
         return true;
      } else {
         Boolean effectOverride = ClientVfxLocalOverrides.getEffect(normalized);
         if (effectOverride != null) {
            return effectOverride;
         } else {
            Boolean allOverride = ClientVfxLocalOverrides.getAll();
            if (allOverride != null) {
               return allOverride;
            } else if (!configLoaded()) {
               return true;
            } else {
               return !ClientVfxConfigs.CLIENT.allEffects.get() ? false : ClientVfxConfigs.CLIENT.isEnabled(normalized);
            }
         }
      }
   }

   public static boolean shouldCancelCurrentDraw() {
      return false;
   }

   private static boolean configLoaded() {
      try {
         ClientVfxConfigs.CLIENT.allEffects.get();
         return true;
      } catch (IllegalStateException | NullPointerException ignored) {
         return false;
      }
   }
}
