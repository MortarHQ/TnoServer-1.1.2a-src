package twilightforest.world.components.structures.icetower.floordecorators;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface IFloorDecorator {
   @NotNull
   default List<FloorParts> getBlockedFloorParts() {
      return new ArrayList<>();
   }

   @NotNull
   default List<FloorParts> getRequiredFloorParts() {
      return new ArrayList<>();
   }
}
