package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AcropolisMonsters;

import com.github.L_Ender.cataclysm.client.particle.Options.ParryParticleOptions;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Internal_Animation_Monster;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.etc.path.SemiAquaticPathNavigator;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class Hippocamtus_Entity extends Internal_Animation_Monster {
   boolean searchingForLand;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState swing1AnimationState = new AnimationState();
   public AnimationState swing2AnimationState = new AnimationState();
   public AnimationState stabAnimationState = new AnimationState();
   public AnimationState guardAnimationState = new AnimationState();
   public AnimationState guardcounterAnimationState = new AnimationState();
   public AnimationState parryingAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   public static final EntityDataAccessor<Boolean> STAB = SynchedEntityData.defineId(Hippocamtus_Entity.class, EntityDataSerializers.BOOLEAN);
   private int block_stage = 0;
   private int charge_cooldown = 0;
   public static final int CHARGE_COOLDOWN = 100;
   private int guard_cooldown = 0;
   public static final int GUARD_COOLDOWN = 160;
   protected final SemiAquaticPathNavigator waterNavigation;
   protected final CMPathNavigateGround groundNavigation;

   public Hippocamtus_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 35;
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, 0.0F);
      this.setPathfindingMalus(PathType.WATER_BORDER, 0.0F);
      this.waterNavigation = new SemiAquaticPathNavigator(this, world);
      this.groundNavigation = new CMPathNavigateGround(this, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.goalSelector.addGoal(3, new InternalMoveGoal(this, false, 1.0));
      this.goalSelector.addGoal(2, new Hippocamtus_Entity.ChargeAttackGoal(this, 0, 3, 0, 41, 13, 4.5F, 16.0F, 16.0F));
      this.goalSelector.addGoal(2, new InternalAttackGoal(this, 0, 4, 0, 50, 50, 4.0F) {
         @Override
         public boolean canUse() {
            return super.canUse() && Hippocamtus_Entity.this.getRandom().nextFloat() * 100.0F < 48.0F && Hippocamtus_Entity.this.guard_cooldown <= 0;
         }

         @Override
         public boolean canContinueToUse() {
            return super.canContinueToUse() && Hippocamtus_Entity.this.block_stage == 0;
         }

         @Override
         public void stop() {
            Hippocamtus_Entity.this.guard_cooldown = 160;
            if (Hippocamtus_Entity.this.block_stage == 1) {
               this.entity.setAttackState(5);
            } else if (Hippocamtus_Entity.this.block_stage == 2) {
               this.entity.setAttackState(6);
            } else {
               super.stop();
            }
         }
      });
      this.goalSelector.addGoal(2, new InternalAttackGoal(this, 0, 1, 0, 45, 8, 4.0F) {
         @Override
         public boolean canUse() {
            return super.canUse() && Hippocamtus_Entity.this.getStab();
         }

         @Override
         public void stop() {
            super.stop();
            Hippocamtus_Entity.this.setStab(Hippocamtus_Entity.this.random.nextBoolean());
         }
      });
      this.goalSelector.addGoal(2, new InternalAttackGoal(this, 0, 2, 0, 39, 8, 4.0F) {
         @Override
         public boolean canUse() {
            return super.canUse() && !Hippocamtus_Entity.this.getStab();
         }

         @Override
         public void stop() {
            super.stop();
            Hippocamtus_Entity.this.setStab(Hippocamtus_Entity.this.random.nextBoolean());
         }
      });
      this.goalSelector.addGoal(1, new InternalStateGoal(this, 5, 5, 0, 64, 64) {
         @Override
         public void stop() {
            super.stop();
            Hippocamtus_Entity.this.block_stage = 0;
         }
      });
      this.goalSelector.addGoal(1, new InternalStateGoal(this, 6, 6, 0, 51, 51) {
         @Override
         public void stop() {
            super.stop();
            Hippocamtus_Entity.this.block_stage = 0;
         }
      });
   }

   public static Builder Hippocamtus_Entity() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 30.0)
         .add(Attributes.MOVEMENT_SPEED, 0.28F)
         .add(Attributes.ATTACK_DAMAGE, 10.0)
         .add(Attributes.MAX_HEALTH, 85.0)
         .add(Attributes.ARMOR, 15.0)
         .add(Attributes.STEP_HEIGHT, 1.75)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
   }

   boolean wantsToSwim() {
      if (this.searchingForLand) {
         return true;
      }

      LivingEntity livingentity = this.getTarget();
      return livingentity != null && livingentity.isInWater();
   }

   public void travel(Vec3 travelVector) {
      if (this.isControlledByLocalInstance() && this.isInWater() && this.wantsToSwim()) {
         this.moveRelative(0.01F, travelVector);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
      } else {
         super.travel(travelVector);
      }
   }

   public void updateSwimming() {
      if (!this.level().isClientSide) {
         boolean inWaterAI = this.isEffectiveAi() && this.isInWater() && this.wantsToSwim();
         if (inWaterAI && !(this.moveControl instanceof Hippocamtus_Entity.HippocamtusSwimControl)) {
            this.navigation = this.waterNavigation;
            this.moveControl = new Hippocamtus_Entity.HippocamtusSwimControl(this, 4.0F);
            this.setSwimming(true);
         } else if (!inWaterAI && this.moveControl instanceof Hippocamtus_Entity.HippocamtusSwimControl) {
            this.navigation = this.groundNavigation;
            this.moveControl = new MoveControl(this);
            this.setSwimming(false);
         }
      }
   }

   public boolean hurt(DamageSource source, float damage) {
      Entity entity = source.getDirectEntity();
      if (!source.is(DamageTypeTags.BYPASSES_SHIELD) && !this.isNoAi() && this.getAttackState() == 4) {
         Vec3 vector3d2 = source.getSourcePosition();
         if (vector3d2 != null) {
            Vec3 vector3d = this.getViewVector(1.0F);
            Vec3 vector3d1 = vector3d2.vectorTo(this.position()).normalize();
            vector3d1 = new Vec3(vector3d1.x, 0.0, vector3d1.z);
            if (vector3d1.dot(vector3d) < 0.0) {
               if (this.attackTicks >= 7 && this.attackTicks <= 17) {
                  if (entity instanceof LivingEntity living) {
                     this.block_stage = 1;
                     if (!this.level().isClientSide) {
                        living.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN, 50));
                     }

                     this.playSound((SoundEvent)ModSounds.PARRY.get(), 0.8F, 1.0F);
                  }

                  return false;
               }

               if (this.attackTicks > 17 && this.attackTicks <= 43) {
                  if (entity instanceof LivingEntity living) {
                     this.block_stage = 2;
                     living.knockback(0.5, this.getX() - living.getX(), this.getZ() - living.getZ());
                  }

                  return false;
               }

               return super.hurt(source, damage);
            }
         }
      }

      return super.hurt(source, damage);
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "swing1") {
         return this.swing1AnimationState;
      } else if (input == "swing2") {
         return this.swing2AnimationState;
      } else if (input == "stab") {
         return this.stabAnimationState;
      } else if (input == "guard") {
         return this.guardAnimationState;
      } else if (input == "guardcounter") {
         return this.guardcounterAnimationState;
      } else if (input == "parry") {
         return this.parryingAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else {
         return input == "death" ? this.deathAnimationState : new AnimationState();
      }
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(STAB, false);
   }

   public void setStab(boolean stab) {
      this.entityData.set(STAB, stab);
   }

   public boolean getStab() {
      return (Boolean)this.entityData.get(STAB);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_)) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.swing1AnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.swing2AnimationState.startIfStopped(this.tickCount);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.stabAnimationState.startIfStopped(this.tickCount);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.guardAnimationState.startIfStopped(this.tickCount);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.parryingAnimationState.startIfStopped(this.tickCount);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.guardcounterAnimationState.startIfStopped(this.tickCount);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.deathAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.swing1AnimationState.stop();
      this.swing2AnimationState.stop();
      this.stabAnimationState.stop();
      this.guardAnimationState.stop();
      this.guardcounterAnimationState.stop();
      this.parryingAnimationState.stop();
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

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.HIPPOCAMTUS_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.HIPPOCAMTUS_DEATH.get();
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSounds.HIPPOCAMTUS_IDLE.get();
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         this.idleAnimationState.animateWhen(this.getAttackState() != 7, this.tickCount);
      }

      if (this.charge_cooldown > 0) {
         this.charge_cooldown--;
      }

      if (this.guard_cooldown > 0) {
         this.guard_cooldown--;
      }
   }

   public void aiStep() {
      super.aiStep();
      if (this.getAttackState() == 3) {
         if (this.attackTicks == 16) {
            this.playSound((SoundEvent)ModSounds.IGNIS_POKE.get(), 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.attackTicks == 18) {
            this.AreaAttack(8.5F, 6.0F, 45.0F, 1.0F, 90, false, false);
         }
      }

      if (this.getAttackState() == 1 && this.attackTicks == 11) {
         this.playSound((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
         this.AreaAttack(5.0F, 5.0F, 200.0F, 1.0F, 70, false, false);
      }

      if (this.getAttackState() == 2 && this.attackTicks == 11) {
         this.playSound((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
         this.AreaAttack(5.0F, 5.0F, 200.0F, 1.0F, 60, false, false);
      }

      if (this.getAttackState() == 5) {
         if (this.attackTicks == 1) {
            this.parryParticle(1.5F, 0.9F, -0.1F);
         }

         if (this.attackTicks == 35 || this.attackTicks == 42 || this.attackTicks == 48) {
            this.playSound((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(7.0F, 6.0F, 45.0F, 0.9F, 90, false, true);
         }
      }

      if (this.getAttackState() == 6 && (this.attackTicks == 10 || this.attackTicks == 17 || this.attackTicks == 24)) {
         this.playSound((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.25F + this.getRandom().nextFloat() * 0.1F);
         this.AreaAttack(7.0F, 6.0F, 45.0F, 0.9F, 90, false, true);
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, int shieldbreakticks, boolean knockback, boolean penetrate) {
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(range, height, range, range);
      if (!this.level().isClientSide) {
         DamageSource damagesource = penetrate ? CMDamageTypes.causePenetrateDamage(this) : this.damageSources().mobAttack(this);

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
               && !(entityHit instanceof Hippocamtus_Entity)
               && entityHit != this) {
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

   private void parryParticle(float height, float vec, float math) {
      if (this.level().isClientSide) {
         double d0 = this.getX();
         double d1 = this.getY() + height;
         double d2 = this.getZ();
         float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         double theta = this.yBodyRot * (Math.PI / 180.0);
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);
         double theta2 = theta + (Math.PI / 2);
         double X = Math.cos(theta2);
         double Z = Math.sin(theta2);

         for (int i = 0; i < 12; i++) {
            float throwAngle = i * (float) Math.PI / 6.0F;
            double y = 2.0 * Math.sin(throwAngle);
            double xz = 2.0 * Math.cos(throwAngle);
            double d3 = xz * vecX + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
            double d4 = y + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
            double d5 = xz * vecZ + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
            double speed = 0.35;
            this.level()
               .addParticle(
                  new ParryParticleOptions(1.0F, 0.41568628F, 0.0F), d0 + vec * X + f * math, d1, d2 + vec * Z + f1 * math, d3 * speed, d4 * speed, d5 * speed
               );
         }
      }
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_SCYLLA) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public boolean canBeAffected(MobEffectInstance p_34192_) {
      return p_34192_.getEffect() != ModEffect.EFFECTABYSSAL_CURSE.get() && super.canBeAffected(p_34192_);
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
      protected final Hippocamtus_Entity entity;
      private final float attackminrange;
      private final float random;

      public ChargeAttackGoal(
         Hippocamtus_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackminrange,
         float attackrange,
         float random
      ) {
         super(entity, getattackstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.attackminrange = attackminrange;
         this.random = random;
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse()
            && target != null
            && this.entity.distanceTo(target) > this.attackminrange
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.charge_cooldown <= 0;
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.charge_cooldown = 100;
      }

      @Override
      public void tick() {
         super.tick();
         LivingEntity target = this.entity.getTarget();
         if (this.entity.attackTicks == this.attackseetick) {
            float f1 = (float)Math.cos(Math.toRadians(this.entity.yBodyRot + 90.0F));
            float f2 = (float)Math.sin(Math.toRadians(this.entity.yBodyRot + 90.0F));
            if (target != null) {
               float r = this.entity.distanceTo(target);
               r = Mth.clamp(r, 0.0F, 5.0F);
               this.entity.push(f1 * 0.4 * r, 0.0, f2 * 0.4 * r);
            } else {
               this.entity.push(f1 * 2.0, 0.0, f2 * 2.0);
            }
         }
      }
   }

   static class HippocamtusSwimControl extends MoveControl {
      private final Hippocamtus_Entity drowned;
      private final float speedMulti;

      public HippocamtusSwimControl(Hippocamtus_Entity p_32433_, float speedMulti) {
         super(p_32433_);
         this.drowned = p_32433_;
         this.speedMulti = speedMulti;
      }

      public void tick() {
         LivingEntity livingentity = this.drowned.getTarget();
         if (this.drowned.wantsToSwim() && this.drowned.isInWater()) {
            if (livingentity != null && livingentity.getY() > this.drowned.getY() || this.drowned.searchingForLand) {
               this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0, 0.002, 0.0));
            }

            if (this.operation != Operation.MOVE_TO || this.drowned.getNavigation().isDone()) {
               this.drowned.setSpeed(0.0F);
               return;
            }

            double d0 = this.wantedX - this.drowned.getX();
            double d1 = this.wantedY - this.drowned.getY();
            double d2 = this.wantedZ - this.drowned.getZ();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 /= d3;
            float f = (float)(Mth.atan2(d2, d0) * 180.0F / (float)Math.PI) - 90.0F;
            this.drowned.setYRot(this.rotlerp(this.drowned.getYRot(), f, 90.0F));
            this.drowned.yBodyRot = this.drowned.getYRot();
            float f1 = (float)(this.speedModifier * this.speedMulti * this.drowned.getAttributeValue(Attributes.MOVEMENT_SPEED));
            float f2 = Mth.lerp(0.125F, this.drowned.getSpeed(), f1);
            this.drowned.setSpeed(f2);
            this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(f2 * d0 * 0.005, f2 * d1 * 0.1, f2 * d2 * 0.005));
         } else {
            if (!this.drowned.onGround()) {
               this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0, -0.008, 0.0));
            }

            super.tick();
         }
      }
   }
}
