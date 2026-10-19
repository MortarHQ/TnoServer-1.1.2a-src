package com.iafenvoy.iceandfire.entity;

import com.google.common.base.Predicate;
import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.ai.CyclopsAIAttackMeleeGoal;
import com.iafenvoy.iceandfire.entity.ai.CyclopsAITargetSheepPlayersGoal;
import com.iafenvoy.iceandfire.entity.pathfinding.CyclopsNavigation;
import com.iafenvoy.iceandfire.entity.util.BlacklistedFromStatues;
import com.iafenvoy.iceandfire.entity.util.IHasCustomizableAttributes;
import com.iafenvoy.iceandfire.entity.util.IHumanoid;
import com.iafenvoy.iceandfire.entity.util.IVillagerFear;
import com.iafenvoy.iceandfire.entity.util.IafEntityUtil;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.event.IafEvents;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.iceandfire.registry.tag.IafEntityTags;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.AnimationHandler;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class CyclopsEntity extends Monster implements IAnimatedEntity, BlacklistedFromStatues, IVillagerFear, IHumanoid, IHasCustomizableAttributes {
   private static final EntityDataAccessor<Boolean> BLINDED = SynchedEntityData.defineId(CyclopsEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(CyclopsEntity.class, EntityDataSerializers.INT);
   public static Animation ANIMATION_STOMP;
   public static Animation ANIMATION_EATPLAYER;
   public static Animation ANIMATION_KICK;
   public static Animation ANIMATION_ROAR;
   public CyclopsEyeEntity eyeEntity;
   private int animationTick;
   private Animation currentAnimation;

   public CyclopsEntity(EntityType<CyclopsEntity> type, Level worldIn) {
      super(type, worldIn);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.FENCE, 0.0F);
      ANIMATION_STOMP = Animation.create(27);
      ANIMATION_EATPLAYER = Animation.create(40);
      ANIMATION_KICK = Animation.create(20);
      ANIMATION_ROAR = Animation.create(30);
   }

   public static Builder bakeAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, (Double)IafCommonConfig.INSTANCE.cyclops.maxHealth.getValue())
         .add(Attributes.MOVEMENT_SPEED, 0.35)
         .add(Attributes.ATTACK_DAMAGE, (Double)IafCommonConfig.INSTANCE.cyclops.attackDamage.getValue())
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.ARMOR, 20.0)
         .add(Attributes.STEP_HEIGHT, 2.5);
   }

   @Override
   public void setConfigurableAttributes() {
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)IafCommonConfig.INSTANCE.cyclops.maxHealth.getValue());
      this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35);
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CyclopsNavigation(this, this.level());
   }

   public int getBaseExperienceReward() {
      return 40;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new RestrictSunGoal(this));
      this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
      this.goalSelector.addGoal(3, new CyclopsAIAttackMeleeGoal(this, 1.0, false));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector
         .addGoal(
            2,
            new NearestAttackableTargetGoal(
               this,
               LivingEntity.class,
               10,
               true,
               true,
               (Predicate)entity -> {
                  if (GorgonEntity.isStoneMob(entity)) {
                     return false;
                  } else if (!DragonUtils.isAlive(entity)) {
                     return false;
                  } else if (entity instanceof WaterAnimal) {
                     return false;
                  } else if (entity instanceof Player playerEntity && (playerEntity.isCreative() || playerEntity.isSpectator())) {
                     return false;
                  } else if (entity instanceof CyclopsEntity) {
                     return false;
                  } else {
                     return entity instanceof Animal && !(entity instanceof Wolf) && !(entity instanceof PolarBear) && !(entity instanceof DragonBaseEntity)
                        ? false
                        : !entity.getType().is(IafEntityTags.SHEEP);
                  }
               }
            )
         );
      this.targetSelector
         .addGoal(
            2,
            new NearestAttackableTargetGoal(
               this, Player.class, 10, true, true, entity -> entity instanceof Player player && !player.isCreative() && !player.isSpectator()
            )
         );
      this.targetSelector.addGoal(3, new CyclopsAITargetSheepPlayersGoal(this, Player.class, true));
   }

   protected void doPush(Entity entityIn) {
      if (!entityIn.getType().is(IafEntityTags.SHEEP)) {
         entityIn.push(this);
      }
   }

   public boolean doHurtTarget(Entity entityIn) {
      int attackDescision = this.getRandom().nextInt(3);
      if (attackDescision == 0) {
         this.setAnimation(ANIMATION_STOMP);
         return true;
      }

      if (attackDescision != 1) {
         this.setAnimation(ANIMATION_KICK);
         return true;
      }

      if (!entityIn.hasPassenger(this)
         && entityIn.getBbWidth() < 1.95F
         && !(entityIn instanceof DragonBaseEntity)
         && !entityIn.getType().is(IafEntityTags.CYCLOPS_UNLIFTABLES)) {
         this.setAnimation(ANIMATION_EATPLAYER);
         entityIn.stopRiding();
         entityIn.startRiding(this, true);
      } else {
         this.setAnimation(ANIMATION_STOMP);
      }

      return true;
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(BLINDED, Boolean.FALSE);
      builder.define(VARIANT, 0);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("Blind", this.isBlinded());
      compound.putInt("Variant", this.getVariant());
      if (this.eyeEntity != null) {
         this.eyeEntity.remove(RemovalReason.DISCARDED);
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setBlinded(compound.getBoolean("Blind"));
      this.setVariant(compound.getInt("Variant"));
      this.setConfigurableAttributes();
   }

   public int getVariant() {
      return (Integer)this.entityData.get(VARIANT);
   }

   public void setVariant(int variant) {
      this.entityData.set(VARIANT, variant);
   }

   public boolean isBlinded() {
      return (Boolean)this.entityData.get(BLINDED);
   }

   public void setBlinded(boolean blind) {
      this.entityData.set(BLINDED, blind);
   }

   public void positionRider(Entity passenger, MoveFunction callback) {
      super.positionRider(passenger, callback);
      if (this.hasPassenger(passenger)) {
         passenger.setDeltaMovement(0.0, passenger.getDeltaMovement().y, 0.0);
         this.setAnimation(ANIMATION_EATPLAYER);
         double raiseUp = this.getAnimationTick() < 10 ? 0.0 : Math.min((this.getAnimationTick() * 3 - 30) * 0.2, 5.2F);
         float pullIn = this.getAnimationTick() < 15 ? 0.0F : Math.min((this.getAnimationTick() - 15) * 0.15F, 0.75F);
         this.yBodyRot = this.getYRot();
         this.setYRot(0.0F);
         float radius = -2.75F + pullIn;
         float angle = (float) (Math.PI / 180.0) * this.yBodyRot + 3.15F;
         double extraX = radius * Mth.sin((float)(Math.PI + angle));
         double extraZ = radius * Mth.cos(angle);
         passenger.setPos(this.getX() + extraX, this.getY() + raiseUp, this.getZ() + extraZ);
         if (this.getAnimationTick() == 32) {
            passenger.hurt(this.level().damageSources().mobAttack(this), ((Double)IafCommonConfig.INSTANCE.cyclops.biteDamage.getValue()).floatValue());
            passenger.stopRiding();
         }
      }
   }

   public void travel(Vec3 vec) {
      if (this.getAnimation() == ANIMATION_EATPLAYER) {
         super.travel(vec.multiply(0.0, 0.0, 0.0));
      } else {
         super.travel(vec);
      }
   }

   public boolean isPushable() {
      return false;
   }

   public void aiStep() {
      super.aiStep();
      if (this.eyeEntity == null) {
         this.eyeEntity = new CyclopsEyeEntity(this, 0.2F, 0.0F, 7.4F, 1.2F, 0.6F, 1.0F);
         this.eyeEntity.copyPosition(this);
         this.level().addFreshEntity(this.eyeEntity);
      }

      if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.getTarget() instanceof Player) {
         this.setTarget(null);
      }

      if (this.isBlinded() && this.getTarget() != null && this.distanceToSqr(this.getTarget()) > 6.0) {
         this.setTarget(null);
      }

      if (this.getAnimation() == ANIMATION_ROAR && this.getAnimationTick() == 5) {
         this.playSound((SoundEvent)IafSounds.CYCLOPS_BLINDED.get(), 1.0F, 1.0F);
      }

      if (this.getAnimation() == ANIMATION_EATPLAYER && this.getAnimationTick() == 25) {
         this.playSound((SoundEvent)IafSounds.CYCLOPS_BITE.get(), 1.0F, 1.0F);
      }

      if (this.getAnimation() == ANIMATION_STOMP && this.getTarget() != null && this.distanceToSqr(this.getTarget()) < 12.0 && this.getAnimationTick() == 14) {
         this.getTarget().hurt(this.level().damageSources().mobAttack(this), (float)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
      }

      if (this.getAnimation() == ANIMATION_KICK && this.getTarget() != null && this.distanceToSqr(this.getTarget()) < 14.0 && this.getAnimationTick() == 12) {
         this.getTarget().hurt(this.level().damageSources().mobAttack(this), (float)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
         if (this.getTarget() != null) {
            this.getTarget().knockback(2.0, this.getX() - this.getTarget().getX(), this.getZ() - this.getTarget().getZ());
         }
      }

      if (this.getAnimation() != ANIMATION_EATPLAYER
         && this.getTarget() != null
         && !this.getPassengers().isEmpty()
         && this.getPassengers().contains(this.getTarget())) {
         this.setAnimation(ANIMATION_EATPLAYER);
      }

      if (this.getAnimation() == NO_ANIMATION && this.getTarget() != null && this.getRandom().nextInt(100) == 0) {
         this.setAnimation(ANIMATION_ROAR);
      }

      if (this.getAnimation() == ANIMATION_STOMP && this.getAnimationTick() == 14) {
         for (int i1 = 0; i1 < 20; i1++) {
            double motionX = this.getRandom().nextGaussian() * 0.07;
            double motionY = this.getRandom().nextGaussian() * 0.07;
            double motionZ = this.getRandom().nextGaussian() * 0.07;
            float radius = -1.5F;
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1 * 1.0F;
            double extraX = radius * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.8F;
            double extraZ = radius * Mth.cos(angle);
            BlockState BlockState = this.level().getBlockState(BlockPos.containing(this.getX() + extraX, this.getY() + extraY - 1.0, this.getZ() + extraZ));
            if (BlockState.isAir() && this.level().isClientSide) {
               this.level()
                  .addParticle(
                     new BlockParticleOption(ParticleTypes.BLOCK, BlockState),
                     this.getX() + extraX,
                     this.getY() + extraY,
                     this.getZ() + extraZ,
                     motionX,
                     motionY,
                     motionZ
                  );
            }
         }
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
      if (this.eyeEntity == null || this.eyeEntity.isRemoved()) {
         this.eyeEntity = new CyclopsEyeEntity(this, 0.2F, 0.0F, 7.4F, 1.2F, 0.5F, 1.0F);
         this.eyeEntity.copyPosition(this);
         this.level().addFreshEntity(this.eyeEntity);
      }

      if (!this.isRemoved()) {
         IafEntityUtil.updatePart(this.eyeEntity, this);
      }

      this.breakBlock();
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
      this.setVariant(this.getRandom().nextInt(4));
      return spawnDataIn;
   }

   public void breakBlock() {
      if ((Boolean)IafCommonConfig.INSTANCE.cyclops.griefing.getValue()) {
         for (int a = (int)Math.round(this.getBoundingBox().minX) - 1; a <= (int)Math.round(this.getBoundingBox().maxX) + 1; a++) {
            for (int b = (int)Math.round(this.getBoundingBox().minY) + 1; b <= (int)Math.round(this.getBoundingBox().maxY) + 2 && b <= 127; b++) {
               for (int c = (int)Math.round(this.getBoundingBox().minZ) - 1; c <= (int)Math.round(this.getBoundingBox().maxZ) + 1; c++) {
                  BlockPos pos = new BlockPos(a, b, c);
                  BlockState state = this.level().getBlockState(pos);
                  Block block = state.getBlock();
                  if (!state.isAir()
                     && !state.getShape(this.level(), pos).isEmpty()
                     && !(block instanceof BushBlock)
                     && block != Blocks.BEDROCK
                     && (state.getBlock() instanceof LeavesBlock || state.is(BlockTags.LOGS))) {
                     this.getDeltaMovement().scale(0.6);
                     if (!((IafEvents.GriefBreakBlock)IafEvents.ON_GRIEF_BREAK_BLOCK.invoker()).onBreakBlock(this, a, b, c)
                        && block != Blocks.AIR
                        && !this.level().isClientSide) {
                        this.level().destroyBlock(pos, true);
                     }
                  }
               }
            }
         }
      }
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public void remove(RemovalReason reason) {
      if (this.eyeEntity != null) {
         this.eyeEntity.remove(reason);
      }

      super.remove(reason);
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, ANIMATION_STOMP, ANIMATION_EATPLAYER, ANIMATION_KICK, ANIMATION_ROAR};
   }

   public boolean isBlinking() {
      return this.tickCount % 50 > 40 && !this.isBlinded();
   }

   public void onHitEye(DamageSource source, float damage) {
      if (!this.isBlinded()) {
         this.setBlinded(true);
         this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(6.0);
         this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35);
         this.setAnimation(ANIMATION_ROAR);
         this.hurt(source, damage * 3.0F);
      }
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)IafSounds.CYCLOPS_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)IafSounds.CYCLOPS_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)IafSounds.CYCLOPS_DIE.get();
   }

   @Override
   public boolean canBeTurnedToStone() {
      return !this.isBlinded();
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }
}
