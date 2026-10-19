package twilightforest.world.components.structures.icetower.floordecorators;

import java.util.List;

public class FloorQuadPillarStairs extends FloorWith3x3Map {
   public FloorQuadPillarStairs() {
      this.REQUIRED_FLOOR_PARTS = List.of(FloorParts.LEFT, FloorParts.LEFT_FRONT);
      this.BLOCKED_FLOOR_PARTS = List.of(FloorParts.BACK, FloorParts.RIGHT_BACK, FloorParts.PILLARS);
   }
}
