package com.iafenvoy.uranus.object.entity.pathfinding.raycoms;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.phys.Vec3;

public class PathingStuckHandler implements IStuckHandler {
   private static final double MIN_TARGET_DIST = 3.0;
   private static final int MIN_TP_DELAY = 2400;
   private static final int MIN_DIST_FOR_TP = 10;
   private final List<Direction> directions = Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
   private final Random rand = new Random();
   private int teleportRange = 0;
   private int timePerBlockDistance = 100;
   private int stuckLevel = 0;
   private int globalTimeout = 0;
   private BlockPos prevDestination = BlockPos.ZERO;
   private boolean canBreakBlocks = false;
   private boolean canPlaceLadders = false;
   private boolean canBuildLeafBridges = false;
   private boolean canTeleportGoal = false;
   private boolean takeDamageOnCompleteStuck = false;
   private float damagePct = 0.2F;
   private int completeStuckBlockBreakRange = 0;
   private boolean hadPath = false;
   private int lastPathIndex = -1;
   private int progressedNodes = 0;
   private int delayBeforeActions = 1200;
   private int delayToNextUnstuckAction = this.delayBeforeActions;
   private BlockPos moveAwayStartPos = BlockPos.ZERO;

   private PathingStuckHandler() {
   }

   public static PathingStuckHandler createStuckHandler() {
      return new PathingStuckHandler();
   }

   public static Direction getFacing(BlockPos pos, BlockPos neighbor) {
      BlockPos vector = neighbor.subtract(pos);
      return Direction.getNearest(vector.getX(), vector.getY(), -vector.getZ());
   }

   public static BlockPos findAround(Level world, BlockPos start, int vRange, int hRange, BiPredicate<BlockGetter, BlockPos> predicate) {
      if (vRange < 1 && hRange < 1) {
         return null;
      }

      if (predicate.test(world, start)) {
         return start;
      }

      int y = 0;
      int y_offset = 1;

      for (int i = 0; i < hRange + 2; i++) {
         for (int steps = 1; steps <= vRange; steps++) {
            BlockPos temp = start.offset(-steps, y, -steps);

            for (int x = 0; x <= steps; x++) {
               temp = temp.offset(1, 0, 0);
               if (predicate.test(world, temp)) {
                  return temp;
               }
            }

            for (int z = 0; z <= steps; z++) {
               temp = temp.offset(0, 0, 1);
               if (predicate.test(world, temp)) {
                  return temp;
               }
            }

            for (int x = 0; x <= steps; x++) {
               temp = temp.offset(-1, 0, 0);
               if (predicate.test(world, temp)) {
                  return temp;
               }
            }

            for (int z = 0; z <= steps; z++) {
               temp = temp.offset(0, 0, -1);
               if (predicate.test(world, temp)) {
                  return temp;
               }
            }
         }

         y += y_offset;
         y_offset = y_offset > 0 ? y_offset + 1 : y_offset - 1;
         y_offset *= -1;
         if (world.getMaxBuildHeight() <= start.getY() + y) {
            return null;
         }
      }

      return null;
   }

   @Override
   public void checkStuck(AbstractAdvancedPathNavigate navigator) {
      if (navigator.getDesiredPos() != null && !navigator.getDesiredPos().equals(BlockPos.ZERO)) {
         double distanceToGoal = navigator.getOurEntity()
            .position()
            .distanceTo(new Vec3(navigator.getDesiredPos().getX(), navigator.getDesiredPos().getY(), navigator.getDesiredPos().getZ()));
         if (distanceToGoal < 3.0) {
            this.resetGlobalStuckTimers();
         } else {
            if (this.prevDestination.equals(navigator.getDesiredPos())) {
               this.globalTimeout++;
               if (this.globalTimeout > Math.max(2400.0, this.timePerBlockDistance * Math.max(10.0, distanceToGoal))) {
                  this.completeStuckAction(navigator);
               }
            } else {
               this.resetGlobalStuckTimers();
            }

            this.prevDestination = navigator.getDesiredPos();
            if (navigator.getPath() != null && !navigator.getPath().isDone()) {
               if (navigator.getPath().getNextNodeIndex() == this.lastPathIndex) {
                  this.tryUnstuck(navigator);
               } else if (this.lastPathIndex != -1 && navigator.getPath().getTarget().distSqr(this.prevDestination) < 25.0) {
                  this.progressedNodes = navigator.getPath().getNextNodeIndex() > this.lastPathIndex ? this.progressedNodes + 1 : this.progressedNodes - 1;
                  if (this.progressedNodes > 5
                     && (navigator.getPath().getEndNode() == null || !this.moveAwayStartPos.equals(navigator.getPath().getEndNode().asBlockPos()))) {
                     this.resetStuckTimers();
                  }
               }
            } else {
               this.lastPathIndex = -1;
               this.progressedNodes = 0;
               if (!this.hadPath) {
                  this.tryUnstuck(navigator);
               }
            }

            this.lastPathIndex = navigator.getPath() != null ? navigator.getPath().getNextNodeIndex() : -1;
            this.hadPath = navigator.getPath() != null && !navigator.getPath().isDone();
         }
      }
   }

