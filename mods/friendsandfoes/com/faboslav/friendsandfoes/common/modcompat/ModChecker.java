package com.faboslav.friendsandfoes.common.modcompat;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.platform.PlatformHooks;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class ModChecker {
   public static final List<ModCompat> CUSTOM_EQUIPMENT_SLOTS_COMPATS = new ArrayList<>();

   public static void setupModCompat() {
      String modId = "";

      try {
         PlatformHooks.PLATFORM_COMPAT.setupPlatformModCompat();
      } catch (Throwable e) {
         FriendsAndFoes.getLogger().error("Failed to setup compat with " + modId);
         e.printStackTrace();
      }
   }

   public static void loadModCompat(String modId, Supplier<ModCompat> loader) {
      try {
         if (PlatformHooks.PLATFORM_HELPER.isModLoaded(modId)) {
            ModCompat compat = loader.get();
            if (compat.compatTypes().contains(ModCompat.Type.CUSTOM_EQUIPMENT_SLOTS)) {
               CUSTOM_EQUIPMENT_SLOTS_COMPATS.add(compat);
            }
         }
      } catch (Throwable e) {
         FriendsAndFoes.getLogger().error("Failed to load compat with " + modId);
         e.printStackTrace();
      }
   }
}
