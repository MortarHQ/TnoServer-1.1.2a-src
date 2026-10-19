package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import twilightforest.entity.boss.SnowQueen;

public class HoverThenDropGoal extends HoverBaseGoal<SnowQueen> {
   private int hoverTimer;
   private int dropTimer;
   private int seekTimer;
   private final int maxHoverTime;
   private final int maxDropTime;
   private final int maxSeekTime;
   private double dropY;

   public HoverThenDropGoal(SnowQueen snowQueen, int hoverTime, int dropTime) {
      super(snowQueen, 6.0F, 0.0F);
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      this.maxHoverTime = hoverTime;
      this.maxSeekTime = hoverTime;
      this.maxDropTime = dropTime;
      this.hoverTimer = 0;
   }

   public boolean canUse() {
      LivingEntity target = this.attacker.getTarget();
      if (target == null) {
         return false;
      } else {
         return !target.isAlive() ? false : this.attacker.getCurrentPhase() == SnowQueen.Phase.DROP;
      }
   }

   public boolean canContinueToUse() {
      LivingEntity target = this.attacker.getTarget();
      if (target != null && target.isAlive()) {
         if (this.attacker.getCurrentPhase() != SnowQueen.Phase.DROP) {
            return false;
         }

         if (this.seekTimer > this.maxSeekTime) {
            return false;
         }

         if (this.attacker.distanceToSqr(this.hoverPosX, this.hoverPosY, this.hoverPosZ) <= 1.0) {
            this.hoverTimer++;
            return true;
         }

         if (this.dropTimer < this.maxDropTime) {
            return true;
         }

         this.attacker.incrementSuccessfulDrops();
         return false;
      } else {
         return false;
      }
   }

   public void stop() {
      this.hoverTimer = 0;
      this.dropTimer = 0;
   }

   public void tick() {
      if (this.hoverTimer > 0) {
         this.hoverTimer++;
      } else {
         this.seekTimer++;
      }

      if (this.hoverTimer < this.maxHoverTime) {
         double offsetX = this.hoverPosX - this.attacker.getX();
         double offsetY = this.hoverPosY - this.attacker.getY();
         double offsetZ = this.hoverPosZ - this.attacker.getZ();
         double distanceDesired = offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ;
         distanceDesired = Mth.sqrt((float)distanceDesired);
         double velX = offsetX / distanceDesired * 0.05;
         double velY = offsetY / distanceDesired * 0.1;
         double velZ = offsetZ / distanceDesired * 0.05;
         velY += 0.05;
         this.attacker.push(velX, velY, velZ);
         LivingEntity target = this.attacker.getTarget();
         if (target != null) {
            this.attacker.lookAt(target, 30.0F, 30.0F);
            this.attacker.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }
      } else {
         this.dropTimer++;
         if (this.attacker.getY() > this.dropY) {
            this.attacker.destroyBlocksInAABB(this.attacker.getBoundingBox().inflate(1.0, 0.5, 1.0));
         }
      }
   }

   @Override
   protected void makeNewHoverSpot(LivingEntity target) {
      super.makeNewHoverSpot(target);
      this.dropY = target.getY() - 1.0;
      this.seekTimer = 0;
   }
}
