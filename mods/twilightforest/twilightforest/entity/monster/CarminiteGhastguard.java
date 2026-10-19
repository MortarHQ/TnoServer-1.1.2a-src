package twilightforest.entity.monster;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Ghast.GhastLookGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.EnforcedHomePoint;
import twilightforest.entity.ai.goal.GhastguardAttackGoal;
import twilightforest.entity.ai.goal.GhastguardHomedFlightGoal;
import twilightforest.entity.ai.goal.GhastguardRandomFlyGoal;
import twilightforest.init.TFSounds;

public class CarminiteGhastguard extends Ghast implements EnforcedHomePoint {
   private static final EntityDataAccessor<Byte> ATTACK_STATUS = SynchedEntityData.defineId(CarminiteGhastguard.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Byte> ATTACK_TIMER = SynchedEntityData.defineId(CarminiteGhastguard.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Byte> ATTACK_PREVTIMER = SynchedEntityData.defineId(CarminiteGhastguard.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Optional<GlobalPos>> HOME_POINT = SynchedEntityData.defineId(
      CarminiteGhastguard.class, EntityDataSerializers.OPTIONAL_GLOBAL_POS
   );
   private GhastguardAttackGoal attackGoal;
   protected float wanderFactor = 16.0F;

   public CarminiteGhastguard(EntityType<? extends CarminiteGhastguard> type, Level level) {
      super(type, level);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ATTACK_STATUS, (byte)0);
      builder.define(ATTACK_TIMER, (byte)0);
      builder.define(ATTACK_PREVTIMER, (byte)0);
      builder.define(HOME_POINT, Optional.empty());
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(5, new GhastguardHomedFlightGoal(this));
      this.goalSelector.addGoal(5, new GhastguardRandomFlyGoal(this));
      this.goalSelector.addGoal(7, new GhastLookGoal(this));
      this.goalSelector.addGoal(7, this.attackGoal = new GhastguardAttackGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public float getWanderFactor() {
      return this.wanderFactor;
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder registerAttributes() {
      return Ghast.createAttributes().add(Attributes.MAX_HEALTH, 30.0).add(Attributes.FOLLOW_RANGE, 64.0);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.CARMINITE_GHASTGUARD_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.CARMINITE_GHASTGUARD_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.CARMINITE_GHASTGUARD_DEATH.get();
   }

   public SoundEvent getFireSound() {
      return (SoundEvent)TFSounds.CARMINITE_GHASTGUARD_SHOOT.get();
   }

   public SoundEvent getWarnSound() {
      return (SoundEvent)TFSounds.CARMINITE_GHASTGUARD_WARN.get();
   }

   protected float getSoundVolume() {
      return 0.5F;
   }

   public int getAmbientSoundInterval() {
      return 160;
   }

   public int getMaxSpawnClusterSize() {
      return 8;
   }

   public void aiStep() {
      if (this.getRandom().nextBoolean()) {
         this.level()
            .addParticle(
               DustParticleOptions.REDSTONE,
               this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(),
               this.getY() + this.getRandom().nextDouble() * this.getBbHeight() - 0.25,
               this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(),
               0.0,
               0.0,
               0.0
            );
      }

      super.aiStep();
   }

   protected void customServerAiStep() {
      int status = this.getTarget() != null && this.shouldAttack(this.getTarget()) ? 1 : 0;
      this.getEntityData().set(ATTACK_STATUS, (byte)status);
      this.getEntityData().set(ATTACK_TIMER, (byte)this.attackGoal.attackTimer);
      this.getEntityData().set(ATTACK_PREVTIMER, (byte)this.attackGoal.prevAttackTimer);
   }

   public int getAttackStatus() {
      return (Byte)this.getEntityData().get(ATTACK_STATUS);
   }

   public int getAttackTimer() {
      return (Byte)this.getEntityData().get(ATTACK_TIMER);
   }

   public int getPrevAttackTimer() {
      return (Byte)this.getEntityData().get(ATTACK_PREVTIMER);
   }

   public boolean shouldAttack(LivingEntity living) {
      return true;
   }

   public int getMaxHeadXRot() {
      return 500;
   }

   protected boolean canRide(Entity entity) {
      return false;
   }

   public static boolean ghastSpawnHandler(
      EntityType<? extends CarminiteGhastguard> entityType, LevelAccessor accessor, MobSpawnType type, BlockPos pos, RandomSource random
   ) {
      return accessor.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(entityType, accessor, type, pos, random);
   }

   public boolean checkSpawnObstruction(LevelReader world) {
      return world.isUnobstructed(this) && !world.containsAnyLiquid(this.getBoundingBox());
   }

   @Override
   public boolean isMobWithinHomeArea(Entity entity) {
      return !this.isRestrictionPointValid(entity.level().dimension())
         ? true
         : entity.blockPosition().getY() > this.level().getMinBuildHeight() + 64
            && entity.blockPosition().getY() < this.level().getMaxBuildHeight() - 64
            && this.getRestrictionPoint().pos().distSqr(entity.blockPosition()) < this.getHomeRadius() * this.getHomeRadius();
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      this.saveHomePointToNbt(compound);
      super.addAdditionalSaveData(compound);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.loadHomePointFromNbt(compound);
   }

   @Nullable
   @Override
   public GlobalPos getRestrictionPoint() {
      return (GlobalPos)((Optional)this.getEntityData().get(HOME_POINT)).orElse(null);
   }

   @Override
   public void setRestrictionPoint(@Nullable GlobalPos pos) {
      this.getEntityData().set(HOME_POINT, Optional.ofNullable(pos));
   }

   @Override
   public int getHomeRadius() {
      return 64;
   }
}
