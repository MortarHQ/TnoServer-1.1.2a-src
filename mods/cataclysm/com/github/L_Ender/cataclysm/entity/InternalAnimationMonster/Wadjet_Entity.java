package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Ancient_Desert_Stele_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Poison_Dart_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Sandstorm_Projectile;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import com.github.L_Ender.lionfishapi.client.model.tools.DynamicChain;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class Wadjet_Entity extends Internal_Animation_Monster {
   @OnlyIn(Dist.CLIENT)
   public DynamicChain dc;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState sleepAnimationState = new AnimationState();
   public AnimationState awakeAnimationState = new AnimationState();
   public AnimationState stabnswingAnimationState = new AnimationState();
   public AnimationState doublswingAnimationState = new AnimationState();
   public AnimationState spearchargeAnimationState = new AnimationState();
   public AnimationState magicAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   public AnimationState blockAnimationState = new AnimationState();
   private static final EntityDataAccessor<Boolean> AWAKEN = SynchedEntityData.defineId(Wadjet_Entity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Boolean> STAB = SynchedEntityData.defineId(Wadjet_Entity.class, EntityDataSerializers.BOOLEAN);
   private float prevAttackProgress;
   private float AttackProgress;
   private int charge_cooldown = 0;
   public static final int CHARGE_COOLDOWN = 160;
   private int magic_cooldown = 0;
   public static final int MAGIC_COOLDOWN = 160;

   public Wadjet_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 60;
      if (world.isClientSide) {
         this.dc = new DynamicChain(this);
      }

      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setConfigattribute(this, CMCommonConfig.Wadjet.healthMultiplier, CMCommonConfig.Wadjet.attackMultiplier);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.goalSelector.addGoal(2, new InternalMoveGoal(this, false, 1.0));
      this.goalSelector.addGoal(1, new InternalStateGoal(this, 2, 2, 0, 70, 0));
      this.goalSelector.addGoal(0, new Wadjet_Entity.WadjetDoNothingGoal());
      this.goalSelector.addGoal(1, new Wadjet_Entity.ChargeAttackGoal(this, 0, 3, 0, 45, 15, 20, 5.5F, 16));
      this.goalSelector.addGoal(1, new Wadjet_Entity.MagicAttackGoal(this, 0, 4, 0, 35, 15, 3.5F, 12.0F));
      this.goalSelector.addGoal(1, new InternalAttackGoal(this, 0, 5, 0, 60, 60, 5.0F) {
         @Override
         public boolean canUse() {
            return super.canUse() && Wadjet_Entity.this.getStab();
         }

         @Override
         public void stop() {
            super.stop();
            Wadjet_Entity.this.setStab(Wadjet_Entity.this.random.nextBoolean());
         }
      });
      this.goalSelector.addGoal(1, new InternalAttackGoal(this, 0, 6, 0, 55, 55, 5.0F) {
         @Override
         public boolean canUse() {
            return super.canUse() && !Wadjet_Entity.this.getStab();
         }

         @Override
         public void stop() {
            super.stop();
            Wadjet_Entity.this.setStab(Wadjet_Entity.this.random.nextBoolean());
         }
      });
      this.goalSelector.addGoal(1, new InternalStateGoal(this, 1, 1, 0, 0, 0) {
         @Override
         public void tick() {
            this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
         }
      });
      this.goalSelector.addGoal(0, new InternalAttackGoal(this, 1, 2, 0, 70, 0, 18.0F));
      this.goalSelector.addGoal(0, new InternalStateGoal(this, 8, 8, 0, 20, 0, false));
   }

   public static Builder wadjet() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 30.0)
         .add(Attributes.MOVEMENT_SPEED, 0.28F)
         .add(Attributes.ATTACK_DAMAGE, 11.0)
         .add(Attributes.MAX_HEALTH, 150.0)
         .add(Attributes.ARMOR, 5.0)
         .add(Attributes.STEP_HEIGHT, 1.25)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
   }

   public boolean hurt(DamageSource source, float damage) {
      Entity entity = source.getDirectEntity();
      if (entity instanceof Poison_Dart_Entity) {
         return false;
      }

      if (!this.isSleep() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      }

      if (this.canBlockDamageSource(source)) {
         if (entity instanceof AbstractArrow) {
            float f = 170.0F + this.random.nextFloat() * 80.0F;
            entity.setDeltaMovement(entity.getDeltaMovement().scale(1.0));
            entity.setYRot(entity.getYRot() + f);
            entity.hurtMarked = true;
         }

         if (this.getAttackState() == 0) {
            this.playSound(SoundEvents.ANVIL_LAND, 1.0F, 2.0F);
            this.setAttackState(8);
         }

         return false;
      } else {
         return super.hurt(source, damage);
      }
   }

   public void handleDamageEvent(DamageSource damageSource) {
      this.invulnerableTime = 20;
      this.hurtDuration = 10;
      this.hurtTime = this.hurtDuration;
      SoundEvent soundevent = this.getHurtSound(damageSource);
      if (soundevent != null) {
         this.playSound(soundevent, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
      }

      this.hurt(this.damageSources().generic(), 0.0F);
      this.lastDamageSource = damageSource;
      this.lastDamageStamp = this.level().getGameTime();
   }

   private boolean canBlockDamageSource(DamageSource damageSourceIn) {
      boolean flag = false;
      if (!this.isNoAi() && damageSourceIn.is(DamageTypeTags.IS_PROJECTILE) && !flag && (this.getAttackState() == 0 || this.getAttackState() == 8)) {
         Vec3 vector3d2 = damageSourceIn.getSourcePosition();
         if (vector3d2 != null) {
            Vec3 vector3d = this.getViewVector(1.0F);
            Vec3 vector3d1 = vector3d2.vectorTo(this.position()).normalize();
            vector3d1 = new Vec3(vector3d1.x, 0.0, vector3d1.z);
            return vector3d1.dot(vector3d) < 0.0;
         }
      }

      return false;
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "sleep") {
         return this.sleepAnimationState;
      } else if (input == "awake") {
         return this.awakeAnimationState;
      } else if (input == "charge") {
         return this.spearchargeAnimationState;
      } else if (input == "magic") {
         return this.magicAnimationState;
      } else if (input == "stabnswing") {
         return this.stabnswingAnimationState;
      } else if (input == "doubleswing") {
         return this.doublswingAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "death") {
         return this.deathAnimationState;
      } else {
         return input == "block" ? this.blockAnimationState : new AnimationState();
      }
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(STAB, false);
      p_326229_.define(AWAKEN, false);
   }

   public boolean isSleep() {
      return this.getAwaken() || this.getAttackState() == 2;
   }

   public void setSleep(boolean sleep) {
      this.setAttackState(sleep ? 1 : 0);
   }

   public void setStab(boolean stab) {
      this.entityData.set(STAB, stab);
   }

   public boolean getStab() {
      return (Boolean)this.entityData.get(STAB);
   }

   public void setAwaken(boolean necklace) {
      if (necklace) {
         this.heal(this.getMaxHealth());
      }

      this.entityData.set(AWAKEN, necklace);
   }

   public boolean getAwaken() {
      return (Boolean)this.entityData.get(AWAKEN);
   }

   public boolean canBeSeenAsEnemy() {
      return !this.isSleep() && super.canBeSeenAsEnemy();
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType p_29680_, @Nullable SpawnGroupData p_29681_) {
      return super.finalizeSpawn(p_29678_, p_29679_, p_29680_, p_29681_);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_)) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.sleepAnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.awakeAnimationState.startIfStopped(this.tickCount);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.spearchargeAnimationState.startIfStopped(this.tickCount);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.magicAnimationState.startIfStopped(this.tickCount);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.stabnswingAnimationState.startIfStopped(this.tickCount);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.doublswingAnimationState.startIfStopped(this.tickCount);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.deathAnimationState.startIfStopped(this.tickCount);
               break;
            case 8:
               this.stopAllAnimationStates();
               this.blockAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.sleepAnimationState.stop();
      this.awakeAnimationState.stop();
      this.blockAnimationState.stop();
      this.spearchargeAnimationState.stop();
      this.magicAnimationState.stop();
      this.stabnswingAnimationState.stop();
      this.doublswingAnimationState.stop();
      this.deathAnimationState.stop();
   }

   @Override
   public void die(DamageSource p_21014_) {
      super.die(p_21014_);
      this.setAttackState(7);
   }

   @Override
   public int deathtimer() {
      return 60;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("Awaken", this.getAwaken());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setAwaken(compound.getBoolean("Awaken"));
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         this.idleAnimationState.animateWhen(!this.walkAnimation.isMoving() && this.getAttackState() == 0 && this.getAwaken(), this.tickCount);
      }

      this.prevAttackProgress = this.AttackProgress;
      if (this.isAggressive() && this.AttackProgress < 10.0F) {
         this.AttackProgress++;
      }

      if (!this.isAggressive() && this.AttackProgress > 0.0F) {
         this.AttackProgress--;
      }

      if (this.charge_cooldown > 0) {
         this.charge_cooldown--;
      }

      if (this.magic_cooldown > 0) {
         this.magic_cooldown--;
      }
   }

   public void aiStep() {
      super.aiStep();
      if (this.getAttackState() == 3) {
         if (this.attackTicks == 18) {
            this.playSound((SoundEvent)ModSounds.IGNIS_POKE.get(), 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.attackTicks == 20) {
            this.AreaAttack(9.0F, 6.0F, 45.0F, 1.0F, 90, false);
         }
      }

      if (this.getAttackState() == 4 && this.attackTicks == 15) {
         this.playSound(SoundEvents.EVOKER_PREPARE_ATTACK, 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
      }

      if (this.getAttackState() == 5) {
         if (this.attackTicks == 14) {
            this.playSound((SoundEvent)ModSounds.IGNIS_POKE.get(), 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(8.0F, 6.0F, 45.0F, 1.0F, 90, false);
         }

         if (this.attackTicks == 37) {
            this.playSound((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(6.0F, 4.0F, 220.0F, 1.0F, 70, true);
         }
      }

      if (this.getAttackState() == 6) {
         if (this.attackTicks == 14) {
            this.playSound((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(6.0F, 4.0F, 220.0F, 1.0F, 60, true);
         }

         if (this.attackTicks == 28) {
            this.playSound((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(6.0F, 4.0F, 220.0F, 1.0F, 60, true);
         }
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, int shieldbreakticks, boolean knockback) {
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(range, height, range, range);
      if (!this.level().isClientSide) {
         for (LivingEntity entityHit : entitiesHit) {
            float entityHitAngle = (float)((Math.atan2(entityHit.getZ() - this.getZ(), entityHit.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
            float entityAttackingAngle = this.yBodyRot % 360.0F;
            if (entityHitAngle < 0.0F) {
               entityHitAngle += 360.0F;
            }

            if (entityAttackingAngle < 0.0F) {
               entityAttackingAngle += 360.0F;
            }

            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            float entityHitDistance = (float)Math.sqrt(
               (entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ()) + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX())
            );
            if ((
                  entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                     || entityRelativeAngle >= 360.0F - arc / 2.0F
                     || entityRelativeAngle <= -360.0F + arc / 2.0F
               )
               && !this.isAlliedTo(entityHit)
               && !(entityHit instanceof Wadjet_Entity)
               && entityHit != this) {
               DamageSource damagesource = this.damageSources().mobAttack(this);
               boolean hurt = entityHit.hurt(damagesource, (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage));
               if (entityHit.isDamageSourceBlocked(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               double d0 = entityHit.getX() - this.getX();
               double d1 = entityHit.getZ() - this.getZ();
               double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
               if (hurt && knockback) {
                  entityHit.push(d0 / d2 * 2.25, 0.15, d1 / d2 * 2.25);
               }
            }
         }
      }
   }

   public float getAttackProgress(float partialTicks) {
      return this.prevAttackProgress + (this.AttackProgress - this.prevAttackProgress) * partialTicks;
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_ANCIENT_REMNANT) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.WADJET_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.WADJET_DEATH.get();
   }

   protected SoundEvent getAmbientSound() {
      return this.isSleep() ? super.getAmbientSound() : (SoundEvent)ModSounds.WADJET_AMBIENT.get();
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public boolean canBeAffected(MobEffectInstance p_34192_) {
      return p_34192_.getEffect() != ModEffect.EFFECTSTUN && p_34192_.getEffect() != ModEffect.EFFECTABYSSAL_CURSE.get() && super.canBeAffected(p_34192_);
   }

   public boolean removeWhenFarAway(double p_21542_) {
      return false;
   }

   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   protected boolean canRide(Entity p_31508_) {
      return false;
   }

   static class ChargeAttackGoal extends InternalAttackGoal {
      protected final Wadjet_Entity entity;
      private final int attackshottick;
      private final float attackminrange;

      public ChargeAttackGoal(
         Wadjet_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         int attackshottick,
         float attackminrange,
         int attackrange
      ) {
         super(entity, getattackstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.attackshottick = attackshottick;
         this.attackminrange = attackminrange;
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && this.entity.distanceTo(target) > this.attackminrange
            && super.canUse()
            && this.entity.getRandom().nextFloat() * 100.0F < 16.0F
            && this.entity.charge_cooldown <= 0;
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.charge_cooldown = 160;
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         super.tick();
         if (this.entity.attackTicks == this.attackseetick) {
            float f1 = (float)Math.cos(Math.toRadians(this.entity.yBodyRot + 90.0F));
            float f2 = (float)Math.sin(Math.toRadians(this.entity.yBodyRot + 90.0F));
            if (target != null) {
               float r = this.entity.distanceTo(target);
               r = Mth.clamp(r, 0.0F, 4.0F);
               this.entity.push(f1 * 0.3 * r, 0.0, f2 * 0.3 * r);
            } else {
               this.entity.push(f1 * 2.0, 0.0, f2 * 2.0);
            }
         }

         if (this.entity.attackTicks == this.attackshottick && target != null) {
            double d1 = 5.0;
            Vec3 vec3 = this.entity.getViewVector(1.0F);
            double d2 = target.getX() - (this.entity.getX() + vec3.x * d1);
            double d3 = target.getY(0.5) - this.entity.getY(0.15);
            double d4 = target.getZ() - (this.entity.getZ() + vec3.z * d1);
            Sandstorm_Projectile largefireball = new Sandstorm_Projectile(this.entity, d2, d3, d4, this.entity.level(), 6.0F);
            largefireball.setState(1);
            largefireball.setPos(this.entity.getX() + vec3.x * d1, this.entity.getY(0.15), largefireball.getZ() + vec3.z * d1);
            this.entity.level().addFreshEntity(largefireball);
         }
      }
   }

   static class MagicAttackGoal extends InternalAttackGoal {
      protected final Wadjet_Entity entity;
      private final float attackminrange;

      public MagicAttackGoal(
         Wadjet_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackminrange,
         float attackrange
      ) {
         super(entity, getattackstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.attackminrange = attackminrange;
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && this.entity.distanceTo(target) > this.attackminrange
            && super.canUse()
            && this.entity.getRandom().nextFloat() * 100.0F < 24.0F
            && this.entity.magic_cooldown <= 0;
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.magic_cooldown = 160;
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.entity.attackTicks == this.attackseetick) {
               double d1 = target.getY();
               float f = (float)Mth.atan2(target.getZ() - this.entity.getZ(), target.getX() - this.entity.getX());

               for (int k = 0; k < 8; k++) {
                  float f2 = f + k * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
                  this.spawnSpikeLine(this.entity.getX() + Mth.cos(f2) * 4.5, this.entity.getZ() + Mth.sin(f2) * 4.5, d1, f2, 3);
               }

               for (int k = 0; k < 13; k++) {
                  float f3 = f + k * (float) Math.PI * 2.0F / 13.0F + (float) (Math.PI / 5);
                  this.spawnSpikeLine(this.entity.getX() + Mth.cos(f3) * 6.5, this.entity.getZ() + Mth.sin(f3) * 6.5, d1, f3, 10);
               }

               for (int k = 0; k < 16; k++) {
                  float f4 = f + k * (float) Math.PI * 2.0F / 16.0F + (float) (Math.PI / 10);
                  this.spawnSpikeLine(this.entity.getX() + Mth.cos(f4) * 8.5, this.entity.getZ() + Mth.sin(f4) * 8.5, d1, f4, 15);
               }

               for (int k = 0; k < 19; k++) {
                  float f5 = f + k * (float) Math.PI * 2.0F / 19.0F + (float) (Math.PI / 20);
                  this.spawnSpikeLine(this.entity.getX() + Mth.cos(f5) * 10.5, this.entity.getZ() + Mth.sin(f5) * 10.5, d1, f5, 20);
               }

               for (int k = 0; k < 24; k++) {
                  float f6 = f + k * (float) Math.PI * 2.0F / 24.0F + 0.07853982F;
                  this.spawnSpikeLine(this.entity.getX() + Mth.cos(f6) * 12.5, this.entity.getZ() + Mth.sin(f6) * 12.5, d1, f6, 30);
               }
            }
         }
      }

      private void spawnSpikeLine(double posX, double posZ, double posY, float rotation, int delay) {
         BlockPos blockpos = BlockPos.containing(posX, posY, posZ);
         double d0 = 0.0;

         do {
            BlockPos blockpos1 = blockpos.above();
            BlockState blockstate = this.entity.level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(this.entity.level(), blockpos1, Direction.DOWN)) {
               if (!this.entity.level().isEmptyBlock(blockpos)) {
                  BlockState blockstate1 = this.entity.level().getBlockState(blockpos);
                  VoxelShape voxelshape = blockstate1.getCollisionShape(this.entity.level(), blockpos);
                  if (!voxelshape.isEmpty()) {
                     d0 = voxelshape.max(Axis.Y);
                  }
               }
               break;
            }

            blockpos = blockpos.above();
         } while (blockpos.getY() < Math.min(this.entity.level().getMaxBuildHeight(), this.entity.getBlockY() + 12));

         this.entity
            .level()
            .addFreshEntity(
               new Ancient_Desert_Stele_Entity(
                  this.entity.level(),
                  posX,
                  blockpos.getY() + d0 - 3.0,
                  posZ,
                  rotation,
                  delay,
                  (float)CMCommonConfig.Wadjet.AncientDesertSteledamage,
                  this.entity
               )
            );
      }
   }

   class WadjetDoNothingGoal extends Goal {
      public WadjetDoNothingGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean canUse() {
         LivingEntity target = Wadjet_Entity.this.getTarget();
         return !Wadjet_Entity.this.getAwaken()
            && (
               target == null
                  || !target.isAlive()
                  || !(Wadjet_Entity.this.distanceToSqr(target) < 50.0)
                  || !Wadjet_Entity.this.getSensing().hasLineOfSight(target)
            );
      }

      public void tick() {
         Wadjet_Entity.this.setDeltaMovement(0.0, Wadjet_Entity.this.getDeltaMovement().y, 0.0);
      }

      public void stop() {
         Wadjet_Entity.this.setAwaken(true);
         Wadjet_Entity.this.setAttackState(2);
      }

      public boolean isInterruptable() {
         return false;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }
}
