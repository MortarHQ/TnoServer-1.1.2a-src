package net.pixeldreamstudios.mythika.entity;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mythika.Mythika;

public class PipeAtCampfireGoal extends Goal {
   public static final double ORBIT_RADIUS = 3.0;
   private static final int LAP_TICKS = 480;
   private static final double ORBIT_SPEED = 0.01308996938995747;
   private static final double RING_PACE = 0.71;
   private static final double EASE_PACE = 0.5;
   private static final double CATCH_PACE = 0.9;
   private static final double PACE_GAIN = 0.3;
   private static final double LEAD = Math.PI / 12;
   private static final double SLACK_OUT = 1.5;
   private static final double SLACK_IN = 0.6;
   private static final double FIRE_CLEARANCE = 1.8;
   private static final int DETOUR_TRIES = 6;
   private static final double DETOUR_ARC = Math.PI / 12;
   private static final int STALL_WINDOW = 20;
   private static final double STALL_MIN_ARC = 0.025;
   private static final int DETOUR_TICKS = 60;
   private static final double DETOUR_TARGET = Math.PI / 2;
   private static final int SLOT_INTERVAL = 40;
   private static final double LEAVE_DISTANCE = 16.0;
   private final SatyrEntity satyr;
   private BlockPos fire;
   private double slot;
   private int slotTicks;
   private double windowBearing;
   private int windowTicks;
   private int detourTicks;

   public PipeAtCampfireGoal(SatyrEntity satyr) {
      this.satyr = satyr;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      if (Mythika.config.satyrPlaysMusic && this.satyr.getTarget() == null && !this.satyr.isInWater()) {
         this.fire = this.satyr.nearestCampfire();
         return this.fire != null;
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      return Mythika.config.satyrPlaysMusic
         && this.satyr.getTarget() == null
         && this.fire != null
         && SatyrEntity.isLitCampfire(this.satyr.level(), this.fire)
         && this.satyr.distanceToSqr(Vec3.atCenterOf(this.fire)) < 256.0;
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void start() {
      this.detourTicks = 0;
      this.resetStall();
      this.satyr.startPiping(this.fire);
      this.takeSlot();
   }

   public void stop() {
      this.satyr.stopPiping();
      this.satyr.getNavigation().stop();
      this.fire = null;
   }

   public void tick() {
      if (--this.slotTicks <= 0) {
         this.takeSlot();
      }

      this.satyr.getLookControl().setLookAt(this.fire.getX() + 0.5, this.fire.getY() + 0.5, this.fire.getZ() + 0.5);
      double bearing = this.bearing();
      double radius = this.radius();
      if (radius < 1.8) {
         Vec3 out = this.ringPoint(bearing);
         this.satyr.getNavigation().stop();
         this.satyr.getMoveControl().setWantedPosition(out.x, out.y, out.z, 0.9);
         this.resetStall();
      } else if (radius > 4.5 || radius < 2.4) {
         Vec3 join = this.ringPoint(bearing);
         if (this.satyr.getNavigation().isDone()) {
            this.satyr.getNavigation().moveTo(join.x, join.y, join.z, 0.9);
         }

         this.resetStall();
      } else if (this.detourTicks > 0) {
         if (--this.detourTicks <= 0) {
            this.resetStall();
         } else if (this.satyr.getNavigation().isDone()) {
            Vec3 round = this.ringPoint(bearing + this.direction() * (Math.PI / 2));
            this.satyr.getNavigation().moveTo(round.x, round.y, round.z, 0.9);
         }
      } else if (this.stalled(bearing)) {
         this.detourTicks = 60;
      } else {
         this.satyr.getNavigation().stop();
         Vec3 ahead = this.stepAhead(bearing);
         this.satyr.getMoveControl().setWantedPosition(ahead.x, ahead.y, ahead.z, this.pace(bearing));
      }
   }

   private boolean stalled(double bearing) {
      if (--this.windowTicks > 0) {
         return false;
      }

      double advance = wrap((bearing - this.windowBearing) * this.direction());
      this.windowBearing = bearing;
      this.windowTicks = 20;
      return advance < 0.025;
   }

   private void resetStall() {
      this.windowBearing = this.bearing();
      this.windowTicks = 20;
   }

   private Vec3 stepAhead(double bearing) {
      double direction = this.direction();

      for (int tries = 0; tries < 6; tries++) {
         Vec3 point = this.ringPoint(bearing + direction * ((Math.PI / 12) + tries * (Math.PI / 12)));
         if (this.standable(point)) {
            return point;
         }
      }

      return this.ringPoint(bearing + direction * (Math.PI / 12));
   }

   private boolean standable(Vec3 point) {
      BlockPos pos = BlockPos.containing(point.x, this.satyr.getY(), point.z);
      return !this.satyr.level().getBlockState(pos).blocksMotion() && !this.satyr.level().getBlockState(pos.above()).blocksMotion();
   }

   private double direction() {
      return (this.fire.asLong() & 1L) == 0L ? 1.0 : -1.0;
   }

   private double pace(double bearing) {
      double wanted = this.slot + this.satyr.level().getGameTime() * 0.01308996938995747 * this.direction();
      double lag = wrap((wanted - bearing) * this.direction());
      return Mth.clamp(0.71 + lag * 0.3, 0.5, 0.9);
   }

   private double bearing() {
      return Math.atan2(this.satyr.getZ() - (this.fire.getZ() + 0.5), this.satyr.getX() - (this.fire.getX() + 0.5));
   }

   private double radius() {
      double dx = this.satyr.getX() - (this.fire.getX() + 0.5);
      double dz = this.satyr.getZ() - (this.fire.getZ() + 0.5);
      return Math.sqrt(dx * dx + dz * dz);
   }

   private Vec3 ringPoint(double angle) {
      return new Vec3(this.fire.getX() + 0.5 + Math.cos(angle) * 3.0, this.fire.getY(), this.fire.getZ() + 0.5 + Math.sin(angle) * 3.0);
   }

   private static double wrap(double radians) {
      return radians - (Math.PI * 2) * Math.floor((radians + Math.PI) / (Math.PI * 2));
   }

   private void takeSlot() {
      this.slotTicks = 40;
      double span = 10.0;
      List<SatyrEntity> ring = this.satyr
         .level()
         .getEntitiesOfClass(
            SatyrEntity.class, AABB.ofSize(Vec3.atCenterOf(this.fire), span, span, span), other -> other.isAlive() && this.fire.equals(other.getCampfire())
         );
      ring.sort(Comparator.comparing(Entity::getUUID));
      int index = ring.indexOf(this.satyr);
      if (index >= 0) {
         this.slot = (Math.PI * 2) * index / ring.size();
      }
   }
}
