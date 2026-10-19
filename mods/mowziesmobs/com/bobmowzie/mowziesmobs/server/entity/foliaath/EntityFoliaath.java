package com.bobmowzie.mowziesmobs.server.entity.foliaath;

import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.client.model.tools.IntermittentAnimation;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDieAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationTakeDamage;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

public class EntityFoliaath extends MowzieLLibraryEntity implements Enemy {
   public static final Animation DIE_ANIMATION = Animation.create(50);
   public static final Animation HURT_ANIMATION = Animation.create(10);
   public static final Animation ATTACK_ANIMATION = Animation.create(14);
   private static final EntityDataAccessor<Boolean> CAN_DESPAWN = SynchedEntityData.defineId(EntityFoliaath.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> ACTIVATE_TARGET = SynchedEntityData.defineId(EntityFoliaath.class, EntityDataSerializers.INT);
   private static final int ACTIVATE_DURATION = 30;
   public IntermittentAnimation<EntityFoliaath> openMouth = new IntermittentAnimation(this, 15, 30, 50, !this.level().isClientSide);
   public ControlledAnimation activate = new ControlledAnimation(30);
   public ControlledAnimation deathFlail = new ControlledAnimation(5);
   public ControlledAnimation stopDance = new ControlledAnimation(10);
   public int lastTimeDecrease = 0;
   private int resettingTargetTimer = 0;
   private double prevOpenMouth;
   private double prevActivate;
   private int activateTarget;

   public EntityFoliaath(EntityType<? extends EntityFoliaath> type, Level world) {
      super(type, world);
      this.xpReward = 5;
      this.addIntermittentAnimation(this.openMouth);
   }

   protected boolean isMovementNoisy() {
      return false;
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public void push(double x, double y, double z) {
      super.push(0.0, y, 0.0);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector
         .addGoal(1, new AnimationAttackAI<>(this, ATTACK_ANIMATION, (SoundEvent)MMSounds.ENTITY_FOLIAATH_BITE_1.get(), null, 2.0F, 4.0F, 1.0F, 3));
      this.goalSelector.addGoal(1, new AnimationTakeDamage<>(this));
      this.goalSelector.addGoal(1, new AnimationDieAI<>(this));
      this.targetSelector
         .addGoal(
            3,
            new NearestAttackableTargetGoal(
               this,
               LivingEntity.class,
               0,
               true,
               false,
               e -> (PathfinderMob.class.isAssignableFrom(e.getClass()) || e instanceof Player)
                  && !(e instanceof EntityFoliaath)
                  && !(e instanceof EntityBabyFoliaath)
                  && !(e instanceof Creeper)
            ) {
               public boolean canContinueToUse() {
                  this.findTarget();
                  return this.target != EntityFoliaath.this.getTarget() ? false : super.canContinueToUse();
               }
            }
         );
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(CAN_DESPAWN, true);
      builder.define(ACTIVATE_TARGET, 0);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return MowzieEntity.createAttributes().add(Attributes.ATTACK_DAMAGE, 8.0).add(Attributes.MAX_HEALTH, 10.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)MMSounds.ENTITY_FOLIAATH_HURT.get();
   }

   public SoundEvent getDeathSound() {
      return (SoundEvent)MMSounds.ENTITY_FOLIAATH_DIE.get();
   }

   public boolean isPushable() {
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      this.activate.updatePrevTimer();
      this.deathFlail.updatePrevTimer();
      this.stopDance.updatePrevTimer();
      this.openMouth.updatePrevTimer();
      this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      if (this.getAnimation() == NO_ANIMATION && !this.activate.canIncreaseTimer()) {
         this.openMouth.update();
      } else {
         this.openMouth.stop();
      }

      if (this.activate.getAnimationFraction() >= 0.8F) {
         if (!this.active) {
            this.active = true;
         }
      } else if (this.activate.getAnimationFraction() < 0.8F && this.active) {
         this.active = false;
      }

      if (this.frame % 13 == 3 && this.getAnimation() != DIE_ANIMATION) {
         if (this.openMouth.getTimeRunning() >= 10) {
            this.playSound((SoundEvent)MMSounds.ENTITY_FOLIAATH_PANT_1.get(), 1.0F, 1.0F);
         } else if (this.activate.getTimer() >= 25) {
            this.playSound((SoundEvent)MMSounds.ENTITY_FOLIAATH_PANT_2.get(), 1.0F, 1.0F);
         }
      }

      int openMouthTime = this.openMouth.getTimeRunning();
      if (this.prevOpenMouth - openMouthTime < 0.0) {
         if (openMouthTime == 1) {
            this.playSound((SoundEvent)MMSounds.ENTITY_FOLIAATH_RUSTLE.get(), 1.0F, 1.0F);
         } else if (openMouthTime == 13) {
            this.playSound((SoundEvent)MMSounds.ENTITY_FOLIAATH_GRUNT.get(), 1.0F, 1.0F);
         }
      }

      this.prevOpenMouth = openMouthTime;
      int activateTime = this.activate.getTimer();
      if (!this.level().isClientSide) {
         SoundEvent sound = null;
         if (this.prevActivate - activateTime < 0.0) {
            switch (activateTime) {
               case 1:
                  sound = (SoundEvent)MMSounds.ENTITY_FOLIAATH_RUSTLE.get();
                  break;
               case 5:
                  sound = (SoundEvent)MMSounds.ENTITY_FOLIAATH_MERGE.get();
            }
         } else if (this.prevActivate - activateTime > 0.0) {
            switch (activateTime) {
               case 24:
                  sound = (SoundEvent)MMSounds.ENTITY_FOLIAATH_RETREAT.get();
                  break;
               case 28:
                  sound = (SoundEvent)MMSounds.ENTITY_FOLIAATH_RUSTLE.get();
            }
         }

         if (sound != null) {
            this.playSound(sound, 1.0F, 1.0F);
         }
      }

      this.prevActivate = activateTime;
      this.yBodyRot = 0.0F;
      this.setYRot(0.0F);
      if (this.resettingTargetTimer > 0 && !this.level().isClientSide) {
         this.yHeadRot = this.yHeadRotO;
      }

      if (this.getTarget() != null) {
         this.yHeadRot = this.targetAngle;
         if (this.targetDistance <= 4.0F
            && this.getTarget().getY() - this.getY() >= -1.0
            && this.getTarget().getY() - this.getY() <= 2.0
            && this.getAnimation() == NO_ANIMATION
            && this.active) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, ATTACK_ANIMATION);
         }

         if (this.targetDistance <= 10.5 && this.getTarget().getY() - this.getY() >= -1.5 && this.getTarget().getY() - this.getY() <= 2.0) {
            this.setActivateTarget(30);
            this.lastTimeDecrease = 0;
         } else if (this.lastTimeDecrease <= 30 && this.getAnimation() == NO_ANIMATION) {
            this.setActivateTarget(0);
            this.lastTimeDecrease++;
         }
      } else if (!this.level().isClientSide && this.lastTimeDecrease <= 30 && this.getAnimation() == NO_ANIMATION && this.resettingTargetTimer == 0) {
         this.setActivateTarget(0);
         this.lastTimeDecrease++;
      }

      if (this.getAnimation() == DIE_ANIMATION) {
         if (this.getAnimationTick() <= 12) {
            this.deathFlail.increaseTimer();
         } else {
            this.deathFlail.decreaseTimer();
         }

         this.stopDance.increaseTimer();
         this.setActivateTarget(30);
      }

      if (this.resettingTargetTimer > 0) {
         this.resettingTargetTimer--;
      }

      if (this.activateTarget == activateTime) {
         this.activateTarget = this.getActivateTarget();
      } else if (activateTime < this.activateTarget && this.activate.canIncreaseTimer()
         || activateTime > this.activateTarget && this.activate.canDecreaseTimer()) {
         this.activate.increaseTimer(activateTime < this.activateTarget ? 1 : -2);
      }

      if (!this.level().isClientSide && this.level().getDifficulty() == Difficulty.PEACEFUL) {
         this.discard();
      }
   }

