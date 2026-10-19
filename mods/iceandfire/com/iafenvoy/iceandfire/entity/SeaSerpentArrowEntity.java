package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.registry.IafItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class SeaSerpentArrowEntity extends AbstractArrow {
   public SeaSerpentArrowEntity(EntityType<? extends AbstractArrow> t, Level worldIn) {
      super(t, worldIn);
      this.setBaseDamage(3.0);
   }

   public SeaSerpentArrowEntity(EntityType<? extends AbstractArrow> t, Level worldIn, double x, double y, double z) {
      this(t, worldIn);
      this.setPos(x, y, z);
      this.setBaseDamage(3.0);
   }

   public SeaSerpentArrowEntity(EntityType<? extends AbstractArrow> t, Level worldIn, LivingEntity shooter, ItemStack shotFrom) {
      super(t, shooter, worldIn, new ItemStack((ItemLike)IafItems.SEA_SERPENT_ARROW.get()), shotFrom);
      this.setBaseDamage(3.0);
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide && !this.inGround) {
         double d0 = this.random.nextGaussian() * 0.02;
         double d1 = this.random.nextGaussian() * 0.02;
         double d2 = this.random.nextGaussian() * 0.02;
         double xRatio = this.getDeltaMovement().x * this.getBbHeight();
         double zRatio = this.getDeltaMovement().z * this.getBbHeight();
         this.level()
            .addParticle(
               ParticleTypes.BUBBLE,
               this.getX() + xRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d0 * 10.0,
               this.getY() + this.random.nextFloat() * this.getBbHeight() - d1 * 10.0,
               this.getZ() + zRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d2 * 10.0,
               d0,
               d1,
               d2
            );
         this.level()
            .addParticle(
               ParticleTypes.SPLASH,
               this.getX() + xRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d0 * 10.0,
               this.getY() + this.random.nextFloat() * this.getBbHeight() - d1 * 10.0,
               this.getZ() + zRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d2 * 10.0,
               d0,
               d1,
               d2
            );
      }
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)IafItems.SEA_SERPENT_ARROW.get());
   }

   public boolean isInWater() {
      return false;
   }
}
