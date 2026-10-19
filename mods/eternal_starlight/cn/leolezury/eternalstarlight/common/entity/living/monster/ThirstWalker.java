package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MeleeAttackPhase;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiBehaviorUser;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ThirstWalker extends Monster implements MultiBehaviorUser, NeutralMob {
   private static final String TAG_HUNGER_LEVEL = "hunger_level";
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
   private int remainingPersistentAngerTime;
   @Nullable
   private UUID persistentAngerTarget;
   private float hungerLevel = 1.0F;
   private Vec3 fleeTo;
   private int fleeTicks;
   private Entity fleeFrom;
   private static final int MELEE_ID = 1;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState meleeAnimationState = new AnimationState();
   protected static final EntityDataAccessor<Integer> BEHAVIOR_STATE = SynchedEntityData.defineId(ThirstWalker.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> BEHAVIOR_TICKS = SynchedEntityData.defineId(ThirstWalker.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Boolean> INTENTIONAL_ATTACK = SynchedEntityData.defineId(ThirstWalker.class, EntityDataSerializers.BOOLEAN);
   private final BehaviorManager<ThirstWalker> behaviorManager = new BehaviorManager(this, List.of(new MeleeAttackPhase(1, 1, 20, 10).with(2, 7)));

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

   public boolean isIntentionalAttack() {
      return (Boolean)this.getEntityData().get(INTENTIONAL_ATTACK);
   }

   public void setIntentionalAttack(boolean intentionalAttack) {
      this.getEntityData().set(INTENTIONAL_ATTACK, intentionalAttack);
   }

   public ThirstWalker(EntityType<? extends ThirstWalker> entityType, Level level) {
      super(entityType, level);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(BEHAVIOR_STATE, 0).define(BEHAVIOR_TICKS, 0).define(INTENTIONAL_ATTACK, false);
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
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(1, new ResetUniversalAngerTargetGoal(this, false));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.thirstWalker.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.thirstWalker.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.thirstWalker.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.thirstWalker.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.3);
   }

   public void setTarget(@Nullable LivingEntity livingEntity) {
      super.setTarget(livingEntity);
      if (livingEntity != null) {
         this.setIntentionalAttack(false);
      }
   }

   public boolean doHurtTarget(Entity entity) {
      boolean flag = super.doHurtTarget(entity);
      if (flag && entity instanceof LivingEntity living) {
         float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
         living.addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int)f), this);
         this.hungerLevel = Math.min(this.hungerLevel + (this.isIntentionalAttack() ? 0.6F : 0.1F), 1.0F);
      }

      if (this.isIntentionalAttack()) {
         this.stopBeingAngry();
         this.fleeFrom = entity;
         this.fleeTicks = 100;
         this.tryFlee();
         if (entity instanceof LivingEntity living && living.isBlocking() && this.getRandom().nextInt(6) == 0) {
            int i = this.getRandom().nextInt(3);

            for (int j = 0; j < i; j++) {
               ItemEntity itemEntity = this.spawnAtLocation((ItemLike)ESItems.TOOTH_OF_HUNGER.get(), 1);
               if (itemEntity != null) {
                  itemEntity.setDeltaMovement(
                     itemEntity.getDeltaMovement()
                        .add(
                           (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F,
                           this.getRandom().nextFloat() * 0.05F,
                           (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F
                        )
                  );
               }
            }
         }
      }

      return flag;
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.getTarget() != null && !this.getTarget().isAlive()) {
         this.setTarget(null);
      }

      if (!this.isNoAi() && this.isAlive()) {
         this.behaviorManager.tick();
      }

      if (this.fleeTicks > 0) {
         this.fleeTicks--;
         if (this.tickCount % 20 == 0) {
            this.tryFlee();
         }
      }
   }

   private void tryFlee() {
      if (this.fleeFrom != null) {
         Vec3 fleePos = LandRandomPos.getPosAway(this, 20, 8, this.fleeFrom.position());
         if (fleePos != null) {
            this.fleeTo = fleePos;
         }
      }

      if (this.fleeTo != null) {
         this.getNavigation().stop();
         this.getNavigation().moveTo(this.fleeTo.x, this.fleeTo.y, this.fleeTo.z, 1.5);
         this.getMoveControl().setWantedPosition(this.fleeTo.x, this.fleeTo.y, this.fleeTo.z, 1.5);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
         this.updatePersistentAnger(serverLevel, true);
         if (this.hungerLevel < 0.3 && this.getTarget() == null && (this.tickCount + this.getId() * 17) % 1200 == 0) {
            List<Player> players = this.level().getNearbyEntities(Player.class, TargetingConditions.DEFAULT, this, this.getBoundingBox().inflate(20.0));
            if (!players.isEmpty()) {
               Player target = players.get(this.getRandom().nextInt(players.size()));
               this.setTarget(target);
               this.setIntentionalAttack(true);
            }
         }

         this.hungerLevel = Math.max(this.hungerLevel - 0.001F, 0.0F);
      }

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

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      this.addPersistentAngerSaveData(compoundTag);
      compoundTag.putFloat("hunger_level", this.hungerLevel);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.readPersistentAngerSaveData(this.level(), compoundTag);
      if (compoundTag.contains("hunger_level", 99)) {
         this.hungerLevel = compoundTag.getFloat("hunger_level");
      }
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return ESSoundEvents.THIRST_WALKER_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return ESSoundEvents.THIRST_WALKER_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.THIRST_WALKER_DEATH.get();
   }

   public int getRemainingPersistentAngerTime() {
      return this.remainingPersistentAngerTime;
   }

   public void setRemainingPersistentAngerTime(int i) {
      this.remainingPersistentAngerTime = i;
   }

   @Nullable
   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   public void setPersistentAngerTarget(@Nullable UUID uuid) {
      this.persistentAngerTarget = uuid;
   }

   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.getRandom()));
   }

   public static boolean checkThirstWalkerSpawnRules(
      EntityType<? extends ThirstWalker> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.thirstWalker.canSpawn();
   }
}
