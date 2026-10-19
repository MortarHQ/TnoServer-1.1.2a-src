package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.monster.Redcap;

public class RedcapShyGoal extends RedcapBaseGoal {
   private LivingEntity entityTarget;
   private final float speed;
   private final boolean lefty = Math.random() < 0.5;
   private double targetX;
   private double targetY;
   private double targetZ;
   private static final double minDistance = 3.0;
   private static final double maxDistance = 6.0;

   public RedcapShyGoal(Redcap entityTFRedcap, float moveSpeed) {
      super(entityTFRedcap);
      this.speed = moveSpeed;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      LivingEntity attackTarget = this.redcap.getTarget();
      if (attackTarget != null
         && this.redcap.isShy()
         && !(attackTarget.distanceTo(this.redcap) > 6.0)
         && !(attackTarget.distanceTo(this.redcap) < 3.0)
         && this.isTargetLookingAtMe(attackTarget)) {
         this.entityTarget = attackTarget;
         Vec3 avoidPos = this.findCirclePoint(this.redcap, this.entityTarget, 5.0, this.lefty ? 1.0 : -1.0);
         this.targetX = avoidPos.x();
         this.targetY = avoidPos.y();
         this.targetZ = avoidPos.z();
         return true;
      } else {
         return false;
      }
   }

   public void start() {
      this.redcap.getNavigation().moveTo(this.targetX, this.targetY, this.targetZ, this.speed);
   }

   public boolean canContinueToUse() {
      LivingEntity attackTarget = this.redcap.getTarget();
      if (attackTarget == null) {
         return false;
      } else if (!this.entityTarget.isAlive()) {
         return false;
      } else {
         return this.redcap.getNavigation().isDone()
            ? false
            : this.redcap.isShy()
               && attackTarget.distanceTo(this.redcap) < 6.0
               && attackTarget.distanceTo(this.redcap) > 3.0
               && this.isTargetLookingAtMe(attackTarget);
      }
   }

   public void tick() {
      this.redcap.getLookControl().setLookAt(this.entityTarget, 30.0F, 30.0F);
   }

   public void stop() {
      this.entityTarget = null;
      this.redcap.getNavigation().stop();
   }

   private Vec3 findCirclePoint(Entity circler, Entity toCircle, double radius, double rotation) {
      double vecx = circler.getX() - toCircle.getX();
      double vecz = circler.getZ() - toCircle.getZ();
      float rangle = (float)Math.atan2(vecz, vecx);
      rangle += (float)rotation;
      double dx = Mth.cos(rangle) * radius;
      double dz = Mth.sin(rangle) * radius;
      return new Vec3(toCircle.getX() + dx, circler.getBoundingBox().minY, toCircle.getZ() + dz);
   }
}
