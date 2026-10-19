package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.ai.GhostAIChargeGoal;
import com.iafenvoy.iceandfire.entity.ai.GhostPathNavigatorGoal;
import com.iafenvoy.iceandfire.entity.util.BlacklistedFromStatues;
import com.iafenvoy.iceandfire.entity.util.IAnimalFear;
import com.iafenvoy.iceandfire.entity.util.IHasCustomizableAttributes;
import com.iafenvoy.iceandfire.entity.util.IHumanoid;
import com.iafenvoy.iceandfire.entity.util.IVillagerFear;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafParticles;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.AnimationHandler;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

public class GhostEntity extends Monster implements IAnimatedEntity, IVillagerFear, IAnimalFear, IHumanoid, BlacklistedFromStatues, IHasCustomizableAttributes {
   private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(GhostEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(GhostEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_DAYTIME_MODE = SynchedEntityData.defineId(GhostEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> WAS_FROM_CHEST = SynchedEntityData.defineId(GhostEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> DAYTIME_COUNTER = SynchedEntityData.defineId(GhostEntity.class, EntityDataSerializers.INT);
   public static Animation ANIMATION_SCARE;
   public static Animation ANIMATION_HIT;
   private int animationTick;
   private Animation currentAnimation;

   public GhostEntity(EntityType<GhostEntity> type, Level worldIn) {
      super(type, worldIn);
      ANIMATION_SCARE = Animation.create(30);
      ANIMATION_HIT = Animation.create(10);
      this.moveControl = new GhostEntity.MoveHelper(this);
   }

   public static Builder bakeAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, (Double)IafCommonConfig.INSTANCE.ghost.maxHealth.getValue())
         .add(Attributes.FOLLOW_RANGE, 64.0)
         .add(Attributes.MOVEMENT_SPEED, 0.15)
         .add(Attributes.ATTACK_DAMAGE, (Double)IafCommonConfig.INSTANCE.ghost.attackDamage.getValue())
         .add(Attributes.ARMOR, 1.0);
   }

   protected ResourceKey<LootTable> getDefaultLootTable() {
      return this.wasFromChest() ? BuiltInLootTables.EMPTY : this.getType().getDefaultLootTable();
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)IafSounds.GHOST_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)IafSounds.GHOST_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)IafSounds.GHOST_DIE.get();
   }

   @Override
   public void setConfigurableAttributes() {
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)IafCommonConfig.INSTANCE.ghost.maxHealth.getValue());
      this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((Double)IafCommonConfig.INSTANCE.ghost.attackDamage.getValue());
   }

   public boolean canBeAffected(MobEffectInstance potioneffectIn) {
      return potioneffectIn.getEffect() != MobEffects.POISON && potioneffectIn.getEffect() != MobEffects.WITHER && super.canBeAffected(potioneffectIn);
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return super.isInvulnerableTo(source)
         || source.is(DamageTypeTags.IS_FIRE)
         || source.is(DamageTypes.IN_WALL)
         || source.is(DamageTypes.CACTUS)
         || source.is(DamageTypes.DROWN)
         || source.is(DamageTypes.FALLING_BLOCK)
         || source.is(DamageTypes.FALLING_ANVIL)
         || source.is(DamageTypes.SWEET_BERRY_BUSH);
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new GhostPathNavigatorGoal(this, worldIn);
   }

   public boolean isCharging() {
      return (Boolean)this.entityData.get(CHARGING);
   }

   public void setCharging(boolean moving) {
      this.entityData.set(CHARGING, moving);
   }

   public boolean isDaytimeMode() {
      return (Boolean)this.entityData.get(IS_DAYTIME_MODE);
   }

   public void setDaytimeMode(boolean moving) {
      this.entityData.set(IS_DAYTIME_MODE, moving);
   }

   public boolean wasFromChest() {
      return (Boolean)this.entityData.get(WAS_FROM_CHEST);
   }

   public void setFromChest(boolean moving) {
      this.entityData.set(WAS_FROM_CHEST, moving);
   }

   public boolean isPushable() {
      return false;
   }

   @Override
   public boolean canBeTurnedToStone() {
      return false;
   }

   protected void doPush(Entity entity) {
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new RestrictSunGoal(this));
      this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
      this.goalSelector.addGoal(3, new GhostAIChargeGoal(this));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F) {
         public boolean canContinueToUse() {
            return this.lookAt != null && this.lookAt instanceof Player && ((Player)this.lookAt).isCreative() ? false : super.canContinueToUse();
         }
      });
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.6) {
         public boolean canUse() {
            this.interval = 60;
            return super.canUse();
         }
      });
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, 10, false, false, Entity::isAlive));
      this.targetSelector
         .addGoal(
            3,
            new NearestAttackableTargetGoal(this, LivingEntity.class, 10, false, false, entity -> DragonUtils.isAlive(entity) && DragonUtils.isVillager(entity))
         );
   }

   public void aiStep() {
      super.aiStep();
      this.noPhysics = true;
      if (!this.level().isClientSide) {
         boolean day = this.isSunBurnTick() && !this.wasFromChest();
         if (day) {
            if (!this.isDaytimeMode()) {
               this.setAnimation(ANIMATION_SCARE);
            }

            this.setDaytimeMode(true);
         } else {
            this.setDaytimeMode(false);
            this.setDaytimeCounter(0);
         }

         if (this.isDaytimeMode()) {
            this.setDeltaMovement(Vec3.ZERO);
            this.setDaytimeCounter(this.getDaytimeCounter() + 1);
            if (this.getDaytimeCounter() >= 100) {
               this.setInvisible(true);
            }
         } else {
            this.setInvisible(this.hasEffect(MobEffects.INVISIBILITY));
            this.setDaytimeCounter(0);
         }
      } else if (this.getAnimation() == ANIMATION_SCARE && this.getAnimationTick() == 3 && !this.isHauntedShoppingList() && this.random.nextInt(3) == 0) {
         this.playSound((SoundEvent)IafSounds.GHOST_JUMPSCARE.get(), this.getSoundVolume(), this.getVoicePitch());
         if (this.level().isClientSide) {
            this.level().addParticle((ParticleOptions)IafParticles.GHOST_APPEARANCE.get(), this.getX(), this.getY(), this.getZ(), this.getId(), 0.0, 0.0);
         }
      }

      if (this.getAnimation() == ANIMATION_HIT
         && this.getTarget() != null
         && this.distanceTo(this.getTarget()) < 1.4
         && this.getAnimationTick() >= 4
         && this.getAnimationTick() < 6) {
         this.playSound((SoundEvent)IafSounds.GHOST_ATTACK.get(), this.getSoundVolume(), this.getVoicePitch());
         this.doHurtTarget(this.getTarget());
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public boolean isNoAi() {
      return this.isDaytimeMode() || super.isNoAi();
   }

   public boolean isSilent() {
      return this.isDaytimeMode() || super.isSilent();
   }

   protected boolean isSunBurnTick() {
      if (this.level().isDay() && !this.level().isClientSide) {
         float f = this.level().getBrightness(LightLayer.BLOCK, this.blockPosition());
         BlockPos blockpos = this.getVehicle() instanceof Boat
            ? new BlockPos(this.getBlockX(), this.getBlockY(), this.getBlockZ()).above()
            : new BlockPos(this.getBlockX(), this.getBlockY() + 4, this.getBlockZ());
         return f > 0.5F && this.level().canSeeSky(blockpos);
      } else {
         return false;
      }
   }

   public boolean isNoGravity() {
      return true;
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (itemstack != null && itemstack.getItem() == IafItems.MANUSCRIPT.get() && !this.isHauntedShoppingList()) {
         this.setColor(-1);
         this.playSound((SoundEvent)IafSounds.BESTIARY_PAGE.get(), 1.0F, 1.0F);
         if (!player.isCreative()) {
            itemstack.shrink(1);
         }

         return InteractionResult.SUCCESS;
      } else {
         return super.mobInteract(player, hand);
      }
   }

   public void travel(Vec3 vec) {
      if (this.isDaytimeMode()) {
         super.travel(Vec3.ZERO);
      } else {
         super.travel(vec);
      }
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
      this.setColor(this.random.nextInt(3));
      if (this.random.nextInt(200) == 0) {
         this.setColor(-1);
      }

      return spawnDataIn;
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(COLOR, 0);
      builder.define(CHARGING, false);
      builder.define(IS_DAYTIME_MODE, false);
      builder.define(WAS_FROM_CHEST, false);
      builder.define(DAYTIME_COUNTER, 0);
   }

   public int getColor() {
      return Mth.clamp((Integer)this.getEntityData().get(COLOR), -1, 2);
   }

   public void setColor(int color) {
      this.getEntityData().set(COLOR, color);
   }

   public int getDaytimeCounter() {
      return (Integer)this.getEntityData().get(DAYTIME_COUNTER);
   }

   public void setDaytimeCounter(int counter) {
      this.getEntityData().set(DAYTIME_COUNTER, counter);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setColor(compound.getInt("Color"));
      this.setDaytimeMode(compound.getBoolean("DaytimeMode"));
      this.setDaytimeCounter(compound.getInt("DaytimeCounter"));
      this.setFromChest(compound.getBoolean("FromChest"));
      this.setConfigurableAttributes();
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Color", this.getColor());
      compound.putBoolean("DaytimeMode", this.isDaytimeMode());
      compound.putInt("DaytimeCounter", this.getDaytimeCounter());
      compound.putBoolean("FromChest", this.wasFromChest());
   }

   public boolean isHauntedShoppingList() {
      return this.getColor() == -1;
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, ANIMATION_SCARE, ANIMATION_HIT};
   }

   @Override
   public boolean shouldAnimalsFear(Entity entity) {
      return false;
   }

   static class MoveHelper extends MoveControl {
      final GhostEntity ghost;

      public MoveHelper(GhostEntity ghost) {
         super(ghost);
         this.ghost = ghost;
      }

      public void tick() {
         if (this.operation == Operation.MOVE_TO) {
            Vec3 vec3d = new Vec3(this.getWantedX() - this.ghost.getX(), this.getWantedY() - this.ghost.getY(), this.getWantedZ() - this.ghost.getZ());
            double d0 = vec3d.length();
            double edgeLength = this.ghost.getBoundingBox().getSize();
            if (d0 < edgeLength) {
               this.operation = Operation.WAIT;
               this.ghost.setDeltaMovement(this.ghost.getDeltaMovement().scale(0.5));
            } else {
               this.ghost.setDeltaMovement(this.ghost.getDeltaMovement().add(vec3d.scale(this.speedModifier * 0.5 * 0.05 / d0)));
               if (this.ghost.getTarget() == null) {
                  Vec3 vec3d1 = this.ghost.getDeltaMovement();
                  this.ghost.setYRot(-((float)Mth.atan2(vec3d1.x, vec3d1.z)) * (180.0F / (float)Math.PI));
               } else {
                  double d4 = this.ghost.getTarget().getX() - this.ghost.getX();
                  double d5 = this.ghost.getTarget().getZ() - this.ghost.getZ();
                  this.ghost.setYRot(-((float)Mth.atan2(d4, d5)) * (180.0F / (float)Math.PI));
               }

               this.ghost.yBodyRot = this.ghost.getYRot();
            }
         }
      }
   }
}
