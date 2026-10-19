package net.pixeldreamstudios.mythika.entity;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.platform.Services;

public class MinotaurChargeGoal extends Goal {
   private static final double MIN_DISTANCE = 6.0;
   private static final double MAX_DISTANCE = 16.0;
   private static final double SPEED_MODIFIER = 2.26;
   private static final int WINDUP_TICKS = 20;
   private static final int STUN_TICKS = 40;
   private static final int MAX_CHARGE_TICKS = 60;
   private static final double KNOCKBACK = 2.4;
   private static final double LIFT = 0.2;
   private static final double MAX_DRIFT = 2.4;
   private static final double DRIFT_STEP = 0.4;
   private static final double PROBE_AHEAD = 6.0;
   private static final double PROBE_STEP = 0.75;
   private static final double SIDE_MARGIN = 0.2;
   private static final double LOOK_AHEAD = 3.0;
   private static final double BOUNDARY_LOOKAHEAD = 1.5;
   private static final int CLEAR_TICKS = 5;
   private static final double WALL_RECOIL = 0.8;
   private static final int RECOIL_TICKS = 7;
   private final MinotaurEntity minotaur;
   private Vec3 start = Vec3.ZERO;
   private Vec3 direction = Vec3.ZERO;
   private int driftSide;
   private int clearTicks;
   private boolean smashes;
   private int phaseTicks;

