package net.pixeldreamstudios.mythika.entity;

import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mythika.Mythika;

public class MenacingChorusGoal extends Goal {
   private static final double STANDOFF = 4.5;
   private static final double ARC_SPAN = 1.7278759594743864;
   private static final double ARRIVED = 1.25;
   private static final float BODY_TURN = 12.0F;
   private static final int REPATH_TICKS = 10;
   private final SatyrEntity satyr;
   private int repathTicks;

   public MenacingChorusGoal(SatyrEntity satyr) {
      this.satyr = satyr;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      return Mythika.config.satyrPlaysMusic && this.satyr.getTarget() != null && !this.satyr.isChampion();
   }

   public boolean canContinueToUse() {
      return this.canUse();
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void start() {
      this.repathTicks = 0;
      this.satyr.startWarSong();
   }

   public void stop() {
      this.satyr.stopPiping();
      this.satyr.getNavigation().stop();
   }

   public void tick() {
      LivingEntity target = this.satyr.getTarget();
      if (target != null) {
         this.satyr.getLookControl().setLookAt(target, 30.0F, 30.0F);
         Vec3 mark = this.mark(target);
         if (this.satyr.position().distanceToSqr(mark) <= 1.5625) {
            this.satyr.getNavigation().stop();
            this.squareUp(target);
         } else {
            if (--this.repathTicks <= 0) {
               this.repathTicks = 10;
               this.satyr.getNavigation().moveTo(mark.x, mark.y, mark.z, 1.4);
            }
         }
      }
   }

   private Vec3 mark(LivingEntity target) {
      int size = this.satyr.packSize();
      double offset = size > 1 ? 1.7278759594743864 * ((double)this.satyr.packIndex() / (size - 1) - 0.5) : 0.0;
      double angle = this.satyr.packBearing() + offset;
      return new Vec3(target.getX() + Math.cos(angle) * 4.5, target.getY(), target.getZ() + Math.sin(angle) * 4.5);
   }

   private void squareUp(LivingEntity target) {
      float wanted = (float)(Mth.atan2(target.getZ() - this.satyr.getZ(), target.getX() - this.satyr.getX()) * (180.0 / Math.PI)) - 90.0F;
      this.satyr.yBodyRot = Mth.approachDegrees(this.satyr.yBodyRot, wanted, 12.0F);
      this.satyr.setYRot(this.satyr.yBodyRot);
   }
}
