package twilightforest.world.components.structures.icetower.floordecorators;

public enum FloorParts {
   LEFT_FRONT,
   FRONT,
   RIGHT_FRONT,
   LEFT,
   MID,
   RIGHT,
   LEFT_BACK,
   BACK,
   RIGHT_BACK,
   PILLARS;

   public FloorParts rotateClockwise() {
      return switch (this) {
         case LEFT_FRONT -> RIGHT_FRONT;
         case FRONT -> RIGHT;
         case RIGHT_FRONT -> RIGHT_BACK;
         case LEFT -> FRONT;
         default -> this;
         case RIGHT -> BACK;
         case LEFT_BACK -> LEFT_FRONT;
         case BACK -> LEFT;
         case RIGHT_BACK -> LEFT_BACK;
      };
   }

   public FloorParts rotateClockwise(int times) {
      FloorParts partNew = this;

      for (int i = 0; i < times % 4; i++) {
         partNew = partNew.rotateClockwise();
      }

      return partNew;
   }
}