   private void resetGlobalStuckTimers() {
      this.globalTimeout = 0;
      this.prevDestination = BlockPos.ZERO;
      this.resetStuckTimers();
   }

   private void completeStuckAction(AbstractAdvancedPathNavigate navigator) {
      BlockPos desired = navigator.getDesiredPos();
      Level world = navigator.getOurEntity().level();
      Mob entity = navigator.getOurEntity();
      if (this.canTeleportGoal) {
         BlockPos tpPos = findAround(
            world,
            desired,
            10,
            10,
            (posworld, pos) -> SurfaceType.getSurfaceType(posworld, posworld.getBlockState(pos.below()), pos.below()) == SurfaceType.WALKABLE
               && SurfaceType.getSurfaceType(posworld, posworld.getBlockState(pos), pos) == SurfaceType.DROPABLE
               && SurfaceType.getSurfaceType(posworld, posworld.getBlockState(pos.above()), pos.above()) == SurfaceType.DROPABLE
         );
         if (tpPos != null) {
            entity.teleportTo(tpPos.getX() + 0.5, tpPos.getY(), tpPos.getZ() + 0.5);
         }
      }

      if (this.takeDamageOnCompleteStuck) {
         entity.hurt(new DamageSource(entity.level().damageSources().inWall().typeHolder(), entity), entity.getMaxHealth() * this.damagePct);
      }

      if (this.completeStuckBlockBreakRange > 0) {
         Direction facing = getFacing(entity.blockPosition(), navigator.getDesiredPos());

         for (int i = 1; i <= this.completeStuckBlockBreakRange; i++) {
            if (!world.isEmptyBlock(new BlockPos(entity.blockPosition()).relative(facing, i))
               || !world.isEmptyBlock(new BlockPos(entity.blockPosition()).relative(facing, i).above())) {
               this.breakBlocksAhead(world, new BlockPos(entity.blockPosition()).relative(facing, i - 1), facing);
               break;
            }
         }
      }

      navigator.stop();
      this.resetGlobalStuckTimers();
   }

   private void tryUnstuck(AbstractAdvancedPathNavigate navigator) {
      if (this.delayToNextUnstuckAction-- <= 0) {
         this.delayToNextUnstuckAction = 50;
         if (this.stuckLevel == 0) {
            this.stuckLevel++;
            this.delayToNextUnstuckAction = 100;
            navigator.stop();
         } else if (this.stuckLevel == 1) {
            this.stuckLevel++;
            this.delayToNextUnstuckAction = 200;
            navigator.stop();
            navigator.moveAwayFromXYZ(new BlockPos(navigator.getOurEntity().blockPosition()), 10.0, 1.0, false);
            navigator.getPathingOptions().setCanClimb(false);
            this.moveAwayStartPos = navigator.getOurEntity().blockPosition();
         } else {
            if (this.stuckLevel == 2 && this.teleportRange > 0 && this.hadPath) {
               assert navigator.getPath() != null;
               int index = Math.min(navigator.getPath().getNextNodeIndex() + this.teleportRange, navigator.getPath().getNodeCount() - 1);
               Node togo = navigator.getPath().getNode(index);
               navigator.getOurEntity().teleportTo(togo.x + 0.5, togo.y, togo.z + 0.5);
               this.delayToNextUnstuckAction = 300;
            }

            if (this.stuckLevel >= 3 && this.stuckLevel <= 5) {
               if (this.canPlaceLadders && this.rand.nextBoolean()) {
                  this.delayToNextUnstuckAction = 200;
                  this.placeLadders(navigator);
               } else if (this.canBuildLeafBridges && this.rand.nextBoolean()) {
                  this.delayToNextUnstuckAction = 100;
                  this.placeLeaves(navigator);
               }
            }

            if (this.stuckLevel >= 6 && this.stuckLevel <= 8 && this.canBreakBlocks) {
               this.delayToNextUnstuckAction = 200;
               this.breakBlocks(navigator);
            }

            this.chanceStuckLevel();
            if (this.stuckLevel == 9) {
               this.completeStuckAction(navigator);
               this.resetStuckTimers();
            }
         }
      }
   }

