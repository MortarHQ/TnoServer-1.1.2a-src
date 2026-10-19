package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.entity.ITFCharger;
import twilightforest.entity.boss.Minoshroom;
import twilightforest.util.entities.EntityUtil;

public class ChargeAttackGoal extends Goal {
   private static final double MIN_RANGE_SQ = 16.0;
   private static final double MAX_RANGE_SQ = 64.0;
   private static final int FREQ = 10;
   private final PathfinderMob charger;
   private LivingEntity chargeTarget;
   private Vec3 chargePos;
   protected final float speed;
   private final boolean canBreak;
   private int windup;
   private boolean hasAttacked;

   public ChargeAttackGoal(PathfinderMob mob, float f, boolean canBreak) {
      this.charger = mob;
      this.speed = f;
      this.canBreak = canBreak;
      this.windup = 0;
      this.hasAttacked = false;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      this.chargeTarget = this.charger.getTarget();
      if (this.chargeTarget == null) {
         return false;
      }

      double distance = this.charger.distanceToSqr(this.chargeTarget);
      double minoshroomBonus = this.charger instanceof Minoshroom ? 9.0 : 0.0;
      if (distance < 16.0 + minoshroomBonus || distance > 64.0 + minoshroomBonus) {
         return false;
      }

      if (!this.charger.onGround()) {
         return false;
      }

      Vec3 chargePos = this.findChargePoint(this.charger, this.chargeTarget);
      boolean canSeeTargetFromDest = this.charger.getSensing().hasLineOfSight(this.chargeTarget);
      if (!canSeeTargetFromDest) {
         return false;
      }

      this.chargePos = chargePos;
      return this.charger.getRandom().nextInt(10) == 0;
   }

   public void start() {
      this.windup = 15 + this.charger.getRandom().nextInt(30);
      this.charger.setSprinting(true);
   }

   public boolean canContinueToUse() {
      return this.windup > 0 || !this.charger.getNavigation().isDone();
   }

   public void tick() {
      this.charger.getLookControl().setLookAt(this.chargePos.x(), this.chargePos.y() - 1.0, this.chargePos.z(), 10.0F, this.charger.getMaxHeadXRot());
      if (this.windup > 0) {
         if (--this.windup == 0) {
            this.charger.getNavigation().moveTo(this.chargePos.x(), this.chargePos.y(), this.chargePos.z(), this.speed);
         } else {
            this.charger.walkAnimation.setSpeed(this.charger.walkAnimation.speed() + 0.8F);
            if (this.charger instanceof ITFCharger chargeMob) {
               chargeMob.setCharging(true);
            }
         }
      } else if (this.canBreak && !this.charger.level().isClientSide() && EventHooks.canEntityGrief(this.charger.level(), this.charger)) {
         AABB bb = this.charger.getBoundingBox();
         int minx = Mth.floor(bb.minX - 0.75);
         int miny = Mth.floor(bb.minY + 0.0);
         int minz = Mth.floor(bb.minZ - 0.75);
         int maxx = Mth.floor(bb.maxX + 0.75);
         int maxy = Mth.floor(bb.maxY + 0.15);
         int maxz = Mth.floor(bb.maxZ + 0.75);
         BlockPos min = new BlockPos(minx, miny, minz);
         BlockPos max = new BlockPos(maxx, maxy, maxz);
         if (this.charger.level().hasChunksAt(min, max)) {
            for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
               if (EntityUtil.canDestroyBlock(this.charger.level(), pos, this.charger) && this.charger.level().getBlockEntity(pos) == null) {
                  this.charger.level().destroyBlock(pos, true);
               }
            }
         }
      }

      double rangeSq = this.charger instanceof Minoshroom
         ? 5.0
         : this.charger.getBbWidth() * 2.0F * this.charger.getBbWidth() * 2.0F + this.chargeTarget.getBbWidth();
      if (this.charger.distanceToSqr(this.chargeTarget.getX(), this.chargeTarget.getBoundingBox().minY, this.chargeTarget.getZ()) <= rangeSq
         && !this.hasAttacked) {
         this.hasAttacked = true;
         this.charger.doHurtTarget(this.chargeTarget);
      }
   }

   public void stop() {
      this.windup = 0;
      this.chargeTarget = null;
      this.hasAttacked = false;
      this.charger.setSprinting(false);
      if (this.charger instanceof ITFCharger chargeMob) {
         chargeMob.setCharging(false);
      }
   }

   protected Vec3 findChargePoint(Entity attacker, Entity target) {
      double vecx = target.getX() - attacker.getX();
      double vecz = target.getZ() - attacker.getZ();
      float rangle = (float)Math.atan2(vecz, vecx);
      double distance = Mth.sqrt((float)(vecx * vecx + vecz * vecz));
      double overshoot = 2.1;
      double dx = Mth.cos(rangle) * (distance + overshoot);
      double dz = Mth.sin(rangle) * (distance + overshoot);
      return new Vec3(attacker.getX() + dx, target.getY(), attacker.getZ() + dz);
   }
}
