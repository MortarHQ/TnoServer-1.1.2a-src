package twilightforest.world.components.structures.icetower.floordecorators;

import java.util.List;

public class FloorPillarPlatforms extends FloorWith3x3Map {
   public FloorPillarPlatforms() {
      this.REQUIRED_FLOOR_PARTS = List.of(
         FloorParts.LEFT_BACK, FloorParts.BACK, FloorParts.RIGHT_BACK, FloorParts.LEFT, FloorParts.MID, FloorParts.RIGHT, FloorParts.PILLARS
      );
      this.BLOCKED_FLOOR_PARTS = List.of(
         FloorParts.LEFT_BACK, FloorParts.BACK, FloorParts.LEFT, FloorParts.MID, FloorParts.LEFT_FRONT, FloorParts.FRONT, FloorParts.PILLARS
      );
   }
}
