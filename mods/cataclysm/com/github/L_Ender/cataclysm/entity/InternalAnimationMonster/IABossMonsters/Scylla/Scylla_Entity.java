package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Scylla;

import com.github.L_Ender.cataclysm.blockentities.Boss_Respawn_Spawner_Block_Entity;
import com.github.L_Ender.cataclysm.client.particle.Options.CircleLightningParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.GatheringWaterParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.LightningStormParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.NotSpinTrailParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.ParryParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.RingParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.RoarParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.ScyllaSwingParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.StormParticleOptions;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AI.HurtByNearestTargetGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.IABoss_monster;
import com.github.L_Ender.cataclysm.entity.effect.Lightning_Area_Effect_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Lightning_Storm_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Lightning_Spear_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Spark_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Storm_Serpent_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Water_Spear_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
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
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;

public class Scylla_Entity extends IABoss_monster {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState CrossSwingAnimationState = new AnimationState();
   public AnimationState CrossSwing2AnimationState = new AnimationState();
   public AnimationState DoubleSwingAnimationState = new AnimationState();
   public AnimationState SwingSmashAnimationState = new AnimationState();
   public AnimationState SmashAnimationState = new AnimationState();
   public AnimationState backstepanimationState = new AnimationState();
   public AnimationState spinAnimationState = new AnimationState();
   public AnimationState waveAnimationState = new AnimationState();
   public AnimationState lightningexplosionAnimationState = new AnimationState();
   public AnimationState WaterSpearAnimationState = new AnimationState();
   public AnimationState LightningSpearAnimationState = new AnimationState();
   public AnimationState AnchorShotAnimationState = new AnimationState();
   public AnimationState AnchorShotPullAnimationState = new AnimationState();
   public AnimationState AnchorExplosionAnimationState = new AnimationState();
   public AnimationState ChainJump1AnimationState = new AnimationState();
   public AnimationState ChainJump2AnimationState = new AnimationState();
   public AnimationState ChainJump3AnimationState = new AnimationState();
   public AnimationState ParryAnimationState = new AnimationState();
   public AnimationState Enchant1AnimationState = new AnimationState();
   public AnimationState Enchant2AnimationState = new AnimationState();
   public AnimationState ElectricWhipAnimationState = new AnimationState();
   public AnimationState WhipAndSpearAnimationState = new AnimationState();
   public AnimationState SpawnIdleAnimationState = new AnimationState();
   public AnimationState SpawnAnimationState = new AnimationState();
   public AnimationState SummonSnakeAnimationState = new AnimationState();
   public AnimationState GrabSmashAnimationState = new AnimationState();
   public AnimationState DeathAnimationState = new AnimationState();
   public static final EntityDataAccessor<Boolean> EYE = SynchedEntityData.defineId(Scylla_Entity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Boolean> CHAIN_ANCHOR = SynchedEntityData.defineId(Scylla_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Optional<UUID>> ANCHOR_UUID = SynchedEntityData.defineId(Scylla_Entity.class, EntityDataSerializers.OPTIONAL_UUID);
   private static final EntityDataAccessor<Integer> ANCHOR_ID = SynchedEntityData.defineId(Scylla_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(Scylla_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> PARRY_COUNT = SynchedEntityData.defineId(Scylla_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(Scylla_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> ACT = SynchedEntityData.defineId(Scylla_Entity.class, EntityDataSerializers.BOOLEAN);
   private int spin_cooldown = 0;
   public static final int SPIN_COOLDOWN = 160;
   public static final int ATTACK_DELAY = 3;
   private int anchor_pull_cooldown = 0;
   public static final int ANCHOR_COOLDOWN = 140;
   private int wave_cooldown = 0;
   public static final int WAVE_COOLDOWN = 200;
   private int flying_cooldown = 160;
   public static final int FLYING_COOLDOWN = 400;
   private int spear_cooldown = 0;
   public static final int SPEAR_COOLDOWN = 80;
   private int thundercloud_cooldown = 0;
   public static final int THUNDER_CLOUD_COOLDOWN = 350;
   private int summon_snake_cooldown = 0;
   public static final int SUMMON_SNAKE_COOLDOWN = 350;
   private int back_step_cooldown = 0;
   public static final int BACK_STEP_COOLDOWN = 150;
   private int explosion_cooldown = 0;
   public static final int EXPLOSION_COOLDOWN = 300;
   private int whip_cooldown = 0;
   public static final int WHIP_COOLDOWN = 400;
   private int parry_cooldown = 0;
   public static final int PARRY_COOLDOWN = 100;
   public static final int MAX_PARRY_COUNT = 18;
   public static final int CAN_PARRY = 25;
   private int destroyBlocksTick;
   private final CMBossInfoServer bossEvent = new CMBossInfoServer(this.getDisplayName(), BossBarColor.BLUE, true, 12);

   public Scylla_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 500;
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setConfigattribute(this, CMCommonConfig.Scylla.healthMultiplier, CMCommonConfig.Scylla.attackMultiplier);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByNearestTargetGoal(this));
      this.goalSelector.addGoal(4, new InternalMoveGoal(this, false, 1.0));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.goalSelector.addGoal(0, new Scylla_Entity.Scylla_EntityPhaseChangeGoal(this, 0, 21, 0, 55, 1, 0.6666667F));
      this.goalSelector.addGoal(0, new Scylla_Entity.Scylla_EntityPhaseChangeGoal(this, 0, 22, 0, 68, 2, 0.33333334F));
      this.goalSelector.addGoal(3, new Scylla_Entity.HorizontalSwingGoal(this, 0, 6.75F, 38.0F));
      this.goalSelector.addGoal(3, new InternalAttackGoal(this, 0, 5, 0, 44, 14, 3.75F) {
         @Override
         public boolean canUse() {
            return super.canUse() && Scylla_Entity.this.getRandom().nextFloat() * 100.0F < 14.0F;
         }
      });
      this.goalSelector.addGoal(3, new Scylla_Entity.SummonSnake(this, 0, 26, 0, 75, 5, 6.0F, 45.0F));
      this.goalSelector.addGoal(3, new Scylla_Entity.SpearThrowGoal(this, 0, 7.0F, 25.0F, 28, 16.0F));
      this.goalSelector.addGoal(3, new Scylla_Entity.AnchorThrowGoal(this, 0, 13, 0, 90, 25, 6.0F, 22.0F, 12.0F));
      this.goalSelector.addGoal(1, new InternalStateGoal(this, 23, 23, 24, 0, 0) {
         @Override
         public boolean canContinueToUse() {
            return super.canContinueToUse() && !Scylla_Entity.this.getAct();
         }

         @Override
         public void tick() {
            this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
         }
      });
      this.goalSelector.addGoal(0, new InternalStateGoal(this, 24, 24, 0, 40, 0) {
         @Override
         public void start() {
            super.start();
            Scylla_Entity.this.setEye(true);
         }

         @Override
         public void stop() {
            super.stop();
            Scylla_Entity.this.setEye(false);
         }
      });
      this.goalSelector.addGoal(2, new InternalStateGoal(this, 14, 14, 0, 23, 10) {
         @Override
         public void stop() {
            Entity weapon = Scylla_Entity.this.getAnchor();
            if (weapon != null) {
               weapon.discard();
            }

            LivingEntity target = this.entity.getTarget();
            if (target != null && target.isAlive() && this.entity.distanceTo(target) < 4.0F) {
               this.entity.setAttackState(27);
            } else {
               super.stop();
            }
         }
      });
      this.goalSelector.addGoal(2, new InternalStateGoal(this, 27, 27, 0, 35, 7));
      this.goalSelector.addGoal(3, new Scylla_Entity.Back_StepGoal(this, 0, 6, 0, 16, 16, 3.0F, 28.0F));
      this.goalSelector.addGoal(3, new InternalAttackGoal(this, 0, 7, 0, 87, 56, 4.4F) {
         @Override
         public boolean canUse() {
            return super.canUse() && this.entity.getRandom().nextFloat() * 100.0F < 22.0F && Scylla_Entity.this.spin_cooldown <= 0;
         }

         @Override
         public void start() {
            super.start();
            Scylla_Entity.this.setChainAnchor(true);
         }

         @Override
         public void tick() {
            LivingEntity target = this.entity.getTarget();
            if (target != null && this.entity.attackTicks < 56 && this.entity.attackTicks >= 12) {
               this.entity.getNavigation().moveTo(target, 1.2F);
            } else {
               this.entity.getNavigation().stop();
            }

            if (target != null) {
               boolean flag = this.entity.attackTicks < this.attackseetick;
               if (flag) {
                  this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
                  this.entity.lookAt(target, 30.0F, 30.0F);
               } else {
                  this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
                  this.entity.setYRot(this.entity.yRotO);
               }
            } else {
               this.entity.setYRot(this.entity.yRotO);
            }
         }

         @Override
         public void stop() {
            super.stop();
            Scylla_Entity.this.setChainAnchor(false);
            Scylla_Entity.this.spin_cooldown = 160;
         }
      });
      this.goalSelector
         .addGoal(
            3,
            new InternalAttackGoal(this, 0, 8, 0, 65, 35, 15.0F) {
               @Override
               public boolean canUse() {
                  LivingEntity target = this.entity.getTarget();
                  return super.canUse()
                     && this.entity.getRandom().nextFloat() * 100.0F < 24.0F
                     && target != null
                     && this.entity.distanceTo(target) >= 6.5 & Scylla_Entity.this.wave_cooldown <= 0;
               }

               @Override
               public void start() {
                  super.start();
                  Scylla_Entity.this.setEye(true);
               }

               @Override
               public void stop() {
                  super.stop();
                  Scylla_Entity.this.setEye(false);
                  Scylla_Entity.this.wave_cooldown = 200;
               }
            }
         );
      this.goalSelector
         .addGoal(
            3,
            new InternalAttackGoal(this, 0, 20, 0, 50, 17, 15.0F) {
               @Override
               public boolean canUse() {
                  LivingEntity target = this.entity.getTarget();
                  return super.canUse()
                     && this.entity.getRandom().nextFloat() * 100.0F < 30.0F
                     && target != null
                     && this.entity.distanceTo(target) <= 8.0F
                     && Scylla_Entity.this.whip_cooldown <= 0
                     && Scylla_Entity.this.isPhase() < 2
                     && Scylla_Entity.this.isPhase() > 0;
               }

               @Override
               public void start() {
                  super.start();
                  Scylla_Entity.this.setEye(true);
                  Scylla_Entity.this.setChainAnchor(true);
               }

               @Override
               public void stop() {
                  super.stop();
                  Scylla_Entity.this.setEye(false);
                  Scylla_Entity.this.setChainAnchor(false);
                  Scylla_Entity.this.whip_cooldown = 400;
               }
            }
         );
      this.goalSelector.addGoal(3, new Scylla_Entity.WhipAndSpearGoal(this, 0, 25, 0, 130, 0, 8.0F, 15.0F, 30.0F));
      this.goalSelector.addGoal(3, new Scylla_Entity.ThunderCloud(this, 0, 9, 0, 150, 55, 15.0F, 16.0F));
      this.goalSelector.addGoal(3, new Scylla_Entity.Scylla_Flying(this, 0, 15, 16, 70, 70, 40.0F, 47, 27.0F));
      this.goalSelector.addGoal(3, new Scylla_Entity.ScyllafallingState(this, 16, 16, 17, 44, 43));
      this.goalSelector.addGoal(2, new InternalStateGoal(this, 17, 17, 0, 23, 0));
      this.goalSelector.addGoal(3, new Scylla_Entity.Scylla_Lightning_Explosion(this, 0, 18, 0, 85, 16, 16.0F, 26, 54, 13, 20.0F));
      this.goalSelector.addGoal(2, new InternalStateGoal(this, 19, 19, 0, 15, 15) {
         @Override
         public void stop() {
            LivingEntity target = this.entity.getTarget();
            if (Scylla_Entity.this.back_step_cooldown <= 0 && target != null) {
               this.entity.setAttackState(6);
            } else {
               super.stop();
            }

            Scylla_Entity.this.setParryCount(0);
            Scylla_Entity.this.parry_cooldown = 100;
         }
      });
   }

   public static Builder scylla() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 50.0)
         .add(Attributes.MOVEMENT_SPEED, 0.31F)
         .add(Attributes.ATTACK_DAMAGE, 18.0)
         .add(Attributes.MAX_HEALTH, 390.0)
         .add(Attributes.ARMOR, 12.0)
         .add(Attributes.STEP_HEIGHT, 1.75)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         if (this.canBlockFaceSource(source) && !this.level().isClientSide) {
            if (this.CanParryState()) {
               if (this.getParryCount() >= 18) {
                  this.setParryCount(25);
                  this.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.8F);
                  this.level().broadcastEntityEvent(this, (byte)11);
                  return false;
               }

               if (this.random.nextFloat() * 100.0F < (this.getParryCount() + 1) * 5 && this.parry_cooldown <= 0) {
                  this.setParryCount(25);
                  this.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.8F);
                  this.level().broadcastEntityEvent(this, (byte)11);
                  return false;
               }
            }

            if (this.getAttackState() == 19 && this.attackTicks < 11 && this.attackTicks > 1) {
               this.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.8F);
               this.level().broadcastEntityEvent(this, (byte)11);
               return false;
            }
         }

         if (this.getAttackState() == 21 || this.getAttackState() == 22) {
            return false;
         }

