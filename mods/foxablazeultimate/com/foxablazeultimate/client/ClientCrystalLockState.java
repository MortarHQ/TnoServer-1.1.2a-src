package com.foxablazeultimate.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public final class ClientCrystalLockState {
   private static volatile Set<ResourceLocation> LOCKED = Set.of();

   private ClientCrystalLockState() {
   }

   public static boolean isLocked(ResourceLocation skillId) {
      return skillId != null && LOCKED.contains(skillId);
   }

   public static void replaceAll(Set<ResourceLocation> locked) {
      LOCKED = locked != null && !locked.isEmpty() ? Collections.unmodifiableSet(new HashSet<>(locked)) : Set.of();
   }
}
