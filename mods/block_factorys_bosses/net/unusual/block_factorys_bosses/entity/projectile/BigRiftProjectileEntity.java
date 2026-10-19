package net.unusual.block_factorys_bosses.entity.projectile;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class BigRiftProjectileEntity extends RiftProjectileEntity {
   public BigRiftProjectileEntity(EntityType<? extends BigRiftProjectileEntity> type, Level world) {
      super(type, world);
   }

   @Override
   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (result.getEntity() instanceof LivingEntity entity && this.getOwner() != entity) {
         entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
      }
   }
}
