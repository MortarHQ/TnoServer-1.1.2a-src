package twilightforest.world.components.structures.icetower.floordecorators;

import java.util.Map;

public enum FloorTypesAuroraPalace {
   FAR_WALL_STEPS,
   PILLAR_PARKOUR,
   PILLAR_PLATFORMS,
   PILLAR_PLATFORMS_OUTSIDE,
   PLATFORM,
   QUAD_PILLAR_STAIRS,
   TOP,
   WRAPAROUND_WALL_STEPS,
   WRAPAROUND_WALL_STEPS_PILLARS;

   private static final Map<FloorTypesAuroraPalace, FloorWith3x3Map> floors = Map.ofEntries(
      Map.entry(FAR_WALL_STEPS, new FloorFarWallSteps()),
      Map.entry(PILLAR_PARKOUR, new FloorPillarParkour()),
      Map.entry(PILLAR_PLATFORMS, new FloorPillarPlatforms()),
      Map.entry(PILLAR_PLATFORMS_OUTSIDE, new FloorPillarPlatformsOutside()),
      Map.entry(PLATFORM, new FloorPlatform()),
      Map.entry(QUAD_PILLAR_STAIRS, new FloorQuadPillarStairs()),
      Map.entry(TOP, new FloorTop()),
      Map.entry(WRAPAROUND_WALL_STEPS, new FloorWraparoundWallSteps()),
      Map.entry(WRAPAROUND_WALL_STEPS_PILLARS, new FloorWraparoundWallStepsPillars())
   );
   private static final Map<FloorTypesAuroraPalace, Float> weights = Map.ofEntries(
      Map.entry(FAR_WALL_STEPS, 1.0F),
      Map.entry(PILLAR_PARKOUR, 6.0F),
      Map.entry(PILLAR_PLATFORMS, 6.0F),
      Map.entry(PILLAR_PLATFORMS_OUTSIDE, 1.0F),
      Map.entry(PLATFORM, 1.0F),
      Map.entry(QUAD_PILLAR_STAIRS, 1.0F),
      Map.entry(TOP, 0.0F),
      Map.entry(WRAPAROUND_WALL_STEPS, 1.0F),
      Map.entry(WRAPAROUND_WALL_STEPS_PILLARS, 1.0F)
   );

   public FloorWith3x3Map getFloorWith3x3Map() {
      return floors.get(this);
   }

   public float getWeight() {
      return weights.get(this);
   }
}
