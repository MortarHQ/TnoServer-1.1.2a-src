package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.projectile.FrozenTube;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Freeze extends Monster implements RangedAttackMob {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState throwAnimationState = new AnimationState();
   protected static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(Freeze.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(Freeze.class, EntityDataSerializers.INT);

   public boolean isAttacking() {
      return (Boolean)this.getEntityData().get(ATTACKING);
   }

   public void setAttacking(boolean attacking) {
      this.getEntityData().set(ATTACKING, attacking);
   }

   public int getAttackTicks() {
      return (Integer)this.getEntityData().get(ATTACK_TICKS);
   }

   public void setAttackTicks(int attackTicks) {
      this.getEntityData().set(ATTACK_TICKS, attackTicks);
   }

   public Freeze(EntityType<? extends Freeze> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 10;
      this.moveControl = new FlyingMoveControl(this, 20, true);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ATTACKING, false).define(ATTACK_TICKS, 0);
   }

   protected PathNavigation createNavigation(Level level) {
      FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
         public boolean isStableDestination(BlockPos blockPos) {
            return this.level.getBlockState(blockPos).isAir();
         }
      };
      navigation.setCanOpenDoors(true);
      navigation.setCanFloat(true);
      navigation.setCanPassDoors(true);
      return navigation;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Freeze.FreezeAttackGoal(this, 1.5, 120, 15.0F));
      this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 32.0F));
      this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.3));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   public void performRangedAttack(LivingEntity entity, float f) {
      this.setAttacking(true);
      this.setAttackTicks(0);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.freeze.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.freeze.armor())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.freeze.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.FLYING_SPEED, 0.6);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      if (accessor.equals(ATTACKING)) {
         this.throwAnimationState.stop();
         if (this.isAttacking()) {
            this.throwAnimationState.start(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(accessor);
   }

   public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
      return false;
   }

   public boolean isAlliedTo(Entity entity) {
      return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.STARLIGHT_GOLEM_ALLIES);
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.isAttacking()) {
            if (this.getAttackTicks() == 12 && this.getTarget() != null) {
               LivingEntity livingEntity = this.getTarget();
               Vec3 targetPos = livingEntity.position().add(0.0, livingEntity.getBbHeight() / 2.0F, 0.0);
               Vec3 launchPos = this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0);
               Vec3 delta = targetPos.subtract(launchPos).normalize();
               FrozenTube tube = new FrozenTube(this.level(), this);
               tube.shoot(delta.x, delta.y + delta.horizontalDistance() * 0.4, delta.z, 0.9F, 0.2F);
               tube.setPos(launchPos);
               tube.playSound(ESSoundEvents.FROZEN_TUBE_THROW.get());
               this.level().addFreshEntity(tube);
            }

            this.setAttackTicks(this.getAttackTicks() + 1);
            if (this.getAttackTicks() > 20) {
               this.setAttacking(false);
            }
         } else {
            this.setAttackTicks(0);
         }
      } else {
         if (!this.onGround()) {
            Vec3 pos = this.position().add(0.0, this.getBbHeight() / 20.0F * 12.0F, 0.0);

            for (int i = 0; i < 5; i++) {
               this.level()
                  .addParticle(
                     ParticleTypes.WHITE_SMOKE,
                     pos.x + this.getBbWidth() / 2.0F * (this.getRandom().nextFloat() - 0.5F),
                     pos.y,
                     pos.z + this.getBbWidth() / 2.0F * (this.getRandom().nextFloat() - 0.5F),
                     0.0,
                     -0.15,
                     0.0
                  );
            }
         }

         this.idleAnimationState.startIfStopped(this.tickCount);
      }
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return ESSoundEvents.FREEZE_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.FREEZE_DEATH.get();
   }

   public static boolean checkFreezeSpawnRules(
      EntityType<? extends Freeze> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.freeze.canSpawn();
   }

   private static class FreezeAttackGoal extends RangedAttackGoal {
      private final Freeze freeze;

      public FreezeAttackGoal(Freeze freeze, double speed, int interval, float radius) {
         super(freeze, speed, interval, radius);
         this.freeze = freeze;
      }

      public void start() {
         super.start();
         this.freeze.setAggressive(true);
      }

      public void stop() {
         super.stop();
         this.freeze.setAggressive(false);
      }
   }
}
