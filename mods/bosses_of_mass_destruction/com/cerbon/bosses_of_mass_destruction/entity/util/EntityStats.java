package com.cerbon.bosses_of_mass_destruction.entity.util;

import net.minecraft.world.entity.LivingEntity;

public class EntityStats implements IEntityStats {
   final LivingEntity livingEntity;

   public EntityStats(LivingEntity livingEntity) {
      this.livingEntity = livingEntity;
   }

   @Override
   public float getMaxHealth() {
      return this.livingEntity.getMaxHealth();
   }

   @Override
   public float getHealth() {
      return this.livingEntity.getHealth();
   }
}
