package net.regions_unexplored.module.platform;

import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.regions_unexplored.RegionsUnexploredNeo;

public class EntityHelper {
   public static <T extends Mob> void registerPlacement(
      Supplier<EntityType<T>> type, SpawnPlacementType placementType, Types heightmap, SpawnPredicate<T> predicate
   ) {
      RegionsUnexploredNeo.SPAWN_PLACEMENTS.add(event -> event.register(type.get(), placementType, heightmap, predicate, Operation.REPLACE));
   }

   public static <T extends LivingEntity> void registerAttributes(Supplier<EntityType<T>> type, Supplier<Builder> builder) {
      RegionsUnexploredNeo.ENTITY_ATTRIBUTES.add(event -> event.put(type.get(), builder.get().build()));
   }
}
