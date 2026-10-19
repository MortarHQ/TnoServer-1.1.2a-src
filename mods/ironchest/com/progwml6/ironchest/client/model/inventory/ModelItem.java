package com.progwml6.ironchest.client.model.inventory;

import org.joml.Vector3f;

public class ModelItem {
   private final Vector3f center;
   private final float size;
   private Vector3f centerScaled;
   private Float sizeScaled;

   public ModelItem(Vector3f center, float size) {
      this.center = center;
      this.size = size;
   }

   public Vector3f getCenterScaled() {
      if (this.centerScaled == null) {
         this.centerScaled = new Vector3f(this.center);
         this.centerScaled.mul(0.0625F);
      }

      return this.centerScaled;
   }

   public float getSizeScaled() {
      if (this.sizeScaled == null) {
         this.sizeScaled = this.size / 16.0F;
      }

      return this.sizeScaled;
   }

   public Vector3f getCenter() {
      return this.center;
   }

   public float getSize() {
      return this.size;
   }
}
