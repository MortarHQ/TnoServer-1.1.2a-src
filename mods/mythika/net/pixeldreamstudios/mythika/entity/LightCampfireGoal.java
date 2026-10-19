package net.pixeldreamstudios.mythika.entity;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mythika.Mythika;
import org.jetbrains.annotations.Nullable;

public class LightCampfireGoal extends Goal {
   private static final double SPEED = 0.9;
   private static final double COMPANY_RADIUS = 8.0;
   private static final double BUILDER_RADIUS = 32.0;
   private static final double CLEARING_RADIUS = 4.5;
   private static final int CLEARING_SLACK = 4;
   private static final float DEFAULT_FRICTION = 0.6F;
   private static final float FULL_SPEED = 1.0F;
   private static final int SITE_RADIUS = 6;
   private static final int SITE_ATTEMPTS = 16;
   private static final int MAX_CLIMB = 4;
   private static final int SCOUT_STEP = 12;
   private static final int SCOUT_LEGS = 4;
   private static final double BEARING_JITTER = Math.PI / 2;
   private static final int LEG_TIMEOUT = 300;
   private static final double LEG_ARRIVED = 3.0;
   private static final int SCAN_INTERVAL = 20;
   private static final int RETRY_TICKS = 200;
   private static final int COOLDOWN_TICKS = 2400;
   private static final double PLACE_DISTANCE = 2.0;
   private final SatyrEntity satyr;
   @Nullable
   private BlockPos site;
   @Nullable
   private BlockPos leg;
   private double bearing;
   private int legsLeft;
   private int legTicks;
   private int scanTicks;
   private int nextAttempt;

   public LightCampfireGoal(SatyrEntity satyr) {
      this.satyr = satyr;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      if (!Mythika.config.satyrPlaysMusic || !Mythika.config.satyrLightsCampfires) {
         return false;
      }

      if (this.satyr.getTarget() == null && this.satyr.tickCount >= this.nextAttempt) {
         if (!this.satyr.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return false;
         }

         this.nextAttempt = this.satyr.tickCount + 200;
         return !this.company().isEmpty() && !this.builderAbroad() ? this.satyr.nearestCampfire() == null && this.satyr.campfireToReturnTo() == null : false;
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      return this.satyr.getTarget() == null
         && this.satyr.nearestCampfire() == null
         && this.satyr.campfireToReturnTo() == null
         && (this.site != null || this.legsLeft > 0);
   }

   public void start() {
      this.satyr.setBuildingFire(true);
      this.legsLeft = 4;
      this.bearing = this.satyr.getRandom().nextDouble() * Math.PI * 2.0;
      this.scanTicks = 0;
      this.site = this.findSite();
      if (this.site == null) {
         this.beginLeg();
      }
   }

   public void stop() {
      this.satyr.setBuildingFire(false);
      this.satyr.getNavigation().stop();
      this.site = null;
      this.leg = null;
      this.nextAttempt = Math.max(this.nextAttempt, this.satyr.tickCount + 200);
   }

   public void tick() {
      if (this.site != null) {
         this.approachSite();
      } else {
         if (--this.scanTicks <= 0) {
            this.scanTicks = 20;
            this.site = this.findSite();
            if (this.site != null) {
               this.satyr.getNavigation().stop();
               return;
            }
         }

         this.satyr.getLookControl().setLookAt(this.leg.getX() + 0.5, this.satyr.getEyeY(), this.leg.getZ() + 0.5);
         if (--this.legTicks <= 0 || this.satyr.distanceToSqr(Vec3.atCenterOf(this.leg)) < 9.0 || this.satyr.getNavigation().isDone()) {
            this.beginLeg();
         }
      }
   }

   private void approachSite() {
      this.satyr.getLookControl().setLookAt(this.site.getX() + 0.5, this.site.getY(), this.site.getZ() + 0.5);
      if (this.satyr.distanceToSqr(Vec3.atCenterOf(this.site)) > 4.0) {
         if (--this.legTicks <= 0) {
            this.site = null;
            this.beginLeg();
         } else if (this.satyr.getNavigation().isDone()) {
            this.satyr.getNavigation().moveTo(this.site.getX() + 0.5, this.site.getY(), this.site.getZ() + 0.5, 0.9);
         }
      } else {
         this.light();
         this.site = null;
         this.legsLeft = 0;
      }
   }

   private void beginLeg() {
      if (--this.legsLeft <= 0) {
         this.leg = null;
      } else {
         Level level = this.satyr.level();
         RandomSource random = this.satyr.getRandom();
         this.bearing = this.bearing + (random.nextDouble() - 0.5) * (Math.PI / 2);
         BlockPos aim = this.satyr.blockPosition().offset(Mth.floor(Math.cos(this.bearing) * 12.0), 0, Mth.floor(Math.sin(this.bearing) * 12.0));
         if (!level.hasChunkAt(aim)) {
            this.bearing += Math.PI;
            aim = this.satyr.blockPosition();
         }

         this.leg = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, aim);
         this.legTicks = 300;
         this.satyr.getNavigation().moveTo(this.leg.getX() + 0.5, this.leg.getY(), this.leg.getZ() + 0.5, 0.9);
      }
   }

