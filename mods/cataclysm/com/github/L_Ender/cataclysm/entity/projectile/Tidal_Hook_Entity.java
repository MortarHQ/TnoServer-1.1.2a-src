package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModDataAttachments;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.items.Tidal_Claws;
import com.github.L_Ender.cataclysm.message.MessageHookFalling;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class Tidal_Hook_Entity extends AbstractArrow {
   private static final EntityDataAccessor<Integer> HOOKED_ENTITY_ID = SynchedEntityData.defineId(Tidal_Hook_Entity.class, EntityDataSerializers.INT);
   private double maxRange = 0.0;
   private double maxSpeed = 0.0;
   private boolean isPulling = false;
   private Entity hookedEntity;
   private ItemStack stack;

   public Tidal_Hook_Entity(EntityType<? extends Tidal_Hook_Entity> p_37561_, Level p_37562_) {
      super(p_37561_, p_37562_);
      this.setNoGravity(true);
      this.pickup = Pickup.DISALLOWED;
      this.setBaseDamage(0.0);
   }

   public Tidal_Hook_Entity(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_) {
      super((EntityType)ModEntities.TIDAL_HOOK.get(), p_37570_, p_37569_, p_37571_, null);
      this.setNoGravity(true);
      this.pickup = Pickup.DISALLOWED;
      this.setBaseDamage(0.0);
   }

   public Tidal_Hook_Entity(Level p_338686_, double p_338771_, double p_338674_, double p_338477_, ItemStack p_338255_) {
      super((EntityType)ModEntities.TIDAL_HOOK.get(), p_338771_, p_338674_, p_338477_, p_338686_, p_338255_, p_338255_);
      this.setNoGravity(true);
      this.setBaseDamage(0.0);
   }

   protected void defineSynchedData(Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(HOOKED_ENTITY_ID, 0);
   }

   public void tick() {
      super.tick();
      if (this.getOwner() instanceof Player owner) {
         boolean flag = (Boolean)this.getOwner().getData(ModDataAttachments.HOOK_FALLING);
         if (this.isPulling && this.tickCount % 3 == 0) {
            this.level().playSound(null, this.getOwner().blockPosition(), (SoundEvent)ModSounds.TIDAL_HOOK_LOOP.get(), SoundSource.PLAYERS, 0.4F, 1.0F);
         }

         if (!this.level().isClientSide) {
            if (owner.isDeadOrDying()
               || !flag
               || owner.distanceTo(this) > this.maxRange
               || !(owner.getMainHandItem().getItem() instanceof Tidal_Claws) && !(owner.getOffhandItem().getItem() instanceof Tidal_Claws)) {
               this.kill();
            }

            if (this.hookedEntity != null) {
               if (this.hookedEntity.isRemoved()) {
                  this.hookedEntity = null;
                  this.discard();
               } else {
                  this.moveTo(this.hookedEntity.getX(), this.hookedEntity.getY(0.8), this.hookedEntity.getZ());
               }
            }

            if (owner.getMainHandItem() != this.stack && owner.getOffhandItem() != this.stack) {
               this.kill();
            } else if (this.isPulling) {
               Entity origin = this;
               double brakeZone = 6.0 * (this.maxSpeed / 10.0);
               double pullSpeed = this.maxSpeed / 6.0;
               Vec3 distance = origin.position().subtract(owner.position().add(0.0, owner.getBbHeight() / 2.0F, 0.0));
               Vec3 motion = distance.normalize().scale(distance.length() < brakeZone ? pullSpeed * distance.length() / brakeZone : pullSpeed);
               if (Math.abs(distance.y) < 0.1) {
                  motion = new Vec3(motion.x, 0.0, motion.z);
               }

               if (new Vec3(distance.x, 0.0, distance.z).length() < new Vec3(owner.getBbWidth() / 2.0F, 0.0, owner.getBbWidth() / 2.0F).length() / 1.4) {
                  motion = new Vec3(0.0, motion.y, 0.0);
               }

               owner.setDeltaMovement(motion);
               owner.hurtMarked = true;
            }
         }
      }
   }

   public void kill() {
      if (!this.level().isClientSide && this.getOwner() instanceof Player owner) {
         boolean flag = (Boolean)this.getOwner().getData(ModDataAttachments.HOOK_FALLING);
         PacketDistributor.sendToPlayersTrackingEntityAndSelf(owner, new MessageHookFalling(owner.getId(), true), new CustomPacketPayload[0]);
         owner.setNoGravity(false);
      }

      super.kill();
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      return true;
   }

   protected float getWaterInertia() {
      return 0.9F;
   }

   protected ItemStack getPickupItem() {
      return new ItemStack(Items.ARROW);
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack(Items.ARROW);
   }

   protected void onHitBlock(BlockHitResult blockHitResult) {
      super.onHitBlock(blockHitResult);
      this.isPulling = true;
      if (!this.level().isClientSide && this.getOwner() instanceof Player owner && this.hookedEntity == null) {
         owner.setNoGravity(true);
      }
   }

   public boolean canUsePortal(boolean allowPassengers) {
      return false;
   }

   protected SoundEvent getDefaultHitGroundSoundEvent() {
      return (SoundEvent)ModSounds.TIDAL_HOOK_HIT.get();
   }

   protected void onHitEntity(EntityHitResult entityHitResult) {
      if (!this.level().isClientSide
         && this.getOwner() instanceof Player owner
         && entityHitResult.getEntity() != owner
         && (entityHitResult.getEntity() instanceof LivingEntity || entityHitResult.getEntity() instanceof EnderDragonPart)
         && this.hookedEntity == null) {
         this.hookedEntity = entityHitResult.getEntity();
         this.getEntityData().set(HOOKED_ENTITY_ID, this.hookedEntity.getId() + 1);
         this.isPulling = true;
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.maxRange = tag.getDouble("maxRange");
      this.maxSpeed = tag.getDouble("maxSpeed");
      this.isPulling = tag.getBoolean("isPulling");
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putDouble("maxRange", this.maxRange);
      tag.putDouble("maxSpeed", this.maxSpeed);
      tag.putBoolean("isPulling", this.isPulling);
   }

   public void setProperties(ItemStack stack, double maxRange, double maxVelocity, float pitch, float yaw, float roll, float modifierZ) {
      float f = (float) (Math.PI / 180.0);
      float x = -Mth.sin(yaw * f) * Mth.cos(pitch * f);
      float y = -Mth.sin((pitch + roll) * f);
      float z = Mth.cos(yaw * f) * Mth.cos(pitch * f);
      this.shoot(x, y, z, modifierZ, 0.0F);
      this.stack = stack;
      this.maxRange = maxRange;
      this.maxSpeed = maxVelocity;
   }
}
