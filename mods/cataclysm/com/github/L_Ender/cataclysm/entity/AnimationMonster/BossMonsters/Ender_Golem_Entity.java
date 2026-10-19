package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AI.CmAttackGoal;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;

public class Ender_Golem_Entity extends LLibrary_Boss_Monster {
   public static final Animation ANIMATION_ATTACK1 = Animation.create(25);
   public static final Animation ANIMATION_ATTACK2 = Animation.create(25);
   public static final Animation ANIMATION_EARTHQUAKE = Animation.create(35);
   public static final Animation VOID_RUNE_ATTACK = Animation.create(83);
   public static final Animation ENDER_GOLEM_DEATH = Animation.create(95);
   public static final int VOID_RUNE_ATTACK_COOLDOWN = 250;
   private static final EntityDataAccessor<Boolean> IS_AWAKEN = SynchedEntityData.defineId(Ender_Golem_Entity.class, EntityDataSerializers.BOOLEAN);
   private int void_rune_attack_cooldown = 0;
   private int timeWithoutTarget;
   public float deactivateProgress;
   public float prevdeactivateProgress;

   public Ender_Golem_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 15;
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setConfigattribute(this, CMCommonConfig.EnderGolem.healthMultiplier, CMCommonConfig.EnderGolem.attackMultiplier);
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, ANIMATION_ATTACK1, ANIMATION_ATTACK2, ANIMATION_EARTHQUAKE, VOID_RUNE_ATTACK, ENDER_GOLEM_DEATH};
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(2, new CmAttackGoal(this, 1.0));
      this.goalSelector.addGoal(1, new Ender_Golem_Entity.AttackGoal());
      this.goalSelector.addGoal(0, new Ender_Golem_Entity.AwakenGoal());
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   public static Builder ender_golem() {
      return createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.28F)
         .add(Attributes.ATTACK_DAMAGE, 10.0)
         .add(Attributes.MAX_HEALTH, 150.0)
         .add(Attributes.ARMOR, 12.0)
         .add(Attributes.STEP_HEIGHT, 1.5)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   @Override
   public double RangeLimit() {
      return CMCommonConfig.EnderGolem.rangeCap;
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   private static Animation getRandomAttack(RandomSource rand) {
      return switch (rand.nextInt(3)) {
         case 0 -> ANIMATION_ATTACK1;
         case 1 -> ANIMATION_ATTACK2;
         default -> ANIMATION_EARTHQUAKE;
      };
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if ((this.getAnimation() == VOID_RUNE_ATTACK || !this.getIsAwaken())
         && (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || !source.is(DamageTypes.MAGIC))) {
         damage = (float)(damage * 0.5);
      }

      double range = this.calculateRange(source);
      Entity entity = source.getDirectEntity();
      if (entity instanceof AbstractGolem) {
         damage = (float)(damage * 0.5);
      }

      return super.hurt(source, damage);
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(IS_AWAKEN, false);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("is_Awaken", this.getIsAwaken());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setIsAwaken(compound.getBoolean("is_Awaken"));
   }

   public void setIsAwaken(boolean isAwaken) {
      this.entityData.set(IS_AWAKEN, isAwaken);
   }

   public boolean getIsAwaken() {
      return (Boolean)this.entityData.get(IS_AWAKEN);
   }

   @Override
   public void tick() {
      super.tick();
      this.repelEntities(1.7F, 3.7F, 1.7F, 1.7F);
      LivingEntity target = this.getTarget();
      this.prevdeactivateProgress = this.deactivateProgress;
      if (!this.getIsAwaken() && this.deactivateProgress < 30.0F) {
         this.deactivateProgress++;
      }

      if (this.getIsAwaken() && this.deactivateProgress > 0.0F) {
         this.deactivateProgress--;
      }

      if (!this.getIsAwaken() && this.tickCount % 20 == 0) {
         this.heal(2.0F);
      }

      if (this.deactivateProgress == 0.0F && this.isAlive()) {
         if (target != null && target.isAlive()) {
            if (this.void_rune_attack_cooldown > 0
               || this.isNoAi()
               || this.getAnimation() != NO_ANIMATION
               || !target.onGround()
               || (this.random.nextInt(45) != 0 || !(this.distanceTo(target) < 4.0F)) && (this.random.nextInt(24) != 0 || !(this.distanceTo(target) < 10.0F))) {
               if (this.distanceTo(target) < 4.0F && !this.isNoAi() && this.getAnimation() == NO_ANIMATION) {
                  Animation animation = getRandomAttack(this.random);
                  this.setAnimation(animation);
               }
            } else {
               this.void_rune_attack_cooldown = 250;
               this.setAnimation(VOID_RUNE_ATTACK);
            }
         }

         if (this.getAnimation() == ANIMATION_EARTHQUAKE && this.getAnimationTick() == 19) {
            this.EarthQuake(5.0F, 6);
            this.EarthQuakeParticle();
            if (!this.level().isClientSide) {
               if (CMCommonConfig.EnderGolem.ignoreMobGriefing) {
                  this.BlockBreaking(4, 4, 4);
               } else if (EventHooks.canEntityGrief(this.level(), this)) {
                  this.BlockBreaking(4, 4, 4);
               }
            }
         }

         if ((this.getAnimation() == ANIMATION_ATTACK1 || this.getAnimation() == ANIMATION_ATTACK2) && this.getAnimationTick() == 13) {
            this.playSound((SoundEvent)ModSounds.GOLEMATTACK.get(), 1.0F, 1.0F);
            if (target != null && target.isAlive() && this.distanceTo(target) < 4.75F) {
               target.hurt(this.damageSources().mobAttack(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) + this.random.nextInt(4));
               target.knockback(1.25, this.getX() - target.getX(), this.getZ() - target.getZ());
            }
         }

         if (this.getAnimation() == VOID_RUNE_ATTACK) {
            if (this.getAnimationTick() == 22) {
               this.EarthQuake(4.25F, 4);
               this.EarthQuakeParticle();
               if (!this.level().isClientSide) {
                  if (CMCommonConfig.EnderGolem.ignoreMobGriefing) {
                     this.BlockBreaking(4, 4, 4);
                  } else if (EventHooks.canEntityGrief(this.level(), this)) {
                     this.BlockBreaking(4, 4, 4);
                  }
               }
            }

            if (this.getAnimationTick() == 28) {
               this.VoidRuneAttack();
            }
         }
      }

      if (this.void_rune_attack_cooldown > 0) {
         this.void_rune_attack_cooldown--;
      }

      if (!this.level().isClientSide) {
         this.timeWithoutTarget++;
         if (target != null) {
            this.timeWithoutTarget = 0;
            if (!this.getIsAwaken()) {
               this.setIsAwaken(true);
            }
         }

         if (this.timeWithoutTarget > 400 && this.getIsAwaken() && target == null) {
            this.timeWithoutTarget = 0;
            this.setIsAwaken(false);
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
            for (int j = 0; j <= y; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               BlockState block = this.level().getBlockState(blockpos);
               if (block != Blocks.AIR.defaultBlockState()
                  && block.is(ModTag.ENDER_GOLEM_CAN_DESTROY)
                  && block.canEntityDestroy(this.level(), blockpos, this)
                  && EventHooks.onEntityDestroyBlock(this, blockpos, block)) {
                  flag = this.level().destroyBlock(blockpos, true, this) || flag;
               }
            }
         }
      }
   }

   private void EarthQuakeParticle() {
      if (this.level().isClientSide) {
         BlockState block = this.level().getBlockState(this.blockPosition().below());

         for (int i1 = 0; i1 < 20 + this.random.nextInt(12); i1++) {
            double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
            double extraX = 4.0F * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.3F;
            double extraZ = 4.0F * Mth.cos(angle);
            if (block.getRenderShape() != RenderShape.INVISIBLE) {
               this.level()
                  .addParticle(
                     new BlockParticleOption(ParticleTypes.BLOCK, block),
                     this.getX() + extraX,
                     this.getY() + extraY,
                     this.getZ() + extraZ,
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }
      }
   }

   private void EarthQuake(float grow, int damage) {
      this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
      if (!this.level().isClientSide) {
         DamageSource damagesource = this.damageSources().mobAttack(this);

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(grow))) {
            if (!this.isAlliedTo(entity) && !(entity instanceof Ender_Golem_Entity) && entity != this) {
               entity.hurt(damagesource, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) + this.random.nextInt(damage));
               this.launch(entity, true);
            }
         }
      }
   }

   private void VoidRuneAttack() {
      LivingEntity target = this.getTarget();
      if (target != null) {
         double d0 = Math.min(target.getY(), this.getY());
         double d1 = Math.max(target.getY(), this.getY()) + 1.0;
         float f = (float)Mth.atan2(target.getZ() - this.getZ(), target.getX() - this.getX());
         float f2 = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * 2.0F;
         float f3 = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)) * 2.0F;

         for (int l = 0; l < 10; l++) {
            double d2 = 1.5 * (l + 1);
            int j = (int)(1.25F * l);
            this.spawnFangs(this.getX() + f2 + Mth.cos(f) * d2, this.getZ() + f3 + Mth.sin(f) * d2, d0, d1, f, j);
            this.spawnFangs(this.getX() - f2 + Mth.cos(f) * d2, this.getZ() - f3 + Mth.sin(f) * d2, d0, d1, f, j);
         }

         for (int k = 0; k < 6; k++) {
            float f4 = f + k * (float) Math.PI * 2.0F / 6.0F + 0.83775806F;
            this.spawnFangs(this.getX() + Mth.cos(f4) * 2.5, this.getZ() + Mth.sin(f4) * 2.5, d0, d1, f2, 5);
         }

         for (int k = 0; k < 8; k++) {
            this.spawnFangs(this.getX() + this.random.nextGaussian() * 4.5, this.getZ() + this.random.nextGaussian() * 4.5, d0, d1, f3, 15);
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
               new Void_Rune_Entity(this.level(), x, blockpos.getY() + d0, z, rotation, delay, (float)CMCommonConfig.EnderGolem.VoidRuneDamage, this)
            );
      }
   }

   private void launch(LivingEntity e, boolean huge) {
      double d0 = e.getX() - this.getX();
      double d1 = e.getZ() - this.getZ();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      float f = huge ? 2.0F : 0.5F;
      e.push(d0 / d2 * f, huge ? 0.5 : 0.2F, d1 / d2 * f);
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
   protected void onDeathAIUpdate() {
      super.onDeathAIUpdate();
      this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      if (this.deathTime == 40) {
         this.playSound((SoundEvent)ModSounds.MONSTROSITYLAND.get(), 1.0F, 1.0F);
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
      return ENDER_GOLEM_DEATH;
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 1.0F);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.GOLEMHURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.GOLEMDEATH.get();
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   class AttackGoal extends Goal {
      public AttackGoal() {
         this.setFlags(EnumSet.of(Flag.JUMP, Flag.LOOK, Flag.MOVE));
      }

      public boolean canUse() {
         return Ender_Golem_Entity.this.getAnimation() == Ender_Golem_Entity.ANIMATION_EARTHQUAKE
            || Ender_Golem_Entity.this.getAnimation() == Ender_Golem_Entity.VOID_RUNE_ATTACK;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void stop() {
         super.stop();
      }

      public void tick() {
         Ender_Golem_Entity.this.setDeltaMovement(0.0, Ender_Golem_Entity.this.getDeltaMovement().y, 0.0);
         LivingEntity target = Ender_Golem_Entity.this.getTarget();
         if (Ender_Golem_Entity.this.getAnimation() == Ender_Golem_Entity.ANIMATION_EARTHQUAKE) {
            if (Ender_Golem_Entity.this.getAnimationTick() < 19 && target != null) {
               Ender_Golem_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
               Ender_Golem_Entity.this.lookAt(target, 30.0F, 30.0F);
            } else {
               Ender_Golem_Entity.this.setYRot(Ender_Golem_Entity.this.yRotO);
            }
         }

         if (Ender_Golem_Entity.this.getAnimation() == Ender_Golem_Entity.VOID_RUNE_ATTACK) {
            if (Ender_Golem_Entity.this.getAnimationTick() < 22 && target != null) {
               Ender_Golem_Entity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
               Ender_Golem_Entity.this.lookAt(target, 30.0F, 30.0F);
            } else {
               Ender_Golem_Entity.this.setYRot(Ender_Golem_Entity.this.yRotO);
            }
         }
      }
   }

   class AwakenGoal extends Goal {
      public AwakenGoal() {
         this.setFlags(EnumSet.of(Flag.JUMP, Flag.LOOK, Flag.MOVE));
      }

      public boolean canUse() {
         return Ender_Golem_Entity.this.deactivateProgress > 0.0F;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         Ender_Golem_Entity.this.setDeltaMovement(0.0, Ender_Golem_Entity.this.getDeltaMovement().y, 0.0);
      }
   }
}
