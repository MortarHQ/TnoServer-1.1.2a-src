package com.github.wal_bos.moreskills.client.renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public final class MorphClientState {
   private static final Map<UUID, ResourceLocation> MORPHS = new HashMap<>();

   private MorphClientState() {
   }

   public static void set(UUID playerId, String entityId) {
      if (playerId != null && entityId != null && !entityId.isBlank()) {
         ResourceLocation id = ResourceLocation.tryParse(entityId);
         if (id != null && !id.equals(ResourceLocation.withDefaultNamespace("player"))) {
            MORPHS.put(playerId, id);
         } else {
            MORPHS.remove(playerId);
         }
      } else {
         if (playerId != null) {
            MORPHS.remove(playerId);
         }
      }
   }

   public static EntityType<?> getMorphType(UUID playerId) {
      ResourceLocation id = MORPHS.get(playerId);
      if (id == null) {
         return null;
      }

      EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
      return type == EntityType.PLAYER ? null : type;
   }

   public static void clear(UUID playerId) {
      MORPHS.remove(playerId);
   }
}
