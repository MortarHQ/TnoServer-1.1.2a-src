package com.mortal_cultivation.entity.ai;

import com.mortal_cultivation.entity.SpiritMonkeyEntity;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class SpiritMonkeyCanopyClimb {
   private final List<SpiritMonkeyCanopyClimb.Grip> grips;
   private final Vec3 start;
   private final BlockPos trunk;
   private int next;
   private int stalledTicks;
   private Vec3 progressPosition;

   private SpiritMonkeyCanopyClimb(List<SpiritMonkeyCanopyClimb.Grip> grips, Vec3 start, BlockPos trunk) {
      this.grips = grips;
      this.start = start;
      this.trunk = trunk;
      this.progressPosition = start;
   }

   public double landingY() {
      return this.grips.getLast().position().y;
   }

   public SpiritMonkeyCanopyClimb reverse(Vec3 current) {
      SpiritMonkeyCanopyClimb.Grip underStart = this.grips.get(0);
      SpiritMonkeyCanopyClimb.Grip underEdge = this.grips.get(1);
      SpiritMonkeyCanopyClimb.Grip aboveEdge = this.grips.get(2);
      return new SpiritMonkeyCanopyClimb(
         List.of(
            aboveEdge,
            new SpiritMonkeyCanopyClimb.Grip(underEdge.position(), underEdge.block(), false),
            new SpiritMonkeyCanopyClimb.Grip(underStart.position(), underStart.block(), true),
            new SpiritMonkeyCanopyClimb.Grip(this.start, this.trunk, false)
         ),
         current,
         this.trunk
      );
   }

   @Nullable
   public static SpiritMonkeyCanopyClimb find(SpiritMonkeyEntity monkey, BlockPos trunk) {
      Level level = monkey.level();
      Vec3 start = monkey.position();
      Vec3 away = start.subtract(Vec3.atCenterOf(trunk));
      if (away.horizontalDistanceSqr() < 0.25) {
         return null;
      }

      Direction outward = Math.abs(away.x) > Math.abs(away.z)
         ? (away.x > 0.0 ? Direction.EAST : Direction.WEST)
         : (away.z > 0.0 ? Direction.SOUTH : Direction.NORTH);
      BlockPos ceiling = BlockPos.containing(start.x, Math.ceil(monkey.getBoundingBox().maxY), start.z);

      for (int gap = 0; gap < 3 && !isGrip(level, ceiling); gap++) {
         ceiling = ceiling.above();
      }

      if (!isGrip(level, ceiling)) {
         return null;
      }

      BlockPos edge = ceiling;

      for (int length = 0; length < 5 && isGrip(level, edge.relative(outward)); length++) {
         edge = edge.relative(outward);
      }

      if (isGrip(level, edge.relative(outward))) {
         return null;
      }

      BlockPos top = edge.above();

      for (int height = 0; height < 3 && isGrip(level, top); height++) {
         top = top.above();
      }

      if (isGrip(level, top)) {
         return null;
      }

      double underY = ceiling.getY() - monkey.getBbHeight() - 0.04;
      if (underY < start.y - 0.1) {
         return null;
      }

      double separation = 0.5 + monkey.getBbWidth() / 2.0 + 0.03;
      Vec3 underStart = new Vec3(start.x, underY, start.z);
      Vec3 underEdge = new Vec3(
         outward.getAxis() == Axis.X ? edge.getX() + 0.5 + outward.getStepX() * separation : start.x,
         underY,
         outward.getAxis() == Axis.Z ? edge.getZ() + 0.5 + outward.getStepZ() * separation : start.z
      );
      Vec3 aboveEdge = new Vec3(underEdge.x, top.getY() + 0.02, underEdge.z);
      Vec3 landing = Vec3.atBottomCenterOf(top).add(0.0, 0.02, 0.0);
      List<SpiritMonkeyCanopyClimb.Grip> grips = List.of(
         new SpiritMonkeyCanopyClimb.Grip(underStart, ceiling, false),
         new SpiritMonkeyCanopyClimb.Grip(underEdge, edge, true),
         new SpiritMonkeyCanopyClimb.Grip(aboveEdge, top.below(), false),
         new SpiritMonkeyCanopyClimb.Grip(landing, top.below(), false)
      );
      Vec3 previous = start;

      for (SpiritMonkeyCanopyClimb.Grip grip : grips) {
         AABB sweep = monkey.getBoundingBox().move(previous.subtract(start)).expandTowards(grip.position().subtract(previous));
         if (!hasRoom(monkey, sweep)) {
            return null;
         }

         previous = grip.position();
      }

      return new SpiritMonkeyCanopyClimb(grips, start, trunk.immutable());
   }

   public SpiritMonkeyCanopyClimb.Result tick(SpiritMonkeyEntity monkey) {
      if (monkey.position().distanceToSqr(this.progressPosition) > 0.0016) {
         this.progressPosition = monkey.position();
         this.stalledTicks = 0;
      } else if (++this.stalledTicks > 20) {
         return SpiritMonkeyCanopyClimb.Result.FAILED;
      }

      while (this.next < this.grips.size() && monkey.position().distanceToSqr(this.grips.get(this.next).position()) < 6.250000000000001E-4) {
         this.next++;
      }

      if (this.next == this.grips.size()) {
         monkey.stopClimbing();
         monkey.setDeltaMovement(0.0, -0.08, 0.0);
         return SpiritMonkeyCanopyClimb.Result.FINISHED;
      }

      SpiritMonkeyCanopyClimb.Grip grip = this.grips.get(this.next);
      Vec3 delta = grip.position().subtract(monkey.position());
      Vec3 motion = delta.normalize().scale(Math.min(grip.hanging() ? 0.1 : 0.14, delta.length()));
      BlockPos support = grip.hanging() ? new BlockPos((int)Math.floor(monkey.getX()), grip.block().getY(), (int)Math.floor(monkey.getZ())) : grip.block();
      if (!isGrip(monkey.level(), support) && grip.hanging()) {
         AABB reach = monkey.getBoundingBox().inflate(0.22, 0.0, 0.22);

         for (BlockPos candidate : BlockPos.betweenClosed(
            BlockPos.containing(reach.minX, grip.block().getY(), reach.minZ), BlockPos.containing(reach.maxX, grip.block().getY(), reach.maxZ)
         )) {
            if (isGrip(monkey.level(), candidate)) {
               support = candidate.immutable();
               break;
            }
         }
      }

      if (isGrip(monkey.level(), support)
         && hasReachableSupport(monkey, monkey.getBoundingBox())
         && hasReachableSupport(monkey, monkey.getBoundingBox().move(motion))
         && hasRoom(monkey, monkey.getBoundingBox().expandTowards(motion))) {
         monkey.climbSurface(support, motion, grip.hanging());
         return SpiritMonkeyCanopyClimb.Result.MOVING;
      } else {
         return SpiritMonkeyCanopyClimb.Result.FAILED;
      }
   }

   private static boolean isGrip(Level level, BlockPos pos) {
      return level.hasChunkAt(pos) && (level.getBlockState(pos).is(BlockTags.LOGS) || level.getBlockState(pos).is(BlockTags.LEAVES));
   }

   private static boolean hasReachableSupport(SpiritMonkeyEntity monkey, AABB bounds) {
      AABB reach = bounds.inflate(0.22, 0.12, 0.22);

      for (BlockPos pos : BlockPos.betweenClosed(
         BlockPos.containing(reach.minX, reach.minY, reach.minZ), BlockPos.containing(reach.maxX, reach.maxY, reach.maxZ)
      )) {
         if (isGrip(monkey.level(), pos)) {
            return true;
         }
      }

      return false;
   }

   private static boolean hasRoom(SpiritMonkeyEntity monkey, AABB bounds) {
      Level level = monkey.level();
      return level.hasChunksAt(BlockPos.containing(bounds.minX, bounds.minY, bounds.minZ), BlockPos.containing(bounds.maxX, bounds.maxY, bounds.maxZ))
         && level.getWorldBorder().isWithinBounds(bounds)
         && level.noCollision(monkey, bounds)
         && !level.containsAnyLiquid(bounds);
   }

   private record Grip(Vec3 position, BlockPos block, boolean hanging) {
   }

   public enum Result {
      MOVING,
      FINISHED,
      FAILED;
   }
}
