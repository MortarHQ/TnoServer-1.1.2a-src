package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class Urchin_Spike_Entity extends ThrowableItemProjectile {
   public Urchin_Spike_Entity(EntityType<? extends Urchin_Spike_Entity> type, Level world) {
      super(type, world);
   }

   public Urchin_Spike_Entity(Level worldIn, LivingEntity throwerIn) {
      super((EntityType)ModEntities.URCHIN_SPIKE.get(), throwerIn, worldIn);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
   }

   protected Item getDefaultItem() {
      return (Item)ModItems.URCHIN_SPIKE.get();
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      Entity shooter = this.getOwner();
      Entity entity = result.getEntity();
      float i = 4.0F;
      if (shooter instanceof LivingEntity) {
         if (entity != shooter
            && !shooter.isAlliedTo(entity)
            && entity.hurt(this.damageSources().mobProjectile(this, (LivingEntity)shooter), i)
            && entity instanceof LivingEntity) {
            ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0), this);
         }
      } else {
         entity.hurt(this.damageSources().mobProjectile(this, null), i);
      }
   }

   public boolean isInWater() {
      return false;
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
            ItemStack itemstack = this.getItem();
            if (!itemstack.isEmpty()) {
               this.level()
                  .addParticle(
                     new ItemParticleOption(ParticleTypes.ITEM, itemstack),
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
}