   public MinotaurChargeGoal(MinotaurEntity minotaur) {
      this.minotaur = minotaur;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      LivingEntity target = this.minotaur.getTarget();
      if (target != null && target.isAlive() && this.minotaur.canStartCharge()) {
         double distance = this.minotaur.distanceTo(target);
         return distance >= 6.0 && distance <= 16.0 && this.minotaur.isInsideLabyrinth(target) && this.minotaur.getSensing().hasLineOfSight(target);
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      return this.minotaur.chargePhase() != MinotaurEntity.ChargePhase.NONE;
   }

   public boolean isInterruptable() {
      return false;
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void start() {
      this.phaseTicks = 0;
      this.minotaur.setChargePhase(MinotaurEntity.ChargePhase.WINDUP);
      this.minotaur.getNavigation().stop();
      this.minotaur.playSound(SoundEvents.GOAT_PREPARE_RAM, 1.3F, 0.5F);
   }

   public void stop() {
      this.driftSide = 0;
      this.clearTicks = 0;
      this.minotaur.setChargePhase(MinotaurEntity.ChargePhase.NONE);
      this.minotaur.startChargeCooldown();
      this.phaseTicks = 0;
   }

   public void tick() {
      switch (this.minotaur.chargePhase()) {
         case WINDUP:
            this.tickWindup();
            break;
         case CHARGING:
            this.tickCharge();
            break;
         case STUNNED:
            this.tickStun();
      }
   }

   private void hold() {
      this.minotaur.getNavigation().stop();
      this.minotaur.setDeltaMovement(0.0, this.minotaur.getDeltaMovement().y, 0.0);
   }

   private void tickWindup() {
      this.hold();
      LivingEntity target = this.minotaur.getTarget();
      if (target != null) {
         this.minotaur.getLookControl().setLookAt(target, 30.0F, 30.0F);
      }

      if (++this.phaseTicks >= 20) {
         if (target == null) {
            this.minotaur.setChargePhase(MinotaurEntity.ChargePhase.NONE);
         } else {
            Vec3 toTarget = new Vec3(target.getX() - this.minotaur.getX(), 0.0, target.getZ() - this.minotaur.getZ());
            if (toTarget.lengthSqr() < 1.0E-4) {
               this.minotaur.setChargePhase(MinotaurEntity.ChargePhase.NONE);
            } else {
               this.direction = toTarget.normalize();
               this.start = this.minotaur.position();
               this.phaseTicks = 0;
               this.minotaur.setChargePhase(MinotaurEntity.ChargePhase.CHARGING);
               this.minotaur.playSound(SoundEvents.HOGLIN_ANGRY, 1.4F, 0.6F);
            }
         }
      }
   }

   private void tickCharge() {
      this.smashes = Mythika.config.minotaurChargeBreaksBlocks && Services.PLATFORM.canGrief(this.minotaur);
      if (this.strike()) {
         this.minotaur.setChargePhase(MinotaurEntity.ChargePhase.NONE);
      } else if (!this.minotaur.isInsideLabyrinth(this.minotaur.position().add(this.direction.scale(1.5)))) {
         this.hold();
         this.minotaur.setChargePhase(MinotaurEntity.ChargePhase.NONE);
      } else {
         this.steer();
         if (this.minotaur.horizontalCollision && !this.plough()) {
            this.slamIntoWall();
         } else {
            if (++this.phaseTicks >= 60) {
               this.minotaur.setChargePhase(MinotaurEntity.ChargePhase.NONE);
            }
         }
      }
   }

   private Vec3 perpendicular() {
      return new Vec3(-this.direction.z, 0.0, this.direction.x);
   }

   private void steer() {
      Vec3 perp = this.perpendicular();
      Vec3 travelled = this.minotaur.position().subtract(this.start);
      double along = travelled.dot(this.direction);
      double drift = travelled.dot(perp);
      Vec3 waypoint = this.start.add(this.direction.scale(along + 3.0)).add(perp.scale(this.sidestep(drift)));
      this.minotaur.getMoveControl().setWantedPosition(waypoint.x, this.minotaur.getY(), waypoint.z, 2.26);
   }

   private double sidestep(double drift) {
      double held = Mth.clamp(drift, -2.4, 2.4);
      double straight = this.clearance(0.0);
      if (straight >= 6.0) {
         if (++this.clearTicks >= 5 && this.clearance(-drift) >= 6.0) {
            this.driftSide = 0;
            return 0.0;
         } else {
            return held;
         }
      } else {
         this.clearTicks = 0;
         int committed = this.driftSide != 0 ? this.driftSide : 1;
         double best = held;
         double bestRoom = straight;

         for (double offset = 0.4; offset <= 2.400001; offset += 0.4) {
            for (int i = 0; i < 2; i++) {
               double aim = (i == 0 ? committed : -committed) * offset;
               double room = this.clearance(aim - drift);
               if (room >= 6.0) {
                  this.driftSide = aim < 0.0 ? -1 : 1;
                  return aim;
               }

               if (room > bestRoom) {
                  bestRoom = room;
                  best = aim;
               }
            }
         }

         this.driftSide = (int)Math.signum(best);
         return best;
      }
   }

   private double clearance(double lateral) {
      Vec3 perp = this.perpendicular();
      AABB from = this.minotaur.getBoundingBox().move(perp.x * lateral, 0.0, perp.z * lateral).inflate(0.2, 0.0, 0.2);
      double reached = 0.0;

      for (double ahead = 0.0; ahead <= 6.0; ahead += 0.75) {
         if (this.occupied(from.move(this.direction.x * ahead, 0.0, this.direction.z * ahead))) {
            return reached;
         }

         reached = ahead;
      }

      return 6.0;
   }

   private boolean occupied(AABB box) {
      Level level = this.minotaur.level();
      int bottom = Mth.floor(box.minY + this.minotaur.maxUpStep());

      for (BlockPos pos : BlockPos.betweenClosed(
         Mth.floor(box.minX), bottom, Mth.floor(box.minZ), Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ)
      )) {
         BlockState state = level.getBlockState(pos);
         if (!state.isAir() && (!this.smashes || !MinotaurEntity.canSmash(state)) && !state.getCollisionShape(level, pos).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private void tickStun() {
      this.minotaur.getNavigation().stop();
      if (this.phaseTicks >= 7) {
         this.minotaur.setDeltaMovement(0.0, this.minotaur.getDeltaMovement().y, 0.0);
      }

      if (this.phaseTicks % 4 == 0) {
         this.minotaur.spawnStunStars();
      }

      if (++this.phaseTicks >= 40) {
         this.minotaur.setChargePhase(MinotaurEntity.ChargePhase.NONE);
      }
   }

   private boolean strike() {
      List<LivingEntity> hits = this.minotaur
         .level()
         .getEntitiesOfClass(
            LivingEntity.class, this.minotaur.getAttackBoundingBox(), other -> other != this.minotaur && other.isAlive() && !(other instanceof MinotaurEntity)
         );
      if (hits.isEmpty()) {
         return false;
      }

      float damage = (float)Mythika.config.minotaurChargeDamage;

      for (LivingEntity victim : hits) {
         victim.hurt(this.minotaur.damageSources().mobAttack(this.minotaur), damage);
         victim.knockback(2.4, -this.direction.x, -this.direction.z);
         victim.setDeltaMovement(victim.getDeltaMovement().add(0.0, 0.2, 0.0));
         victim.hurtMarked = true;
      }

      this.minotaur.recoverFromCharge();
      this.minotaur.onChargeImpact();
      this.minotaur.playSound(SoundEvents.GOAT_RAM_IMPACT, 1.5F, 0.5F);
      return true;
   }

   private boolean plough() {
      if (!this.smashes) {
         return false;
      }

      AABB ahead = this.minotaur.getBoundingBox().inflate(0.2).move(this.direction.x * 0.6, 0.0, this.direction.z * 0.6);
      boolean broke = false;

      for (BlockPos pos : BlockPos.betweenClosed(
         Mth.floor(ahead.minX), Mth.floor(ahead.minY), Mth.floor(ahead.minZ), Mth.floor(ahead.maxX), Mth.floor(ahead.maxY), Mth.floor(ahead.maxZ)
      )) {
         if (MinotaurEntity.canSmash(this.minotaur.level().getBlockState(pos))) {
            broke = this.minotaur.level().destroyBlock(pos, true, this.minotaur) || broke;
         }
      }

      return broke;
   }

   private void slamIntoWall() {
      this.phaseTicks = 0;
      this.minotaur.setChargePhase(MinotaurEntity.ChargePhase.STUNNED);
      this.minotaur.setDeltaMovement(this.direction.scale(-0.8).add(0.0, 0.08, 0.0));
      this.minotaur.hurtMarked = true;
      this.minotaur.onChargeImpact();
      this.minotaur.playSound(SoundEvents.GOAT_RAM_IMPACT, 1.5F, 0.4F);
      this.minotaur.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.2F, 0.6F);
   }
}
