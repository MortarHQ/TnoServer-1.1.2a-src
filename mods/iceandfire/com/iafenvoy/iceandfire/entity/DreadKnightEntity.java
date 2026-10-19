package com.iafenvoy.iceandfire.entity;

import com.google.common.base.Predicate;
import com.iafenvoy.iceandfire.entity.ai.DreadAIRideHorseGoal;
import com.iafenvoy.iceandfire.entity.ai.DreadAITargetNonDreadGoal;
import com.iafenvoy.iceandfire.entity.util.IAnimalFear;
import com.iafenvoy.iceandfire.entity.util.IDreadMob;
import com.iafenvoy.iceandfire.entity.util.IVillagerFear;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.registry.IafBannerPatterns;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.AnimationHandler;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import com.iafenvoy.uranus.object.RegistryHelper;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BannerPatternLayers.Layer;
import net.minecraft.world.level.block.state.BlockState;

public class DreadKnightEntity extends DreadMobEntity implements IAnimatedEntity, IVillagerFear, IAnimalFear {
   public static final Animation ANIMATION_SPAWN = Animation.create(40);
   private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(DreadKnightEntity.class, EntityDataSerializers.INT);
   private int animationTick;
   private Animation currentAnimation;

   public DreadKnightEntity(EntityType<? extends DreadKnightEntity> type, Level worldIn) {
      super(type, worldIn);
   }

   private ItemStack generateShield() {
      ItemStack shield = new ItemStack(Items.SHIELD);
      shield.set(
         DataComponents.BANNER_PATTERNS,
         new BannerPatternLayers(
            List.of(
               new Layer(RegistryHelper.getEntry(this.registryAccess(), Registries.BANNER_PATTERN, BannerPatterns.BASE), DyeColor.CYAN),
               new Layer(RegistryHelper.getEntry(this.registryAccess(), Registries.BANNER_PATTERN, IafBannerPatterns.PATTERN_DREAD), DyeColor.WHITE)
            )
         )
      );
      return shield;
   }

   public static Builder bakeAttributes() {
      return createMobAttributes()
         .add(Attributes.MAX_HEALTH, 40.0)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.ATTACK_DAMAGE, 2.0)
         .add(Attributes.FOLLOW_RANGE, 128.0)
         .add(Attributes.ARMOR, 20.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new DreadAIRideHorseGoal(this));
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{IDreadMob.class}));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, DragonUtils::canHostilesTarget));
      this.targetSelector
         .addGoal(
            3,
            new DreadAITargetNonDreadGoal(
               this, LivingEntity.class, false, (Predicate)entity -> entity instanceof LivingEntity && DragonUtils.canHostilesTarget(entity)
            )
         );
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, 0);
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (this.getAnimation() == ANIMATION_SPAWN && this.getAnimationTick() < 30) {
         BlockState belowBlock = this.level().getBlockState(this.blockPosition().below());
         if (belowBlock.getBlock() != Blocks.AIR) {
            for (int i = 0; i < 5; i++) {
               this.level()
                  .addParticle(
                     new BlockParticleOption(ParticleTypes.BLOCK, belowBlock),
                     this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                     this.getBoundingBox().minY,
                     this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                     this.random.nextGaussian() * 0.02,
                     this.random.nextGaussian() * 0.02,
                     this.random.nextGaussian() * 0.02
                  );
            }
         }

         this.setDeltaMovement(0.0, this.getDeltaMovement().y, this.getDeltaMovement().z);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
      super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)IafItems.DREAD_KNIGHT_SWORD.get()));
      if (this.random.nextBoolean()) {
         this.setItemSlot(EquipmentSlot.OFFHAND, this.generateShield());
      }

      this.setArmorVariant(this.random.nextInt(3));
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      SpawnGroupData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
      this.setAnimation(ANIMATION_SPAWN);
      this.populateDefaultEquipmentSlots(worldIn.getRandom(), difficultyIn);
      return data;
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("ArmorVariant", this.getArmorVariant());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setArmorVariant(compound.getInt("ArmorVariant"));
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public int getArmorVariant() {
      return (Integer)this.entityData.get(VARIANT);
   }

   public void setArmorVariant(int variant) {
      this.entityData.set(VARIANT, variant);
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_SPAWN};
   }

   @Override
   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.STRAY_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.STRAY_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.STRAY_DEATH;
   }

   protected void playStepSound(BlockPos pos, Block blockIn) {
      this.playSound(SoundEvents.STRAY_STEP, 0.15F, 1.0F);
   }

   public float getVoicePitch() {
      return super.getVoicePitch() * 0.75F;
   }
}
