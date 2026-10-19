package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class Blazing_Bone_Entity extends ThrowableItemProjectile {
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Blazing_Bone_Entity.class, EntityDataSerializers.FLOAT);

   public Blazing_Bone_Entity(EntityType<? extends Blazing_Bone_Entity> type, Level world) {
      super(type, world);
   }

   public Blazing_Bone_Entity(Level worldIn, float damage, LivingEntity throwerIn) {
      super((EntityType)ModEntities.BLAZING_BONE.get(), throwerIn, worldIn);
      this.setDamage(damage);
   }

   protected void defineSynchedData(Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(DAMAGE, 0.0F);
   }

   public float getDamage() {
      return (Float)this.entityData.get(DAMAGE);
   }

   public void setDamage(float damage) {
      this.entityData.set(DAMAGE, damage);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putFloat("damage", this.getDamage());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setDamage(tag.getFloat("damage"));
   }

   protected Item getDefaultItem() {
      return (Item)ModItems.BLAZING_BONE.get();
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      Entity shooter = this.getOwner();
      Entity entity = result.getEntity();
      if (shooter instanceof LivingEntity) {
         if (entity != shooter && !shooter.isAlliedTo(entity)) {
            entity.hurt(this.damageSources().mobProjectile(this, (LivingEntity)shooter), this.getDamage());
         }
      } else {
         entity.hurt(this.damageSources().magic(), this.getDamage());
      }
   }

   public boolean isNoGravity() {
      return false;
   }

   protected void onHit(HitResult result) {
      super.onHit(result);
      if (!this.level().isClientSide) {
         this.level().broadcastEntityEvent(this, (byte)3);
         this.discard();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void handleEntityEvent(byte id) {
      if (id == 3) {
         for (int i = 0; i < 8; i++) {
            this.level()
               .addParticle(
                  new ItemParticleOption(ParticleTypes.ITEM, new ItemStack((ItemLike)ModItems.BLAZING_BONE.get())),
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  this.random.nextGaussian() * 0.2,
                  this.random.nextGaussian() * 0.2,
                  this.random.nextGaussian() * 0.2
               );
         }
      }
   }
}
