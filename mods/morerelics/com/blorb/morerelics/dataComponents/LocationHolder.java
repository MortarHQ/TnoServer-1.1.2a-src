package com.blorb.morerelics.dataComponents;

import java.util.Objects;

public class LocationHolder {
   public double x = 0.0;
   public double y = 0.0;
   public double z = 0.0;

   public LocationHolder(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }

   public void setZ(double z) {
      this.z = z;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.x, this.y, this.z);
   }

   @Override
   public boolean equals(Object obj) {
      return obj == this ? true : obj instanceof LocationHolder ex && this.x == ex.x && this.y == ex.y && this.z == ex.z;
   }
}
