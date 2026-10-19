package com.github.wal_bos.moreskills.world.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TheCoordinateEntity extends PathfinderMob {
   public TheCoordinateEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
      this.setNoAi(true);
      this.setPersistenceRequired();
      this.setInvulnerable(true);
      this.noPhysics = false;
      this.xpReward = 0;
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 1024.0)
         .add(Attributes.MOVEMENT_SPEED, 0.0)
         .add(Attributes.FOLLOW_RANGE, 0.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.SCALE, 1.0);
   }

   protected void registerGoals() {
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         if (!this.isNoAi()) {
            this.setNoAi(true);
         }

         if (!this.isInvulnerable()) {
            this.setInvulnerable(true);
         }

         if (this.getTarget() != null) {
            this.setTarget(null);
         }

         this.setLastHurtByMob(null);
         this.setLastHurtByPlayer(null);
         this.setDeltaMovement(0.0, 0.0, 0.0);
         if (this.isPassenger()) {
            this.stopRiding();
         }

         this.hurtMarked = false;
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      return false;
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return true;
   }

   public boolean skipAttackInteraction(Entity entity) {
      return true;
   }

   public boolean isAttackable() {
      return false;
   }

   public boolean canBeSeenAsEnemy() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   protected void doPush(Entity entity) {
   }

   public void push(Entity entity) {
   }

   public void push(double x, double y, double z) {
   }

   public boolean canBeLeashed() {
      return false;
   }

   public boolean canChangeDimensions(Level level, Level level2) {
      return false;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      return true;
   }

   public boolean canAttackType(EntityType<?> type) {
      return false;
   }

   public boolean canAttack(LivingEntity target) {
      return false;
   }

   public boolean wantsToPickUp(ItemStack stack) {
      return false;
   }

   public boolean canTakeItem(ItemStack stack) {
      return false;
   }

   public boolean canBeAffected(MobEffectInstance effect) {
      return false;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean isPickable() {
      return false;
   }

   public void playerTouch(Player player) {
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setNoAi(true);
      this.setPersistenceRequired();
      this.setInvulnerable(true);
   }
}
