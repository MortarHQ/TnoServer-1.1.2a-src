package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.blockentities.Boss_Respawn_Spawner_Block_Entity;
import com.github.L_Ender.cataclysm.client.particle.Options.LightningParticleOptions;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AI.HurtByNearestTargetGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackAniamtionGoal3;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Homing_Missile_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Howitzer_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Missile_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class The_Harbinger_Entity extends LLibrary_Boss_Monster implements RangedAttackMob, PowerableMob {
   public static final Animation DEATHLASER_ANIMATION = Animation.create(124);
   public static final Animation CHARGE_ANIMATION = Animation.create(45);
   public static final Animation DEATH_ANIMATION = Animation.create(144);
   public static final Animation LAUNCH_ANIAMATION = Animation.create(59);
   public static final Animation MISSILE_FIRE_ANIAMATION = Animation.create(118);
   public static final Animation MISSILE_FIRE_FAST_ANIAMATION = Animation.create(96);
   public static final Animation STUN_ANIAMATION = Animation.create(105);
   public static final int SKILL_COOLDOWN = 240;
   private static final EntityDataAccessor<Integer> FIRST_HEAD_TARGET = SynchedEntityData.defineId(The_Harbinger_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> SECOND_HEAD_TARGET = SynchedEntityData.defineId(The_Harbinger_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> THIRD_HEAD_TARGET = SynchedEntityData.defineId(The_Harbinger_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> OVERLOAD = SynchedEntityData.defineId(The_Harbinger_Entity.class, EntityDataSerializers.INT);
   private static final List<EntityDataAccessor<Integer>> HEAD_TARGETS = ImmutableList.of(FIRST_HEAD_TARGET, SECOND_HEAD_TARGET, THIRD_HEAD_TARGET);
   private static final EntityDataAccessor<Boolean> LASER_MODE = SynchedEntityData.defineId(The_Harbinger_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> ISCHARGE = SynchedEntityData.defineId(The_Harbinger_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_ACT = SynchedEntityData.defineId(The_Harbinger_Entity.class, EntityDataSerializers.BOOLEAN);
   public static final int MODE_CHANGE_COOLDOWN = 300;
   private final float[] xRotHeads = new float[2];
   private final float[] yRotHeads = new float[2];
   private final float[] xRotOHeads = new float[2];
   private final float[] yRotOHeads = new float[2];
   private final int[] nextHeadUpdate = new int[2];
   private final int[] idleHeadUpdates = new int[2];
   public float Laser_Mode_Progress;
   public float prev_Laser_Mode_Progress;
   public float deactivateProgress;
   public float prevdeactivateProgress;
   private int destroyBlocksTick;
   private int blockBreakCounter;
   private final CMBossInfoServer bossEvent = new CMBossInfoServer(this.getDisplayName(), BossBarColor.RED, true, 4);
   private int mode_change_cooldown = 0;
   private int skill_cooldown = 160;
   private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = p_31504_ -> p_31504_.attackable() && !p_31504_.getType().is(ModTag.TEAM_THE_HARBINGER);
   private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forCombat().range(20.0).selector(LIVING_ENTITY_SELECTOR);

   public The_Harbinger_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 300;
      this.moveControl = new FlyingMoveControl(this, 10, false);
      this.setConfigattribute(this, CMCommonConfig.Harbinger.healthMultiplier, CMCommonConfig.Harbinger.attackMultiplier);
   }

   protected PathNavigation createNavigation(Level p_186262_) {
      FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_186262_);
      flyingpathnavigation.setCanOpenDoors(false);
      flyingpathnavigation.setCanFloat(true);
      flyingpathnavigation.setCanPassDoors(true);
      return flyingpathnavigation;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         NO_ANIMATION,
         DEATHLASER_ANIMATION,
         CHARGE_ANIMATION,
         DEATH_ANIMATION,
         LAUNCH_ANIAMATION,
         MISSILE_FIRE_ANIAMATION,
         STUN_ANIAMATION,
         MISSILE_FIRE_FAST_ANIAMATION
      };
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new The_Harbinger_Entity.AwakenGoal());
      this.goalSelector.addGoal(1, new The_Harbinger_Entity.DeathLaserGoal(this, DEATHLASER_ANIMATION));
      this.goalSelector.addGoal(1, new The_Harbinger_Entity.ChargeGoal(this, CHARGE_ANIMATION));
      this.goalSelector.addGoal(1, new The_Harbinger_Entity.LaunchGoal(this, LAUNCH_ANIAMATION));
      this.goalSelector.addGoal(1, new The_Harbinger_Entity.MissileLaunchGoal(this, MISSILE_FIRE_ANIAMATION));
      this.goalSelector.addGoal(1, new The_Harbinger_Entity.MissileLaunchGoal2(this, MISSILE_FIRE_FAST_ANIAMATION));
      this.goalSelector.addGoal(1, new AttackAniamtionGoal3<>(this, STUN_ANIAMATION));
      this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 40, 20.0F));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByNearestTargetGoal(this));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));
   }

   public static Builder harbinger() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 390.0)
         .add(Attributes.MOVEMENT_SPEED, 0.6F)
         .add(Attributes.ATTACK_DAMAGE, 9.0)
         .add(Attributes.FLYING_SPEED, 0.6F)
         .add(Attributes.FOLLOW_RANGE, 40.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
         .add(Attributes.ARMOR, 12.0);
   }

   @Override
   public float DamageCap() {
      return (float)CMCommonConfig.Harbinger.damageCap;
   }

   @Override
   public float NatureRegen() {
      return (float)CMCommonConfig.Harbinger.natureHeal;
   }

   @Override
   public float DpsCap() {
      return (float)CMCommonConfig.Harbinger.dpsCap;
   }

   @Override
   public double RangeLimit() {
      return CMCommonConfig.Harbinger.rangeCap;
   }

   protected int decreaseAirSupply(int air) {
      return air;
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
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(LASER_MODE, false);
      p_326229_.define(ISCHARGE, false);
      p_326229_.define(IS_ACT, true);
      p_326229_.define(FIRST_HEAD_TARGET, 0);
      p_326229_.define(SECOND_HEAD_TARGET, 0);
      p_326229_.define(THIRD_HEAD_TARGET, 0);
      p_326229_.define(OVERLOAD, 0);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("Is_Act", this.getIsAct());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setIsAct(compound.getBoolean("Is_Act"));
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      Entity entity1 = source.getEntity();
      if (entity1 instanceof The_Harbinger_Entity) {
         return false;
      }

      for (int i = 0; i < this.idleHeadUpdates.length; i++) {
         this.idleHeadUpdates[i] = this.idleHeadUpdates[i] + 3;
      }

      if (this.destroyBlocksTick <= 0) {
         this.destroyBlocksTick = 20;
      }

      if (this.getAnimation() != STUN_ANIAMATION && this.getAnimation() != DEATHLASER_ANIMATION && source.is(CMDamageTypes.EMP)) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this, STUN_ANIAMATION);
      }

      if (this.isPowered()) {
         Entity entity = source.getDirectEntity();
         if (entity instanceof AbstractArrow) {
            return false;
         }
      }

      return this.deactivateProgress > 0.0F && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) ? false : super.hurt(source, damage);
   }

   public boolean canBeSeenAsEnemy() {
      return this.getIsAct() && super.canBeSeenAsEnemy();
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_THE_HARBINGER) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   public void aiStep() {
      Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.6, 1.0);
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
      this.prev_Laser_Mode_Progress = this.Laser_Mode_Progress;
      if (this.getIsLaserMode() && this.Laser_Mode_Progress < 30.0F) {
         this.Laser_Mode_Progress++;
      }

      if (!this.getIsLaserMode() && this.Laser_Mode_Progress > 0.0F) {
         this.Laser_Mode_Progress--;
      }

      this.prevdeactivateProgress = this.deactivateProgress;
      if (!this.getIsAct() && this.deactivateProgress < 40.0F) {
         this.deactivateProgress = 40.0F;
      }

      if (this.getIsAct() && this.deactivateProgress > 0.0F) {
         this.deactivateProgress--;
      }

      if (this.getIsAct() && this.skill_cooldown > 0) {
         this.skill_cooldown--;
      }

      Entity entity = this.level().getEntity(this.getAlternativeTarget(0));
      if (!this.level().isClientSide
         && this.getAlternativeTarget(0) > 0
         && this.isAlive()
         && !this.getIsCharge()
         && this.getAnimation() != STUN_ANIAMATION
         && entity != null) {
         double d0 = vec3.y;
         double l0 = this.getAnimation() != MISSILE_FIRE_FAST_ANIAMATION
               && this.getAnimation() != MISSILE_FIRE_ANIAMATION
               && this.getAnimation() != LAUNCH_ANIAMATION
            ? 2.25
            : 1.0;
         if (this.getY() < entity.getY() + l0) {
            d0 = Math.max(0.0, d0);
            d0 += 0.3 - d0 * 0.6F;
         }

         vec3 = new Vec3(vec3.x, d0, vec3.z);
         Vec3 vec31 = new Vec3(entity.getX() - this.getX(), 0.0, entity.getZ() - this.getZ());
         if (vec31.horizontalDistanceSqr() > 25.0
            && (this.getAnimation() != DEATHLASER_ANIMATION || this.getAnimationTick() <= 13)
            && this.getAnimation() != MISSILE_FIRE_ANIAMATION
            && this.getAnimation() != MISSILE_FIRE_FAST_ANIAMATION) {
            Vec3 vec32 = vec31.normalize();
            vec3 = vec3.add(vec32.x * 0.3 - vec3.x * 0.6, 0.0, vec32.z * 0.3 - vec3.z * 0.6);
         }
      }

      LivingEntity target = this.getTarget();
      if (this.getIsAct()
         && this.isAlive()
         && this.deactivateProgress == 0.0F
         && target != null
         && target.isAlive()
         && this.skill_cooldown <= 0
         && (this.Laser_Mode_Progress == 30.0F || this.Laser_Mode_Progress == 0.0F)) {
         if (!this.isNoAi() && this.getAnimation() == NO_ANIMATION && this.getOverload() >= 3 && this.getRandom().nextFloat() * 100.0F < 3.0F) {
            this.skill_cooldown = 240;
            this.setAnimation(DEATHLASER_ANIMATION);
         } else if (this.isNoAi()
            || this.getAnimation() != NO_ANIMATION
            || this.getOverload() >= 3
            || !(this.distanceToSqr(target) < 64.0)
            || (!(this.getRandom().nextFloat() * 100.0F < 4.0F) || !this.hasLineOfSight(target))
               && (!(this.getRandom().nextFloat() * 100.0F < 20.0F) || this.hasLineOfSight(target))) {
            if (!this.isNoAi()
               && this.getAnimation() == NO_ANIMATION
               && this.getOverload() < 3
               && this.getRandom().nextFloat() * 100.0F < 3.0F
               && this.Laser_Mode_Progress == 0.0F) {
               this.skill_cooldown = 240;
               this.setAnimation(LAUNCH_ANIAMATION);
            } else if (!this.isNoAi() && this.getAnimation() == NO_ANIMATION && this.getOverload() < 3 && this.getRandom().nextFloat() * 100.0F < 1.5F) {
               this.skill_cooldown = 240;
               Animation missile = this.isPowered() ? MISSILE_FIRE_FAST_ANIAMATION : MISSILE_FIRE_ANIAMATION;
               this.setAnimation(missile);
            }
         } else {
            this.skill_cooldown = 240;
            this.setAnimation(CHARGE_ANIMATION);
         }
      }

      this.setDeltaMovement(vec3);
      if (vec3.horizontalDistanceSqr() > 0.05) {
         this.setYRot((float)Mth.atan2(vec3.z, vec3.x) * (180.0F / (float)Math.PI) - 90.0F);
      }

      super.aiStep();

      for (int i = 0; i < 2; i++) {
         this.yRotOHeads[i] = this.yRotHeads[i];
         this.xRotOHeads[i] = this.xRotHeads[i];
      }

      if (this.getAnimation() != CHARGE_ANIMATION && this.getIsCharge()) {
         this.setIsCharge(false);
      }

      if (this.getAnimation() == STUN_ANIAMATION && this.getAnimationTick() == 15) {
         this.level()
            .playSound((Player)null, this, (SoundEvent)ModSounds.HARBINGER_STUN.get(), SoundSource.HOSTILE, 4.0F, this.level().random.nextFloat() * 0.2F + 1.0F);
      }

      if (this.getAnimation() == DEATHLASER_ANIMATION && this.getAnimationTick() == 33) {
         this.level().playSound((Player)null, this, (SoundEvent)ModSounds.DEATH_LASER.get(), SoundSource.HOSTILE, 4.0F, 0.75F);
      }

      if (this.getAnimation() == CHARGE_ANIMATION && this.getAnimationTick() == 24) {
         this.level().playSound((Player)null, this, (SoundEvent)ModSounds.HARBINGER_CHARGE.get(), SoundSource.HOSTILE, 4.0F, 0.65F);
      }

      if ((this.getAnimation() == MISSILE_FIRE_ANIAMATION || this.getAnimation() == MISSILE_FIRE_FAST_ANIAMATION) && this.getAnimationTick() == 24) {
         this.level().playSound((Player)null, this, (SoundEvent)ModSounds.HARBINGER_PREPARE.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
      }

      for (int j = 0; j < 2; j++) {
         int k = this.getAlternativeTarget(j + 1);
         Entity entity1 = null;
         if (k > 0) {
            entity1 = this.level().getEntity(k);
         }

         if (entity1 != null) {
            double d9 = this.getHeadX(j + 1);
            double d1 = this.getHeadY(j + 1);
            double d3 = this.getHeadZ(j + 1);
            double d4 = entity1.getX() - d9;
            double d5 = entity1.getEyeY() - d1;
            double d6 = entity1.getZ() - d3;
            double d7 = Math.sqrt(d4 * d4 + d6 * d6);
            float f = (float)(Mth.atan2(d6, d4) * 180.0F / (float)Math.PI) - 90.0F;
            float f1 = (float)(-(Mth.atan2(d5, d7) * 180.0F / (float)Math.PI));
            this.xRotHeads[j] = this.m_31442_(this.xRotHeads[j], f1, 40.0F);
            this.yRotHeads[j] = this.m_31442_(this.yRotHeads[j], f, 10.0F);
         } else {
            this.yRotHeads[j] = this.m_31442_(this.yRotHeads[j], this.yBodyRot, 10.0F);
         }
      }

      if (this.level().isClientSide && this.getIsAct()) {
         double d0 = this.random.nextFloat() - 0.5F + this.getDeltaMovement().x;
         double d1 = this.random.nextFloat() - 0.5F + this.getDeltaMovement().y;
         double d2 = this.random.nextFloat() - 0.5F + this.getDeltaMovement().z;
         double dist = 1.0F + this.random.nextFloat() * 0.2F;
         double d3 = d0 * dist;
         double d4 = d1 * dist;
         double d5 = d2 * dist;
         this.level().addParticle(new LightningParticleOptions(255, 51, 0), this.getX() + d0, this.getY() + 2.0, this.getZ() + d2, d3, d4, d5);
         if (entity != null && this.getAnimation() != MISSILE_FIRE_ANIAMATION) {
            float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
            float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
            double theta = this.yBodyRot * (Math.PI / 180.0);
            theta += Math.PI / 2;
            double vecX = Math.cos(theta);
            double vecZ = Math.sin(theta);
            double vec = -1.75;
            double math = 1.35;

            for (int i1 = 0; i1 < 5; i1++) {
               float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
               double extraX = 0.2F * Mth.sin((float)(Math.PI + angle));
               double extraY = 2.75;
               double extraZ = 0.2F * Mth.cos(angle);
               this.level()
                  .addParticle(
                     ParticleTypes.SMOKE,
                     this.getX() + vec * vecX + extraX + f * math,
                     this.getY() + extraY,
                     this.getZ() + vec * vecZ + extraZ + f1 * math,
                     0.0,
                     -0.07,
                     0.0
                  );
               this.level()
                  .addParticle(
                     ParticleTypes.SMOKE,
                     this.getX() + vec * vecX + extraX + f * -math,
                     this.getY() + extraY,
                     this.getZ() + vec * vecZ + extraZ + f1 * -math,
                     0.0,
                     -0.07,
                     0.0
                  );
            }
         }

         if (this.getAnimation() == STUN_ANIAMATION) {
            for (int i = 0; i < 2; i++) {
               this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0);
            }
         }
      }
   }

   private void blockbreak() {
      if (this.getIsCharge()) {
         if (!this.level().isClientSide && EventHooks.canEntityGrief(this.level(), this)) {
            boolean flag = false;
            AABB aabb = this.getBoundingBox().inflate(1.5, 0.2, 1.5);

            for (BlockPos blockpos : BlockPos.betweenClosed(
               Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
            )) {
               BlockState blockstate = this.level().getBlockState(blockpos);
               if (!blockstate.isAir()
                  && blockstate.canEntityDestroy(this.level(), blockpos, this)
                  && !blockstate.is(ModTag.HARBINGER_IMMUNE)
                  && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)) {
                  if (this.random.nextInt(3) == 0 && !blockstate.hasBlockEntity()) {
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
                                    (-1.2 + this.random.nextDouble()) / 3.0,
                                    0.2 + this.getRandom().nextGaussian() * 0.15,
                                    (-1.2 + this.random.nextDouble()) / 3.0
                                 )
                           )
                     );
                     this.level().addFreshEntity(fallingBlockEntity);
                  } else {
                     flag = this.level().destroyBlock(blockpos, false, this) || flag;
                  }
               }
            }

            if (flag) {
               this.level().levelEvent((Player)null, 1022, this.blockPosition(), 0);
            }
         }

         if (this.tickCount % 4 == 0) {
            for (LivingEntity Lentity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.5))) {
               if (!this.isAlliedTo(Lentity) && !(Lentity instanceof The_Harbinger_Entity) && Lentity != this) {
                  boolean flag = Lentity.hurt(
                     this.damageSources().mobAttack(this),
                     (float)(
                        (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) + this.random.nextInt(5)
                           + Math.min(this.getAttributeValue(Attributes.ATTACK_DAMAGE), Lentity.getMaxHealth() * (float)CMCommonConfig.Harbinger.ChargeHpDamage)
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

   private void destoryblock2() {
      if (this.blockBreakCounter > 0) {
         this.blockBreakCounter--;
      } else {
         boolean flag = false;
         if (this.getAnimation() == NO_ANIMATION
            && !this.getIsCharge()
            && !this.level().isClientSide
            && this.blockBreakCounter == 0
            && EventHooks.canEntityGrief(this.level(), this)) {
            AABB aabb = this.getBoundingBox().inflate(0.2);

            for (BlockPos pos : BlockPos.betweenClosed(
               Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
            )) {
               BlockState blockstate = this.level().getBlockState(pos);
               if (!blockstate.isAir() && blockstate.canEntityDestroy(this.level(), pos, this) && !blockstate.is(ModTag.HARBINGER_IMMUNE)) {
                  if (this.random.nextInt(5) == 0 && !blockstate.hasBlockEntity()) {
                     Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                        this.level(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, blockstate, 20
                     );
                     flag = this.level().destroyBlock(pos, false, this) || flag;
                     fallingBlockEntity.setDeltaMovement(
                        fallingBlockEntity.getDeltaMovement()
                           .add(
                              this.position()
                                 .subtract(fallingBlockEntity.position())
                                 .multiply(
                                    (-1.2 + this.random.nextDouble()) / 3.0,
                                    0.2 + this.getRandom().nextGaussian() * 0.15,
                                    (-1.2 + this.random.nextDouble()) / 3.0
                                 )
                           )
                     );
                     this.level().addFreshEntity(fallingBlockEntity);
                  } else {
                     flag = this.level().destroyBlock(pos, false, this) || flag;
                     this.setDeltaMovement(this.getDeltaMovement().multiply(0.6F, 1.0, 0.6F));
                  }
               }
            }
         }

         if (flag) {
            this.blockBreakCounter = 20;
            this.level().levelEvent((Player)null, 1022, this.blockPosition(), 0);
         }
      }
   }

   private void destroyBlock() {
      if (this.getAnimation() != STUN_ANIAMATION && !this.level().isClientSide && this.destroyBlocksTick > 0) {
         this.destroyBlocksTick--;
         if (this.destroyBlocksTick == 0 && EventHooks.canEntityGrief(this.level(), this)) {
            int j1 = Mth.floor(this.getY());
            int i2 = Mth.floor(this.getX());
            int j2 = Mth.floor(this.getZ());
            boolean flag = false;

            for (int j = -1; j <= 1; j++) {
               for (int k2 = -1; k2 <= 1; k2++) {
                  for (int k = 0; k <= 3; k++) {
                     int l2 = i2 + j;
                     int l = j1 + k;
                     int i1 = j2 + k2;
                     BlockPos blockpos = new BlockPos(l2, l, i1);
                     BlockState blockstate = this.level().getBlockState(blockpos);
                     if (!blockstate.isAir()
                        && blockstate.canEntityDestroy(this.level(), blockpos, this)
                        && !blockstate.is(ModTag.HARBINGER_IMMUNE)
                        && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)) {
                        if (this.random.nextInt(5) == 0 && !blockstate.hasBlockEntity()) {
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
                                          (-1.2 + this.random.nextDouble()) / 3.0,
                                          0.2 + this.getRandom().nextGaussian() * 0.15,
                                          (-1.2 + this.random.nextDouble()) / 3.0
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
            }

            if (flag) {
               this.level().levelEvent((Player)null, 1022, this.blockPosition(), 0);
            }
         }
      }
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      Item item = itemstack.getItem();
      if (item == Items.NETHER_STAR && !this.getIsAct()) {
         if (!player.isCreative()) {
            itemstack.shrink(1);
         }

         this.setIsAct(true);
         this.setHomePos(GlobalPos.of(this.level().dimension(), this.blockPosition()));
         this.heal(this.getMaxHealth());
         return InteractionResult.SUCCESS;
      } else {
         return super.mobInteract(player, hand);
      }
   }

   protected void customServerAiStep() {
      if (this.getIsAct()) {
         if (this.deactivateProgress > 0.0F) {
            float k1 = this.deactivateProgress - 1.0F;
            if (k1 <= 0.0F && !this.isSilent()) {
               this.level().globalLevelEvent(1023, this.blockPosition(), 0);
            }
         } else {
            super.customServerAiStep();

            for (int i = 1; i < 3; i++) {
               if (this.tickCount >= this.nextHeadUpdate[i - 1]) {
                  this.nextHeadUpdate[i - 1] = this.tickCount + 10 + this.random.nextInt(10);
                  int l1 = this.getAlternativeTarget(i);
                  if (l1 > 0) {
                     LivingEntity livingentity = (LivingEntity)this.level().getEntity(l1);
                     if (livingentity != null
                        && this.canAttack(livingentity)
                        && !(this.distanceToSqr(livingentity) > 1600.0)
                        && this.hasLineOfSight(livingentity)
                        && (this.Laser_Mode_Progress == 30.0F || this.Laser_Mode_Progress == 0.0F)
                        && this.getAnimation() == NO_ANIMATION) {
                        this.performRangedAttack(i + 1, livingentity);
                        int f = this.getIsLaserMode() ? 15 + this.random.nextInt(5) : 30 + this.random.nextInt(20);
                        this.nextHeadUpdate[i - 1] = this.tickCount + f;
                        this.idleHeadUpdates[i - 1] = 0;
                     } else {
                        this.setAlternativeTarget(i, 0);
                     }
                  } else {
                     List<LivingEntity> list = this.level()
                        .getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, this, this.getBoundingBox().inflate(20.0, 8.0, 20.0));
                     if (!list.isEmpty()) {
                        LivingEntity livingentity1 = list.get(this.random.nextInt(list.size()));
                        this.setAlternativeTarget(i, livingentity1.getId());
                     }
                  }
               }
            }

            this.blockbreak();
            this.destroyBlock();
            if (this.getTarget() != null) {
               this.destoryblock2();
            }

            if (this.mode_change_cooldown < 300) {
               this.mode_change_cooldown++;
            } else if (this.getAnimation() == NO_ANIMATION) {
               this.setIsLaserMode(!this.getIsLaserMode());
               this.playSound((SoundEvent)ModSounds.HARBINGER_MODE_CHANGE.get(), 3.0F, 1.0F);
               this.mode_change_cooldown = this.random.nextInt(50);
            }

            if (this.getTarget() != null) {
               this.setAlternativeTarget(0, this.getTarget().getId());
            } else {
               this.setAlternativeTarget(0, 0);
            }

            if (this.tickCount % 20 == 0) {
               this.heal((float)CMCommonConfig.Harbinger.AutoHeal);
            }
         }
      } else if (this.tickCount % 20 == 0) {
         this.heal(50.0F * (float)CMCommonConfig.Harbinger.healthMultiplier);
      }
   }

   protected SoundEvent getAmbientSound() {
      return this.getIsAct() ? (SoundEvent)ModSounds.HARBINGER_IDLE.get() : super.getAmbientSound();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.HARBINGER_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.HARBINGER_HURT.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.HARBINGER_MUSIC.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && this.getIsAct();
   }

   @Override
   protected void onDeathAIUpdate() {
      super.onDeathAIUpdate();
      this.move(MoverType.SELF, new Vec3(0.0, 0.15F, 0.0));
      this.setYRot(this.yRotO);
      this.yBodyRot = this.getYRot();
      this.yHeadRot = this.getYRot();
      if (this.deathTime == 123 && !this.level().isClientSide) {
         this.level().explode(this, this.getX(), this.getEyeY(), this.getZ(), 7.0F, false, ExplosionInteraction.MOB);
      }
   }

   @Override
   protected void AfterDefeatBoss(@Nullable LivingEntity living) {
      if (CMCommonConfig.Harbinger.respawner && !this.level().isClientSide && this.getHomePos() != null && this.level() instanceof ServerLevel serverLevel) {
         MinecraftServer server = serverLevel.getServer();
         ServerLevel targetLevel = server.getLevel(this.getHomePos().dimension());
         if (targetLevel != null) {
            BlockPos targetPos = this.getHomePos().pos();
            BlockState blockState = ((Block)ModBlocks.BOSS_RESPAWNER.get()).defaultBlockState();
            targetLevel.setBlock(targetPos, blockState, 2);
            if (targetLevel.getBlockEntity(targetPos) instanceof Boss_Respawn_Spawner_Block_Entity spawnerBlockEntity) {
               spawnerBlockEntity.setEntityId((EntityType<?>)ModEntities.THE_HARBINGER.get());
               spawnerBlockEntity.setTheItem(((Item)ModItems.MECH_EYE.get()).getDefaultInstance());
            }
         }
      }
   }

   private double getHeadX(int head) {
      if (head <= 0) {
         return this.getX();
      }

      float f = (this.yBodyRot + 180 * (head - 1)) * (float) (Math.PI / 180.0);
      float f1 = Mth.cos(f);
      double f2 = this.getIsLaserMode() ? 1.65 : 1.5;
      return this.getX() + f1 * f2;
   }

   private double getHeadY(int head) {
      return head <= 0 ? this.getY() + 3.0 : this.getY() + 2.6;
   }

   private double getHeadZ(int head) {
      if (head <= 0) {
         return this.getZ();
      }

      float f = (this.yBodyRot + 180 * (head - 1)) * (float) (Math.PI / 180.0);
      float f1 = Mth.sin(f);
      double f2 = this.getIsLaserMode() ? 1.65 : 1.5;
      return this.getZ() + f1 * f2;
   }

   private float m_31442_(float p_31443_, float p_31444_, float p_31445_) {
      float f = Mth.wrapDegrees(p_31444_ - p_31443_);
      if (f > p_31445_) {
         f = p_31445_;
      }

      if (f < -p_31445_) {
         f = -p_31445_;
      }

      return p_31443_ + f;
   }

   private void performRangedAttack(int head, LivingEntity target) {
      this.performRangedAttack(head, target.getX(), target.getY() + target.getEyeHeight() * 0.5, target.getZ());
   }

   private void performRangedAttack(int head, double targetX, double targetY, double targetZ) {
      double d0 = this.getHeadX(head);
      double d1 = this.getHeadY(head);
      double d2 = this.getHeadZ(head);
      double d3 = targetX - d0;
      double d4 = targetY - d1;
      double d5 = targetZ - d2;
      Vec3 vec3 = new Vec3(d3, d4, d5);
      if (this.getIsLaserMode()) {
         if (!this.isSilent()) {
            this.playSound((SoundEvent)ModSounds.HARBINGER_LASER.get(), 1.0F, 1.0F);
         }

         Laser_Beam_Entity laser = new Laser_Beam_Entity(this, vec3.normalize(), this.level(), (float)CMCommonConfig.Harbinger.Laserdamage);
         float yRot = (float)(Mth.atan2(vec3.z, vec3.x) * (180.0 / Math.PI)) + 90.0F;
         float xRot = (float)(-(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180.0 / Math.PI)));
         laser.setYRot(yRot);
         laser.setXRot(xRot);
         laser.setPosRaw(d0, d1, d2);
         this.level().addFreshEntity(laser);
      } else {
         if (!this.isSilent()) {
            this.playSound((SoundEvent)ModSounds.ROCKET_LAUNCH.get(), 1.0F, 0.8F);
         }

         Wither_Missile_Entity witherskull = new Wither_Missile_Entity(
            this, vec3.normalize(), this.level(), (float)CMCommonConfig.Harbinger.WitherMissiledamage
         );
         witherskull.setPosRaw(d0, d1, d2);
         this.level().addFreshEntity(witherskull);
      }
   }

   public void performRangedAttack(LivingEntity p_31468_, float p_31469_) {
      this.performRangedAttack(0, p_31468_);
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   public float getHeadYRot(int head) {
      return this.yRotHeads[head];
   }

   public float getHeadXRot(int head) {
      return this.xRotHeads[head];
   }

   public int getAlternativeTarget(int head) {
      return (Integer)this.entityData.get(HEAD_TARGETS.get(head));
   }

   public void setAlternativeTarget(int targetOffset, int newId) {
      this.entityData.set(HEAD_TARGETS.get(targetOffset), newId);
   }

   public void setIsLaserMode(boolean isLaserMode) {
      this.entityData.set(LASER_MODE, isLaserMode);
   }

   public boolean getIsLaserMode() {
      return (Boolean)this.entityData.get(LASER_MODE);
   }

   public void setIsAct(boolean isAct) {
      this.entityData.set(IS_ACT, isAct);
      this.bossEvent.setVisible(isAct);
   }

   public boolean getIsAct() {
      return (Boolean)this.entityData.get(IS_ACT);
   }

   public void setIsCharge(boolean isCharge) {
      this.entityData.set(ISCHARGE, isCharge);
   }

   public boolean getIsCharge() {
      return (Boolean)this.entityData.get(ISCHARGE);
   }

   public void setOverload(int Overload) {
      this.entityData.set(OVERLOAD, Overload);
   }

   public int getOverload() {
      return (Integer)this.entityData.get(OVERLOAD);
   }

   public boolean isPowered() {
      return this.getHealth() <= this.getMaxHealth() / 2.0F;
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   public boolean canChangeDimensions() {
      return false;
   }

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return DEATH_ANIMATION;
   }

   @Override
   public boolean canBeAffected(MobEffectInstance p_31495_) {
      return p_31495_.getEffect() != MobEffects.MOVEMENT_SLOWDOWN
         && p_31495_.getEffect() != MobEffects.POISON
         && p_31495_.getEffect() != MobEffects.WITHER
         && p_31495_.getEffect() != MobEffects.WEAKNESS
         && p_31495_.getEffect() != MobEffects.LEVITATION
         && super.canBeAffected(p_31495_);
   }

   public BossBarColor bossBarColor() {
      return BossBarColor.PURPLE;
   }

   class AwakenGoal extends Goal {
      public AwakenGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean canUse() {
         return The_Harbinger_Entity.this.deactivateProgress > 0.0F;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         The_Harbinger_Entity.this.setDeltaMovement(0.0, The_Harbinger_Entity.this.getDeltaMovement().y, 0.0);
      }
   }

   static class ChargeGoal extends SimpleAnimationGoal<The_Harbinger_Entity> {
      public ChargeGoal(The_Harbinger_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         super.start();
         this.entity.setOverload(this.entity.getOverload() + 1);
         this.entity.level().playSound((Player)null, this.entity, (SoundEvent)ModSounds.HARBINGER_CHARGE_PREPARE.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() == 24) {
            this.entity.setIsCharge(true);
         }

         if (this.entity.getAnimationTick() == 36) {
            this.entity.setIsCharge(false);
         }

         if (target != null) {
            if (this.entity.getAnimationTick() < 24) {
               this.entity.lookAt(target, 30.0F, 30.0F);
               this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            if (this.entity.getAnimationTick() == 24) {
               Vec3 rot = target.position().subtract(0.0, 2.0, 0.0).add(this.entity.position().multiply(-1.0, -1.0, -1.0)).normalize();
               this.entity.setDeltaMovement(rot.multiply(4.0, 5.0, 4.0));
            }

            if (this.entity.getAnimationTick() == 45) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, The_Harbinger_Entity.CHARGE_ANIMATION);
            }
         }
      }

      public void stop() {
         super.stop();
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getOverload() < 3 && this.entity.isPowered() && target != null && this.entity.random.nextInt(2) == 0) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, The_Harbinger_Entity.CHARGE_ANIMATION);
         }
      }
   }

   static class DeathLaserGoal extends SimpleAnimationGoal<The_Harbinger_Entity> {
      public DeathLaserGoal(The_Harbinger_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         super.start();
         this.entity.level().playSound((Player)null, this.entity, (SoundEvent)ModSounds.HARBINGER_DEATHLASER_PREPARE.get(), SoundSource.HOSTILE, 8.0F, 1.2F);
         this.entity.setOverload(0);
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
         if (this.entity.getAnimationTick() == 18 && !this.entity.level().isClientSide) {
            Death_Laser_Beam_Entity DeathBeam = new Death_Laser_Beam_Entity(
               (EntityType<? extends Death_Laser_Beam_Entity>)ModEntities.DEATH_LASER_BEAM.get(),
               this.entity.level(),
               this.entity,
               this.entity.getX(),
               this.entity.getY() + 2.9,
               this.entity.getZ(),
               (float)((this.entity.yHeadRot + 90.0F) * Math.PI / 180.0),
               (float)(-this.entity.getXRot() * Math.PI / 180.0),
               60,
               (float)CMCommonConfig.Harbinger.DeathLaserdamage,
               (float)CMCommonConfig.Harbinger.DeathLaserHpdamage
            );
            if (this.entity.isPowered()) {
               DeathBeam.setFire(true);
            }

            this.entity.level().addFreshEntity(DeathBeam);
         }

         if (this.entity.getAnimationTick() >= 35 && target != null) {
            this.entity.getLookControl().setLookAt(target.getX(), target.getY() + target.getBbHeight() / 2.0F, target.getZ(), 6.0F, 90.0F);
            this.entity.lookAt(target, 30.0F, 30.0F);
         }
      }
   }

   static class LaunchGoal extends SimpleAnimationGoal<The_Harbinger_Entity> {
      public LaunchGoal(The_Harbinger_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         super.start();
         this.entity.setOverload(this.entity.getOverload() + 1);
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            if (this.entity.getAnimationTick() == 13) {
               this.launch(2, target);
            }

            if (this.entity.getAnimationTick() == 19) {
               this.launch(1, target);
            }
         }
      }

      private void launch(int head, LivingEntity p_31459_) {
         this.launch(head, p_31459_.getX(), p_31459_.getY() + p_31459_.getEyeHeight() * 0.5, p_31459_.getZ());
      }

      private void launch(int head, double p_31450_, double p_31451_, double p_31452_) {
         if (!this.entity.isSilent()) {
            this.entity.playSound((SoundEvent)ModSounds.ROCKET_LAUNCH.get(), 1.0F, 1.0F);
         }

         double d0 = this.entity.getHeadX(head);
         double d1 = this.entity.getHeadY(head);
         double d2 = this.entity.getHeadZ(head);
         double d3 = p_31450_ - d0;
         double d4 = p_31451_ - d1;
         double d5 = p_31452_ - d2;
         double d6 = Mth.sqrt((float)(d3 * d3 + d5 * d5));
         int b = this.entity.isPowered() ? 7 : 5;

         for (int i = 0; i < b; i++) {
            Wither_Howitzer_Entity lava = new Wither_Howitzer_Entity(
               (EntityType<Wither_Howitzer_Entity>)ModEntities.WITHER_HOWITZER.get(), this.entity.level(), this.entity
            );
            lava.setPosRaw(d0, d1, d2);
            lava.setRadius(3.0F);
            lava.shoot(d3, d4 + d6 * 6.0, d5, 0.6F, 60.0F);
            this.entity.level().addFreshEntity(lava);
         }
      }
   }

   static class MissileLaunchGoal extends SimpleAnimationGoal<The_Harbinger_Entity> {
      public MissileLaunchGoal(The_Harbinger_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         super.start();
         this.entity.setOverload(this.entity.getOverload() + 1);
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.lookAt(target, 30.0F, 30.0F);
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.entity.getAnimationTick() == 80 || this.entity.getAnimationTick() == 84 || this.entity.getAnimationTick() == 88) {
               this.mlaunch(2, target);
            }

            if (this.entity.getAnimationTick() == 98 || this.entity.getAnimationTick() == 102 || this.entity.getAnimationTick() == 106) {
               this.mlaunch(1, target);
            }
         }
      }

      private void mlaunch(int head, LivingEntity target) {
         if (!this.entity.isSilent()) {
            this.entity.playSound((SoundEvent)ModSounds.ROCKET_LAUNCH.get(), 1.0F, 1.0F);
         }

         double d0 = this.getLauncherX(head);
         double d1 = this.getLauncherY(head);
         double d2 = this.getLauncherZ(head);
         double d3 = target.getX() - d0;
         double d4 = target.getY() - d1;
         double d5 = target.getZ() - d2;
         Vec3 vec3 = new Vec3(d3, d4, d5).normalize();
         Wither_Homing_Missile_Entity laserBeam = new Wither_Homing_Missile_Entity(
            this.entity, vec3, this.entity.level(), (float)CMCommonConfig.Harbinger.WitherMissiledamage, target
         );
         laserBeam.setPosRaw(d0, d1, d2);
         this.entity.level().addFreshEntity(laserBeam);
      }

      private double getLauncherX(int head) {
         if (head <= 0) {
            return this.entity.getX();
         }

         double theta = this.entity.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         float f = (this.entity.yBodyRot + 180 * (head - 1)) * (float) (Math.PI / 180.0);
         float f1 = Mth.cos(f);
         return this.entity.getX() + f1 * 1.25 + vecX * 1.35;
      }

      private double getLauncherY(int head) {
         return head <= 0 ? this.entity.getY() + 3.0 : this.entity.getY() + 3.8;
      }

      private double getLauncherZ(int head) {
         if (head <= 0) {
            return this.entity.getZ();
         }

         double theta = this.entity.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecZ = Math.sin(theta);
         float f = (this.entity.yBodyRot + 180 * (head - 1)) * (float) (Math.PI / 180.0);
         float f1 = Mth.sin(f);
         return this.entity.getZ() + f1 * 1.25 + vecZ * 1.35;
      }
   }

   static class MissileLaunchGoal2 extends SimpleAnimationGoal<The_Harbinger_Entity> {
      public MissileLaunchGoal2(The_Harbinger_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         super.start();
         this.entity.setOverload(this.entity.getOverload() + 1);
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.lookAt(target, 30.0F, 30.0F);
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.entity.getAnimationTick() == 71 || this.entity.getAnimationTick() == 75 || this.entity.getAnimationTick() == 79) {
               this.mlaunch(2, target);
               this.mlaunch(1, target);
            }
         }
      }

      private void mlaunch(int head, LivingEntity target) {
         if (!this.entity.isSilent()) {
            this.entity.playSound((SoundEvent)ModSounds.ROCKET_LAUNCH.get(), 1.0F, 1.0F);
         }

         double d0 = this.getLauncherX(head);
         double d1 = this.getLauncherY(head);
         double d2 = this.getLauncherZ(head);
         double d3 = target.getX() - d0;
         double d4 = target.getY() - d1;
         double d5 = target.getZ() - d2;
         Vec3 vec3 = new Vec3(d3, d4, d5).normalize();
         Wither_Homing_Missile_Entity laserBeam = new Wither_Homing_Missile_Entity(
            this.entity, vec3, this.entity.level(), (float)CMCommonConfig.Harbinger.WitherMissiledamage, target
         );
         laserBeam.setPosRaw(d0, d1, d2);
         this.entity.level().addFreshEntity(laserBeam);
      }

      private double getLauncherX(int head) {
         if (head <= 0) {
            return this.entity.getX();
         }

         double theta = this.entity.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         float f = (this.entity.yBodyRot + 180 * (head - 1)) * (float) (Math.PI / 180.0);
         float f1 = Mth.cos(f);
         return this.entity.getX() + f1 * 1.25 + vecX * 1.35;
      }

      private double getLauncherY(int head) {
         return head <= 0 ? this.entity.getY() + 3.0 : this.entity.getY() + 3.8;
      }

      private double getLauncherZ(int head) {
         if (head <= 0) {
            return this.entity.getZ();
         }

         double theta = this.entity.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecZ = Math.sin(theta);
         float f = (this.entity.yBodyRot + 180 * (head - 1)) * (float) (Math.PI / 180.0);
         float f1 = Mth.sin(f);
         return this.entity.getZ() + f1 * 1.25 + vecZ * 1.35;
      }
   }
}
