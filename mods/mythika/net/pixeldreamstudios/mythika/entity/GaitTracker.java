package net.pixeldreamstudios.mythika.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public final class GaitTracker {
   private static final double SMOOTHING = 0.25;
   private static final double MIN_RATE = 0.5;
   private static final double MAX_RATE = 2.0;
   private static final double MOVING_ON = 0.25;
   private static final double MOVING_OFF = 0.12;
   private boolean moving;
   private double lastX;
   private double lastZ;
   private boolean started;
   private double blocksPerSecond;

   public void tick(Entity entity) {
      double x = entity.getX();
      double z = entity.getZ();
      if (this.started) {
         double dx = x - this.lastX;
         double dz = z - this.lastZ;
         double measured = Math.sqrt(dx * dx + dz * dz) * 20.0;
         this.blocksPerSecond = this.blocksPerSecond + (measured - this.blocksPerSecond) * 0.25;
      }

      this.started = true;
      this.lastX = x;
      this.lastZ = z;
      this.moving = this.blocksPerSecond > (this.moving ? 0.12 : 0.25);
   }

   public double blocksPerSecond() {
      return this.blocksPerSecond;
   }

   public boolean isMoving() {
      return this.moving;
   }

   public double rateFor(double authoredBlocksPerSecond) {
      return !this.moving ? 1.0 : Mth.clamp(this.blocksPerSecond / authoredBlocksPerSecond, 0.5, 2.0);
   }
}
