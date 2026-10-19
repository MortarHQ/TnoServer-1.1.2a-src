package net.unusual.block_factorys_bosses.entity.boss.dragon.guardians;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.procedures.FlamingSkeletonGuardSwordOnEntityTickUpdateProcedure;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FlamingSkeletonGuardSwordEntity extends Monster implements GeoEntity {
   public static final EntityDataAccessor<Integer> DATA_attack_cooldown = SynchedEntityData.defineId(
      FlamingSkeletonGuardSwordEntity.class, EntityDataSerializers.INT
   );
   public static final EntityDataAccessor<Integer> DATA_attack_animtime = SynchedEntityData.defineId(
      FlamingSkeletonGuardSwordEntity.class, EntityDataSerializers.INT
   );
   public static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenLoop("animation.attack");
   public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.idle");
   public static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.walk");
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

   public FlamingSkeletonGuardSwordEntity(EntityType<FlamingSkeletonGuardSwordEntity> type, Level world) {
      super(type, world);
      this.xpReward = 7;
      this.setNoAi(false);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_attack_cooldown, 30);
      builder.define(DATA_attack_animtime, 0);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector
         .addGoal(
            1,
            new MeleeAttackGoal(this, 0.9, false) {
               protected boolean canPerformAttack(LivingEntity entity) {
                  return this.isTimeToAttack()
                     && this.mob.distanceToSqr(entity) < this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth()
                     && this.mob.getSensing().hasLineOfSight(entity);
               }
            }
         );
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
      this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Player.class, false, false));
   }

   public void tick() {
      super.tick();
   }

   public SoundEvent getAmbientSound() {
      return SoundEvents.SKELETON_AMBIENT;
   }

   public SoundEvent getHurtSound(DamageSource ds) {
      return SoundEvents.SKELETON_HURT;
   }

   public SoundEvent getDeathSound() {
      return SoundEvents.SKELETON_DEATH;
   }

   public boolean hurt(DamageSource damagesource, float amount) {
      return damagesource.is(DamageTypes.IN_FIRE) ? false : super.hurt(damagesource, amount);
   }

   public boolean fireImmune() {
      return true;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Dataattack_cooldown", (Integer)this.entityData.get(DATA_attack_cooldown));
      compound.putInt("Dataattack_animtime", (Integer)this.entityData.get(DATA_attack_animtime));
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("Dataattack_cooldown")) {
         this.entityData.set(DATA_attack_cooldown, compound.getInt("Dataattack_cooldown"));
      }

      if (compound.contains("Dataattack_animtime")) {
         this.entityData.set(DATA_attack_animtime, compound.getInt("Dataattack_animtime"));
      }
   }

   public void baseTick() {
      super.baseTick();
      FlamingSkeletonGuardSwordOnEntityTickUpdateProcedure.execute(this.level(), this);
   }

   public static void init(RegisterSpawnPlacementsEvent event) {
      event.register(
         (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         (entityType, world, reason, pos, random) -> world.getDifficulty() != Difficulty.PEACEFUL,
         Operation.REPLACE
      );
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder builder = Mob.createMobAttributes();
      builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
      builder = builder.add(Attributes.MAX_HEALTH, 30.0);
      builder = builder.add(Attributes.ARMOR, 4.0);
      builder = builder.add(Attributes.ATTACK_DAMAGE, 9.0);
      builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
      builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
      return builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.1);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(
            this,
            "main_controller",
            5,
            state -> this.getEntityData().get(DATA_attack_animtime) > 0
               ? state.setAndContinue(ATTACK_ANIM)
               : (state.isMoving() ? state.setAndContinue(WALK_ANIM) : state.setAndContinue(IDLE_ANIM))
         )
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }
}
