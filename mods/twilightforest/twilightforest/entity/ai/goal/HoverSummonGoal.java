package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import twilightforest.entity.boss.SnowQueen;

public class HoverSummonGoal extends HoverBaseGoal<SnowQueen> {
   private static final int MAX_MINIONS_AT_ONCE = 4;
   private int seekTimer;
   private final int maxSeekTime;

   public HoverSummonGoal(SnowQueen snowQueen) {
      super(snowQueen, 6.0F, 6.0F);
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      this.maxSeekTime = 80;
   }

   public boolean canUse() {
      LivingEntity target = this.attacker.getTarget();
      if (target == null) {
         return false;
      } else if (!target.isAlive()) {
         return false;
      } else {
         return this.attacker.getCurrentPhase() != SnowQueen.Phase.SUMMON ? false : this.attacker.getSensing().hasLineOfSight(target);
      }
   }

   public boolean canContinueToUse() {
      LivingEntity target = this.attacker.getTarget();
      if (target != null && target.isAlive()) {
         if (this.attacker.getCurrentPhase() != SnowQueen.Phase.SUMMON) {
            return false;
         } else {
            return this.seekTimer > this.maxSeekTime ? false : this.attacker.hasLineOfSight(target);
         }
      } else {
         return false;
      }
   }

   public void stop() {
   }

   public void tick() {
      this.seekTimer++;
      LivingEntity target = this.attacker.getTarget();
      if (target != null && this.attacker.distanceToSqr(this.hoverPosX, this.hoverPosY, this.hoverPosZ) <= 3.0) {
         this.checkAndSummon();
         this.makeNewHoverSpot(target);
      }

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
      if (target != null) {
         this.attacker.lookAt(target, 30.0F, 30.0F);
         this.attacker.getLookControl().setLookAt(target, 30.0F, 30.0F);
      }
   }

   @Override
   protected void makeNewHoverSpot(LivingEntity target) {
      super.makeNewHoverSpot(target);
      this.seekTimer = 0;
   }

   private void checkAndSummon() {
      if (this.attacker.getSummonsRemaining() > 0 && this.attacker.countMyMinions() < 4) {
         this.attacker.summonMinionAt(this.attacker.getTarget());
      }
   }
}
