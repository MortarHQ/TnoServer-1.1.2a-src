package cc.sighs.oelib.registry.extra;

import cc.sighs.oelib.registry.RegistrationDispatcher;
import cc.sighs.oelib.registry.action.SpawnPlacementAction;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public final class SpawnPlacementsRegister {
   private SpawnPlacementsRegister() {
   }

   public static <T extends Mob> void register(
      Supplier<? extends EntityType<T>> type, SpawnPlacementType placement, Types heightmap, SpawnPredicate<T> predicate
   ) {
      RegistrationDispatcher.perform(new SpawnPlacementAction<>(type, placement, heightmap, predicate));
   }
}
