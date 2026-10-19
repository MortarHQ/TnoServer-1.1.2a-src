package org.popcraft.chunky.shape;

@FunctionalInterface
public interface Shape {
   boolean isBounding(double var1, double var3);

   default String name() {
      return "shape";
   }
}
