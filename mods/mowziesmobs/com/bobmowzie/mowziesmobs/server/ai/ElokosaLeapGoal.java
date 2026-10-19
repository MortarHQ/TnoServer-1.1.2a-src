package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa;
import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedEntry.IntrusiveBase;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

public abstract class ElokosaLeapGoal extends Goal {
   protected final EntityElokosa elokosa;
   private int timer;
   private final UniformInt timeBetweenLongJumps;
   private int ticksCantFindLeap = 0;
   protected List<ElokosaLeapGoal.PossibleJump> jumpCandidates = Lists.newArrayList();
   private static final int ATTEMPTS_PER_TICK = 20;

   public ElokosaLeapGoal(EntityElokosa elokosa, UniformInt cooldown) {
      this.elokosa = elokosa;
      this.timeBetweenLongJumps = cooldown;
      this.timer = (int)(this.timeBetweenLongJumps.sample(elokosa.getRandom()) * 0.2F);
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   protected boolean ignoreTimerCondition() {
      return false;
   }

   protected Stream<BlockPos> generateStream() {
      BlockPos blockpos = this.elokosa.blockPosition();
      int i = blockpos.getX();
      int j = blockpos.getY() + 6;
      int k = blockpos.getZ();
      int maxLongJumpWidth = 26;
      int maxLongJumpHeight = 20;
      return BlockPos.betweenClosedStream(
            i - maxLongJumpWidth, j - maxLongJumpHeight, k - maxLongJumpWidth, i + maxLongJumpWidth, j + maxLongJumpHeight, k + maxLongJumpWidth
         )
         .filter(p -> p.distSqr(blockpos) <= maxLongJumpWidth * maxLongJumpWidth);
   }

   protected void generateCandidates() {
      BlockPos blockpos = this.elokosa.blockPosition();
      this.jumpCandidates = this.generateStream()
         .filter(
            p -> !p.equals(blockpos)
               && (this.elokosa.level().getBlockState(p).isSolid() || this.elokosa.level().getBlockState(p).isLadder(this.elokosa.level(), p, this.elokosa))
         )
         .<ElokosaLeapGoal.PossibleJump>mapMulti((p, downstream) -> {
            if (this.elokosa.level().getBlockState(p).isLadder(this.elokosa.level(), p, this.elokosa)) {
               downstream.accept(new ElokosaLeapGoal.PossibleJump(p.immutable(), Direction.UP, this.scoreJump(p, Direction.UP)));
            } else {
               downstream.accept(new ElokosaLeapGoal.PossibleJump(p.immutable(), Direction.UP, this.scoreJump(p, Direction.UP)));
               downstream.accept(new ElokosaLeapGoal.PossibleJump(p.immutable(), Direction.DOWN, this.scoreJump(p, Direction.DOWN)));
               downstream.accept(new ElokosaLeapGoal.PossibleJump(p.immutable(), Direction.NORTH, this.scoreJump(p, Direction.NORTH)));
               downstream.accept(new ElokosaLeapGoal.PossibleJump(p.immutable(), Direction.SOUTH, this.scoreJump(p, Direction.SOUTH)));
               downstream.accept(new ElokosaLeapGoal.PossibleJump(p.immutable(), Direction.EAST, this.scoreJump(p, Direction.EAST)));
               downstream.accept(new ElokosaLeapGoal.PossibleJump(p.immutable(), Direction.WEST, this.scoreJump(p, Direction.WEST)));
            }
         })
         .filter(j -> !this.elokosa.level().getBlockState(j.getJumpTarget().offset(j.getSurfaceDirection().getNormal())).isSolid())
         .sorted(Comparator.comparingInt(p -> -p.getWeight().asInt()))
         .collect(Collectors.toCollection(Lists::newArrayList));
   }

   protected int scoreJumpRandomnessAmount() {
      return 30;
   }

   protected int scoreJump(BlockPos pos, Direction dir) {
      int baseValue = 16;
      BlockState state = this.elokosa.level().getBlockState(pos);
      float treeBonus = !state.is(BlockTags.LOGS) && !state.is(BlockTags.LEAVES) ? 0.0F : 6.0F;
      int randomnessAmount = this.scoreJumpRandomnessAmount();
      int randomnessScore = this.elokosa.getRandom().nextIntBetweenInclusive(-randomnessAmount, randomnessAmount);
      float heightScore = (int)(pos.getY() - this.elokosa.getY());
      float pathfindingCost = this.elokosa.level().getPathfindingCostFromLightLevels(pos);
      int rawScore = baseValue + Mth.ceil(heightScore - pathfindingCost + treeBonus) + randomnessScore;
      return Math.max(rawScore, 1);
   }

   public boolean canUse() {
      if (this.elokosa.isOnGroundOrClinging()) {
         this.timer--;
         if (this.timer <= 0 || this.ignoreTimerCondition()) {
            if (this.jumpCandidates.isEmpty()) {
               this.generateCandidates();
            }

            boolean foundLeap = this.pickCandidate();
            if (foundLeap) {
               this.ticksCantFindLeap = 0;
            } else {
               this.ticksCantFindLeap++;
            }

            return foundLeap;
         }
      }

      return false;
   }

   protected Optional<ElokosaLeapGoal.PossibleJump> getJumpCandidate() {
      if (this.jumpCandidates.isEmpty()) {
         return Optional.empty();
      }

      Optional<ElokosaLeapGoal.PossibleJump> optional = this.jumpCandidates.stream().findFirst();
      optional.ifPresent(this.jumpCandidates::remove);
      return optional;
   }

   protected boolean pickCandidate() {
      for (int attempts = 0; !this.jumpCandidates.isEmpty(); attempts++) {
         if (attempts > 20) {
            return false;
         }

         Optional<ElokosaLeapGoal.PossibleJump> optional = this.getJumpCandidate();
         if (optional.isPresent()) {
            ElokosaLeapGoal.PossibleJump possiblejump = optional.get();
            BlockPos blockpos = possiblejump.getJumpTarget();
            if (this.isAcceptableLandingPosition(possiblejump)) {
               Vec3 vec3 = this.getLandingPositionFromJump(possiblejump);
               if (this.elokosa.getNightForm() && possiblejump.getSurfaceDirection() == Direction.DOWN) {
                  vec3 = vec3.add(0.0, 0.5, 0.0);
               }

               Vec3 vec31 = this.elokosa.calculateOptimalJumpVector(this.elokosa, vec3);
               if (vec31 != null) {
                  PathNavigation pathnavigation = this.elokosa.getNavigation();
                  Path path = pathnavigation.createPath(blockpos, 2);
                  if (path == null || !path.canReach()) {
                     this.elokosa.jumpVel = vec31;
                     this.elokosa.jumpGoalPos = vec3;
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   protected final Vec3 getLandingPositionFromJump(ElokosaLeapGoal.PossibleJump jump) {
      EntityDimensions entityDimensions = this.elokosa.getDimensions(Pose.STANDING);
      Vec3 leapLocation;
      if (jump.getSurfaceDirection().getAxis().isHorizontal()) {
         leapLocation = jump.getJumpTarget().offset(jump.getSurfaceDirection().getNormal()).getBottomCenter();
      } else if (jump.getSurfaceDirection() == Direction.DOWN) {
         leapLocation = jump.getJumpTarget().getBottomCenter().subtract(0.0, entityDimensions.height(), 0.0);
      } else {
         leapLocation = jump.getJumpTarget().above().getBottomCenter();
      }

      return leapLocation;
   }

   protected boolean isAcceptableLandingPosition(ElokosaLeapGoal.PossibleJump jump) {
      Level level = this.elokosa.level();
      if (!level.getBlockState(jump.getJumpTarget()).isSolid()
         && !level.getBlockState(jump.getJumpTarget()).isLadder(level, jump.getJumpTarget(), this.elokosa)) {
         return false;
      }

      if (this.elokosa.distanceToSqr(jump.getJumpTarget().getBottomCenter()) < 6.0) {
         return false;
      }

      EntityDimensions entityDimensions = this.elokosa.getDimensions(Pose.STANDING);
      Vec3 leapLocation = this.getLandingPositionFromJump(jump);
      AABB bounds = entityDimensions.makeBoundingBox(leapLocation);
      return level.noCollision(this.elokosa, bounds) && level.isUnobstructed(this.elokosa, Shapes.create(bounds)) && !level.containsAnyLiquid(bounds);
   }

   public void start() {
      super.start();
      this.jumpCandidates.clear();
      AbilityHandler.INSTANCE.sendAbilityMessage(this.elokosa, EntityElokosa.LEAP_ABILITY);
      this.timer = this.timeBetweenLongJumps.sample(this.elokosa.getRandom());
   }

   public void tick() {
      super.tick();
   }

   public boolean canContinueToUse() {
      Ability<?> ability = AbilityHandler.INSTANCE.getAbility(this.elokosa, EntityElokosa.LEAP_ABILITY);
      return ability != null && ability.isUsing();
   }

   public void stop() {
      super.stop();
   }

   public static class PossibleJump extends IntrusiveBase {
      private final BlockPos jumpTarget;
      private final Direction surfaceDirection;

      public PossibleJump(BlockPos jumpTarget, Direction surfaceDirection, int weight) {
         super(weight);
         this.jumpTarget = jumpTarget;
         this.surfaceDirection = surfaceDirection;
      }

      public BlockPos getJumpTarget() {
         return this.jumpTarget;
      }

      public Direction getSurfaceDirection() {
         return this.surfaceDirection;
      }
   }
}
