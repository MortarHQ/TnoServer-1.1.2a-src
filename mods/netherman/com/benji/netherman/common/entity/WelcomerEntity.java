package com.benji.netherman.common.entity;

import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WelcomerEntity extends Monster implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final EntityDataAccessor<Boolean> IS_CASTING = SynchedEntityData.defineId(WelcomerEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_SPAWNING = SynchedEntityData.defineId(WelcomerEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_WELCOMING = SynchedEntityData.defineId(WelcomerEntity.class, EntityDataSerializers.BOOLEAN);
   private int spawnTicks = 0;
   private boolean wasCasting = false;
   private int welcomeTicks = 0;

   public WelcomerEntity(EntityType<? extends Monster> entityType, Level level) {
      super(entityType, level);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 15.0).add(Attributes.MOVEMENT_SPEED, 0.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(IS_CASTING, false);
      builder.define(IS_SPAWNING, false);
      builder.define(IS_WELCOMING, false);
   }

   public boolean isWelcoming() {
      return (Boolean)this.entityData.get(IS_WELCOMING);
   }

   public void setWelcoming(boolean welcoming) {
      this.entityData.set(IS_WELCOMING, welcoming);
   }

   public boolean isSpawning() {
      return (Boolean)this.entityData.get(IS_SPAWNING);
   }

   public void setSpawning(boolean spawning) {
      this.entityData.set(IS_SPAWNING, spawning);
   }

   public boolean isCasting() {
      return (Boolean)this.entityData.get(IS_CASTING);
   }

   public void setCasting(boolean casting) {
      this.entityData.set(IS_CASTING, casting);
   }

   public void startSpawning() {
      this.setSpawning(true);
      this.spawnTicks = 20;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 16.0F, 1.0F));
   }

   public boolean isPushable() {
      return false;
   }

   protected void doPush(Entity entityIn) {
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         if (this.spawnTicks > 0) {
            this.spawnTicks--;
            if (this.spawnTicks == 0) {
               this.setSpawning(false);
            }

            return;
         }

         if (this.tickCount % 10 == 0) {
            AABB searchBox = this.getBoundingBox().inflate(30.0);
            List<GuardianEntity> guardians = this.level().getEntitiesOfClass(GuardianEntity.class, searchBox);
            List<GhastlyEntity> ghastlies = this.level().getEntitiesOfClass(GhastlyEntity.class, searchBox);
            boolean hasGhastly = false;

            for (GhastlyEntity g : ghastlies) {
               if (g.isTame()) {
                  hasGhastly = true;
                  break;
               }
            }

            boolean foundAggressive = false;

            for (GuardianEntity guardian : guardians) {
               if (guardian.getTarget() != null) {
                  foundAggressive = true;
                  break;
               }
            }

            if (foundAggressive) {
               this.setCasting(true);
               this.setWelcoming(false);
            } else if (hasGhastly) {
               this.setCasting(false);
               if (!this.isWelcoming()) {
                  this.welcomeTicks = 20;
               }

               this.setWelcoming(true);
            } else {
               this.setCasting(false);
               this.setWelcoming(false);
            }
         }

         boolean currentCasting = this.isCasting();
         if (currentCasting && !this.wasCasting) {
            this.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.0F, 1.0F);
         }

         this.wasCasting = currentCasting;
      } else if (this.isCasting()) {
         for (int i = 0; i < 2; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 1.5;
            double offsetZ = (this.random.nextDouble() - 0.5) * 1.5;
            this.level().addParticle(ParticleTypes.PORTAL, this.getX() + offsetX, this.getY() + 2.2, this.getZ() + offsetZ, 1.0, 0.0, 0.0);
         }
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<WelcomerEntity> controller = new AnimationController(
         this,
         "controller",
         5,
         event -> {
            if (this.isSpawning()) {
               return event.setAndContinue(RawAnimation.begin().thenPlay("spawn"));
            } else if (this.isCasting()) {
               return event.setAndContinue(RawAnimation.begin().thenLoop("magic_loop"));
            } else if (this.isWelcoming()) {
               return this.welcomeTicks > 0
                  ? event.setAndContinue(RawAnimation.begin().thenPlay("welcome"))
                  : event.setAndContinue(RawAnimation.begin().thenLoop("welcome_loop"));
            } else {
               return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            }
         }
      );
      if (this.isSpawning()) {
         controller.setAnimation(RawAnimation.begin().thenPlay("spawn"));
      } else if (this.isWelcoming() && this.welcomeTicks > 0) {
         controller.setAnimation(RawAnimation.begin().thenPlay("welcome"));
      }

      controllers.add(controller);
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.EVOKER_HURT;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
