package team.lodestar.lodestone.systems.worldgen;

import net.minecraft.world.level.WorldGenLevel;

@FunctionalInterface
public interface PlacementCondition {
   PlacementCondition CAN_SURVIVE = (level, entry) -> entry.blockState().canSurvive(level, entry.position());

   boolean canPlace(WorldGenLevel var1, LodestoneWorldgenBuilderEntry var2);
}
