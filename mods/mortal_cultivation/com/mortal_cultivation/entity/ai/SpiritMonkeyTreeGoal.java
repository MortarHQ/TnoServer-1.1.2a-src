package com.mortal_cultivation.entity.ai;

import com.mortal_cultivation.entity.SpiritMonkeyEntity;
import com.mortal_cultivation.registry.BlockRegistry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class SpiritMonkeyTreeGoal extends Goal {
   private final SpiritMonkeyEntity monkey;
   @Nullable
   private SpiritMonkeyTreeGoal.TreeRoute route;
   @Nullable
   private SpiritMonkeyTreeGoal.TreeJump pendingJump;
   @Nullable
   private SpiritMonkeyCanopyClimb canopyClimb;
   private final Deque<SpiritMonkeyCanopyClimb> returnClimbs = new ArrayDeque<>();
   private boolean returning;
   private boolean canopyAttempted;
   private int approachStallTicks;
   private double bestApproachDistance;
   private long nextSearch;
   private int age;
   private int restTicks;
   private int jumps;
   private boolean jumpAttempted;
   private boolean attached;
   private boolean descending;

   public SpiritMonkeyTreeGoal(SpiritMonkeyEntity monkey) {
      this.monkey = monkey;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
   }

   public boolean canUse() {
      if (this.monkey.level().isDay()
         && this.monkey.onGround()
         && this.monkey.getTarget() == null
         && !this.monkey.isInLiquid()
         && !this.monkey.isPassenger()
         && !this.monkey.isSleeping()
         && !this.monkey.isInLove()
         && !this.monkey.isEatingFruit()
         && this.monkey.level().getGameTime() >= this.nextSearch) {
         this.nextSearch = this.monkey.level().getGameTime() + 120L + this.monkey.getRandom().nextInt(120);
         this.route = this.findTree();
         return this.route != null;
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      return this.route != null
         && this.age < 900
         && this.monkey.getTarget() == null
         && !this.monkey.isInLiquid()
         && !this.monkey.isOnFire()
         && !this.monkey.isPassenger()
         && this.monkey.level().hasChunkAt(this.route.trunk())
         && this.monkey.level().getBlockState(this.route.trunk()).is(BlockTags.LOGS)
         && (this.pendingJump != null || this.canopyClimb != null || !this.attached || this.monkey.isTouchingTree())
         && (!this.descending || !this.monkey.onGround() || this.canopyClimb != null || !this.returnClimbs.isEmpty());
   }

   public void start() {
      this.age = 0;
      this.restTicks = 100 + this.monkey.getRandom().nextInt(80);
      this.attached = false;
      this.descending = false;
      this.jumpAttempted = false;
      this.jumps = 0;
      this.pendingJump = null;
      this.canopyClimb = null;
      this.returnClimbs.clear();
      this.returning = false;
      this.canopyAttempted = false;
      this.approachStallTicks = 0;
      this.bestApproachDistance = Double.MAX_VALUE;
      this.monkey.getNavigation().moveTo(this.route.path(), 1.0);
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void tick() {
      this.age++;
      if (this.canopyClimb != null) {
         SpiritMonkeyCanopyClimb.Result result = this.canopyClimb.tick(this.monkey);
         if (result == SpiritMonkeyCanopyClimb.Result.MOVING) {
            return;
         }

         SpiritMonkeyCanopyClimb finished = this.canopyClimb;
         this.canopyClimb = null;
         if (result == SpiritMonkeyCanopyClimb.Result.FINISHED && !this.returning) {
            this.returnClimbs.push(finished);
         }

         if (result == SpiritMonkeyCanopyClimb.Result.FINISHED && (!this.monkey.level().isDay() || this.monkey.isInLove())) {
            this.descending = true;
            if (this.beginReturnClimb()) {
               return;
            }
         }

         this.returning = false;
         if (result == SpiritMonkeyCanopyClimb.Result.FAILED
            || this.monkey.getY() >= this.trunkTop(this.route.trunk()).getY() + 0.5
            || horizontalDistanceSqr(this.monkey.position(), Vec3.atCenterOf(this.route.trunk())) > 1.3224999999999998) {
            this.monkey.stopClimbing();
            this.age = 900;
            return;
         }

         this.route = new SpiritMonkeyTreeGoal.TreeRoute(this.route.trunk(), this.trunkTop(this.route.trunk()).getY(), null, this.route.fruit());
         this.canopyAttempted = false;
      }

      if (this.pendingJump != null) {
         if (this.monkey.isTreeJumping()) {
            this.monkey.getLookControl().setLookAt(this.pendingJump.landing());
            return;
         }

         if (!this.pendingJump.trunk().equals(this.monkey.getClimbingTrunk())) {
            this.age = 900;
            return;
         }

         this.route = new SpiritMonkeyTreeGoal.TreeRoute(this.pendingJump.trunk(), this.monkey.getY(), null, false);
         this.pendingJump = null;
         this.attached = true;
         this.jumpAttempted = false;
         this.canopyAttempted = false;
         this.restTicks = 100 + this.monkey.getRandom().nextInt(80);
      }

      Vec3 trunk = Vec3.atCenterOf(this.route.trunk());
      if (!this.attached) {
         double approachDistance = this.monkey.position().distanceToSqr(Vec3.atBottomCenterOf(this.route.approach()));
         if (approachDistance < this.bestApproachDistance - 0.03) {
            this.bestApproachDistance = approachDistance;
            this.approachStallTicks = 0;
         } else {
            this.approachStallTicks++;
         }

         if (!this.monkey.isTouchingTree()
            || !(Math.abs(this.monkey.getY() - this.route.approach().getY()) < 0.75)
            || !(horizontalDistanceSqr(this.monkey.position(), Vec3.atBottomCenterOf(this.route.approach())) < 0.48999999999999994)) {
            if (this.age > 160 || this.approachStallTicks > 40 || !this.monkey.level().isDay() || this.monkey.isInLove()) {
               this.age = 900;
            } else if (this.monkey.getNavigation().isDone()) {
               if (horizontalDistanceSqr(this.monkey.position(), trunk) < 6.25 && Math.abs(this.monkey.getY() - this.route.approach().getY()) < 0.75) {
                  Vec3 approach = Vec3.atBottomCenterOf(this.route.approach());
                  Vec3 target = horizontalDistanceSqr(this.monkey.position(), approach) > 0.25 ? approach : trunk;
                  this.monkey.getMoveControl().setWantedPosition(target.x, this.monkey.getY(), target.z, 0.8);
               } else {
                  this.age = 900;
               }
            }

            return;
         }

         this.attached = true;
         this.monkey.getNavigation().stop();
      }

      if (!this.monkey.level().isDay() || this.monkey.isInLove()) {
         this.descending = true;
      }

      if (!this.descending || !this.beginReturnClimb()) {
         if (this.monkey.isEatingFruit()) {
            this.monkey.climbTrunk(this.route.trunk(), 0.0);
         } else if (!this.descending && (this.route.fruit() || this.age % 10 == 0) && this.monkey.harvestNearbyFruit()) {
            this.monkey.climbTrunk(this.route.trunk(), 0.0);
         } else if (this.descending
            || this.route.fruit()
            || this.jumpAttempted
            || this.jumps >= 2
            || this.monkey.isBaby()
            || !(this.monkey.getY() >= this.route.trunk().getY() + 3.0)
            || this.age % 40 != 0
            || !this.tryJump()) {
            this.monkey.getLookControl().setLookAt(trunk.x, this.monkey.getEyeY() + 0.5, trunk.z);
            if (this.descending) {
               this.monkey.climbTrunk(this.route.trunk(), -0.12);
            } else if (this.monkey.getY() >= this.route.topY() - 0.1 || this.monkey.verticalCollision && !this.monkey.onGround()) {
               if (!this.canopyAttempted) {
                  this.canopyAttempted = true;
                  this.canopyClimb = SpiritMonkeyCanopyClimb.find(this.monkey, this.route.trunk());
                  if (this.canopyClimb != null) {
                     if (this.canopyClimb.tick(this.monkey) == SpiritMonkeyCanopyClimb.Result.FAILED) {
                        this.canopyClimb = null;
                        this.age = 900;
                     }

                     return;
                  }
               }

               this.monkey.climbTrunk(this.route.trunk(), 0.0);
               Player observer = this.monkey.level().getNearestPlayer(this.monkey, 6.0);
               if (observer != null) {
                  this.monkey.getLookControl().setLookAt(observer, 20.0F, 35.0F);
               }

               if (!this.jumpAttempted && this.restTicks <= 70 && this.jumps < 2 && !this.monkey.isBaby()) {
                  this.jumpAttempted = true;
                  if (this.tryJump()) {
                     return;
                  }
               }

               if (--this.restTicks <= 0) {
                  this.descending = true;
               }
            } else {
               this.monkey.climbTrunk(this.route.trunk(), 0.16);
            }
         }
      }
   }

   private boolean tryJump() {
      SpiritMonkeyTreeGoal.TreeJump jump = this.findJump();
      if (jump == null) {
         return false;
      }

      this.pendingJump = jump;
      this.returnClimbs.clear();
      this.returning = false;
      this.jumps++;
      this.monkey.startTreeJump(jump.trunk(), jump.landing(), jump.velocity(), jump.duration());
      return true;
   }

   private boolean beginReturnClimb() {
      if (!this.returnClimbs.isEmpty() && !(this.monkey.getY() > this.returnClimbs.peek().landingY() + 0.2)) {
         this.canopyClimb = this.returnClimbs.pop().reverse(this.monkey.position());
         this.returning = true;
         if (this.canopyClimb.tick(this.monkey) == SpiritMonkeyCanopyClimb.Result.FAILED) {
            this.canopyClimb = null;
            this.monkey.stopClimbing();
            this.age = 900;
         }

         return true;
      } else {
         return false;
      }
   }

   public void stop() {
      this.monkey.stopClimbing();
      this.monkey.getNavigation().stop();
      this.route = null;
      this.pendingJump = null;
      this.canopyClimb = null;
      this.returnClimbs.clear();
      this.nextSearch = this.monkey.level().getGameTime() + 200L + this.monkey.getRandom().nextInt(200);
   }

   @Nullable
   private SpiritMonkeyTreeGoal.TreeRoute findTree() {
      Level level = this.monkey.level();
      BlockPos origin = this.monkey.blockPosition();
      List<BlockPos> trunks = new ArrayList<>();

      for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-8, -3, -8), origin.offset(8, 3, 8))) {
         if (level.hasChunkAt(pos) && level.getBlockState(pos).is(BlockTags.LOGS) && !level.getBlockState(pos.below()).is(BlockTags.LOGS)) {
            trunks.add(pos.immutable());
         }
      }

      trunks.sort(Comparator.comparingDouble(posx -> posx.distSqr(origin)));
      List<SpiritMonkeyTreeGoal.TreeRoute> candidates = new ArrayList<>();

      for (BlockPos trunk : trunks.stream().limit(12L).toList()) {
         BlockPos top = this.trunkTop(trunk);
         if (top.getY() >= trunk.getY() + 2 && this.hasCanopy(top)) {
            boolean fruit = this.monkey.wantsFruit() && this.hasFruit(top);
            List<Direction> sides = new ArrayList<>();
            Plane.HORIZONTAL.forEach(sides::add);
            sides.sort(Comparator.comparingDouble(sidex -> trunk.relative(sidex).distSqr(origin)));

            for (Direction side : sides) {
               BlockPos approach = trunk.relative(side);
               if (level.hasChunkAt(approach)
                  && level.getBlockState(approach.below()).isFaceSturdy(level, approach.below(), Direction.UP)
                  && this.hasRoom(Vec3.atBottomCenterOf(approach))) {
                  int topY = approach.getY();
                  int y = topY + 1;

                  while (
                     y <= Math.min(top.getY(), approach.getY() + 8) && this.hasRoom(Vec3.atBottomCenterOf(new BlockPos(approach.getX(), y, approach.getZ())))
                  ) {
                     topY = y++;
                  }

                  if (topY >= approach.getY() + 2) {
                     candidates.add(
                        new SpiritMonkeyTreeGoal.TreeRoute(trunk, topY, null, fruit && this.canReachFruitOnApproach(trunk, top, side, topY), approach)
                     );
                  }
               }
            }
         }
      }

      candidates.sort(
         Comparator.<SpiritMonkeyTreeGoal.TreeRoute>comparingInt(route -> route.fruit() ? 0 : 1)
            .thenComparingDouble(route -> -(route.topY() - route.trunk().getY()))
            .thenComparingDouble(route -> route.approach().distSqr(origin))
      );

      for (SpiritMonkeyTreeGoal.TreeRoute candidate : candidates.stream().limit(4L).toList()) {
         Path path = this.monkey.getNavigation().createPath(candidate.approach(), 0);
         if (path != null && path.canReach()) {
            return new SpiritMonkeyTreeGoal.TreeRoute(candidate.trunk(), candidate.topY(), path, candidate.fruit(), candidate.approach());
         }
      }

      return null;
   }

   @Nullable
   private SpiritMonkeyTreeGoal.TreeJump findJump() {
      Level level = this.monkey.level();
      Vec3 start = this.monkey.position();
      BlockPos origin = this.monkey.blockPosition();
      List<BlockPos> targets = new ArrayList<>();

      for (int dx = -6; dx <= 6; dx++) {
         for (int dz = -6; dz <= 6; dz++) {
            BlockPos pos = origin.offset(dx, 0, dz);
            double distance = horizontalDistanceSqr(Vec3.atCenterOf(pos), Vec3.atCenterOf(this.route.trunk()));
            if (!(distance < 6.25)
               && !(distance > 49.0)
               && level.hasChunkAt(pos)
               && level.getBlockState(pos).is(BlockTags.LOGS)
               && this.hasCanopy(this.trunkTop(pos))) {
               targets.add(pos);
            }
         }
      }

      targets.sort(Comparator.comparingDouble(pos -> horizontalDistanceSqr(Vec3.atCenterOf(pos), start)));
      int checked = 0;

      for (BlockPos log : targets) {
         BlockPos base = log;

         for (int down = 0; down < 16 && level.getBlockState(base.below()).is(BlockTags.LOGS); down++) {
            base = base.below();
         }

         List<Direction> sides = new ArrayList<>();
         Plane.HORIZONTAL.forEach(sides::add);
         sides.sort(Comparator.comparingDouble(sidex -> horizontalDistanceSqr(Vec3.atCenterOf(log.relative(sidex)), start)));

         for (Direction side : sides) {
            for (int dy : new int[]{0, -1, 1, -2}) {
               double separation = 0.5 + this.monkey.getBbWidth() / 2.0 + 0.01;
               Vec3 landing = new Vec3(log.getX() + 0.5 + side.getStepX() * separation, start.y + dy, log.getZ() + 0.5 + side.getStepZ() * separation);
               if (this.hasRoom(landing) && level.getBlockState(BlockPos.containing(log.getX(), landing.y, log.getZ())).is(BlockTags.LOGS)) {
                  double distance = Math.sqrt(horizontalDistanceSqr(start, landing));
                  if (!(distance < 1.5) && !(distance > 6.5)) {
                     int duration = Mth.clamp((int)Math.ceil(distance / 0.35), 10, 18);
                     double drag = 0.98;
                     double sum = (1.0 - Math.pow(drag, duration)) / (1.0 - drag);
                     Vec3 delta = landing.subtract(start);
                     double vy = (delta.y + 0.08 * drag / (1.0 - drag) * (duration - sum)) / sum;
                     Vec3 velocity = new Vec3(delta.x / sum, vy, delta.z / sum);
                     if (!(vy > 0.85) && !(vy < 0.15) && !(velocity.horizontalDistanceSqr() > 0.42250000000000004)) {
                        if (this.isJumpClear(start, velocity, duration)) {
                           return new SpiritMonkeyTreeGoal.TreeJump(base, landing, velocity, duration);
                        }

                        if (++checked >= 20) {
                           return null;
                        }
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   private boolean isJumpClear(Vec3 start, Vec3 velocity, int duration) {
      Vec3 point = start;

      for (int tick = 0; tick < duration; tick++) {
         for (int substep = 1; substep <= 3; substep++) {
            if (!this.hasRoom(point.add(velocity.scale(substep / 3.0)))) {
               return false;
            }
         }

         point = point.add(velocity);
         velocity = new Vec3(velocity.x * 0.98, (velocity.y - 0.08) * 0.98, velocity.z * 0.98);
      }

      return true;
   }

   private BlockPos trunkTop(BlockPos trunk) {
      BlockPos top = trunk;

      while (top.getY() < trunk.getY() + 14 && this.monkey.level().getBlockState(top.above()).is(BlockTags.LOGS)) {
         top = top.above();
      }

      return top;
   }

   private boolean hasCanopy(BlockPos top) {
      Level level = this.monkey.level();

      for (BlockPos pos : BlockPos.betweenClosed(top.offset(-2, -1, -2), top.offset(2, 2, 2))) {
         if (level.hasChunkAt(pos) && level.getBlockState(pos).is(BlockTags.LEAVES)) {
            return true;
         }
      }

      return false;
   }

   private boolean hasFruit(BlockPos top) {
      Level level = this.monkey.level();

      for (BlockPos pos : BlockPos.betweenClosed(top.offset(-3, -3, -3), top.offset(3, 2, 3))) {
         if (level.hasChunkAt(pos) && level.getBlockState(pos).is((Block)BlockRegistry.SPIRIT_FRUIT_LEAVES.get())) {
            return true;
         }
      }

      return false;
   }

   private boolean canReachFruitOnApproach(BlockPos trunk, BlockPos top, Direction side, int topY) {
      Level level = this.monkey.level();
      double separation = 0.5 + this.monkey.getBbWidth() / 2.0 + 0.01;
      double x = trunk.getX() + 0.5 + side.getStepX() * separation;
      double z = trunk.getZ() + 0.5 + side.getStepZ() * separation;

      for (BlockPos fruit : BlockPos.betweenClosed(top.offset(-3, -3, -3), top.offset(3, 2, 3))) {
         if (level.hasChunkAt(fruit) && level.getBlockState(fruit).is((Block)BlockRegistry.SPIRIT_FRUIT_LEAVES.get())) {
            for (double y = trunk.getY(); y <= topY; y += 0.5) {
               if (this.monkey.canReachFruitFrom(new Vec3(x, y + this.monkey.getEyeHeight(), z), fruit)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private boolean hasRoom(Vec3 position) {
      Level level = this.monkey.level();
      AABB bounds = this.monkey.getBoundingBox().move(position.subtract(this.monkey.position()));
      return level.hasChunksAt(BlockPos.containing(bounds.minX, bounds.minY, bounds.minZ), BlockPos.containing(bounds.maxX, bounds.maxY, bounds.maxZ))
         && level.getWorldBorder().isWithinBounds(bounds)
         && level.noCollision(this.monkey, bounds)
         && !level.containsAnyLiquid(bounds);
   }

   private static double horizontalDistanceSqr(Vec3 first, Vec3 second) {
      return first.subtract(second).horizontalDistanceSqr();
   }

   private record TreeJump(BlockPos trunk, Vec3 landing, Vec3 velocity, int duration) {
   }

   private record TreeRoute(BlockPos trunk, double topY, @Nullable Path path, boolean fruit, BlockPos approach) {
      TreeRoute(BlockPos trunk, double topY, @Nullable Path path, boolean fruit) {
         this(trunk, topY, path, fruit, trunk);
      }
   }
}
