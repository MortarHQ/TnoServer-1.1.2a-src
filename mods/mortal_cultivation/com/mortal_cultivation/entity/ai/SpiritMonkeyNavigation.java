package com.mortal_cultivation.entity.ai;

import com.mortal_cultivation.entity.SpiritMonkeyEntity;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class SpiritMonkeyNavigation extends GroundPathNavigation {
   @Nullable
   private SpiritMonkeyNavigation.Ledge climb;
   private int climbTicks;
   private Vec3 climbProgress = Vec3.ZERO;
   private int climbStalledTicks;
   @Nullable
   private BlockPos progressTarget;
   @Nullable
   private BlockPos progressNode;
   private double bestNodeDistance;
   private long nodeProgressTime;
   private Vec3 progressAnchor = Vec3.ZERO;
   private long anchorTime;
   @Nullable
   private BlockPos failedTarget;
   private Vec3 failedPosition = Vec3.ZERO;
   private long retryAfter;

   public SpiritMonkeyNavigation(SpiritMonkeyEntity monkey, Level level) {
      super(monkey, level);
   }

   protected boolean canUpdatePath() {
      return this.climb != null || super.canUpdatePath();
   }

   protected PathFinder createPathFinder(int maxVisitedNodes) {
      this.nodeEvaluator = new WalkNodeEvaluator() {
         public PathType getPathType(PathfindingContext context, int x, int y, int z) {
            PathType type = super.getPathType(context, x, y, z);
            BlockPos pos = new BlockPos(x, y, z);
            return type != PathType.LEAVES
                  && (!context.getBlockState(pos).is(BlockTags.LEAVES) || context.getBlockState(pos).getCollisionShape(context.level(), pos).isEmpty())
               ? type
               : PathType.BLOCKED;
         }

         public int getNeighbors(Node[] neighbors, Node from) {
            int count = super.getNeighbors(neighbors, from);
            Vec3 start = new Vec3(from.x + 0.5, this.getFloorLevel(from.asBlockPos()), from.z + 0.5);

            for (Direction side : Plane.HORIZONTAL) {
               BlockPos wall = BlockPos.containing(start).relative(side);
               SpiritMonkeyNavigation.Ledge ledge = SpiritMonkeyNavigation.this.findLedge(start, wall, side);
               if (ledge != null) {
                  BlockPos top = BlockPos.containing(ledge.landing());
                  PathType type = this.getCachedPathType(top.getX(), top.getY(), top.getZ());
                  if (type == PathType.WALKABLE && !(this.mob.getPathfindingMalus(type) < 0.0F)) {
                     Node next = this.getNode(top);
                     next.type = type;
                     next.costMalus = Math.max(next.costMalus, this.mob.getPathfindingMalus(type) + 2.0F);
                     if (this.isNeighborValid(next, from) && count < neighbors.length) {
                        neighbors[count++] = next;
                     }
                  }
               }
            }

            return count;
         }
      };
      this.nodeEvaluator.setCanPassDoors(true);
      return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
   }

   public boolean isStableDestination(BlockPos pos) {
      BlockPos support = pos.below();
      return this.level.getBlockState(support).is(BlockTags.LEAVES)
         ? this.hasClimbingFace(support, Direction.UP)
            && this.hasRoom(this.mob.getBoundingBox().move(Vec3.atBottomCenterOf(pos).subtract(this.mob.position())))
         : super.isStableDestination(pos);
   }

   public boolean moveTo(Entity target, double speed) {
      return this.moveTo(this.createPath(target, 1), speed);
   }

   public boolean moveTo(@Nullable Path nextPath, double speed) {
      if (nextPath != null
         && (
            this.failedTarget == null
               || this.level.getGameTime() >= this.retryAfter
               || !(nextPath.getTarget().distSqr(this.failedTarget) <= 4.0)
               || !(this.mob.position().distanceToSqr(this.failedPosition) < 4.0)
         )) {
         if (this.climb != null && !nextPath.sameAs(this.path) && !this.includesClimbLanding(nextPath)) {
            this.cancelClimb();
            this.clearMovementInput();
         }

         boolean moving = super.moveTo(nextPath, speed);
         if (!moving) {
            if (this.path != null && !this.path.canReach()) {
               this.failRoute();
            } else {
               this.stop();
            }

            return false;
         } else {
            if (this.progressTarget == null || this.progressTarget.distSqr(nextPath.getTarget()) > 4.0) {
               this.progressTarget = nextPath.getTarget().immutable();
               this.progressNode = null;
               this.progressAnchor = this.mob.position();
               this.anchorTime = this.level.getGameTime();
            }

            return true;
         }
      } else {
         this.stop();
         return false;
      }
   }

   private boolean includesClimbLanding(Path candidate) {
      BlockPos landing = BlockPos.containing(this.climb.landing());

      for (int index = candidate.getNextNodeIndex(); index < Math.min(candidate.getNodeCount(), candidate.getNextNodeIndex() + 3); index++) {
         if (candidate.getNodePos(index).equals(landing)) {
            return true;
         }
      }

      return false;
   }

   public void tick() {
      if (this.climb == null) {
         boolean followingPath = !this.isDone();
         super.tick();
         SpiritMonkeyEntity monkey = (SpiritMonkeyEntity)this.mob;
         if (!this.isDone()) {
            if (this.checkProgress()) {
               if (this.path != null && !this.path.isDone() && monkey.onGround() && this.canClimb(monkey) && monkey.getClimbingTrunk() == null) {
                  Vec3 next = this.path.getNextEntityPos(monkey);
                  if (!(next.y <= monkey.getY() + monkey.maxUpStep() + 0.1)) {
                     Vec3 delta = next.subtract(monkey.position());
                     Direction side = Math.abs(delta.x) > Math.abs(delta.z)
                        ? (delta.x > 0.0 ? Direction.EAST : Direction.WEST)
                        : (delta.z > 0.0 ? Direction.SOUTH : Direction.NORTH);
                     double reach = monkey.getBbWidth() / 2.0 + 0.18;
                     BlockPos wall = BlockPos.containing(monkey.getX() + side.getStepX() * reach, monkey.getY() + 0.05, monkey.getZ() + side.getStepZ() * reach);
                     SpiritMonkeyNavigation.Ledge ledge = this.findLedge(monkey.position(), wall, side);
                     if (ledge != null && Math.abs(ledge.landing().y - next.y) < 0.1) {
                        this.climb = ledge;
                        this.climbTicks = 0;
                        this.climbStalledTicks = 0;
                        this.climbProgress = monkey.position();
                        this.clearMovementInput();
                        monkey.climbTrunk(ledge.wall(), 0.0);
                     }
                  }
               }
            }
         } else {
            if (followingPath) {
               if (this.path != null && !this.path.canReach()) {
                  this.failRoute();
               } else {
                  this.stop();
               }
            }
         }
      }
   }

   private boolean checkProgress() {
      long now = this.level.getGameTime();
      BlockPos node = this.path.getNextNodePos();
      double distance = this.mob.position().distanceTo(this.path.getNextEntityPos(this.mob));
      if (!node.equals(this.progressNode)) {
         this.progressNode = node.immutable();
         this.bestNodeDistance = distance;
         this.nodeProgressTime = now;
      } else if (distance < this.bestNodeDistance - 0.08) {
         this.bestNodeDistance = distance;
         this.nodeProgressTime = now;
      }

      if (this.mob.position().distanceToSqr(this.progressAnchor) >= 1.0) {
         this.progressAnchor = this.mob.position();
         this.anchorTime = now;
      }

      if (now - this.nodeProgressTime <= 60L && now - this.anchorTime <= 80L) {
         return true;
      }

      this.failRoute();
      return false;
   }

   private void failRoute() {
      if (this.path != null) {
         this.failedTarget = this.path.getTarget().immutable();
         this.failedPosition = this.mob.position();
         this.retryAfter = this.level.getGameTime() + 60L;
      }

      this.stop();
   }

   public boolean isClimbingLedge() {
      return this.climb != null;
   }

   public boolean travelClimb() {
      if (this.climb == null) {
         return false;
      }

      SpiritMonkeyEntity monkey = (SpiritMonkeyEntity)this.mob;
      SpiritMonkeyNavigation.Ledge ledge = this.climb;
      if (this.canClimb(monkey) && ledge.wall().equals(monkey.getClimbingTrunk()) && ++this.climbTicks <= 80 && this.hasWall(ledge)) {
         if (monkey.position().distanceToSqr(this.climbProgress) >= 0.01) {
            this.climbProgress = monkey.position();
            this.climbStalledTicks = 0;
         } else if (++this.climbStalledTicks > 20) {
            this.failRoute();
            return false;
         }

         double remainingY = ledge.landing().y - monkey.getY();
         Vec3 horizontal = ledge.landing().subtract(monkey.position()).multiply(1.0, 0.0, 1.0);
         if (remainingY <= 0.005 && horizontal.lengthSqr() < 0.0036) {
            this.climb = null;
            monkey.stopClimbing();
            this.clearMovementInput();
            this.progressNode = null;
            this.progressAnchor = monkey.position();
            this.anchorTime = this.level.getGameTime();
            monkey.setDeltaMovement(0.0, -0.08, 0.0);
            return false;
         } else {
            Vec3 motion = remainingY > 0.005
               ? Vec3.ZERO.add(0.0, Math.min(0.16, remainingY), 0.0)
               : horizontal.normalize().scale(Math.min(0.12, horizontal.length()));
            monkey.moveAlongClimbingFace(ledge.wall(), motion);
            return true;
         }
      } else {
         this.failRoute();
         return false;
      }
   }

   public void stop() {
      super.stop();
      this.hasDelayedRecomputation = false;
      this.cancelClimb();
      this.progressTarget = null;
      this.progressNode = null;
      this.clearMovementInput();
   }

   private void cancelClimb() {
      if (this.climb != null) {
         this.climb = null;
         ((SpiritMonkeyEntity)this.mob).stopClimbing();
      }
   }

   private void clearMovementInput() {
      this.mob.getMoveControl().setWantedPosition(this.mob.getX(), this.mob.getY(), this.mob.getZ(), 0.0);
      this.mob.getJumpControl().tick();
      this.mob.setJumping(false);
      this.mob.setSpeed(0.0F);
      this.mob.setXxa(0.0F);
      this.mob.setZza(0.0F);
   }

   private boolean canClimb(SpiritMonkeyEntity monkey) {
      return !monkey.isSleeping() && !monkey.isEatingFruit() && !monkey.isTreeJumping() && !monkey.isInLiquid() && !monkey.isPassenger() && !monkey.isOnFire();
   }

   @Nullable
   private SpiritMonkeyNavigation.Ledge findLedge(Vec3 start, BlockPos wall, Direction side) {
      BlockPos top = wall;

      for (int height = 0; height < 3 && this.hasClimbingFace(top, side.getOpposite()); height++) {
         top = top.above();
      }

      double rise = top.getY() - start.y;
      BlockPos support = top.below();
      if (!(rise <= this.mob.maxUpStep() + 0.1) && !(rise > 3.01) && this.hasClimbingFace(support, Direction.UP)) {
         Vec3 landing = Vec3.atBottomCenterOf(top).add(0.0, 0.02, 0.0);
         AABB bounds = this.mob.getBoundingBox().move(start.subtract(this.mob.position()));
         Vec3 lift = new Vec3(0.0, landing.y - start.y, 0.0);
         AABB ascent = bounds.expandTowards(lift);
         AABB crossing = bounds.move(lift).expandTowards(landing.subtract(start.add(lift)));
         return this.hasRoom(ascent) && this.hasRoom(crossing) ? new SpiritMonkeyNavigation.Ledge(wall.immutable(), support, landing, side) : null;
      } else {
         return null;
      }
   }

   private boolean hasWall(SpiritMonkeyNavigation.Ledge ledge) {
      for (BlockPos pos = ledge.wall(); pos.getY() <= ledge.support().getY(); pos = pos.above()) {
         if (!this.hasClimbingFace(pos, ledge.side().getOpposite())) {
            return false;
         }
      }

      return this.hasClimbingFace(ledge.support(), Direction.UP);
   }

   private boolean hasClimbingFace(BlockPos pos, Direction face) {
      if (!this.level.hasChunkAt(pos)) {
         return false;
      }

      BlockState state = this.level.getBlockState(pos);
      return state.isFaceSturdy(this.level, pos, face) || state.is(BlockTags.LEAVES) && Block.isFaceFull(state.getCollisionShape(this.level, pos), face);
   }

   private boolean hasRoom(AABB bounds) {
      return this.level.hasChunksAt(BlockPos.containing(bounds.minX, bounds.minY, bounds.minZ), BlockPos.containing(bounds.maxX, bounds.maxY, bounds.maxZ))
         && this.level.getWorldBorder().isWithinBounds(bounds)
         && this.level.noCollision(this.mob, bounds)
         && !this.level.containsAnyLiquid(bounds);
   }

   private record Ledge(BlockPos wall, BlockPos support, Vec3 landing, Direction side) {
   }
}
