package team.lodestar.lodestone.systems.worldgen;

import net.minecraft.world.level.WorldGenLevel;

@FunctionalInterface
public interface AdditionalPlacement {
   void place(WorldGenLevel var1, LodestoneWorldgenBuilderEntry var2);
}
