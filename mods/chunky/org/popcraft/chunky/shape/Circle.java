package org.popcraft.chunky.shape;

import org.popcraft.chunky.Selection;
import org.popcraft.chunky.platform.util.Vector2;

public class Circle extends AbstractEllipse {
   public Circle(Selection selection, boolean chunkAligned) {
      super(selection, chunkAligned);
   }

   @Override
   public boolean isBounding(double x, double z) {
      return Math.hypot(this.centerX - x, this.centerZ - z) <= this.radiusX;
   }

   @Override
   public String name() {
      return "circle";
   }

   @Override
   public Vector2 radii() {
      return Vector2.of(this.radiusX, this.radiusX);
   }
}
