package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.registry.IafItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class StymphalianFeatherEntity extends AbstractArrow {
   public StymphalianFeatherEntity(EntityType<? extends AbstractArrow> t, Level worldIn) {
      super(t, worldIn);
   }

   public StymphalianFeatherEntity(EntityType<? extends AbstractArrow> t, Level worldIn, LivingEntity shooter) {
      super(t, worldIn);
      this.setOwner(shooter);
      this.setBaseDamage((Double)IafCommonConfig.INSTANCE.stymphalianBird.featherAttackDamage.getValue());
      this.setPos(shooter.position());
   }

   public void remove(RemovalReason reason) {
      super.remove(reason);
      if ((Double)IafCommonConfig.INSTANCE.stymphalianBird.featherDropChance.getValue() > 0.0
         && this.level().isClientSide
         && this.random.nextDouble() < (Double)IafCommonConfig.INSTANCE.stymphalianBird.featherDropChance.getValue()) {
         this.spawnAtLocation(this.getPickupItem(), 0.1F);
      }
   }

   public void tick() {
      super.tick();
      if (this.tickCount > 100) {
         this.remove(RemovalReason.DISCARDED);
      }
   }

   protected void onHitEntity(EntityHitResult entityHit) {
      Entity shootingEntity = this.getOwner();
      if (!(shootingEntity instanceof StymphalianBirdEntity) || entityHit.getEntity() == null || !(entityHit.getEntity() instanceof StymphalianBirdEntity)) {
         super.onHitEntity(entityHit);
         if (entityHit.getEntity() != null && entityHit.getEntity() instanceof StymphalianBirdEntity bird) {
            bird.setArrowCount(bird.getArrowCount() - 1);
         }
      }
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)IafItems.STYMPHALIAN_BIRD_FEATHER.get());
   }
}
