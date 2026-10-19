package com.chronodawn.worldgen.runtime;

import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.ServerPacksSource;

public final class OverlayPackInjection {
   private OverlayPackInjection() {
   }

   public static boolean isServerDataRepository(RepositorySource[] sources) {
      for (RepositorySource s : sources) {
         if (s instanceof ServerPacksSource) {
            return true;
         }
      }

      return false;
   }
}
