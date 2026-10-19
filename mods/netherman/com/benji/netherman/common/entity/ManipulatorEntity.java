package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ManipulatorEntity extends Monster implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(ManipulatorEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> HEALTH_PHASE = SynchedEntityData.defineId(ManipulatorEntity.class, EntityDataSerializers.INT);
   public static final int STATE_IDLE = 0;
   public static final int STATE_WALK = 1;
   public static final int STATE_RUN = 2;
   public static final int STATE_ATTACK = 3;
   public static final int STATE_ATTACK_LOOP = 4;
   public int castTicks = 0;
   public int fleeTicks = 0;
   public int manipulationCooldown = 0;
   private boolean guardianSpawned = false;

   public ManipulatorEntity(EntityType<? extends Monster> entityType, Level level) {
      super(entityType, level);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 60.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.FOLLOW_RANGE, 20.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(STATE, 0);
      builder.define(HEALTH_PHASE, 0);
   }

   public int getEntityState() {
      return (Integer)this.entityData.get(STATE);
   }

   public void setEntityState(int state) {
      if (state == 3 && this.getEntityState() != 3 && !this.level().isClientSide()) {
         this.level().broadcastEntityEvent(this, (byte)60);
         SoundEvent summonSound = this.random.nextBoolean() ? (SoundEvent)ModSounds.SUMMON1.get() : (SoundEvent)ModSounds.SUMMON2.get();
         this.playSound(summonSound, 1.5F, this.getVoicePitch());
      }

      this.entityData.set(STATE, state);
   }

   public int getHealthPhase() {
      return (Integer)this.entityData.get(HEALTH_PHASE);
   }

   public void setHealthPhase(int phase) {
      this.entityData.set(HEALTH_PHASE, phase);
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      if (this.random.nextInt(3) == 0) {
         this.playSound(SoundEvents.WITHER_SKELETON_STEP, 0.5F, this.getVoicePitch());
      } else {
         super.playStepSound(pos, blockIn);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         if (this.manipulationCooldown > 0) {
            this.manipulationCooldown--;
         }

         if (this.castTicks > 0) {
            this.castTicks--;
            this.getNavigation().stop();
            if (this.castTicks == 0) {
               if (this.getEntityState() == 3) {
                  this.setEntityState(0);
               } else if (this.getEntityState() == 4) {
                  this.setEntityState(0);
               }
            }
         }

         if (this.fleeTicks > 0) {
            this.fleeTicks--;
            if (this.fleeTicks == 0 && this.getEntityState() == 2) {
               this.setEntityState(0);
            }
         }
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      boolean flag = super.hurt(source, amount);
      if (flag && !this.level().isClientSide()) {
         float health = this.getHealth();
         if (health <= 40.0F && health > 20.0F && this.getHealthPhase() == 0) {
            this.setHealthPhase(1);
            this.triggerPhaseEffects();
         } else if (health <= 20.0F && health > 0.0F && this.getHealthPhase() == 1) {
            this.setHealthPhase(2);
            this.triggerPhaseEffects();
         }

         if (health <= 10.0F && !this.guardianSpawned) {
            this.guardianSpawned = true;
            this.setEntityState(3);
            this.castTicks = 35;
            this.spawnGuardianCompanion();
         }

         if (source.getEntity() instanceof Player && health > 10.0F) {
            this.fleeTicks = 60;
            this.setEntityState(2);
         }
      }

      return flag;
   }

   private void triggerPhaseEffects() {
      this.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.5F, 0.7F);

      for (int i = 0; i < 20; i++) {
         this.level().addParticle(ParticleTypes.DAMAGE_INDICATOR, this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0.0, 0.1, 0.0);
      }
   }

   private void spawnGuardianCompanion() {
      BlockPos spawnPos = this.blockPosition().relative(this.getDirection().getOpposite(), 2);
      GuardianEntity guardian = (GuardianEntity)((EntityType)ModEntities.GUARDIAN.get()).create(this.level());
      if (guardian != null) {
         guardian.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, this.getYRot(), 0.0F);
         if (this.getTarget() != null) {
            guardian.setTarget(this.getTarget());
         }

         guardian.startSpawning();
         this.level().addFreshEntity(guardian);
      }
   }

   public void spawnWitherSkeletons() {
      int count = this.random.nextInt(2) + 1;

      for (int i = 0; i < count; i++) {
         BlockPos p = this.blockPosition().offset(this.random.nextInt(7) - 3, 0, this.random.nextInt(7) - 3);
         WitherSkeleton skeleton = (WitherSkeleton)EntityType.WITHER_SKELETON.create(this.level());
         if (skeleton != null) {
            skeleton.moveTo(p.getX() + 0.5, p.getY(), p.getZ() + 0.5, 0.0F, 0.0F);
            skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
            skeleton.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
            skeleton.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
            skeleton.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
            skeleton.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
            skeleton.setDropChance(EquipmentSlot.HEAD, 0.0F);
            skeleton.setDropChance(EquipmentSlot.CHEST, 0.0F);
            skeleton.setDropChance(EquipmentSlot.LEGS, 0.0F);
            skeleton.setDropChance(EquipmentSlot.FEET, 0.0F);
            if (this.getTarget() != null) {
               skeleton.setTarget(this.getTarget());
            }

            this.level().addFreshEntity(skeleton);

            for (int j = 0; j < 4; j++) {
               this.level().addParticle(ParticleTypes.CRIMSON_SPORE, skeleton.getRandomX(0.5), skeleton.getRandomY(), skeleton.getRandomZ(0.5), 0.0, 0.05, 0.0);
            }
         }
      }
   }

   public void handleEntityEvent(byte id) {
      if (id == 60) {
         for (int i = 0; i < 360; i += 30) {
            double rad = Math.toRadians(i);
            double x = Math.cos(rad) * 1.5;
            double z = Math.sin(rad) * 1.5;
            double yOffset = this.random.nextDouble();
            this.level().addParticle(DustParticleOptions.REDSTONE, this.getX() + x, this.getY() + 0.1 + yOffset, this.getZ() + z, 0.0, 0.1, 0.0);
         }
      } else {
         super.handleEntityEvent(id);
      }
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new ManipulatorConvertGoal(this));
      this.goalSelector.addGoal(2, new ManipulatorAttackGoal(this));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 5, event -> {
         int state = this.getEntityState();
         if (state == 3) {
            return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
         } else if (state == 4) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("attack_loop"));
         } else if (this.walkAnimation.isMoving()) {
            return state == 2 ? event.setAndContinue(RawAnimation.begin().thenLoop("run")) : event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
         } else {
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
         }
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
