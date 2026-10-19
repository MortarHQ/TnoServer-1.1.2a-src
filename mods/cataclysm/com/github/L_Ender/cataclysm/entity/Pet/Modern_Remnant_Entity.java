package com.github.L_Ender.cataclysm.entity.Pet;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.Pet.AI.TameableAIFollowOwner;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class Modern_Remnant_Entity extends LLibraryAnimationPet implements Bucketable {
   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Modern_Remnant_Entity.class, EntityDataSerializers.BOOLEAN);
   public float sitProgress;
   public float prevSitProgress;
   private Modern_Remnant_Entity.AttackMode mode = Modern_Remnant_Entity.AttackMode.CIRCLE;
   public static final Animation MODERN_REMNANT_BITE = Animation.create(11);

   public Modern_Remnant_Entity(EntityType type, Level world) {
      super(type, world);
      this.xpReward = 0;
      this.setConfigattribute(this, CMCommonConfig.ModernRemnant.healthMultiplier, CMCommonConfig.ModernRemnant.attackMultiplier);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSounds.REMNANT_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.REMNANT_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.MODERN_REMNANT_DEATH.get();
   }

   public float getVoicePitch() {
      return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 2.0F;
   }

   public static Builder modernremnant() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 150.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
         .add(Attributes.ARMOR, 5.0)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.ATTACK_DAMAGE, 6.0)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.4F);
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
      this.goalSelector.addGoal(3, new Modern_Remnant_Entity.ModernRemnantAIMelee(this));
      this.goalSelector.addGoal(6, new TameableAIFollowOwner(this, 1.3, 6.0F, 2.0F, true));
      this.goalSelector.addGoal(6, new TemptGoal(this, 1.0, Ingredient.of(new ItemLike[]{Items.SNIFFER_EGG}), false));
      this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0, 60));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
      this.targetSelector.addGoal(4, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector
         .addGoal(5, new NonTameRandomTargetGoal(this, LivingEntity.class, false, ModEntities.buildPredicateFromTag(ModTag.MODERN_REMNANT_TARGET)));
   }

   public void travel(Vec3 vec3d) {
      if (this.isSitting()) {
         if (this.getNavigation().getPath() != null) {
            this.getNavigation().stop();
         }

         vec3d = Vec3.ZERO;
      }

      super.travel(vec3d);
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALLING_BLOCK) || super.isInvulnerableTo(source);
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(FROM_BUCKET, false);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("FromBucket", this.fromBucket());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setFromBucket(compound.getBoolean("FromBucket"));
   }

   @Override
   public boolean isFood(ItemStack p_27600_) {
      return p_27600_.is(ModTag.BONE_ITEM);
   }

   public boolean fromBucket() {
      return (Boolean)this.entityData.get(FROM_BUCKET);
   }

   public void setFromBucket(boolean sit) {
      this.entityData.set(FROM_BUCKET, sit);
   }

   public void saveToBucketTag(@Nonnull ItemStack bucket) {
      Bucketable.saveDefaultDataToBucketTag(this, bucket);
      CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, this::addAdditionalSaveData);
   }

   public void loadFromBucketTag(CompoundTag p_148832_) {
      this.readAdditionalSaveData(p_148832_);
      Bucketable.loadDefaultDataFromBucketTag(this, p_148832_);
   }

   @Nonnull
   public ItemStack getBucketItemStack() {
      return new ItemStack((ItemLike)ModItems.MODERN_REMNANT_BUCKET.get());
   }

   public SoundEvent getPickupSound() {
      return (SoundEvent)ModSounds.MODERN_REMNANT_FILL_BUCKET.get();
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      Item item = itemstack.getItem();
      InteractionResult type = super.mobInteract(player, hand);
      if (!this.isTame() && item == Items.SNIFFER_EGG) {
         this.usePlayerItem(player, hand, itemstack);
         this.gameEvent(GameEvent.EAT);
         if (!EventHooks.onAnimalTame(this, player)) {
            this.tame(player);
            this.level().broadcastEntityEvent(this, (byte)7);
         } else {
            this.level().broadcastEntityEvent(this, (byte)6);
         }

         return InteractionResult.SUCCESS;
      } else if (this.isTame() && this.isFood(itemstack)) {
         if (this.getHealth() < this.getMaxHealth()) {
            this.usePlayerItem(player, hand, itemstack);
            this.gameEvent(GameEvent.EAT);
            this.heal(5.0F);
            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.PASS;
         }
      } else {
         if (this.isTame()) {
            Optional<InteractionResult> result = emptybucketMobPickup(player, hand, this);
            if (result.isPresent()) {
               return result.get();
            }
         }

         InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
         if (interactionresult != InteractionResult.SUCCESS
            && type != InteractionResult.SUCCESS
            && this.isTame()
            && this.isOwnedBy(player)
            && !player.isShiftKeyDown()) {
            this.setCommand(this.getCommand() + 1);
            if (this.getCommand() == 3) {
               this.setCommand(0);
            }

            player.displayClientMessage(Component.translatable("entity.cataclysm.all.command_" + this.getCommand(), new Object[]{this.getName()}), true);
            boolean sit = this.getCommand() == 2;
            if (sit) {
               this.setOrderedToSit(true);
               return InteractionResult.SUCCESS;
            } else {
               this.setOrderedToSit(false);
               return InteractionResult.SUCCESS;
            }
         } else {
            return type;
         }
      }
   }

   private static <T extends LivingEntity & Bucketable> Optional<InteractionResult> emptybucketMobPickup(
      Player p_148829_, InteractionHand p_148830_, T p_148831_
   ) {
      ItemStack itemstack = p_148829_.getItemInHand(p_148830_);
      if (itemstack.getItem() == Items.BUCKET && p_148831_.isAlive()) {
         p_148831_.playSound(p_148831_.getPickupSound(), 1.0F, 1.0F);
         ItemStack itemstack1 = p_148831_.getBucketItemStack();
         p_148831_.saveToBucketTag(itemstack1);
         ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, p_148829_, itemstack1, false);
         p_148829_.setItemInHand(p_148830_, itemstack2);
         Level level = p_148831_.level();
         if (!level.isClientSide) {
            CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)p_148829_, itemstack1);
         }

         p_148831_.discard();
         return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
      } else {
         return Optional.empty();
      }
   }

   public void aiStep() {
      super.aiStep();
      if (this.isSitting() && this.getNavigation().isDone()) {
         this.getNavigation().stop();
      }

      this.prevSitProgress = this.sitProgress;
      if (this.isSitting() && this.sitProgress < 10.0F) {
         this.sitProgress++;
      }

      if (!this.isSitting() && this.sitProgress > 0.0F) {
         this.sitProgress--;
      }

      if (this.getAnimation() == MODERN_REMNANT_BITE && this.getAnimationTick() == 3) {
         this.playSound((SoundEvent)ModSounds.MODERN_REMNANT_BITE.get(), 0.5F, 2.0F);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (this.isTame()) {
         LivingEntity livingentity = this.getOwner();
         if (entityIn == livingentity) {
            return true;
         }

         if (entityIn instanceof TamableAnimal) {
            return ((TamableAnimal)entityIn).isOwnedBy(livingentity);
         }

         if (livingentity != null) {
            return livingentity.isAlliedTo(entityIn);
         }
      }

      return super.isAlliedTo(entityIn);
   }

   @Nullable
   @Override
   public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageableEntity) {
      return null;
   }

   @Override
   public boolean shouldFollow() {
      return this.getCommand() == 1;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, MODERN_REMNANT_BITE};
   }

   private enum AttackMode {
      CIRCLE,
      MELEE;
   }

   class ModernRemnantAIMelee extends Goal {
      private final Modern_Remnant_Entity mob;
      private LivingEntity target;
      private int circlingTime = 0;
      private int maxcirclingTime = 0;
      private float circleDistance = 13.0F;
      private boolean clockwise = false;
      private float MeleeModeTime = 0.0F;
      private static final int MELEE_MODE_TIME = 120;

      public ModernRemnantAIMelee(Modern_Remnant_Entity mob) {
         this.mob = mob;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         this.target = this.mob.getTarget();
         return this.target != null && this.target.isAlive();
      }

      public boolean canContinueToUse() {
         this.target = this.mob.getTarget();
         return this.target != null;
      }

      public void start() {
         this.mob.mode = Modern_Remnant_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.maxcirclingTime = 120 + this.mob.random.nextInt(40);
         this.circleDistance = 8 + this.mob.random.nextInt(4);
         this.clockwise = this.mob.random.nextBoolean();
         this.MeleeModeTime = 0.0F;
         this.mob.setAggressive(true);
      }

      public void stop() {
         this.mob.mode = Modern_Remnant_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.maxcirclingTime = 120 + this.mob.random.nextInt(40);
         this.circleDistance = 8 + this.mob.random.nextInt(4);
         this.clockwise = this.mob.random.nextBoolean();
         this.target = this.mob.getTarget();
         if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(this.target)) {
            this.mob.setTarget(null);
         }

         this.MeleeModeTime = 0.0F;
         this.mob.getNavigation().stop();
         if (this.mob.getTarget() == null) {
            this.mob.setAggressive(false);
            this.mob.getNavigation().stop();
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity target = this.mob.getTarget();
         if (target != null) {
            if (this.mob.mode == Modern_Remnant_Entity.AttackMode.CIRCLE) {
               this.circlingTime++;
               Modern_Remnant_Entity.this.circleEntity(target, this.circleDistance, 1.0F, this.clockwise, this.circlingTime, 0.0F, 1.0F);
               if (this.circlingTime >= this.maxcirclingTime) {
                  this.mob.mode = Modern_Remnant_Entity.AttackMode.MELEE;
               }

               if (target.distanceTo(this.mob) < 5.0F) {
                  this.mob.mode = Modern_Remnant_Entity.AttackMode.MELEE;
               }
            } else if (this.mob.mode == Modern_Remnant_Entity.AttackMode.MELEE) {
               this.MeleeModeTime++;
               this.mob.getNavigation().moveTo(target, 1.0);
               this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
               if (this.MeleeModeTime >= 120.0F) {
                  this.start();
               } else if (this.mob.distanceToSqr(target) <= 4.0 && this.mob.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
                  this.mob.setAnimation(Modern_Remnant_Entity.MODERN_REMNANT_BITE);
               }
            }

            if (this.mob.getAnimation() == Modern_Remnant_Entity.MODERN_REMNANT_BITE
               && this.mob.getAnimationTick() == 5
               && this.mob.distanceTo(target) < this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 2.5F + target.getBbWidth()) {
               float damage = (float)this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
               target.hurt(Modern_Remnant_Entity.this.damageSources().mobAttack(this.mob), damage);
            }
         }
      }
   }
}
