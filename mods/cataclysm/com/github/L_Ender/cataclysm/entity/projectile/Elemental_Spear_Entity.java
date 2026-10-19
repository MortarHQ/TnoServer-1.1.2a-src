package com.github.L_Ender.cataclysm.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.EventHooks;

public class Elemental_Spear_Entity extends Projectile {
   public double accelerationPower;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Elemental_Spear_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(Elemental_Spear_Entity.class, EntityDataSerializers.INT);
   public AnimationState SpawnAnimationState = new AnimationState();
   public AnimationState idlespawnAnimationState = new AnimationState();
   private int lifetick;

   public Elemental_Spear_Entity(EntityType<? extends Elemental_Spear_Entity> type, Level level) {
      super(type, level);
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(DAMAGE, 0.0F);
      p_326229_.define(STATE, 0);
   }

   public AnimationState getAnimationState(String input) {
      if (input == "spawn") {
         return this.SpawnAnimationState;
      } else {
         return input == "idle" ? this.idlespawnAnimationState : new AnimationState();
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
      if (STATE.equals(p_21104_)) {
         switch (this.getState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.SpawnAnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.idlespawnAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.idlespawnAnimationState.stop();
      this.SpawnAnimationState.stop();
   }

   public int getState() {
      return (Integer)this.entityData.get(STATE);
   }

   public void setState(int state) {
      this.entityData.set(STATE, state);
   }

   public float getDamage() {
      return (Float)this.entityData.get(DAMAGE);
   }

   public void setDamage(float damage) {
      this.entityData.set(DAMAGE, damage);
   }

   protected Block getClipType() {
      return Block.COLLIDER;
   }

   public void tick() {
      Entity entity = this.getOwner();
      if (this.level().isClientSide || (entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
         super.tick();
         this.lifetick++;
         HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity, this.getClipType());
         if (hitresult.getType() != Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
         }

         if (this.getState() == 1 && this.lifetick > 5) {
            this.setState(2);
         }

         if (this.lifetick > 600) {
            this.discard();
         }

         this.checkInsideBlocks();
         Vec3 vec3 = this.getDeltaMovement();
         double d0 = this.getX() + vec3.x;
         double d1 = this.getY() + vec3.y;
         double d2 = this.getZ() + vec3.z;
         ProjectileUtil.rotateTowardsMovement(this, 1.0F);
         float f = this.getInertia();
         this.SpawnParticle();
         this.setDeltaMovement(vec3.add(vec3.normalize().scale(this.accelerationPower)).scale(f));
         this.setPos(d0, d1, d2);
      } else {
         this.discard();
      }
   }

   protected void SpawnParticle() {
   }

   protected void onHitEntity(EntityHitResult p_37626_) {
      super.onHitEntity(p_37626_);
   }

   protected void onHit(HitResult result) {
      Type hitresult$type = result.getType();
      if (hitresult$type == Type.ENTITY) {
         EntityHitResult entityhitresult = (EntityHitResult)result;
         Entity entity = entityhitresult.getEntity();
         if (entity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) && entity instanceof Projectile projectile) {
            projectile.deflect(ProjectileDeflection.AIM_DEFLECT, this.getOwner(), this.getOwner(), true);
         }

         this.onHitEntity(entityhitresult);
         this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), Context.of(this, (BlockState)null));
      } else if (hitresult$type == Type.BLOCK) {
         BlockHitResult blockhitresult = (BlockHitResult)result;
         this.onHitBlock(blockhitresult);
         BlockPos blockpos = blockhitresult.getBlockPos();
         this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, Context.of(this, this.level().getBlockState(blockpos)));
      }
   }

   protected boolean canHitEntity(Entity p_36842_) {
      return super.canHitEntity(p_36842_) && !p_36842_.noPhysics;
   }

   protected float getInertia() {
      return 0.95F;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putDouble("acceleration_power", this.accelerationPower);
      compound.putFloat("Damage", this.getDamage());
      compound.putInt("State", this.getState());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("acceleration_power", 6)) {
         this.accelerationPower = compound.getDouble("acceleration_power");
      }

      this.setDamage(compound.getFloat("Damage"));
      this.setState(compound.getInt("State"));
   }

   public boolean isPickable() {
      return false;
   }

   public float getPickRadius() {
      return 1.0F;
   }

   public boolean hurt(DamageSource p_37616_, float p_37617_) {
      return false;
   }

   public float getLightLevelDependentMagicValue() {
      return 1.0F;
   }

   public void checkDespawn() {
      if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
         this.discard();
      } else {
         Entity entity = this.level().getNearestPlayer(this, -1.0);
         if (entity != null) {
            double d0 = entity.distanceToSqr(this);
            int i = this.getType().getCategory().getDespawnDistance();
            int j = i * i;
            if (d0 > j && this.removeWhenFarAway(d0)) {
               this.discard();
            }
         }
      }
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return true;
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      this.xRotO = this.getXRot();
      this.yRotO = this.getYRot();
   }

   protected void assignDirectionalMovement(Vec3 movement, double accelerationPower) {
      this.setDeltaMovement(movement.normalize().scale(accelerationPower));
      this.hasImpulse = true;
   }

   protected void onDeflection(@Nullable Entity entity, boolean deflectedByPlayer) {
      super.onDeflection(entity, deflectedByPlayer);
      if (deflectedByPlayer) {
         this.accelerationPower = 0.1;
      } else {
         this.accelerationPower *= 0.5;
      }
   }
}
