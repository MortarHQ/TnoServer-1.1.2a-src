package dev.foxboss.entity;

import dev.foxboss.FoxBossConfig;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.entity.template.subclass.IArenaBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

final class MilimBurstPlanner {
   private static final TagKey<EntityType<?>> COMMON_BOSSES = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", "bosses"));
   private static final int MAX_RETREAT_TICKS = 60;
   private static final int MAX_STALLED_TICKS = 20;
   private static final int REPATH_TICKS = 10;
   private static final double[] RETREAT_ANGLES = new double[]{0.0, Math.PI / 4, -Math.PI / 4, Math.PI / 2, -Math.PI / 2};
   private final MilimEntity milim;
   private LivingEntity preparingTarget;
   private int startedAt;
   private int lastProgressAt;
   private int nextRepathAt;
   private double bestDistance;

   MilimBurstPlanner(MilimEntity milim) {
      this.milim = milim;
   }

   boolean wantsBurst(LivingEntity target, int cooldown, boolean prefersPursuit) {
      if (cooldown <= 0
         && target != null
         && target.isAlive()
         && target.level() == this.milim.level()
         && this.milim.canAttack(target)
         && this.milim.hasLineOfSight(target)) {
         double distance = this.milim.distanceToSqr(target);
         double range = (Double)FoxBossConfig.BURST_RANGE.get();
         return distance >= range * range ? false : isBoss(target) || !prefersPursuit && distance > 144.0;
      } else {
         return false;
      }
   }

   boolean tick(LivingEntity target, int cooldown, boolean prefersPursuit) {
      if (!this.wantsBurst(target, cooldown, prefersPursuit)) {
         this.reset();
         return false;
      }

      double distance = this.milim.distanceTo(target);
      double desiredDistance = Math.min(14.0, (Double)FoxBossConfig.BURST_RANGE.get() * 0.7);
      if (distance >= desiredDistance) {
         this.cast(target);
         return true;
      }

      if (this.preparingTarget != target) {
         this.preparingTarget = target;
         this.startedAt = this.lastProgressAt = this.nextRepathAt = this.milim.tickCount;
         this.bestDistance = distance;
      }

      if (distance > this.bestDistance + 0.5) {
         this.bestDistance = distance;
         this.lastProgressAt = this.milim.tickCount;
      }

      if (!this.milim.onGround() && !this.milim.isInLiquid() && !this.milim.isPassenger()) {
         this.milim.setSprinting(false);
         return true;
      }

      if (this.milim.tickCount - this.startedAt < 60 && this.milim.tickCount - this.lastProgressAt < 20) {
         if (this.milim.tickCount >= this.nextRepathAt || this.milim.getNavigation().isDone()) {
            this.nextRepathAt = this.milim.tickCount + 10;
            Path retreat = this.retreatPath(target, desiredDistance);
            if (retreat == null || !this.milim.getNavigation().moveTo(retreat, 1.45)) {
               this.cast(target);
               return true;
            }
         }

         this.milim.setSprinting(true);
         return true;
      } else {
         this.cast(target);
         return true;
      }
   }

   void reset() {
      this.preparingTarget = null;
   }

   static boolean isBoss(LivingEntity target) {
      return target instanceof IArenaBoss || target.getType().is(TensuraEntityTags.HERO_BOSS) || target.getType().is(COMMON_BOSSES);
   }

   private Path retreatPath(LivingEntity target, double desiredDistance) {
      Vec3 away = this.milim.position().subtract(target.position()).multiply(1.0, 0.0, 1.0);
      if (away.lengthSqr() < 0.01) {
         away = this.milim.forward().scale(-1.0);
      }

      away = away.normalize();

      for (double angle : RETREAT_ANGLES) {
         Vec3 candidate = target.position().add(away.yRot((float)angle).scale(desiredDistance + 1.0));

         for (int dy = 1; dy >= -2; dy--) {
            BlockPos feet = BlockPos.containing(candidate.x, this.milim.getY() + dy, candidate.z);
            Vec3 destination = Vec3.atBottomCenterOf(feet);
            Level level = this.milim.level();
            AABB box = this.milim.getBoundingBox().move(destination.subtract(this.milim.position()));
            if (level.hasChunkAt(feet)
               && level.getWorldBorder().isWithinBounds(box)
               && level.getFluidState(feet).isEmpty()
               && level.getBlockState(feet.below()).isFaceSturdy(level, feet.below(), Direction.UP)
               && level.noCollision(this.milim, box)
               && !(destination.distanceToSqr(target.position()) <= this.milim.distanceToSqr(target) + 1.0)) {
               BlockHitResult hit = level.clip(
                  new ClipContext(destination.add(0.0, this.milim.getEyeHeight(), 0.0), target.getEyePosition(), Block.COLLIDER, Fluid.NONE, this.milim)
               );
               if (hit.getType() == Type.MISS) {
                  Path path = this.milim.getNavigation().createPath(feet, 0);
                  if (path != null && path.canReach()) {
                     return path;
                  }
               }
            }
         }
      }

      return null;
   }

   private void cast(LivingEntity target) {
      this.reset();
      float yaw = (float)(Mth.atan2(target.getZ() - this.milim.getZ(), target.getX() - this.milim.getX()) * 180.0 / Math.PI) - 90.0F;
      this.milim.setYRot(yaw);
      this.milim.setYHeadRot(yaw);
      this.milim.setYBodyRot(yaw);
      this.milim.startAction(3);
   }
}
