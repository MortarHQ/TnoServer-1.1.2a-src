package com.chronodawn.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.AABB;

public class MomentCreeperEntity extends Monster {
   private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(MomentCreeperEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_SWELL_TIME = SynchedEntityData.defineId(MomentCreeperEntity.class, EntityDataSerializers.INT);
   private static final int MAX_SWELL_TIME = 30;
   private static final int EXPLOSION_RADIUS = 3;
   private static final int FREEZE_DURATION = 10;
   private int oldSwellTime = 0;
   private boolean isFreezing = false;

   public MomentCreeperEntity(EntityType<? extends MomentCreeperEntity> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 5;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_SWELL_DIR, -1);
      builder.define(DATA_SWELL_TIME, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new MomentCreeperEntity.MomentCreeperSwellGoal(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void tick() {
      if (this.isAlive()) {
         this.oldSwellTime = this.getSwellTime();
         if (!this.level().isClientSide) {
            int swellDir = this.getSwellDir();
            int currentSwellTime = this.getSwellTime();
            if (swellDir > 0 && currentSwellTime == 0) {
               this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
            }

            currentSwellTime += swellDir;
            if (currentSwellTime >= 20 && !this.isFreezing) {
               this.isFreezing = true;
               this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
            }

            if (currentSwellTime >= 30) {
               int var4 = 30;
               this.setSwellTime(var4);
               this.explode();
               return;
            }

            if (currentSwellTime < 0) {
               currentSwellTime = 0;
               this.isFreezing = false;
            }

            this.setSwellTime(currentSwellTime);
         }
      }

      super.tick();
   }

   public int getSwellDir() {
      return (Integer)this.entityData.get(DATA_SWELL_DIR);
   }

   public void setSwellDir(int swellDir) {
      this.entityData.set(DATA_SWELL_DIR, swellDir);
   }

   public int getSwellTime() {
      return (Integer)this.entityData.get(DATA_SWELL_TIME);
   }

   public void setSwellTime(int swellTime) {
      this.entityData.set(DATA_SWELL_TIME, swellTime);
   }

   public float getSwelling(float partialTick) {
      return Mth.lerp(partialTick, this.oldSwellTime, this.getSwellTime()) / 29.0F;
   }

   private void explode() {
      if (!this.level().isClientSide) {
         float explosionPower = 1.5F;
         AABB damageBox = this.getBoundingBox().inflate(3.0);

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, damageBox)) {
            if (entity instanceof Player) {
               entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
               entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 0));
               entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
            }

            double distance = entity.distanceTo(this);
            if (distance < 3.0) {
               double damageMultiplier = 1.0 - distance / 3.0;
               float damage = (int)((damageMultiplier * damageMultiplier + damageMultiplier) * 7.0 * 3.0 + 1.0);
               entity.hurt(this.damageSources().explosion(this, this), damage);
            }
         }

         this.level().explode(this, this.getX(), this.getY(), this.getZ(), explosionPower, ExplosionInteraction.MOB);
         this.discard();
      }
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putShort("Fuse", (short)this.getSwellTime());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("Fuse")) {
         this.setSwellTime(tag.getShort("Fuse"));
      }
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 22.0)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.ATTACK_DAMAGE, 0.0)
         .add(Attributes.FOLLOW_RANGE, 35.0);
   }

   public static boolean checkMomentCreeperSpawnRules(
      EntityType<MomentCreeperEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return level.getDifficulty() == Difficulty.PEACEFUL ? false : Mob.checkMobSpawnRules(entityType, level, spawnType, pos, random);
   }

   static class MomentCreeperSwellGoal extends Goal {
      private final MomentCreeperEntity creeper;

      public MomentCreeperSwellGoal(MomentCreeperEntity creeper) {
         this.creeper = creeper;
      }

      public boolean canUse() {
         LivingEntity target = this.creeper.getTarget();
         return target != null && this.creeper.distanceToSqr(target) < 9.0;
      }

      public void start() {
         this.creeper.setSwellDir(1);
      }

      public void stop() {
         this.creeper.setSwellDir(-1);
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }
}
