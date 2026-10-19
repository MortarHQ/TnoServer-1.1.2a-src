package com.github.L_Ender.cataclysm.entity.Pet;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AI.EntityAINearestTarget3D;
import com.github.L_Ender.cataclysm.entity.AI.MobAIFindWater;
import com.github.L_Ender.cataclysm.entity.AI.MobAILeaveWater;
import com.github.L_Ender.cataclysm.entity.AI.SemiAquaticAIRandomSwimming;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Portal_Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.entity.Pet.AI.PetSimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.Pet.AI.TameableAIFollowOwnerWater;
import com.github.L_Ender.cataclysm.entity.etc.ISemiAquatic;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.GroundPathNavigatorWide;
import com.github.L_Ender.cataclysm.entity.etc.path.SemiAquaticPathNavigator;
import com.github.L_Ender.cataclysm.entity.projectile.Mini_Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class The_Baby_Leviathan_Entity extends LLibraryAnimationPet implements ISemiAquatic, Bucketable {
   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(The_Baby_Leviathan_Entity.class, EntityDataSerializers.BOOLEAN);
   public static final Animation BABY_LEVIATHAN_BITE = Animation.create(14);
   public static final Animation BABY_LEVIATHAN_ABYSS_BLAST = Animation.create(157);
   public float sitProgress;
   public float prevSitProgress;
   public float SwimProgress;
   public float prevSwimProgress;
   private int fishFeedings;
   private boolean isLandNavigator;
   private The_Baby_Leviathan_Entity.AttackMode mode = The_Baby_Leviathan_Entity.AttackMode.CIRCLE;
   private int blast_cooldown = 0;
   public static final int BLAST_HUNTING_COOLDOWN = 100;
   public double endPosX;
   public double endPosY;
   public double endPosZ;
   public double collidePosX;
   public double collidePosY;
   public double collidePosZ;

   public The_Baby_Leviathan_Entity(EntityType type, Level world) {
      super(type, world);
      this.xpReward = 0;
      this.setPathfindingMalus(PathType.WATER, 0.0F);
      this.setPathfindingMalus(PathType.WATER_BORDER, 0.0F);
      this.switchNavigator(false);
      this.setConfigattribute(this, CMCommonConfig.BabyLeviathan.healthMultiplier, CMCommonConfig.BabyLeviathan.attackMultiplier);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSounds.LEVIATHAN_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.LEVIATHAN_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.LEVIATHAN_DEFEAT.get();
   }

   public float getVoicePitch() {
      return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 2.0F;
   }

   public boolean hurt(DamageSource source, float amount) {
      Entity entity = source.getDirectEntity();
      return !(entity instanceof Mini_Abyss_Blast_Entity) && !(entity instanceof Abyss_Blast_Entity) && !(entity instanceof Portal_Abyss_Blast_Entity)
         ? super.hurt(source, amount)
         : false;
   }

   public static Builder babyleviathan() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 120.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
         .add(Attributes.ARMOR, 5.0)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.ATTACK_DAMAGE, 4.0)
         .add(Attributes.MOVEMENT_SPEED, 0.2F);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
      this.goalSelector.addGoal(2, new TameableAIFollowOwnerWater(this, 1.3, 4.0F, 2.0F, true));
      this.goalSelector.addGoal(3, new The_Baby_Leviathan_Entity.BabyLeviathanAttackGoal(this));
      this.goalSelector.addGoal(0, new The_Baby_Leviathan_Entity.BabyLeviathanBiteAttackGoal(this, BABY_LEVIATHAN_BITE));
      this.goalSelector.addGoal(0, new The_Baby_Leviathan_Entity.BabyLeviathanBlastAttackGoal(this, BABY_LEVIATHAN_ABYSS_BLAST));
      this.goalSelector.addGoal(4, new MobAIFindWater(this, 1.0));
      this.goalSelector.addGoal(4, new MobAILeaveWater(this));
      this.goalSelector.addGoal(6, new TemptGoal(this, 1.0, Ingredient.of(new ItemLike[]{Items.TROPICAL_FISH}), false));
      this.goalSelector.addGoal(7, new SemiAquaticAIRandomSwimming(this, 1.0, 30));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
      this.targetSelector
         .addGoal(
            3,
            new EntityAINearestTarget3D(this, LivingEntity.class, 120, false, true, ModEntities.buildPredicateFromTag(ModTag.BABY_LEVIATHAN_TARGET)) {
               public boolean canUse() {
                  return The_Baby_Leviathan_Entity.this.getCommand() != 2
                     && !The_Baby_Leviathan_Entity.this.isSitting()
                     && super.canUse()
                     && !The_Baby_Leviathan_Entity.this.isTame();
               }
            }
         );
      this.targetSelector.addGoal(4, new HurtByTargetGoal(this, new Class[0]));
   }

   private void switchNavigator(boolean onLand) {
      if (onLand) {
         this.moveControl = new MoveControl(this);
         this.navigation = new GroundPathNavigatorWide(this, this.level());
         this.isLandNavigator = true;
      } else {
         this.moveControl = new The_Baby_Leviathan_Entity.BabyLeviathanMoveController(this, 3.0F, 1.0F, 10.0F);
         this.navigation = new SemiAquaticPathNavigator(this, this.level());
         this.isLandNavigator = false;
      }
   }

   public void travel(Vec3 travelVector) {
      if (this.isSitting()) {
         if (this.getNavigation().getPath() != null) {
            this.getNavigation().stop();
         }

         travelVector = Vec3.ZERO;
         super.travel(travelVector);
      } else {
         if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null && this.getAnimation() == NO_ANIMATION) {
               this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
         } else {
            super.travel(travelVector);
         }
      }
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
      return p_27600_.is(ItemTags.FISHES);
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
      return new ItemStack((ItemLike)ModItems.THE_BABY_LEVIATHAN_BUCKET.get());
   }

   public SoundEvent getPickupSound() {
      return SoundEvents.BUCKET_FILL_FISH;
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      Item item = itemstack.getItem();
      InteractionResult type = super.mobInteract(player, hand);
      if (!this.isTame() && item == Items.TROPICAL_FISH) {
         this.usePlayerItem(player, hand, itemstack);
         this.gameEvent(GameEvent.EAT);
         this.fishFeedings++;
         if ((this.fishFeedings > 10 && this.getRandom().nextInt(6) == 0 || this.fishFeedings > 30) && !EventHooks.onAnimalTame(this, player)) {
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
            Optional<InteractionResult> result = Bucketable.bucketMobPickup(player, hand, this);
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

   public void aiStep() {
      super.aiStep();
      if (this.isSitting() && this.getNavigation().isDone()) {
         this.getNavigation().stop();
      }

      boolean swim = this.isInWater();
      this.prevSwimProgress = this.SwimProgress;
      this.prevSitProgress = this.sitProgress;
      if (this.SwimProgress < 5.0F && swim) {
         this.SwimProgress++;
      }

      if (this.SwimProgress > 0.0F && !swim) {
         this.SwimProgress--;
      }

      if (this.isSitting() && this.sitProgress < 5.0F) {
         this.sitProgress++;
      }

      if (!this.isSitting() && this.sitProgress > 0.0F) {
         this.sitProgress--;
      }

      if (this.isInWater() && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (!this.isInWater() && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      if (this.blast_cooldown > 0) {
         this.blast_cooldown--;
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
      if (this.getAnimation() == BABY_LEVIATHAN_BITE) {
         if (this.getAnimationTick() == 7) {
            this.playSound((SoundEvent)ModSounds.LEVIATHAN_BITE.get(), 0.5F, 2.0F);
         }

         if (this.getAnimationTick() == 9) {
            this.biteattack(1.5F, 0.5, 0.5, 0.5);
         }
      }
   }

   private void biteattack(float radius, double inflateX, double inflateY, double inflateZ) {
      double renderYaw = (this.yHeadRot + 90.0F) * Math.PI / 180.0;
      double renderPitch = (float)(-this.getXRot() * Math.PI / 180.0);
      this.endPosX = this.getX() + radius * Math.cos(renderYaw) * Math.cos(renderPitch);
      this.endPosZ = this.getZ() + radius * Math.sin(renderYaw) * Math.cos(renderPitch);
      this.endPosY = this.getY() + radius * Math.sin(renderPitch);
      if (!this.level().isClientSide) {
         for (LivingEntity target : this.raytraceEntities(
               this.level(), inflateX, inflateY, inflateZ, new Vec3(this.getX(), this.getY(), this.getZ()), new Vec3(this.endPosX, this.endPosY, this.endPosZ)
            )
            .entities) {
            if (!this.isAlliedTo(target) && !(target instanceof The_Baby_Leviathan_Entity) && target != this) {
               target.hurt(this.damageSources().mobAttack(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
            }
         }
      }
   }

   private The_Baby_Leviathan_Entity.BiteHitResult raytraceEntities(Level world, double inflateX, double inflateY, double inflateZ, Vec3 from, Vec3 to) {
      The_Baby_Leviathan_Entity.BiteHitResult result = new The_Baby_Leviathan_Entity.BiteHitResult();
      this.collidePosX = this.endPosX;
      this.collidePosY = this.endPosY;
      this.collidePosZ = this.endPosZ;

      for (LivingEntity entity : world.getEntitiesOfClass(
         LivingEntity.class,
         new AABB(
               Math.min(this.getX(), this.collidePosX),
               Math.min(this.getY(), this.collidePosY),
               Math.min(this.getZ(), this.collidePosZ),
               Math.max(this.getX(), this.collidePosX),
               Math.max(this.getY(), this.collidePosY),
               Math.max(this.getZ(), this.collidePosZ)
            )
            .inflate(inflateX, inflateY, inflateZ)
      )) {
         float pad = 2.5F;
         AABB aabb = entity.getBoundingBox().inflate(pad, pad, pad);
         Optional<Vec3> hit = aabb.clip(from, to);
         if (aabb.contains(from)) {
            result.addEntityHit(entity);
         } else if (hit.isPresent()) {
            result.addEntityHit(entity);
         }
      }

      return result;
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

   public boolean isPushedByFluid() {
      return false;
   }

   @Nullable
   @Override
   public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageableEntity) {
      return null;
   }

   @Override
   public boolean shouldEnterWater() {
      return !this.isSitting();
   }

   @Override
   public boolean shouldLeaveWater() {
      return false;
   }

   @Override
   public boolean shouldStopMoving() {
      return this.isSitting();
   }

   @Override
   public int getWaterSearchRange() {
      return 32;
   }

   @Override
   public boolean shouldFollow() {
      return this.getCommand() == 1;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, BABY_LEVIATHAN_ABYSS_BLAST, BABY_LEVIATHAN_BITE};
   }

   private enum AttackMode {
      CIRCLE,
      MELEE,
      RANGE;
   }

   class BabyLeviathanAttackGoal extends Goal {
      private final The_Baby_Leviathan_Entity mob;
      private LivingEntity target;
      private int circlingTime = 0;
      private float circleDistance = 4.0F;
      private boolean clockwise = false;
      private float MeleeModeTime = 0.0F;
      private static final int MELEE_MODE_TIME = 100;

      public BabyLeviathanAttackGoal(The_Baby_Leviathan_Entity mob) {
         this.mob = mob;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         this.target = this.mob.getTarget();
         return this.target != null && this.target.isAlive() && this.mob.getAnimation() == IAnimatedEntity.NO_ANIMATION;
      }

      public boolean canContinueToUse() {
         this.target = this.mob.getTarget();
         return this.target != null;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void start() {
         this.mob.mode = The_Baby_Leviathan_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.MeleeModeTime = 0.0F;
         this.circleDistance = 8 + this.mob.random.nextInt(2);
         this.clockwise = this.mob.random.nextBoolean();
         this.mob.setAggressive(true);
      }

      public void stop() {
         this.mob.mode = The_Baby_Leviathan_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.MeleeModeTime = 0.0F;
         this.circleDistance = 8 + this.mob.random.nextInt(2);
         this.clockwise = this.mob.random.nextBoolean();
         this.target = this.mob.getTarget();
         if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(this.target)) {
            this.mob.setTarget((LivingEntity)null);
         }

         this.mob.getNavigation().stop();
         if (this.mob.getTarget() == null) {
            this.mob.setAggressive(false);
            this.mob.getNavigation().stop();
         }
      }

      public void tick() {
         LivingEntity target = this.mob.getTarget();
         if (target != null) {
            if (this.mob.mode == The_Baby_Leviathan_Entity.AttackMode.CIRCLE) {
               this.circlingTime++;
               The_Baby_Leviathan_Entity.this.circleEntity(target, this.circleDistance, 1.0F, this.clockwise, this.circlingTime, 0.0F, 1.0F);
               if (0 >= this.mob.blast_cooldown) {
                  this.mob.mode = The_Baby_Leviathan_Entity.AttackMode.RANGE;
               } else {
                  this.mob.mode = The_Baby_Leviathan_Entity.AttackMode.MELEE;
               }
            } else if (this.mob.mode == The_Baby_Leviathan_Entity.AttackMode.RANGE) {
               if (this.mob.getRandom().nextFloat() * 100.0F < 3.0F) {
                  this.mob.setAnimation(The_Baby_Leviathan_Entity.BABY_LEVIATHAN_ABYSS_BLAST);
               }
            } else if (this.mob.mode == The_Baby_Leviathan_Entity.AttackMode.MELEE) {
               this.MeleeModeTime++;
               this.mob.getNavigation().moveTo(target, 1.0);
               this.mob.getLookControl().setLookAt(target, 30.0F, 90.0F);
               if (this.MeleeModeTime >= 100.0F) {
                  this.mob.mode = The_Baby_Leviathan_Entity.AttackMode.RANGE;
               } else if (this.mob.distanceToSqr(target) <= 4.0) {
                  this.mob.setAnimation(The_Baby_Leviathan_Entity.BABY_LEVIATHAN_BITE);
               }
            }
         }
      }
   }

   static class BabyLeviathanBiteAttackGoal extends PetSimpleAnimationGoal<The_Baby_Leviathan_Entity> {
      public BabyLeviathanBiteAttackGoal(The_Baby_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.start();
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() < 9 && target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }
      }

      public void stop() {
         super.stop();
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }
      }
   }

   static class BabyLeviathanBlastAttackGoal extends PetSimpleAnimationGoal<The_Baby_Leviathan_Entity> {
      public BabyLeviathanBlastAttackGoal(The_Baby_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         this.entity.level().playSound((Player)null, this.entity, (SoundEvent)ModSounds.ABYSS_BLAST.get(), SoundSource.NEUTRAL, 1.0F, 2.0F);
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
            this.entity.lookAt(target, 30.0F, 90.0F);
         }

         super.start();
      }

      public void stop() {
         super.stop();
         this.entity.blast_cooldown = 100;
      }

      public void tick() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
            this.entity.lookAt(target, 30.0F, 90.0F);
         }

         float dir = 90.0F;
         if (this.entity.getAnimationTick() == 37 && !this.entity.level().isClientSide) {
            Mini_Abyss_Blast_Entity DeathBeam = new Mini_Abyss_Blast_Entity(
               (EntityType<? extends Mini_Abyss_Blast_Entity>)ModEntities.MINI_ABYSS_BLAST.get(),
               this.entity.level(),
               this.entity,
               this.entity.getX(),
               this.entity.getY(),
               this.entity.getZ(),
               (float)((this.entity.yHeadRot + dir) * Math.PI / 180.0),
               (float)(-this.entity.getXRot() * Math.PI / 180.0),
               80,
               dir
            );
            this.entity.level().addFreshEntity(DeathBeam);
         }
      }
   }

   static class BabyLeviathanMoveController extends MoveControl {
      private final The_Baby_Leviathan_Entity entity;
      private final float speedMulti;
      private final float ySpeedMod;
      private final float yawLimit;
      private int stillTicks = 0;

      public BabyLeviathanMoveController(The_Baby_Leviathan_Entity entity, float speedMulti, float ySpeedMod, float yawLimit) {
         super(entity);
         this.entity = entity;
         this.speedMulti = speedMulti;
         this.ySpeedMod = ySpeedMod;
         this.yawLimit = yawLimit;
      }

      public void tick() {
         if (this.entity.isInWater() && this.entity.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            if (Math.abs(this.entity.xo - this.entity.getX()) < 0.01F
               && Math.abs(this.entity.yo - this.entity.getY()) < 0.01F
               && Math.abs(this.entity.zo - this.entity.getZ()) < 0.01F) {
               this.stillTicks++;
            } else {
               this.stillTicks = 0;
            }

            if (this.stillTicks > 40) {
               this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
         }

         if (this.entity.shouldStopMoving()) {
            this.entity.setSpeed(0.0F);
         } else {
            if (this.operation == Operation.MOVE_TO && !this.entity.getNavigation().isDone()) {
               double lvt_1_1_ = this.wantedX - this.entity.getX();
               double lvt_3_1_ = this.wantedY - this.entity.getY();
               double lvt_5_1_ = this.wantedZ - this.entity.getZ();
               double lvt_7_1_ = lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_;
               if (lvt_7_1_ < 2.5000003E-7F) {
                  this.mob.setZza(0.0F);
               } else {
                  float lvt_9_1_ = (float)(Mth.atan2(lvt_5_1_, lvt_1_1_) * 180.0F / (float)Math.PI) - 90.0F;
                  this.entity.setYRot(this.rotlerp(this.entity.getYRot(), lvt_9_1_, this.yawLimit));
                  this.entity.yBodyRot = this.entity.getYRot();
                  this.entity.yHeadRot = this.entity.getYRot();
                  float lvt_10_1_ = (float)(this.speedModifier * this.speedMulti * 3.0 * this.entity.getAttributeValue(Attributes.MOVEMENT_SPEED));
                  if (this.entity.isInWater()) {
                     if (lvt_3_1_ > 0.0 && this.entity.horizontalCollision) {
                        this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0, 0.08F, 0.0));
                     } else {
                        this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0, this.entity.getSpeed() * lvt_3_1_ * 0.6 * this.ySpeedMod, 0.0));
                     }

                     this.entity.setSpeed(lvt_10_1_ * 0.02F);
                     float lvt_11_1_ = -((float)(Mth.atan2(lvt_3_1_, Mth.sqrt((float)(lvt_1_1_ * lvt_1_1_ + lvt_5_1_ * lvt_5_1_))) * 180.0F / (float)Math.PI));
                     lvt_11_1_ = Mth.clamp(Mth.wrapDegrees(lvt_11_1_), -85.0F, 85.0F);
                     this.entity.setXRot(this.rotlerp(this.entity.getXRot(), lvt_11_1_, 5.0F));
                     float lvt_12_1_ = Mth.cos(this.entity.getXRot() * (float) (Math.PI / 180.0));
                     float lvt_13_1_ = Mth.sin(this.entity.getXRot() * (float) (Math.PI / 180.0));
                     this.entity.zza = lvt_12_1_ * lvt_10_1_;
                     this.entity.yya = -lvt_13_1_ * lvt_10_1_;
                  } else {
                     this.entity.setSpeed(lvt_10_1_ * 0.1F);
                  }
               }
            } else {
               this.entity.setSpeed(0.0F);
               this.entity.setXxa(0.0F);
               this.entity.setYya(0.0F);
               this.entity.setZza(0.0F);
            }
         }
      }
   }

   public static class BiteHitResult {
      private final List<LivingEntity> entities = new ArrayList<>();

      public void addEntityHit(LivingEntity entity) {
         this.entities.add(entity);
      }
   }
}
