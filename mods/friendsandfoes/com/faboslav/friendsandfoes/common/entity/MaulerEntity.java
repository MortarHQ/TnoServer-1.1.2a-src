package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.goal.mauler.MaulerBurrowDownGoal;
import com.faboslav.friendsandfoes.common.entity.ai.goal.mauler.MaulerLookAroundGoal;
import com.faboslav.friendsandfoes.common.entity.ai.goal.mauler.MaulerLookAtEntityGoal;
import com.faboslav.friendsandfoes.common.entity.ai.goal.mauler.MaulerMeeleAttackGoal;
import com.faboslav.friendsandfoes.common.entity.ai.goal.mauler.MaulerWanderAroundFarGoal;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.MaulerAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityDataSerializers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedInteractionResult;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class MaulerEntity extends Animal implements NeutralMob, AnimatedEntity {
   private static final int HEALTH = 20;
   private static final float ANGERED_MOVEMENT_SPEED = 0.5F;
   private static final float MOVEMENT_SPEED = 0.3F;
   private static final float ATTACK_DAMAGE = 8.0F;
   private static final int MAXIMUM_STORED_EXPERIENCE_POINTS = 1395;
   public static final int MIN_TICKS_UNTIL_NEXT_BURROWING = 3000;
   public static final int MAX_TICKS_UNTIL_NEXT_BURROWING = 6000;
   private static final MaulerEntity.Type DEFAULT_TYPE = MaulerEntity.Type.DESERT;
   private static final String TYPE_NBT_NAME = "Type";
   private static final String STORED_EXPERIENCE_POINTS_NBT_NAME = "StoredExperiencePoints";
   private static final String IS_BURROWED_DOWN_NBT_NAME = "IsBurrowedDown";
   private static final String TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME = "TicksUntilNextBurrowingDown";
   private static final String BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME = "BurrowingDownAnimationProgress";
   private static final String BURROWED_DOWN_TICKS_NBT_NAME = "BurrowedDownTicks";
   private static final EntityDataAccessor<String> TYPE = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.STRING);
   private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> STORED_EXPERIENCE_POINTS = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> IS_MOVING = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_BURROWED_DOWN = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> TICKS_UNTIL_NEXT_BURROWING_DOWN = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Float> BURROWING_DOWN_ANIMATION_PROGRESS = SynchedEntityData.defineId(
      MaulerEntity.class, EntityDataSerializers.FLOAT
   );
   private static final EntityDataAccessor<Integer> POSE_TICKS = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<FriendsAndFoesEntityPose> ENTITY_POSE = SynchedEntityData.defineId(
      MaulerEntity.class, FriendsAndFoesEntityDataSerializers.ENTITY_POSE
   );
   @Nullable
   private UUID angryAt;
   public MaulerBurrowDownGoal burrowDownGoal;
   private AnimationContextTracker animationContextTracker;

   @Override
   public AnimationContextTracker getAnimationContextTracker() {
      if (this.animationContextTracker == null) {
         this.animationContextTracker = new AnimationContextTracker();

         for (AnimationHolder animation : this.getTrackedAnimations()) {
            this.animationContextTracker.add(animation);
         }

         this.animationContextTracker.add(MaulerAnimations.SNAP);
      }

      return this.animationContextTracker;
   }

   @Override
   public ArrayList<AnimationHolder> getTrackedAnimations() {
      return MaulerAnimations.TRACKED_ANIMATIONS;
   }

   @Override
   public AnimationHolder getMovementAnimation() {
      return this.isAngry() ? MaulerAnimations.WALK : MaulerAnimations.RUN;
   }

   @Override
   public int getCurrentAnimationTick() {
      return (Integer)this.entityData.get(POSE_TICKS);
   }

   @Override
   public void setCurrentAnimationTick(int keyframeAnimationTicks) {
      this.entityData.set(POSE_TICKS, keyframeAnimationTicks);
   }

   @Nullable
   @Override
   public AnimationHolder getAnimationByPose() {
      AnimationHolder animation = null;
      if (this.isInEntityPose(FriendsAndFoesEntityPose.IDLE) && !this.walkAnimation.isMoving()) {
         animation = MaulerAnimations.IDLE;
      }

      return animation;
   }

   public MaulerEntity(EntityType<? extends MaulerEntity> entityType, Level world) {
      super(entityType, world);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(TYPE, DEFAULT_TYPE.name());
      builder.define(ANGER_TIME, 0);
      builder.define(STORED_EXPERIENCE_POINTS, 0);
      builder.define(IS_MOVING, false);
      builder.define(IS_BURROWED_DOWN, false);
      builder.define(TICKS_UNTIL_NEXT_BURROWING_DOWN, this.getRandom().nextIntBetweenInclusive(3000, 6000));
      builder.define(BURROWING_DOWN_ANIMATION_PROGRESS, 0.0F);
      builder.define(POSE_TICKS, 0);
      builder.define(ENTITY_POSE, FriendsAndFoesEntityPose.IDLE);
   }

   public void addAdditionalSaveData(CompoundTag nbt) {
      super.addAdditionalSaveData(nbt);
      this.addPersistentAngerSaveData(nbt);
      nbt.putString("Type", this.getMaulerType().getName());
      nbt.putInt("StoredExperiencePoints", this.getStoredExperiencePoints());
      nbt.putBoolean("IsBurrowedDown", this.isBurrowedDown());
      nbt.putInt("TicksUntilNextBurrowingDown", this.getTicksUntilNextBurrowingDown());
      nbt.putFloat("BurrowingDownAnimationProgress", this.getBurrowingDownAnimationProgress());
      if (this.burrowDownGoal != null && this.isBurrowedDown() && this.burrowDownGoal.getBurrowedDownTicks() > 0) {
         nbt.putInt("BurrowedDownTicks", this.burrowDownGoal.getBurrowedDownTicks());
      }
   }

   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      this.readPersistentAngerSaveData(this.level(), nbt);
      this.setType(MaulerEntity.Type.fromName(VersionedNbt.getString(nbt, "Type", this.getMaulerType().getName())));
      this.setStoredExperiencePoints(VersionedNbt.getInt(nbt, "StoredExperiencePoints", this.getStoredExperiencePoints()));
      this.setBurrowedDown(VersionedNbt.getBoolean(nbt, "IsBurrowedDown", this.isBurrowedDown()));
      this.setTicksUntilNextBurrowingDown(VersionedNbt.getInt(nbt, "TicksUntilNextBurrowingDown", this.getTicksUntilNextBurrowingDown()));
      this.setBurrowingDownAnimationProgress(VersionedNbt.getFloat(nbt, "BurrowingDownAnimationProgress", this.getBurrowingDownAnimationProgress()));
      if (this.burrowDownGoal != null && this.isBurrowedDown()) {
         int burrowedDownTicks = VersionedNbt.getInt(nbt, "BurrowedDownTicks", 0);
         if (burrowedDownTicks != 0) {
            this.burrowDownGoal.setBurrowedDownTicks(burrowedDownTicks);
            this.setInvulnerable(true);
            this.setInvisible(true);
         }
      }
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
      ResourceKey<Biome> biomeKey = world.getBiome(this.blockPosition()).unwrapKey().orElse(Biomes.SAVANNA);
      MaulerEntity.Type type = MaulerEntity.Type.getTypeByBiome(biomeKey);
      this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
      this.setType(type);
      this.setSize();
      return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
      return null;
   }

   public boolean isFood(ItemStack itemStack) {
      return false;
   }

   public static boolean canSpawn(
      EntityType<MaulerEntity> maulerEntityType, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random
   ) {
      return serverWorldAccess.getBlockState(blockPos.below()).is(FriendsAndFoesTags.MAULERS_SPAWNABLE_ON);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new MaulerMeeleAttackGoal(this, 0.5, false));
      this.goalSelector.addGoal(3, new MaulerWanderAroundFarGoal(this, 0.6));
      this.goalSelector.addGoal(4, new MaulerLookAtEntityGoal(this, Player.class, 10.0F));
      this.goalSelector.addGoal(5, new MaulerLookAroundGoal(this));
      this.burrowDownGoal = new MaulerBurrowDownGoal(this);
      this.goalSelector.addGoal(6, this.burrowDownGoal);
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector
         .addGoal(
            2,
            new NearestAttackableTargetGoal(
               this,
               PathfinderMob.class,
               10,
               true,
               true,
               livingEntity -> !(livingEntity instanceof Slime slimeEntity && slimeEntity.getSize() != 1)
                     && !(livingEntity instanceof Zombie zombie && !zombie.isBaby())
                  ? ((LivingEntity)livingEntity).getType().is(FriendsAndFoesTags.MAULER_PREY)
                  : false
            )
         );
   }

   public void tick() {
      if (!FriendsAndFoes.getConfig().enableMauler) {
         this.discard();
      }

      this.setSize();
      super.tick();
      if (!this.level().isClientSide()) {
         if (this.getTicksUntilNextBurrowingDown() > 0) {
            this.setTicksUntilNextBurrowingDown(this.getTicksUntilNextBurrowingDown() - 1);
         }

         this.updateBurrowingDownAnimation();
      }
   }

   public void aiStep() {
      if (!this.level().isClientSide() && this.getBurrowingDownAnimationProgress() > 0.0F) {
         this.getNavigation().setSpeedModifier(0.0);
         this.getNavigation().stop();
      }

      super.aiStep();
      if (!this.level().isClientSide()) {
         this.updatePersistentAnger((ServerLevel)this.level(), true);
         this.setMoving(this.getNavigation().isInProgress());
      }
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      if (!this.level().isClientSide() && this.burrowDownGoal.isRunning()) {
         this.burrowDownGoal.stop();
      }

      return super.hurt(damageSource, amount);
   }

   public float getSpeed() {
      return this.isAngry() ? 0.5F : 0.3F;
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      Item itemInHand = itemStack.getItem();
      boolean interactionResult = false;
      if (this.isAngry() || itemStack.isEmpty() || !itemStack.isEnchanted() && itemInHand != Items.ENCHANTED_BOOK) {
         if (!this.isAngry() && itemInHand == Items.GLASS_BOTTLE) {
            interactionResult = this.tryToInteractWithGlassBottle(player, itemStack);
         }
      } else {
         interactionResult = this.tryToInteractWithEnhancedItem(player, hand, itemStack);
      }

      if (interactionResult) {
         this.gameEvent(GameEvent.ENTITY_INTERACT, this);
         return VersionedInteractionResult.success(this);
      } else {
         return super.mobInteract(player, hand);
      }
   }

   private boolean tryToInteractWithEnhancedItem(Player player, InteractionHand hand, ItemStack itemStack) {
      int storedExperiencePoints = this.getStoredExperiencePoints();
      if (storedExperiencePoints >= 1395) {
         return false;
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         this.setPersistenceRequired();
         int experiencePoints = this.getExperiencePoints(itemStack);
         int recalculatedExperiencePoints = storedExperiencePoints + experiencePoints;
         if (recalculatedExperiencePoints > 1395) {
            recalculatedExperiencePoints = 1395;
         }

         this.setStoredExperiencePoints(recalculatedExperiencePoints);
         if (!player.getAbilities().instabuild) {
            if (itemStack.isStackable()) {
               itemStack.shrink(1);
            } else {
               EquipmentSlot equipmentSlot;
               if (hand == InteractionHand.MAIN_HAND) {
                  equipmentSlot = EquipmentSlot.MAINHAND;
               } else {
                  equipmentSlot = EquipmentSlot.OFFHAND;
               }

               player.setItemSlot(equipmentSlot, ItemStack.EMPTY);
            }
         }

         this.playSound((SoundEvent)FriendsAndFoesSoundEvents.ENTITY_MAULER_BITE.get(), 0.2F, RandomGenerator.generateFloat(0.9F, 0.95F));
         this.spawnParticles(ParticleTypes.ENCHANT, 7);
      }

      return true;
   }

   private boolean tryToInteractWithGlassBottle(Player player, ItemStack itemStack) {
      int storedExperiencePoints = this.getStoredExperiencePoints();
      if (storedExperiencePoints < 7) {
         return false;
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         this.setPersistenceRequired();
         int glassBottlesCount = itemStack.getCount();
         int experienceBottleCount = storedExperiencePoints / 7;
         if (experienceBottleCount > glassBottlesCount) {
            experienceBottleCount = glassBottlesCount;
         }

         itemStack.shrink(experienceBottleCount);
         ItemStack experienceBottleItemStack = new ItemStack(Items.EXPERIENCE_BOTTLE, experienceBottleCount);
         player.addItem(experienceBottleItemStack);
         this.setStoredExperiencePoints(storedExperiencePoints - experienceBottleCount * 7);
         this.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1.0F, 1.0F);
      }

      return true;
   }

   public boolean canSpawnSprintParticle() {
      return false;
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createMaulerAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.SCALE, 1.0)
         .add(Attributes.MAX_HEALTH, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.3F)
         .add(Attributes.ATTACK_DAMAGE, 8.0);
   }

   public boolean isPushable() {
      return !this.isBurrowedDown() && super.isPushable();
   }

   public void doPush(Entity entity) {
      if (!this.isBurrowedDown()) {
         super.doPush(entity);
      }
   }

   public void setEntityPose(FriendsAndFoesEntityPose pose) {
      if (!this.level().isClientSide()) {
         this.entityData.set(ENTITY_POSE, pose);
      }
   }

   public FriendsAndFoesEntityPose getEntityPose() {
      return (FriendsAndFoesEntityPose)this.entityData.get(ENTITY_POSE);
   }

   public boolean isInEntityPose(FriendsAndFoesEntityPose pose) {
      return this.getEntityPose() == pose;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_MAULER_GROWL.get();
   }

   public void playAmbientSound() {
      if ((!this.isAngry() || !this.isMoving()) && !this.isBurrowedDown()) {
         this.playSound(this.getAmbientSound(), 0.5F, RandomGenerator.generateFloat(0.75F, 0.85F));
      }
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_MAULER_HURT.get();
   }

   public void playHurtSound(DamageSource source) {
      this.ambientSoundTime = -this.getAmbientSoundInterval();
      this.playSound(this.getHurtSound(source), 0.5F, RandomGenerator.generateFloat(0.85F, 0.95F));
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_MAULER_DEATH.get();
   }

   public void playStepSound(BlockPos pos, BlockState state) {
      super.playStepSound(pos, state);
      if (this.isAngry() && this.isMoving() && this.onGround() && this.getDeltaMovement().y() <= 1.0E-4) {
         this.playSound((SoundEvent)FriendsAndFoesSoundEvents.ENTITY_MAULER_BITE.get(), 0.2F, RandomGenerator.generateFloat(0.9F, 0.95F));
      }
   }

   public boolean doHurtTarget(Entity target) {
      return this.isBurrowedDown()
         ? false
         : VersionedEntity.hurt(target, this.damageSources().mobAttack(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
   }

   public int getRemainingPersistentAngerTime() {
      return (Integer)this.entityData.get(ANGER_TIME);
   }

   public void setRemainingPersistentAngerTime(int angerTime) {
      this.entityData.set(ANGER_TIME, angerTime);
   }

   public void startPersistentAngerTimer() {
      int angerTime = this.getRandom().nextIntBetweenInclusive(400, 1000);
      this.setRemainingPersistentAngerTime(angerTime);
   }

   @Nullable
   public UUID getPersistentAngerTarget() {
      return this.angryAt;
   }

   public void setPersistentAngerTarget(@Nullable UUID angryAt) {
      this.angryAt = angryAt;
   }

   public MaulerEntity.Type getMaulerType() {
      return MaulerEntity.Type.fromName((String)this.entityData.get(TYPE));
   }

   private void setType(MaulerEntity.Type type) {
      this.entityData.set(TYPE, type.name);
   }

   public int getStoredExperiencePoints() {
      return (Integer)this.entityData.get(STORED_EXPERIENCE_POINTS);
   }

   public void setStoredExperiencePoints(int storedExperiencePoints) {
      this.entityData.set(STORED_EXPERIENCE_POINTS, storedExperiencePoints);
      this.xpReward = storedExperiencePoints;
   }

   public void setSize() {
      float size = this.getSize();
      this.getAttribute(Attributes.SCALE).setBaseValue(this.getSize());
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((int)(20.0F * size));
      this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0F * (size / 2.0F));
      this.refreshDimensions();
      this.makeBoundingBox();
   }

   public float getSize() {
      return 1.0F + this.getStoredExperiencePoints() / 1395.0F * 1.25F;
   }

   public float getAgeScale() {
      return this.getSize();
   }

   public boolean isMoving() {
      return (Boolean)this.entityData.get(IS_MOVING);
   }

   public void setMoving(boolean isMoving) {
      this.entityData.set(IS_MOVING, isMoving);
   }

   public boolean isBurrowedDown() {
      return (Boolean)this.entityData.get(IS_BURROWED_DOWN);
   }

   public void setBurrowedDown(boolean isBurrowedDown) {
      this.entityData.set(IS_BURROWED_DOWN, isBurrowedDown);
   }

   public int getTicksUntilNextBurrowingDown() {
      return (Integer)this.entityData.get(TICKS_UNTIL_NEXT_BURROWING_DOWN);
   }

   public void setTicksUntilNextBurrowingDown(int ticksUntilNextBurrowingDown) {
      this.entityData.set(TICKS_UNTIL_NEXT_BURROWING_DOWN, ticksUntilNextBurrowingDown);
   }

   public float getBurrowingDownAnimationProgress() {
      return (Float)this.entityData.get(BURROWING_DOWN_ANIMATION_PROGRESS);
   }

   public void setBurrowingDownAnimationProgress(float burrowingDownAnimationProgress) {
      this.entityData.set(BURROWING_DOWN_ANIMATION_PROGRESS, burrowingDownAnimationProgress);
   }

   private void updateBurrowingDownAnimation() {
      if (this.isBurrowedDown()) {
         this.setBurrowingDownAnimationProgress(Math.min(1.0F, this.getBurrowingDownAnimationProgress() + 0.04F));
      } else {
         this.setBurrowingDownAnimationProgress(Math.max(0.0F, this.getBurrowingDownAnimationProgress() - 0.04F));
      }
   }

   public Vec3 getLeashOffset() {
      return new Vec3(0.0, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
   }

   private int getExperiencePoints(ItemStack stack) {
      int i = 0;
      ItemEnchantments itemEnchantmentsComponent = EnchantmentHelper.getEnchantmentsForCrafting(stack);

      for (Entry<Holder<Enchantment>> entry : itemEnchantmentsComponent.entrySet()) {
         Holder<Enchantment> registryEntry = (Holder<Enchantment>)entry.getKey();
         int j = entry.getIntValue();
         if (!registryEntry.is(EnchantmentTags.CURSE)) {
            i += ((Enchantment)registryEntry.value()).getMinCost(j);
         }
      }

      return i;
   }

   public void spawnParticles(ParticleOptions particleEffect, int amount) {
      Level world = this.level();
      if (!world.isClientSide()) {
         for (int i = 0; i < amount; i++) {
            ((ServerLevel)world)
               .sendParticles(
                  particleEffect,
                  this.getRandomX(1.0),
                  this.getRandomY() + 0.5,
                  this.getRandomZ(1.0),
                  1,
                  this.getRandom().nextGaussian() * 0.02,
                  this.getRandom().nextGaussian() * 0.02,
                  this.getRandom().nextGaussian() * 0.02,
                  1.0
               );
         }
      }
   }

   public enum Type {
      BADLANDS("badlands"),
      DESERT("desert"),
      SAVANNA("savanna");

      private final String name;

      Type(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      private static MaulerEntity.Type fromName(String name) {
         MaulerEntity.Type[] types = values();

         for (MaulerEntity.Type type : types) {
            if (type.name.equals(name)) {
               return type;
            }
         }

         return SAVANNA;
      }

      public static MaulerEntity.Type getTypeByBiome(ResourceKey<Biome> biome) {
         if (biome == Biomes.DESERT) {
            return DESERT;
         } else {
            return biome != Biomes.BADLANDS && biome != Biomes.ERODED_BADLANDS && biome != Biomes.WOODED_BADLANDS ? SAVANNA : BADLANDS;
         }
      }
   }
}
