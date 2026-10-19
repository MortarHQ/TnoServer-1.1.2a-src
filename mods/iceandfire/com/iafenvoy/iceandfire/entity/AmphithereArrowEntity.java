package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class AmphithereArrowEntity extends AbstractArrow {
   public AmphithereArrowEntity(EntityType<? extends AbstractArrow> type, Level worldIn) {
      super(type, worldIn);
      this.setBaseDamage(2.5);
   }

   public AmphithereArrowEntity(EntityType<? extends AbstractArrow> type, LivingEntity shooter, Level worldIn, ItemStack from) {
      super(type, shooter, worldIn, new ItemStack((ItemLike)IafItems.AMPHITHERE_ARROW.get()), from);
      this.setOwner(shooter);
      this.setBaseDamage(2.5);
   }

   public void tick() {
      super.tick();
      if ((this.tickCount == 1 || this.tickCount % 70 == 0) && !this.inGround && !this.onGround()) {
         this.playSound((SoundEvent)IafSounds.AMPHITHERE_GUST.get(), 1.0F, 1.0F);
      }

      if (this.level().isClientSide && !this.inGround) {
         double d0 = this.random.nextGaussian() * 0.02;
         double d1 = this.random.nextGaussian() * 0.02;
         double d2 = this.random.nextGaussian() * 0.02;
         double d3 = 10.0;
         double xRatio = this.getDeltaMovement().x * this.getBbWidth();
         double zRatio = this.getDeltaMovement().z * this.getBbWidth();
         this.level()
            .addParticle(
               ParticleTypes.CLOUD,
               this.getX() + xRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d0 * d3,
               this.getY() + this.random.nextFloat() * this.getBbHeight() - d1 * d3,
               this.getZ() + zRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d2 * d3,
               d0,
               d1,
               d2
            );
      }
   }

   protected void doPostHurtEffects(LivingEntity living) {
      living.hasImpulse = true;
      double xRatio = this.getDeltaMovement().x;
      double zRatio = this.getDeltaMovement().z;
      float strength = -1.4F;
      float f = Mth.sqrt((float)(xRatio * xRatio + zRatio * zRatio));
      living.setDeltaMovement(living.getDeltaMovement().multiply(0.5, 1.0, 0.5).subtract(xRatio / f * strength, 0.0, zRatio / f * strength).add(0.0, 0.6, 0.0));
      this.spawnExplosionParticle();
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)IafItems.AMPHITHERE_ARROW.get());
   }

   public void spawnExplosionParticle() {
      if (this.level().isClientSide) {
         for (int height = 0; height < 1 + this.random.nextInt(2); height++) {
            for (int i = 0; i < 20; i++) {
               double d0 = this.random.nextGaussian() * 0.02;
               double d1 = this.random.nextGaussian() * 0.02;
               double d2 = this.random.nextGaussian() * 0.02;
               double d3 = 10.0;
               double xRatio = this.getDeltaMovement().x * this.getBbWidth();
               double zRatio = this.getDeltaMovement().z * this.getBbWidth();
               this.level()
                  .addParticle(
                     ParticleTypes.CLOUD,
                     this.getX() + xRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d0 * d3,
                     this.getY() + this.random.nextFloat() * this.getBbHeight() - d1 * d3,
                     this.getZ() + zRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d2 * d3,
                     d0,
                     d1,
                     d2
                  );
            }
         }
      } else {
         this.level().broadcastEntityEvent(this, (byte)20);
      }
   }

   public void handleEntityEvent(byte id) {
      if (id == 20) {
         this.spawnExplosionParticle();
      } else {
         super.handleEntityEvent(id);
      }
   }
}
