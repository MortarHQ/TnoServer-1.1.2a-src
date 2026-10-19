package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MeleeAttackPhase;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiBehaviorUser;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Tangled extends Monster implements MultiBehaviorUser {
   private static final String TAG_VARIANT = "variant";
   private static final int MELEE_ID = 1;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState meleeAnimationState = new AnimationState();
   protected static final EntityDataAccessor<Integer> BEHAVIOR_STATE = SynchedEntityData.defineId(Tangled.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> BEHAVIOR_TICKS = SynchedEntityData.defineId(Tangled.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Tangled.class, EntityDataSerializers.INT);
   private final BehaviorManager<Tangled> behaviorManager = new BehaviorManager(this, List.of(new MeleeAttackPhase(1, 1, 20, 10).with(2, 15)));

   @Override
   public int getBehaviorState() {
      return (Integer)this.getEntityData().get(BEHAVIOR_STATE);
   }

   @Override
   public void setBehaviorState(int attackState) {
      this.getEntityData().set(BEHAVIOR_STATE, attackState);
   }

   @Override
   public int getBehaviorTicks() {
      return (Integer)this.getEntityData().get(BEHAVIOR_TICKS);
   }

   @Override
   public void setBehaviorTicks(int behaviourTicks) {
      this.getEntityData().set(BEHAVIOR_TICKS, behaviourTicks);
   }

   public int getVariant() {
      return (Integer)this.getEntityData().get(VARIANT) % 3;
   }

   public void setVariant(int variant) {
      this.getEntityData().set(VARIANT, variant % 3);
   }

   public Tangled(EntityType<? extends Tangled> entityType, Level level) {
      super(entityType, level);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(BEHAVIOR_STATE, 0).define(BEHAVIOR_TICKS, 0).define(VARIANT, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false) {
         protected void checkAndPerformAttack(LivingEntity livingEntity) {
         }
      });
      this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.tangled.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.tangled.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.tangled.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.tangled.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.2);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      this.setVariant(this.random.nextInt(3));
      return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.getTarget() != null && !this.getTarget().isAlive()) {
         this.setTarget(null);
      }

      if (!this.isNoAi() && this.isAlive()) {
         this.behaviorManager.tick();
      }
   }

   public boolean doHurtTarget(Entity entity) {
      boolean flag = super.doHurtTarget(entity);
      if (flag && entity instanceof LivingEntity living && this.getRandom().nextInt(5) == 0 && !living.hasEffect(MobEffects.POISON)) {
         living.addEffect(new MobEffectInstance(MobEffects.POISON, 40));
      }

      return flag;
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         this.idleAnimationState.startIfStopped(this.tickCount);
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      if (accessor.equals(BEHAVIOR_STATE)) {
         if (this.getBehaviorState() == 1) {
            this.meleeAnimationState.start(this.tickCount);
         } else {
            this.meleeAnimationState.stop();
         }
      }

      super.onSyncedDataUpdated(accessor);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("variant", 3)) {
         this.setVariant(compoundTag.getInt("variant"));
      }
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putInt("variant", this.getVariant());
   }

   protected void tickDeath() {
      if (!this.level().isClientSide && this.deathTime == 0 && this.getRandom().nextBoolean() && ESConfig.INSTANCE.mobsConfig.tangledSkull.canSpawn()) {
         TangledSkull skull = new TangledSkull(ESEntities.TANGLED_SKULL.get(), this.level());
         skull.setPos(this.getX(), this.getY(0.75), this.getZ());
         skull.setTarget(this.getTarget());
         skull.setLastHurtByMob(this.getTarget());
         this.level().addFreshEntity(skull);
      }

      this.deathTime++;
      if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
      super.dropCustomDeathLoot(serverLevel, damageSource, bl);
      if (damageSource.getEntity() instanceof Creeper creeper && creeper.canDropMobsSkull()) {
         ItemStack itemStack = ESItems.TANGLED_SKULL.get().getDefaultInstance();
         creeper.increaseDroppedSkulls();
         this.spawnAtLocation(itemStack);
      }
   }

   public boolean isAlliedTo(Entity entity) {
      return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLIES);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return SoundEvents.SKELETON_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.SKELETON_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.SKELETON_DEATH;
   }

   public static boolean checkTangledSpawnRules(
      EntityType<? extends Tangled> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.tangled.canSpawn();
   }
}
