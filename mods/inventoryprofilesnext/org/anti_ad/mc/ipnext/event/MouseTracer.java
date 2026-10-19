package org.anti_ad.mc.ipnext.event;

import org.anti_ad.mc.common.math2d.Line;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.jetbrains.annotations.NotNull;

public final class MouseTracer {
   @NotNull
   public static final MouseTracer INSTANCE = new MouseTracer();
   private static int x = -1;
   private static int y = -1;
   private static int lastX = -1;
   private static int lastY = -1;

   private MouseTracer() {
   }

   public final int getX() {
      return x;
   }

   public final int getY() {
      return y;
   }

   public final int getLastX() {
      return lastX;
   }

   public final int getLastY() {
      return lastY;
   }

   @NotNull
   public final Line getAsLine() {
      return new Line(lastX, lastY, x, y);
   }

   @NotNull
   public final Point getLocation() {
      return new Point(x, y);
   }

   @NotNull
   public final Point getLastLocation() {
      return new Point(lastX, lastY);
   }

   public final void onTick() {
      lastX = x;
      lastY = y;
      x = VanillaUtil.INSTANCE.mouseX();
      y = VanillaUtil.INSTANCE.mouseY();
   }
}