   private List<SatyrEntity> company() {
      return this.satyr
         .level()
         .getEntitiesOfClass(SatyrEntity.class, this.satyr.getBoundingBox().inflate(8.0), other -> other != this.satyr && other.isAlive());
   }

   private boolean builderAbroad() {
      return !this.satyr
         .level()
         .getEntitiesOfClass(
            SatyrEntity.class, this.satyr.getBoundingBox().inflate(32.0), other -> other != this.satyr && other.isAlive() && other.isBuildingFire()
         )
         .isEmpty();
   }

   private void light() {
      Level level = this.satyr.level();
      this.nextAttempt = this.satyr.tickCount + 2400;
      this.satyr.forgetCampfire();
      if (this.satyr.nearestCampfire() == null && isHearth(level, this.site)) {
         RandomSource random = this.satyr.getRandom();
         level.setBlockAndUpdate(
            this.site,
            (BlockState)((BlockState)((BlockState)((BlockState)Blocks.CAMPFIRE
                        .defaultBlockState()
                        .setValue(CampfireBlock.FACING, Direction.from2DDataValue(random.nextInt(4))))
                     .setValue(CampfireBlock.LIT, true))
                  .setValue(CampfireBlock.SIGNAL_FIRE, false))
               .setValue(CampfireBlock.WATERLOGGED, false)
         );
         level.playSound(null, this.site, SoundEvents.FLINTANDSTEEL_USE, this.satyr.getSoundSource(), 1.0F, 0.8F + random.nextFloat() * 0.4F);
         level.gameEvent(this.satyr, GameEvent.BLOCK_PLACE, this.site);
         this.satyr.forgetCampfire();

         for (SatyrEntity other : level.getEntitiesOfClass(SatyrEntity.class, this.satyr.getBoundingBox().inflate(32.0), LivingEntity::isAlive)) {
            other.forgetCampfire();
            other.rememberCampfire(this.site);
         }
      }
   }

   @Nullable
   private BlockPos findSite() {
      Level level = this.satyr.level();
      BlockPos origin = this.satyr.blockPosition();
      RandomSource random = this.satyr.getRandom();
      BlockPos best = null;
      int fewestFaults = 5;

      for (int attempt = 0; attempt < 16; attempt++) {
         BlockPos guess = origin.offset(random.nextInt(13) - 6, 0, random.nextInt(13) - 6);
         if (level.hasChunkAt(guess)) {
            BlockPos candidate = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, guess);
            if (Math.abs(candidate.getY() - origin.getY()) <= 4 && isHearth(level, candidate)) {
               int faults = clearingFaults(level, candidate, fewestFaults);
               if (faults < fewestFaults) {
                  fewestFaults = faults;
                  best = candidate;
               }
            }
         }
      }

      return best;
   }

   private static boolean isFloor(Level level, BlockPos ground) {
      BlockState state = level.getBlockState(ground);
      return state.isFaceSturdy(level, ground, Direction.UP)
         && !NodeEvaluator.isBurningBlock(state)
         && state.getBlock().getFriction() <= 0.6F
         && state.getBlock().getSpeedFactor() >= 1.0F
         && standable(level.getBlockState(ground.above()))
         && standable(level.getBlockState(ground.above(2)));
   }

   private static boolean standable(BlockState space) {
      return !space.blocksMotion() && space.getFluidState().isEmpty();
   }

   private static boolean clearable(BlockState space) {
      return space.getFluidState().isEmpty() && (space.canBeReplaced() || !space.blocksMotion());
   }

   private static boolean isHearth(Level level, BlockPos fire) {
      if (!clearable(level.getBlockState(fire))) {
         return false;
      }

      for (int x = -1; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            if (!isFloor(level, fire.offset(x, -1, z))) {
               return false;
            }
         }
      }

      return true;
   }

   private static int clearingFaults(Level level, BlockPos fire, int giveUpAt) {
      int radius = Mth.ceil(4.5);
      int faults = 0;

      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            if (!(x * x + z * z > 20.25)) {
               BlockPos column = fire.offset(x, 0, z);
               if (!level.hasChunkAt(column) || blocked(level, column)) {
                  return giveUpAt;
               }

               if (level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, column).getY() != fire.getY() || !isFloor(level, column.below())) {
                  if (++faults >= giveUpAt) {
                     return giveUpAt;
                  }
               }
            }
         }
      }

      return faults;
   }

   private static boolean blocked(Level level, BlockPos standing) {
      return level.getBlockState(standing).blocksMotion() || level.getBlockState(standing.above()).blocksMotion();
   }
}
