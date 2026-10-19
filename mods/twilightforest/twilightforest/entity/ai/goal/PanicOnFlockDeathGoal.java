package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.monster.Kobold;

public class PanicOnFlockDeathGoal extends Goal {
   private final PathfinderMob flockCreature;
   private final float speed;
   private double fleeX;
   private double fleeY;
   private double fleeZ;
   private int fleeTimer;

   public PanicOnFlockDeathGoal(PathfinderMob creature, float speed) {
      this.flockCreature = creature;
      this.speed = speed;
      this.setFlags(EnumSet.of(Flag.MOVE));
      this.fleeTimer = 0;
   }

   public boolean canUse() {
      boolean yikes = this.fleeTimer > 0;

      for (LivingEntity flocker : this.flockCreature
         .level()
         .getEntitiesOfClass(this.flockCreature.getClass(), this.flockCreature.getBoundingBox().inflate(4.0, 2.0, 4.0))) {
         if (flocker.deathTime > 0) {
            yikes = true;
            break;
         }
      }

      if (!yikes) {
         return false;
      }

      Vec3 target = DefaultRandomPos.getPos(this.flockCreature, 5, 4);
      if (target == null) {
         return false;
      }

      this.fleeX = target.x();
      this.fleeY = target.y();
      this.fleeZ = target.z();
      return true;
   }

   public void start() {
      this.fleeTimer = 40;
      this.flockCreature.getNavigation().moveTo(this.fleeX, this.fleeY, this.fleeZ, this.speed);
      if (this.flockCreature instanceof Kobold kobold) {
         kobold.setPanicked(true);
      }
   }

   public boolean canContinueToUse() {
      return this.fleeTimer > 0 && !this.flockCreature.getNavigation().isDone();
   }

   public void tick() {
      this.fleeTimer--;
   }

   public void stop() {
      this.fleeTimer -= 20;
      if (this.flockCreature instanceof Kobold kobold) {
         kobold.setPanicked(false);
      }
   }
}
