package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class HydraArrowEntity extends AbstractArrow {
   public HydraArrowEntity(EntityType<? extends AbstractArrow> t, Level worldIn) {
      super(t, worldIn);
      this.setBaseDamage(5.0);
   }

   public HydraArrowEntity(EntityType<? extends AbstractArrow> t, Level worldIn, double x, double y, double z) {
      this(t, worldIn);
      this.setPos(x, y, z);
      this.setBaseDamage(5.0);
   }

   public HydraArrowEntity(EntityType<? extends HydraArrowEntity> t, Level worldIn, LivingEntity shooter, ItemStack shotFrom) {
      super(t, shooter, worldIn, new ItemStack((ItemLike)IafItems.HYDRA_ARROW.get()), shotFrom);
      this.setBaseDamage(5.0);
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide && !this.inGround) {
         double d0 = this.random.nextGaussian() * 0.02;
         double d1 = this.random.nextGaussian() * 0.02;
         double d2 = this.random.nextGaussian() * 0.02;
         double d3 = 10.0;
         double xRatio = this.getDeltaMovement().x * this.getBbHeight();
         double zRatio = this.getDeltaMovement().z * this.getBbHeight();
         this.level()
            .addParticle(
               (ParticleOptions)IafParticles.HYDRA_BREATH.get(),
               this.getX() + xRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d0 * 10.0,
               this.getY() + this.random.nextFloat() * this.getBbHeight() - d1 * 10.0,
               this.getZ() + zRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d2 * 10.0,
               0.1,
               1.0,
               0.1
            );
         this.level()
            .addParticle(
               (ParticleOptions)IafParticles.HYDRA_BREATH.get(),
               this.getX() + xRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d0 * 10.0,
               this.getY() + this.random.nextFloat() * this.getBbHeight() - d1 * 10.0,
               this.getZ() + zRatio + this.random.nextFloat() * this.getBbWidth() * 1.0F - this.getBbWidth() - d2 * 10.0,
               0.1,
               1.0,
               0.1
            );
      }
   }

   protected void damageShield(Player player, float damage) {
      if (damage >= 3.0F && player.getUseItem().getItem() instanceof ShieldItem) {
         ItemStack copyBeforeUse = player.getUseItem().copy();
         int i = 1 + Mth.floor(damage);
         player.getUseItem().hurtAndBreak(i, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
         if (player.getUseItem().isEmpty()) {
            player.stopUsingItem();
            this.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level().random.nextFloat() * 0.4F);
         }
      }
   }

   protected void doPostHurtEffects(LivingEntity living) {
      if (living instanceof Player player) {
         this.damageShield(player, (float)this.getBaseDamage());
      }

      living.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 0));
      if (this.getOwner() instanceof LivingEntity living1) {
         living1.heal((float)this.getBaseDamage());
      }
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)IafItems.HYDRA_ARROW.get());
   }
}
