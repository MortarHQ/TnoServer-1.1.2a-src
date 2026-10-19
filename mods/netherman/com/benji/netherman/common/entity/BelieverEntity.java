package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModSounds;
import java.util.List;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BelieverEntity extends PathfinderMob implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final EntityDataAccessor<Boolean> IS_SICK = SynchedEntityData.defineId(BelieverEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Boolean> IS_PRAYING = SynchedEntityData.defineId(BelieverEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Boolean> IS_PROTECTED = SynchedEntityData.defineId(BelieverEntity.class, EntityDataSerializers.BOOLEAN);
   public boolean clientIsProtected = false;
   public int protectedTimer = 0;
   private AzazelEntity currentAzazel = null;
   private static final RawAnimation HURT_ANIM = RawAnimation.begin().thenPlay("hurt");
   private static final RawAnimation SICK_ANIM = RawAnimation.begin().thenLoop("sick");
   private static final RawAnimation PRAY_ANIM = RawAnimation.begin().thenPlay("pray_sit").thenLoop("pray_loop");
   private static final RawAnimation RUN_ANIM = RawAnimation.begin().thenLoop("run");
   private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

   public BelieverEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(IS_SICK, false);
      builder.define(IS_PRAYING, false);
      builder.define(IS_PROTECTED, false);
   }

   public boolean isSick() {
      return (Boolean)this.entityData.get(IS_SICK);
   }

   public void setSick(boolean sick) {
      this.entityData.set(IS_SICK, sick);
   }

   public void setProtected(int ticks) {
      this.protectedTimer = ticks;
      this.entityData.set(IS_PROTECTED, true);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));
      this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
   }

   public boolean hurt(DamageSource source, float amount) {
      return this.entityData.get(IS_PROTECTED) ? false : super.hurt(source, amount);
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (stack.is(Items.GUNPOWDER) && !this.isSick()) {
         if (!player.isCreative()) {
            stack.shrink(1);
         }

         this.setSick(true);
         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else if (stack.is(Items.GOLDEN_APPLE) && this.isSick()) {
         if (!player.isCreative()) {
            stack.shrink(1);
         }

         this.setSick(false);
         this.playSound(SoundEvents.PILLAGER_CELEBRATE, 1.0F, 1.0F);
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 1.0, this.getZ(), 15, 0.3, 0.3, 0.3, 0.0);
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         return super.mobInteract(player, hand);
      }
   }

   public void travel(Vec3 travelVector) {
      if (!this.isSick() && !(Boolean)this.entityData.get(IS_PRAYING)) {
         super.travel(travelVector);
      } else {
         this.getNavigation().stop();
         super.travel(Vec3.ZERO);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         this.clientIsProtected = (Boolean)this.entityData.get(IS_PROTECTED);
         if (this.protectedTimer > 0) {
            this.protectedTimer--;
            if (this.protectedTimer <= 0) {
               this.entityData.set(IS_PROTECTED, false);
            }
         }

         if (this.isSick() && this.random.nextInt(80) == 0) {
            this.playSound((SoundEvent)ModSounds.SNEEZE.get(), 1.0F, this.getVoicePitch());
         }

         if (this.tickCount % 20 == 0) {
            if (this.currentAzazel != null && this.currentAzazel.isAlive() && this.distanceToSqr(this.currentAzazel) < 1600.0) {
               this.entityData.set(IS_PRAYING, true);
            } else {
               List<AzazelEntity> azazels = this.level().getEntitiesOfClass(AzazelEntity.class, this.getBoundingBox().inflate(30.0));
               if (!azazels.isEmpty()) {
                  this.currentAzazel = azazels.get(0);
                  this.entityData.set(IS_PRAYING, true);
               } else {
                  this.currentAzazel = null;
                  this.entityData.set(IS_PRAYING, false);
               }
            }
         }

         if (this.currentAzazel != null && this.currentAzazel.isAlive()) {
            this.getLookControl().setLookAt(this.currentAzazel);
            if (this.tickCount % 20 == 0) {
               this.currentAzazel.heal(5.0F);
            }

            if (this.tickCount % 10 == 0 && this.level() instanceof ServerLevel serverLevel) {
               Vec3 start = this.position().add(0.0, 1.0, 0.0);
               Vec3 end = this.currentAzazel.position().add(0.0, 2.0, 0.0);
               double distance = start.distanceTo(end);
               Vec3 dir = end.subtract(start).normalize();

               for (double d = 0.0; d < distance; d += 1.5) {
                  Vec3 pos = start.add(dir.scale(d));
                  serverLevel.sendParticles(DustParticleOptions.REDSTONE, pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
               }
            }
         } else {
            this.entityData.set(IS_PRAYING, false);
         }
      }
   }

   public void die(DamageSource cause) {
      if (!this.level().isClientSide()) {
         ServerLevel serverLevel = (ServerLevel)this.level();
         if (cause.getEntity() instanceof Player killer) {
            killer.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 36000, 0));
         }

         serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 30, 0.3, 0.5, 0.3, 0.05);
         if (((EntityType)ModEntities.STATUE.get()).create(serverLevel) instanceof StatueEntity statueEntity) {
            statueEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            serverLevel.addFreshEntity(statueEntity);
         }
      }

      super.die(cause);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 3, event -> {
         if (this.hurtTime > 0 && !(Boolean)this.entityData.get(IS_PROTECTED)) {
            return event.setAndContinue(HURT_ANIM);
         } else if (this.isSick()) {
            return event.setAndContinue(SICK_ANIM);
         } else if ((Boolean)this.entityData.get(IS_PRAYING)) {
            return event.setAndContinue(PRAY_ANIM);
         } else if (event.isMoving()) {
            return this.getDeltaMovement().horizontalDistanceSqr() > 0.015 ? event.setAndContinue(RUN_ANIM) : event.setAndContinue(WALK_ANIM);
         } else {
            return event.setAndContinue(IDLE_ANIM);
         }
      }));
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return this.isSick() ? null : SoundEvents.PILLAGER_AMBIENT;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.PILLAGER_HURT;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.PILLAGER_DEATH;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putBoolean("IsSick", this.isSick());
      tag.putInt("ProtectedTimer", this.protectedTimer);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setSick(tag.getBoolean("IsSick"));
      this.protectedTimer = tag.getInt("ProtectedTimer");
   }
}
