package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.util.dragon.IafDragonDestructionManager;
import com.iafenvoy.iceandfire.particle.DragonFlameParticleType;
import com.iafenvoy.iceandfire.registry.IafDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;

public class FireDragonChargeEntity extends DragonChargeEntity {
   public FireDragonChargeEntity(EntityType<? extends Fireball> type, Level worldIn) {
      super(type, worldIn);
   }

   public FireDragonChargeEntity(
      EntityType<? extends Fireball> type, Level worldIn, double posX, double posY, double posZ, double accelX, double accelY, double accelZ
   ) {
      super(type, worldIn, posX, posY, posZ, accelX, accelY, accelZ);
   }

   public FireDragonChargeEntity(EntityType<? extends Fireball> type, Level worldIn, DragonBaseEntity shooter, double accelX, double accelY, double accelZ) {
      super(type, worldIn, shooter, accelX, accelY, accelZ);
   }

   @Override
   public boolean isPickable() {
      return true;
   }

   @Override
   public void tick() {
      for (int i = 0; i < 4; i++) {
         this.level()
            .addParticle(
               new DragonFlameParticleType(3.0F),
               this.getX() + (this.random.nextDouble() - 0.5) * this.getBbWidth(),
               this.getY() + (this.random.nextDouble() - 0.5) * this.getBbWidth(),
               this.getZ() + (this.random.nextDouble() - 0.5) * this.getBbWidth(),
               0.0,
               0.0,
               0.0
            );
      }

      if (this.isInWater()) {
         this.remove(RemovalReason.DISCARDED);
      }

      if (this.shouldBurn()) {
         this.igniteForSeconds(1.0F);
      }

      super.tick();
   }

   @Override
   public DamageSource causeDamage(Entity cause) {
      return IafDamageTypes.causeDragonFireDamage(cause);
   }

   @Override
   public void destroyArea(Level world, BlockPos center, DragonBaseEntity destroyer) {
      IafDragonDestructionManager.destroyAreaCharge(world, center, destroyer);
   }

   @Override
   public float getDamage() {
      return ((Double)IafCommonConfig.INSTANCE.dragon.attackDamageFire.getValue()).floatValue();
   }
}
