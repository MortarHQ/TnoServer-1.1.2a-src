package twilightforest.world.components.structures.icetower.floordecorators;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public abstract class FloorWith3x3Map implements IFloorDecorator {
   protected List<FloorParts> REQUIRED_FLOOR_PARTS;
   protected List<FloorParts> BLOCKED_FLOOR_PARTS;

   @NotNull
   @Override
   public List<FloorParts> getRequiredFloorParts() {
      return List.copyOf(this.REQUIRED_FLOOR_PARTS);
   }

   @NotNull
   @Override
   public List<FloorParts> getBlockedFloorParts() {
      return List.copyOf(this.BLOCKED_FLOOR_PARTS);
   }
}
