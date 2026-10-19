package com.mortal_cultivation.entity;

import com.mortal_cultivation.block.MillenniumGinsengBlock;
import com.mortal_cultivation.registry.BlockRegistry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunk.EntityCreationType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MillenniumGinsengEntity extends PathfinderMob implements GeoEntity {
   private static final int EMERGING = 0;
   private static final int FLEEING = 1;
   private static final int BURROWING = 2;
   private static final int EMERGE_TICKS = 3;
   private static final double EMERGE_JUMP_VELOCITY = 0.6;
   private static final double EMERGE_HORIZONTAL_SPEED = 0.34;
   private static final int BURROW_TICKS = 18;
   private static final int MIN_FLEE_TICKS = 40;
   private static final int NEARBY_BURIAL_DELAY = 200;
   private static final double MIN_ESCAPE_DISTANCE_SQR = 49.0;
   private static final double SAFE_THREAT_DISTANCE_SQR = 64.0;
   private static final double FLEE_SPEED = 1.4;
   private static final int SEARCH_INTERVAL = 10;
   private static final int MAX_FAILED_SEARCHES = 6;
   private static final int CANDIDATES_PER_SEARCH = 16;
   private static final int PATHS_PER_SEARCH = 3;
   private static final int SEARCH_SIGHT_RAYS = 192;
   private static final int BURIAL_SIGHT_RAYS = 160;
   private static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("animation.millennium_ginseng.idle");
   private static final RawAnimation WALK_ANIMATION = RawAnimation.begin().thenLoop("animation.millennium_ginseng.walk");
   private static final RawAnimation RUN_ANIMATION = RawAnimation.begin().thenLoop("animation.millennium_ginseng.run");
   private static final RawAnimation POP_UP_ANIMATION = RawAnimation.begin().thenPlay("animation.millennium_ginseng.pop_up");
   private static final RawAnimation BURROW_ANIMATION = RawAnimation.begin().thenPlay("animation.millennium_ginseng.burrow");
   private static final RawAnimation HURT_ANIMATION = RawAnimation.begin().thenPlay("animation.millennium_ginseng.hurt");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(MillenniumGinsengEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Long> DATA_PHASE_STARTED = SynchedEntityData.defineId(MillenniumGinsengEntity.class, EntityDataSerializers.LONG);
   @Nullable
   private UUID threatId;
   @Nullable
   private Vec3 lastThreatPosition;
   @Nullable
   private Vec3 escapeOrigin;
   @Nullable
   private BlockPos escapeTarget;
   @Nullable
   private BlockPos burialTarget;
   private int fleeTicks;
   private int totalFleeTicks;
   private int searchCooldown;
   private int failedSearches;
   private boolean wasOnGround;
   private final BlockGetter visibilityBlocks;

   public MillenniumGinsengEntity(EntityType<? extends MillenniumGinsengEntity> type, Level level) {
      super(type, level);
      this.visibilityBlocks = new MillenniumGinsengEntity.LoadedBlockView(level);
      this.setPersistenceRequired();
      this.setPathfindingMalus(PathType.WATER, 8.0F);
      this.setPathfindingMalus(PathType.WATER_BORDER, 8.0F);
      this.setPhase(0);
   }

   public static Builder createAttributes() {
      return PathfinderMob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 8.0)
         .add(Attributes.MOVEMENT_SPEED, 0.44)
         .add(Attributes.FOLLOW_RANGE, 20.0)
         .add(Attributes.STEP_HEIGHT, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_PHASE, 0);
      builder.define(DATA_PHASE_STARTED, 0L);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
   }

   public void startEscape(Player player) {
      if (this.level() instanceof ServerLevel serverLevel) {
         this.rememberThreat(player);
         this.escapeOrigin = this.position();
         this.escapeTarget = null;
         this.burialTarget = null;
         this.fleeTicks = 0;
         this.totalFleeTicks = 0;
         this.failedSearches = 0;
         this.searchCooldown = 0;
         this.setPhase(0);
         this.getNavigation().stop();
         this.move(MoverType.SELF, new Vec3(0.0, -0.125, 0.0));
         this.chooseEscapePath();
         Vec3 var6 = this.position().subtract(player.position()).multiply(1.0, 0.0, 1.0).normalize();
         Path path = this.getNavigation().getPath();
         if (path != null && !path.isDone()) {
            var6 = path.getNextEntityPos(this).subtract(this.position()).multiply(1.0, 0.0, 1.0).normalize();
         }

         if (var6.horizontalDistanceSqr() < 0.01) {
            var6 = Vec3.directionFromRotation(0.0F, player.getYRot());
         }

         this.setDeltaMovement(var6.x * 0.34, 0.6, var6.z * 0.34);
         this.hasImpulse = true;
         this.hurtMarked = true;
         if (var6.horizontalDistanceSqr() > 0.0) {
            float yaw = (float)(Mth.atan2(var6.z, var6.x) * (180.0 / Math.PI)) - 90.0F;
            this.setYRot(yaw);
            this.setYBodyRot(yaw);
            this.setYHeadRot(yaw);
         }

         this.spawnDigParticles(serverLevel, true);
         this.playSound(SoundEvents.ROOTED_DIRT_BREAK, 0.55F, 1.25F);
      }
   }

   public boolean isEmerging() {
      return (Integer)this.entityData.get(DATA_PHASE) == 0;
   }

   public boolean isFleeing() {
      return (Integer)this.entityData.get(DATA_PHASE) == 1;
   }

   public boolean isBurrowing() {
      return (Integer)this.entityData.get(DATA_PHASE) == 2;
   }

   public float getDigProgress(float partialTick) {
      float elapsed = (float)this.getPhaseAge() + partialTick;

      return switch (this.entityData.get(DATA_PHASE)) {
         case 2 -> Mth.clamp(elapsed / 18.0F, 0.0F, 1.0F);
         default -> 0.0F;
      };
   }

   private long getPhaseAge() {
      return Math.max(0L, this.level().getGameTime() - (Long)this.entityData.get(DATA_PHASE_STARTED));
   }

   private void setPhase(int phase) {
      this.entityData.set(DATA_PHASE, phase);
      this.entityData.set(DATA_PHASE_STARTED, this.level().getGameTime());
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.level() instanceof ServerLevel serverLevel && this.isAlive()) {
         if (this.escapeOrigin == null) {
            this.escapeOrigin = this.position();
         }

         if (this.searchCooldown > 0) {
            this.searchCooldown--;
         }

         if (this.onGround() && !this.wasOnGround && this.getNavigation().isDone()) {
            this.searchCooldown = 0;
         }

         this.wasOnGround = this.onGround();
         if ((Integer)this.entityData.get(DATA_PHASE) != 0) {
            this.totalFleeTicks = Math.min(200, this.totalFleeTicks + 1);
         }

         if (this.threatId != null && (this.tickCount % 10 == 0 || (Integer)this.entityData.get(DATA_PHASE) == 2)) {
            Entity threat = serverLevel.getEntity(this.threatId);
            if (threat != null && threat.isAlive()) {
               this.lastThreatPosition = threat.position();
            }
         }

         switch (this.entityData.get(DATA_PHASE)) {
            case 0:
               this.continueEscapeNavigation();
               if (this.getPhaseAge() >= 3L || this.isInLiquid()) {
                  this.setPhase(1);
               }
               break;
            case 2:
               this.tickBurrowing(serverLevel);
               break;
            default:
               this.tickFleeing();
         }
      }
   }

   private void tickFleeing() {
      this.fleeTicks = Math.min(40, this.fleeTicks + 1);
      if (this.canBuryAt(this.standingPosition())) {
         this.burialTarget = this.standingPosition();
         this.escapeTarget = null;
         this.setPhase(2);
         this.stopForDigging();
         this.playSound(SoundEvents.ROOTED_DIRT_BREAK, 0.45F, 0.9F);
      } else {
         this.continueEscapeNavigation();
      }
   }

   private void tickBurrowing(ServerLevel serverLevel) {
      if (this.burialTarget != null && this.burialTarget.equals(this.standingPosition()) && this.canBuryAt(this.burialTarget)) {
         this.stopForDigging();
         this.spawnDigParticles(serverLevel, false);
         if (this.getPhaseAge() >= 18L) {
            BlockState plant = ((MillenniumGinsengBlock)BlockRegistry.MILLENNIUM_GINSENG.get()).defaultBlockState();
            if (serverLevel.setBlock(this.burialTarget, plant, 3) && serverLevel.getBlockState(this.burialTarget).is(plant.getBlock())) {
               this.gameEvent(GameEvent.BLOCK_PLACE);
               this.discard();
            } else {
               this.resumeFleeing();
            }
         }
      } else {
         this.resumeFleeing();
         this.continueEscapeNavigation();
      }
   }

   private void resumeFleeing() {
      this.burialTarget = null;
      this.escapeTarget = null;
      this.getNavigation().stop();
      this.fleeTicks = 0;
      this.searchCooldown = 0;
      this.setPhase(1);
   }

   private void stopForDigging() {
      this.getNavigation().stop();
      this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0.0);
      this.setXxa(0.0F);
      this.setZza(0.0F);
      Vec3 velocity = this.getDeltaMovement();
      this.setDeltaMovement(0.0, velocity.y, 0.0);
   }

   private void continueEscapeNavigation() {
      if (this.getNavigation().isDone() && this.escapeTarget != null) {
         this.searchCooldown = 0;
      }

      if (this.searchCooldown == 0) {
         this.chooseEscapePath();
      }

      this.followPathImmediately();
      if (!this.onGround() && this.getNavigation().isDone() && this.lastThreatPosition != null) {
         Vec3 away = this.position().subtract(this.lastThreatPosition).multiply(1.0, 0.0, 1.0).normalize();
         this.getMoveControl().setWantedPosition(this.getX() + away.x * 4.0, this.getY(), this.getZ() + away.z * 4.0, 1.4);
      }
   }

   private void followPathImmediately() {
      Path path = this.getNavigation().getPath();
      if (path != null && !path.isDone()) {
         Vec3 next;
         for (next = path.getNextEntityPos(this);
            path.getNextNodeIndex() + 1 < path.getNodeCount() && horizontalDistanceSqr(this.position(), next) < 0.25 && Math.abs(this.getY() - next.y) < 1.0;
            next = path.getNextEntityPos(this)
         ) {
            path.advance();
         }

         this.getMoveControl().setWantedPosition(next.x, next.y, next.z, 1.4);
      }
   }

   private void chooseEscapePath() {
      if (this.onGround() || this.isInLiquid() || this.isPassenger()) {
         BlockPos previousTarget = this.escapeTarget;
         boolean keepPreviousPath = previousTarget != null && !this.getNavigation().isDone() && this.isTravelPosition(previousTarget);
         int newPathLimit = keepPreviousPath ? 2 : 3;
         MillenniumGinsengEntity.VisibilityBudget sightBudget = new MillenniumGinsengEntity.VisibilityBudget(192);
         List<MillenniumGinsengEntity.EscapeCandidate> candidates = new ArrayList<>(17);
         if (this.escapeTarget != null
            && horizontalDistanceSqr(this.position(), Vec3.atBottomCenterOf(this.escapeTarget)) >= 9.0
            && this.isTravelPosition(this.escapeTarget)
            && this.isEscapeDirectionSafe(this.escapeTarget)) {
            candidates.add(this.scoreCandidate(this.escapeTarget, sightBudget));
         }

         Vec3 away = this.lastThreatPosition == null ? Vec3.ZERO : this.position().subtract(this.lastThreatPosition);
         double direction = away.horizontalDistanceSqr() < 0.01 ? this.random.nextDouble() * Math.PI * 2.0 : Math.atan2(away.z, away.x);
         boolean nearbyFallback = this.canUseNearbyBurial();
         BlockPos origin = this.standingPosition();

         for (int i = 0; i < 16; i++) {
            double angle = direction + ((i + this.random.nextDouble()) / 16.0 - 0.5) * Math.PI * 2.0;
            int distance = nearbyFallback && i % 2 == 0 ? 2 + this.random.nextInt(4) : 6 + this.random.nextInt(9);
            int x = Mth.floor(this.getX() + Math.cos(angle) * distance);
            int z = Mth.floor(this.getZ() + Math.sin(angle) * distance);
            if (x != origin.getX() || z != origin.getZ()) {
               for (int dy = 3; dy >= -4; dy--) {
                  BlockPos candidate = new BlockPos(x, origin.getY() + dy, z);
                  if (this.isTravelPosition(candidate) && this.isEscapeDirectionSafe(candidate)) {
                     candidates.add(this.scoreCandidate(candidate, sightBudget));
                     break;
                  }
               }
            }
         }

         candidates.sort(Comparator.comparingDouble(MillenniumGinsengEntity.EscapeCandidate::score).reversed());
         int pathAttempts = 0;

         for (MillenniumGinsengEntity.EscapeCandidate candidate : candidates) {
            if (candidate.hidden()) {
               if (pathAttempts >= 2) {
                  break;
               }

               pathAttempts++;
               if (this.tryEscapePath(candidate)) {
                  return;
               }
            }
         }

         for (MillenniumGinsengEntity.EscapeCandidate candidate : candidates) {
            if (!candidate.hidden()) {
               if (pathAttempts >= newPathLimit) {
                  break;
               }

               pathAttempts++;
               if (this.tryEscapePath(candidate)) {
                  return;
               }
            }
         }

         if (!keepPreviousPath || !this.tryEscapePath(new MillenniumGinsengEntity.EscapeCandidate(previousTarget, 0.0, false))) {
            if (!keepPreviousPath && pathAttempts < 3) {
               int hiddenCandidates = 0;

               for (MillenniumGinsengEntity.EscapeCandidate candidate : candidates) {
                  if (candidate.hidden() && hiddenCandidates++ >= 2) {
                     pathAttempts++;
                     if (this.tryEscapePath(candidate)) {
                        return;
                     }

                     if (pathAttempts >= 3) {
                        break;
                     }
                  }
               }
            }

            this.escapeTarget = null;
            this.getNavigation().stop();
            this.failedSearches = Math.min(6, this.failedSearches + 1);
            this.searchCooldown = 4 + this.failedSearches * 2;
         }
      }
   }

   private boolean tryEscapePath(MillenniumGinsengEntity.EscapeCandidate candidate) {
      Path path = this.getNavigation().createPath(candidate.pos(), 0);
      if (path != null && path.getTarget().equals(candidate.pos()) && path.canReach() && this.isPathLoaded(path) && this.getNavigation().moveTo(path, 1.4)) {
         this.escapeTarget = candidate.pos();
         this.failedSearches = 0;
         this.searchCooldown = 10;
         this.followPathImmediately();
         return true;
      } else {
         return false;
      }
   }

   private MillenniumGinsengEntity.EscapeCandidate scoreCandidate(BlockPos pos, MillenniumGinsengEntity.VisibilityBudget sightBudget) {
      Vec3 center = Vec3.atBottomCenterOf(pos);
      double score = this.lastThreatPosition == null
         ? Math.sqrt(horizontalDistanceSqr(this.position(), center))
         : Math.sqrt(horizontalDistanceSqr(this.lastThreatPosition, center)) - Math.sqrt(horizontalDistanceSqr(this.lastThreatPosition, this.position()));
      boolean soil = this.isPlantPosition(pos);
      boolean hidden = soil && this.isOutsidePlayerAlarm(pos) && this.isHiddenFromPlayers(center, sightBudget);
      score = score * 4.0 - this.position().distanceTo(center) * 0.15;
      if (soil) {
         score += 8.0;
      }

      if (hidden) {
         score += 256.0;
      }

      if (pos.equals(this.escapeTarget)) {
         score += 12.0;
      }

      return new MillenniumGinsengEntity.EscapeCandidate(pos, score, hidden);
   }

   private boolean isEscapeDirectionSafe(BlockPos pos) {
      if (this.lastThreatPosition == null) {
         return true;
      }

      double distance = horizontalDistanceSqr(Vec3.atBottomCenterOf(pos), this.lastThreatPosition);
      return distance >= 16.0;
   }

   private boolean isPathLoaded(Path path) {
      for (int i = 0; i < path.getNodeCount(); i++) {
         BlockPos pos = path.getNodePos(i);
         if (!this.isLoaded(pos) || !this.level().getWorldBorder().isWithinBounds(pos)) {
            return false;
         }
      }

      return true;
   }

   private boolean isLoaded(BlockPos pos) {
      return this.level().getChunkSource().getChunkNow(pos.getX() >> 4, pos.getZ() >> 4) != null;
   }

   private boolean isOpenDryPosition(BlockPos pos) {
      return pos.getY() > this.level().getMinBuildHeight()
         && pos.getY() < this.level().getMaxBuildHeight()
         && this.isLoaded(pos)
         && this.level().getWorldBorder().isWithinBounds(pos)
         && this.level().getBlockState(pos).isAir()
         && this.level().getFluidState(pos).isEmpty()
         && this.level().getFluidState(pos.below()).isEmpty();
   }

   private boolean isTravelPosition(BlockPos pos) {
      if (!this.isOpenDryPosition(pos)) {
         return false;
      }

      BlockPos below = pos.below();
      BlockState ground = this.level().getBlockState(below);
      return (ground.isFaceSturdy(this.level(), below, Direction.UP) || ground.getBlock() instanceof FarmBlock)
         && this.level().noCollision(this, this.getBoundingBox().move(Vec3.atBottomCenterOf(pos).subtract(this.position())));
   }

   private boolean isPlantPosition(BlockPos pos) {
      if (!this.isOpenDryPosition(pos)) {
         return false;
      }

      BlockState ground = this.level().getBlockState(pos.below());
      return (ground.is(BlockTags.DIRT) || ground.getBlock() instanceof FarmBlock)
         && ((MillenniumGinsengBlock)BlockRegistry.MILLENNIUM_GINSENG.get()).defaultBlockState().canSurvive(this.level(), pos);
   }

   private boolean canBuryAt(BlockPos pos) {
      if (this.fleeTicks >= 40
         && this.onGround()
         && !this.isInLiquid()
         && (
            this.canUseNearbyBurial()
               || this.escapeOrigin != null
                  && horizontalDistanceSqr(this.position(), this.escapeOrigin) >= 49.0
                  && (this.lastThreatPosition == null || horizontalDistanceSqr(this.position(), this.lastThreatPosition) >= 64.0)
         )
         && this.isPlantPosition(pos)
         && this.isOutsidePlayerAlarm(pos)) {
         MillenniumGinsengEntity.VisibilityBudget budget = new MillenniumGinsengEntity.VisibilityBudget(160);
         Vec3 plantedCenter = Vec3.atBottomCenterOf(pos);
         return this.isHiddenFromPlayers(this.position(), budget)
            && (horizontalDistanceSqr(this.position(), plantedCenter) < 0.015625 || this.isHiddenFromPlayers(plantedCenter, budget));
      } else {
         return false;
      }
   }

   private boolean canUseNearbyBurial() {
      return this.totalFleeTicks >= 200;
   }

   private boolean isOutsidePlayerAlarm(BlockPos pos) {
      Vec3 center = Vec3.atBottomCenterOf(pos);

      for (Player player : this.level().players()) {
         if (MillenniumGinsengBlock.isThreat(player) && player.position().distanceToSqr(center) < 64.0) {
            return false;
         }
      }

      return true;
   }

   private boolean isHiddenFromPlayers(Vec3 feet, MillenniumGinsengEntity.VisibilityBudget budget) {
      double observationRange = this.getType().clientTrackingRange() * 16.0;

      for (Player player : this.level()
         .players()
         .stream()
         .filter(playerx -> playerx.isAlive() && !playerx.isSpectator() && playerx.position().distanceToSqr(feet) <= observationRange * observationRange)
         .sorted(Comparator.comparingDouble(playerx -> playerx.position().distanceToSqr(feet)))
         .toList()) {
         Vec3 eye = player.getEyePosition();
         Vec3 side = new Vec3(eye.z - feet.z, 0.0, feet.x - eye.x).normalize().scale(this.getBbWidth() * 0.5 + 0.1);
         Vec3 crown = feet.add(0.0, this.getBbHeight() * 0.85, 0.0);
         Vec3[] silhouette = new Vec3[]{
            feet.add(0.0, this.getBbHeight(), 0.0),
            feet.add(0.0, this.getBbHeight() * 0.5, 0.0),
            feet.add(0.0, 0.1, 0.0),
            crown.add(side),
            crown.subtract(side)
         };

         for (Vec3 point : silhouette) {
            if (!budget.takeRay() || !this.isSightBlocked(eye, point, player)) {
               return false;
            }
         }
      }

      return true;
   }

   private boolean isSightBlocked(Vec3 from, Vec3 to, Player observer) {
      ClipContext context = new ClipContext(from, to, Block.VISUAL, Fluid.NONE, observer);
      return (Boolean)BlockGetter.traverseBlocks(from, to, context, (ray, pos) -> {
         if (!this.isLoaded(pos)) {
            return Boolean.FALSE;
         }

         BlockState state = this.visibilityBlocks.getBlockState(pos);
         return ray.getBlockShape(state, this.visibilityBlocks, pos).clip(from, to, pos) != null ? Boolean.TRUE : null;
      }, ray -> Boolean.FALSE);
   }

   private BlockPos standingPosition() {
      return BlockPos.containing(this.getX(), this.getY() + 0.1, this.getZ());
   }

   private void spawnDigParticles(ServerLevel level, boolean burst) {
      if (burst || this.tickCount % 4 == 0) {
         BlockPos groundPos = this.standingPosition().below();
         if (this.isLoaded(groundPos)) {
            BlockState ground = level.getBlockState(groundPos);
            if (!ground.isAir()) {
               level.sendParticles(
                  new BlockParticleOption(ParticleTypes.BLOCK, ground), this.getX(), this.getY() + 0.04, this.getZ(), burst ? 12 : 4, 0.2, 0.03, 0.2, 0.025
               );
            }
         }
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      boolean hurt = super.hurt(source, amount);
      if (hurt && this.isAlive() && !this.level().isClientSide) {
         if (source.getEntity() != null) {
            this.rememberThreat(source.getEntity());
         }

         this.escapeOrigin = this.position();
         this.fleeTicks = 0;
         this.failedSearches = 0;
         this.resumeFleeing();
         this.triggerAnim("reaction", "hurt");
      }

      return hurt;
   }

   private void rememberThreat(Entity threat) {
      this.threatId = threat.getUUID();
      this.lastThreatPosition = threat.position();
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "movement", 0, state -> {
         if (this.isEmerging()) {
            return state.setAndContinue(POP_UP_ANIMATION);
         } else if (this.isBurrowing()) {
            return state.setAndContinue(BURROW_ANIMATION);
         } else {
            return state.isMoving() ? state.setAndContinue(this.isFleeing() ? RUN_ANIMATION : WALK_ANIMATION) : state.setAndContinue(IDLE_ANIMATION);
         }
      }));
      controllers.add(new AnimationController(this, "reaction", 0, state -> PlayState.STOP).triggerableAnim("hurt", HURT_ANIMATION));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("GinsengPhase", (Integer)this.entityData.get(DATA_PHASE));
      tag.putInt("PhaseAge", (int)Math.min(2147483647L, this.getPhaseAge()));
      tag.putInt("FleeTicks", this.fleeTicks);
      tag.putInt("TotalFleeTicks", this.totalFleeTicks);
      tag.putInt("SearchCooldown", this.searchCooldown);
      tag.putInt("FailedSearches", this.failedSearches);
      if (this.threatId != null) {
         tag.putUUID("Threat", this.threatId);
      }

      writePosition(tag, "LastThreatPosition", this.lastThreatPosition);
      writePosition(tag, "EscapeOrigin", this.escapeOrigin);
      if (this.escapeTarget != null) {
         tag.putLong("EscapeTarget", this.escapeTarget.asLong());
      }

      if (this.burialTarget != null) {
         tag.putLong("BurialTarget", this.burialTarget.asLong());
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setPersistenceRequired();
      int phase = Mth.clamp(tag.getInt("GinsengPhase"), 0, 2);
      this.setPhase(phase);
      int maxAge = phase == 0 ? 3 : (phase == 2 ? 18 : 40);
      this.entityData.set(DATA_PHASE_STARTED, this.level().getGameTime() - Mth.clamp(tag.getInt("PhaseAge"), 0, maxAge));
      this.fleeTicks = Mth.clamp(tag.getInt("FleeTicks"), 0, 40);
      this.totalFleeTicks = Mth.clamp(tag.getInt("TotalFleeTicks"), 0, 200);
      this.searchCooldown = Mth.clamp(tag.getInt("SearchCooldown"), 0, 10);
      this.failedSearches = Mth.clamp(tag.getInt("FailedSearches"), 0, 6);
      this.threatId = tag.hasUUID("Threat") ? tag.getUUID("Threat") : null;
      this.lastThreatPosition = readPosition(tag, "LastThreatPosition");
      this.escapeOrigin = readPosition(tag, "EscapeOrigin");
      this.escapeTarget = tag.contains("EscapeTarget", 4) ? BlockPos.of(tag.getLong("EscapeTarget")) : null;
      this.burialTarget = tag.contains("BurialTarget", 4) ? BlockPos.of(tag.getLong("BurialTarget")) : null;
   }

   private static void writePosition(CompoundTag tag, String key, @Nullable Vec3 position) {
      if (position != null) {
         CompoundTag coordinates = new CompoundTag();
         coordinates.putDouble("X", position.x);
         coordinates.putDouble("Y", position.y);
         coordinates.putDouble("Z", position.z);
         tag.put(key, coordinates);
      }
   }

   @Nullable
   private static Vec3 readPosition(CompoundTag tag, String key) {
      if (!tag.contains(key, 10)) {
         return null;
      }

      CompoundTag coordinates = tag.getCompound(key);
      return new Vec3(coordinates.getDouble("X"), coordinates.getDouble("Y"), coordinates.getDouble("Z"));
   }

   private static double horizontalDistanceSqr(Vec3 first, Vec3 second) {
      double dx = first.x - second.x;
      double dz = first.z - second.z;
      return dx * dx + dz * dz;
   }

   private record EscapeCandidate(BlockPos pos, double score, boolean hidden) {
   }

   private record LoadedBlockView(Level level) implements BlockGetter {
      @Nullable
      public BlockEntity getBlockEntity(BlockPos pos) {
         LevelChunk chunk = this.level.getChunkSource().getChunkNow(pos.getX() >> 4, pos.getZ() >> 4);
         return chunk == null ? null : chunk.getBlockEntity(pos, EntityCreationType.CHECK);
      }

      public BlockState getBlockState(BlockPos pos) {
         LevelChunk chunk = this.level.getChunkSource().getChunkNow(pos.getX() >> 4, pos.getZ() >> 4);
         return chunk == null ? Blocks.AIR.defaultBlockState() : chunk.getBlockState(pos);
      }

      public FluidState getFluidState(BlockPos pos) {
         return this.getBlockState(pos).getFluidState();
      }

      public int getHeight() {
         return this.level.getHeight();
      }

      public int getMinBuildHeight() {
         return this.level.getMinBuildHeight();
      }
   }

   private static final class VisibilityBudget {
      private int remaining;

      private VisibilityBudget(int rays) {
         this.remaining = rays;
      }

      private boolean takeRay() {
         return this.remaining-- > 0;
      }
   }
}
