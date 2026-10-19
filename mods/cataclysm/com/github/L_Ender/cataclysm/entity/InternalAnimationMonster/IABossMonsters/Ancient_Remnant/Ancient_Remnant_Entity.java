package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant;

import com.github.L_Ender.cataclysm.blockentities.Boss_Respawn_Spawner_Block_Entity;
import com.github.L_Ender.cataclysm.client.particle.Options.RingParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.RoarParticleOptions;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AI.HurtByNearestTargetGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.IABoss_monster;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Ancient_Desert_Stele_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.EarthQuake_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import com.github.L_Ender.lionfishapi.server.animation.LegSolver;
import com.github.L_Ender.lionfishapi.server.animation.LegSolver.Leg;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

public class Ancient_Remnant_Entity extends IABoss_monster {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState sandstormroarAnimationState = new AnimationState();
   public AnimationState phaseroarAnimationState = new AnimationState();
   public AnimationState sleepAnimationState = new AnimationState();
   public AnimationState awakenAnimationState = new AnimationState();
   public AnimationState threetailhitAnimationState = new AnimationState();
   public AnimationState rightstompAnimationState = new AnimationState();
   public AnimationState leftstompAnimationState = new AnimationState();
   public AnimationState rightDoubleStompAnimationState = new AnimationState();
   public AnimationState leftDoubleStompAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   public AnimationState rightbiteAnimationState = new AnimationState();
   public AnimationState chargeprepareAnimationState = new AnimationState();
   public AnimationState chargeAnimationState = new AnimationState();
   public AnimationState chargestunAnimationState = new AnimationState();
   public AnimationState groundtailAnimationState = new AnimationState();
   public AnimationState tailswingAnimationState = new AnimationState();
   public AnimationState monolithAnimationState = new AnimationState();
   private static final EntityDataAccessor<Integer> RAGE = SynchedEntityData.defineId(Ancient_Remnant_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> NECKLACE = SynchedEntityData.defineId(Ancient_Remnant_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> POWER = SynchedEntityData.defineId(Ancient_Remnant_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> CRASH = SynchedEntityData.defineId(Ancient_Remnant_Entity.class, EntityDataSerializers.BOOLEAN);
   public final LegSolver legSolver = new LegSolver(new Leg[]{new Leg(0.0F, 0.75F, 4.0F, false), new Leg(0.0F, -0.75F, 4.0F, false)});
   private Ancient_Remnant_Entity.AttackMode mode = Ancient_Remnant_Entity.AttackMode.CIRCLE;
   private int hunting_cooldown = 160;
   private int roar_cooldown = 0;
   private int monoltih_cooldown = 0;
   private int earthquake_cooldown = 0;
   private int stomp_cooldown = 0;
   private boolean hit = false;
   public static final int ROAR_COOLDOWN = 500;
   public static final int MONOLITH2_COOLDOWN = 200;
   public static final int EARTHQUAKE_COOLDOWN = 160;
   public static final int STOMP_COOLDOWN = 110;
   private final CMBossInfoServer bossEvent = new CMBossInfoServer(this.getDisplayName(), BossBarColor.WHITE, false, 7);
   private final CMBossInfoServer bossEvent2 = new CMBossInfoServer(Component.translatable("entity.cataclysm.rage_meter"), BossBarColor.WHITE, false, 8);
   public int frame;

   public Ancient_Remnant_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 500;
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setConfigattribute(this, CMCommonConfig.AncientRemnant.healthMultiplier, CMCommonConfig.AncientRemnant.attackMultiplier);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByNearestTargetGoal(this));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector
         .addGoal(
            3, new NearestAttackableTargetGoal(this, LivingEntity.class, 120, true, true, ModEntities.buildPredicateFromTag(ModTag.ANCIENT_REMNANT_TARGET))
         );
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
      this.goalSelector.addGoal(5, new Ancient_Remnant_Entity.RemnantAttackModeGoal(this));
      this.goalSelector.addGoal(3, new Ancient_Remnant_Entity.RemnantAttackGoal(this, 0, 4, 0, 70, 29, 6.0F, 10.0F));
      this.goalSelector.addGoal(0, new Ancient_Remnant_Entity.RemnantDoNothingGoal());
      this.goalSelector.addGoal(0, new Ancient_Remnant_Entity.RemnantAwakenGoal(this, 2, 2, 0, 80, 0));
      this.goalSelector.addGoal(0, new Ancient_Remnant_Entity.RemnantPhaseChangeGoal(this, 0, 7, 0, 60, 13));
      this.goalSelector.addGoal(3, new Ancient_Remnant_Entity.RemnantStompGoal(this, 0, 8, 9, 13, 14, 0, 50, 66, 26, 20.0, 36.0F));
      this.goalSelector.addGoal(3, new Ancient_Remnant_Entity.RemnantAttackGoal(this, 0, 6, 0, 60, 11, 32.0F, 18.0F) {
         @Override
         public boolean canUse() {
            return super.canUse() && this.entity.roar_cooldown <= 0;
         }

         @Override
         public void stop() {
            super.stop();
            this.entity.roar_cooldown = 500;
         }
      });
      this.goalSelector.addGoal(3, new Ancient_Remnant_Entity.RemnantMonolithAttackGoal(this, 0, 17, 0, 70, 20, 10.0F, 12.0F));
      this.goalSelector.addGoal(3, new Ancient_Remnant_Entity.RemnantChargeGoal(this, 0, 10, 11, 70, 66, 32.0F, 60.0F));
      this.goalSelector.addGoal(2, new InternalStateGoal(this, 11, 11, 12, 60, 0) {
         @Override
         public boolean canContinueToUse() {
            return super.canContinueToUse() && !Ancient_Remnant_Entity.this.getCrash();
         }

         @Override
         public void stop() {
            super.stop();
            Ancient_Remnant_Entity.this.setCrash(false);
         }

         @Override
         public void tick() {
            if (this.entity.onGround()) {
               Vec3 vector3d = this.entity.getDeltaMovement();
               float f = this.entity.getYRot() * (float) (Math.PI / 180.0);
               Vec3 vector3d1 = new Vec3(-Mth.sin(f), this.entity.getDeltaMovement().y, Mth.cos(f)).scale(1.0).add(vector3d.scale(0.5));
               this.entity.setDeltaMovement(vector3d1.x, this.entity.getDeltaMovement().y, vector3d1.z);
            }
         }
      });
      this.goalSelector.addGoal(1, new InternalStateGoal(this, 12, 12, 0, 120, 0));
      this.goalSelector.addGoal(3, new Ancient_Remnant_Entity.RemnantAttackGoal(this, 0, 15, 0, 110, 85, 12.0F, 10.0F) {
         @Override
         public boolean canUse() {
            LivingEntity target = this.entity.getTarget();
            return super.canUse() && target != null && target.onGround() && this.entity.earthquake_cooldown <= 0;
         }

         @Override
         public void stop() {
            super.stop();
            this.entity.earthquake_cooldown = 160;
         }
      });
      this.goalSelector.addGoal(3, new Ancient_Remnant_Entity.RemnantAttackGoal(this, 0, 16, 0, 58, 13, 7.5F, 10.0F) {
         @Override
         public boolean canUse() {
            LivingEntity target = this.entity.getTarget();
            return super.canUse() && target != null && target.getY() < this.entity.getY() + 1.0;
         }
      });
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public static Builder maledictus() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 70.0)
         .add(Attributes.MOVEMENT_SPEED, 0.33F)
         .add(Attributes.ATTACK_DAMAGE, 25.0)
         .add(Attributes.MAX_HEALTH, 450.0)
         .add(Attributes.ARMOR, 12.0)
         .add(Attributes.ARMOR_TOUGHNESS, 4.0)
         .add(Attributes.STEP_HEIGHT, 1.5)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   public boolean canBeSeenAsEnemy() {
      return !this.isSleep() && super.canBeSeenAsEnemy();
   }

   @Override
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType p_29680_, @Nullable SpawnGroupData p_29681_) {
      return super.finalizeSpawn(p_29678_, p_29679_, p_29680_, p_29681_);
   }