   private void chanceStuckLevel() {
      this.stuckLevel++;
      if (this.stuckLevel > 1 && this.rand.nextInt(6) == 0) {
         this.stuckLevel -= 2;
      }
   }

   private void resetStuckTimers() {
      this.delayToNextUnstuckAction = this.delayBeforeActions;
      this.lastPathIndex = -1;
      this.progressedNodes = 0;
      this.stuckLevel = 0;
      this.moveAwayStartPos = BlockPos.ZERO;
   }

   private void breakBlocksAhead(Level world, BlockPos start, Direction facing) {
      if (!world.isEmptyBlock(start.above(3))) {
         this.setAirIfPossible(world, start.above(3));
      } else if (!world.isEmptyBlock(start.above().relative(facing))) {
         this.setAirIfPossible(world, start.above().relative(facing));
      } else {
         if (!world.isEmptyBlock(start.relative(facing))) {
            this.setAirIfPossible(world, start.relative(facing));
         }
      }
   }

   private void setAirIfPossible(Level world, BlockPos pos) {
      world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
   }

   private void placeLadders(AbstractAdvancedPathNavigate navigator) {
      Level world = navigator.getOurEntity().level();
      Mob entity = navigator.getOurEntity();
      BlockPos entityPos = entity.blockPosition();

      while (world.getBlockState(entityPos).getBlock() == Blocks.LADDER) {
         entityPos = entityPos.above();
      }

      this.tryPlaceLadderAt(world, entityPos);
      this.tryPlaceLadderAt(world, entityPos.above());
      this.tryPlaceLadderAt(world, entityPos.above(2));
   }

   private void placeLeaves(AbstractAdvancedPathNavigate navigator) {
      Level world = navigator.getOurEntity().level();
      Mob entity = navigator.getOurEntity();
      Direction badFacing = getFacing(entity.blockPosition(), navigator.getDesiredPos()).getOpposite();

      for (Direction dir : this.directions) {
         if (dir != badFacing && world.isEmptyBlock(entity.blockPosition().below().relative(dir))) {
            world.setBlockAndUpdate(entity.blockPosition().below().relative(dir), Blocks.ACACIA_LEAVES.defaultBlockState());
         }
      }
   }

   private void breakBlocks(AbstractAdvancedPathNavigate navigator) {
      Level world = navigator.getOurEntity().level();
      Mob entity = navigator.getOurEntity();
      Direction facing = getFacing(entity.blockPosition(), navigator.getDesiredPos());
      this.breakBlocksAhead(world, entity.blockPosition(), facing);
   }

   private void tryPlaceLadderAt(Level world, BlockPos pos) {
      BlockState state = world.getBlockState(pos);
      if (state.getBlock() != Blocks.LADDER && !state.canOcclude() && world.getFluidState(pos).isEmpty()) {
         for (Direction dir : this.directions) {
            BlockState toPlace = (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, dir.getOpposite());
            if (world.getBlockState(pos.relative(dir)).isSolid() && toPlace.canSurvive(world, pos)) {
               world.setBlockAndUpdate(pos, toPlace);
               break;
            }
         }
      }
   }

   public PathingStuckHandler withBlockBreaks() {
      this.canBreakBlocks = true;
      return this;
   }

   public PathingStuckHandler withPlaceLadders() {
      this.canPlaceLadders = true;
      return this;
   }

   public PathingStuckHandler withBuildLeafBridges() {
      this.canBuildLeafBridges = true;
      return this;
   }

   public PathingStuckHandler withTeleportSteps(int steps) {
      this.teleportRange = steps;
      return this;
   }

   public PathingStuckHandler withTeleportOnFullStuck() {
      this.canTeleportGoal = true;
      return this;
   }

   public PathingStuckHandler withTakeDamageOnStuck(float damagePct) {
      this.damagePct = damagePct;
      this.takeDamageOnCompleteStuck = true;
      return this;
   }

   public PathingStuckHandler withTimePerBlockDistance(int time) {
      this.timePerBlockDistance = time;
      return this;
   }

   public PathingStuckHandler withDelayBeforeStuckActions(int delay) {
      this.delayBeforeActions = delay;
      return this;
   }

   public PathingStuckHandler withCompleteStuckBlockBreak(int range) {
      this.completeStuckBlockBreakRange = range;
      return this;
   }
}
