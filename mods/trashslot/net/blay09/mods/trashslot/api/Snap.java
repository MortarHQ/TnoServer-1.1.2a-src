package net.blay09.mods.trashslot.api;

public class Snap {
   private final int x;
   private final int y;
   private final Snap.Type type;

   public Snap(Snap.Type type, int x, int y) {
      this.type = type;
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public Snap.Type getType() {
      return this.type;
   }

   public enum Type {
      VERTICAL,
      HORIZONTAL,
      FIXED;
   }
}
