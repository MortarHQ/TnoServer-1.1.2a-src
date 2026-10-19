package it.hurts.sskirillss.relics.items.relics.base.data.research;

import net.minecraft.world.phys.Vec2;

public class StarData {
   private int index;
   private int x;
   private int y;

   public Vec2 getPos() {
      return new Vec2(this.x, this.y);
   }

   public int getIndex() {
      return this.index;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public void setIndex(int index) {
      this.index = index;
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof StarData other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getIndex() != other.getIndex()) {
         return false;
      } else {
         return this.getX() != other.getX() ? false : this.getY() == other.getY();
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof StarData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getIndex();
      result = result * 59 + this.getX();
      return result * 59 + this.getY();
   }

   @Override
   public String toString() {
      return "StarData(index=" + this.getIndex() + ", x=" + this.getX() + ", y=" + this.getY() + ")";
   }

   public StarData(int index, int x, int y) {
      this.index = index;
      this.x = x;
      this.y = y;
   }
}
