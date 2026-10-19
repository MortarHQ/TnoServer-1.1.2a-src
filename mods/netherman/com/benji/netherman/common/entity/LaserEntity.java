package com.benji.netherman.common.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LaserEntity extends Mob implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final EntityDataAccessor<Integer> LIFE_TICKS = SynchedEntityData.defineId(LaserEntity.class, EntityDataSerializers.INT);
   private float moveAngle;

   public LaserEntity(EntityType<? extends Mob> type, Level level) {
      super(type, level);
      this.noPhysics = false;
      this.setNoGravity(false);
      this.moveAngle = this.random.nextFloat() * 360.0F;
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 100.0).add(Attributes.MOVEMENT_SPEED, 0.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(LIFE_TICKS, 1200);
   }

   public boolean isPushable() {
      return false;
   }

   public void push(Entity entity) {
   }

   protected void doPush(Entity entity) {
   }

   public boolean isPickable() {
      return false;
   }

   public void knockback(double strength, double x, double z) {
   }

   public boolean hurt(DamageSource source, float amount) {
      return false;
   }

   public void tick() {
      super.tick();
      int life = (Integer)this.entityData.get(LIFE_TICKS);
      if (!this.level().isClientSide()) {
         this.entityData.set(LIFE_TICKS, --life);
         if (life == 20) {
            this.triggerAnim("controller", "death");
         }

         if (life <= 0 && this.tickCount > 1200) {
            this.discard();
            return;
         }

         if (this.tickCount % 10 == 0) {
            this.moveAngle = this.moveAngle + (this.random.nextFloat() - 0.5F) * 60.0F;
         }

         double speed = 0.15;
         double forwardX = Math.cos(Math.toRadians(this.moveAngle)) * speed;
         double forwardZ = Math.sin(Math.toRadians(this.moveAngle)) * speed;
         double waveOffset = Math.sin(this.tickCount * 0.3) * 0.15;
         double perpX = Math.cos(Math.toRadians(this.moveAngle + 90.0F)) * waveOffset;
         double perpZ = Math.sin(Math.toRadians(this.moveAngle + 90.0F)) * waveOffset;
         this.setPos(this.getX() + forwardX + perpX, this.getY(), this.getZ() + forwardZ + perpZ);
         if (this.tickCount % 2 == 0) {
            for (Player player : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox())) {
               if (player.isAlive() && !player.isCreative()) {
                  player.hurt(this.damageSources().magic(), 5.0F);
                  player.setRemainingFireTicks(60);
               }
            }
         }

         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 2, 0.1, 0.1, 0.1, 0.02);
         }
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<LaserEntity> controller = new AnimationController(
         this, "controller", 0, event -> event.setAndContinue(RawAnimation.begin().thenLoop("idle"))
      );
      controller.triggerableAnim("death", RawAnimation.begin().thenPlay("death"));
      controllers.add(controller);
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public void checkDespawn() {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("LifeTicks", (Integer)this.entityData.get(LIFE_TICKS));
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.entityData.set(LIFE_TICKS, tag.getInt("LifeTicks"));
   }
}
