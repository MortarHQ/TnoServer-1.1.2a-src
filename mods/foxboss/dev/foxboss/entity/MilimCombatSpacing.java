package dev.foxboss.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

final class MilimCombatSpacing {
   private final MilimEntity milim;
   private LivingEntity target;
   private int endsAt;
   private boolean started;

   MilimCombatSpacing(MilimEntity milim) {
      this.milim = milim;
   }

   void request() {
      if (!this.milim.isDemo() && !this.milim.isDebugAnimation() && !this.milim.isNoAi() && !this.milim.isOrderedToSit() && this.milim.hasCombatTarget()) {
         this.target = this.milim.getTarget();
         this.endsAt = this.milim.tickCount + 18;
         this.started = false;
      }
   }

   void reset() {
      this.target = null;
      this.started = false;
   }

   boolean tick(LivingEntity currentTarget) {
      if (this.target == null) {
         return false;
      }

      if (this.target == currentTarget
         && this.milim.tickCount < this.endsAt
         && this.milim.onGround()
         && !this.milim.isInLiquid()
         && !this.milim.isPassenger()
         && !(this.milim.distanceToSqr(this.target) > 16.0)
         && this.milim.hasLineOfSight(this.target)) {
         if (!this.started) {
            Path path = this.stepPath();
            if (path == null || !this.milim.getNavigation().moveTo(path, 1.45)) {
               this.stop();
               return false;
            }

            this.started = true;
         } else if (this.milim.getNavigation().isDone()) {
            this.stop();
            return false;
         }

         this.milim.setSprinting(true);
         this.milim.getLookControl().setLookAt(this.target, 60.0F, 60.0F);
         return true;
      } else {
         this.stop();
         return false;
      }
   }

   private void stop() {
      this.milim.getNavigation().stop();
      this.reset();
   }

   private Path stepPath() {
      Vec3 away = this.milim.position().subtract(this.target.position()).multiply(1.0, 0.0, 1.0);
      if (away.lengthSqr() < 0.01) {
         away = this.milim.forward().multiply(1.0, 0.0, 1.0).scale(-1.0);
      }

      away = away.normalize();
      float side = this.milim.getRandom().nextBoolean() ? 1.0F : -1.0F;

      for (float direction : new float[]{side, -side, 0.0F}) {
         Vec3 offset = direction == 0.0F ? away.scale(1.3) : away.yRot(direction * (float) Math.PI / 2.0F).scale(1.5).add(away.scale(0.4));
         BlockPos feet = BlockPos.containing(this.milim.position().add(offset));
         Vec3 destination = Vec3.atBottomCenterOf(feet);
         Level level = this.milim.level();
         AABB box = this.milim.getBoundingBox().move(destination.subtract(this.milim.position()));
         double distance = destination.distanceToSqr(this.target.position());
         if (!(destination.distanceToSqr(this.milim.position()) < 0.36)
            && !(distance < 1.44)
            && !(distance > 12.25)
            && level.hasChunkAt(feet)
            && level.getWorldBorder().isWithinBounds(box)
            && level.getFluidState(feet).isEmpty()
            && level.getBlockState(feet.below()).isFaceSturdy(level, feet.below(), Direction.UP)
            && level.noCollision(this.milim, box)) {
            BlockHitResult sight = level.clip(
               new ClipContext(destination.add(0.0, this.milim.getEyeHeight(), 0.0), this.target.getEyePosition(), Block.COLLIDER, Fluid.NONE, this.milim)
            );
            if (sight.getType() == Type.MISS) {
               Path path = this.milim.getNavigation().createPath(feet, 0);
               if (path != null && path.canReach() && path.getNodeCount() <= 5) {
                  boolean safe = true;

                  for (int i = 0; i < path.getNodeCount(); i++) {
                     Node node = path.getNode(i);
                     BlockPos step = new BlockPos(node.x, node.y, node.z);
                     if (node.costMalus > 0.0F
                        || node.y != feet.getY()
                        || !level.getFluidState(step).isEmpty()
                        || !level.getBlockState(step.below()).isFaceSturdy(level, step.below(), Direction.UP)) {
                        safe = false;
                        break;
                     }
                  }

                  if (safe) {
                     return path;
                  }
               }
            }
         }
      }

      return null;
   }
}
