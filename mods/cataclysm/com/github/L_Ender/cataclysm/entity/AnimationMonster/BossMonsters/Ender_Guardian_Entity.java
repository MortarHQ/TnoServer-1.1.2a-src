package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.blockentities.Boss_Respawn_Spawner_Block_Entity;
import com.github.L_Ender.cataclysm.client.particle.Options.RingParticleOptions;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AI.HurtByNearestTargetGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AnimationGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackAnimationGoal1;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackAnimationGoal2;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackMoveGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Void_Vortex_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Ender_Guardian_Bullet_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.message.MessageMusic;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent.EnderEntity;
import net.neoforged.neoforge.network.PacketDistributor;

public class Ender_Guardian_Entity extends LLibrary_Boss_Monster {
   private final CMBossInfoServer bossInfo = new CMBossInfoServer(this.getDisplayName(), BossBarColor.PURPLE, false, 1);
   private static final EntityDataAccessor<Boolean> IS_HELMETLESS = SynchedEntityData.defineId(Ender_Guardian_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> USED_MASS_DESTRUCTION = SynchedEntityData.defineId(
      Ender_Guardian_Entity.class, EntityDataSerializers.BOOLEAN
   );
   private static final EntityDataAccessor<Optional<BlockPos>> TELEPORT_POS = SynchedEntityData.defineId(
      Ender_Guardian_Entity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS
   );
   public static final Animation GUARDIAN_RIGHT_STRONG_ATTACK = Animation.create(60);
   public static final Animation GUARDIAN_LEFT_STRONG_ATTACK = Animation.create(60);
   public static final Animation GUARDIAN_RIGHT_ATTACK = Animation.create(40);
   public static final Animation GUARDIAN_LEFT_ATTACK = Animation.create(40);
   public static final Animation GUARDIAN_BURST_ATTACK = Animation.create(53);
   public static final Animation GUARDIAN_UPPERCUT_AND_BULLET = Animation.create(100);
   public static final Animation GUARDIAN_RAGE_UPPERCUT = Animation.create(120);
   public static final Animation GUARDIAN_STOMP = Animation.create(48);
   public static final Animation GUARDIAN_RAGE_STOMP = Animation.create(83);
   public static final Animation GUARDIAN_MASS_DESTRUCTION = Animation.create(75);
   public static final Animation GUARDIAN_FALLEN = Animation.create(196);
   public static final Animation GUARDIAN_HUG_ME = Animation.create(76);
   public static final Animation GUARDIAN_AIR_STRIKE1 = Animation.create(123);
   public static final Animation GUARDIAN_AIR_STRIKE2 = Animation.create(39);
   public static final Animation GUARDIAN_RIGHT_SWING = Animation.create(42);
   public static final Animation GUARDIAN_LEFT_SWING = Animation.create(42);
   public static final Animation GUARDIAN_BLACKHOLE = Animation.create(62);
   public static final Animation GUARDIAN_ROCKETPUNCH = Animation.create(58);
   public static final int STOMP_COOLDOWN = 400;
   public static final int UPPERCUT_COOLDOWN = 200;
   public static final int TELEPORT_COOLDOWN = 280;
   public static final int TELEPORT_SMASH_COOLDOWN = 600;
   public static final int VORTEX_COOLDOWN = 280;
   private int stomp_cooldown = 0;
   private int teleport_cooldown = 0;
   private int teleport_smash_cooldown = 0;
   private int uppercut_cooldown = 0;
   private int vortexcooldown = 0;

   public Ender_Guardian_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 300;
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
      this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
      this.setConfigattribute(this, CMCommonConfig.EnderGuardian.healthMultiplier, CMCommonConfig.EnderGuardian.attackMultiplier);
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         NO_ANIMATION,
         GUARDIAN_RIGHT_STRONG_ATTACK,
         GUARDIAN_LEFT_STRONG_ATTACK,
         GUARDIAN_BURST_ATTACK,
         GUARDIAN_UPPERCUT_AND_BULLET,
         GUARDIAN_STOMP,
         GUARDIAN_RIGHT_ATTACK,
         GUARDIAN_LEFT_ATTACK,
         GUARDIAN_MASS_DESTRUCTION,
         GUARDIAN_RAGE_STOMP,
         GUARDIAN_RAGE_UPPERCUT,
         GUARDIAN_FALLEN,
         GUARDIAN_HUG_ME,
         GUARDIAN_AIR_STRIKE1,
         GUARDIAN_AIR_STRIKE2,
         GUARDIAN_LEFT_SWING,
         GUARDIAN_RIGHT_SWING,
         GUARDIAN_BLACKHOLE,
         GUARDIAN_ROCKETPUNCH
      };
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(2, new AttackMoveGoal(this, true, 1.0));
      this.goalSelector.addGoal(1, new Ender_Guardian_Entity.PunchAttackGoal(this));
      this.goalSelector.addGoal(1, new AttackAnimationGoal2<Ender_Guardian_Entity>(this, GUARDIAN_MASS_DESTRUCTION, 39, 50) {
         public void start() {
            super.start();
            this.entity.setUsedMassDestruction(true);
         }
      });
      this.goalSelector.addGoal(1, new AttackAnimationGoal2<>(this, GUARDIAN_BURST_ATTACK, 27, 47));
      this.goalSelector.addGoal(1, new Ender_Guardian_Entity.VoidVortexGoal(this, GUARDIAN_BLACKHOLE));
      this.goalSelector.addGoal(1, new Ender_Guardian_Entity.RocketPunchGoal(this, GUARDIAN_ROCKETPUNCH));
      this.goalSelector.addGoal(1, new SimpleAnimationGoal<>(this, GUARDIAN_AIR_STRIKE2));
      this.goalSelector.addGoal(1, new AttackAnimationGoal1<>(this, GUARDIAN_RIGHT_SWING, 26, true));
      this.goalSelector.addGoal(1, new AttackAnimationGoal1<>(this, GUARDIAN_LEFT_SWING, 26, true));
      this.goalSelector.addGoal(1, new Ender_Guardian_Entity.HugmeGoal(this, GUARDIAN_HUG_ME, 30.0F, 20));
      this.goalSelector.addGoal(1, new Ender_Guardian_Entity.TeleportStrikeGoal(this, GUARDIAN_AIR_STRIKE1));
      this.goalSelector.addGoal(1, new Ender_Guardian_Entity.StompAttackGoal(this));
      this.goalSelector.addGoal(1, new Ender_Guardian_Entity.UppercutAndBulletGoal(this, GUARDIAN_UPPERCUT_AND_BULLET));
      this.goalSelector.addGoal(1, new Ender_Guardian_Entity.RageUppercut(this, GUARDIAN_RAGE_UPPERCUT));
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByNearestTargetGoal(this));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   public static Builder ender_guardian() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 50.0)
         .add(Attributes.MOVEMENT_SPEED, 0.27F)
         .add(Attributes.ATTACK_DAMAGE, 16.0)
         .add(Attributes.MAX_HEALTH, 333.0)
         .add(Attributes.ARMOR, 20.0)
         .add(Attributes.STEP_HEIGHT, 1.75)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(IS_HELMETLESS, false);
      p_326229_.define(TELEPORT_POS, Optional.empty());
      p_326229_.define(USED_MASS_DESTRUCTION, true);
   }

   private static Animation getRandomAttack(RandomSource rand) {
      switch (rand.nextInt(4)) {
         case 0:
            return GUARDIAN_RIGHT_STRONG_ATTACK;
         case 1:
            return GUARDIAN_LEFT_STRONG_ATTACK;
         case 2:
            return GUARDIAN_RIGHT_ATTACK;
         case 3:
            return GUARDIAN_LEFT_ATTACK;
         default:
            return GUARDIAN_RIGHT_STRONG_ATTACK;
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      Optional<BlockPos> restPos = this.getTeleportPos();
      if (restPos.isPresent()) {
         compound.put("TeleportPos", NbtUtils.writeBlockPos(this.getTeleportPos().get()));
      }

      compound.putBoolean("is_Helmetless", this.getIsHelmetless());
      compound.putBoolean("used_mass_destruction", this.getUsedMassDestruction());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      NbtUtils.readBlockPos(compound, "TeleportPos").ifPresent(this::setTeleportPos);
      this.setIsHelmetless(compound.getBoolean("is_Helmetless"));
      this.setUsedMassDestruction(compound.getBoolean("used_mass_destruction"));
      if (this.hasCustomName()) {
         this.bossInfo.setName(this.getDisplayName());
      }
   }

   public void setIsHelmetless(boolean isHelmetless) {
      if (isHelmetless) {
         this.getAttribute(Attributes.ARMOR).setBaseValue(15.0);
         this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.29F);
      } else {
         this.getAttribute(Attributes.ARMOR).setBaseValue(20.0);
         this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.27F);
      }

      this.entityData.set(IS_HELMETLESS, isHelmetless);
   }

   public boolean getIsHelmetless() {
      return (Boolean)this.entityData.get(IS_HELMETLESS);
   }

   public void setUsedMassDestruction(boolean usedMassDestruction) {
      this.entityData.set(USED_MASS_DESTRUCTION, usedMassDestruction);
   }

   public boolean getUsedMassDestruction() {
      return (Boolean)this.entityData.get(USED_MASS_DESTRUCTION);
   }

   public void setTeleportPos(BlockPos p_30220_) {
      this.entityData.set(TELEPORT_POS, Optional.of(p_30220_));
   }

   public Optional<BlockPos> getTeleportPos() {
      return (Optional<BlockPos>)this.entityData.get(TELEPORT_POS);
   }

   public void setCustomName(@Nullable Component name) {
      super.setCustomName(name);
      this.bossInfo.setName(this.getDisplayName());
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if (this.getAnimation() == GUARDIAN_MASS_DESTRUCTION && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      }

      Entity entity = source.getDirectEntity();
      if (!this.getIsHelmetless() && entity instanceof AbstractArrow) {
         return false;
      }

      if (!(entity instanceof ShulkerBullet) && !(entity instanceof Ender_Guardian_Bullet_Entity)) {
         if (entity instanceof AbstractGolem) {
            damage = (float)(damage * 0.5);
         }

         boolean attack = super.hurt(source, damage);
         if (attack && this.getIsHelmetless()) {
            this.playSound(SoundEvents.SHULKER_HURT, 1.0F, 0.8F);
         }

         return attack;
      } else {
         return false;
      }
   }

   @Override
   public float DamageCap() {
      return (float)CMCommonConfig.EnderGuardian.damageCap;
   }

   @Override
   public float NatureRegen() {
      return (float)CMCommonConfig.EnderGuardian.natureHeal;
   }

   @Override
   public float DpsCap() {
      return (float)CMCommonConfig.EnderGuardian.dpsCap;
   }

   @Override
   public double RangeLimit() {
      return CMCommonConfig.EnderGuardian.rangeCap;
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return source.is(DamageTypes.IN_WALL) || super.isInvulnerableTo(source);
   }

   @Override
   public void tick() {
      super.tick();
      this.repelEntities(1.8F, 4.0F, 1.8F, 1.8F);
      this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
      LivingEntity target = this.getTarget();
      Animation animation = getRandomAttack(this.random);
      Animation animation2 = this.getIsHelmetless() ? GUARDIAN_RAGE_UPPERCUT : GUARDIAN_UPPERCUT_AND_BULLET;
      if (this.isAlive()) {
         if (!this.getIsHelmetless() && this.isHelmetless()) {
            this.setIsHelmetless(true);
            this.BrokenHelmet();
         }

         if (!this.isNoAi() && this.getAnimation() == NO_ANIMATION && !this.getUsedMassDestruction() && this.isHelmetless()) {
            this.setAnimation(GUARDIAN_MASS_DESTRUCTION);
         } else if (target != null && target.isAlive()) {
            if (!this.isNoAi()
               && this.getAnimation() == NO_ANIMATION
               && this.distanceToSqr(target) >= 256.0
               && this.distanceToSqr(target) <= 1024.0
               && target.onGround()
               && this.getIsHelmetless()
               && this.getRandom().nextFloat() * 100.0F < 20.0F
               && this.teleport_smash_cooldown <= 0) {
               this.teleport_smash_cooldown = 600;
               this.setAnimation(GUARDIAN_AIR_STRIKE1);
            } else if (this.vortexcooldown > 0
               || this.isNoAi()
               || this.getAnimation() != NO_ANIMATION
               || !(this.distanceToSqr(target) <= 1024.0)
               || (!(this.distanceToSqr(target) >= 35.0) || !(this.getRandom().nextFloat() * 100.0F < 2.0F))
                  && (!(this.getRandom().nextFloat() * 100.0F < 60.0F) || !(this.getY() + 3.0 <= target.getY()))) {
               if (!this.isNoAi()
                  && this.getAnimation() == NO_ANIMATION
                  && this.distanceTo(target) >= 4.3
                  && this.distanceTo(target) <= 16.0
                  && target.onGround()
                  && this.getRandom().nextFloat() * 100.0F < 4.0F
                  && this.teleport_cooldown <= 0) {
                  this.teleport_cooldown = 280;
                  this.setAnimation(GUARDIAN_HUG_ME);
               } else if (!this.isNoAi()
                  && this.getAnimation() == NO_ANIMATION
                  && this.distanceTo(target) < 4.3
                  && target.onGround()
                  && this.getRandom().nextFloat() * 100.0F < 0.7F
                  && this.teleport_cooldown <= 0) {
                  this.teleport_cooldown = 280;
                  this.setAnimation(GUARDIAN_HUG_ME);
               } else if (!this.isNoAi() && this.getAnimation() == NO_ANIMATION && this.distanceTo(target) < 2.75F) {
                  if (this.random.nextInt(6) == 0) {
                     this.setAnimation(GUARDIAN_BURST_ATTACK);
                  } else {
                     this.setAnimation(animation);
                  }
               } else if (this.isNoAi()
                  || this.getAnimation() != NO_ANIMATION
                  || !(this.distanceTo(target) > 2.75F)
                  || !(this.distanceTo(target) < 4.3F)
                  || !target.hasEffect(MobEffects.LEVITATION) && !target.hasEffect(ModEffect.EFFECTSTUN)) {
                  if (this.stomp_cooldown <= 0
                     && !this.isNoAi()
                     && this.getAnimation() == NO_ANIMATION
                     && target.onGround()
                     && (
                        this.distanceTo(target) > 6.0F && this.distanceTo(target) < 13.0F && this.getRandom().nextFloat() * 100.0F < 10.0F
                           || this.distanceTo(target) > 2.75F && this.distanceTo(target) < 4.3F && this.getRandom().nextFloat() * 100.0F < 2.0F
                     )) {
                     this.stomp_cooldown = 400;
                     Animation animation3 = this.getIsHelmetless() ? GUARDIAN_RAGE_STOMP : GUARDIAN_STOMP;
                     this.setAnimation(animation3);
                  } else if (!this.isNoAi() && this.getAnimation() == NO_ANIMATION && this.distanceTo(target) > 2.75F && this.distanceTo(target) < 4.3F) {
                     if (this.random.nextInt(4) == 0) {
                        this.uppercut_cooldown = 200;
                        this.setAnimation(animation2);
                     } else {
                        this.setAnimation(animation);
                     }
                  }
               } else {
                  this.uppercut_cooldown = 200;
                  this.setAnimation(animation2);
               }
            } else {
               this.vortexcooldown = 280;
               this.setAnimation(GUARDIAN_BLACKHOLE);
            }
         }
      }

      if (this.stomp_cooldown > 0) {
         this.stomp_cooldown--;
      }

      if (this.teleport_cooldown > 0) {
         this.teleport_cooldown--;
      }

      if (this.teleport_smash_cooldown > 0) {
         this.teleport_smash_cooldown--;
      }

      if (this.uppercut_cooldown > 0) {
         this.uppercut_cooldown--;
      }

      if (this.vortexcooldown > 0) {
         this.vortexcooldown--;
      }
   }

   public void aiStep() {
      super.aiStep();
      if (this.getAnimation() == GUARDIAN_LEFT_STRONG_ATTACK) {
         if (this.getAnimationTick() < 2) {
            this.GravityPullparticle();
         }

         if (this.getAnimationTick() < 29) {
            this.GravityPull();
         }

         if (this.getAnimationTick() == 34) {
            this.playSound((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(5.15F, 5.0F, 70.0F, 1.0F, (float)CMCommonConfig.EnderGuardian.GravityPunchHpdamage, 100, 0, 0.0F, false);
            this.Attackparticle(2.2F, 0.0F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.2F, 0, 10);
         }
      }

      if (this.getAnimation() == GUARDIAN_RIGHT_STRONG_ATTACK) {
         if (this.getAnimationTick() < 2) {
            this.GravityPullparticle();
         }

         if (this.getAnimationTick() < 24) {
            this.GravityPull();
         }

         if (this.getAnimationTick() == 29) {
            this.AreaAttack(5.15F, 5.0F, 70.0F, 1.0F, (float)CMCommonConfig.EnderGuardian.GravityPunchHpdamage, 100, 0, 0.0F, false);
            this.playSound((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            this.Attackparticle(2.2F, 0.0F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.2F, 0, 10);
         }
      }

      if (this.getAnimation() == GUARDIAN_RIGHT_ATTACK && this.getAnimationTick() == 22) {
         this.AreaAttack(5.85F, 5.0F, 80.0F, 1.0F, 0.0F, 80, 0, 0.0F, false);
         this.playSound((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         this.Attackparticle(2.75F, 0.5F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 10);
      }

      if (this.getAnimation() == GUARDIAN_LEFT_ATTACK && this.getAnimationTick() == 19) {
         this.AreaAttack(5.85F, 5.0F, 80.0F, 1.0F, 0.0F, 80, 0, 0.0F, false);
         this.playSound((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         this.Attackparticle(2.75F, -0.5F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 10);
      }

      if (this.getAnimation() == GUARDIAN_BURST_ATTACK) {
         if (this.getAnimationTick() == 15) {
            this.Burstparticle();
         }

         if (this.getAnimationTick() == 27) {
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            this.AreaAttack(7.5F, 6.0F, 100.0F, 1.0F, (float)CMCommonConfig.EnderGuardian.KnockbackHpdamage, 0, 0, 0.0F, true);
         }
      }

      if ((this.getAnimation() == GUARDIAN_UPPERCUT_AND_BULLET || this.getAnimation() == GUARDIAN_RAGE_UPPERCUT) && this.getAnimationTick() == 27) {
         this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         this.AreaAttack(6.25F, 7.0F, 60.0F, 1.4F, (float)CMCommonConfig.EnderGuardian.UppercutHpdamage, 150, 60, 0.5F, false);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.3F, 0, 10);
      }

      if (this.getAnimation() == GUARDIAN_STOMP && this.getAnimationTick() == 32) {
         this.StompAttack();
         this.Attackparticle(0.4F, 0.8F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 10.0F, 0.1F, 0, 5);
      }

      if (this.getAnimation() == GUARDIAN_RAGE_STOMP && (this.getAnimationTick() == 32 || this.getAnimationTick() == 53 || this.getAnimationTick() == 62)) {
         this.StompAttack();
         this.Attackparticle(0.4F, 0.8F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 10.0F, 0.1F, 0, 5);
      }

      if (this.getAnimation() == GUARDIAN_RAGE_UPPERCUT && this.getAnimationTick() == 84) {
         this.RageAttack();
         this.AreaAttack(5.5F, 5.0F, 120.0F, 1.2F, (float)CMCommonConfig.EnderGuardian.AreaAttackHpdamage, 100, 0, 0.0F, false);
         this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.2F, 0, 10);
      }

      if (this.getAnimation() == GUARDIAN_MASS_DESTRUCTION) {
         if (this.getAnimationTick() == 1 && CMCommonConfig.EnderGuardian.SeparatePhaseMusic && !this.level().isClientSide && this.getBossMusic() != null) {
            PacketDistributor.sendToAllPlayers(new MessageMusic(this.getId(), false), new CustomPacketPayload[0]);
         }

         if (this.getAnimationTick() == 39) {
            this.Attackparticle(2.75F, 2.25F);
            this.Attackparticle(2.75F, -2.25F);
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.3F, 0, 10);
            this.MassDestruction(5.0F, 1.1F, 150);
            if (!this.level().isClientSide) {
               if (CMCommonConfig.EnderGuardian.ignoreMobGriefing) {
                  this.BlockBreaking(
                     CMCommonConfig.EnderGuardian.BlockBreakingX, CMCommonConfig.EnderGuardian.BlockBreakingY, CMCommonConfig.EnderGuardian.BlockBreakingZ
                  );
               } else if (EventHooks.canEntityGrief(this.level(), this)) {
                  this.BlockBreaking(
                     CMCommonConfig.EnderGuardian.BlockBreakingX, CMCommonConfig.EnderGuardian.BlockBreakingY, CMCommonConfig.EnderGuardian.BlockBreakingZ
                  );
               }
            }
         }

         if (this.getAnimationTick() == 50 && CMCommonConfig.EnderGuardian.SeparatePhaseMusic && !this.level().isClientSide && this.getBossMusic() != null) {
            PacketDistributor.sendToAllPlayers(new MessageMusic(this.getId(), true), new CustomPacketPayload[0]);
         }
      }

      if (this.getAnimation() == GUARDIAN_HUG_ME) {
         if (this.getAnimationTick() == 15) {
            this.Teleportparticle();
         }

         if (this.getAnimationTick() == 38) {
            this.AreaAttack(6.0F, 6.0F, 120.0F, 1.0F, (float)CMCommonConfig.EnderGuardian.TeleportAttackHpdamage, 80, 60, 0.6F, false);
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.2F, 0, 10);
         }
      }

      if (this.getAnimation() == GUARDIAN_AIR_STRIKE1) {
         if (this.getAnimationTick() == 20) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.15F, 0, 20);
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.getAnimationTick() == 40) {
            this.playSound(SoundEvents.SHULKER_TELEPORT, 1.0F, 1.0F);
         }
      }

      if (this.getAnimation() == GUARDIAN_AIR_STRIKE2 && this.getAnimationTick() == 3) {
         this.MassDestruction(3.0F, 1.5F, 200);
         this.playSound((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         this.Attackparticle(2.5F, 1.25F);
         this.Attackparticle(2.5F, -0.5F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.3F, 0, 20);
         switch (this.random.nextInt(3)) {
            case 0:
               this.StrikeRune(8, 0.5);
               break;
            case 1:
               this.StrikeRune(10, 0.75);
               break;
            case 2:
               this.StrikeRune(12, 1.0);
         }
      }

      if ((this.getAnimation() == GUARDIAN_RIGHT_SWING || this.getAnimation() == GUARDIAN_LEFT_SWING) && this.getAnimationTick() == 24) {
         this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0F, 0.5F);
         this.AreaAttack(4.25F, 4.25F, 80.0F, 1.0F, 0.0F, 40, 40, 0.0F, true);
      }

      if (this.getAnimation() == GUARDIAN_BLACKHOLE && this.getAnimationTick() == 26) {
         this.playSound((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.3F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         this.Attackparticle(1.0F, 0.2F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 10.0F, 0.1F, 0, 5);
      }

      if (this.getAnimation() == GUARDIAN_ROCKETPUNCH) {
         if (this.getAnimationTick() == 24) {
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.getAnimationTick() == 28) {
            this.AreaAttack(7.0F, 7.0F, 120.0F, 1.25F, (float)CMCommonConfig.EnderGuardian.RocketPunchHpdamage, 200, 0, 0.0F, true);
         }
      }
   }

   private void StrikeRune(int rune, double time) {
      for (int i = 0; i < rune; i++) {
         float throwAngle = i * (float) Math.PI / (rune / 2);

         for (int k = 0; k < 8; k++) {
            double d2 = 1.15 * (k + 1);
            int d3 = (int)(time * (k + 1));
            this.spawnFangs(
               this.getX() + Mth.cos(throwAngle) * 1.25 * d2, this.getZ() + Mth.sin(throwAngle) * 1.25 * d2, this.getY(), this.getY() + 2.0, throwAngle, d3
            );
         }
      }
   }

   public boolean isHelmetless() {
      return this.getHealth() <= this.getMaxHealth() / 2.0F;
   }

   @Override
   protected void onDeathAIUpdate() {
      super.onDeathAIUpdate();
      this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      if (this.deathTime == 50) {
         this.playSound((SoundEvent)ModSounds.MONSTROSITYLAND.get(), 1.0F, 1.0F);
      }

      if (this.deathTime == 100) {
         this.playSound(SoundEvents.SHULKER_TELEPORT, 1.0F, 1.0F);
      }
   }

   @Override
   protected void AfterDefeatBoss(@Nullable LivingEntity living) {
      if (CMCommonConfig.EnderGuardian.respawner && !this.level().isClientSide && this.getHomePos() != null && this.level() instanceof ServerLevel serverLevel) {
         MinecraftServer server = serverLevel.getServer();
         ServerLevel targetLevel = server.getLevel(this.getHomePos().dimension());
         if (targetLevel != null) {
            this.Respawner(
               this.getHomePos().pos().getX(), this.getHomePos().pos().getZ(), this.getHomePos().pos().getY() - 10, this.getHomePos().pos().getY(), targetLevel
            );
         }
      }
   }

   private void Respawner(int x, int z, int minY, int maxY, ServerLevel serverLevel) {
      BlockPos blockpos = new BlockPos(x, maxY, z);
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

      BlockState block = ((Block)ModBlocks.BOSS_RESPAWNER.get()).defaultBlockState();
      int newY = Mth.floor(blockpos.getY() + d0);
      BlockPos pos = new BlockPos(x, flag ? newY : maxY, z);
      serverLevel.setBlock(pos, block, 2);
      if (serverLevel.getBlockEntity(pos) instanceof Boss_Respawn_Spawner_Block_Entity spawnerblockentity) {
         spawnerblockentity.setEntityId((EntityType<?>)ModEntities.ENDER_GUARDIAN.get());
         spawnerblockentity.setTheItem(((Item)ModItems.VOID_EYE.get()).getDefaultInstance());
      }
   }

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return GUARDIAN_FALLEN;
   }

   private void AreaAttack(
      float range, float height, float arc, float damage, float hpdamage, int shieldbreakticks, int stunticks, float airborne, boolean knockback
   ) {
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
               && !(entityHit instanceof Ender_Guardian_Entity)) {
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

               if (airborne > 0.0F) {
                  entityHit.setDeltaMovement(entityHit.getDeltaMovement().add(0.0, airborne, 0.0));
               }

               if (flag && stunticks > 0) {
                  entityHit.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN, stunticks));
               }

               if (knockback) {
                  this.launch(entityHit);
               }
            }
         }
      }
   }

   private void MassDestruction(float grow, float damage, int ticks) {
      if (!this.level().isClientSide) {
         DamageSource damagesource = this.damageSources().mobAttack(this);

         for (LivingEntity entityHit : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(grow))) {
            if (!this.isAlliedTo(entityHit) && !(entityHit instanceof Ender_Guardian_Entity) && entityHit != this) {
               entityHit.hurt(damagesource, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage);
               if (entityHit.isDamageSourceBlocked(damagesource) && entityHit instanceof Player player && ticks > 0) {
                  EntityUtil.disableShield(player, ticks);
               }

               this.launch(entityHit);
            }
         }
      }
   }

   private void BlockBreaking(int x, int y, int z) {
      int MthX = Mth.floor(this.getX());
      int MthY = Mth.floor(this.getY());
      int MthZ = Mth.floor(this.getZ());
      boolean flag = false;

      for (int k2 = -x; k2 <= x; k2++) {
         for (int l2 = -z; l2 <= z; l2++) {
            for (int j = -y; j <= -1; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               BlockState block = this.level().getBlockState(blockpos);
               if (block != Blocks.AIR.defaultBlockState()
                  && block.is(ModTag.ENDER_GUARDIAN_CAN_DESTROY)
                  && block.canEntityDestroy(this.level(), blockpos, this)
                  && EventHooks.onEntityDestroyBlock(this, blockpos, block)) {
                  flag = this.level().destroyBlock(blockpos, false, this) || flag;
               }
            }
         }
      }
   }

   private void Burstparticle() {
      if (this.level().isClientSide) {
         double d0 = this.getX();
         double d1 = this.getY() + 2.0;
         double d2 = this.getZ();
         int size = 5;

         for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
               for (int k = -size; k <= size; k++) {
                  double d3 = j + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double d4 = i + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double d5 = k + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double d6 = Mth.sqrt((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + this.random.nextGaussian() * 0.05;
                  this.level().addParticle(ParticleTypes.REVERSE_PORTAL, d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
                  if (i != -size && i != size && j != -size && j != size) {
                     k += size * 2 - 1;
                  }
               }
            }
         }
      }
   }

   private void Teleportparticle() {
      if (this.level().isClientSide && this.getTeleportPos().isPresent()) {
         double d0 = this.getTeleportPos().get().getX();
         double d1 = this.getY();
         double d2 = this.getTeleportPos().get().getZ();
         if (this.level().isClientSide) {
            for (int i1 = 0; i1 < 40 + this.random.nextInt(12); i1++) {
               double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
               float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
               double extraX = 2.0F * Mth.sin((float)(Math.PI + angle));
               double extraY = 0.3F;
               double extraZ = 2.0F * Mth.cos(angle);
               this.level().addParticle(ParticleTypes.END_ROD, d0 + extraX, d1 + extraY, d2 + extraZ, 0.0, DeltaMovementY, 0.0);
            }
         }
      }
   }

   private void launch(Entity entityHit) {
      double d0 = entityHit.getX() - this.getX();
      double d1 = entityHit.getZ() - this.getZ();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      entityHit.push(d0 / d2 * 4.0, 0.2, d1 / d2 * 4.0);
   }

   private void GravityPull() {
      for (LivingEntity inRange : this.getEntityLivingBaseNearby(12.0, 12.0, 12.0, 12.0)) {
         if ((!(inRange instanceof Player) || !((Player)inRange).getAbilities().invulnerable) && !this.isAlliedTo(inRange)) {
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot;
            double extraX = Mth.sin((float)(Math.PI + angle));
            double extraZ = Mth.cos(angle);
            double theta = this.yBodyRot * (Math.PI / 180.0);
            theta += Math.PI / 2;
            double vecX = Math.cos(theta);
            double vecZ = Math.sin(theta);
            Vec3 diff = inRange.position().subtract(this.position().add(2.0 * vecX + extraX * 0.25, 0.0, 2.0 * vecZ + extraZ * 0.25));
            diff = diff.normalize().scale(0.085);
            inRange.setDeltaMovement(inRange.getDeltaMovement().subtract(diff));
         }
      }
   }

   private void GravityPullparticle() {
      if (this.level().isClientSide) {
         for (int i1 = 0; i1 < 80 + this.random.nextInt(12); i1++) {
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
            double extraX = Mth.sin((float)(Math.PI + angle));
            double extraY = 0.3F;
            double extraZ = Mth.cos(angle);
            double theta = this.yBodyRot * (Math.PI / 180.0);
            theta += Math.PI / 2;
            double vecX = Math.cos(theta);
            double vecZ = Math.sin(theta);
            this.level()
               .addParticle(
                  ParticleTypes.PORTAL,
                  this.getX() + 2.2 * vecX + extraX * 0.75,
                  this.getY() + extraY,
                  this.getZ() + 2.2 * vecZ + extraZ * 0.75,
                  (this.random.nextDouble() - 0.5) * 12.0,
                  -this.random.nextDouble(),
                  (this.random.nextDouble() - 0.5) * 12.0
               );
         }
      }
   }

   private void Attackparticle(float vec, float math) {
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
            double extraX = 1.2 * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.3F;
            double extraZ = 1.2 * Mth.cos(angle);
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
               new RingParticleOptions(0.0F, (float) (Math.PI / 2), 25, 255, 255, 255, 1.0F, 25.0F, false, 2),
               this.getX() + vec * vecX + f * math,
               this.getY() + 0.3F,
               this.getZ() + vec * vecZ + f1 * math,
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void StompAttack() {
      this.playSound((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.3F, 1.0F + this.getRandom().nextFloat() * 0.1F);
      LivingEntity target = this.getTarget();
      if (target != null) {
         double d0 = Math.min(target.getY(), this.getY());
         double d1 = Math.max(target.getY(), this.getY()) + 1.0;
         float f = (float)Mth.atan2(target.getZ() - this.getZ(), target.getX() - this.getX());
         float angle2 = (float) (Math.PI / 180.0) * this.yBodyRot;

         for (int k = 0; k < 6; k++) {
            float f2 = angle2 + k * (float) Math.PI * 2.0F / 6.0F + (float) (Math.PI * 2.0 / 5.0);
            this.spawnFangs(this.getX() + Mth.cos(f2) * 2.5, this.getZ() + Mth.sin(f2) * 2.5, d0, d1, f2, 3);
         }

         for (int k = 0; k < 11; k++) {
            float f3 = angle2 + k * (float) Math.PI * 2.0F / 11.0F + (float) (Math.PI / 5);
            this.spawnFangs(this.getX() + Mth.cos(f3) * 3.5, this.getZ() + Mth.sin(f3) * 3.5, d0, d1, f3, 10);
         }

         for (int k = 0; k < 14; k++) {
            float f4 = angle2 + k * (float) Math.PI * 2.0F / 14.0F + (float) (Math.PI / 10);
            this.spawnFangs(this.getX() + Mth.cos(f4) * 4.5, this.getZ() + Mth.sin(f4) * 4.5, d0, d1, f4, 15);
         }

         float[] all = new float[]{f - 0.4F, f, f + 0.4F};
         float[] all2 = new float[]{f - 0.1F, f + 0.1F};
         switch (this.random.nextInt(3)) {
            case 0:
               for (float angle : all) {
                  for (int l = 0; l < 13; l++) {
                     double d2 = 1.25 * (l + 1);
                     int j = (int)(0.75F * l);
                     this.spawnFangs(this.getX() + Mth.cos(angle) * d2, this.getZ() + Mth.sin(angle) * d2, d0, d1, angle, j);
                  }
               }
               break;
            case 1:
               for (float angle : all2) {
                  for (int l = 0; l < 13; l++) {
                     double d2 = 1.25 * (l + 1);
                     int j = (int)(0.25F * l);
                     this.spawnFangs(this.getX() + Mth.cos(angle) * d2, this.getZ() + Mth.sin(angle) * d2, d0, d1, angle, j);
                  }
               }
               break;
            case 2:
               for (int l = 0; l < 13; l++) {
                  double d2 = 1.25 * (l + 1);
                  int j = (int)(0.5F * l);
                  this.spawnFangs(this.getX() + Mth.cos(f) * d2, this.getZ() + Mth.sin(f) * d2, d0, d1, f, j);
               }
         }
      }
   }

   private void RageAttack() {
      LivingEntity target = this.getTarget();
      if (target != null) {
         double d0 = Math.min(target.getY(), this.getY());
         double d1 = Math.max(target.getY(), this.getY()) + 1.0;
         float f = (float)Mth.atan2(target.getZ() - this.getZ(), target.getX() - this.getX());
         float[] all = new float[]{f - 0.3F, f - 0.6F, f - 0.9F, f, f + 0.3F, f + 0.6F, f + 0.9F};

         for (float angle : all) {
            for (int l = 0; l < 10; l++) {
               double d2 = 1.25 * (l + 1);
               int j = (int)(0.75F * l);
               this.spawnFangs(this.getX() + Mth.cos(angle) * d2, this.getZ() + Mth.sin(angle) * d2, d0, d1, angle, j);
            }
         }
      }
   }

   private void spawnFangs(double x, double z, double minY, double maxY, float rotation, int delay) {
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
               new Void_Rune_Entity(this.level(), x, blockpos.getY() + d0, z, rotation, delay, (float)CMCommonConfig.EnderGuardian.VoidRuneDamage, this)
            );
      }
   }

   private void spawnVortex(double x, double z, double minY, double maxY, float rotation) {
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
         this.level().addFreshEntity(new Void_Vortex_Entity(this.level(), x, blockpos.getY() + d0, z, rotation, this, 300));
      }
   }

   private void BrokenHelmet() {
      if (!this.level().isClientSide) {
         double xx = Mth.cos(this.getYRot() % 360.0F / 180.0F * (float) Math.PI) * 0.75F;
         double zz = Mth.sin(this.getYRot() % 360.0F / 180.0F * (float) Math.PI) * 0.75F;
         this.level().explode(this, this.getX() + xx, this.getY() + this.getEyeHeight(), this.getZ() + zz, 2.0F, ExplosionInteraction.TRIGGER);
      }
   }

   private void Bulletpattern() {
      LivingEntity target = this.getTarget();
      if (target != null) {
         BlockPos tgt = target.blockPosition();
         double tx = tgt.getX();
         double tz = tgt.getZ();
         double ty = target.getY() + 0.1;
         if (this.getAnimationTick() == 54) {
            if (!target.onGround() && !target.isInWater() && !this.level().getBlockState(tgt.below()).blocksMotion()) {
               ty--;
            }

            BlockPos Pos = this.blockPosition();
            double sx = Pos.getX();
            double sz = Pos.getZ();
            Direction dir = Direction.getNearest(tx - sx, 0.0, tz - sz);
            double cx = dir.getStepX();
            double cz = dir.getStepZ();
            double offsetangle = Math.toRadians(6.0);

            for (int i = -4; i <= 4; i++) {
               double angle = (i - 2) * offsetangle;
               double x = cx * Math.cos(angle) + cz * Math.sin(angle);
               double z = -cx * Math.sin(angle) + cz * Math.cos(angle);
               Vec3 vec3 = new Vec3(x, this.getY() + 2.0, z);
               Ender_Guardian_Bullet_Entity bullet = new Ender_Guardian_Bullet_Entity(this.level(), this, vec3.normalize());
               bullet.setOwner(this);
               bullet.setPos(this.getX(), this.getY() - 2.0 + this.random.nextDouble() * 4.0, this.getZ());
               bullet.setUp(30, cx, 0.0, cz, tx - 7.0 * cx + i * cz, ty, tz - 7.0 * cz + i * cx);
               this.level().addFreshEntity(bullet);
            }
         }
      }
   }

   private boolean teleport(double p_32544_, double p_32545_, double p_32546_) {
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos(p_32544_, p_32545_, p_32546_);

      while (blockpos$mutableblockpos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(blockpos$mutableblockpos).blocksMotion()) {
         blockpos$mutableblockpos.move(Direction.DOWN);
      }

      BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);
      boolean flag = blockstate.blocksMotion();
      if (flag) {
         EnderEntity event = EventHooks.onEnderTeleport(this, p_32544_, p_32545_, p_32546_);
         if (event.isCanceled()) {
            return false;
         }

         Vec3 vec3 = this.position();
         boolean flag2 = this.ProperTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
         if (flag2) {
            this.level().gameEvent(GameEvent.TELEPORT, vec3, Context.of(this));
            if (!this.isSilent()) {
               this.playSound(SoundEvents.SHULKER_TELEPORT, 1.0F, 1.0F);
            }
         }

         return flag2;
      } else {
         return false;
      }
   }

   public boolean ProperTeleport(double p_20985_, double p_20986_, double p_20987_, boolean p_20988_) {
      double d0 = this.getX();
      double d1 = this.getY();
      double d2 = this.getZ();
      double d3 = p_20986_;
      boolean flag = false;
      BlockPos blockpos = BlockPos.containing(p_20985_, p_20986_, p_20987_);
      Level level = this.level();
      if (level.hasChunkAt(blockpos)) {
         boolean flag1 = false;

         while (!flag1 && blockpos.getY() > level.getMinBuildHeight()) {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = level.getBlockState(blockpos1);
            if (blockstate.blocksMotion()) {
               flag1 = true;
            } else {
               d3--;
               blockpos = blockpos1;
            }
         }

         if (flag1) {
            this.teleportTo(p_20985_, d3, p_20987_);
            if (level.noCollision(this) && !level.containsAnyLiquid(this.getBoundingBox())) {
               flag = true;
            }
         }
      }

      if (!flag) {
         this.teleportTo(d0, d1, d2);
         return false;
      }

      if (p_20988_) {
         level.broadcastEntityEvent(this, (byte)46);
      }

      this.getNavigation().stop();
      return true;
   }

   protected boolean isAffectedByFluids() {
      return false;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public ItemEntity spawnAtLocation(ItemStack stack) {
      ItemEntity itementity = this.spawnAtLocation(stack, 0.0F);
      if (itementity != null) {
         itementity.setDeltaMovement(itementity.getDeltaMovement().multiply(0.0, 3.5, 0.0));
         itementity.setGlowingTag(true);
         itementity.setExtendedLifetime();
      }

      return itementity;
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_ENDER_GUARDIAN) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.ENDERGUARDIANHURT.get();
   }

   protected SoundEvent getAmbientSound() {
      return this.getIsHelmetless() ? SoundEvents.SHULKER_AMBIENT : super.getAmbientSound();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.ENDERGUARDIANDEATH.get();
   }

   @Override
   protected boolean canPlayMusic() {
      if (CMCommonConfig.EnderGuardian.SeparatePhaseMusic) {
         return this.getAnimation() != GUARDIAN_MASS_DESTRUCTION ? super.canPlayMusic() : this.getAnimationTick() > 50 && super.canPlayMusic();
      } else {
         return super.canPlayMusic();
      }
   }

   @Override
   public SoundEvent getBossMusic() {
      if (!CMCommonConfig.EnderGuardian.SeparatePhaseMusic) {
         return (SoundEvent)ModSounds.ENDERGUARDIAN_MUSIC_DISC.get();
      } else {
         return !this.getIsHelmetless() && !this.getUsedMassDestruction()
            ? (SoundEvent)ModSounds.ENDERGUARDIAN_MUSIC_1.get()
            : (SoundEvent)ModSounds.ENDERGUARDIAN_MUSIC_2.get();
      }
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossInfo.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossInfo.removePlayer(player);
   }

   class HugmeGoal extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      private final float sensing;
      private final int teleport;
      public double prevX;
      public double prevZ;
      private int newX;
      private int newZ;

      public HugmeGoal(Ender_Guardian_Entity entity, Animation animation, float sensing, int teleport) {
         super(entity, animation);
         this.sensing = sensing;
         this.teleport = teleport;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void start() {
         super.start();
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.prevX = target.getX();
            this.prevZ = target.getZ();
         }
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (Ender_Guardian_Entity.this.getAnimationTick() < 40 && target != null) {
            Ender_Guardian_Entity.this.lookAt(target, 30.0F, 30.0F);
            Ender_Guardian_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
         } else {
            Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yRotO);
         }

         if (this.entity.getAnimationTick() == this.teleport - 6 && target != null) {
            double x = target.getX();
            double z = target.getZ();
            double vx = (x - this.prevX) / this.teleport;
            double vz = (z - this.prevZ) / this.teleport;
            this.newX = Mth.floor(x + vx * this.sensing);
            this.newZ = Mth.floor(z + vz * this.sensing);
            this.entity.setTeleportPos(BlockPos.containing(this.newX, target.getY(), this.newZ));
         }

         if (this.entity.getAnimationTick() == this.teleport && target != null && this.entity.getTeleportPos().isPresent()) {
            this.entity.teleport(this.entity.getTeleportPos().get().getX(), target.getY(), this.entity.getTeleportPos().get().getZ());
         }
      }
   }

   class PunchAttackGoal extends AnimationGoal<Ender_Guardian_Entity> {
      public PunchAttackGoal(Ender_Guardian_Entity entity) {
         super(entity);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      @Override
      protected boolean test(Animation animation) {
         return animation == Ender_Guardian_Entity.GUARDIAN_LEFT_ATTACK
            || animation == Ender_Guardian_Entity.GUARDIAN_RIGHT_ATTACK
            || animation == Ender_Guardian_Entity.GUARDIAN_LEFT_STRONG_ATTACK
            || animation == Ender_Guardian_Entity.GUARDIAN_RIGHT_STRONG_ATTACK;
      }

      public void tick() {
         Ender_Guardian_Entity.this.setDeltaMovement(0.0, Ender_Guardian_Entity.this.getDeltaMovement().y, 0.0);
         LivingEntity target = Ender_Guardian_Entity.this.getTarget();
         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_LEFT_ATTACK) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 17 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 27 || target == null)) {
               Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yRotO);
            } else {
               Ender_Guardian_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.entity.lookAt(target, 30.0F, 30.0F);
            }

            if (Ender_Guardian_Entity.this.getAnimationTick() == 24
               && target != null
               && Ender_Guardian_Entity.this.random.nextInt(2) == 0
               && Ender_Guardian_Entity.this.distanceTo(target) <= 4.0F) {
               AnimationHandler.INSTANCE.sendAnimationMessage(Ender_Guardian_Entity.this, Ender_Guardian_Entity.GUARDIAN_LEFT_SWING);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_RIGHT_ATTACK) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 22 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 32 || target == null)) {
               Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yRotO);
            } else {
               Ender_Guardian_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.entity.lookAt(target, 30.0F, 30.0F);
            }

            if (Ender_Guardian_Entity.this.getAnimationTick() == 26
               && target != null
               && Ender_Guardian_Entity.this.random.nextInt(2) == 0
               && Ender_Guardian_Entity.this.distanceTo(target) <= 4.0F) {
               AnimationHandler.INSTANCE.sendAnimationMessage(Ender_Guardian_Entity.this, Ender_Guardian_Entity.GUARDIAN_RIGHT_SWING);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_LEFT_STRONG_ATTACK) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 34 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 44 || target == null)) {
               Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yRotO);
            } else {
               Ender_Guardian_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.entity.lookAt(target, 30.0F, 30.0F);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_RIGHT_STRONG_ATTACK) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 29 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 39 || target == null)) {
               Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yRotO);
            } else {
               Ender_Guardian_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.entity.lookAt(target, 30.0F, 30.0F);
            }
         }
      }
   }

   class RageUppercut extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      public RageUppercut(Ender_Guardian_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void tick() {
         LivingEntity target = Ender_Guardian_Entity.this.getTarget();
         if ((Ender_Guardian_Entity.this.getAnimationTick() >= 29 || target == null)
            && (Ender_Guardian_Entity.this.getAnimationTick() <= 54 || Ender_Guardian_Entity.this.getAnimationTick() >= 84 || target == null)
            && (Ender_Guardian_Entity.this.getAnimationTick() <= 104 || target == null)) {
            Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yRotO);
         } else {
            Ender_Guardian_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yBodyRot);
         }

         if (Ender_Guardian_Entity.this.getAnimationTick() == 26) {
            float f1 = (float)Math.cos(Math.toRadians(Ender_Guardian_Entity.this.getYRot() + 90.0F));
            float f2 = (float)Math.sin(Math.toRadians(Ender_Guardian_Entity.this.getYRot() + 90.0F));
            if (target != null) {
               float r = Ender_Guardian_Entity.this.distanceTo(target);
               r = Mth.clamp(r, 0.0F, 7.0F);
               Ender_Guardian_Entity.this.push(f1 * 0.3 * r, 0.0, f2 * 0.3 * r);
            } else {
               Ender_Guardian_Entity.this.push(f1 * 2.0, 0.0, f2 * 2.0);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimationTick() > 32 || Ender_Guardian_Entity.this.getAnimationTick() < 26) {
            Ender_Guardian_Entity.this.setDeltaMovement(0.0, Ender_Guardian_Entity.this.getDeltaMovement().y, 0.0);
         }

         Ender_Guardian_Entity.this.Bulletpattern();
      }
   }

   static class RocketPunchGoal extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      public RocketPunchGoal(Ender_Guardian_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() < 24 && target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            this.entity.lookAt(target, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.getAnimationTick() == 24 && target != null) {
            this.entity.setDeltaMovement((target.getX() - this.entity.getX()) * 0.3F, 0.0, (target.getZ() - this.entity.getZ()) * 0.3F);
         }
      }
   }

   class StompAttackGoal extends AnimationGoal<Ender_Guardian_Entity> {
      public StompAttackGoal(Ender_Guardian_Entity entity) {
         super(entity);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      @Override
      protected boolean test(Animation animation) {
         return animation == Ender_Guardian_Entity.GUARDIAN_STOMP || animation == Ender_Guardian_Entity.GUARDIAN_RAGE_STOMP;
      }

      public void tick() {
         Ender_Guardian_Entity.this.setDeltaMovement(0.0, Ender_Guardian_Entity.this.getDeltaMovement().y, 0.0);
         LivingEntity target = Ender_Guardian_Entity.this.getTarget();
         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_STOMP) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 32 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 42 || target == null)) {
               Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yRotO);
            } else {
               Ender_Guardian_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.entity.lookAt(target, 30.0F, 30.0F);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_RAGE_STOMP) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 32 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 42 || Ender_Guardian_Entity.this.getAnimationTick() >= 53 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 58 || target == null)) {
               Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yRotO);
            } else {
               Ender_Guardian_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.entity.lookAt(target, 30.0F, 30.0F);
            }
         }
      }
   }

   static class TeleportStrikeGoal extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      public TeleportStrikeGoal(Ender_Guardian_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() < 40) {
            this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
         }

         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            this.entity.lookAt(target, 30.0F, 30.0F);
            if (this.entity.getAnimationTick() == 40) {
               float y = this.entity.getIsHelmetless() ? 8.0F : 4.0F;
               this.entity.teleportTo(target.getX(), target.getY() + y, target.getZ());
            }
         }

         if (this.entity.getAnimationTick() > 48 && this.entity.onGround()) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, Ender_Guardian_Entity.GUARDIAN_AIR_STRIKE2);
         }
      }
   }

   class UppercutAndBulletGoal extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      public UppercutAndBulletGoal(Ender_Guardian_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void tick() {
         LivingEntity target = Ender_Guardian_Entity.this.getTarget();
         if ((Ender_Guardian_Entity.this.getAnimationTick() >= 29 || target == null) && (Ender_Guardian_Entity.this.getAnimationTick() <= 54 || target == null)
            )
          {
            Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yRotO);
         } else {
            Ender_Guardian_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            Ender_Guardian_Entity.this.setYRot(Ender_Guardian_Entity.this.yBodyRot);
         }

         if (Ender_Guardian_Entity.this.getAnimationTick() == 26) {
            float f1 = (float)Math.cos(Math.toRadians(Ender_Guardian_Entity.this.getYRot() + 90.0F));
            float f2 = (float)Math.sin(Math.toRadians(Ender_Guardian_Entity.this.getYRot() + 90.0F));
            if (target != null) {
               float r = Ender_Guardian_Entity.this.distanceTo(target);
               r = Mth.clamp(r, 0.0F, 7.0F);
               Ender_Guardian_Entity.this.push(f1 * 0.3 * r, 0.0, f2 * 0.3 * r);
            } else {
               Ender_Guardian_Entity.this.push(f1 * 2.0, 0.0, f2 * 2.0);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimationTick() > 32 || Ender_Guardian_Entity.this.getAnimationTick() < 26) {
            Ender_Guardian_Entity.this.setDeltaMovement(0.0, Ender_Guardian_Entity.this.getDeltaMovement().y, 0.0);
         }

         Ender_Guardian_Entity.this.Bulletpattern();
      }
   }

   static class VoidVortexGoal extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      public VoidVortexGoal(Ender_Guardian_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() < 26 && target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            this.entity.lookAt(target, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.getAnimationTick() == 26 && target != null) {
            double tx = target.getX();
            double ty = target.getY();
            double tz = target.getZ();
            double minY = Math.min(ty, this.entity.getY());
            double maxY = Math.max(ty, this.entity.getY()) + 1.0;
            float angle = (float)Mth.atan2(tz - this.entity.getZ(), tx - this.entity.getX());
            this.entity.spawnVortex(tx, tz, minY, maxY, angle);
         }

         if (this.entity.getAnimationTick() == 37 && target != null && this.entity.distanceToSqr(target) >= 25.0) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, Ender_Guardian_Entity.GUARDIAN_ROCKETPUNCH);
         }
      }
   }
}
