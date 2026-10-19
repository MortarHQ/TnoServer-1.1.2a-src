package dev.ftb.mods.ftblibrary.sidebar;

public record GridLocation(int x, int y) {
   public static final GridLocation OUT_OF_BOUNDS = new GridLocation(-1, -1);

   public boolean isOutOfBounds() {
      return this.x < 0 || this.y < 0;
   }

   public boolean isLaterInRow(GridLocation other) {
      return this.x == other.x && this.y <= other.y;
   }

   public boolean isLaterInColumn(GridLocation other) {
      return this.x <= other.x && this.y == other.y;
   }
}
