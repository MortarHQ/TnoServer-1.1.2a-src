package com.iafenvoy.iceandfire.entity;

import com.google.common.base.Predicate;
import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.ai.CockatriceAIAggroLookGoal;
import com.iafenvoy.iceandfire.entity.ai.CockatriceAIFollowOwnerGoal;
import com.iafenvoy.iceandfire.entity.ai.CockatriceAIStareAttackGoal;
import com.iafenvoy.iceandfire.entity.ai.CockatriceAITargetGoal;
import com.iafenvoy.iceandfire.entity.ai.CockatriceAITargetItemsGoal;
import com.iafenvoy.iceandfire.entity.ai.CockatriceAIWanderGoal;
import com.iafenvoy.iceandfire.entity.ai.EntityAIAttackMeleeNoCooldownGoal;
import com.iafenvoy.iceandfire.entity.ai.IAFLookControl;
import com.iafenvoy.iceandfire.entity.util.BlacklistedFromStatues;
import com.iafenvoy.iceandfire.entity.util.HomePosition;
import com.iafenvoy.iceandfire.entity.util.IHasCustomizableAttributes;
import com.iafenvoy.iceandfire.entity.util.IVillagerFear;
import com.iafenvoy.iceandfire.entity.util.IafEntityUtil;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.iceandfire.registry.tag.IafEntityTags;
import com.iafenvoy.iceandfire.registry.tag.IafItemTags;
import com.iafenvoy.iceandfire.world.DangerousGeneration;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.AnimationHandler;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class CockatriceEntity extends TamableAnimal implements IAnimatedEntity, BlacklistedFromStatues, IVillagerFear, IHasCustomizableAttributes {
   public static final Animation ANIMATION_JUMPAT = Animation.create(30);
   public static final Animation ANIMATION_WATTLESHAKE = Animation.create(20);
   public static final Animation ANIMATION_BITE = Animation.create(15);
   public static final Animation ANIMATION_SPEAK = Animation.create(10);
   public static final Animation ANIMATION_EAT = Animation.create(20);
   public static final float VIEW_RADIUS = 0.6F;
   private static final EntityDataAccessor<Boolean> HEN = SynchedEntityData.defineId(CockatriceEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> STARING = SynchedEntityData.defineId(CockatriceEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> TARGET_ENTITY = SynchedEntityData.defineId(CockatriceEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> TAMING_PLAYER = SynchedEntityData.defineId(CockatriceEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> TAMING_LEVEL = SynchedEntityData.defineId(CockatriceEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(CockatriceEntity.class, EntityDataSerializers.INT);
   private final CockatriceAIStareAttackGoal aiStare;
   private final MeleeAttackGoal aiMelee;
   public float sitProgress;
   public float stareProgress;
   public int ticksStaring = 0;
   public HomePosition homePos;
   public boolean hasHomePosition = false;
   private int animationTick;
   private Animation currentAnimation;
   private boolean isSitting;
   private boolean isStaring;
   private boolean isMeleeMode = false;
   private LivingEntity targetedEntity;
   private int clientSideAttackTime;

   public CockatriceEntity(EntityType<CockatriceEntity> type, Level worldIn) {
      super(type, worldIn);
      this.lookControl = new IAFLookControl(this);
      this.aiStare = new CockatriceAIStareAttackGoal(this, 1.0, 0, 15.0F);
      this.aiMelee = new EntityAIAttackMeleeNoCooldownGoal(this, 1.5, false);
   }

   public static boolean canCockatriceSpawn(EntityType<? extends Mob> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
      return (new DangerousGeneration() {}).isFarEnoughFromSpawn(world, pos) && checkMobSpawnRules(type, world, spawnReason, pos, random);
   }

   public static Builder bakeAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, (Double)IafCommonConfig.INSTANCE.cockatrice.maxHealth.getValue())
         .add(Attributes.MOVEMENT_SPEED, 0.4)
         .add(Attributes.ATTACK_DAMAGE, 5.0)
         .add(Attributes.FOLLOW_RANGE, 64.0)
         .add(Attributes.ARMOR, 2.0);
   }

   @Override
   public void setConfigurableAttributes() {
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)IafCommonConfig.INSTANCE.cockatrice.maxHealth.getValue());
   }

   public int getBaseExperienceReward() {
      return 10;
   }

   public boolean isFood(ItemStack stack) {
      return false;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(3, new CockatriceAIFollowOwnerGoal(this, 1.0, 7.0F, 2.0F));
      this.goalSelector.addGoal(3, new SitWhenOrderedToGoal(this));
      this.goalSelector
         .addGoal(
            3,
            new AvoidEntityGoal(
               this,
               LivingEntity.class,
               14.0F,
               1.0,
               1.0,
               (Predicate)entity -> entity instanceof Player player
                  ? !player.isCreative() && !entity.isSpectator()
                  : entity.getType().is(IafEntityTags.SCARES_COCKATRICES) && !entity.getType().is(IafEntityTags.CHICKENS)
            )
         );
      this.goalSelector.addGoal(4, new CockatriceAIWanderGoal(this, 1.0));
      this.goalSelector.addGoal(5, new CockatriceAIAggroLookGoal(this));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 6.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new CockatriceAITargetItemsGoal(this, false));
      this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
      this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));
      this.targetSelector.addGoal(4, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector
         .addGoal(
            5,
            new CockatriceAITargetGoal<LivingEntity>(
               this,
               LivingEntity.class,
               true,
               entity -> entity instanceof Player player
                  ? !player.isCreative() && !entity.isSpectator()
                  : entity instanceof Enemy
                        && this.isTame()
                        && !(entity instanceof Creeper)
                        && !(entity instanceof ZombifiedPiglin)
                        && !(entity instanceof EnderMan)
                     || entity.getType().is(IafEntityTags.COCKATRICE_TARGETS) && !entity.getType().is(IafEntityTags.CHICKENS)
            )
         );
   }

   public boolean hasRestriction() {
      return this.hasHomePosition && this.getCommand() == 3 && this.getHomeDimensionName().equals(DragonUtils.getDimensionName(this.level()))
         || super.hasRestriction();
   }

   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   public BlockPos getRestrictCenter() {
      return this.hasHomePosition && this.getCommand() == 3 && this.homePos != null ? this.homePos.getPosition() : super.getRestrictCenter();
   }

   public float getRestrictRadius() {
      return 30.0F;
   }

   public String getHomeDimensionName() {
      return this.homePos == null ? "" : this.homePos.getDimension();
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn.getType().is(IafEntityTags.CHICKENS)) {
         return true;
      }

      if (this.isTame()) {
         LivingEntity livingentity = this.getOwner();
         if (entityIn == livingentity) {
            return true;
         }

         if (entityIn instanceof TamableAnimal tameable) {
            return tameable.isOwnedBy(livingentity);
         }

         if (livingentity != null) {
            return livingentity.isAlliedTo(entityIn);
         }
      }

      return super.isAlliedTo(entityIn);
   }

   public boolean hurt(DamageSource source, float damage) {
      if (source.getEntity() != null) {
         Entity entity = source.getEntity();
         if (entity.getType().is(IafEntityTags.SCARES_COCKATRICES)) {
            damage *= 5.0F;
         }
      }

      return source == this.level().damageSources().inWall() ? false : super.hurt(source, damage);
   }

   private boolean canUseStareOn(Entity entity) {
      return !(entity instanceof BlacklistedFromStatues statues && !statues.canBeTurnedToStone()) && !entity.getType().is(IafEntityTags.COCKATRICE_TARGETS);
   }

   private void switchAI(boolean melee) {
      if (melee) {
         this.goalSelector.removeGoal(this.aiStare);
         if (this.aiMelee != null) {
            this.goalSelector.addGoal(2, this.aiMelee);
         }

         this.isMeleeMode = true;
      } else {
         this.goalSelector.removeGoal(this.aiMelee);
         if (this.aiStare != null) {
            this.goalSelector.addGoal(2, this.aiStare);
         }

         this.isMeleeMode = false;
      }
   }

   public boolean doHurtTarget(Entity entityIn) {
      if (this.isStaring()) {
         return false;
      }

      if (this.getRandom().nextBoolean()) {
         if (this.getAnimation() != ANIMATION_JUMPAT && this.getAnimation() != ANIMATION_BITE) {
            this.setAnimation(ANIMATION_JUMPAT);
         }
      } else if (this.getAnimation() != ANIMATION_BITE && this.getAnimation() != ANIMATION_JUMPAT) {
         this.setAnimation(ANIMATION_BITE);
      }

      return false;
   }

   public boolean canMove() {
      return !this.isOrderedToSit() && (this.getAnimation() != ANIMATION_JUMPAT || this.getAnimationTick() >= 7);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(HEN, Boolean.FALSE);
      builder.define(STARING, Boolean.FALSE);
      builder.define(TARGET_ENTITY, 0);
      builder.define(TAMING_PLAYER, 0);
      builder.define(TAMING_LEVEL, 0);
      builder.define(COMMAND, 0);
   }

   public boolean hasTargetedEntity() {
      return (Integer)this.entityData.get(TARGET_ENTITY) != 0;
   }

   public boolean hasTamingPlayer() {
      return (Integer)this.entityData.get(TAMING_PLAYER) != 0;
   }

   public Entity getTamingPlayer() {
      if (!this.hasTamingPlayer()) {
         return null;
      }

      if (this.level().isClientSide) {
         if (this.targetedEntity != null) {
            return this.targetedEntity;
         } else {
            return this.level().getEntity((Integer)this.entityData.get(TAMING_PLAYER)) instanceof LivingEntity livingEntity
               ? (this.targetedEntity = livingEntity)
               : null;
         }
      } else {
         return this.level().getEntity((Integer)this.entityData.get(TAMING_PLAYER));
      }
   }

   public void setTamingPlayer(int entityId) {
      this.entityData.set(TAMING_PLAYER, entityId);
   }

   public LivingEntity getTargetedEntity() {
      boolean blindness = this.hasEffect(MobEffects.BLINDNESS)
         || this.getTarget() != null && this.getTarget().hasEffect(MobEffects.BLINDNESS)
         || GorgonEntity.isBlindfolded(this.getTarget());
      if (blindness) {
         return null;
      }

      if (!this.hasTargetedEntity()) {
         return null;
      }

      if (this.level().isClientSide) {
         if (this.targetedEntity != null) {
            return this.targetedEntity;
         } else {
            return this.level().getEntity((Integer)this.entityData.get(TARGET_ENTITY)) instanceof LivingEntity livingEntity
               ? (this.targetedEntity = livingEntity)
               : null;
         }
      } else {
         return this.getTarget();
      }
   }

   public void setTargetedEntity(int entityId) {
      this.entityData.set(TARGET_ENTITY, entityId);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      super.onSyncedDataUpdated(key);
      if (TARGET_ENTITY.equals(key)) {
         this.clientSideAttackTime = 0;
         this.targetedEntity = null;
      }
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putBoolean("Hen", this.isHen());
      tag.putBoolean("Staring", this.isStaring());
      tag.putInt("TamingLevel", this.getTamingLevel());
      tag.putInt("TamingPlayer", (Integer)this.entityData.get(TAMING_PLAYER));
      tag.putInt("Command", this.getCommand());
      tag.putBoolean("HasHomePosition", this.hasHomePosition);
      if (this.homePos != null && this.hasHomePosition) {
         this.homePos.write(tag);
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setHen(tag.getBoolean("Hen"));
      this.setStaring(tag.getBoolean("Staring"));
      this.setTamingLevel(tag.getInt("TamingLevel"));
      this.setTamingPlayer(tag.getInt("TamingPlayer"));
      this.setCommand(tag.getInt("Command"));
      this.hasHomePosition = tag.getBoolean("HasHomePosition");
      if (this.hasHomePosition && tag.getInt("HomeAreaX") != 0 && tag.getInt("HomeAreaY") != 0 && tag.getInt("HomeAreaZ") != 0) {
         this.homePos = new HomePosition(tag, this.level());
      }

      this.setConfigurableAttributes();
   }

   public boolean isOrderedToSit() {
      if (this.level().isClientSide) {
         boolean isSitting = ((Byte)this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
         this.isSitting = isSitting;
         return isSitting;
      } else {
         return this.isSitting;
      }
   }

   public void setOrderedToSit(boolean sitting) {
      super.setSwimming(sitting);
      if (!this.level().isClientSide) {
         this.isSitting = sitting;
      }
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
      this.setHen(this.getRandom().nextBoolean());
      return spawnDataIn;
   }

   public boolean isHen() {
      return (Boolean)this.entityData.get(HEN);
   }

   public void setHen(boolean hen) {
      this.entityData.set(HEN, hen);
   }

   public int getTamingLevel() {
      return (Integer)this.entityData.get(TAMING_LEVEL);
   }

   public void setTamingLevel(int level) {
      this.entityData.set(TAMING_LEVEL, level);
   }

   public int getCommand() {
      return (Integer)this.entityData.get(COMMAND);
   }

   public void setCommand(int command) {
      this.entityData.set(COMMAND, command);
      this.setOrderedToSit(command == 1);
   }

   public boolean isStaring() {
      return this.level().isClientSide ? (this.isStaring = (Boolean)this.entityData.get(STARING)) : this.isStaring;
   }

   public void setStaring(boolean staring) {
      this.entityData.set(STARING, staring);
      if (!this.level().isClientSide) {
         this.isStaring = staring;
      }
   }

   public void forcePreyToLook(Mob mob) {
      mob.getLookControl().setLookAt(this.getX(), this.getY() + this.getEyeHeight(), this.getZ(), mob.getMaxHeadYRot(), mob.getMaxHeadXRot());
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack stackInHand = player.getItemInHand(hand);
      Item itemInHand = stackInHand.getItem();
      if (stackInHand.getItem() != Items.NAME_TAG && itemInHand != Items.LEAD && itemInHand != Items.POISONOUS_POTATO) {
         if (this.isTame() && this.isOwnedBy(player)) {
            if (stackInHand.is(IafItemTags.HEAL_COCKATRICE)) {
               if (this.getHealth() < this.getMaxHealth()) {
                  this.heal(8.0F);
                  this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                  stackInHand.shrink(1);
               }

               return InteractionResult.SUCCESS;
            }

            if (stackInHand.isEmpty()) {
               if (player.isShiftKeyDown()) {
                  if (this.hasHomePosition) {
                     this.hasHomePosition = false;
                     player.displayClientMessage(Component.translatable("cockatrice.command.remove_home"), true);
                  } else {
                     BlockPos pos = this.blockPosition();
                     this.homePos = new HomePosition(pos, this.level());
                     this.hasHomePosition = true;
                     player.displayClientMessage(
                        Component.translatable("cockatrice.command.new_home", new Object[]{pos.getX(), pos.getY(), pos.getZ(), this.homePos.getDimension()}),
                        true
                     );
                  }
               } else {
                  this.setCommand(this.getCommand() + 1);
                  if (this.getCommand() > 3) {
                     this.setCommand(0);
                  }

                  player.displayClientMessage(Component.translatable("cockatrice.command." + this.getCommand()), true);
                  this.playSound(SoundEvents.ZOMBIE_INFECT, 1.0F, 1.0F);
               }

               return InteractionResult.SUCCESS;
            }
         }

         return InteractionResult.FAIL;
      } else {
         return super.mobInteract(player, hand);
      }
   }

   public void aiStep() {
      super.aiStep();
      LivingEntity attackTarget = this.getTarget();
      if (this.level().getDifficulty() == Difficulty.PEACEFUL && attackTarget instanceof Player) {
         this.setTarget(null);
      }

      if (this.isOrderedToSit() && this.getCommand() != 1) {
         this.setOrderedToSit(false);
      }

      if (this.isOrderedToSit() && attackTarget != null) {
         this.setTarget(null);
      }

      if (attackTarget != null && this.isAlliedTo(attackTarget)) {
         this.setTarget(null);
      }

      if (!this.level().isClientSide) {
         if (attackTarget == null || !attackTarget.isAlive()) {
            this.setTargetedEntity(0);
         } else if (this.isStaring() || this.shouldStareAttack(attackTarget)) {
            this.setTargetedEntity(attackTarget.getId());
         }
      }

      if (this.getAnimation() == ANIMATION_BITE && attackTarget != null && this.getAnimationTick() == 7) {
         double dist = this.distanceToSqr(attackTarget);
         if (dist < 8.0) {
            attackTarget.hurt(this.level().damageSources().mobAttack(this), (int)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
         }
      }

      if (this.getAnimation() == ANIMATION_JUMPAT && attackTarget != null) {
         double dist = this.distanceToSqr(attackTarget);
         double d0 = attackTarget.getX() - this.getX();
         double d1 = attackTarget.getZ() - this.getZ();
         float leap = Mth.sqrt((float)(d0 * d0 + d1 * d1));
         if (dist < 4.0 && this.getAnimationTick() > 10) {
            attackTarget.hurt(this.level().damageSources().mobAttack(this), (int)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
            if (leap >= 1.0E-4) {
               attackTarget.setDeltaMovement(
                  attackTarget.getDeltaMovement()
                     .add(d0 / leap * 0.8F + this.getDeltaMovement().x * 0.2F, 0.0, d1 / leap * 0.8F + this.getDeltaMovement().z * 0.2F)
               );
            }
         }
      }

      boolean sitting = this.isOrderedToSit();
      if (sitting && this.sitProgress < 20.0F) {
         this.sitProgress += 0.5F;
      } else if (!sitting && this.sitProgress > 0.0F) {
         this.sitProgress -= 0.5F;
      }

      boolean staring = this.isStaring();
      if (staring && this.stareProgress < 20.0F) {
         this.stareProgress += 0.5F;
      } else if (!staring && this.stareProgress > 0.0F) {
         this.stareProgress -= 0.5F;
      }

      if (!this.level().isClientSide) {
         if (staring) {
            this.ticksStaring++;
         } else {
            this.ticksStaring = 0;
         }
      }

      if (!this.level().isClientSide && staring && (attackTarget == null || this.shouldMelee())) {
         this.setStaring(false);
      }

      if (attackTarget != null) {
         this.getLookControl()
            .setLookAt(
               attackTarget.getX(), attackTarget.getY() + attackTarget.getEyeHeight(), attackTarget.getZ(), this.getMaxHeadYRot(), this.getMaxHeadXRot()
            );
         if (!this.shouldMelee() && attackTarget instanceof Mob mob) {
            this.forcePreyToLook(mob);
         }
      }

      boolean blindness = this.hasEffect(MobEffects.BLINDNESS) || attackTarget != null && attackTarget.hasEffect(MobEffects.BLINDNESS);
      if (blindness) {
         this.setStaring(false);
      }

      if (!this.level().isClientSide
         && !blindness
         && attackTarget != null
         && IafEntityUtil.isEntityLookingAt(this, attackTarget, 0.6F)
         && IafEntityUtil.isEntityLookingAt(attackTarget, this, 0.6F)
         && !GorgonEntity.isBlindfolded(attackTarget)
         && !this.shouldMelee()) {
         if (!this.isStaring()) {
            this.setStaring(true);
         } else {
            int attackStrength = this.getFriendsCount(attackTarget);
            if (this.level().getDifficulty() == Difficulty.HARD) {
               attackStrength++;
            }

            attackTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 10, 2 + Math.min(1, attackStrength)));
            attackTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, Math.min(4, attackStrength)));
            attackTarget.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            if (attackStrength >= 2 && attackTarget.tickCount % 40 == 0) {
               attackTarget.hurt(this.level().damageSources().wither(), attackStrength - 1);
            }

            attackTarget.setLastHurtByMob(this);
            if (!this.isTame() && attackTarget instanceof Player) {
               this.setTamingPlayer(attackTarget.getId());
               this.setTamingLevel(this.getTamingLevel() + 1);
               if (this.getTamingLevel() % 100 == 0) {
                  this.level().broadcastEntityEvent(this, (byte)46);
               }

               if (this.getTamingLevel() >= 1000) {
                  this.level().broadcastEntityEvent(this, (byte)45);
                  if (this.getTamingPlayer() instanceof Player player) {
                     this.tame(player);
                  }

                  this.setTarget(null);
                  this.setTamingPlayer(0);
                  this.setTargetedEntity(0);
               }
            }
         }
      }

      if (!this.level().isClientSide && attackTarget == null && this.getRandom().nextInt(300) == 0 && this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(ANIMATION_WATTLESHAKE);
      }

      if (!this.level().isClientSide) {
         if (this.shouldMelee() && !this.isMeleeMode) {
            this.switchAI(true);
         }

         if (!this.shouldMelee() && this.isMeleeMode) {
            this.switchAI(false);
         }
      }

      if (this.level().isClientSide
         && this.getTargetedEntity() != null
         && IafEntityUtil.isEntityLookingAt(this, this.getTargetedEntity(), 0.6F)
         && IafEntityUtil.isEntityLookingAt(this.getTargetedEntity(), this, 0.6F)
         && this.isStaring()
         && this.hasTargetedEntity()) {
         if (this.clientSideAttackTime < this.getAttackDuration()) {
            this.clientSideAttackTime++;
         }

         LivingEntity livingEntity = this.getTargetedEntity();
         if (livingEntity != null) {
            this.getLookControl().setLookAt(livingEntity, 90.0F, 90.0F);
            this.getLookControl().tick();
            double d5 = this.getAttackAnimationScale(0.0F);
            double d0 = livingEntity.getX() - this.getX();
            double d1 = livingEntity.getY() + livingEntity.getBbHeight() * 0.5F - (this.getY() + this.getEyeHeight());
            double d2 = livingEntity.getZ() - this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d0 /= d3;
            d1 /= d3;
            d2 /= d3;
            double d4 = this.random.nextDouble();

            while (d4 < d3) {
               d4 += 1.8 - d5 + this.random.nextDouble() * (1.7 - d5);
               this.level()
                  .addParticle(
                     ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, -16777216),
                     this.getX() + d0 * d4,
                     this.getY() + d1 * d4 + this.getEyeHeight(),
                     this.getZ() + d2 * d4,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   private int getFriendsCount(LivingEntity attackTarget) {
      if (this.getTarget() == null) {
         return 0;
      }

      float dist = ((Integer)IafCommonConfig.INSTANCE.cockatrice.chickenSearchLength.getValue()).intValue();
      List<CockatriceEntity> list = this.level().getEntitiesOfClass(CockatriceEntity.class, this.getBoundingBox().expandTowards(dist, dist, dist));
      int i = 0;

      for (CockatriceEntity cockatrice : list) {
         if (!cockatrice.is(this) && cockatrice.getTarget() != null && cockatrice.getTarget() == this.getTarget()) {
            boolean bothLooking = IafEntityUtil.isEntityLookingAt(cockatrice, cockatrice.getTarget(), 0.6F)
               && IafEntityUtil.isEntityLookingAt(cockatrice.getTarget(), cockatrice, 0.6F);
            if (bothLooking) {
               i++;
            }
         }
      }

      return i;
   }

   public float getAttackAnimationScale(float f) {
      return (this.clientSideAttackTime + f) / this.getAttackDuration();
   }

   public boolean shouldStareAttack(Entity entity) {
      return this.distanceTo(entity) > 5.0F;
   }

   public int getAttackDuration() {
      return 80;
   }

   private boolean shouldMelee() {
      boolean blindness = this.hasEffect(MobEffects.BLINDNESS) || this.getTarget() != null && this.getTarget().hasEffect(MobEffects.BLINDNESS);
      if (this.getTarget() != null) {
         if (this.distanceTo(this.getTarget()) < 4.0) {
            return true;
         }

         Entity entity = this.getTarget();
         return entity.getType().is(IafEntityTags.COCKATRICE_TARGETS) || blindness || !this.canUseStareOn(this.getTarget());
      } else {
         return false;
      }
   }

   public void travel(Vec3 motionVec) {
      if (!this.canMove() && !this.isVehicle()) {
         motionVec = motionVec.multiply(0.0, 1.0, 0.0);
      }

      super.travel(motionVec);
   }

   public void playAmbientSound() {
      if (this.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
         this.setAnimation(ANIMATION_SPEAK);
      }

      super.playAmbientSound();
   }

   protected void playHurtSound(DamageSource source) {
      if (this.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
         this.setAnimation(ANIMATION_SPEAK);
      }

      super.playHurtSound(source);
   }

   public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
      return null;
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
      return new Animation[]{NO_ANIMATION, ANIMATION_JUMPAT, ANIMATION_WATTLESHAKE, ANIMATION_BITE, ANIMATION_SPEAK, ANIMATION_EAT};
   }

   @Override
   public boolean canBeTurnedToStone() {
      return false;
   }

   public boolean isTargetBlocked(Vec3 target) {
      Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
      return this.level().clip(new ClipContext(Vector3d, target, Block.COLLIDER, Fluid.NONE, this)).getType() == Type.MISS;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)IafSounds.COCKATRICE_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)IafSounds.COCKATRICE_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)IafSounds.COCKATRICE_DIE.get();
   }

   public void handleEntityEvent(byte id) {
      if (id == 45) {
         this.playEffect(true);
      } else if (id == 46) {
         this.playEffect(false);
      } else {
         super.handleEntityEvent(id);
      }
   }

   protected void playEffect(boolean play) {
      ParticleOptions enumparticletypes = ParticleTypes.HEART;
      if (!play) {
         enumparticletypes = ParticleTypes.DAMAGE_INDICATOR;
      }

      for (int i = 0; i < 7; i++) {
         double d0 = this.random.nextGaussian() * 0.02;
         double d1 = this.random.nextGaussian() * 0.02;
         double d2 = this.random.nextGaussian() * 0.02;
         this.level()
            .addParticle(
               enumparticletypes,
               this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
               this.getY() + 0.5 + this.random.nextFloat() * this.getBbHeight(),
               this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
               d0,
               d1,
               d2
            );
      }
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }
}
