package com.faboslav.variantsandventures.common.events.lifecycle;

import com.faboslav.variantsandventures.common.events.base.EventHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public record RegisterEntitySpawnRestrictionsEvent(RegisterEntitySpawnRestrictionsEvent.Registrar registrar) {
   public static final EventHandler<RegisterEntitySpawnRestrictionsEvent> EVENT = new EventHandler<>();

   public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType location, Types heightmap, SpawnPredicate<T> predicate) {
      this.registrar.register(entityType, new RegisterEntitySpawnRestrictionsEvent.Placement<>(predicate, location, heightmap));
   }

   public record Placement<T extends Mob>(SpawnPredicate<T> predicate, SpawnPlacementType location, Types heightmap) {
   }

   @FunctionalInterface
   public interface Registrar {
      <T extends Mob> void register(EntityType<T> var1, RegisterEntitySpawnRestrictionsEvent.Placement<T> var2);
   }
}
