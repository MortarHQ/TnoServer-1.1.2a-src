package com.tensura_tno.compat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public final class MysticismRestrictedSkillPolicy {
   public static final ResourceLocation RESTRICTED = id("restricted");
   private static final Set<ResourceLocation> ALLOWED_RACES = Set.of(
      id("restricted_human"), id("restricted_saint"), id("bound_enlightenment"), id("heavenly_restriction")
   );
   private static final ThreadLocal<Map<UUID, ResourceLocation>> CHANGING_RACES = new ThreadLocal<>();

   private MysticismRestrictedSkillPolicy() {
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", path);
   }

   public static boolean shouldBlock(ResourceLocation skillId, ResourceLocation raceId) {
      return RESTRICTED.equals(skillId) && (raceId == null || !ALLOWED_RACES.contains(raceId));
   }

   public static ResourceLocation effectiveRace(UUID playerId, Supplier<ResourceLocation> actualRace) {
      Map<UUID, ResourceLocation> changing = CHANGING_RACES.get();
      return changing != null && changing.containsKey(playerId) ? changing.get(playerId) : actualRace.get();
   }

   public static boolean withRaceChange(UUID playerId, ResourceLocation targetRaceId, BooleanSupplier action) {
      Map<UUID, ResourceLocation> changing = CHANGING_RACES.get();
      if (changing == null) {
         changing = new HashMap<>();
         CHANGING_RACES.set(changing);
      }

      boolean nested = changing.containsKey(playerId);
      ResourceLocation previous = changing.put(playerId, targetRaceId);

      try {
         return action.getAsBoolean();
      } finally {
         if (nested) {
            changing.put(playerId, previous);
         } else {
            changing.remove(playerId);
         }

         if (changing.isEmpty()) {
            CHANGING_RACES.remove();
         }
      }
   }
}
