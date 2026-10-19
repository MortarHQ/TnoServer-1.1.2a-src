package com.bobmowzie.mowziesmobs.server.entity.frostmaw;

import com.bobmowzie.mowziesmobs.client.particle.ParticleCloud;
import com.bobmowzie.mowziesmobs.client.particle.ParticleRing;
import com.bobmowzie.mowziesmobs.client.particle.ParticleSnowFlake;
import com.bobmowzie.mowziesmobs.client.sound.BossMusic;
import com.bobmowzie.mowziesmobs.client.sound.BossMusicPlayer;
import com.bobmowzie.mowziesmobs.server.advancement.AdvancementHandler;
import com.bobmowzie.mowziesmobs.server.advancement.StealIceCrystalTrigger;
import com.bobmowzie.mowziesmobs.server.ai.MMEntityMoveHelper;
import com.bobmowzie.mowziesmobs.server.ai.MMPathNavigateGround;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationActivateAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationAreaAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDeactivateAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDieAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationTakeDamage;
import com.bobmowzie.mowziesmobs.server.ai.animation.SimpleAnimationAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.LegSolverQuadruped;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBall;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBreath;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class EntityFrostmaw extends MowzieLLibraryEntity implements Enemy {
   public static final Animation DIE_ANIMATION = Animation.create(94);
   public static final Animation HURT_ANIMATION = Animation.create(0);
   public static final Animation ROAR_ANIMATION = Animation.create(76);
   public static final Animation SWIPE_ANIMATION = Animation.create(28);
   public static final Animation SWIPE_TWICE_ANIMATION = Animation.create(57);
   public static final Animation ICE_BREATH_ANIMATION = Animation.create(92);
   public static final Animation ICE_BALL_ANIMATION = Animation.create(50);
   public static final Animation ACTIVATE_ANIMATION = Animation.create(118);
   public static final Animation ACTIVATE_NO_CRYSTAL_ANIMATION = Animation.create(100);
   public static final Animation DEACTIVATE_ANIMATION = Animation.create(25);
   public static final Animation DODGE_ANIMATION = Animation.create(15);
   public static final Animation LAND_ANIMATION = Animation.create(14);
   public static final Animation SLAM_ANIMATION = Animation.create(113);
   private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.defineId(EntityFrostmaw.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> HAS_CRYSTAL = SynchedEntityData.defineId(EntityFrostmaw.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> ALWAYS_ACTIVE = SynchedEntityData.defineId(EntityFrostmaw.class, EntityDataSerializers.BOOLEAN);
   public static final int ICE_BREATH_COOLDOWN = 260;
   public static final int ICE_BALL_COOLDOWN = 200;
   public static final int SLAM_COOLDOWN = 500;
   public static final int DODGE_COOLDOWN = 200;
   public EntityIceBreath iceBreath;
   public boolean swingWhichArm = false;
   private Vec3 prevRightHandPos = new Vec3(0.0, 0.0, 0.0);
   private Vec3 prevLeftHandPos = new Vec3(0.0, 0.0, 0.0);
   private int iceBreathCooldown = 0;
   private int iceBallCooldown = 0;
   private int slamCooldown = 0;
   private int timeWithoutTarget;
   private int shouldDodgeMeasure = 0;
   private int dodgeCooldown = 0;
   private boolean shouldDodge;
   private float dodgeYaw = 0.0F;
   private boolean wantsToIceBreathAfterDodging = false;
   private Vec3 prevTargetPos = new Vec3(0.0, 0.0, 0.0);
   private boolean shouldPlayLandAnimation = false;
   public LegSolverQuadruped legSolver;

   public EntityFrostmaw(EntityType<? extends EntityFrostmaw> type, Level world) {
      super(type, world);
      this.frame = this.frame + this.random.nextInt(50);
      this.legSolver = new LegSolverQuadruped(1.0F, 2.0F, -1.0F, 1.5F);
      if (world.isClientSide) {
         this.socketPosArray = new Vec3[]{new Vec3(0.0, 0.0, 0.0), new Vec3(0.0, 0.0, 0.0), new Vec3(0.0, 0.0, 0.0)};
      }

      this.active = false;
      this.playsHurtAnimation = false;
      this.setYRot(this.yBodyRot = this.random.nextFloat() * 360.0F);
      this.xpReward = 60;
      this.moveControl = new MMEntityMoveHelper(this, 7.0F);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(2, new AnimationAreaAttackAI<EntityFrostmaw>(this, SWIPE_ANIMATION, null, null, 2.0F, 6.5F, 6.0F, 135.0F, 1.0F, 9) {
         @Override
         public void start() {
            super.start();
         }
      });
      this.goalSelector.addGoal(2, new AnimationAreaAttackAI<EntityFrostmaw>(this, SWIPE_TWICE_ANIMATION, null, null, 1.0F, 6.5F, 6.0F, 135.0F, 1.0F, 9) {
         @Override
         public void start() {
            super.start();
         }

         @Override
         public void tick() {
            super.tick();
            if (EntityFrostmaw.this.getAnimationTick() == 21) {
               this.hitEntities();
            }

            if (EntityFrostmaw.this.getAnimationTick() == 16) {
               EntityFrostmaw.this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_WHOOSH.get(), 2.0F, 0.7F);
            }

            if (EntityFrostmaw.this.getAnimationTick() == 6) {
               EntityFrostmaw.this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_WHOOSH.get(), 2.0F, 0.8F);
            }

            if (EntityFrostmaw.this.getTarget() != null) {
               EntityFrostmaw.this.lookControl.setLookAt(EntityFrostmaw.this.getTarget(), 30.0F, 30.0F);
            }
         }

         @Override
         protected void onAttack(LivingEntity entityTarget, float damageMultiplier, float applyKnockbackMultiplier) {
            super.onAttack(entityTarget, damageMultiplier, applyKnockbackMultiplier);
            if (EntityFrostmaw.this.getAnimationTick() == 21 && entityTarget instanceof Player player && player.isBlocking()) {
               player.disableShield();
            }
         }
      });
      this.goalSelector.addGoal(2, new SimpleAnimationAI<>(this, ICE_BREATH_ANIMATION, true));
      this.goalSelector.addGoal(2, new SimpleAnimationAI<EntityFrostmaw>(this, ICE_BALL_ANIMATION, true) {
         @Override
         public void start() {
            super.start();
            EntityFrostmaw.this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_ICEBALL_CHARGE.get(), 2.0F, 0.9F);
         }
      });
      this.goalSelector.addGoal(2, new SimpleAnimationAI<>(this, ROAR_ANIMATION, false));
      this.goalSelector.addGoal(2, new AnimationActivateAI<EntityFrostmaw>(this, ACTIVATE_ANIMATION) {
         @Override
         public void start() {
            super.start();
            EntityFrostmaw.this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_WAKEUP.get(), 1.0F, 1.0F);
         }
      });
      this.goalSelector.addGoal(2, new AnimationActivateAI<EntityFrostmaw>(this, ACTIVATE_NO_CRYSTAL_ANIMATION) {
         @Override
         public void start() {
            super.start();
            EntityFrostmaw.this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_WAKEUP.get(), 1.0F, 1.0F);
         }
      });
      this.goalSelector.addGoal(2, new AnimationDeactivateAI<>(this, DEACTIVATE_ANIMATION));
      this.goalSelector.addGoal(2, new SimpleAnimationAI<>(this, LAND_ANIMATION, false));
      this.goalSelector.addGoal(2, new SimpleAnimationAI<>(this, SLAM_ANIMATION, EnumSet.of(Flag.LOOK)));
      this.goalSelector.addGoal(2, new SimpleAnimationAI<>(this, DODGE_ANIMATION, EnumSet.of(Flag.MOVE, Flag.JUMP)));
      this.goalSelector.addGoal(3, new AnimationTakeDamage<>(this));
      this.goalSelector.addGoal(1, new AnimationDieAI<>(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, 0, true, false, null));
   }

   protected PathNavigation createNavigation(Level world) {
      return new MMPathNavigateGround(this, world);
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .add(Attributes.ATTACK_DAMAGE, 10.0)
         .add(Attributes.MAX_HEALTH, 250.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.FOLLOW_RANGE, 50.0)
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.9);
   }

   protected float getWaterSlowDown() {
      return 0.98F;
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ACTIVE, false);
      builder.define(HAS_CRYSTAL, true);
      builder.define(ALWAYS_ACTIVE, false);
   }

   public void playAmbientSound() {
      if (this.active) {
         int i = this.random.nextInt(4);
         super.playAmbientSound();
         if (i == 0 && this.getAnimation() == NO_ANIMATION) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, ROAR_ANIMATION);
         } else {
            if (i < MMSounds.ENTITY_FROSTMAW_LIVING.size()) {
               this.playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_FROSTMAW_LIVING.get(i)).get(), 2.0F, 0.8F + this.random.nextFloat() * 0.3F);
            }
         }
      }
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return super.getAmbientSound();
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      for (Entity entity : this.getPlayersNearby(x, y, z, radius)) {
         double angle = (this.getAngleBetweenEntities(this, entity) + 90.0) * Math.PI / 180.0;
         entity.setDeltaMovement(-0.1 * Math.cos(angle), entity.getDeltaMovement().y, -0.1 * Math.sin(angle));
      }
   }

   @Override
   public void tick() {
      this.setYRot(this.yBodyRot);
      super.tick();
      this.repelEntities(3.8F, 3.8F, 3.8F, 3.8F);
      if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget().getHealth() <= 0.0F)) {
         this.setTarget(null);
      }

      if (this.isAlwaysActive()) {
         this.setActive(true);
      }

      if (this.getActive() && this.getAnimation() != ACTIVATE_ANIMATION && this.getAnimation() != ACTIVATE_NO_CRYSTAL_ANIMATION) {
         this.legSolver.update(this);
         if ((this.getAnimation() == SWIPE_ANIMATION || this.getAnimation() == SWIPE_TWICE_ANIMATION) && this.getAnimationTick() == 3) {
            int i = Mth.nextInt(this.random, 0, MMSounds.ENTITY_FROSTMAW_ATTACK.size());
            if (i < MMSounds.ENTITY_FROSTMAW_ATTACK.size()) {
               this.playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_FROSTMAW_ATTACK.get(i)).get(), 2.0F, 0.9F + this.random.nextFloat() * 0.2F);
            }
         }

         if (this.getAnimation() == SWIPE_ANIMATION) {
            if (this.getAnimationTick() == 6) {
               this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_WHOOSH.get(), 2.0F, 0.8F);
            }

            if (this.getTarget() != null) {
               this.lookControl.setLookAt(this.getTarget(), 30.0F, 30.0F);
            }
         }

         if (this.getAnimation() == ROAR_ANIMATION) {
            if (this.getAnimationTick() == 10) {
               this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_ROAR.get(), 4.0F, 1.0F);
               EntityCameraShake.cameraShake(this.level(), this.position(), 45.0F, 0.03F, 60, 20);
            }

            if (this.getAnimationTick() >= 8 && this.getAnimationTick() < 65) {
               this.doRoarEffects();
            }
         }

         if (this.getAnimation() == LAND_ANIMATION && this.getAnimationTick() == 3) {
            this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_LAND.get(), 3.0F, 0.9F);
         }

         if (this.getAnimation() == SLAM_ANIMATION) {
            if (this.getAnimationTick() == 82) {
               this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_LIVING_1.get(), 2.0F, 1.0F);
            }

            if (this.getTarget() != null) {
               this.lookControl.setLookAt(this.getTarget(), 30.0F, 30.0F);
            }

            if (this.getAnimationTick() == 82) {
               int i = Mth.nextInt(this.random, 0, MMSounds.ENTITY_FROSTMAW_ATTACK.size() - 1);
               if (i < MMSounds.ENTITY_FROSTMAW_ATTACK.size()) {
                  this.playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_FROSTMAW_ATTACK.get(i)).get(), 2.0F, 0.9F + this.random.nextFloat() * 0.2F);
               }

               this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_WHOOSH.get(), 2.0F, 0.7F);
            }

            if (this.getAnimationTick() == 87) {
               this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_LAND.get(), 3.0F, 1.0F);
               float radius = 4.0F;
               float slamPosX = (float)(this.getX() + radius * Math.cos(Math.toRadians(this.getYRot() + 90.0F)));
               float slamPosZ = (float)(this.getZ() + radius * Math.sin(Math.toRadians(this.getYRot() + 90.0F)));
               if (this.level().isClientSide) {
                  this.level()
                     .addParticle(
                        new ParticleRing.Data(0.0F, (float) (Math.PI / 2), 17.0F, 1.0F, 1.0F, 1.0F, 1.0F, 60, false, ParticleRing.EnumRingBehavior.GROW),
                        slamPosX,
                        this.getY() + 0.2F,
                        slamPosZ,
                        0.0,
                        0.0,
                        0.0
                     );
               }

               AABB hitBox = new AABB(slamPosX - 0.5F, this.getY() - 0.5, slamPosZ - 0.5F, slamPosX + 0.5F, this.getY() + 0.5, slamPosZ + 0.5F)
                  .inflate(3.0, 3.0, 3.0);

               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, hitBox)) {
                  if (entity != this && entity.position().distanceToSqr(slamPosX, this.getY(), slamPosZ) <= 9.0) {
                     this.doHurtTarget(entity, 4.0F, 1.0F);
                     if (entity.isBlocking()) {
                        entity.getUseItem().hurtAndBreak(400, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
                     }
                  }
               }

               EntityCameraShake.cameraShake(this.level(), new Vec3(slamPosX, this.getY(), slamPosZ), 30.0F, 0.1F, 0, 20);
            }
         }

         if (this.getAnimation() == DODGE_ANIMATION && !this.level().isClientSide) {
            this.getNavigation().stop();
            if (this.getAnimationTick() == 2) {
               this.dodgeYaw = (float)Math.toRadians(this.targetAngle + 90.0F + this.random.nextFloat() * 150.0F - 75.0F);
            }

            if (this.getAnimationTick() == 6 && (this.onGround() || this.isInLava() || this.isInWater())) {
               float speed = 1.7F;
               Vec3 m = this.getDeltaMovement().add(speed * Math.cos(this.dodgeYaw), 0.0, speed * Math.sin(this.dodgeYaw));
               this.setDeltaMovement(m.x, 0.6, m.z);
            }

            if (this.getTarget() != null) {
               this.lookControl.setLookAt(this.getTarget(), 30.0F, 30.0F);
            }
         }

         if (this.getAnimation() == ICE_BREATH_ANIMATION) {
            if (this.getTarget() != null) {
               this.lookControl.setLookAt(this.getTarget(), 30.0F, 30.0F);
               this.lookAt(this.getTarget(), 30.0F, 30.0F);
               this.yHeadRot = this.yBodyRot = this.getYRot();
            }

            Vec3 mouthPos = new Vec3(2.3, 2.65, 0.0);
            mouthPos = mouthPos.yRot((float)Math.toRadians(-this.getYRot() - 90.0F));
            mouthPos = mouthPos.add(this.position());
            mouthPos = mouthPos.add(new Vec3(0.0, 0.0, 1.0).xRot((float)Math.toRadians(-this.getXRot())).yRot((float)Math.toRadians(-this.yHeadRot)));
            if (this.getAnimationTick() == 13) {
               this.iceBreath = new EntityIceBreath((EntityType<? extends EntityIceBreath>)EntityHandler.ICE_BREATH.get(), this.level(), this);
               this.iceBreath.absMoveTo(mouthPos.x, mouthPos.y, mouthPos.z, this.yHeadRot, this.getXRot() + 10.0F);
               if (!this.level().isClientSide) {
                  this.level().addFreshEntity(this.iceBreath);
               }
            }

            if (this.iceBreath != null) {
               this.iceBreath.absMoveTo(mouthPos.x, mouthPos.y, mouthPos.z, this.yHeadRot, this.getXRot() + 10.0F);
            }
         }

         if (this.getAnimation() == ICE_BALL_ANIMATION) {
            if (this.getTarget() != null) {
               this.lookControl.setLookAt(this.getTarget(), 15.0F, 15.0F);
            }

            Vec3 projectilePos = new Vec3(2.0, 1.9, 0.0);
            projectilePos = projectilePos.yRot((float)Math.toRadians(-this.getYRot() - 90.0F));
            projectilePos = projectilePos.add(this.position());
            projectilePos = projectilePos.add(new Vec3(0.0, 0.0, 1.0).xRot((float)Math.toRadians(-this.getXRot())).yRot((float)Math.toRadians(-this.yHeadRot)));
            if (this.level().isClientSide) {
               Vec3 mouthPos = this.socketPosArray[2];
               if (this.getAnimationTick() < 12) {
                  for (int i = 0; i < 6; i++) {
                     Vec3 particlePos = new Vec3(3.5, 0.0, 0.0);
                     particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
                     particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
                     float value = this.random.nextFloat() * 0.15F;
                     this.level()
                        .addParticle(
                           new ParticleCloud.Data(
                              0.75F + value, 0.75F + value, 1.0F, 5.0F + this.random.nextFloat() * 15.0F, 30, ParticleCloud.EnumCloudBehavior.CONSTANT, 1.0F
                           ),
                           mouthPos.x + particlePos.x,
                           mouthPos.y + particlePos.y,
                           mouthPos.z + particlePos.z,
                           -0.1 * particlePos.x,
                           -0.1 * particlePos.y,
                           -0.1 * particlePos.z
                        );
                  }

                  for (int i = 0; i < 8; i++) {
                     Vec3 particlePos = new Vec3(3.5, 0.0, 0.0);
                     particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
                     particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
                     this.level()
                        .addParticle(
                           new ParticleSnowFlake.Data(40.0F, false),
                           mouthPos.x + particlePos.x,
                           mouthPos.y + particlePos.y,
                           mouthPos.z + particlePos.z,
                           -0.07 * particlePos.x,
                           -0.07 * particlePos.y,
                           -0.07 * particlePos.z
                        );
                  }
               }
            }

            if (this.getAnimationTick() == 32 && this.getTarget() != null) {
               this.prevTargetPos = this.getTarget().position().add(new Vec3(0.0, this.getTarget().getBbHeight() / 2.0, 0.0));
            }

            if (this.getAnimationTick() == 33) {
               this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_ICEBALL_SHOOT.get(), 2.0F, 0.7F);
               EntityIceBall iceBall = new EntityIceBall((EntityType<? extends EntityIceBall>)EntityHandler.ICE_BALL.get(), this.level(), this);
               iceBall.absMoveTo(projectilePos.x, projectilePos.y, projectilePos.z, this.yHeadRot, this.getXRot() + 10.0F);
               float projSpeed = 1.6F;
               if (this.getTarget() != null) {
                  float ticksUntilHit = this.targetDistance / projSpeed;
                  Vec3 targetPos = this.getTarget().position().add(new Vec3(0.0, this.getTarget().getBbHeight() / 2.0, 0.0));
                  Vec3 targetMovement = targetPos.subtract(this.prevTargetPos).scale(ticksUntilHit * 0.95);
                  targetMovement = targetMovement.subtract(0.0, targetMovement.y, 0.0);
                  Vec3 futureTargetPos = targetPos.add(targetMovement);
                  Vec3 projectileMid = projectilePos.add(new Vec3(0.0, iceBall.getBbHeight() / 2.0, 0.0));
                  Vec3 shootVec = futureTargetPos.subtract(projectileMid).normalize();
                  iceBall.shoot(shootVec.x, shootVec.y, shootVec.z, projSpeed, 0.0F);
               } else {
                  iceBall.shoot(this.getLookAngle().x, this.getLookAngle().y, this.getLookAngle().z, projSpeed, 0.0F);
               }

               if (!this.level().isClientSide) {
                  this.level().addFreshEntity(iceBall);
               }
            }
         }

         this.spawnSwipeParticles();
         if (this.fallDistance > 0.2 && !this.onGround() && this.getLastDamageSource() != null && !this.getLastDamageSource().is(DamageTypes.LAVA)) {
            this.shouldPlayLandAnimation = true;
         }

         if (this.onGround() && this.shouldPlayLandAnimation && this.getAnimation() != DODGE_ANIMATION) {
            if (!this.level().isClientSide && this.getAnimation() == NO_ANIMATION) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, LAND_ANIMATION);
            }

            this.shouldPlayLandAnimation = false;
         }

         if (this.getTarget() != null) {
            this.timeWithoutTarget = 0;
            float entityHitAngle = (float)(
               (Math.atan2(this.getTarget().getZ() - this.getZ(), this.getTarget().getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0
            );
            float entityAttackingAngle = this.yBodyRot % 360.0F;
            if (entityHitAngle < 0.0F) {
               entityHitAngle += 360.0F;
            }

            if (entityAttackingAngle < 0.0F) {
               entityAttackingAngle += 360.0F;
            }

            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            if (this.getNavigation().isDone()
               && (!(entityRelativeAngle <= 15.0F) || !(entityRelativeAngle >= -15.0F))
               && !(entityRelativeAngle >= 345.0F)
               && !(entityRelativeAngle <= -345.0F)) {
               this.getNavigation().moveTo(this.getTarget(), 0.85);
            }

            if (this.shouldDodgeMeasure >= 16) {
               this.shouldDodge = true;
            }

            if (this.getTarget().hasEffect(EffectHandler.FROZEN)) {
               this.shouldDodge = false;
            }

            if (this.targetDistance < 4.0F && this.shouldDodge && this.getAnimation() == NO_ANIMATION) {
               this.shouldDodge = false;
               this.dodgeCooldown = 200;
               this.shouldDodgeMeasure = 0;
               if (this.getHasCrystal()) {
                  this.wantsToIceBreathAfterDodging = true;
               }

               this.iceBreathCooldown = 0;
               AnimationHandler.INSTANCE.sendAnimationMessage(this, DODGE_ANIMATION);
            }

            if (this.targetDistance > 5.5 && (this.getAnimation() != ICE_BREATH_ANIMATION || !(this.targetDistance < 7.5))) {
               if (this.getAnimation() != SLAM_ANIMATION) {
                  this.getNavigation().moveTo(this.getTarget(), 1.0);
               } else {
                  this.getNavigation().moveTo(this.getTarget(), 0.95);
               }
            } else {
               this.getNavigation().stop();
            }

            if (this.targetDistance <= 8.5
               && this.getAnimation() == NO_ANIMATION
               && this.slamCooldown <= 0
               && this.random.nextInt(4) == 0
               && this.getHealthRatio() < 0.6) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, SLAM_ANIMATION);
               this.slamCooldown = 500;
            }

            if (this.targetDistance <= 6.5 && this.getAnimation() == NO_ANIMATION && !this.wantsToIceBreathAfterDodging) {
               if (this.random.nextInt(4) == 0) {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, SWIPE_TWICE_ANIMATION);
               } else {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, SWIPE_ANIMATION);
               }
            }

            if (this.targetDistance <= 13.5
               && this.getAnimation() == NO_ANIMATION
               && this.iceBreathCooldown <= 0
               && this.getHasCrystal()
               && (this.onGround() || this.wasTouchingWater)) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, ICE_BREATH_ANIMATION);
               this.iceBreathCooldown = 260;
               this.wantsToIceBreathAfterDodging = false;
            }

            if (this.targetDistance >= 14.5
               && this.getAnimation() == NO_ANIMATION
               && this.iceBallCooldown <= 0
               && this.getHasCrystal()
               && (this.onGround() || this.wasTouchingWater)) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, ICE_BALL_ANIMATION);
               this.iceBallCooldown = 200;
            }
         } else if (!this.level().isClientSide && !this.isAlwaysActive()) {
            this.timeWithoutTarget++;
            if (this.timeWithoutTarget > 1200 || this.level().getDifficulty() == Difficulty.PEACEFUL) {
               this.timeWithoutTarget = 0;
               if (this.getAnimation() == NO_ANIMATION) {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, DEACTIVATE_ANIMATION);
                  this.setActive(false);
               }
            }
         }
      } else {
         this.getNavigation().stop();
         this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
         this.yBodyRot = this.yBodyRotO;
         if (!this.level().isClientSide && this.getAnimation() != ACTIVATE_ANIMATION && (Boolean)ConfigHandler.COMMON.MOBS.FROSTMAW.healsOutOfBattle.get()) {
            this.heal(0.3F);
         }

         if (this.getTarget() != null && this.getTarget().hasEffect(MobEffects.INVISIBILITY)) {
            this.setTarget(null);
         }

         if (!this.getAttackableEntityLivingBaseNearby(8.0, 8.0, 8.0, 8.0).isEmpty()
            && this.getTarget() != null
            && this.getAnimation() == NO_ANIMATION
            && this.level().getDifficulty() != Difficulty.PEACEFUL) {
            if (this.getHasCrystal()) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_ANIMATION);
            } else {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_NO_CRYSTAL_ANIMATION);
            }

            this.setActive(true);
         }

         if ((Boolean)ConfigHandler.COMMON.MOBS.FROSTMAW.stealableIceCrystal.get()
            && this.getHasCrystal()
            && this.tickCount > 20
            && this.getAnimation() == NO_ANIMATION) {
            Vec3 crystalPos = new Vec3(1.6, 0.4, 1.8);
            crystalPos = crystalPos.yRot((float)Math.toRadians(-this.getYRot() - 90.0F));
            crystalPos = crystalPos.add(this.position());

            for (Player player : this.getPlayersNearby(8.0, 8.0, 8.0, 8.0)) {
               if (player.position().distanceTo(crystalPos) <= 1.8 && (player.isCreative() || player.isInvisible()) && !isInventoryFull(player.getInventory())) {
                  ItemHandlerHelper.giveItemToPlayer(player, new ItemStack((ItemLike)ItemHandler.ICE_CRYSTAL.get()));
                  this.setHasCrystal(false);
                  if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
                     AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_NO_CRYSTAL_ANIMATION);
                     this.setActive(true);
                  }

                  if (player instanceof ServerPlayer serverPlayer) {
                     ((StealIceCrystalTrigger)AdvancementHandler.STEAL_ICE_CRYSTAL_TRIGGER.value()).trigger(serverPlayer);
                  }
                  break;
               }
            }
         }
      }

      if (this.getAnimation() == ACTIVATE_ANIMATION || this.getAnimation() == ACTIVATE_NO_CRYSTAL_ANIMATION) {
         if (this.getAnimation() == ACTIVATE_ANIMATION && this.getAnimationTick() == 18) {
            this.playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_FROSTMAW_ATTACK.get(0)).get(), 1.5F, 1.0F);
         }

         if (this.getAnimation() == ACTIVATE_ANIMATION && this.getAnimationTick() == 52
            || this.getAnimation() == ACTIVATE_NO_CRYSTAL_ANIMATION && this.getAnimationTick() == 34) {
            this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_ROAR.get(), 4.0F, 1.0F);
            EntityCameraShake.cameraShake(this.level(), this.position(), 45.0F, 0.03F, 60, 20);
         }

         if (this.getAnimation() == ACTIVATE_ANIMATION && this.getAnimationTick() >= 51 && this.getAnimationTick() < 108
            || this.getAnimation() == ACTIVATE_NO_CRYSTAL_ANIMATION && this.getAnimationTick() >= 33 && this.getAnimationTick() < 90) {
            this.doRoarEffects();
         }
      }

      if (this.level().isClientSide && (this.getAnimation() == SWIPE_ANIMATION || this.getAnimation() == SWIPE_TWICE_ANIMATION) && this.getAnimationTick() == 1
         )
       {
         this.swingWhichArm = this.random.nextBoolean();
      }

      float moveX = (float)(this.getX() - this.xo);
      float moveZ = (float)(this.getZ() - this.zo);
      float speed = Mth.sqrt(moveX * moveX + moveZ * moveZ);
      if (this.frame % 16 == 5 && speed > 0.05 && this.active) {
         this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_STEP.get(), 3.0F, 0.8F + this.random.nextFloat() * 0.2F);
         EntityCameraShake.cameraShake(this.level(), this.position(), 20.0F, 0.03F, 0, 10);
      }

      if (this.frame % 118 == 1 && !this.active) {
         int i = Mth.nextInt(this.random, 0, 1);
         this.playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_FROSTMAW_BREATH.get(i)).get(), 1.5F, 1.1F + this.random.nextFloat() * 0.1F);
      }

      if (this.iceBreathCooldown > 0) {
         this.iceBreathCooldown--;
      }

      if (this.iceBallCooldown > 0) {
         this.iceBallCooldown--;
      }

      if (this.slamCooldown > 0) {
         this.slamCooldown--;
      }

      if (this.shouldDodgeMeasure > 0 && this.tickCount % 7 == 0) {
         this.shouldDodgeMeasure--;
      }

      if (this.dodgeCooldown > 0) {
         this.dodgeCooldown--;
      }

      this.yRotO = this.getYRot();
   }

   private void doRoarEffects() {
      if (this.getHasCrystal()) {
         for (LivingEntity entity : this.getEntityLivingBaseNearby(10.0, 3.0, 10.0, 10.0)) {
            if (entity != this) {
               double angle = (this.getAngleBetweenEntities(this, entity) + 90.0) * Math.PI / 180.0;
               double distance = this.distanceTo(entity) - 4.0F;
               entity.setDeltaMovement(
                  entity.getDeltaMovement()
                     .add(
                        Math.min(1.0 / (distance * distance), 1.0) * -1.0 * Math.cos(angle),
                        0.0,
                        Math.min(1.0 / (distance * distance), 1.0) * -1.0 * Math.sin(angle)
                     )
               );
            }
         }

         if (this.getAnimationTick() % 12 == 0 && this.level().isClientSide) {
            int particleCount = 15;

            for (int i = 1; i <= particleCount; i++) {
               double yaw = i * 360.0F / particleCount;
               double speed = 0.9;
               double xSpeed = speed * Math.cos(Math.toRadians(yaw));
               double zSpeed = speed * Math.sin(Math.toRadians(yaw));
               this.level()
                  .addParticle(
                     new ParticleCloud.Data(0.75F, 0.75F, 1.0F, 40.0F, 22, ParticleCloud.EnumCloudBehavior.GROW, 1.0F),
                     this.getX(),
                     this.getY() + 1.0,
                     this.getZ(),
                     xSpeed,
                     0.0,
                     zSpeed
                  );
            }

            for (int i = 1; i <= particleCount; i++) {
               double yaw = i * 360.0F / particleCount;
               double speed = 0.65;
               double xSpeed = speed * Math.cos(Math.toRadians(yaw));
               double zSpeed = speed * Math.sin(Math.toRadians(yaw));
               this.level()
                  .addParticle(
                     new ParticleCloud.Data(0.75F, 0.75F, 1.0F, 35.0F, 22, ParticleCloud.EnumCloudBehavior.GROW, 1.0F),
                     this.getX(),
                     this.getY() + 1.0,
                     this.getZ(),
                     xSpeed,
                     0.0,
                     zSpeed
                  );
            }
         }
      }
   }

   private static boolean isInventoryFull(Inventory inventory) {
      for (ItemStack itemstack : inventory.items) {
         if (itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      for (LivingEntity nearbyEntity : this.getEntityLivingBaseNearby(100.0, 100.0, 100.0, 100.0)) {
         if (nearbyEntity instanceof EntityFrostmaw || nearbyEntity instanceof Villager) {
            return false;
         }
      }

      return super.checkSpawnRules(world, reason);
   }

   public int getMaxSpawnClusterSize() {
      return 1;
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingData) {
      this.setHasCrystal(true);
      return super.finalizeSpawn(world, difficulty, reason, livingData);
   }

   private void spawnSwipeParticles() {
      if (this.level().isClientSide && this.getHasCrystal()) {
         double motionX = this.getDeltaMovement().x();
         double motionY = this.getDeltaMovement().y();
         double motionZ = this.getDeltaMovement().z();
         int snowflakeDensity = 4;
         float snowflakeRandomness = 0.5F;
         int cloudDensity = 2;
         float cloudRandomness = 0.5F;
         if (this.getAnimation() == SWIPE_ANIMATION || this.getAnimation() == SWIPE_TWICE_ANIMATION) {
            Vec3 rightHandPos = this.socketPosArray[0];
            Vec3 leftHandPos = this.socketPosArray[1];
            if (this.getAnimation() == SWIPE_ANIMATION) {
               if (this.getAnimationTick() > 8 && this.getAnimationTick() < 14) {
                  if (this.swingWhichArm) {
                     double length = this.prevRightHandPos.subtract(rightHandPos).length();
                     int numClouds = (int)Math.floor(2.0 * length);

                     for (int i = 0; i < numClouds; i++) {
                        double x = this.prevRightHandPos.x + i * (rightHandPos.x - this.prevRightHandPos.x) / numClouds;
                        double y = this.prevRightHandPos.y + i * (rightHandPos.y - this.prevRightHandPos.y) / numClouds;
                        double z = this.prevRightHandPos.z + i * (rightHandPos.z - this.prevRightHandPos.z) / numClouds;

                        for (int j = 0; j < snowflakeDensity; j++) {
                           float xOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           float yOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           float zOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           this.level()
                              .addParticle(new ParticleSnowFlake.Data(40.0F, false), x + xOffset, y + yOffset, z + zOffset, motionX, motionY - 0.01F, motionZ);
                        }

                        for (int j = 0; j < cloudDensity; j++) {
                           float xOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           float yOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           float zOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           float value = this.random.nextFloat() * 0.1F;
                           this.level()
                              .addParticle(
                                 new ParticleCloud.Data(
                                    0.8F + value,
                                    0.8F + value,
                                    1.0F,
                                    (float)(10.0 + this.random.nextDouble() * 10.0),
                                    40,
                                    ParticleCloud.EnumCloudBehavior.SHRINK,
                                    1.0F
                                 ),
                                 x + xOffset,
                                 y + yOffset,
                                 z + zOffset,
                                 motionX,
                                 motionY,
                                 motionZ
                              );
                        }
                     }
                  } else {
                     double length = this.prevLeftHandPos.subtract(leftHandPos).length();
                     int numClouds = (int)Math.floor(2.5 * length);

                     for (int i = 0; i < numClouds; i++) {
                        double x = this.prevLeftHandPos.x + i * (leftHandPos.x - this.prevLeftHandPos.x) / numClouds;
                        double y = this.prevLeftHandPos.y + i * (leftHandPos.y - this.prevLeftHandPos.y) / numClouds;
                        double z = this.prevLeftHandPos.z + i * (leftHandPos.z - this.prevLeftHandPos.z) / numClouds;

                        for (int j = 0; j < snowflakeDensity; j++) {
                           float xOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           float yOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           float zOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           this.level()
                              .addParticle(new ParticleSnowFlake.Data(40.0F, false), x + xOffset, y + yOffset, z + zOffset, motionX, motionY - 0.01F, motionZ);
                        }

                        for (int j = 0; j < cloudDensity; j++) {
                           float xOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           float yOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           float zOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                           float value = this.random.nextFloat() * 0.1F;
                           this.level()
                              .addParticle(
                                 new ParticleCloud.Data(
                                    0.8F + value,
                                    0.8F + value,
                                    1.0F,
                                    (float)(10.0 + this.random.nextDouble() * 10.0),
                                    40,
                                    ParticleCloud.EnumCloudBehavior.SHRINK,
                                    1.0F
                                 ),
                                 x + xOffset,
                                 y + yOffset,
                                 z + zOffset,
                                 motionX,
                                 motionY,
                                 motionZ
                              );
                        }
                     }
                  }
               }
            } else if ((!this.swingWhichArm || this.getAnimationTick() <= 8 || this.getAnimationTick() >= 14)
               && (this.swingWhichArm || this.getAnimationTick() <= 19 || this.getAnimationTick() >= 25)) {
               if (!this.swingWhichArm && this.getAnimationTick() > 8 && this.getAnimationTick() < 14
                  || this.swingWhichArm && this.getAnimationTick() > 19 && this.getAnimationTick() < 25) {
                  double length = this.prevLeftHandPos.subtract(leftHandPos).length();
                  int numClouds = (int)Math.floor(2.5 * length);

                  for (int i = 0; i < numClouds; i++) {
                     double x = this.prevLeftHandPos.x + i * (leftHandPos.x - this.prevLeftHandPos.x) / numClouds;
                     double y = this.prevLeftHandPos.y + i * (leftHandPos.y - this.prevLeftHandPos.y) / numClouds;
                     double z = this.prevLeftHandPos.z + i * (leftHandPos.z - this.prevLeftHandPos.z) / numClouds;

                     for (int j = 0; j < snowflakeDensity; j++) {
                        float xOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                        float yOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                        float zOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                        this.level()
                           .addParticle(new ParticleSnowFlake.Data(40.0F, false), x + xOffset, y + yOffset, z + zOffset, motionX, motionY - 0.01F, motionZ);
                     }

                     for (int j = 0; j < cloudDensity; j++) {
                        float xOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                        float yOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                        float zOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                        float value = this.random.nextFloat() * 0.1F;
                        this.level()
                           .addParticle(
                              new ParticleCloud.Data(
                                 0.8F + value,
                                 0.8F + value,
                                 1.0F,
                                 (float)(10.0 + this.random.nextDouble() * 10.0),
                                 40,
                                 ParticleCloud.EnumCloudBehavior.SHRINK,
                                 1.0F
                              ),
                              x + xOffset,
                              y + yOffset,
                              z + zOffset,
                              motionX,
                              motionY,
                              motionZ
                           );
                     }
                  }
               }
            } else {
               double length = this.prevRightHandPos.subtract(rightHandPos).length();
               int numClouds = (int)Math.floor(2.0 * length);

               for (int i = 0; i < numClouds; i++) {
                  double x = this.prevRightHandPos.x + i * (rightHandPos.x - this.prevRightHandPos.x) / numClouds;
                  double y = this.prevRightHandPos.y + i * (rightHandPos.y - this.prevRightHandPos.y) / numClouds;
                  double z = this.prevRightHandPos.z + i * (rightHandPos.z - this.prevRightHandPos.z) / numClouds;

                  for (int j = 0; j < snowflakeDensity; j++) {
                     float xOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                     float yOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                     float zOffset = snowflakeRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                     this.level()
                        .addParticle(new ParticleSnowFlake.Data(40.0F, false), x + xOffset, y + yOffset, z + zOffset, motionX, motionY - 0.01F, motionZ);
                  }

                  for (int j = 0; j < cloudDensity; j++) {
                     float xOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                     float yOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                     float zOffset = cloudRandomness * (2.0F * this.random.nextFloat() - 1.0F);
                     float value = this.random.nextFloat() * 0.1F;
                     this.level()
                        .addParticle(
                           new ParticleCloud.Data(
                              0.8F + value,
                              0.8F + value,
                              1.0F,
                              (float)(10.0 + this.random.nextDouble() * 10.0),
                              40,
                              ParticleCloud.EnumCloudBehavior.SHRINK,
                              1.0F
                           ),
                           x + xOffset,
                           y + yOffset,
                           z + zOffset,
                           motionX,
                           motionY,
                           motionZ
                        );
                  }
               }
            }

            this.prevLeftHandPos = leftHandPos;
            this.prevRightHandPos = rightHandPos;
         }
      }
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if (source.is(DamageTypeTags.IS_FALL)) {
         return false;
      }

      if (source.is(DamageTypes.LAVA) && this.getAnimation() == NO_ANIMATION) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this, DODGE_ANIMATION);
      }

      if (source.is(DamageTypeTags.IS_FIRE)) {
         damage = (float)(damage * 1.25);
      }

      if (source.getDirectEntity() instanceof AbstractArrow) {
         this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), 0.4F, 2.0F);
         Entity entity = source.getEntity();
         if (entity != null
            && entity instanceof LivingEntity
            && (!(entity instanceof Player) || !((Player)entity).isCreative())
            && this.getTarget() == null
            && !(entity instanceof EntityFrostmaw)) {
            this.setTarget((LivingEntity)entity);
         }

         if (!this.getActive()) {
            if (this.getAnimation() != DIE_ANIMATION && this.level().getDifficulty() != Difficulty.PEACEFUL) {
               if (this.getHasCrystal()) {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_ANIMATION);
               } else {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_NO_CRYSTAL_ANIMATION);
               }
            }

            if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
               this.setActive(true);
            }
         }

         return false;
      } else {
         boolean attack = super.hurt(source, damage);
         if (attack) {
            this.shouldDodgeMeasure = (int)(this.shouldDodgeMeasure + damage);
            Entity entity = source.getEntity();
            if (entity != null
               && entity instanceof LivingEntity
               && (!(entity instanceof Player) || !((Player)entity).isCreative())
               && this.getTarget() == null
               && !(entity instanceof EntityFrostmaw)) {
               this.setTarget((LivingEntity)entity);
            }

            if (!this.getActive()) {
               if (this.getAnimation() != DIE_ANIMATION && this.level().getDifficulty() != Difficulty.PEACEFUL) {
                  if (this.getHasCrystal()) {
                     AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_ANIMATION);
                  } else {
                     AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_NO_CRYSTAL_ANIMATION);
                  }
               }

               if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
                  this.setActive(true);
               }
            }
         }

         return attack;
      }
   }

   @Override
   protected void tickDeath() {
      super.tickDeath();
      if (this.getAnimationTick() == 5) {
         this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_DIE.get(), 2.5F, 1.0F);
      } else if (this.getAnimationTick() == 53) {
         this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_LAND.get(), 2.5F, 1.0F);
      }
   }

   @Override
   public Animation getDeathAnimation() {
      return DIE_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return HURT_ANIMATION;
   }

   public void setActive(boolean active) {
      this.getEntityData().set(ACTIVE, active);
   }

   public boolean getActive() {
      this.active = (Boolean)this.getEntityData().get(ACTIVE);
      return this.active;
   }

   public void setHasCrystal(boolean hasCrystal) {
      this.getEntityData().set(HAS_CRYSTAL, hasCrystal);
   }

   public boolean getHasCrystal() {
      return (Boolean)this.getEntityData().get(HAS_CRYSTAL);
   }

   public boolean isAlwaysActive() {
      return (Boolean)this.getEntityData().get(ALWAYS_ACTIVE);
   }

   public void setAlwaysActive(boolean isAlwaysActive) {
      this.getEntityData().set(ALWAYS_ACTIVE, isAlwaysActive);
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         DIE_ANIMATION,
         HURT_ANIMATION,
         ROAR_ANIMATION,
         SWIPE_ANIMATION,
         SWIPE_TWICE_ANIMATION,
         ICE_BREATH_ANIMATION,
         ICE_BALL_ANIMATION,
         ACTIVATE_ANIMATION,
         ACTIVATE_NO_CRYSTAL_ANIMATION,
         DEACTIVATE_ANIMATION,
         SLAM_ANIMATION,
         LAND_ANIMATION,
         DODGE_ANIMATION
      };
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("has_crystal")) {
         this.setHasCrystal(compound.getBoolean("has_crystal"));
      }

      this.setActive(compound.getBoolean("active"));
      this.setAlwaysActive(compound.getBoolean("alwaysActive"));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("has_crystal", this.getHasCrystal());
      compound.putBoolean("active", this.getActive());
      compound.putBoolean("alwaysActive", this.isAlwaysActive());
   }

   public boolean requiresCustomPersistence() {
      return this.getHasCrystal();
   }

   @Override
   public boolean hasBossBar() {
      return (Boolean)ConfigHandler.COMMON.MOBS.FROSTMAW.hasBossBar.get();
   }

   @Override
   public BossBarColor bossBarColor() {
      return BossBarColor.WHITE;
   }

   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LootTableHandler.FROSTMAW;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.FROSTMAW.combatConfig;
   }

   public Vec3 getDeltaMovement() {
      return !this.getActive() ? super.getDeltaMovement().multiply(0.0, 1.0, 0.0) : super.getDeltaMovement();
   }

   @Override
   public boolean hasBossMusic() {
      return true;
   }

   @Override
   public BossMusic<?> getBossMusic() {
      return BossMusicPlayer.FROSTMAW_MUSIC;
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && (this.active || this.getAnimation() == ACTIVATE_ANIMATION);
   }

   @Override
   public boolean resetHealthOnPlayerRespawn() {
      return (Boolean)ConfigHandler.COMMON.MOBS.FROSTMAW.resetHealthWhenRespawn.get();
   }
}
