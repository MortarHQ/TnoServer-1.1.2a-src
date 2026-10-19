package net.unusual.block_factorys_bosses.entity.monster;

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
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
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
import net.unusual.block_factorys_bosses.init.BossesRiseTags;
import net.unusual.block_factorys_bosses.procedures.SoulSkeletonOnEntityTickUpdateProcedure;
import net.unusual.block_factorys_bosses.procedures.SoulSkeletonsEntityDiesProcedure;

public class SoulSkeletonEntity extends Monster {
   public final AnimationState attackAnimationState = new AnimationState();
   public final AnimationState idleAnimationState = new AnimationState();
   public static final EntityDataAccessor<Integer> DATA_attack_cooldown = SynchedEntityData.defineId(SoulSkeletonEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_attack_animtime = SynchedEntityData.defineId(SoulSkeletonEntity.class, EntityDataSerializers.INT);

   public SoulSkeletonEntity(EntityType<SoulSkeletonEntity> type, Level world) {
      super(type, world);
      this.xpReward = 5;
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
      if (this.level().isClientSide) {
         this.idleAnimationState.animateWhen(true, this.tickCount);
         this.attackAnimationState.animateWhen((Integer)this.getEntityData().get(DATA_attack_animtime) > 0, this.tickCount);
      }

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
      SoulSkeletonOnEntityTickUpdateProcedure.execute(this.level(), this);
   }

   public void die(DamageSource source) {
      super.die(source);
      SoulSkeletonsEntityDiesProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
   }

   public static void init(RegisterSpawnPlacementsEvent event) {
      event.register(
         (EntityType)BossesRiseEntities.SOUL_SKELETON.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         (entityType, world, reason, pos, random) -> world.getDifficulty() != Difficulty.PEACEFUL
            && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)
            && (reason == MobSpawnType.SPAWNER || world.getBlockState(pos.below()).is(BossesRiseTags.Blocks.UNDERWORLD_ARENA_VALID_SPAWN)),
         Operation.REPLACE
      );
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder builder = Mob.createMobAttributes();
      builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
      builder = builder.add(Attributes.MAX_HEALTH, 20.0);
      builder = builder.add(Attributes.ARMOR, 4.0);
      builder = builder.add(Attributes.ATTACK_DAMAGE, 6.0);
      builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
      builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
      return builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.1);
   }
}