         if (this.isSleep()) {
            return false;
         }
      }

      if (this.destroyBlocksTick <= 0) {
         this.destroyBlocksTick = 20;
      }

      boolean flag = super.hurt(source, damage);
      if (flag && this.canBlockFaceSource(source) && this.getParryCount() < 18) {
         this.setParryCount(this.getParryCount() + (source.is(DamageTypeTags.IS_PROJECTILE) ? 2 : 1));
      }

      return flag;
   }

   private boolean CanParryState() {
      return this.attackTicks > 35 && this.getAttackState() == 1
         || this.attackTicks > 35 && this.getAttackState() == 2
         || this.attackTicks > 51 && this.getAttackState() == 3
         || this.attackTicks > 50 && this.getAttackState() == 4;
   }

   public void handleDamageEvent(DamageSource damageSource) {
      if (this.getAttackState() == 0) {
         this.walkAnimation.setSpeed(1.5F);
      }

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

   private boolean canBlockFaceSource(DamageSource damageSource) {
      if (!this.isNoAi()) {
         Vec3 vec32 = damageSource.getSourcePosition();
         if (vec32 != null) {
            Vec3 vec3 = this.calculateViewVector(0.0F, this.getYHeadRot());
            Vec3 vec31 = vec32.vectorTo(this.position());
            vec31 = new Vec3(vec31.x, 0.0, vec31.z).normalize();
            return vec31.dot(vec3) < 0.0;
         }
      }

      return false;
   }

   @Override
   public float DamageCap() {
      return (float)CMCommonConfig.Scylla.damageCap;
   }

   @Override
   public float NatureRegen() {
      return (float)CMCommonConfig.Scylla.natureHeal;
   }

   @Override
   public float DpsCap() {
      return (float)CMCommonConfig.Scylla.dpsCap;
   }

   @Override
   public double RangeLimit() {
      return CMCommonConfig.Scylla.rangeCap;
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   public boolean canBeSeenAsEnemy() {
      return !this.isSleep() && super.canBeSeenAsEnemy();
   }

   @Override
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType spawnType, @Nullable SpawnGroupData p_29681_) {
      if (spawnType == MobSpawnType.COMMAND || spawnType == MobSpawnType.SPAWN_EGG || MobSpawnType.isSpawner(spawnType) || spawnType == MobSpawnType.DISPENSER) {
         this.setAct(true);
      }

      return super.finalizeSpawn(p_29678_, p_29679_, spawnType, p_29681_);
   }

   public AnimationState getAnimationState(String input) {
      if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "cross_swing") {
         return this.CrossSwingAnimationState;
      } else if (input == "cross_swing2") {
         return this.CrossSwing2AnimationState;
      } else if (input == "double_swing") {
         return this.DoubleSwingAnimationState;
      } else if (input == "swing_smash") {
         return this.SwingSmashAnimationState;
      } else if (input == "smash") {
         return this.SmashAnimationState;
      } else if (input == "back_step") {
         return this.backstepanimationState;
      } else if (input == "spin") {
         return this.spinAnimationState;
      } else if (input == "wave") {
         return this.waveAnimationState;
      } else if (input == "lightning_explosion") {
         return this.lightningexplosionAnimationState;
      } else if (input == "lightning_spear_throw") {
         return this.LightningSpearAnimationState;
      } else if (input == "water_spear_throw") {
         return this.WaterSpearAnimationState;
      } else if (input == "death") {
         return this.DeathAnimationState;
      } else if (input == "anchor_shot") {
         return this.AnchorShotAnimationState;
      } else if (input == "anchor_shot_pull") {
         return this.AnchorShotPullAnimationState;
      } else if (input == "chain_jump_1") {
         return this.ChainJump1AnimationState;
      } else if (input == "chain_jump_2") {
         return this.ChainJump2AnimationState;
      } else if (input == "chain_jump_3") {
         return this.ChainJump3AnimationState;
      } else if (input == "anchor_explosion") {
         return this.AnchorExplosionAnimationState;
      } else if (input == "parry") {
         return this.ParryAnimationState;
      } else if (input == "electric_whip") {
         return this.ElectricWhipAnimationState;
      } else if (input == "whip_and_spear") {
         return this.WhipAndSpearAnimationState;
      } else if (input == "enchant_1") {
         return this.Enchant1AnimationState;
      } else if (input == "enchant_2") {
         return this.Enchant2AnimationState;
      } else if (input == "spawn_idle") {
         return this.SpawnIdleAnimationState;
      } else if (input == "spawn") {
         return this.SpawnAnimationState;
      } else if (input == "summon_snake") {
         return this.SummonSnakeAnimationState;
      } else {
         return input == "grab_smash" ? this.GrabSmashAnimationState : new AnimationState();
      }
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(EYE, false);
      p_326229_.define(CHAIN_ANCHOR, false);
      p_326229_.define(ANCHOR_UUID, Optional.empty());
      p_326229_.define(ANCHOR_ID, -1);
      p_326229_.define(FLYING, false);
      p_326229_.define(PARRY_COUNT, 0);
      p_326229_.define(PHASE, 0);
      p_326229_.define(ACT, false);
   }

   public boolean getEye() {
      return (Boolean)this.entityData.get(EYE);
   }

   public void setEye(boolean weapon) {
      this.entityData.set(EYE, weapon);
   }

   public boolean isFlying() {
      return (Boolean)this.entityData.get(FLYING);
   }

   public void setFlying(boolean flying) {
      this.entityData.set(FLYING, flying);
   }

   public int isPhase() {
      return (Integer)this.entityData.get(PHASE);
   }

   public void setPhase(int phase) {
      this.entityData.set(PHASE, phase);
   }

   public int getParryCount() {
      return (Integer)this.entityData.get(PARRY_COUNT);
   }

   public void setParryCount(int parry) {
      this.entityData.set(PARRY_COUNT, parry);
   }

   public boolean getChainAnchor() {
      return (Boolean)this.entityData.get(CHAIN_ANCHOR);
   }

   public void setChainAnchor(boolean weapon) {
      this.entityData.set(CHAIN_ANCHOR, weapon);
   }

   public boolean isSleep() {
      return this.getAttackState() == 23 || this.getAttackState() == 24;
   }

   public void setAct(boolean necklace) {
      this.entityData.set(ACT, necklace);
      this.bossEvent.setVisible(necklace);
      if (!necklace) {
         this.setAttackState(23);
      }
   }

   public boolean getAct() {
      return (Boolean)this.entityData.get(ACT);
   }

   @Nullable
   public UUID getAnchorUUID() {
      return (UUID)((Optional)this.entityData.get(ANCHOR_UUID)).orElse(null);
   }

   public void setAnchorUUID(@Nullable UUID uniqueId) {
      this.entityData.set(ANCHOR_UUID, Optional.ofNullable(uniqueId));
   }

   public Entity getAnchor() {
      if (!this.level().isClientSide) {
         UUID id = this.getAnchorUUID();
         return id == null ? null : ((ServerLevel)this.level()).getEntity(id);
      } else {
         int id = (Integer)this.entityData.get(ANCHOR_ID);
         return id == -1 ? null : this.level().getEntity(id);
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_)) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.CrossSwingAnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.CrossSwing2AnimationState.startIfStopped(this.tickCount);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.DoubleSwingAnimationState.startIfStopped(this.tickCount);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.SwingSmashAnimationState.startIfStopped(this.tickCount);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.SmashAnimationState.startIfStopped(this.tickCount);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.backstepanimationState.startIfStopped(this.tickCount);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.spinAnimationState.startIfStopped(this.tickCount);
               break;
            case 8:
               this.stopAllAnimationStates();
               this.waveAnimationState.startIfStopped(this.tickCount);
               break;
            case 9:
               this.stopAllAnimationStates();
               this.lightningexplosionAnimationState.startIfStopped(this.tickCount);
               break;
            case 10:
               this.stopAllAnimationStates();
               this.LightningSpearAnimationState.startIfStopped(this.tickCount);
               break;
            case 11:
               this.stopAllAnimationStates();
               this.WaterSpearAnimationState.startIfStopped(this.tickCount);
               break;
            case 12:
               this.stopAllAnimationStates();
               this.DeathAnimationState.startIfStopped(this.tickCount);
               break;
            case 13:
               this.stopAllAnimationStates();
               this.AnchorShotAnimationState.startIfStopped(this.tickCount);
               break;
            case 14:
               this.stopAllAnimationStates();
               this.AnchorShotPullAnimationState.startIfStopped(this.tickCount);
               break;
            case 15:
               this.stopAllAnimationStates();
               this.ChainJump1AnimationState.startIfStopped(this.tickCount);
               break;
            case 16:
               this.stopAllAnimationStates();
               this.ChainJump2AnimationState.startIfStopped(this.tickCount);
               break;
            case 17:
               this.stopAllAnimationStates();
               this.ChainJump3AnimationState.startIfStopped(this.tickCount);
               break;
            case 18:
               this.stopAllAnimationStates();
               this.AnchorExplosionAnimationState.startIfStopped(this.tickCount);
               break;
            case 19:
               this.stopAllAnimationStates();
               this.ParryAnimationState.startIfStopped(this.tickCount);
               break;
            case 20:
               this.stopAllAnimationStates();
               this.ElectricWhipAnimationState.startIfStopped(this.tickCount);
               break;
            case 21:
               this.stopAllAnimationStates();
               this.Enchant1AnimationState.startIfStopped(this.tickCount);
               break;
            case 22:
               this.stopAllAnimationStates();
               this.Enchant2AnimationState.startIfStopped(this.tickCount);
               break;
            case 23:
               this.stopAllAnimationStates();
               this.SpawnIdleAnimationState.startIfStopped(this.tickCount);
               break;
            case 24:
               this.stopAllAnimationStates();
               this.SpawnAnimationState.startIfStopped(this.tickCount);
               break;
            case 25:
               this.stopAllAnimationStates();
               this.WhipAndSpearAnimationState.startIfStopped(this.tickCount);
               break;
            case 26:
               this.stopAllAnimationStates();
               this.SummonSnakeAnimationState.startIfStopped(this.tickCount);
               break;
            case 27:
               this.stopAllAnimationStates();
               this.GrabSmashAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   @Override
   public void handleEntityEvent(byte id) {
      if (id == 11) {
         this.parryParticle(1.5F, 0.9F, 0.0F);
      } else {
         super.handleEntityEvent(id);
      }
   }

   public void stopAllAnimationStates() {
      this.CrossSwingAnimationState.stop();
      this.CrossSwing2AnimationState.stop();
      this.SmashAnimationState.stop();
      this.backstepanimationState.stop();
      this.spinAnimationState.stop();
      this.waveAnimationState.stop();
      this.lightningexplosionAnimationState.stop();
      this.LightningSpearAnimationState.stop();
      this.WaterSpearAnimationState.stop();
      this.DeathAnimationState.stop();
      this.AnchorShotAnimationState.stop();
      this.AnchorShotPullAnimationState.stop();
      this.ChainJump1AnimationState.stop();
      this.ChainJump2AnimationState.stop();
      this.ChainJump3AnimationState.stop();
      this.AnchorExplosionAnimationState.stop();
      this.DoubleSwingAnimationState.stop();
      this.SwingSmashAnimationState.stop();
      this.ParryAnimationState.stop();
      this.ElectricWhipAnimationState.stop();
      this.Enchant1AnimationState.stop();
      this.Enchant2AnimationState.stop();
      this.SpawnIdleAnimationState.stop();
      this.SpawnAnimationState.stop();
      this.WhipAndSpearAnimationState.stop();
      this.SummonSnakeAnimationState.stop();
      this.GrabSmashAnimationState.stop();
   }

   @Override
   public void die(DamageSource p_21014_) {
      super.die(p_21014_);
      this.setEye(true);
      if (this.isFlying()) {
         this.setFlying(false);
      }

      this.setAttackState(12);
   }

   @Override
   protected void AfterDefeatBoss(@Nullable LivingEntity living) {
      if (CMCommonConfig.Scylla.respawner && !this.level().isClientSide && this.getHomePos() != null && this.level() instanceof ServerLevel serverLevel) {
         MinecraftServer server = serverLevel.getServer();
         ServerLevel targetLevel = server.getLevel(this.getHomePos().dimension());
         if (targetLevel != null) {
            BlockPos targetPos = this.getHomePos().pos();
            BlockState blockState = ((Block)ModBlocks.BOSS_RESPAWNER.get()).defaultBlockState();
            targetLevel.setBlock(targetPos, blockState, 2);
            if (targetLevel.getBlockEntity(targetPos) instanceof Boss_Respawn_Spawner_Block_Entity spawnerBlockEntity) {
               spawnerBlockEntity.setEntityId((EntityType<?>)ModEntities.SCYLLA.get());
               spawnerBlockEntity.setTheItem(((Item)ModItems.STORM_EYE.get()).getDefaultInstance());
            }
         }
      }
   }

   @Override
   public int deathtimer() {
      return 160;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      if (this.getAnchorUUID() != null) {
         compound.putUUID("AnchorUUID", this.getAnchorUUID());
      }

      compound.putInt("Phase", this.isPhase());
      compound.putBoolean("Act", this.getAct());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.hasUUID("AnchorUUID")) {
         this.setAnchorUUID(compound.getUUID("AnchorUUID"));
      }

      this.setPhase(compound.getInt("Phase"));
      this.setAct(compound.getBoolean("Act"));
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         this.idleAnimationState.animateWhen(this.getAttackState() == 0, this.tickCount);
      } else {
         this.bossEvent.setLife(this.getLife());
      }

      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
      if (this.getAnchor() instanceof Scylla_Ceraunus_Entity anchor) {
         this.entityData.set(ANCHOR_ID, anchor.getId());
         anchor.setControllerUUID(this.getUUID());
      }

      if (this.isFlying()) {
         this.setNoGravity(!this.verticalCollisionBelow);
      } else {
         this.setNoGravity(false);
      }

      if (this.spin_cooldown > 0) {
         this.spin_cooldown--;
      }

      if (this.anchor_pull_cooldown > 0) {
         this.anchor_pull_cooldown--;
      }

      if (this.flying_cooldown > 0) {
         this.flying_cooldown--;
      }

      if (this.thundercloud_cooldown > 0) {
         this.thundercloud_cooldown--;
      }

      if (this.back_step_cooldown > 0) {
         this.back_step_cooldown--;
      }

      if (this.explosion_cooldown > 0) {
         this.explosion_cooldown--;
      }

      if (this.whip_cooldown > 0) {
         this.whip_cooldown--;
      }

      if (this.wave_cooldown > 0) {
         this.wave_cooldown--;
      }

      if (this.spear_cooldown > 0) {
         this.spear_cooldown--;
      }

      if (this.parry_cooldown > 0) {
         this.parry_cooldown--;
      }

      if (this.summon_snake_cooldown > 0) {
         this.summon_snake_cooldown--;
      }

      this.blockbreak();
      this.floatScylla();
      float dis = this.getBbWidth() * 0.75F;
      this.repelEntities(dis, this.getBbHeight(), dis, dis);
   }

   private void blockbreak() {
      if (!this.isNoAi() && !this.isSleep() && !this.level().isClientSide && this.destroyBlocksTick > 0) {
         this.destroyBlocksTick--;
         if (this.destroyBlocksTick == 0 && EventHooks.canEntityGrief(this.level(), this)) {
            boolean flag = false;
            AABB aabb = this.getBoundingBox().inflate(0.2);

            for (BlockPos blockpos : BlockPos.betweenClosed(
               Mth.floor(aabb.minX), this.getBlockY(), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
            )) {
               BlockState blockstate = this.level().getBlockState(blockpos);
               if (!blockstate.isAir()
                  && blockstate.canEntityDestroy(this.level(), blockpos, this)
                  && !blockstate.is(ModTag.SCYLLA_IMMUNE)
                  && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)) {
                  flag = this.level().destroyBlock(blockpos, true, this) || flag;
               }
            }

            if (flag) {
               this.level().levelEvent((Player)null, 1022, this.blockPosition(), 0);
            }
         }
      }
   }

   public void aiStep() {
      super.aiStep();
      float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
      float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      if (this.getAttackState() == 1 && this.attackTicks == 15) {
         this.playSound((SoundEvent)ModSounds.STRONGSWING.get(), 2.0F, 0.75F + this.getRandom().nextFloat() * 0.1F);
         this.AreaAttack(3.25F, 6.0F, 100.0F, 1.0F, 80, true);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 10.0F, 0.1F, 0, 20);
         this.SwingParticle(vecX, -5.0, vecZ, 1.5, false, 0.95F);
      }

      if (this.getAttackState() == 2 && this.attackTicks == 15) {
         this.playSound((SoundEvent)ModSounds.STRONGSWING.get(), 2.0F, 0.75F + this.getRandom().nextFloat() * 0.1F);
         this.AreaAttack(3.25F, 6.0F, 100.0F, 1.0F, 80, true);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 10.0F, 0.07F, 0, 20);
         this.SwingParticle(vecX, -5.0, vecZ, 1.5, true, 0.95F);
      }

      if (this.getAttackState() == 3) {
         if (this.attackTicks == 16) {
            this.playSound((SoundEvent)ModSounds.STRONGSWING.get(), 2.0F, 0.75F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(3.25F, 6.0F, 100.0F, 0.9F, 20, true);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 10.0F, 0.07F, 0, 20);
            this.SwingParticle(vecX, -5.0, vecZ, 1.5, false, 0.95F);
         }

         if (this.attackTicks == 30) {
            this.playSound((SoundEvent)ModSounds.STRONGSWING.get(), 2.0F, 0.75F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(3.25F, 6.0F, 100.0F, 0.9F, 80, true);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 10.0F, 0.07F, 0, 20);
            this.SwingParticle(vecX, -5.0, vecZ, 1.5, true, 0.95F);
         }
      }

      if (this.getAttackState() == 4) {
         if (this.attackTicks == 16) {
            this.playSound((SoundEvent)ModSounds.STRONGSWING.get(), 2.0F, 0.75F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(3.25F, 6.0F, 100.0F, 1.0F, 0, true);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 10.0F, 0.07F, 0, 20);
            this.SwingParticle(vecX, -5.0, vecZ, 1.5, false, 0.95F);
         }

         if (this.attackTicks == 28) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 20);
            this.playSound((SoundEvent)ModSounds.HEAVY_SMASH.get(), 0.8F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(5.0F, 6.75F, 65.0F, 1.1F, 120, false);
            if (!this.level().isClientSide) {
               this.spawnLightning(this.getX() + vecX * 3.25, this.getZ() + vecZ * 3.25, this.getY() - 2.0, this.getY() + 5.0, (float)theta, -9, 2.0F);
            } else {
               double vec = 1.5;
               double d0 = this.getX() + vecX * vec;
               double d1 = this.getY() + this.getBbHeight() / 2.0F + 0.3;
               double d2 = this.getZ() + vecZ * vec;
               float yaw = (float)Math.toRadians(-this.yBodyRot - 90.0F);
               this.level().addParticle(new ScyllaSwingParticleOptions(this.getScale() * 0.95F, yaw, 0.0F), d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }
      }

      if (this.getAttackState() == 5 && this.attackTicks == 17) {
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 20);
         this.playSound((SoundEvent)ModSounds.HEAVY_SMASH.get(), 0.8F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         this.AreaAttack(5.0F, 6.75F, 65.0F, 1.1F, 120, false);
         if (!this.level().isClientSide) {
            this.spawnLightning(this.getX() + vecX * 3.25, this.getZ() + vecZ * 3.25, this.getY() - 2.0, this.getY() + 5.0, (float)theta, -9, 2.0F);
         } else {
            double vec = 1.5;
            double d0 = this.getX() + vecX * vec;
            double d1 = this.getX() + this.getBbHeight() / 2.0F + 0.3;
            double d2 = this.getZ() + vecZ * vec;
            float yaw = (float)Math.toRadians(-this.yBodyRot - 90.0F);
            this.level().addParticle(new ScyllaSwingParticleOptions(this.getScale() * 0.95F, yaw, 0.0F), d0, d1, d2, 0.0, 0.0, 0.0);
         }
      }

      if (this.getAttackState() == 7) {
         if (this.attackTicks < 40 && this.attackTicks >= 12 && this.level().isClientSide) {
            float r = 0.56078434F;
            float g = 0.94509804F;
            float b = 0.84313726F;
            this.level()
               .addParticle(
                  new StormParticleOptions(
                     0.3882353F, 0.7607843F, 0.8784314F, 6.0F + this.random.nextFloat() * 0.25F, 1.5F + this.random.nextFloat() * 0.45F, this.getId()
                  ),
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  0.0,
                  0.0,
                  0.0
               );
            this.level()
               .addParticle(
                  new StormParticleOptions(r, g, b, 4.0F + this.random.nextFloat() * 1.2F, 1.0F + this.random.nextFloat() * 0.45F, this.getId()),
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  0.0,
                  0.0,
                  0.0
               );
            this.level()
               .addParticle(
                  new StormParticleOptions(r, g, b, 2.0F + this.random.nextFloat() * 0.7F, 0.35F + this.random.nextFloat() * 0.45F, this.getId()),
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  0.0,
                  0.0,
                  0.0
               );
         }

         if (this.attackTicks == 29 || this.attackTicks == 40 || this.attackTicks == 51) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.1F, 0, 20);
            this.playSound((SoundEvent)ModSounds.STRONGSWING.get(), 2.0F, 1.1F);
            if (this.level().isClientSide) {
               double d0 = this.getX();
               double d1 = this.getY() + this.getBbHeight() / 2.0F + 0.5;
               double d2 = this.getZ();
               float yaw = (float)Math.toRadians(-this.yBodyRot);
               double lookX = -Math.cos(yaw);
               double lookZ = -Math.sin(yaw);
               float pitch = (float)Math.atan2(-5.0, Math.sqrt(lookX * lookX + lookZ * lookZ));
               this.level().addParticle(new ScyllaSwingParticleOptions(this.getScale() * 2.0F, yaw, pitch), d0, d1, d2, 0.0, 0.0, 0.0);
            } else {
               this.SpinDamage(2.0F, 5.5, 3.0F, 1.5, 1.0F, (float)CMCommonConfig.Scylla.SpinHpDamage, 0.0F, 120);
            }
         }

         if (this.attackTicks < 26 && this.attackTicks >= 12) {
            this.Stormknockback(0.5F, 5.5);
         }

         if (this.attackTicks < 53 && this.attackTicks >= 26) {
            this.Stormknockback(0.6F, 5.5);
         }
      }

      if (this.getAttackState() == 8) {
         if (this.attackTicks == 35) {
            this.AreaAttack(3.5F, 3.5F, 120.0F, 1.2F, 200, false);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.15F, 0, 20);
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 2.0F, 0.95F + this.getRandom().nextFloat() * 0.1F);
            this.SummonWave(5, 25.0F, 2.4);
            if (this.level().isClientSide()) {
               double vec = 1.5;
               double d0 = this.getX() + vecX * vec;
               double d1 = this.getX() + this.getBbHeight() / 2.0F + 0.3;
               double d2 = this.getZ() + vecZ * vec;
               float yaw = (float)Math.toRadians(-this.yBodyRot - 90.0F);
               this.level().addParticle(new ScyllaSwingParticleOptions(this.getScale() * 0.95F, yaw, 0.0F), d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }

         if (this.attackTicks == 41 && !this.level().isClientSide()) {
            this.SummonWave(4, 25.0F, 2.4);
         }
      }

      if (this.getAttackState() == 9) {
         if (this.attackTicks < 115 && this.attackTicks > 55) {
            this.CircleLighning(0.2F, 0.2F, (this.random.nextFloat() - 0.5F) * 12.0F, 9.0, 3, 1);
            this.Stormknockback(0.7F, 5.5);
            if (this.level().isClientSide) {
               float r = 0.56078434F;
               float g = 0.94509804F;
               float b = 0.84313726F;
               this.level()
                  .addParticle(
                     new StormParticleOptions(
                        0.3882353F, 0.7607843F, 0.8784314F, 6.0F + this.random.nextFloat() * 0.25F, 1.5F + this.random.nextFloat() * 0.45F, this.getId()
                     ),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
               this.level()
                  .addParticle(
                     new StormParticleOptions(r, g, b, 4.0F + this.random.nextFloat() * 1.2F, 1.0F + this.random.nextFloat() * 0.45F, this.getId()),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
               this.level()
                  .addParticle(
                     new StormParticleOptions(r, g, b, 2.0F + this.random.nextFloat() * 0.7F, 0.35F + this.random.nextFloat() * 0.45F, this.getId()),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (this.attackTicks < 140 && this.attackTicks > 55) {
            this.Nimbo(0.2F, 0.2F, 5.0F, 9.5, 5, 2);
         }

         if (this.attackTicks == 55) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.02F, 20, 10);
         }

         if (this.attackTicks == 75) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.03F, 20, 10);
         }

         if (this.attackTicks == 95) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 25.0F, 0.04F, 20, 10);
         }

         if (this.attackTicks == 114) {
            this.playSound((SoundEvent)ModSounds.SUPER_LIGHTNING.get(), 0.4F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            if (this.level().isClientSide) {
               double d0 = this.getX() + vecX * 0.2F + f * 0.2F;
               double d1 = this.getY() + 9.0;
               double d2 = this.getZ() + vecZ * 0.2F + f1 * 0.2F;
               this.level().addParticle((ParticleOptions)ModParticle.LIGHTNING_EXPLODE.get(), d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }
      }

      if (this.getAttackState() == 10) {
         if (this.attackTicks < 24 && this.attackTicks > 7) {
            this.PullLighning(7.0, -0.5, -0.5F, this.getBbHeight());
         }

         if (this.attackTicks == 27) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.05F, 0, 10);
         }
      }

      if (this.getAttackState() == 11) {
         if (this.attackTicks < 17 && this.level().isClientSide) {
            float r = 0.56078434F;
            float g = 0.94509804F;
            float b = 0.84313726F;
            double vec = -0.5;
            float math = -0.5F;
            double d0 = this.getX() + vecX * vec + f * math;
            double d1 = this.getY() + this.getBbHeight();
            double d2 = this.getZ() + vecZ * vec + f1 * math;
            this.level()
               .addParticle(
                  new GatheringWaterParticleOptions(r, g, b),
                  d0 + (this.random.nextFloat() - 0.5F) * 7.0F,
                  d1 + this.random.nextFloat() - 0.25,
                  d2 + (this.random.nextFloat() - 0.5F) * 7.0F,
                  d0,
                  d1,
                  d2
               );
         }

         if (this.attackTicks == 27) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.05F, 0, 10);
         }
      }

      if (this.getAttackState() == 12) {
         if (this.attackTicks < 67 && this.attackTicks > 42) {
            this.PullLighning(7.0, 0.45, -0.5F, this.getBbHeight() - 0.2);
         }

         if (this.attackTicks == 42) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.01F, 25, 10);
         }

         if (this.attackTicks == 67) {
            if (!this.level().isClientSide) {
               double d0 = this.getX() + vecX * 0.5 + f * -0.5;
               double d1 = this.getY() + this.getBbHeight() / 2.0F;
               double d2 = this.getZ() + vecZ * 0.5 + f1 * -0.5;
               this.level()
                  .addFreshEntity(
                     new Lightning_Storm_Entity(
                        this.level(),
                        d0,
                        d1,
                        d2,
                        (float)theta,
                        -9,
                        (float)CMCommonConfig.Scylla.LightningStormDamage,
                        (float)CMCommonConfig.Scylla.StormHpDamage,
                        this,
                        2.0F
                     )
                  );
            }

            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 20);
            if (CMCommonConfig.Scylla.WeatherChange
               && this.level().getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)
               && this.level() instanceof ServerLevel serverLevel) {
               serverLevel.setWeatherParameters(24000, 0, false, false);
            }
         }

         if (this.attackTicks > 100 && this.level().isClientSide) {
            for (int i = 0; i < 4; i++) {
               double d0 = Math.cos(Math.toRadians(this.getYRot() - this.random.nextFloat() * 80.0F)) * 0.3;
               double d1 = Math.sin(Math.toRadians(this.getYRot() - this.random.nextFloat() * 80.0F)) * 0.3;
               this.level()
                  .addAlwaysVisibleParticle(
                     ParticleTypes.POOF,
                     this.getRandomX(0.5),
                     this.getRandomY() - this.getBbHeight() / 2.0F,
                     this.getRandomZ(0.5),
                     d0,
                     this.random.nextGaussian() * 0.035,
                     d1
                  );
            }
         }
      }

      if (this.getAttackState() == 15 && this.attackTicks == 25) {
         this.setFlying(true);
         this.setDeltaMovement(Vec3.ZERO);
      }

      if (this.getAttackState() == 17) {
         if (this.attackTicks == 1) {
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 2.0F, 0.75F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 25.0F, 0.06F, 15, 10);
            if (this.level().isClientSide) {
               this.level().addParticle((ParticleOptions)ModParticle.LIGHTNING_EXPLODE.get(), this.getX(), this.getY() + 0.03, this.getZ(), 0.0, 0.0, 0.0);

               for (int i1 = 0; i1 < 80 + this.random.nextInt(12); i1++) {
                  double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
                  double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
                  double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
                  float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
                  double extraX = 1.0F * Mth.sin((float)(Math.PI + angle));
                  double extraY = 0.3F;
                  double extraZ = 1.0F * Mth.cos(angle);
                  int hitX = Mth.floor(this.getX() + extraX);
                  int hitY = Mth.floor(this.getY());
                  int hitZ = Mth.floor(this.getZ() + extraZ);
                  BlockPos hit = new BlockPos(hitX, hitY, hitZ);
                  BlockState block = this.level().getBlockState(hit.below());
                  if (block.getRenderShape() != RenderShape.INVISIBLE) {
                     this.level()
                        .addParticle(
                           new BlockParticleOption(ParticleTypes.DUST_PILLAR, block),
                           this.getX() + extraX,
                           this.getY() + extraY,
                           this.getZ() + extraZ,
                           DeltaMovementX,
                           DeltaMovementY,
                           DeltaMovementZ
                        );
                  }
               }
            } else {
               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.5))) {
                  if (!this.isAlliedTo(entity) && entity != this) {
                     entity.hurt(
                        CMDamageTypes.causeLightningMobDamage(this),
                        (float)(
                           this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                              + Math.min(this.getAttributeValue(Attributes.ATTACK_DAMAGE), entity.getMaxHealth() * (float)CMCommonConfig.Scylla.HpDamage)
                        )
                     );
                  }
               }

               int rune = 7;
               int time = 4;

               for (int i = 0; i < rune; i++) {
                  float throwAngle = i * (float) Math.PI / (rune / 2.0F);

                  for (int k = 0; k < 5; k++) {
                     double d2 = 1.75 * (k + 1);
                     int d3 = time * (k + 1) - 9;
                     this.spawnLightning(
                        this.getX() + Mth.cos(throwAngle) * 1.25 * d2,
                        this.getZ() + Mth.sin(throwAngle) * 1.25 * d2,
                        this.getY(),
                        this.getY() + 2.0,
                        throwAngle,
                        d3,
                        2.0F
                     );
                  }
               }

               int sparkAmount = 2;
               if (this.isPhase() > 0 && this.isPhase() < 2) {
                  sparkAmount = 4;
               } else if (this.isPhase() > 1) {
                  sparkAmount = 6;
               }

               for (int i = 0; i < sparkAmount; i++) {
                  Spark_Entity peq = new Spark_Entity(this.level(), this);
                  peq.setDamage((float)CMCommonConfig.Scylla.LightningStormDamage);
                  peq.setAreaDamage((float)CMCommonConfig.Scylla.LightningAreaDamage);
                  peq.setAreaRadius(1.0F);
                  peq.setHpDamage((float)CMCommonConfig.Scylla.StormHpDamage);
                  peq.shoot((this.random.nextFloat() - 0.5) * 0.5, this.random.nextFloat() * 0.4F + 0.01F, (this.random.nextFloat() - 0.5) * 0.5, 1.0F, 1.0F);
                  peq.setPos(this.getX(), this.getY() + 0.03, this.getZ());
                  this.level().addFreshEntity(peq);
               }
            }
         }

         if (this.attackTicks >= 1 && this.attackTicks < 12 && this.level().isClientSide) {
            double d0 = this.getX();
            double d1 = this.getY() + 0.03;
            double d2 = this.getZ();

            for (int i = 0; i < 2; i++) {
               this.level()
                  .addParticle(
                     new CircleLightningParticleOptions(0.1F, 143, 241, 215),
                     d0,
                     d1,
                     d2,
                     d0 + (this.random.nextFloat() - 0.5F) * 6.0F,
                     d1 + this.random.nextFloat() - 0.25,
                     d2 + (this.random.nextFloat() - 0.5F) * 6.0F
                  );
            }
         }
      }

      if (this.getAttackState() == 18) {
         double d0 = this.getX() + vecX * 1.1 + f * 0.4;
         double d1 = this.getY() + 0.1;
         double d2 = this.getZ() + vecZ * 1.1 + f1 * 0.4;
         if (this.attackTicks >= 24 && this.attackTicks < 54 && this.level().isClientSide) {
            for (int i = 0; i < 2; i++) {
               this.level()
                  .addParticle(
                     new CircleLightningParticleOptions(0.1F, 143, 241, 215),
                     d0,
                     d1,
                     d2,
                     d0 + (this.random.nextFloat() - 0.5F) * 12.0F,
                     d1,
                     d2 + (this.random.nextFloat() - 0.5F) * 12.0F
                  );
            }
         }

         if (this.attackTicks == 25) {
            this.playSound((SoundEvent)ModSounds.HEAVY_SMASH.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            this.playSound((SoundEvent)ModSounds.SUPER_LIGHTNING.get(), 0.2F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.05F, 25, 10);
            if (this.level().isClientSide) {
               float vec = 1.0F;
               this.level()
                  .addParticle(
                     new RingParticleOptions(0.0F, (float) (Math.PI / 2), 30, 143, 241, 215, 1.0F, 65.0F, false, 0),
                     this.getX() + vec * vecX,
                     this.getY() + 0.02F,
                     this.getZ() + vec * vecZ,
                     0.0,
                     0.0,
                     0.0
                  );
               this.level().addParticle((ParticleOptions)ModParticle.LIGHTNING_EXPLODE.get(), d0, d1, d2, 0.0, 0.0, 0.0);
            } else {
               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0))) {
                  if (!this.isAlliedTo(entity) && !(entity instanceof Scylla_Entity) && entity != this) {
                     entity.hurt(
                        CMDamageTypes.causeLightningMobDamage(this),
                        (float)(
                           this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                              + Math.min(this.getAttributeValue(Attributes.ATTACK_DAMAGE), entity.getMaxHealth() * (float)CMCommonConfig.Scylla.HpDamage)
                        )
                     );
                  }
               }
            }
         }

         if (this.attackTicks == 53) {
            this.playSound((SoundEvent)ModSounds.HEAVY_SMASH.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            this.playSound((SoundEvent)ModSounds.SUPER_LIGHTNING.get(), 0.2F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.05F, 25, 10);
            if (this.level().isClientSide) {
               float vec = 1.0F;
               this.level()
                  .addParticle(
                     new RingParticleOptions(0.0F, (float) (Math.PI / 2), 30, 143, 241, 215, 1.0F, 65.0F, false, 0),
                     this.getX() + vec * vecX,
                     this.getY() + 0.02F,
                     this.getZ() + vec * vecZ,
                     0.0,
                     0.0,
                     0.0
                  );
               this.level().addParticle((ParticleOptions)ModParticle.LIGHTNING_EXPLODE.get(), d0, d1, d2, 0.0, 0.0, 0.0);
            } else {
               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0))) {
                  if (!this.isAlliedTo(entity) && !(entity instanceof Scylla_Entity) && entity != this) {
                     entity.hurt(
                        CMDamageTypes.causeLightningMobDamage(this),
                        (float)(
                           this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                              + Math.min(this.getAttributeValue(Attributes.ATTACK_DAMAGE), entity.getMaxHealth() * (float)CMCommonConfig.Scylla.HpDamage)
                        )
                     );
                  }
               }
            }
         }
      }

      if (this.getAttackState() == 20) {
         double d0 = this.getX() + vecX * 6.0;
         double d1 = this.getY() + 0.1;
         double d2 = this.getZ() + vecZ * 6.0;
         if (this.attackTicks == 21) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 35.0F, 0.1F, 0, 50);
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 2.0F, 0.75F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(6.75F, 6.75F, 40.0F, 1.2F, 150, false);
            if (this.level().isClientSide) {
               this.level()
                  .addParticle(new RingParticleOptions(0.0F, (float) (Math.PI / 2), 30, 143, 241, 215, 1.0F, 30.0F, false, 0), d0, d1, d2, 0.0, 0.0, 0.0);
               this.level().addParticle((ParticleOptions)ModParticle.LIGHTNING_EXPLODE.get(), d0, d1, d2, 0.0, 0.0, 0.0);

               for (int i1 = 0; i1 < 40 + this.random.nextInt(8); i1++) {
                  double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
                  double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
                  double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
                  float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
                  double extraX = 1.5F * Mth.sin((float)(Math.PI + angle));
                  double extraY = 0.3F;
                  double extraZ = 1.5F * Mth.cos(angle);
                  double hitX = d0 + extraX;
                  double hitY = this.getY();
                  double hitZ = d2 + extraZ;
                  BlockPos hit = BlockPos.containing(hitX, hitY, hitZ);
                  BlockState block = this.level().getBlockState(hit.below());
                  BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.DUST_PILLAR, block);
                  this.level().addParticle(blockparticleoption, d0 + extraX, this.getY() + extraY, d2 + extraZ, DeltaMovementX, DeltaMovementY, DeltaMovementZ);
               }
            } else {
               for (int i = 0; i < 16; i++) {
                  Spark_Entity peq = new Spark_Entity(this.level(), this);
                  peq.setDamage((float)CMCommonConfig.Scylla.LightningStormDamage);
                  peq.setAreaDamage((float)CMCommonConfig.Scylla.LightningAreaDamage);
                  peq.setAreaRadius(2.0F);
                  peq.setHpDamage((float)CMCommonConfig.Scylla.StormHpDamage);
                  peq.shoot((this.random.nextFloat() - 0.5) * 0.5, this.random.nextFloat() * 0.4F + 0.01F, (this.random.nextFloat() - 0.5) * 0.5, 1.0F, 1.0F);
                  peq.setPos(d0, d1, d2);
                  this.level().addFreshEntity(peq);
               }
            }
         }

         if (this.attackTicks >= 21 && this.attackTicks < 30 && this.level().isClientSide) {
            for (int i = 0; i < 5; i++) {
               this.level()
                  .addParticle(
                     new CircleLightningParticleOptions(0.1F, 143, 241, 215),
                     d0,
                     d1,
                     d2,
                     d0 + (this.random.nextFloat() - 0.5F) * 12.0F,
                     d1,
                     d2 + (this.random.nextFloat() - 0.5F) * 12.0F
                  );
            }
         }
      }

      if (this.getAttackState() == 21) {
         if (this.level().isClientSide) {
            if (this.attackTicks > 1 && this.attackTicks < 15) {
               float r = 0.56078434F;
               float g = 0.94509804F;
               float b = 0.84313726F;
               double vec = 1.1;
               double math = 0.0;
               double d0 = this.getX() + vecX * vec + f * math;
               double d1 = this.getY() + this.getBbHeight() * 0.5;
               double d2 = this.getZ() + vecZ * vec + f1 * math;

               for (int i = 0; i < 2; i++) {
                  this.level()
                     .addParticle(
                        new GatheringWaterParticleOptions(r, g, b),
                        d0 + (this.random.nextFloat() - 0.5F) * 7.0F,
                        d1 + this.random.nextFloat() - 0.25,
                        d2 + (this.random.nextFloat() - 0.5F) * 7.0F,
                        d0,
                        d1,
                        d2
                     );
               }

               this.level()
                  .addParticle(
                     new StormParticleOptions(
                        0.3882353F, 0.7607843F, 0.8784314F, 4.0F + this.random.nextFloat() * 0.25F, 0.75F + this.random.nextFloat() * 0.45F, this.getId()
                     ),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
               this.level()
                  .addParticle(
                     new StormParticleOptions(r, g, b, 2.0F + this.random.nextFloat() * 0.7F, 0.25F + this.random.nextFloat() * 0.45F, this.getId()),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
            }

            if (this.attackTicks < 30 && this.attackTicks >= 15 && this.level().isClientSide) {
               float r = 0.56078434F;
               float g = 0.94509804F;
               float b = 0.84313726F;
               this.level()
                  .addParticle(
                     new StormParticleOptions(
                        0.3882353F, 0.7607843F, 0.8784314F, 8.0F + this.random.nextFloat() * 0.25F, 0.75F + this.random.nextFloat() * 0.45F, this.getId()
                     ),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
               this.level()
                  .addParticle(
                     new StormParticleOptions(r, g, b, 6.0F + this.random.nextFloat() * 1.2F, 0.25F + this.random.nextFloat() * 0.45F, this.getId()),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (this.attackTicks < 15 && this.attackTicks > 1) {
            this.Stormknockback(0.5F, 4.5);
         }

         if (this.attackTicks < 30 && this.attackTicks >= 15) {
            this.Stormknockback(0.9F, 8.5);
         }

         if (this.attackTicks == 30
            && CMCommonConfig.Scylla.WeatherChange
            && this.level().getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)
            && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.setWeatherParameters(0, 24000, true, false);
         }
      }

      if (this.getAttackState() == 22) {
         if (this.level().isClientSide) {
            float vec = -0.6F;
            float math = 0.5F;
            double d0 = this.getX() + vecX * vec + f * math;
            double d1 = this.getY() + this.getBbHeight() * 2.0F;
            double d2 = this.getZ() + vecZ * vec + f1 * math;
            if (this.attackTicks == 48) {
               this.level().addParticle(new LightningStormParticleOptions(3.0F), d0, d1 + 2.0, d2, 0.0, 0.0, 0.0);
            }

            if (this.attackTicks < 48 && this.attackTicks > 39) {
               this.CircleLighning(vec, math, (this.random.nextFloat() - 0.5F) * 7.0F, this.getBbHeight() * 2.0F - 1.0F, 2, 0);
            }

            if (this.attackTicks > 1 && this.attackTicks < 15) {
               float r = 0.56078434F;
               float g = 0.94509804F;
               float b = 0.84313726F;
               double vec1 = 1.1;
               double d0w = this.getX() + vecX * vec1;
               double d1w = this.getY() + this.getBbHeight() * 0.5;
               double d2w = this.getZ() + vecZ * vec1;

               for (int i = 0; i < 2; i++) {
                  this.level()
                     .addParticle(
                        new GatheringWaterParticleOptions(r, g, b),
                        d0w + (this.random.nextFloat() - 0.5F) * 7.0F,
                        d1w + this.random.nextFloat() - 0.25,
                        d2w + (this.random.nextFloat() - 0.5F) * 7.0F,
                        d0w,
                        d1w,
                        d2w
                     );
               }

               this.level()
                  .addParticle(
                     new StormParticleOptions(
                        0.3882353F, 0.7607843F, 0.8784314F, 4.0F + this.random.nextFloat() * 0.25F, 0.75F + this.random.nextFloat() * 0.45F, this.getId()
                     ),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
               this.level()
                  .addParticle(
                     new StormParticleOptions(r, g, b, 2.0F + this.random.nextFloat() * 0.7F, 0.25F + this.random.nextFloat() * 0.45F, this.getId()),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
            }

            if (this.attackTicks < 30 && this.attackTicks >= 15 && this.level().isClientSide) {
               float r = 0.56078434F;
               float g = 0.94509804F;
               float b = 0.84313726F;
               this.level()
                  .addParticle(
                     new StormParticleOptions(
                        0.3882353F, 0.7607843F, 0.8784314F, 8.0F + this.random.nextFloat() * 0.25F, 0.75F + this.random.nextFloat() * 0.45F, this.getId()
                     ),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
               this.level()
                  .addParticle(
                     new StormParticleOptions(r, g, b, 6.0F + this.random.nextFloat() * 1.2F, 0.25F + this.random.nextFloat() * 0.45F, this.getId()),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (this.attackTicks < 15 && this.attackTicks > 1) {
            this.Stormknockback(0.5F, 4.5);
         }

         if (this.attackTicks < 30 && this.attackTicks >= 15) {
            this.Stormknockback(0.9F, 8.5);
         }

         if (this.attackTicks == 30
            && CMCommonConfig.Scylla.WeatherChange
            && this.level().getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)
            && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.setWeatherParameters(0, 24000, true, true);
         }
      }

      if (this.getAttackState() == 24) {
         if (this.attackTicks == 1 && !this.level().isClientSide) {
            double d0 = this.getX() + vecX * 1.2 + f * 0.65;
            double d1 = this.getY() + 0.03;
            double d2 = this.getZ() + vecZ * 1.2 + f1 * 0.65;
            this.level()
               .addFreshEntity(
                  new Lightning_Storm_Entity(
                     this.level(),
                     d0,
                     d1,
                     d2,
                     (float)theta,
                     -9,
                     (float)CMCommonConfig.Scylla.LightningStormDamage,
                     (float)CMCommonConfig.Scylla.StormHpDamage,
                     this,
                     3.0F
                  )
               );
         }

         if (this.attackTicks == 11) {
            for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0))) {
               if (!this.isAlliedTo(entity) && !(entity instanceof Scylla_Entity) && entity != this) {
                  this.launch(entity, 2.5, 0.3);
               }
            }

            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 2.0F, 0.75F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 25.0F, 0.1F, 25, 10);
            if (!this.level().isClientSide) {
               int rune = 7;
               int time = 4;

               for (int i = 0; i < rune; i++) {
                  float throwAngle = i * (float) Math.PI / (rune / 2);

                  for (int k = 0; k < 5; k++) {
                     double d2 = 1.75 * (k + 1);
                     int d3 = time * (k + 1) - 9;
                     this.spawnLightning(
                        this.getX() + Mth.cos(throwAngle) * 1.25 * d2,
                        this.getZ() + Mth.sin(throwAngle) * 1.25 * d2,
                        this.getY(),
                        this.getY() + 2.0,
                        throwAngle,
                        d3,
                        2.0F
                     );
                  }
               }
            }
         }

         if (this.attackTicks >= 11 && this.attackTicks < 22 && this.level().isClientSide) {
            double d0 = this.getX();
            double d1 = this.getY() + 0.03;
            double d2 = this.getZ();

            for (int i = 0; i < 2; i++) {
               this.level()
                  .addParticle(
                     new CircleLightningParticleOptions(0.1F, 143, 241, 215),
                     d0,
                     d1,
                     d2,
                     d0 + (this.random.nextFloat() - 0.5F) * 6.0F,
                     d1 + this.random.nextFloat() - 0.25,
                     d2 + (this.random.nextFloat() - 0.5F) * 6.0F
                  );
            }

            for (int i1 = 0; i1 < 20 + this.random.nextInt(8); i1++) {
               double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
               double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
               double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
               float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
               double extraX = 3.0F * Mth.sin((float)(Math.PI + angle));
               double extraY = 0.3F;
               double extraZ = 3.0F * Mth.cos(angle);
               double hitX = d0 + extraX;
               double hitY = this.getY();
               double hitZ = d2 + extraZ;
               BlockPos hit = BlockPos.containing(hitX, hitY, hitZ);
               BlockState block = this.level().getBlockState(hit.below());
               BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.DUST_PILLAR, block);
               this.level().addParticle(blockparticleoption, d0 + extraX, this.getY() + extraY, d2 + extraZ, DeltaMovementX, DeltaMovementY, DeltaMovementZ);
            }
         }
      }

      if (this.getAttackState() == 25) {
         double d0 = this.getX() + vecX * 6.0;
         double d1 = this.getY() + 0.1;
         double d2 = this.getZ() + vecZ * 6.0;
         if (this.attackTicks == 21) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 35.0F, 0.3F, 0, 50);
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 2.0F, 0.75F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(6.75F, 6.75F, 40.0F, 1.2F, 150, false);
            if (this.level().isClientSide) {
               this.level().addParticle((ParticleOptions)ModParticle.LIGHTNING_EXPLODE.get(), d0, d1, d2, 0.0, 0.0, 0.0);
               this.level()
                  .addParticle(new RingParticleOptions(0.0F, (float) (Math.PI / 2), 30, 143, 241, 215, 1.0F, 30.0F, false, 0), d0, d1, d2, 0.0, 0.0, 0.0);

               for (int i1 = 0; i1 < 40 + this.random.nextInt(8); i1++) {
                  double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
                  double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
                  double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
                  float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
                  double extraX = 1.5F * Mth.sin((float)(Math.PI + angle));
                  double extraY = 0.3F;
                  double extraZ = 1.5F * Mth.cos(angle);
                  double hitX = d0 + extraX;
                  double hitY = this.getY();
                  double hitZ = d2 + extraZ;
                  BlockPos hit = BlockPos.containing(hitX, hitY, hitZ);
                  BlockState block = this.level().getBlockState(hit.below());
                  BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.DUST_PILLAR, block);
                  this.level().addParticle(blockparticleoption, d0 + extraX, this.getY() + extraY, d2 + extraZ, DeltaMovementX, DeltaMovementY, DeltaMovementZ);
               }
            } else {
               for (int i = 0; i < 16; i++) {
                  Spark_Entity peq = new Spark_Entity(this.level(), this);
                  peq.setDamage((float)CMCommonConfig.Scylla.LightningStormDamage);
                  peq.setAreaDamage((float)CMCommonConfig.Scylla.LightningAreaDamage);
                  peq.setHpDamage((float)CMCommonConfig.Scylla.StormHpDamage);
                  peq.setAreaRadius(2.0F);
                  peq.shoot((this.random.nextFloat() - 0.5) * 0.5, this.random.nextFloat() * 0.4F + 0.01F, (this.random.nextFloat() - 0.5) * 0.5, 1.0F, 1.0F);
                  peq.setPos(d0, d1, d2);
                  this.level().addFreshEntity(peq);
               }
            }
         }

         if (this.attackTicks >= 21 && this.attackTicks < 30 && this.level().isClientSide) {
            for (int i = 0; i < 5; i++) {
               this.level()
                  .addParticle(
                     new CircleLightningParticleOptions(0.1F, 143, 241, 215),
                     d0,
                     d1,
                     d2,
                     d0 + (this.random.nextFloat() - 0.5F) * 12.0F,
                     d1,
                     d2 + (this.random.nextFloat() - 0.5F) * 12.0F
                  );
            }
         }

         if (this.attackTicks == 104) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 35.0F, 0.3F, 0, 50);
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 2.0F, 0.75F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(6.75F, 6.75F, 40.0F, 1.2F, 150, false);
            if (this.level().isClientSide) {
               this.level()
                  .addParticle(new RingParticleOptions(0.0F, (float) (Math.PI / 2), 30, 143, 241, 215, 1.0F, 30.0F, false, 0), d0, d1, d2, 0.0, 0.0, 0.0);
               this.level().addParticle((ParticleOptions)ModParticle.LIGHTNING_EXPLODE.get(), d0, d1, d2, 0.0, 0.0, 0.0);

               for (int i1 = 0; i1 < 40 + this.random.nextInt(8); i1++) {
                  double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
                  double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
                  double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
                  float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
                  double extraX = 1.5F * Mth.sin((float)(Math.PI + angle));
                  double extraY = 0.3F;
                  double extraZ = 1.5F * Mth.cos(angle);
                  double hitX = d0 + extraX;
                  double hitY = this.getY();
                  double hitZ = d2 + extraZ;
                  BlockPos hit = BlockPos.containing(hitX, hitY, hitZ);
                  BlockState block = this.level().getBlockState(hit.below());
                  BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.DUST_PILLAR, block);
                  this.level().addParticle(blockparticleoption, d0 + extraX, this.getY() + extraY, d2 + extraZ, DeltaMovementX, DeltaMovementY, DeltaMovementZ);
               }
            } else {
               for (int i = 0; i < 16; i++) {
                  Spark_Entity peq = new Spark_Entity(this.level(), this);
                  peq.setDamage((float)CMCommonConfig.Scylla.LightningStormDamage);
                  peq.setAreaDamage((float)CMCommonConfig.Scylla.LightningAreaDamage);
                  peq.setHpDamage((float)CMCommonConfig.Scylla.StormHpDamage);
                  peq.setAreaRadius(2.0F);
                  peq.shoot((this.random.nextFloat() - 0.5) * 0.5, this.random.nextFloat() * 0.4F + 0.01F, (this.random.nextFloat() - 0.5) * 0.5, 1.0F, 1.0F);
                  peq.setPos(d0, d1, d2);
                  this.level().addFreshEntity(peq);
               }
            }
         }

         if (this.attackTicks >= 104 && this.attackTicks < 113 && this.level().isClientSide) {
            for (int i = 0; i < 5; i++) {
               this.level()
                  .addParticle(
                     new CircleLightningParticleOptions(0.1F, 143, 241, 215),
                     d0,
                     d1,
                     d2,
                     d0 + (this.random.nextFloat() - 0.5F) * 12.0F,
                     d1,
                     d2 + (this.random.nextFloat() - 0.5F) * 12.0F
                  );
            }
         }

         if (this.attackTicks < 58 && this.attackTicks >= 41 && this.level().isClientSide) {
            float r = 0.56078434F;
            float g = 0.94509804F;
            float b = 0.84313726F;
            double vec = -0.5;
            float math = -0.5F;
            double p0 = this.getX() + vecX * vec + f * math;
            double p1 = this.getY() + this.getBbHeight();
            double p2 = this.getZ() + vecZ * vec + f1 * math;
            this.level()
               .addParticle(
                  new GatheringWaterParticleOptions(r, g, b),
                  p0 + (this.random.nextFloat() - 0.5F) * 7.0F,
                  p1 + this.random.nextFloat() - 0.25,
                  p2 + (this.random.nextFloat() - 0.5F) * 7.0F,
                  p0,
                  p1,
                  p2
               );
         }
      }

      if (this.getAttackState() == 26) {
         if (this.level().isClientSide) {
            if (this.attackTicks < 20 && this.attackTicks > 1) {
               for (int i = 0; i < 2; i++) {
                  float r = 0.36862746F;
                  float g = 0.5882353F;
                  float b = 0.8862745F;
                  double p0 = this.getX();
                  double p1 = this.getY() + 0.1;
                  double p2 = this.getZ();
                  this.level()
                     .addParticle(
                        new GatheringWaterParticleOptions(r, g, b),
                        p0 + (this.random.nextFloat() - 0.5F) * 12.0F,
                        p1 + (this.random.nextFloat() - 0.5F) * 2.0F,
                        p2 + (this.random.nextFloat() - 0.5F) * 12.0F,
                        p0,
                        p1,
                        p2
                     );
               }
            }

            if (this.attackTicks == 30) {
               this.level()
                  .addParticle(
                     new RingParticleOptions(0.0F, (float) (Math.PI / 2), 30, 94, 150, 226, 1.0F, 65.0F, false, 0),
                     this.getX(),
                     this.getY() + 0.02F,
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
            }

            if (this.attackTicks < 40 && this.attackTicks > 30) {
               float r = 0.56078434F;
               float g = 0.94509804F;
               float b = 0.84313726F;
               this.level()
                  .addParticle(
                     new StormParticleOptions(
                        0.3882353F, 0.7607843F, 0.8784314F, 6.0F + this.random.nextFloat() * 0.25F, 1.5F + this.random.nextFloat() * 0.45F, this.getId()
                     ),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
               this.level()
                  .addParticle(
                     new StormParticleOptions(r, g, b, 4.0F + this.random.nextFloat() * 1.2F, 1.0F + this.random.nextFloat() * 0.45F, this.getId()),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
               this.level()
                  .addParticle(
                     new StormParticleOptions(r, g, b, 2.0F + this.random.nextFloat() * 0.7F, 0.35F + this.random.nextFloat() * 0.45F, this.getId()),
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (this.attackTicks == 1) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 35.0F, 0.1F, 0, 120);
         }

         if (this.attackTicks == 30) {
            this.playSound((SoundEvent)ModSounds.SCYLLA_ROAR.get(), 0.6F, 1.0F);
            this.Roarparticle(-0.4F, 0.0F, 2.4F, 20, 99, 194, 224, 0.4F, 0.4F, 0.5F, 2.5F);
         }

         if (this.attackTicks == 33 || this.attackTicks == 36 || this.attackTicks == 39) {
            this.Roarparticle(-0.4F, 0.0F, 2.4F, 20, 99, 194, 224, 0.4F, 0.4F, 0.5F, 2.5F);
         }
      }

      if (this.getAttackState() == 27 && this.attackTicks == 11) {
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 20);
         this.playSound((SoundEvent)ModSounds.HEAVY_SMASH.get(), 0.8F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         this.AreaAttack(4.5F, 6.75F, 65.0F, 1.1F, 120, false);
         if (!this.level().isClientSide) {
            this.spawnLightning(this.getX() + vecX * 3.25, this.getZ() + vecZ * 3.25, this.getY() - 2.0, this.getY() + 5.0, (float)theta, -9, 2.0F);
         } else {
            double vec = 1.5;
            double d0 = this.getX() + vecX * vec;
            double d1 = this.getX() + this.getBbHeight() / 2.0F + 0.3;
            double d2 = this.getZ() + vecZ * vec;
            float yaw = (float)Math.toRadians(-this.yBodyRot - 90.0F);
            this.level().addParticle(new ScyllaSwingParticleOptions(this.getScale() * 0.95F, yaw, 0.0F), d0, d1, d2, 0.0, 0.0, 0.0);
         }
      }
   }

   private void SwingParticle(double x, double y, double z, double vec, boolean reverse, float scale) {
      if (this.level().isClientSide) {
         double d0 = this.getX() + x * vec;
         double d1 = this.getY() + this.getBbHeight() / 2.0F + 0.4;
         double d2 = this.getZ() + z * vec;
         float yaw = (float)Math.toRadians(-this.yBodyRot + (reverse ? 180 : 0));
         double lookX = -Math.cos(yaw);
         double lookZ = -Math.sin(yaw);
         float pitch = (reverse ? -1 : 1) * (float)Math.atan2(y, Math.sqrt(lookX * lookX + lookZ * lookZ));
         this.level().addParticle(new ScyllaSwingParticleOptions(this.getScale() * scale, yaw, pitch), d0, d1, d2, 0.0, 0.0, 0.0);
      }
   }

   private void Roarparticle(float vec, float math, float y, int duration, int r, int g, int b, float a, float start, float inc, float end) {
      if (this.level().isClientSide) {
         float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);
         this.level()
            .addParticle(
               new RoarParticleOptions(duration, r, g, b, a, start, inc, end),
               this.getX() + vec * vecX + f * math,
               this.getY() + y,
               this.getZ() + vec * vecZ + f1 * math,
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void Whip(double startdistance, double range, double inflateXZ, double inflateY, int number, float anglestep, float damage, float hpdamage) {
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = startdistance * Math.cos(theta);
      double vecZ = startdistance * Math.sin(theta);
      double px = this.getX() + vecX;
      double pz = this.getZ() + vecZ;
      double firstAngleOffset = (number - 1) / 2.0 * anglestep;
      DamageSource damagesource = CMDamageTypes.causeLightningMobDamage(this);

      for (int i = 0; i < number; i++) {
         double angle = this.yBodyRot + 90.0F - firstAngleOffset + i * anglestep;
         float f1 = (float)Math.cos(Math.toRadians(angle));
         float f2 = (float)Math.sin(Math.toRadians(angle));
         AABB attackRange = new AABB(px - inflateXZ, this.getY() - inflateY, pz - inflateXZ, px + inflateXZ, this.getY() + inflateY, pz + inflateXZ)
            .expandTowards(f1 * range * 0.75, 0.0, f2 * range * 0.75);

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, attackRange)) {
            if (!this.isAlliedTo(entity) && entity != this) {
               entity.hurt(
                  damagesource,
                  (float)(
                     this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage
                        + Math.min((float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage), entity.getMaxHealth() * hpdamage)
                  )
               );
            }
         }

         if (this.level().isClientSide) {
            double d0 = px + f1 * range;
            double d1 = this.getY() + 0.3;
            double d2 = pz + f2 * range;
            this.level()
               .addParticle(
                  new CircleLightningParticleOptions(0.1F, 143, 241, 215),
                  px,
                  this.getY(),
                  pz,
                  d0 + (this.random.nextFloat() - 0.5F) * 2.0F,
                  d1,
                  d2 + (this.random.nextFloat() - 0.5F) * 2.0F
               );
         }
      }
   }

   private void LightningAttack(double startdistance, double range, int number, float anglestep) {
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = startdistance * Math.cos(theta);
      double vecZ = startdistance * Math.sin(theta);
      double px = this.getX() + vecX;
      double pz = this.getZ() + vecZ;
      float firstAngleOffset = (number - 1) / 2.0F * anglestep;

      for (int i = 0; i < number; i++) {
         float angle = this.yBodyRot + 90.0F - firstAngleOffset + i * anglestep;
         float f1 = (float)Math.cos(Math.toRadians(angle));
         float f2 = (float)Math.sin(Math.toRadians(angle));
         if (!this.level().isClientSide) {
            Lightning_Area_Effect_Entity areaeffectcloud = new Lightning_Area_Effect_Entity(this.level(), px + f1 * range, this.getY(), pz + f2 * range);
            areaeffectcloud.setRadius(3.5F);
            areaeffectcloud.setOwner(this);
            areaeffectcloud.setRadiusOnUse(-1.5F);
            areaeffectcloud.setDamage(5.0F);
            areaeffectcloud.setWaitTime(20);
            areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / areaeffectcloud.getDuration());
            this.spawnLightning(px + f1 * range, pz + f2 * range, this.getY() - 5.0, this.getY() + 3.0, angle, 0, 3.5F);
            this.StrikeWindmillLightning(px + f1 * range, pz + f2 * range, 2, 12, 1.0, 0.7, 0.4, 4);
         }
      }
   }

   private void StrikeWindmillLightning(
      double x, double z, int numberOfBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay
   ) {
      float angleIncrement = (float)((Math.PI * 2) / numberOfBranches);

      for (int branch = 0; branch < numberOfBranches; branch++) {
         float baseAngle = angleIncrement * branch;

         for (int i = 0; i < particlesPerBranch; i++) {
            double currentRadius = initialRadius + i * radiusIncrement;
            float currentAngle = (float)(baseAngle + i * angleIncrement / initialRadius + (float)(i * curveFactor));
            double xOffset = currentRadius * Math.cos(currentAngle);
            double zOffset = currentRadius * Math.sin(currentAngle);
            double spawnX = x + xOffset;
            double spawnZ = z + zOffset;
            int d3 = delay * (i + 1);
            this.spawnLightning(spawnX, spawnZ, this.getY() - 5.0, this.getY() + 3.0, currentAngle, d3, 1.0F);
         }
      }
   }

   private void SpinDamage(float spreadarc, double distance, float mxy, double ab, float damage, float hpdamage, float airborne, int shieldbreakticks) {
      double perpFacing = this.yBodyRot * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      double spread = Math.PI * spreadarc;
      int arcLen = Mth.ceil(distance * spread);
      double minY = this.getY() - 1.0;
      double maxY = this.getY() + mxy;
      DamageSource damagesource = this.damageSources().mobAttack(this);

      for (int i = 0; i < arcLen; i++) {
         double theta = (i / (arcLen - 1.0) - 0.5) * spread + facingAngle;
         double vx = Math.cos(theta);
         double vz = Math.sin(theta);
         double px = this.getX() + vx * distance;
         double pz = this.getZ() + vz * distance;
         AABB selection = new AABB(px - ab, minY, pz - ab, px + ab, maxY, pz + ab);

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, selection)) {
            if (!this.isAlliedTo(entity) && entity != this) {
               boolean flag = entity.hurt(
                  damagesource,
                  (float)(
                     this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage
                        + Math.min(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage, entity.getMaxHealth() * hpdamage)
                  )
               );
               if (entity.isDamageSourceBlocked(damagesource) && entity instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               if (flag) {
                  entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, airborne * distance + this.level().random.nextDouble() * 0.15, 0.0));
               }
            }
         }
      }
   }

   private void Stormknockback(float scale, double distance) {
      for (Entity target : this.getEntitiesNearby(Entity.class, distance, distance, distance, distance)) {
         double d0 = target.getX() - this.getX();
         double d1 = target.getZ() - this.getZ();
         double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
         double power = target.isShiftKeyDown() ? scale / 3.0F : scale;
         target.push(d0 / d2 * power, 0.0, d1 / d2 * power);
      }
   }

   private void WhipLightningAttack(float distance, float mxy, float math, int lifeticks) {
      double minY = this.getY() - 5.0;
      double maxY = this.getY() + mxy;
      float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
      float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
      float f0 = (float)Mth.atan2(f, f1);
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = distance * Math.cos(theta);
      double vecZ = distance * Math.sin(theta);
      double px = this.getX() + vecX + f * math;
      double pz = this.getZ() + vecZ + f1 * math;
      this.spawnLightning(px, pz, minY, maxY, f0, lifeticks, 2.0F);
   }

   protected void spawnLightning(double x, double z, double minY, double maxY, float rotation, int delay, float size) {
      BlockPos blockpos = BlockPos.containing(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.below();
         BlockState blockstate = this.level().getBlockState(blockpos1);
         if (blockstate.isFaceSturdy(this.level(), blockpos1, Direction.UP)) {
            if (!this.level().isEmptyBlock(blockpos)) {
               BlockState blockstate1 = this.level().getBlockState(blockpos);
               VoxelShape voxelshape = blockstate1.getCollisionShape(this.level(), blockpos);
               if (!voxelshape.isEmpty()) {
                  d0 = voxelshape.max(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.below();
      } while (blockpos.getY() >= Mth.floor(minY) - 1);

      if (flag) {
         this.level()
            .addFreshEntity(
               new Lightning_Storm_Entity(
                  this.level(),
                  x,
                  blockpos.getY() + d0,
                  z,
                  rotation,
                  delay,
                  (float)CMCommonConfig.Scylla.LightningStormDamage,
                  (float)CMCommonConfig.Scylla.StormHpDamage,
                  this,
                  size
               )
            );
      }
   }

   private void SummonWave(int number, float anglestep, double vec) {
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      if (!this.level().isClientSide()) {
         double firstAngleOffset = (number - 1) / 2.0 * anglestep;

         for (int i = 0; i < number; i++) {
            double angle = this.yBodyRot - firstAngleOffset + i * anglestep;
            double rad = Math.toRadians(angle);
            double dx = -Math.sin(rad);
            double dz = Math.cos(rad);
            double spawnX = this.getX() + vecX * vec;
            double spawnY = this.getY();
            double spawnZ = this.getZ() + vecZ * vec;
            Wave_Entity WaveEntity = new Wave_Entity(this.level(), this, 80, 9.0F);
            WaveEntity.setPos(spawnX, spawnY, spawnZ);
            WaveEntity.setState(1);
            WaveEntity.setYRot(-((float)(Mth.atan2(dx, dz) * (180.0 / Math.PI))));
            this.level().addFreshEntity(WaveEntity);
         }
      } else {
         int numberOfWaves = number * 3;
         float angleStep = anglestep / 3.0F;
         double firstAngleOffset = (numberOfWaves - 1) / 2.0 * angleStep;

         for (int i = 0; i < numberOfWaves; i++) {
            double angle = this.yBodyRot - firstAngleOffset + i * angleStep;
            double rad = Math.toRadians(angle);
            double dx = -Math.sin(rad);
            double dz = Math.cos(rad);
            double spawnX = this.getX() + vecX * vec;
            double spawnY = this.getY();
            double spawnZ = this.getZ() + vecZ * vec;
            double extraX = spawnX + dx * (2.0 + this.random.nextDouble() / 2.0);
            double extraY = spawnY + 0.9 + this.random.nextDouble() * 0.5;
            double extraZ = spawnZ + dz * (2.0 + this.random.nextDouble() / 2.0);
            this.level()
               .addParticle(
                  new NotSpinTrailParticleOptions(
                     0.44313726F, 0.7607843F, 0.9411765F, 0.05F, 0.5F + this.random.nextFloat() * 0.3F, 0.4F + this.random.nextFloat() * 0.2F, 0.0, 80
                  ),
                  spawnX,
                  spawnY,
                  spawnZ,
                  extraX,
                  extraY,
                  extraZ
               );
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

   private void AreaAttack(float range, float height, float arc, float damage, int shieldbreakticks, boolean knockback) {
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(range, height, range, range);
      DamageSource damagesource = this.damageSources().mobAttack(this);
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
               && !(entityHit instanceof Scylla_Entity)
               && entityHit != this) {
               boolean hurt = entityHit.hurt(
                  damagesource,
                  (float)(
                     this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage
                        + Math.min(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage, entityHit.getMaxHealth() * (float)CMCommonConfig.Scylla.HpDamage)
                  )
               );
               if (entityHit.isDamageSourceBlocked(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               double d0 = entityHit.getX() - this.getX();
               double d1 = entityHit.getZ() - this.getZ();
               double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
               if (hurt && knockback) {
                  entityHit.push(d0 / d2 * 1.5, 0.15, d1 / d2 * 1.5);
               }
            }
         }
      }
   }

   private void PullLighning(double radius, double vec, float math, double height) {
      if (this.level().isClientSide) {
         float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);
         double d0 = this.getX() + vecX * vec + f * math;
         double d1 = this.getY() + height;
         double d2 = this.getZ() + vecZ * vec + f1 * math;
         this.level()
            .addParticle(
               new CircleLightningParticleOptions(0.1F, 143, 241, 215),
               d0 + (this.random.nextFloat() - 0.5F) * radius,
               d1 + this.random.nextFloat() - 0.25,
               d2 + (this.random.nextFloat() - 0.5F) * radius,
               d0,
               d1,
               d2
            );
      }
   }

   private void CircleLighning(float vec, float math, float radius, double EndHeight, int amount, int randamount) {
      if (this.level().isClientSide) {
         float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);
         double d0 = this.getX() + vecX * vec + f * math;
         double d1 = this.getY() + EndHeight;
         double d2 = this.getZ() + vecZ * vec + f1 * math;

         for (int i = 0; i < amount + this.random.nextInt(randamount + 1); i++) {
            double theta2 = this.random.nextDouble() * 2.0 * Math.PI;
            double phi = this.random.nextDouble() * Math.PI;
            double posX = radius * Math.sin(phi) * Math.cos(theta2);
            double posY = radius * Math.cos(phi);
            double posZ = radius * Math.sin(phi) * Math.sin(theta2);
            this.level().addParticle(new CircleLightningParticleOptions(0.1F, 143, 241, 215), d0 + posX, d1 + posY, d2 + posZ, d0, d1, d2);
         }
      }
   }

   private void launch(Entity e, double XZpower, double Ypower) {
      double d0 = e.getX() - this.getX();
      double d1 = e.getZ() - this.getZ();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      e.push(d0 / d2 * XZpower, Ypower, d1 / d2 * XZpower);
   }

   private void Nimbo(float vec, float math, float radius, double EndHeight, int amount, int randamount) {
      if (this.level().isClientSide) {
         for (int j = 0; j < amount + this.random.nextInt(randamount); j++) {
            float f2 = this.random.nextFloat() * (float) (Math.PI * 2);
            float f3 = Mth.sqrt(this.random.nextFloat()) * radius;
            float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
            float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
            double theta = this.yBodyRot * (Math.PI / 180.0);
            theta += Math.PI / 2;
            double vecX = Math.cos(theta);
            double vecZ = Math.sin(theta);
            double d0 = this.getX() + vecX * vec + f * math + Mth.cos(f2) * f3;
            double d2 = this.getY() + EndHeight;
            double d4 = this.getZ() + vecZ * vec + f1 * math + Mth.sin(f2) * f3;
            this.level()
               .addAlwaysVisibleParticle(
                  (ParticleOptions)ModParticle.RAIN_CLOUD.get(),
                  d0,
                  d2,
                  d4,
                  this.random.nextGaussian() * 0.03,
                  this.random.nextGaussian() * 0.01,
                  this.random.nextGaussian() * 0.03
               );
         }
      }
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (!this.getAct()) {
         this.setHomePos(GlobalPos.of(this.level().dimension(), this.blockPosition()));
         this.setAct(true);
         this.heal(this.getMaxHealth());
         this.PlayerCounter(this.level());
         return InteractionResult.SUCCESS;
      } else {
         return super.mobInteract(player, hand);
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

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.SCYLLA_MUSIC.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && !this.isSleep();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.SCYLLA_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.SCYLLA_DEATH.get();
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   @Override
   public boolean removeWhenFarAway(double p_21542_) {
      return false;
   }

   @Override
   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   @Override
   protected boolean canRide(Entity p_31508_) {
      return false;
   }

   protected boolean isAffectedByFluids() {
      return false;
   }

   public boolean canStandOnFluid(FluidState p_204067_) {
      return p_204067_.is(FluidTags.WATER);
   }

   public boolean isPushedByFluid() {
      return false;
   }

   private void floatScylla() {
      if (this.isInWater()) {
         CollisionContext collisioncontext = CollisionContext.of(this);
         if (collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true)
            && !this.level().getFluidState(this.blockPosition().above()).is(FluidTags.WATER)) {
            this.setOnGround(true);
         } else {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5).add(0.0, 0.05, 0.0));
         }
      }
   }

   static class AnchorThrowGoal extends InternalAttackGoal {
      private final Scylla_Entity entity;
      private final float attackMinrange;
      private final float random;
      private boolean anchorrecall;

      public AnchorThrowGoal(
         Scylla_Entity entity,
         int attackgetstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackMinrange,
         float attackMaxrange,
         float random
      ) {
         super(entity, attackgetstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackMaxrange);
         this.random = random;
         this.entity = entity;
         this.attackMinrange = attackMinrange;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && super.canUse()
            && this.entity.anchor_pull_cooldown <= 0
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.distanceTo(target) > this.attackMinrange;
      }

      @Override
      public void start() {
         super.start();
         this.anchorrecall = false;
         this.entity.setEye(true);
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         super.tick();
         this.entity.getNavigation().stop();
         if (this.entity.attackTicks == 20 && this.entity.getAnchor() == null) {
            Scylla_Ceraunus_Entity throwntrident = new Scylla_Ceraunus_Entity(this.entity.level(), this.entity);
            double theta = this.entity.yBodyRot * (Math.PI / 180.0);
            theta += Math.PI / 2;
            double vecX = Math.cos(theta);
            double vecZ = Math.sin(theta);
            double p0 = this.entity.getX() + vecX * 3.0;
            double p1 = this.entity.getY() + this.entity.getBbHeight() * 0.62;
            double p2 = this.entity.getZ() + vecZ * 3.0;
            double p3 = Math.sqrt(p0 * p0 + p2 * p2);
            if (target != null) {
               p0 = target.getX() - this.entity.getX();
               p1 = target.getY(0.3333333333333333) - throwntrident.getY();
               p2 = target.getZ() - this.entity.getZ();
               p3 = Math.sqrt(p0 * p0 + p2 * p2);
            }

            throwntrident.setBaseDamage((float)CMCommonConfig.Scylla.AnchorDamage);
            throwntrident.setPhase(this.entity.isPhase());
            throwntrident.shoot(p0, p1 + p3 * 0.2F, p2, 2.0F, 0.0F);
            throwntrident.setControllerUUID(this.entity.getUUID());
            this.entity.setAnchorUUID(throwntrident.getUUID());
            this.entity.level().addFreshEntity(throwntrident);
         }

         if (this.entity.attackTicks > 20) {
            Entity weapon = this.entity.getAnchor();
            if (weapon instanceof Scylla_Ceraunus_Entity anchor) {
               if (anchor.getGrab()) {
                  this.anchorrecall = true;
                  this.stop();
               }
            } else if (weapon == null) {
               this.anchorrecall = true;
               this.stop();
            }
         }
      }

      @Override
      public void stop() {
         if (this.anchorrecall) {
            this.entity.setAttackState(14);
         } else {
            this.entity.setAttackState(this.attackendstate);
         }

         this.entity.setEye(false);
         this.entity.anchor_pull_cooldown = 140;
         LivingEntity target = this.entity.getTarget();
         if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.entity.setTarget((LivingEntity)null);
         }

         this.entity.getNavigation().stop();
         if (this.entity.getTarget() == null) {
            this.entity.setAggressive(false);
         }
      }
   }

   static class Back_StepGoal extends InternalAttackGoal {
      private final float random;
      private final Scylla_Entity entity;
      private final int getattackstate;

      public Back_StepGoal(
         Scylla_Entity entity, int attackgetstate, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, float attackrange, float random
      ) {
         super(entity, attackgetstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.random = random;
         this.entity = entity;
         this.getattackstate = attackgetstate;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse() && target != null && this.entity.getRandom().nextFloat() * 100.0F < this.random && this.entity.back_step_cooldown <= 0
            || this.entity.getAttackState() == this.attackstate;
      }

      @Override
      public void start() {
         super.start();
         float speed = -1.5F;
         float dodgeYaw = (float)Math.toRadians(this.entity.getYRot() + 90.0F + this.entity.getRandom().nextFloat() * 120.0F - 60.0F);
         Vec3 m = this.entity.getDeltaMovement().add(speed * Math.cos(dodgeYaw), 0.0, speed * Math.sin(dodgeYaw));
         this.entity.setDeltaMovement(m.x, 0.3, m.z);
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.back_step_cooldown = 150;
      }
   }

   static class HorizontalSwingGoal extends Goal {
      private final Scylla_Entity entity;
      private final int getattackstate;
      private final float attackrange;
      private final float random;

      public HorizontalSwingGoal(Scylla_Entity entity, int getattackstate, float attackrange, float random) {
         this.entity = entity;
         this.getattackstate = getattackstate;
         this.attackrange = attackrange;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && target.isAlive()
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.distanceTo(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate;
      }

      public void start() {
         this.entity.setAttackState(1 + this.entity.getRandom().nextInt(4));
         this.entity.setEye(true);
      }

      public boolean canContinueToUse() {
         if (this.entity.getAttackState() == 1) {
            return this.entity.attackTicks <= 38 && this.entity.getAttackState() == 1;
         } else if (this.entity.getAttackState() == 2) {
            return this.entity.attackTicks <= 38 && this.entity.getAttackState() == 2;
         } else if (this.entity.getAttackState() == 3) {
            return this.entity.attackTicks <= 54 && this.entity.getAttackState() == 3;
         } else {
            return this.entity.getAttackState() != 4 ? super.canContinueToUse() : this.entity.attackTicks <= 53 && this.entity.getAttackState() == 4;
         }
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         double theta = this.entity.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);
         this.entity.getNavigation().stop();
         if (this.entity.getAttackState() == 1) {
            if (target != null) {
               boolean flag = this.entity.attackTicks < 14;
               if (flag) {
                  this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
                  this.entity.lookAt(target, 30.0F, 30.0F);
               } else {
                  this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
                  this.entity.setYRot(this.entity.yRotO);
               }
            } else {
               this.entity.setYRot(this.entity.yRotO);
            }

            if (this.entity.attackTicks == 13) {
               if (target != null) {
                  float r = this.entity.distanceTo(target);
                  r = Mth.clamp(r, 1.5F, 4.0F);
                  this.entity.push(vecX * 0.45F * r, 0.0, vecZ * 0.45F * r);
               } else {
                  this.entity.push(vecX * 1.8, 0.0, vecZ * 1.8);
               }
            }
         } else if (this.entity.getAttackState() == 2) {
            if (target != null) {
               boolean flag = this.entity.attackTicks < 14;
               if (flag) {
                  this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
                  this.entity.lookAt(target, 30.0F, 30.0F);
               } else {
                  this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
                  this.entity.setYRot(this.entity.yRotO);
               }
            } else {
               this.entity.setYRot(this.entity.yRotO);
            }

            if (this.entity.attackTicks == 11) {
               if (target != null) {
                  float r = this.entity.distanceTo(target);
                  r = Mth.clamp(r, 1.5F, 4.0F);
                  this.entity.push(vecX * 0.45F * r, 0.0, vecZ * 0.45F * r);
               } else {
                  this.entity.push(vecX * 1.8, 0.0, vecZ * 1.8);
               }
            }
         } else if (this.entity.getAttackState() == 3) {
            if (target == null) {
               this.entity.setYRot(this.entity.yRotO);
            } else {
               boolean flag = this.entity.attackTicks < 16 || this.entity.attackTicks > 20 && this.entity.attackTicks < 30;
               if (flag) {
                  this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
                  this.entity.lookAt(target, 30.0F, 30.0F);
               } else {
                  this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
                  this.entity.setYRot(this.entity.yRotO);
               }
            }

            if (this.entity.attackTicks == 12) {
               if (target != null) {
                  float r = this.entity.distanceTo(target);
                  r = Mth.clamp(r, 1.5F, 4.0F);
                  this.entity.push(vecX * 0.45F * r, 0.0, vecZ * 0.45F * r);
               } else {
                  this.entity.push(vecX * 1.8, 0.0, vecZ * 1.8);
               }
            }

            if (this.entity.attackTicks == 26) {
               this.entity.push(vecX * 1.8, 0.0, vecZ * 1.8);
            }
         } else if (this.entity.getAttackState() == 4) {
            if (target == null) {
               this.entity.setYRot(this.entity.yRotO);
            } else {
               boolean flag = this.entity.attackTicks < 16 || this.entity.attackTicks > 20 && this.entity.attackTicks < 26;
               if (flag) {
                  this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
                  this.entity.lookAt(target, 30.0F, 30.0F);
               } else {
                  this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
                  this.entity.setYRot(this.entity.yRotO);
               }
            }

            if (this.entity.attackTicks == 12) {
               if (target != null) {
                  float r = this.entity.distanceTo(target);
                  r = Mth.clamp(r, 1.5F, 4.0F);
                  this.entity.push(vecX * 0.45F * r, 0.0, vecZ * 0.45F * r);
               } else {
                  this.entity.push(vecX * 1.8, 0.0, vecZ * 1.8);
               }
            }
         }

         if (this.entity.CanParryState() && this.entity.getParryCount() >= 25) {
            this.stop();
         }
      }

      public void stop() {
         if (this.entity.getParryCount() >= 25) {
            this.entity.setAttackState(19);
         } else {
            this.entity.setAttackState(0);
         }

         this.entity.setEye(false);
      }

      public boolean isInterruptable() {
         return false;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class Scylla_EntityPhaseChangeGoal extends Goal {
      protected final Scylla_Entity entity;
      private final int phasestate;
      private final float health;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;

      public Scylla_EntityPhaseChangeGoal(
         Scylla_Entity entity, int getattackstate, int attackstate, int attackendstate, int attackMaxtick, int phasestate, float health
      ) {
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
         this.phasestate = phasestate;
         this.health = health;
      }

      public boolean canUse() {
         return this.entity.isPhase() < this.phasestate
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.getHealth() <= this.entity.getMaxHealth() * this.health;
      }

      public void start() {
         if (this.getattackstate != this.attackstate) {
            this.entity.setAttackState(this.attackstate);
         }

         this.entity.setEye(true);
         this.entity.setPhase(this.phasestate);
      }

      public boolean isInterruptable() {
         return false;
      }

      public void stop() {
         this.entity.setAttackState(this.attackendstate);
         this.entity.setEye(false);
      }

      public boolean canContinueToUse() {
         return this.attackMaxtick > 0 ? this.entity.attackTicks <= this.attackMaxtick : this.canUse();
      }
   }

   static class Scylla_Flying extends InternalAttackGoal {
      private final Scylla_Entity entity;
      private final int attackshot;
      private final float random;

      public Scylla_Flying(
         Scylla_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackshot = attackshot;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse() && target != null && this.entity.getRandom().nextFloat() * 100.0F < this.random && this.entity.flying_cooldown <= 0;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setEye(true);
      }

      @Override
      public void stop() {
         super.stop();
         if (this.entity.isFlying()) {
            this.entity.setFlying(false);
         }
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            boolean flag = this.entity.attackTicks < this.attackshot;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
               this.entity.lookAt(target, 30.0F, 90.0F);
            } else {
               this.entity.getLookControl().setLookAt(target, 0.0F, 90.0F);
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.attackTicks == 9) {
            if (target != null) {
               double d1 = target.getY() - this.entity.getY();
               this.entity.setDeltaMovement(0.0, 1.7 + Mth.clamp(d1 * 0.075, 0.0, 7.0), 0.0);
            } else {
               this.entity.setDeltaMovement(0.0, 1.7, 0.0);
            }
         }

         if (this.entity.attackTicks == this.attackshot && this.entity.getAnchor() == null && target != null) {
            Scylla_Ceraunus_Entity throwntrident = new Scylla_Ceraunus_Entity(this.entity.level(), this.entity);
            double p0 = target.getX() - this.entity.getX();
            double p1 = target.getY(0.3333333333333333) - throwntrident.getY();
            double p2 = target.getZ() - this.entity.getZ();
            double p3 = Math.sqrt(p0 * p0 + p2 * p2);
            throwntrident.setBaseDamage((float)CMCommonConfig.Scylla.AnchorDamage);
            throwntrident.setPhase(this.entity.isPhase());
            throwntrident.shoot(p0, p1 + p3 * 0.2F, p2, 2.0F, 0.0F);
            throwntrident.setControllerUUID(this.entity.getUUID());
            throwntrident.setHookMode(true);
            this.entity.setAnchorUUID(throwntrident.getUUID());
            this.entity.level().addFreshEntity(throwntrident);
         }

         if (this.entity.attackTicks > this.attackshot) {
            Entity weapon = this.entity.getAnchor();
            if (weapon instanceof Scylla_Ceraunus_Entity anchor) {
               if (anchor.getGrab()) {
                  this.stop();
               }
            } else if (weapon == null) {
               this.stop();
            }
         }
      }
   }

   static class Scylla_Lightning_Explosion extends InternalAttackGoal {
      private final Scylla_Entity entity;
      private final int attackshot;
      private final int attackshot2;
      private final float random;
      private final int lifeticks;
      private double prevX;
      private double prevZ;
      private double newX;
      private double newZ;

      public Scylla_Lightning_Explosion(
         Scylla_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         int attackshot2,
         int lifeticks,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackshot = attackshot;
         this.attackshot2 = attackshot2;
         this.lifeticks = lifeticks;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse()
            && target != null
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.explosion_cooldown <= 0
            && this.entity.isPhase() >= 1;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setEye(true);
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.prevX = target.getX();
            this.prevZ = target.getZ();
         }
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.explosion_cooldown = 300;
      }

      @Override
      public void tick() {
         super.tick();
         LivingEntity target = this.entity.getTarget();
         if (this.entity.attackTicks == this.attackshot && target != null) {
            double x = target.getX();
            double z = target.getZ();
            double vx = (x - this.prevX) / this.attackshot;
            double vz = (z - this.prevZ) / this.attackshot;
            this.newX = x + vx * this.lifeticks;
            this.newZ = z + vz * this.lifeticks;

            for (int k = -4; k <= 4; k++) {
               double d2 = 1.25 * k;
               float yaw = target.getYRot() * (float) Math.PI / 180.0F;
               this.entity
                  .spawnLightning(
                     this.newX + Mth.cos(yaw) * d2, this.newZ + Mth.sin(yaw) * d2, this.entity.getY() - 2.0, this.entity.getY() + 5.0, yaw, 0, 3.0F
                  );
            }
         }

         if (this.entity.attackTicks == this.attackshot2 && target != null) {
            double x = target.getX();
            double z = target.getZ();
            double vx = (x - this.prevX) / this.attackshot2;
            double vz = (z - this.prevZ) / this.attackshot2;
            this.newX = x + vx * this.lifeticks;
            this.newZ = z + vz * this.lifeticks;

            for (int k = -4; k <= 4; k++) {
               double d2 = 1.25 * k;
               float yaw = target.getYRot() * (float) Math.PI / 180.0F;
               this.entity
                  .spawnLightning(
                     this.newX + Mth.cos(yaw) * d2, this.newZ + Mth.sin(yaw) * d2, this.entity.getY() - 2.0, this.entity.getY() + 5.0, yaw, 0, 3.0F
                  );
            }
         }
      }
   }

   static class ScyllafallingState extends InternalStateGoal {
      private final Scylla_Entity entity;
      private final int attackseetick;

      public ScyllafallingState(Scylla_Entity entity, int getAttackState, int attackstate, int attackendstate, int attackMaxtick, int attackseetick) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick);
         this.entity = entity;
         this.attackseetick = attackseetick;
      }

      @Override
      public void start() {
         super.start();
         if (this.entity.isFlying()) {
            this.entity.setFlying(false);
         }
      }

      @Override
      public boolean canContinueToUse() {
         return (this.entity.attackTicks <= 5 || !this.entity.verticalCollisionBelow) && super.canContinueToUse();
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            boolean flag = this.entity.attackTicks < this.attackseetick;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 30.0F, 0.0F);
               this.entity.lookAt(target, 30.0F, 0.0F);
            } else {
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.getAnchor() instanceof Scylla_Ceraunus_Entity anchor) {
            double maxSpeed = 18.0;
            double pullSpeed = maxSpeed / 6.0;
            Vec3 distance = anchor.position().subtract(this.entity.position().add(0.0, this.entity.getBbHeight() / 2.0F, 0.0));
            Vec3 motion = distance.normalize().scale(pullSpeed);
            if (Math.abs(distance.y) < 0.1) {
               motion = new Vec3(motion.x, 0.0, motion.z);
            }

            if (new Vec3(distance.x, 0.0, distance.z).length() < new Vec3(this.entity.getBbWidth() / 2.0F, 0.0, this.entity.getBbWidth() / 2.0F).length() / 1.4
               )
             {
               motion = new Vec3(0.0, motion.y, 0.0);
            }

            this.entity.setDeltaMovement(motion);
            this.entity.hasImpulse = true;
         }
      }

      @Override
      public void stop() {
         super.stop();
         Entity weapon = this.entity.getAnchor();
         if (weapon != null) {
            weapon.discard();
         }

         this.entity.flying_cooldown = 400;
      }
   }

   static class SpearThrowGoal extends Goal {
      private final Scylla_Entity entity;
      private final int getattackstate;
      private final float attackMinrange;
      private final float attackMaxrange;
      private final int attackseetick;
      private final float random;

      public SpearThrowGoal(Scylla_Entity entity, int getattackstate, float attackMinrange, float attackMaxrange, int attackseetick, float random) {
         this.entity = entity;
         this.getattackstate = getattackstate;
         this.attackMinrange = attackMinrange;
         this.attackMaxrange = attackMaxrange;
         this.attackseetick = attackseetick;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && target.isAlive()
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.distanceTo(target) > this.attackMinrange
            && this.entity.distanceTo(target) < this.attackMaxrange
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.spear_cooldown <= 0;
      }

      public void start() {
         if (this.entity.random.nextBoolean()) {
            this.entity.setAttackState(10);
         } else {
            this.entity.setAttackState(11);
         }

         this.entity.setEye(true);
      }

      public boolean canContinueToUse() {
         return (this.entity.getAttackState() == 10 || this.entity.getAttackState() == 11) && this.entity.attackTicks <= 49;
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            boolean flag = this.entity.attackTicks < this.attackseetick;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.entity.lookAt(target, 30.0F, 30.0F);
            } else {
               this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         this.entity.getNavigation().stop();
         if (target != null) {
            float f = Mth.cos(this.entity.yBodyRot * (float) (Math.PI / 180.0));
            float f1 = Mth.sin(this.entity.yBodyRot * (float) (Math.PI / 180.0));
            if (this.entity.attackTicks == 27) {
               double math = -0.5;
               double d0 = this.entity.getX() + f * math;
               double d1 = this.entity.getY() + this.entity.getBbHeight() * 0.8F;
               double d2 = this.entity.getZ() + f1 * math;
               double d3 = target.getX() - d0;
               double d4 = target.getY(0.35) - d1;
               double d5 = target.getZ() - d2;
               Vec3 vec3 = new Vec3(d3, d4, d5).normalize();
               float yRot = (float)(Mth.atan2(vec3.z, vec3.x) * (180.0 / Math.PI)) + 90.0F;
               float xRot = (float)(-(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180.0 / Math.PI)));
               if (this.entity.getAttackState() == 11) {
                  Water_Spear_Entity water = new Water_Spear_Entity(this.entity, vec3, this.entity.level(), (float)CMCommonConfig.Scylla.SpearDamage, 2.0);
                  water.setYRot(yRot);
                  water.setXRot(xRot);
                  water.setPosRaw(d0, d1, d2);
                  water.setTotalBounces(8);
                  this.entity.level().addFreshEntity(water);
               }

               if (this.entity.getAttackState() == 10) {
                  Lightning_Spear_Entity lightning = new Lightning_Spear_Entity(
                     this.entity, vec3, this.entity.level(), (float)CMCommonConfig.Scylla.SpearDamage, 2.0
                  );
                  lightning.setYRot(yRot);
                  lightning.setXRot(xRot);
                  lightning.setPosRaw(d0, d1, d2);
                  lightning.setAreaDamage((float)CMCommonConfig.Scylla.LightningAreaDamage);
                  lightning.setHpDamage((float)CMCommonConfig.Scylla.StormHpDamage);
                  lightning.setAreaRadius(2.0F);
                  this.entity.level().addFreshEntity(lightning);
               }
            }

            if (this.entity.isPhase() > 0 && this.entity.attackTicks == 32) {
               for (int i = 0; i < 2; i++) {
                  double dis = 2.0;
                  double firstAngleOffset = 0.5 * dis;
                  double math = -0.5 - firstAngleOffset + i * dis;
                  double d0 = this.entity.getX() + f * math;
                  double d1 = this.entity.getY() + this.entity.getBbHeight() * 0.7F;
                  double d2 = this.entity.getZ() + f1 * math;
                  double d3 = target.getX() - d0;
                  double d4 = target.getY(0.35) - d1;
                  double d5 = target.getZ() - d2;
                  Vec3 vec3 = new Vec3(d3, d4, d5).normalize();
                  float yRot = (float)(Mth.atan2(vec3.z, vec3.x) * (180.0 / Math.PI)) + 90.0F;
                  float xRot = (float)(-(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180.0 / Math.PI)));
                  if (this.entity.getAttackState() == 11) {
                     Water_Spear_Entity water = new Water_Spear_Entity(
                        this.entity, vec3, this.entity.level(), (float)CMCommonConfig.Scylla.SpearDamage, this.entity.isPhase() < 2 ? 1.5 : 2.0
                     );
                     water.setYRot(yRot);
                     water.setXRot(xRot);
                     water.setPosRaw(d0, d1, d2);
                     water.setTotalBounces(8);
                     this.entity.level().addFreshEntity(water);
                  }

                  if (this.entity.getAttackState() == 10) {
                     Lightning_Spear_Entity lightning = new Lightning_Spear_Entity(
                        this.entity, vec3, this.entity.level(), (float)CMCommonConfig.Scylla.SpearDamage, this.entity.isPhase() < 2 ? 1.5 : 2.0
                     );
                     lightning.setYRot(yRot);
                     lightning.setXRot(xRot);
                     lightning.setPosRaw(d0, d1, d2);
                     lightning.setAreaDamage((float)CMCommonConfig.Scylla.LightningAreaDamage);
                     lightning.setHpDamage((float)CMCommonConfig.Scylla.StormHpDamage);
                     lightning.setAreaRadius(2.0F);
                     this.entity.level().addFreshEntity(lightning);
                  }
               }
            }
         }
      }

      public void stop() {
         this.entity.setAttackState(0);
         this.entity.setEye(false);
         this.entity.spear_cooldown = 80;
      }

      public boolean isInterruptable() {
         return false;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class SummonSnake extends InternalAttackGoal {
      private final float random;
      private final Scylla_Entity entity;

      public SummonSnake(
         Scylla_Entity entity, int attackgetstate, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, float attackrange, float random
      ) {
         super(entity, attackgetstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.random = random;
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse()
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.summon_snake_cooldown <= 0
            && this.entity.isPhase() > 0;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setEye(true);
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         super.tick();
         if (target != null) {
            if (this.entity.attackTicks == 28) {
               for (int i = 0; i < 2; i++) {
                  float f = Mth.cos(this.entity.yBodyRot * (float) (Math.PI / 180.0));
                  float f1 = Mth.sin(this.entity.yBodyRot * (float) (Math.PI / 180.0));
                  double dis = 8.0;
                  double firstAngleOffset = 0.5 * dis;
                  double math = 0.0 - firstAngleOffset + i * dis;
                  double d0 = this.entity.getX() + f * math;
                  double d1 = this.entity.getY() + this.entity.getBbHeight() * 0.7F;
                  double d2 = this.entity.getZ() + f1 * math;
                  double d3 = target.getX() - d0;
                  double d4 = target.getY(0.35) - d1;
                  double d5 = target.getZ() - d2;
                  Vec3 vec3 = new Vec3(d3, d4, d5).normalize();
                  this.entity
                     .level()
                     .addFreshEntity(
                        new Storm_Serpent_Entity(
                           this.entity.level(),
                           d0,
                           this.entity.getY(),
                           d2,
                           (float)Mth.atan2(vec3.z, vec3.x),
                           i * 8,
                           this.entity,
                           (float)CMCommonConfig.Scylla.SnakeDamage,
                           target,
                           i == 0
                        )
                     );
               }
            }

            if (this.entity.isPhase() > 1 && this.entity.attackTicks == 40) {
               for (int i = 0; i < 2; i++) {
                  float f = Mth.cos(this.entity.yBodyRot * (float) (Math.PI / 180.0));
                  float f1 = Mth.sin(this.entity.yBodyRot * (float) (Math.PI / 180.0));
                  double dis = 12.0;
                  double firstAngleOffset = 0.5 * dis;
                  double math = 0.0 - firstAngleOffset + i * dis;
                  double d0 = this.entity.getX() + f * math;
                  double d1 = this.entity.getY() + this.entity.getBbHeight() * 0.7F;
                  double d2 = this.entity.getZ() + f1 * math;
                  double d3 = target.getX() - d0;
                  double d4 = target.getY(0.35) - d1;
                  double d5 = target.getZ() - d2;
                  Vec3 vec3 = new Vec3(d3, d4, d5).normalize();
                  this.entity
                     .level()
                     .addFreshEntity(
                        new Storm_Serpent_Entity(
                           this.entity.level(),
                           d0,
                           this.entity.getY(),
                           d2,
                           (float)Mth.atan2(vec3.z, vec3.x),
                           i * 8,
                           this.entity,
                           (float)CMCommonConfig.Scylla.SnakeDamage,
                           target,
                           i == 0
                        )
                     );
               }
            }
         }
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.setEye(false);
         this.entity.summon_snake_cooldown = 350;
      }
   }

   static class ThunderCloud extends InternalAttackGoal {
      private final float random;
      private final Scylla_Entity entity;

      public ThunderCloud(
         Scylla_Entity entity, int attackgetstate, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, float attackrange, float random
      ) {
         super(entity, attackgetstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.random = random;
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         return super.canUse()
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.thundercloud_cooldown <= 0
            && this.entity.isPhase() > 0;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setEye(true);
         this.entity.setChainAnchor(true);
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         super.tick();
         if (this.entity.attackTicks >= 115 && this.entity.attackTicks <= 145 && target != null) {
            float f = Mth.cos(this.entity.yBodyRot * (float) (Math.PI / 180.0));
            float f1 = Mth.sin(this.entity.yBodyRot * (float) (Math.PI / 180.0));
            float f2 = this.entity.random.nextFloat() * (float) (Math.PI * 2);
            float f3 = Mth.sqrt(this.entity.random.nextFloat()) * 5.0F;
            float math = 0.2F;
            double d0 = this.entity.getX() + f * math + Mth.cos(f2) * f3;
            double d1 = this.entity.getY() + this.entity.getBbHeight() * 2.8F;
            double d2 = this.entity.getZ() + f1 * math + Mth.sin(f2) * f3;
            double d3 = target.getX() - (this.entity.random.nextDouble() - 0.5) * 6.0 - d0;
            double d4 = target.getY(0.35) - this.entity.random.nextDouble() - d1;
            double d5 = target.getZ() - (this.entity.random.nextDouble() - 0.5) * 6.0 - d2;
            Vec3 vec3 = new Vec3(d3, d4, d5).normalize();
            float yRot = (float)(Mth.atan2(vec3.z, vec3.x) * (180.0 / Math.PI)) + 90.0F;
            float xRot = (float)(-(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180.0 / Math.PI)));
            Water_Spear_Entity water = new Water_Spear_Entity(this.entity, vec3, this.entity.level(), (float)CMCommonConfig.Scylla.SpearDamage, 1.0);
            water.setYRot(yRot);
            water.setXRot(xRot);
            water.setPosRaw(d0, d1, d2);
            water.setTotalBounces(3);
            this.entity.level().addFreshEntity(water);
            Lightning_Spear_Entity lightning = new Lightning_Spear_Entity(this.entity, vec3, this.entity.level(), (float)CMCommonConfig.Scylla.SnakeDamage, 1.0);
            lightning.setYRot(yRot);
            lightning.setXRot(xRot);
            lightning.setPosRaw(d0, d1, d2);
            lightning.setAreaDamage((float)CMCommonConfig.Scylla.LightningAreaDamage);
            lightning.setHpDamage((float)CMCommonConfig.Scylla.StormHpDamage);
            lightning.setAreaRadius(2.0F);
            this.entity.level().addFreshEntity(lightning);
         }
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.setEye(false);
         this.entity.setChainAnchor(false);
         this.entity.thundercloud_cooldown = 350;
      }
   }

   static class WhipAndSpearGoal extends InternalAttackGoal {
      private final Scylla_Entity entity;
      private final float attackMinrange;
      private final float random;

      public WhipAndSpearGoal(
         Scylla_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         float attackMinrange,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackMinrange = attackMinrange;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && super.canUse()
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.distanceTo(target) >= this.attackMinrange
            && this.entity.isPhase() >= 2
            && this.entity.whip_cooldown <= 0;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setEye(true);
         this.entity.setChainAnchor(true);
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            boolean flag = this.entity.attackTicks < 17
               || this.entity.attackTicks > 45 && this.entity.attackTicks < 66
               || this.entity.attackTicks > 80 && this.entity.attackTicks < 95;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.entity.lookAt(target, 30.0F, 30.0F);
            } else {
               this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         this.entity.getNavigation().stop();
         if (target != null) {
            float f = Mth.cos(this.entity.yBodyRot * (float) (Math.PI / 180.0));
            float f1 = Mth.sin(this.entity.yBodyRot * (float) (Math.PI / 180.0));
            if (this.entity.attackTicks == 68) {
               float math = -0.5F;
               int spearAmount = 5;
               double offsetangle = Math.toRadians(15.0);
               double d0 = this.entity.getX() + f * math;
               double d1 = this.entity.getY() + this.entity.getBbHeight() * 0.7F;
               double d2 = this.entity.getZ() + f1 * math;
               double d3 = target.getX() - d0;
               double d4 = target.getY(0.2) - d1;
               double d5 = target.getZ() - d2;

               for (int i = 0; i <= spearAmount - 1; i++) {
                  double angle = (i - (spearAmount - 1) / 2.0) * offsetangle;
                  double x = d3 * Math.cos(angle) + d5 * Math.sin(angle);
                  double y = d4;
                  double z = -d3 * Math.sin(angle) + d5 * Math.cos(angle);
                  Vec3 vec3 = new Vec3(x, y, z).normalize();
                  float yRot = (float)(Mth.atan2(vec3.z, vec3.x) * (180.0 / Math.PI)) + 90.0F;
                  float xRot = (float)(-(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180.0 / Math.PI)));
                  Water_Spear_Entity water = new Water_Spear_Entity(this.entity, vec3, this.entity.level(), (float)CMCommonConfig.Scylla.SpearDamage, 2.0);
                  water.setYRot(yRot);
                  water.setXRot(xRot);
                  water.setPosRaw(d0, d1, d2);
                  water.setTotalBounces(8);
                  this.entity.level().addFreshEntity(water);
               }
            }
         }
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.setEye(false);
         this.entity.setChainAnchor(false);
         this.entity.whip_cooldown = 400;
      }
   }
}
