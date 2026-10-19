package net.jadenxgamer.netherexp.core.entity;

import java.awt.Color;
import java.util.EnumSet;
import java.util.Set;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.jadenxgamer.netherexp.util.VFXHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class Vessel extends PossessedMob implements RangedAttackMob {
   public final AnimationState idleAnimation = new AnimationState();
   public final AnimationState prepareAimAnimation = new AnimationState();
   public final AnimationState aimAnimation = new AnimationState();
   public final AnimationState shootAnimation = new AnimationState();
   public final AnimationState blinkAnimation = new AnimationState();
   private static final EntityDataAccessor<Boolean> IS_DOOM = SynchedEntityData.defineId(Vessel.class, EntityDataSerializers.BOOLEAN);
   private static final Set<String> DOOM_VALID_NAMES = Set.of("DOOM", "ShotgunGuy", "Shotgun Guy");
   private int idleAnimationTimeout = 0;
   private int prepareAimAnimationTimer = 20;
   private boolean isAiming = false;
   private boolean isShooting = false;
   public boolean armSmoke = false;
   public boolean armFlash = false;

   public Vessel(EntityType<? extends PathfinderMob> entityType, Level level) {
      super(entityType, level, NetherExp.minecraftPath("skeleton"));
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.LAVA, 8.0F);
      this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
      this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 24.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.FOLLOW_RANGE, 16.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(3, new Vessel.VesselAttackGoal(this));
      this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Piglin.class, true));
   }

   public void performRangedAttack(LivingEntity target, float velocity) {
      this.performRangedAttack(target.position());
   }

   public void performRangedAttack(Vec3 fireDirection) {
      SoundEvent fireSound = this.getFireSound();
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), fireSound, this.getSoundSource(), 1.0F, 1.0F);
      int count = Mth.nextInt(this.level().random, (Integer)JNEConfigs.MIN_VESSEL_BULLETS.get(), (Integer)JNEConfigs.MAX_VESSEL_BULLETS.get());
      Vec3 lookVector = this.getLookAngle();
      double xVector = fireDirection.x - this.getX();
      double zVector = fireDirection.z - this.getZ();

      for (int i = 0; i < count; i++) {
         ShotgunPellet soulBullet = new ShotgunPellet(this.getX(), this.getY() + 1.5, this.getZ(), this.level(), this);
         soulBullet.shoot(xVector, lookVector.y, zVector, 1.0F, 16.0F);
         this.level().addFreshEntity(soulBullet);
      }
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         this.setupAnimationStates();
      }
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(IS_DOOM, false);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag nbt) {
      super.addAdditionalSaveData(nbt);
      nbt.putBoolean("IsDoom", this.isDoom());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      this.setDoom(nbt.getBoolean("IsDoom"));
   }

   @Override
   public int apparitionPersonality() {
      return 1;
   }

   @Override
   protected double apparitionUnleashingOdds() {
      return (Double)JNEConfigs.VESSEL_UNLEASHING_ODDS.get();
   }

   @Override
   public void handleEntityEvent(byte id) {
      switch (id) {
         case 81:
            this.prepareAimAnimationTimer = 20;
            this.isAiming = true;
            break;
         case 82:
            this.prepareAimAnimation.stop();
            this.aimAnimation.stop();
            this.prepareAimAnimationTimer = 20;
            this.isAiming = false;
            break;
         case 83:
            this.isShooting = true;
            this.armSmoke = true;
            this.armFlash = true;
            VFXHelper.shotgunScreenShake(this.position(), 8.0F, Easing.LINEAR);
            break;
         case 84:
            this.isShooting = false;
            this.armSmoke = false;
            this.shootAnimation.stop();
            break;
         case 85:
            Vessel.Client.accurateShotParticle(this.level(), this, this.random, 0.35F, 1.0F, 2.0F, new Color(16777215), true);
            Vessel.Client.accurateShotParticle(this.level(), this, this.random, 0.65F, 1.0F, 1.5F, new Color(16635971), true);
      }

      super.handleEntityEvent(id);
   }

   public boolean isDoom() {
      return (Boolean)this.entityData.get(IS_DOOM);
   }

   public void setDoom(boolean doom) {
      this.entityData.set(IS_DOOM, doom);
   }

   public void setCustomName(@Nullable Component name) {
      super.setCustomName(name);
      boolean doomValidNames = name != null && DOOM_VALID_NAMES.stream().anyMatch(validName -> validName.equalsIgnoreCase(name.getString()));
      if (!this.isDoom() && doomValidNames) {
         this.setDoom(true);
      }
   }

   private void setupAnimationStates() {
      if (this.idleAnimationTimeout <= 0) {
         this.idleAnimationTimeout = 40;
         this.idleAnimation.startIfStopped(this.tickCount);
      } else {
         this.idleAnimationTimeout--;
      }

      if (this.isAiming) {
         if (this.prepareAimAnimationTimer == 20) {
            this.prepareAimAnimation.startIfStopped(this.tickCount);
         } else if (this.prepareAimAnimationTimer == 0) {
            this.prepareAimAnimation.stop();
            this.aimAnimation.startIfStopped(this.tickCount);
         }

         if (this.prepareAimAnimationTimer > 0) {
            this.prepareAimAnimationTimer--;
         }
      }

      if (this.isShooting) {
         this.shootAnimation.startIfStopped(this.tickCount);
      } else if (this.random.nextInt(8) == 0) {
         this.blinkAnimation.startIfStopped(this.tickCount);
      }
   }

   protected void updateWalkAnimation(float partialTick) {
      float f;
      if (this.getPose() == Pose.STANDING) {
         f = Math.min(partialTick * 6.0F, 1.0F);
      } else {
         f = 0.0F;
      }

      this.walkAnimation.update(f, 0.2F);
   }

   protected SoundEvent getAmbientSound() {
      return this.isDoom() ? JNESoundEvents.SHOTGUN_GUY_AMBIENT.get() : JNESoundEvents.VESSEL_AMBIENT.get();
   }

   protected void playWarnSound() {
      if (!this.isDoom()) {
         this.level().playSound(null, this.blockPosition(), JNESoundEvents.VESSEL_WARN.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
      }
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return this.isDoom() ? JNESoundEvents.SHOTGUN_GUY_HURT.get() : JNESoundEvents.VESSEL_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return this.isDoom() ? JNESoundEvents.SHOTGUN_GUY_DEATH.get() : JNESoundEvents.VESSEL_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);
   }

   protected SoundEvent getFireSound() {
      return this.isDoom() ? JNESoundEvents.SHOTGUN_GUY_FIRE.get() : JNESoundEvents.VESSEL_FIRE.get();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      public static void cooldownParticle(Level level, RandomSource random, double x, double y, double z) {
         LodestoneWorldParticleType particle = CommonParticles.SMOKE_VARIANTS[random.nextInt(CommonParticles.SMOKE_VARIANTS.length)];
         Color startColor = new Color(1812914);
         Color endColor = new Color(1123105);
         WorldParticleBuilder.create(particle)
            .setFullBrightLighting()
            .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.33F, 0.43F), 0.75F).build())
            .setTransparencyData(GenericParticleData.create(0.9F, 0.4F, 0.0F).setEasing(Easing.BOUNCE_OUT).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setColorData(ColorParticleData.create(startColor, endColor).setEasing(Easing.SINE_IN_OUT).build())
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(Mth.randomBetweenInclusive(random, 10, 15))
            .enableNoClip()
            .addMotion(0.0 + random.nextDouble() / 10.0, 0.0 + random.nextDouble() / 64.0, 0.0 + random.nextDouble() / 10.0)
            .spawn(level, x, y, z);
      }

      public static void shotgunFlashParticle(Level level, RandomSource random, double x, double y, double z) {
         LodestoneWorldParticleType particle = JNEParticleTypes.SHOTGUN_FLASH.get();
         WorldParticleBuilder.create(particle)
            .setFullBrightLighting()
            .setScaleData(GenericParticleData.create(0.01F, 1.45F, 0.0F).build())
            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 2.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
            .setTransparencyData(GenericParticleData.create(1.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(5)
            .enableNoClip()
            .addMotion(0.0, 0.0, 0.0)
            .spawn(level, x, y + 0.75, z);
      }

      public static void accurateShotParticle(
         Level level, Vessel vessel, RandomSource random, float scale, float translucency, float spinCoefficient, Color color, boolean additive
      ) {
         double[] offsets = new double[]{0.25, -0.22, 0.2};
         Vec3 eyePos = vessel.getEyePosition();
         Vec3 lookAngle = vessel.getLookAngle();
         Vec3 startPos = eyePos.add(0.0, offsets[2], 0.0)
            .add(lookAngle.normalize().scale(offsets[0]))
            .add(new Vec3(0.0, 1.0, 0.0).cross(lookAngle).normalize().scale(offsets[1]));
         WorldParticleBuilder.create(JNEParticleTypes.SPARKLE.get())
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 2.0F).setCoefficient(spinCoefficient).setEasing(Easing.SINE_IN).build())
            .setScaleData(GenericParticleData.create(0.01F, scale, 0.0F).setCoefficient(1.4F).setEasing(Easing.SINE_IN_OUT).build())
            .setTransparencyData(GenericParticleData.create(translucency).build())
            .setRenderType(additive ? LodestoneWorldParticleRenderType.ADDITIVE : LodestoneWorldParticleRenderType.TRANSPARENT)
            .setColorData(ColorParticleData.create(color).build())
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(20)
            .enableNoClip()
            .setForceSpawn(true)
            .addMotion(0.0, 0.0, 0.0)
            .addTickActor(
               actor -> {
                  Vec3 currentEyePos = vessel.getEyePosition();
                  Vec3 currentLookAngle = vessel.getLookAngle();
                  Vec3 offsetPos = currentEyePos.add(0.0, offsets[2], 0.0)
                     .add(currentLookAngle.normalize().scale(offsets[0]))
                     .add(new Vec3(0.0, 1.0, 0.0).cross(currentLookAngle).normalize().scale(offsets[1]));
                  actor.setPos(offsetPos.x, offsetPos.y, offsetPos.z);
               }
            )
            .spawn(level, startPos.x, startPos.y, startPos.z);
      }
   }

   private static class VesselAttackGoal extends Goal {
      private final Vessel vessel;
      private int attackTime;
      private boolean finished;
      private int seeTime;
      private boolean strafingClockwise;
      private boolean strafingBackwards;
      private int strafingTime;
      private Vec3 targetPos;
      private static int ATTACK_INTERVAL = (Integer)JNEConfigs.VESSEL_ATTACK_TIME.get();
      private static int START_AIM_AT_TICK = (Integer)JNEConfigs.VESSEL_SHOOTS_AT_ATTACK_TIME.get();
      private static int WARN_AT_TICK = START_AIM_AT_TICK - 30;
      private static int SHOOT_AT_TICK = START_AIM_AT_TICK - 60;

      private VesselAttackGoal(Vessel vessel) {
         this.vessel = vessel;
         this.attackTime = ATTACK_INTERVAL;
         this.strafingTime = -1;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         LivingEntity target = this.vessel.getTarget();
         return target != null && target.isAlive() && this.vessel.canAttack(target);
      }

      public boolean canContinueToUse() {
         return !this.finished && super.canContinueToUse();
      }

      public void start() {
         this.attackTime = ATTACK_INTERVAL;
         super.start();
      }

      public void stop() {
         this.attackTime = ATTACK_INTERVAL;
         this.vessel.level().broadcastEntityEvent(this.vessel, (byte)82);
         this.vessel.level().broadcastEntityEvent(this.vessel, (byte)84);
         this.finished = false;
         this.seeTime = 0;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         super.tick();
         LivingEntity target = this.vessel.getTarget();
         if (target != null) {
            boolean hasSight = this.vessel.getSensing().hasLineOfSight(target);
            double distanceFromTarget = this.vessel.distanceToSqr(target);
            double attackRadius = this.getFollowDistance() * this.getFollowDistance();
            boolean hadSightLastTick = this.seeTime > 0;
            if (hasSight != hadSightLastTick) {
               this.seeTime = 0;
            }

            if (hasSight) {
               this.seeTime++;
            } else {
               this.seeTime--;
            }

            if (this.attackTime > SHOOT_AT_TICK) {
               if (!(distanceFromTarget > this.getFollowDistance()) && this.seeTime >= 20) {
                  this.vessel.getNavigation().stop();
                  this.strafingTime++;
               } else {
                  this.vessel.getNavigation().moveTo(target, 1.0);
                  this.strafingTime = -1;
               }

               if (this.strafingTime >= 20) {
                  if (this.vessel.getRandom().nextFloat() < 0.3) {
                     this.strafingClockwise = !this.strafingClockwise;
                  }

                  if (this.vessel.getRandom().nextFloat() < 0.3) {
                     this.strafingBackwards = !this.strafingBackwards;
                  }

                  this.strafingTime = 0;
               }

               if (this.strafingTime > -1) {
                  if (distanceFromTarget > attackRadius * 0.75) {
                     this.strafingBackwards = false;
                  } else if (distanceFromTarget < attackRadius * 0.25) {
                     this.strafingBackwards = true;
                  }

                  this.vessel.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                  if (this.vessel.getControlledVehicle() instanceof Mob vehicleMob) {
                     vehicleMob.lookAt(target, 30.0F, 30.0F);
                  }

                  this.vessel.lookAt(target, 30.0F, 30.0F);
               } else {
                  this.vessel.getLookControl().setLookAt(target, 30.0F, 30.0F);
               }
            } else {
               this.vessel.getNavigation().stop();
               this.vessel.stopInPlace();
               this.vessel.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            if (distanceFromTarget < attackRadius) {
               boolean accurateHitDistance = distanceFromTarget
                  <= (Integer)JNEConfigs.VESSEL_ACCURATE_DISTANCE.get() * (Integer)JNEConfigs.VESSEL_ACCURATE_DISTANCE.get();
               if (this.attackTime > SHOOT_AT_TICK + 10) {
                  if (hasSight) {
                     this.attackTime--;
                  }
               } else {
                  this.attackTime--;
               }

               if (this.attackTime > SHOOT_AT_TICK + 2) {
                  this.targetPos = target.position();
               }

               if (this.attackTime == SHOOT_AT_TICK + 10 && accurateHitDistance) {
                  this.vessel.level().broadcastEntityEvent(this.vessel, (byte)85);
                  this.vessel.level().playSound(null, this.vessel.blockPosition(), SoundEvents.FIRECHARGE_USE, this.vessel.getSoundSource(), 1.0F, 2.0F);
               }

               if (this.attackTime == START_AIM_AT_TICK) {
                  this.vessel.level().broadcastEntityEvent(this.vessel, (byte)81);
               }

               if (this.attackTime == WARN_AT_TICK) {
                  this.vessel.playWarnSound();
               }

               if (this.attackTime == SHOOT_AT_TICK) {
                  this.vessel.level().broadcastEntityEvent(this.vessel, (byte)82);
                  this.vessel.level().broadcastEntityEvent(this.vessel, (byte)83);
                  if (accurateHitDistance) {
                     this.vessel.performRangedAttack(target.position());
                  } else {
                     this.vessel.performRangedAttack(this.targetPos);
                  }
               }

               if (this.attackTime == 0) {
                  this.finished = true;
               }
            }
         }
      }

      private double getFollowDistance() {
         return this.vessel.getAttributeValue(Attributes.FOLLOW_RANGE);
      }
   }
}
