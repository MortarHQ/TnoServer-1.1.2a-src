package net.pixeldreamstudios.mythika.entity;

import java.util.EnumSet;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.Vec3;

public class RetreatIntoLabyrinthGoal extends Goal {
   private static final double ARRIVED = 6.0;
   private static final double ARRIVED_SQR = 36.0;
   private static final double SPEED = 0.9;
   private static final int REPATH_INTERVAL = 20;
   private static final int MAX_TICKS = 800;
   private static final double CHAMBER_RING = 5.0;
   private static final int INSET = 4;
   private final MinotaurEntity minotaur;
   private Vec3 destination = Vec3.ZERO;
   private int ticks;
   private int repathIn;

   public RetreatIntoLabyrinthGoal(MinotaurEntity minotaur) {
      this.minotaur = minotaur;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      if (!this.minotaur.guardsLabyrinth() || this.minotaur.getTarget() != null) {
         return false;
      } else {
         return !this.minotaur.isInsideLabyrinth(this.minotaur) ? true : this.minotaur.isWithdrawing() && !this.arrived();
      }
   }

   public boolean canContinueToUse() {
      return this.minotaur.getTarget() == null && this.ticks < 800 && !this.arrived();
   }

   private boolean arrived() {
      return this.minotaur.isInsideLabyrinth(this.minotaur) && this.minotaur.distanceToSqr(this.chamber()) <= 36.0;
   }

   public void start() {
      this.ticks = 0;
      this.repathIn = 0;
      this.destination = this.pick();
      this.minotaur.playSound(SoundEvents.HOGLIN_RETREAT, 1.2F, 0.6F);
   }

   public void stop() {
      this.minotaur.getNavigation().stop();
      this.minotaur.stopWithdrawing();
   }

   public void tick() {
      this.ticks++;
      if (--this.repathIn <= 0) {
         this.repathIn = 20;
         if (this.minotaur.distanceToSqr(this.destination) <= 36.0) {
            this.destination = this.pick();
         }

         boolean moving = this.minotaur.getNavigation().moveTo(this.destination.x, this.destination.y, this.destination.z, 0.9);
         if (!moving) {
            this.destination = this.pick();
         }
      }
   }

   private Vec3 pick() {
      BoundingBox maze = this.minotaur.labyrinth();
      if (maze == null) {
         return this.minotaur.position();
      }

      if (!this.minotaur.isInsideLabyrinth(this.minotaur)) {
         return new Vec3(
            Mth.clamp(this.minotaur.getX(), maze.minX() + 4, maze.maxX() - 4), maze.minY(), Mth.clamp(this.minotaur.getZ(), maze.minZ() + 4, maze.maxZ() - 4)
         );
      }

      double angle = this.minotaur.getRandom().nextDouble() * Math.PI * 2.0;
      return this.chamber().add(Math.cos(angle) * 5.0, 0.0, Math.sin(angle) * 5.0);
   }

   private Vec3 chamber() {
      BoundingBox maze = this.minotaur.labyrinth();
      if (maze == null) {
         return this.minotaur.position();
      }

      Vec3i centre = maze.getCenter();
      return new Vec3(centre.getX() + 0.5, maze.minY(), centre.getZ() + 0.5);
   }
}
