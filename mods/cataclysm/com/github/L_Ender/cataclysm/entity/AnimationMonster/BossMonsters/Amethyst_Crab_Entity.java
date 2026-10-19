package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.client.particle.Options.RingParticleOptions;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMEntityMoveHelper;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.EarthQuake_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

public class Amethyst_Crab_Entity extends LLibrary_Boss_Monster implements NeutralMob {
   public static final Animation CRAB_SMASH = Animation.create(53);
   public static final Animation CRAB_SMASH_THREE = Animation.create(77);
   public static final Animation CRAB_DEATH = Animation.create(114);
   public static final Animation CRAB_BURROW = Animation.create(65);
   public static final Animation CRAB_BITE = Animation.create(48);
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
   private int remainingPersistentAngerTime;
   private int despawnTime = 4000;
   public static final int BURROW_ATTACK_COOLDOWN = 240;
   private int burrow_cooldown = 0;
   @Nullable
   private UUID persistentAngerTarget;

   public Amethyst_Crab_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 50;
      this.moveControl = new CMEntityMoveHelper(this, 45.0F);
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setConfigattribute(this, CMCommonConfig.AmethystCrab.healthMultiplier, CMCommonConfig.AmethystCrab.attackMultiplier);
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, CRAB_SMASH, CRAB_SMASH_THREE, CRAB_DEATH, CRAB_BURROW, CRAB_BITE};
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(2, new Amethyst_Crab_Entity.CrabMoveGoal(this, false, 1.0));
      this.goalSelector.addGoal(0, new Amethyst_Crab_Entity.CrabSmashGoal(this, CRAB_SMASH));
      this.goalSelector.addGoal(0, new Amethyst_Crab_Entity.CrabAttack(this, CRAB_SMASH_THREE, 10));
      this.goalSelector.addGoal(0, new Amethyst_Crab_Entity.CrabBurrow(this, CRAB_BURROW));
      this.goalSelector.addGoal(0, new Amethyst_Crab_Entity.CrabAttack(this, CRAB_BITE, 17));
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
   }

   public static Builder amethyst_crab() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.28F)
         .add(Attributes.ATTACK_DAMAGE, 13.0)
         .add(Attributes.MAX_HEALTH, 200.0)
         .add(Attributes.ARMOR, 10.0)
         .add(Attributes.STEP_HEIGHT, 1.5)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
   }

   public boolean isKrusty() {
      String s = ChatFormatting.stripFormatting(this.getName().getString());
      return s != null && (s.toLowerCase().contains("eugene harold krabs") || s.toLowerCase().contains("mr.krabs"));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      this.addPersistentAngerSaveData(compound);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.readPersistentAngerSaveData(this.level(), compound);
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if (this.getAnimation() == CRAB_BURROW
         && this.getAnimationTick() > 9
         && this.getAnimationTick() < 52
         && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         this.playSound(SoundEvents.ANVIL_LAND, 0.4F, 2.0F);
         return false;
      } else {
         return super.hurt(source, damage);
      }
   }

   public boolean checkSpawnRules(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      return ModEntities.rollSpawn(CMCommonConfig.Spawning.AmethystCrabSpawnRolls, this.getRandom(), spawnReasonIn);
   }

   public static boolean canCrabSpawnSpawnRules(
      EntityType<? extends Amethyst_Crab_Entity> p_219020_, LevelAccessor p_219021_, MobSpawnType p_219022_, BlockPos p_219023_, RandomSource p_219024_
   ) {
      return checkAnyLightMonsterSpawnRules(p_219020_, p_219021_, p_219022_, p_219023_, p_219024_);
   }

   @Override
   public void tick() {
      super.tick();
      this.repelEntities(1.7F, 3.7F, 1.7F, 1.7F);
      if (this.burrow_cooldown > 0) {
         this.burrow_cooldown--;
      }

      if (this.getdespawnTimee() > 0) {
         this.setdespawnTime(this.getdespawnTimee() - 1);
      }
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide && this.isAlive() && this.tickCount % 20 == 0) {
         this.heal(2.0F * (float)CMCommonConfig.AmethystCrab.healthMultiplier);
      }

      if (this.getAnimation() == CRAB_SMASH && this.getAnimationTick() == 22) {
         this.AreaAttack(4.0F, 4.0F, 70.0F, 1.25F, 120);
         this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.0F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         this.Attackparticle(2.4F, -0.4F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 20);
      }

      if (this.getAnimation() == CRAB_SMASH_THREE) {
         if (this.getAnimationTick() == 16) {
            this.Attackparticle(2.2F, -0.2F);
            this.EarthQuakeSummon(2.2F, -0.2F);
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.0F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 20);
         }

         if (this.getAnimationTick() == 36) {
            this.Attackparticle(1.8F, -1.5F);
            this.EarthQuakeSummon(1.8F, -1.5F);
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.0F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 20);
         }

         if (this.getAnimationTick() == 56) {
            this.Attackparticle(1.7F, 1.3F);
            this.EarthQuakeSummon(1.7F, 1.3F);
            this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.0F, 1.0F + this.getRandom().nextFloat() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 20);
         }
      }

      if (this.getAnimation() == CRAB_BURROW) {
         if (this.getAnimationTick() == 1 || this.getAnimationTick() == 4 || this.getAnimationTick() == 7 || this.getAnimationTick() == 10) {
            this.BurrowSound();
            this.BurrowParticle(0.6F, 0.0F, 2.0F);
         }

         if (this.getAnimationTick() == 39 || this.getAnimationTick() == 42 || this.getAnimationTick() == 45 || this.getAnimationTick() == 48) {
            this.BurrowParticle(0.6F, 0.0F, 2.0F);
            this.BurrowSound();
         }
      }

      if (this.getAnimation() == CRAB_BITE) {
         if (this.getAnimationTick() == 14) {
            this.playSound((SoundEvent)ModSounds.CRAB_BITE.get(), 1.0F, 1.0F + this.getRandom().nextFloat() * 0.1F);
         }

         if (this.getAnimationTick() == 17) {
            this.AreaAttack(4.5F, 4.5F, 110.0F, 1.25F, 120);
         }
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, int shieldbreakticks) {
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
               && !(entityHit instanceof Amethyst_Crab_Entity)) {
               entityHit.hurt(damagesource, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage);
               if (entityHit.isDamageSourceBlocked(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }
            }
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
            double extraX = 1.0 * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.3F;
            double extraZ = 1.0 * Mth.cos(angle);
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

   private void BurrowParticle(float vec, float math, float size) {
      if (this.level().isClientSide) {
         for (int i1 = 0; i1 < 80 + this.random.nextInt(12); i1++) {
            double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementY = this.getRandom().nextGaussian() * 0.1;
            double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
            float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
            float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
            double extraX = size * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.3F;
            double extraZ = size * Mth.cos(angle);
            double theta = this.yBodyRot * (Math.PI / 180.0);
            theta += Math.PI / 2;
            double vecX = Math.cos(theta);
            double vecZ = Math.sin(theta);
            int hitX = Mth.floor(this.getX() + vec * vecX + extraX);
            int hitY = Mth.floor(this.getY());
            int hitZ = Mth.floor(this.getZ() + vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.level().getBlockState(hit.below());
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
   }

   private void BurrowSound() {
      float angle = (float) (Math.PI / 180.0) * this.yBodyRot;
      double extraX = 1.0 * Mth.sin((float)(Math.PI + angle));
      double extraZ = 1.0 * Mth.cos(angle);
      int hitX = Mth.floor(this.getX() + extraX);
      int hitY = Mth.floor(this.getY());
      int hitZ = Mth.floor(this.getZ() + extraZ);
      BlockPos hit = new BlockPos(hitX, hitY, hitZ);
      BlockState block = this.level().getBlockState(hit.below());
      SoundType soundtype = block.getSoundType(this.level(), hit, this);
      this.level().playSound((Player)null, this, soundtype.getBreakSound(), SoundSource.HOSTILE, 3.0F, 0.8F + this.getRandom().nextFloat() * 0.1F);
   }

   private void EarthQuakeSummon(float vec, float math) {
      float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
      float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      int quakeCount = 16;
      float angle = 22.5F;

      for (int i = 0; i < 16; i++) {
         EarthQuake_Entity peq = new EarthQuake_Entity(this.level(), this);
         peq.setDamage((float)CMCommonConfig.AmethystCrab.EarthQuakeDamage);
         peq.shootFromRotation(this, 0.0F, angle * i, 0.0F, 0.25F, 0.0F);
         peq.setPos(this.getX() + vec * vecX + f * math, this.getY(), this.getZ() + vec * vecZ + f1 * math);
         this.level().addFreshEntity(peq);
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

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return CRAB_DEATH;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.CRAB_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.CRAB_DEATH.get();
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public void setRemainingPersistentAngerTime(int p_32515_) {
      this.remainingPersistentAngerTime = p_32515_;
   }

   public int getRemainingPersistentAngerTime() {
      return this.remainingPersistentAngerTime;
   }

   public void setdespawnTime(int p_32515_) {
      this.despawnTime = p_32515_;
   }

   public int getdespawnTimee() {
      return this.despawnTime;
   }

   @Override
   public boolean removeWhenFarAway(double p_21542_) {
      return super.removeWhenFarAway(p_21542_) && this.despawnTime >= 0;
   }

   @Nullable
   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   public void setPersistentAngerTarget(@javax.annotation.Nullable UUID p_32509_) {
      this.persistentAngerTarget = p_32509_;
   }

   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
   }

   static class CrabAttack extends SimpleAnimationGoal<Amethyst_Crab_Entity> {
      private final int look;

      public CrabAttack(Amethyst_Crab_Entity entity, Animation animation, int look) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
         this.look = look;
      }

      public void start() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.start();
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() < this.look && target != null) {
            this.entity.lookAt(target, 30.0F, 30.0F);
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }
      }
   }

   static class CrabBurrow extends SimpleAnimationGoal<Amethyst_Crab_Entity> {
      public CrabBurrow(Amethyst_Crab_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void start() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         this.entity.burrow_cooldown = 240;
         super.start();
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() < 48 && target != null) {
            this.entity.lookAt(target, 30.0F, 30.0F);
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.getAnimationTick() == 50) {
            for (int i = 0; i < 32; i++) {
               float throwAngle = i * (float) Math.PI / 16.0F;
               double sx = this.entity.getX() + Mth.cos(throwAngle) * 1.0F;
               double sy = this.entity.getY() + this.entity.getBbHeight() * 0.2;
               double sz = this.entity.getZ() + Mth.sin(throwAngle) * 1.0F;
               double vx = Mth.cos(throwAngle);
               double vy = 0.0F + this.entity.random.nextFloat() * 0.3F;
               double vz = Mth.sin(throwAngle);
               double v3 = Mth.sqrt((float)(vx * vx + vz * vz));
               Amethyst_Cluster_Projectile_Entity projectile = new Amethyst_Cluster_Projectile_Entity(
                  (EntityType<Amethyst_Cluster_Projectile_Entity>)ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(),
                  this.entity.level(),
                  this.entity,
                  (float)CMCommonConfig.AmethystCrab.AmethystClusterDamage
               );
               projectile.moveTo(sx, sy, sz, i * 11.25F, this.entity.getXRot());
               float speed = 0.8F;
               projectile.shoot(vx, vy + v3 * 0.2F, vz, speed, 1.0F);
               this.entity.level().addFreshEntity(projectile);
            }
         }
      }
   }

   static class CrabMoveGoal extends Goal {
      private final Amethyst_Crab_Entity crab;
      private final boolean followingTargetEvenIfNotSeen;
      private Path path;
      private int delayCounter;
      protected final double moveSpeed;

      public CrabMoveGoal(Amethyst_Crab_Entity boss, boolean followingTargetEvenIfNotSeen, double moveSpeed) {
         this.crab = boss;
         this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
         this.moveSpeed = moveSpeed;
         this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
      }

      public boolean canUse() {
         LivingEntity target = this.crab.getTarget();
         return target != null && target.isAlive() && this.crab.getAnimation() == IAnimatedEntity.NO_ANIMATION;
      }

      public void stop() {
         this.crab.getNavigation().stop();
         LivingEntity livingentity = this.crab.getTarget();
         if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.crab.setTarget((LivingEntity)null);
         }

         this.crab.setAggressive(false);
         this.crab.getNavigation().stop();
      }

      public boolean canContinueToUse() {
         LivingEntity target = this.crab.getTarget();
         if (target == null) {
            return false;
         } else if (!target.isAlive()) {
            return false;
         } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.crab.getNavigation().isDone();
         } else {
            return !this.crab.isWithinRestriction(target.blockPosition())
               ? false
               : !(target instanceof Player) || !target.isSpectator() && !((Player)target).isCreative();
         }
      }

      public void start() {
         this.crab.getNavigation().moveTo(this.path, this.moveSpeed);
         this.crab.setAggressive(true);
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity target = this.crab.getTarget();
         if (target != null) {
            this.crab.getLookControl().setLookAt(target, 30.0F, 30.0F);
            double distSq = this.crab.distanceToSqr(target.getX(), target.getBoundingBox().minY, target.getZ());
            if (--this.delayCounter <= 0) {
               this.delayCounter = 4 + this.crab.getRandom().nextInt(7);
               if (distSq > Math.pow(this.crab.getAttribute(Attributes.FOLLOW_RANGE).getValue(), 2.0)) {
                  if (!this.crab.isPathFinding() && !this.crab.getNavigation().moveTo(target, 1.0)) {
                     this.delayCounter += 5;
                  }
               } else {
                  this.crab.getNavigation().moveTo(target, this.moveSpeed);
               }
            }

            if (target.isAlive() && this.crab.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
               if (this.crab.burrow_cooldown <= 0 && this.crab.getRandom().nextFloat() * 100.0F < 6.0F && this.crab.distanceTo(target) <= 8.0) {
                  this.crab.setAnimation(Amethyst_Crab_Entity.CRAB_BURROW);
               } else if (this.crab.getRandom().nextFloat() * 100.0F < 24.0F && this.crab.distanceTo(target) <= 3.75) {
                  if (this.crab.random.nextInt(2) == 0) {
                     this.crab.setAnimation(Amethyst_Crab_Entity.CRAB_BITE);
                  } else {
                     this.crab.setAnimation(Amethyst_Crab_Entity.CRAB_SMASH);
                  }
               } else if (this.crab.getRandom().nextFloat() * 100.0F < 16.0F && this.crab.distanceTo(target) <= 3.75 && target.onGround()) {
                  this.crab.setAnimation(Amethyst_Crab_Entity.CRAB_SMASH_THREE);
               }
            }
         }
      }
   }

   static class CrabSmashGoal extends SimpleAnimationGoal<Amethyst_Crab_Entity> {
      public CrabSmashGoal(Amethyst_Crab_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void start() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.start();
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() < 19 && target != null) {
            this.entity.lookAt(target, 30.0F, 30.0F);
            this.entity.getNavigation().moveTo(target, 1.0);
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.getAnimationTick() == 19) {
            this.entity.getNavigation().stop();
         }
      }
   }
}