   @Override
   public boolean hurt(DamageSource damageSource, float amount) {
      this.openMouth.resetTimeRunning();
      return (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || this.active) && super.hurt(damageSource, amount);
   }

   @Override
   public Animation getDeathAnimation() {
      return DIE_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return HURT_ANIMATION;
   }

   public boolean isPickable() {
      return true;
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.FOLIAATH.combatConfig;
   }

   private boolean isInTree(LevelAccessor world) {
      int x = Mth.floor(this.getX());
      int y = Mth.floor(this.getBoundingBox().minY);
      int z = Mth.floor(this.getZ());
      BlockPos pos = new BlockPos(x, y, z);
      BlockState floor = world.getBlockState(pos.below());
      if (floor.is(BlockTags.LEAVES)) {
         for (int i = 2; i < 4; i++) {
            BlockState toCheck = world.getBlockState(pos.below(i));
            if (!toCheck.is(BlockTags.LEAVES) && !toCheck.is(BlockTags.LOGS) && !toCheck.isAir()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      return !this.isInTree(world)
         && super.checkSpawnRules(world, reason)
         && this.getEntitiesNearby(Animal.class, 5.0, 5.0, 5.0, 5.0).isEmpty()
         && world.getDifficulty() != Difficulty.PEACEFUL;
   }

   public boolean killedEntity(ServerLevel world, LivingEntity killedEntity) {
      this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 1, true, true));
      return super.killedEntity(world, killedEntity);
   }

   public boolean requiresCustomPersistence() {
      return !(Boolean)this.getEntityData().get(CAN_DESPAWN);
   }

   public void setCanDespawn(boolean canDespawn) {
      this.getEntityData().set(CAN_DESPAWN, canDespawn);
   }

   public int getActivateTarget() {
      return (Integer)this.getEntityData().get(ACTIVATE_TARGET);
   }

   public void setActivateTarget(int activateTarget) {
      this.getEntityData().set(ACTIVATE_TARGET, activateTarget);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("canDespawn", (Boolean)this.getEntityData().get(CAN_DESPAWN));
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setCanDespawn(compound.getBoolean("canDespawn"));
   }

   protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{DIE_ANIMATION, HURT_ANIMATION, ATTACK_ANIMATION};
   }

   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LootTableHandler.FOLIAATH;
   }
}
