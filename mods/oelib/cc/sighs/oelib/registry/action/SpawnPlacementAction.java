package cc.sighs.oelib.registry.action;

import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public record SpawnPlacementAction<T extends Mob>(
   Supplier<? extends EntityType<T>> type, SpawnPlacementType placement, Types heightmap, SpawnPredicate<T> predicate
) implements RegistrationAction {
   public SpawnPlacementAction {
      Objects.requireNonNull(type, "type");
      Objects.requireNonNull(placement, "placement");
      Objects.requireNonNull(heightmap, "heightmap");
      Objects.requireNonNull(predicate, "predicate");
   }
}
