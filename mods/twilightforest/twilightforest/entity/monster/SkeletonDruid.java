package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import twilightforest.TwilightForestMod;
import twilightforest.entity.projectile.NatureBolt;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

public class SkeletonDruid extends AbstractSkeleton {
   private RangedAttackGoal rangedAttackGoal;
   private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(
      TwilightForestMod.prefix("baby_speed_boost"), 0.5, Operation.ADD_MULTIPLIED_BASE
   );
   private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(SkeletonDruid.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDimensions BABY_DIMENSIONS = ((EntityType)TFEntities.SKELETON_DRUID.get()).getDimensions().scale(0.5F).withEyeHeight(0.93F);

   public SkeletonDruid(EntityType<? extends SkeletonDruid> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.rangedAttackGoal = new RangedAttackGoal(this, 1.25, 60, 5.0F);
      this.goalSelector.addGoal(4, this.rangedAttackGoal);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_BABY_ID, false);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.SKELETON_DRUID_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.SKELETON_DRUID_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.SKELETON_DRUID_DEATH.get();
   }

   protected SoundEvent getStepSound() {
      return (SoundEvent)TFSounds.SKELETON_DRUID_STEP.get();
   }

   public void reassessWeaponGoal() {
      if (!this.level().isClientSide()) {
         this.goalSelector.removeGoal(this.rangedAttackGoal);
         if (this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HoeItem) {
            this.goalSelector.addGoal(4, this.rangedAttackGoal);
         } else {
            super.reassessWeaponGoal();
         }
      } else {
         super.reassessWeaponGoal();
      }
   }

   protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
      if (this.isBaby()) {
         this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STICK));
      } else {
         this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_HOE));
      }
   }

   public void performRangedAttack(LivingEntity attackTarget, float extraDamage) {
      if (this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HoeItem) {
         NatureBolt natureBolt = new NatureBolt(this.level(), this);
         this.playSound((SoundEvent)TFSounds.SKELETON_DRUID_SHOOT.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
         double tx = attackTarget.getX() - this.getX();
         double ty = attackTarget.getY() + attackTarget.getEyeHeight() - 2.7 - this.getY();
         double tz = attackTarget.getZ() - this.getZ();
         float heightOffset = Mth.sqrt((float)(tx * tx + tz * tz)) * 0.2F;
         natureBolt.shoot(tx, ty + heightOffset, tz, 0.6F, 6.0F);
         this.level().addFreshEntity(natureBolt);
      } else if (!this.getItemInHand(InteractionHand.MAIN_HAND).is(Items.STICK)) {
         super.performRangedAttack(attackTarget, extraDamage);
      }
   }

   public static boolean checkDruidSpawnRules(
      EntityType<? extends SkeletonDruid> entity, LevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random
   ) {
      return world.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(world, pos, random) && checkMobSpawnRules(entity, world, reason, pos, random);
   }

   public static boolean isValidLightLevel(LevelAccessor accessor, BlockPos pos, RandomSource random) {
      return accessor.getBrightness(LightLayer.SKY, pos) > random.nextInt(32) ? false : accessor.getMaxLocalRawBrightness(pos) <= random.nextInt(12);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("IsBaby", this.isBaby());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setBaby(compound.getBoolean("IsBaby"));
   }

   public boolean isBaby() {
      return (Boolean)this.getEntityData().get(DATA_BABY_ID);
   }

   public int getBaseExperienceReward() {
      if (this.isBaby()) {
         this.xpReward = (int)(this.xpReward * 2.5F);
      }

      return super.getBaseExperienceReward();
   }

   public void setBaby(boolean shouldBaby) {
      this.getEntityData().set(DATA_BABY_ID, shouldBaby);
      if (!this.level().isClientSide()) {
         AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
         attributeinstance.removeModifier(SPEED_MODIFIER_BABY.id());
         if (shouldBaby) {
            attributeinstance.addTransientModifier(SPEED_MODIFIER_BABY);
         }
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
      if (DATA_BABY_ID.equals(dataAccessor)) {
         this.refreshDimensions();
      }

      super.onSyncedDataUpdated(dataAccessor);
   }

   protected EntityDimensions getDefaultDimensions(Pose pose) {
      return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(pose);
   }
}