   public ItemEntity spawnAtLocation(ItemStack stack) {
      ItemEntity itementity = this.spawnAtLocation(stack, 0.0F);
      if (itementity != null) {
         itementity.setGlowingTag(true);
         itementity.setExtendedLifetime();
      }

      return itementity;
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if (this.getAttackState() == 7 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      } else {
         Entity entity = source.getDirectEntity();
         if (this.getAttackState() != 12 && entity instanceof AbstractArrow) {
            return false;
         } else {
            return this.isSleep() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) ? false : super.hurt(source, damage);
         }
      }
   }

   @Override
   public float DamageCap() {
      return (float)CMCommonConfig.AncientRemnant.damageCap;
   }

   @Override
   public float NatureRegen() {
      return (float)CMCommonConfig.AncientRemnant.natureHeal;
   }

   @Override
   public float DpsCap() {
      return (float)CMCommonConfig.AncientRemnant.dpsCap;
   }

   @Override
   public double RangeLimit() {
      return (float)CMCommonConfig.AncientRemnant.rangeCap;
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(RAGE, 0);
      p_326229_.define(NECKLACE, true);
      p_326229_.define(POWER, false);
      p_326229_.define(CRASH, false);
   }

   public boolean isSleep() {
      return this.getAttackState() == 1 || this.getAttackState() == 2 || !this.getNecklace();
   }

   public void setNecklace(boolean necklace) {
      this.entityData.set(NECKLACE, necklace);
      this.bossEvent.setVisible(necklace);
      this.bossEvent2.setVisible(necklace);
   }

   public boolean getNecklace() {
      return (Boolean)this.entityData.get(NECKLACE);
   }

   public void setCustomName(@Nullable Component name) {
      super.setCustomName(name);
      this.bossEvent.setName(this.getDisplayName());
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("has_necklace", this.getNecklace());
      compound.putInt("Rage", this.getRage());
      compound.putBoolean("Power", this.getIsPower());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setNecklace(compound.getBoolean("has_necklace"));
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }

      this.setRage(compound.getInt("Rage"));
      this.setIsPower(compound.getBoolean("Power"));
   }

   public void setRage(int isAnger) {
      this.entityData.set(RAGE, isAnger);
   }

   public int getRage() {
      return (Integer)this.entityData.get(RAGE);
   }

   public void setCrash(boolean isPower) {
      this.entityData.set(CRASH, isPower);
   }

   public boolean getCrash() {
      return (Boolean)this.entityData.get(CRASH);
   }

   public void setIsPower(boolean isPower) {
      this.entityData.set(POWER, isPower);
   }

   public boolean getIsPower() {
      return (Boolean)this.entityData.get(POWER);
   }

   public boolean canStandOnFluid(FluidState p_204067_) {
      return p_204067_.is(FluidTags.WATER);
   }

   public boolean isPushedByFluid() {
      return false;
   }

   private void floatRemnant() {
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

   public AnimationState getAnimationState(String input) {
      if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "sleep") {
         return this.sleepAnimationState;
      } else if (input == "awaken") {
         return this.awakenAnimationState;
      } else if (input == "death") {
         return this.deathAnimationState;
      } else if (input == "three_tail_hit") {
         return this.threetailhitAnimationState;
      } else if (input == "right_bite") {
         return this.rightbiteAnimationState;
      } else if (input == "sandstorm_roar") {
         return this.sandstormroarAnimationState;
      } else if (input == "charge") {
         return this.chargeAnimationState;
      } else if (input == "charge_prepare") {
         return this.chargeprepareAnimationState;
      } else if (input == "phase_roar") {
         return this.phaseroarAnimationState;
      } else if (input == "right_stomp") {
         return this.rightstompAnimationState;
      } else if (input == "left_stomp") {
         return this.leftstompAnimationState;
      } else if (input == "charge_stun") {
         return this.chargestunAnimationState;
      } else if (input == "right_double_stomp") {
         return this.rightDoubleStompAnimationState;
      } else if (input == "left_double_stomp") {
         return this.leftDoubleStompAnimationState;
      } else if (input == "ground_tail") {
         return this.groundtailAnimationState;
      } else if (input == "tail_swing") {
         return this.tailswingAnimationState;
      } else {
         return input == "monolith" ? this.monolithAnimationState : new AnimationState();
      }
   }

   public void travel(Vec3 travelVector) {
      super.travel(travelVector);
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
               this.awakenAnimationState.startIfStopped(this.tickCount);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.deathAnimationState.startIfStopped(this.tickCount);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.rightbiteAnimationState.startIfStopped(this.tickCount);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.threetailhitAnimationState.startIfStopped(this.tickCount);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.sandstormroarAnimationState.startIfStopped(this.tickCount);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.phaseroarAnimationState.startIfStopped(this.tickCount);
               break;
            case 8:
               this.stopAllAnimationStates();
               this.rightstompAnimationState.startIfStopped(this.tickCount);
               break;
            case 9:
               this.stopAllAnimationStates();
               this.leftstompAnimationState.startIfStopped(this.tickCount);
               break;
            case 10:
               this.stopAllAnimationStates();
               this.chargeprepareAnimationState.startIfStopped(this.tickCount);
               break;
            case 11:
               this.stopAllAnimationStates();
               this.chargeAnimationState.startIfStopped(this.tickCount);
               break;
            case 12:
               this.stopAllAnimationStates();
               this.chargestunAnimationState.startIfStopped(this.tickCount);
               break;
            case 13:
               this.stopAllAnimationStates();
               this.rightDoubleStompAnimationState.startIfStopped(this.tickCount);
               break;
            case 14:
               this.stopAllAnimationStates();
               this.leftDoubleStompAnimationState.startIfStopped(this.tickCount);
               break;
            case 15:
               this.stopAllAnimationStates();
               this.groundtailAnimationState.startIfStopped(this.tickCount);
               break;
            case 16:
               this.stopAllAnimationStates();
               this.tailswingAnimationState.startIfStopped(this.tickCount);
               break;
            case 17:
               this.stopAllAnimationStates();
               this.monolithAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.deathAnimationState.stop();
      this.sleepAnimationState.stop();
      this.awakenAnimationState.stop();
      this.rightbiteAnimationState.stop();
      this.threetailhitAnimationState.stop();
      this.sandstormroarAnimationState.stop();
      this.chargeAnimationState.stop();
      this.chargestunAnimationState.stop();
      this.phaseroarAnimationState.stop();
      this.rightstompAnimationState.stop();
      this.leftstompAnimationState.stop();
      this.chargeprepareAnimationState.stop();
      this.rightDoubleStompAnimationState.stop();
      this.leftDoubleStompAnimationState.stop();
      this.groundtailAnimationState.stop();
      this.monolithAnimationState.stop();
      this.tailswingAnimationState.stop();
   }

   @Override
   public void die(DamageSource p_21014_) {
      super.die(p_21014_);
      this.setAttackState(3);
   }

   @Override
   public int deathtimer() {
      return 70;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         this.idleAnimationState.animateWhen(this.getAttackState() != 3, this.tickCount);
      } else {
         this.bossEvent.setLife(this.getLife());
      }

      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
      this.bossEvent2.setProgress(this.getRage() / 5.0F);
      this.legSolver.update(this, this.yBodyRot, this.getScale());
      if (this.hunting_cooldown > 0) {
         this.hunting_cooldown--;
      }

      if (this.roar_cooldown > 0) {
         this.roar_cooldown--;
      }

      if (this.monoltih_cooldown > 0) {
         this.monoltih_cooldown--;
      }

      if (this.earthquake_cooldown > 0) {
         this.earthquake_cooldown--;
      }

      if (this.stomp_cooldown > 0) {
         this.stomp_cooldown--;
      }

      if (!this.level().isClientSide && this.getAttackState() == 0) {
         if (CMCommonConfig.AncientRemnant.ignoreMobGriefing) {
            this.ChargeBlockBreaking(0.5);
         } else if (EventHooks.canEntityGrief(this.level(), this)) {
            this.ChargeBlockBreaking(0.5);
         }
      }

      if (this.getIsPower() && this.tickCount % 20 == 0) {
         this.heal(1.0F);
      }

      this.floatRemnant();
      this.frame++;
      float moveX = (float)(this.getX() - this.xo);
      float moveZ = (float)(this.getZ() - this.zo);
      float speed = Mth.sqrt(moveX * moveX + moveZ * moveZ);
      if (!this.isSilent() && this.frame % 8 == 1 && speed > 0.05 && this.getAttackState() == 11 && this.onGround()) {
         this.playSound((SoundEvent)ModSounds.REMNANT_CHARGE_STEP.get(), 1.0F, 1.0F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
      }
   }

   public boolean isPower() {
      return this.getHealth() <= this.getMaxHealth() / 2.0F;
   }

   private void Charge() {
      if (!this.level().isClientSide) {
         if (CMCommonConfig.AncientRemnant.ignoreMobGriefing) {
            this.ChargeBlockBreaking(1.5);
         } else if (EventHooks.canEntityGrief(this.level(), this)) {
            this.ChargeBlockBreaking(1.5);
         }

         if (this.tickCount % 4 == 0) {
            DamageSource damagesource = this.damageSources().mobAttack(this);

            for (LivingEntity Lentity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox())) {
               if (!this.isAlliedTo(Lentity) && !(Lentity instanceof Ancient_Remnant_Entity) && Lentity != this) {
                  boolean flag = Lentity.hurt(
                     damagesource,
                     (float)(
                        (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0F
                           + Math.min(
                              this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0,
                              Lentity.getMaxHealth() * (float)CMCommonConfig.AncientRemnant.ChargeHpDamage
                           )
                     )
                  );
                  if (flag && Lentity.onGround()) {
                     double d0 = Lentity.getX() - this.getX();
                     double d1 = Lentity.getZ() - this.getZ();
                     double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                     float f = 1.5F;
                     Lentity.push(d0 / d2 * f, 0.5, d1 / d2 * f);
                  }
               }
            }
         }
      }
   }

   private void ChargeBlockBreaking(double inflate) {
      boolean flag = false;
      AABB aabb = this.getBoundingBox().inflate(inflate, 0.2, inflate);

      for (BlockPos blockpos : BlockPos.betweenClosed(
         Mth.floor(aabb.minX), Mth.floor(this.getY()), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
      )) {
         BlockState blockstate = this.level().getBlockState(blockpos);
         if (!blockstate.isAir()
            && blockstate.canEntityDestroy(this.level(), blockpos, this)
            && !blockstate.is(ModTag.REMNANT_IMMUNE)
            && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)) {
            if (this.random.nextInt(6) == 0 && !blockstate.hasBlockEntity()) {
               Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                  this.level(), blockpos.getX() + 0.5, blockpos.getY() + 0.5, blockpos.getZ() + 0.5, blockstate, 20
               );
               flag = this.level().destroyBlock(blockpos, false, this) || flag;
               fallingBlockEntity.setDeltaMovement(
                  fallingBlockEntity.getDeltaMovement()
                     .add(
                        this.position()
                           .subtract(fallingBlockEntity.position())
                           .multiply(
                              (-1.2 + this.random.nextDouble()) / 3.0, 0.2 + this.getRandom().nextGaussian() * 0.15, (-1.2 + this.random.nextDouble()) / 3.0
                           )
                     )
               );
               this.level().addFreshEntity(fallingBlockEntity);
            } else {
               flag = this.level().destroyBlock(blockpos, false, this) || flag;
            }
         }
      }
   }

   public void aiStep() {
      super.aiStep();
      if (this.getAttackState() == 4) {
         if (this.attackTicks == 8) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_BITE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         }

         if (this.attackTicks == 31) {
            this.AreaAttack(7.0F, 7.0F, 70.0F, 1.35F, (float)CMCommonConfig.AncientRemnant.HpDamage, 160, 0);
         }
      }

      if (this.getAttackState() == 6) {
         if (this.attackTicks == 14) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_ROAR.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 60);
            this.Roarparticle(4.0F, 1.0F, 8.0F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }

         if (this.attackTicks == 17) {
            this.Roarparticle(4.0F, 2.5F, 7.0F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }

         if (this.attackTicks == 20) {
            this.Roarparticle(4.0F, 2.5F, 7.0F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }

         if (this.attackTicks == 23) {
            this.Roarparticle(4.0F, 2.5F, 7.0F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }

         if (this.attackTicks == 26) {
            this.Roarparticle(4.0F, 1.25F, 8.0F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }

         if (this.attackTicks == 29) {
            this.Roarparticle(4.0F, 0.0F, 9.0F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }

         if (this.attackTicks == 55) {
            for (int i = 0; i < 3; i++) {
               float angle = i * (float) Math.PI / 1.5F;
               double sx = this.getX() + Mth.cos(angle) * 8.0F;
               double sy = this.getY();
               double sz = this.getZ() + Mth.sin(angle) * 8.0F;
               if (!this.level().isClientSide()) {
                  Sandstorm_Entity projectile = new Sandstorm_Entity(this.level(), sx, sy, sz, 300, angle, this);
                  this.level().addFreshEntity(projectile);
               }
            }
         }
      }

      if (this.getAttackState() == 7) {
         if (this.attackTicks == 14) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.15F, 0, 80);
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_ROAR.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
            this.setIsPower(true);
            this.Roarparticle(5.5F, 0.0F, 4.8F, 10, 255, 255, 255, 0.2F, 1.0F, 0.8F, 5.0F);
         }

         if (this.attackTicks == 17) {
            this.Roarparticle(5.5F, 0.4F, 4.8F, 10, 255, 255, 255, 0.2F, 1.0F, 0.8F, 5.0F);
         }

         if (this.attackTicks == 20) {
            this.Roarparticle(5.5F, 0.8F, 4.8F, 10, 255, 255, 255, 0.2F, 1.0F, 0.8F, 5.0F);
         }

         if (this.attackTicks == 23) {
            this.Roarparticle(5.5F, 1.2F, 4.8F, 10, 255, 255, 255, 0.2F, 1.0F, 0.8F, 5.0F);
         }

         if (this.attackTicks == 26) {
            this.Roarparticle(5.5F, 1.6F, 4.8F, 10, 255, 255, 255, 0.2F, 1.0F, 0.8F, 5.0F);
         }

         if (this.attackTicks == 29) {
            this.Roarparticle(5.5F, 1.6F, 4.8F, 10, 255, 255, 255, 0.2F, 1.0F, 0.8F, 5.0F);
         }

         if (this.attackTicks == 32) {
            this.Roarparticle(5.5F, 1.4F, 4.3F, 10, 255, 255, 255, 0.2F, 1.0F, 0.8F, 5.0F);
         }

         if (this.attackTicks == 35) {
            this.Roarparticle(5.5F, 1.2F, 3.8F, 10, 255, 255, 255, 0.2F, 1.0F, 0.8F, 5.0F);
         }

         if (this.attackTicks == 38) {
            this.Roarparticle(5.5F, 1.0F, 3.3F, 10, 255, 255, 255, 0.2F, 1.0F, 0.8F, 5.0F);
         }

         if (this.attackTicks == 41) {
            this.Roarparticle(5.5F, 0.0F, 3.1F, 10, 255, 255, 255, 0.2F, 1.0F, 0.8F, 5.0F);
         }
      }

      if (this.getAttackState() == 8) {
         if (this.attackTicks == 28) {
            this.StompParticle(0.9F, 1.4F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.playSound((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 28) {
            this.StompDamage(0.4F, 2, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(2.5F, 1.4F);
         }

         if (this.attackTicks == 30) {
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 4, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(3.5F, 1.4F);
         }

         if (this.attackTicks == 32) {
            this.StompDamage(0.4F, 5, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 6, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(5.5F, 1.4F);
         }

         if (this.attackTicks == 34) {
            this.StompDamage(0.4F, 7, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 8, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(7.5F, 1.4F);
         }

         if (this.attackTicks == 36) {
            this.StompDamage(0.4F, 9, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 10, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(9.5F, 1.4F);
         }

         if (this.attackTicks == 38) {
            this.StompDamage(0.4F, 10, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 11, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(10.5F, 1.4F);
         }

         if (this.attackTicks == 40) {
            this.StompDamage(0.4F, 12, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 13, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(12.5F, 1.4F);
         }

         if (this.attackTicks == 42) {
            this.StompDamage(0.4F, 14, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 15, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(14.5F, 1.4F);
         }

         if (this.attackTicks == 44) {
            this.StompDamage(0.4F, 16, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 17, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(16.5F, 1.4F);
         }

         if (this.attackTicks == 46) {
            this.StompDamage(0.4F, 18, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 19, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(18.5F, 1.4F);
         }
      }

      if (this.getAttackState() == 9) {
         if (this.attackTicks == 28) {
            this.StompParticle(0.9F, -1.4F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.playSound((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 28) {
            this.StompDamage(0.4F, 2, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(2.5F, -1.4F);
         }

         if (this.attackTicks == 30) {
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 4, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(3.5F, -1.4F);
         }

         if (this.attackTicks == 32) {
            this.StompDamage(0.4F, 5, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 6, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(5.5F, -1.4F);
         }

         if (this.attackTicks == 34) {
            this.StompDamage(0.4F, 7, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 8, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(7.5F, -1.4F);
         }

         if (this.attackTicks == 36) {
            this.StompDamage(0.4F, 9, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 10, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(9.5F, -1.4F);
         }

         if (this.attackTicks == 38) {
            this.StompDamage(0.4F, 10, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 11, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(10.5F, -1.4F);
         }

         if (this.attackTicks == 40) {
            this.StompDamage(0.4F, 12, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 13, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(12.5F, -1.4F);
         }

         if (this.attackTicks == 42) {
            this.StompDamage(0.4F, 14, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 15, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(14.5F, -1.4F);
         }

         if (this.attackTicks == 44) {
            this.StompDamage(0.4F, 16, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 17, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(16.5F, -1.4F);
         }

         if (this.attackTicks == 46) {
            this.StompDamage(0.4F, 18, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 19, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(18.5F, -1.4F);
         }
      }

      if (this.getAttackState() == 10) {
         if (this.attackTicks == 1) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_CHARGE_PREPARE.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
         }

         if (this.attackTicks == 14) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.StompParticle(-0.1F, -0.75F);
         }

         if (this.attackTicks == 43) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.StompParticle(-0.1F, 0.75F);
         }

         if (this.attackTicks == 66) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_CHARGE_ROAR.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
            this.Roarparticle(6.0F, 0.0F, 4.0F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }

         if (this.attackTicks == 69) {
            this.Roarparticle(6.0F, 0.0F, 4.0F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }
      }

      if (this.getAttackState() == 11) {
         this.Charge();
         if (!this.level().isClientSide && this.horizontalCollision) {
            this.setCrash(true);
         }

         if (this.attackTicks >= 1 && this.attackTicks <= 58 && (this.attackTicks - 1) % 3 == 0) {
            this.Roarparticle(6.0F, 0.0F, 3.3F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }
      }

      if (this.getAttackState() == 12) {
         if (this.attackTicks == 1) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.25F, 0, 60);
         }

         if (this.attackTicks == 1 || this.attackTicks == 3 || this.attackTicks == 5) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_CHARGE_STEP.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
         }
      }

      if (this.getAttackState() == 13) {
         if (this.attackTicks == 28) {
            this.StompParticle(0.9F, 1.4F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.playSound((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 28) {
            this.StompDamage(0.4F, 2, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(2.5F, 1.4F);
         }

         if (this.attackTicks == 30) {
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 4, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(3.5F, 1.4F);
         }

         if (this.attackTicks == 32) {
            this.StompDamage(0.4F, 5, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 6, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(5.5F, 1.4F);
         }

         if (this.attackTicks == 34) {
            this.StompDamage(0.4F, 7, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 8, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(7.5F, 1.4F);
         }

         if (this.attackTicks == 36) {
            this.StompDamage(0.4F, 9, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 10, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(9.5F, 1.4F);
         }

         if (this.attackTicks == 38) {
            this.StompDamage(0.4F, 11, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 12, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(11.5F, 1.4F);
         }

         if (this.attackTicks == 40) {
            this.StompDamage(0.4F, 13, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 14, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(13.5F, 1.4F);
         }

         if (this.attackTicks == 42) {
            this.StompDamage(0.4F, 15, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 16, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(15.5F, 1.4F);
         }

         if (this.attackTicks == 44) {
            this.StompDamage(0.4F, 17, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 18, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(17.5F, 1.4F);
         }

         if (this.attackTicks == 46) {
            this.StompDamage(0.4F, 19, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 20, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(19.5F, 1.4F);
         }

         if (this.attackTicks == 50) {
            this.StompParticle(0.9F, 1.4F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.playSound((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 50) {
            this.StompDamage(0.4F, 2, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(2.5F, 1.4F);
         }

         if (this.attackTicks == 52) {
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 4, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(3.5F, 1.4F);
         }

         if (this.attackTicks == 54) {
            this.StompDamage(0.4F, 5, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 6, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(5.5F, 1.4F);
         }

         if (this.attackTicks == 56) {
            this.StompDamage(0.4F, 7, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 8, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(7.5F, 1.4F);
         }

         if (this.attackTicks == 58) {
            this.StompDamage(0.4F, 9, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 10, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(9.5F, 1.4F);
         }

         if (this.attackTicks == 60) {
            this.StompDamage(0.4F, 11, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 12, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(11.5F, 1.4F);
         }

         if (this.attackTicks == 62) {
            this.StompDamage(0.4F, 13, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 14, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(13.5F, 1.4F);
         }

         if (this.attackTicks == 64) {
            this.StompDamage(0.4F, 15, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 16, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(15.5F, 1.4F);
         }

         if (this.attackTicks == 66) {
            this.StompDamage(0.4F, 17, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 18, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(17.5F, 1.4F);
         }

         if (this.attackTicks == 68) {
            this.StompDamage(0.4F, 19, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 20, 6, 0.9F, 0.0F, 1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(19.5F, 1.4F);
         }
      }

      if (this.getAttackState() == 14) {
         if (this.attackTicks == 28) {
            this.StompParticle(0.9F, -1.4F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.playSound((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 28) {
            this.StompParticle(0.9F, 1.4F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.playSound((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 28) {
            this.StompDamage(0.4F, 2, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(2.5F, -1.4F);
         }

         if (this.attackTicks == 30) {
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 4, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(3.5F, -1.4F);
         }

         if (this.attackTicks == 32) {
            this.StompDamage(0.4F, 5, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 6, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(5.5F, -1.4F);
         }

         if (this.attackTicks == 34) {
            this.StompDamage(0.4F, 7, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 8, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(7.5F, -1.4F);
         }

         if (this.attackTicks == 36) {
            this.StompDamage(0.4F, 9, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 10, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(9.5F, -1.4F);
         }

         if (this.attackTicks == 38) {
            this.StompDamage(0.4F, 11, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 12, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(11.5F, -1.4F);
         }

         if (this.attackTicks == 40) {
            this.StompDamage(0.4F, 13, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 14, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(13.5F, -1.4F);
         }

         if (this.attackTicks == 42) {
            this.StompDamage(0.4F, 15, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 16, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(15.5F, -1.4F);
         }

         if (this.attackTicks == 44) {
            this.StompDamage(0.4F, 17, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 18, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(17.5F, -1.4F);
         }

         if (this.attackTicks == 46) {
            this.StompDamage(0.4F, 19, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 20, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(19.5F, -1.4F);
         }

         if (this.attackTicks == 50) {
            this.StompParticle(0.9F, -1.4F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.playSound((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 50) {
            this.StompDamage(0.4F, 2, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(2.5F, -1.4F);
         }

         if (this.attackTicks == 52) {
            this.StompDamage(0.4F, 3, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 4, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(3.5F, -1.4F);
         }

         if (this.attackTicks == 54) {
            this.StompDamage(0.4F, 5, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 6, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(5.5F, -1.4F);
         }

         if (this.attackTicks == 56) {
            this.StompDamage(0.4F, 7, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 8, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(7.5F, -1.4F);
         }

         if (this.attackTicks == 58) {
            this.StompDamage(0.4F, 9, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 10, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(9.5F, -1.4F);
         }

         if (this.attackTicks == 60) {
            this.StompDamage(0.4F, 11, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 12, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(11.5F, -1.4F);
         }

         if (this.attackTicks == 62) {
            this.StompDamage(0.4F, 13, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 14, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(13.5F, -1.4F);
         }

         if (this.attackTicks == 64) {
            this.StompDamage(0.4F, 15, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 16, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(15.5F, -1.4F);
         }

         if (this.attackTicks == 66) {
            this.StompDamage(0.4F, 17, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 18, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(17.5F, -1.4F);
         }

         if (this.attackTicks == 68) {
            this.StompDamage(0.4F, 19, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.StompDamage(0.4F, 20, 6, 0.9F, 0.0F, -1.4F, 80, 0.9F, (float)CMCommonConfig.AncientRemnant.StompHpDamage, 0.1F);
            this.Stompsound(19.5F, -1.4F);
         }
      }

      if (this.getAttackState() == 15) {
         if (this.attackTicks == 1) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_TAIL_SLAM_1.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         }

         if (this.attackTicks == 26) {
            this.AreaAttack(10.0F, 10.0F, 70.0F, 1.0F, (float)CMCommonConfig.AncientRemnant.HpDamage, 160, 0);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.EarthQuakeSummon(5.5F, 22 + this.random.nextInt(8), 0.8F);
            this.StompParticle(5.5F, 0.8F);
         }

         if (this.attackTicks == 55) {
            this.AreaAttack(10.0F, 10.0F, 70.0F, 1.0F, (float)CMCommonConfig.AncientRemnant.HpDamage, 160, 0);
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_TAIL_SLAM_2.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.EarthQuakeSummon(5.25F, 22 + this.random.nextInt(8), 1.5F);
            this.StompParticle(5.25F, 1.5F);
         }

         if (this.attackTicks == 85) {
            this.AreaAttack(10.0F, 10.0F, 70.0F, 1.0F, (float)CMCommonConfig.AncientRemnant.HpDamage, 160, 0);
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_TAIL_SLAM_3.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 0, 10);
            this.EarthQuakeSummon(5.5F, 22 + this.random.nextInt(8), 0.8F);
            this.StompParticle(5.5F, 0.8F);
         }
      }

      if (this.getAttackState() == 16) {
         if (this.attackTicks == 12) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_TAIL_SWING.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
         }

         if (this.attackTicks == 25) {
            this.TailAreaAttack(8.0F, 8.0F, 1.05F, 120.0F, 1.0F, (float)CMCommonConfig.AncientRemnant.HpDamage, 200, 100);
         }
      }

      if (this.getAttackState() == 17) {
         if (this.attackTicks == 16) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.15F, 0, 80);
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.REMNANT_ROAR.get(), SoundSource.HOSTILE, 3.0F, 1.1F);
         }

         if (this.attackTicks == 18) {
            this.Roarparticle(4.0F, -1.0F, 8.0F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }

         if (this.attackTicks == 21) {
            this.Roarparticle(4.0F, -1.4F, 7.5F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }

         if (this.attackTicks == 24) {
            this.Roarparticle(4.0F, -1.4F, 8.0F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }

         if (this.attackTicks == 27) {
            this.Roarparticle(4.0F, -1.0F, 7.5F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 3.5F);
         }
      }
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      Item item = itemstack.getItem();
      if (item == ModItems.NECKLACE_OF_THE_DESERT.get() && !this.getNecklace()) {
         if (!player.isCreative()) {
            itemstack.shrink(1);
         }

         this.setHomePos(GlobalPos.of(this.level().dimension(), this.blockPosition()));
         this.setNecklace(true);
         this.setAttackState(2);
         return InteractionResult.SUCCESS;
      } else {
         return super.mobInteract(player, hand);
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, float hpdamage, int shieldbreakticks, int stunticks) {
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(range, height, range, range);
      if (!this.level().isClientSide) {
         DamageSource damagesource = this.damageSources().mobAttack(this);

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
               && !(entityHit instanceof Ancient_Remnant_Entity)
               && entityHit != this) {
               boolean flag = entityHit.hurt(
                  damagesource,
                  (float)(
                     this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage
                        + Math.min(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage, entityHit.getMaxHealth() * hpdamage)
                  )
               );
               if (entityHit.isDamageSourceBlocked(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               if (flag) {
                  this.hit = true;
                  if (stunticks > 0) {
                     entityHit.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN, stunticks));
                  }
               }
            }
         }
      }
   }

   private void TailAreaAttack(float range, float height, float height2, float arc, float damage, float hpdamage, int shieldbreakticks, int stunticks) {
      List<LivingEntity> entitiesHit = this.getTailEntityLivingBaseNearby(range, height, height2, range, range);
      if (!this.level().isClientSide) {
         DamageSource damagesource = this.damageSources().mobAttack(this);

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
               && !(entityHit instanceof Ancient_Remnant_Entity)
               && entityHit != this) {
               boolean flag = entityHit.hurt(
                  damagesource,
                  (float)(
                     this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage
                        + Math.min(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage, entityHit.getMaxHealth() * hpdamage)
                  )
               );
               if (entityHit.isDamageSourceBlocked(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               if (flag) {
                  this.hit = true;
                  if (stunticks > 0) {
                     entityHit.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN, stunticks));
                  }

                  double d0 = entityHit.getX() - this.getX();
                  double d1 = entityHit.getZ() - this.getZ();
                  double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                  entityHit.push(d0 / d2 * 4.0, 0.2, d1 / d2 * 4.0);
               }
            }
         }
      }
   }

   private void StompDamage(
      float spreadarc, int distance, int height, float mxy, float vec, float math, int shieldbreakticks, float damage, float hpdamage, float airborne
   ) {
      if (!this.level().isClientSide) {
         double bodyRotRad = this.yBodyRot * (Math.PI / 180.0);
         double cosBodyRot = Math.cos(bodyRotRad);
         double sinBodyRot = Math.sin(bodyRotRad);
         double facingAngle = bodyRotRad + (Math.PI / 2);
         double commonOffsetX = vec * -sinBodyRot + cosBodyRot * math;
         double commonOffsetZ = vec * cosBodyRot + sinBodyRot * math;
         double baseX = this.getX() + commonOffsetX;
         double baseZ = this.getZ() + commonOffsetZ;
         int hitY = Mth.floor(this.getBoundingBox().minY - 0.5);
         double spread = Math.PI * spreadarc;
         int arcLen = Mth.ceil(distance * spread);
         float factor = 1.0F - distance / 12.0F;

         for (int i = 0; i < arcLen; i++) {
            double thetaRatio = arcLen > 1 ? (double)i / (arcLen - 1) : 0.5;
            double theta = (thetaRatio - 0.5) * spread + facingAngle;
            double vx = Math.cos(theta);
            double vz = Math.sin(theta);
            double px = baseX + vx * distance;
            double pz = baseZ + vz * distance;
            int hitX = Mth.floor(px);
            int hitZ = Mth.floor(pz);
            BlockPos pos = new BlockPos(hitX, hitY + height, hitZ);
            BlockState block = this.level().getBlockState(pos);
            int maxDepth = 30;

            for (int depthCount = 0; depthCount < maxDepth && block.getRenderShape() != RenderShape.MODEL; depthCount++) {
               pos = pos.below();
               block = this.level().getBlockState(pos);
            }

            if (block.getRenderShape() != RenderShape.MODEL) {
               block = Blocks.AIR.defaultBlockState();
            }

            this.spawnBlocks(hitX, hitY + height, hitZ, (int)(this.getY() - height), block, px, pz, mxy, vx, vz, factor, shieldbreakticks, damage, hpdamage);
         }
      }
   }

   private void spawnBlocks(
      int hitX,
      int hitY,
      int hitZ,
      int lowestYCheck,
      BlockState blockState,
      double px,
      double pz,
      float mxy,
      double vx,
      double vz,
      float factor,
      int shieldbreakticks,
      float damage,
      float hpdamage
   ) {
      BlockPos blockpos = new BlockPos(hitX, hitY, hitZ);
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
            break;
         }

         blockpos = blockpos.below();
      } while (blockpos.getY() >= Mth.floor(lowestYCheck) - 1);

      Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(this.level(), hitX + 0.5, blockpos.getY() + d0 + 0.5, hitZ + 0.5, blockState, 10);
      fallingBlockEntity.push(0.0, 0.2 + this.getRandom().nextGaussian() * 0.04, 0.0);
      this.level().addFreshEntity(fallingBlockEntity);
      AABB selection = new AABB(px - 0.5, blockpos.getY() + d0 - 1.0, pz - 0.5, px + 0.5, blockpos.getY() + d0 + mxy, pz + 0.5);
      List<LivingEntity> hitbox = this.level().getEntitiesOfClass(LivingEntity.class, selection);
      if (!hitbox.isEmpty()) {
         DamageSource damagesource = this.damageSources().mobAttack(this);
         float baseDamage = (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage);

         for (LivingEntity entity : hitbox) {
            if (!this.isAlliedTo(entity) && !(entity instanceof Ancient_Remnant_Entity) && entity != this) {
               float finalDamage = baseDamage + entity.getMaxHealth() * hpdamage;
               boolean flag = entity.hurt(damagesource, finalDamage);
               if (entity.isDamageSourceBlocked(damagesource) && entity instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               if (flag) {
                  this.hit = true;
                  double magnitude = -4.0;
                  double x = vx * (1.0F - factor) * magnitude;
                  double y = entity.onGround() ? 0.15 : 0.0;
                  double z = vz * (1.0F - factor) * magnitude;
                  entity.setDeltaMovement(entity.getDeltaMovement().add(x, y, z));
               }
            }
         }
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

   private void Stompsound(float distance, float math) {
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
      float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
      this.level()
         .playLocalSound(
            this.getX() + distance * vecX + f * math,
            this.getY(),
            this.getZ() + distance * vecZ + f1 * math,
            (SoundEvent)ModSounds.REMNANT_SHOCKWAVE.get(),
            this.getSoundSource(),
            1.5F,
            0.8F + this.getRandom().nextFloat() * 0.1F,
            false
         );
   }

   public List<LivingEntity> getTailEntityLivingBaseNearby(double distanceX, double distanceMinY, double distanceMaxY, double distanceZ, double radius) {
      return this.getTailEntitiesNearby(LivingEntity.class, distanceX, distanceMinY, distanceMaxY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getTailEntitiesNearby(Class<T> entityClass, double dX, double dY, double pY, double dZ, double r) {
      return this.level()
         .getEntitiesOfClass(
            entityClass,
            new AABB(this.getX() - dX, this.getY() - dY, this.getZ() - dZ, this.getX() + dX, this.getY() + pY, this.getZ() + dZ),
            e -> e != this && this.distanceTo(e) <= r + e.getBbWidth() / 2.0F && e.getY() <= this.getY() + dY
         );
   }

   private void StompParticle(float vec, float math) {
      if (this.level().isClientSide) {
         float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);

         for (int i1 = 0; i1 < 80 + this.random.nextInt(12); i1++) {
            double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
            double extraX = 0.5 * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.1F;
            double extraZ = 0.5 * Mth.cos(angle);
            int hitX = Mth.floor(this.getX() + vec * vecX + extraX);
            int hitY = Mth.floor(this.getY());
            int hitZ = Mth.floor(this.getZ() + vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.level().getBlockState(hit.below());
            if (block.getRenderShape() != RenderShape.INVISIBLE) {
               this.level()
                  .addParticle(
                     new BlockParticleOption(ParticleTypes.BLOCK, block),
                     this.getX() + vec * vecX + extraX + f * math,
                     this.getY() + extraY,
                     this.getZ() + vec * vecZ + extraZ + f1 * math,
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }

         this.level()
            .addParticle(
               new RingParticleOptions(0.0F, (float) (Math.PI / 2), 20, 255, 255, 255, 1.0F, 25.0F, false, 2),
               this.getX() + vec * vecX + f * math,
               this.getY() + 0.2F,
               this.getZ() + vec * vecZ + f1 * math,
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void EarthQuakeSummon(float vec, int quake, float math) {
      float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
      float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      float angle = 360.0F / quake;

      for (int i = 0; i < quake; i++) {
         EarthQuake_Entity peq = new EarthQuake_Entity(this.level(), this);
         peq.setDamage((float)CMCommonConfig.AncientRemnant.EarthQuakeDamage);
         peq.shootFromRotation(this, 0.0F, angle * i, 0.0F, 0.45F, 0.0F);
         peq.setPos(this.getX() + vec * vecX + f * math, this.getY() + 0.3, this.getZ() + vec * vecZ + f1 * math);
         this.level().addFreshEntity(peq);
      }
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

   protected SoundEvent getAmbientSound() {
      return !this.isSleep() ? (SoundEvent)ModSounds.REMNANT_IDLE.get() : super.getAmbientSound();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.REMNANT_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.REMNANT_DEATH.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.REMNANT_MUSIC.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && !this.isSleep();
   }

   @Override
   protected void onDeathAIUpdate() {
      super.onDeathAIUpdate();
   }

   @Override
   protected void AfterDefeatBoss(@Nullable LivingEntity living) {
      if (CMCommonConfig.AncientRemnant.respawner && !this.level().isClientSide && this.getHomePos() != null && this.level() instanceof ServerLevel serverLevel
         )
       {
         MinecraftServer server = serverLevel.getServer();
         ServerLevel targetLevel = server.getLevel(this.getHomePos().dimension());
         if (targetLevel != null) {
            BlockPos targetPos = this.getHomePos().pos();
            BlockState blockState = ((Block)ModBlocks.BOSS_RESPAWNER.get()).defaultBlockState();
            targetLevel.setBlock(targetPos, blockState, 2);
            if (targetLevel.getBlockEntity(targetPos) instanceof Boss_Respawn_Spawner_Block_Entity spawnerBlockEntity) {
               spawnerBlockEntity.setEntityId((EntityType<?>)ModEntities.ANCIENT_REMNANT.get());
               spawnerBlockEntity.setTheItem(((Item)ModItems.DESERT_EYE.get()).getDefaultInstance());
            }
         }
      }
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
      this.bossEvent2.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
      this.bossEvent2.removePlayer(player);
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   private enum AttackMode {
      CIRCLE,
      MELEE;
   }

   static class RemnantAttackGoal extends InternalAttackGoal {
      protected final Ancient_Remnant_Entity entity;
      private final float random;

      public RemnantAttackGoal(
         Ancient_Remnant_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         float random
      ) {
         super(entity, getattackstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.random = random;
      }

      @Override
      public boolean canUse() {
         return super.canUse() && this.entity.getRandom().nextFloat() * 100.0F < this.random && this.entity.mode == Ancient_Remnant_Entity.AttackMode.MELEE;
      }

      @Override
      public void start() {
         super.start();
         this.entity.hit = false;
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.lookAt(target, 30.0F, 30.0F);
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }
      }

      @Override
      public void stop() {
         super.stop();
         if (!this.entity.hit && this.entity.getRage() < 5) {
            this.entity.setRage(this.entity.getRage() + 1);
            this.entity.hit = false;
         }
      }
   }

   class RemnantAttackModeGoal extends Goal {
      private final Ancient_Remnant_Entity mob;
      private LivingEntity target;
      private int circlingTime = 0;
      private final float huntingTime = 0.0F;
      private float circleDistance = 9.0F;
      private boolean clockwise = false;

      public RemnantAttackModeGoal(Ancient_Remnant_Entity mob) {
         this.mob = mob;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         this.target = this.mob.getTarget();
         return this.target != null && this.target.isAlive() && this.mob.getAttackState() == 0;
      }

      public boolean canContinueToUse() {
         LivingEntity target = this.mob.getTarget();
         if (target == null) {
            return false;
         } else if (!target.isAlive()) {
            return false;
         } else {
            return !this.mob.isWithinRestriction(target.blockPosition())
               ? false
               : !(target instanceof Player) || !target.isSpectator() && !((Player)target).isCreative();
         }
      }

      public void start() {
         this.mob.mode = Ancient_Remnant_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.circleDistance = 18 + this.mob.random.nextInt(10);
         this.clockwise = this.mob.random.nextBoolean();
         this.mob.setAggressive(true);
      }

      public void stop() {
         this.mob.mode = Ancient_Remnant_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.circleDistance = 18 + this.mob.random.nextInt(10);
         this.clockwise = this.mob.random.nextBoolean();
         this.target = this.mob.getTarget();
         if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(this.target)) {
            this.mob.setTarget(null);
         }

         this.mob.getNavigation().stop();
         if (this.mob.getTarget() == null) {
            this.mob.setAggressive(false);
            this.mob.getNavigation().stop();
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity target = this.mob.getTarget();
         if (target != null) {
            if (this.mob.mode == Ancient_Remnant_Entity.AttackMode.CIRCLE) {
               this.circlingTime++;
               Ancient_Remnant_Entity.this.circleEntity(target, this.circleDistance, 1.0F, this.clockwise, this.circlingTime, 0.0F, 1.0F);
               if (0.0F >= this.mob.hunting_cooldown) {
                  this.mob.mode = Ancient_Remnant_Entity.AttackMode.MELEE;
               } else if (this.mob.distanceTo(target) < 4.0) {
                  this.mob.mode = Ancient_Remnant_Entity.AttackMode.MELEE;
               }
            } else if (this.mob.mode == Ancient_Remnant_Entity.AttackMode.MELEE) {
               this.mob.getNavigation().moveTo(target, 1.0);
               this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
         }
      }
   }

   static class RemnantAwakenGoal extends InternalStateGoal {
      protected final Ancient_Remnant_Entity entity;

      public RemnantAwakenGoal(Ancient_Remnant_Entity entity, int getAttackState, int attackstate, int attackendstate, int attackMaxtick, int attackseetick) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick);
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         return this.entity.getNecklace() && super.canUse();
      }
   }

   static class RemnantChargeGoal extends InternalAttackGoal {
      protected final Ancient_Remnant_Entity entity;
      private final float random;

      public RemnantChargeGoal(
         Ancient_Remnant_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         float random
      ) {
         super(entity, getattackstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.random = random;
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse()
            && this.entity.getRage() >= 5
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.mode == Ancient_Remnant_Entity.AttackMode.MELEE;
      }

      @Override
      public void start() {
         super.start();
         this.entity.setRage(0);
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.lookAt(target, 30.0F, 30.0F);
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }
      }

      @Override
      public void tick() {
         super.tick();
         this.entity.getNavigation().stop();
      }

      @Override
      public void stop() {
         super.stop();
         if (!this.entity.hit && this.entity.getRage() < 5) {
            this.entity.setRage(this.entity.getRage() + 1);
         }
      }
   }

   class RemnantDoNothingGoal extends Goal {
      public RemnantDoNothingGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean canUse() {
         return !Ancient_Remnant_Entity.this.getNecklace();
      }

      public void tick() {
         Ancient_Remnant_Entity.this.setDeltaMovement(0.0, Ancient_Remnant_Entity.this.getDeltaMovement().y, 0.0);
      }

      public boolean isInterruptable() {
         return false;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class RemnantMonolithAttackGoal extends InternalAttackGoal {
      protected final Ancient_Remnant_Entity entity;
      private final float random;

      public RemnantMonolithAttackGoal(
         Ancient_Remnant_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         float random
      ) {
         super(entity, getattackstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.random = random;
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && target.isAlive()
            && (
               this.entity.monoltih_cooldown <= 0 && this.entity.getRandom().nextFloat() * 100.0F < this.random && target.getY() >= this.entity.getY() + 8.0
                  || this.entity.monoltih_cooldown <= 0 && this.entity.getRandom().nextFloat() * 100.0F < this.random && this.entity.distanceTo(target) > 12.0
                  || this.entity.monoltih_cooldown <= 0
                     && this.entity.getRandom().nextFloat() * 100.0F < this.random
                     && this.entity.distanceTo(target) < this.attackrange
            )
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.mode == Ancient_Remnant_Entity.AttackMode.MELEE;
      }

      @Override
      public void start() {
         super.start();
         this.entity.hit = false;
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.lookAt(target, 30.0F, 30.0F);
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.monoltih_cooldown = 200;
      }

      @Override
      public void tick() {
         super.tick();
         LivingEntity target = this.entity.getTarget();
         if (this.entity.attackTicks == this.attackseetick && target != null) {
            double d1 = target.getY();
            float f = (float)Mth.atan2(target.getZ() - this.entity.getZ(), target.getX() - this.entity.getX());
            this.StrikeWindmillMonolith(8, 16, 2.0, 0.75, 0.6, d1, 1);

            for (int l = 0; l < 16; l++) {
               double d2 = 1.25 * (l + 1);
               int j = (int)(5.0F + 1.5F * l);
               this.spawnSpikeLine(this.entity.getX() + Mth.cos(f) * d2, this.entity.getZ() + Mth.sin(f) * d2, d1, f, j);
            }
         }
      }

      private void StrikeWindmillMonolith(
         int numberOfBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, double spawnY, int delay
      ) {
         float angleIncrement = (float)((Math.PI * 2) / numberOfBranches);

         for (int branch = 0; branch < numberOfBranches; branch++) {
            float baseAngle = angleIncrement * branch;

            for (int i = 0; i < particlesPerBranch; i++) {
               double currentRadius = initialRadius + i * radiusIncrement;
               float currentAngle = (float)(baseAngle + i * angleIncrement / initialRadius + (float)(i * curveFactor));
               double xOffset = currentRadius * Math.cos(currentAngle);
               double zOffset = currentRadius * Math.sin(currentAngle);
               double spawnX = this.entity.getX() + xOffset;
               double spawnZ = this.entity.getZ() + zOffset;
               int d3 = delay * (i + 1);
               this.spawnSpikeLine(spawnX, spawnZ, spawnY, currentAngle, d3);
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
         } while (blockpos.getY() < Math.min(this.entity.level().getMaxBuildHeight(), this.entity.getBlockY() + 20));

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
                  (float)CMCommonConfig.AncientRemnant.AncientDesertSteledamage,
                  this.entity
               )
            );
      }
   }

   static class RemnantPhaseChangeGoal extends InternalAttackGoal {
      protected final Ancient_Remnant_Entity entity;

      public RemnantPhaseChangeGoal(
         Ancient_Remnant_Entity entity, int getattackstate, int attackstate, int attackendstate, int attackMaxtick, int attackseetick
      ) {
         super(entity, getattackstate, attackstate, attackendstate, attackMaxtick, attackseetick, 0.0F);
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         return !this.entity.getIsPower() && this.entity.getAttackState() == this.getattackstate && this.entity.isPower();
      }
   }

   static class RemnantStompGoal extends Goal {
      protected final Ancient_Remnant_Entity entity;
      private final int getattackstate;
      private final int attackstate1;
      private final int attackstate2;
      private final int attackstate3;
      private final int attackstate4;
      private final int attackendstate;
      private final int attackMaxtick1;
      private final int attackMaxtick2;
      private final int attackseetick;
      private final double attackrange;
      private final float random;

      public RemnantStompGoal(
         Ancient_Remnant_Entity entity,
         int getattackstate,
         int attackstate1,
         int attackstate2,
         int attackstate3,
         int attackstate4,
         int attackendstate,
         int attackMaxtick1,
         int attackMaxtick2,
         int attackseetick,
         double attackrange,
         float random
      ) {
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackstate1 = attackstate1;
         this.attackstate2 = attackstate2;
         this.attackstate3 = attackstate3;
         this.attackstate4 = attackstate4;
         this.attackendstate = attackendstate;
         this.attackMaxtick1 = attackMaxtick1;
         this.attackMaxtick2 = attackMaxtick2;
         this.attackseetick = attackseetick;
         this.attackrange = attackrange;
         this.random = random;
      }

      public boolean isInterruptable() {
         return false;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && this.entity.stomp_cooldown <= 0
            && target.isAlive()
            && this.entity.distanceTo(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.mode == Ancient_Remnant_Entity.AttackMode.MELEE
            && target.getY() <= this.entity.getY() + 4.5
            && this.entity.distanceTo(target) > 5.5;
      }

      public void start() {
         if (this.entity.getIsPower()) {
            if (this.entity.random.nextBoolean()) {
               this.entity.setAttackState(this.attackstate3);
            } else {
               this.entity.setAttackState(this.attackstate4);
            }
         } else if (this.entity.random.nextBoolean()) {
            this.entity.setAttackState(this.attackstate1);
         } else {
            this.entity.setAttackState(this.attackstate2);
         }

         this.entity.hit = false;
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.lookAt(target, 30.0F, 30.0F);
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }
      }

      public void stop() {
         this.entity.setAttackState(this.attackendstate);
         this.entity.stomp_cooldown = 110;
         if (!this.entity.hit && this.entity.getRage() < 5) {
            this.entity.setRage(this.entity.getRage() + 1);
            this.entity.hit = false;
         }
      }

      public boolean canContinueToUse() {
         if (this.entity.getAttackState() == this.attackstate1 || this.entity.getAttackState() == this.attackstate2) {
            return this.entity.attackTicks <= this.attackMaxtick1;
         } else {
            return this.entity.getAttackState() != this.attackstate3 && this.entity.getAttackState() != this.attackstate4
               ? false
               : this.entity.attackTicks <= this.attackMaxtick2;
         }
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.attackTicks < this.attackseetick && target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            this.entity.lookAt(target, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }
      }
   }
}
