package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class ThioquartzShard extends ThrowableProjectile {
   public float oSpin;
   public float spin;

   public ThioquartzShard(EntityType<? extends ThioquartzShard> entityType, Level level) {
      super(entityType, level);
   }

   public ThioquartzShard(Level level, LivingEntity livingEntity) {
      super(ESEntities.THIOQUARTZ_SHARD.get(), livingEntity, level);
   }

   public ThioquartzShard(Level level, double x, double y, double z) {
      super(ESEntities.THIOQUARTZ_SHARD.get(), x, y, z, level);
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void tick() {
      super.tick();
      if (this.tickCount > 80) {
         this.discard();
      }

      this.oSpin = this.spin;
      this.spin += 0.18849556F;
   }

   protected void onHitBlock(BlockHitResult hitResult) {
      super.onHitBlock(hitResult);
      switch (hitResult.getDirection().getAxis()) {
         case X:
            this.setDeltaMovement(this.getDeltaMovement().multiply(-0.5, 1.0, 1.0));
            break;
         case Y:
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, -0.5, 1.0));
            break;
         case Z:
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 1.0, -0.5));
      }
   }

   protected void onHitEntity(EntityHitResult hitResult) {
      super.onHitEntity(hitResult);
      if (hitResult.getEntity() != this.getOwner()
         && hitResult.getEntity().hurt(ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.ETHER, this, this.getOwner()), 4.0F)
         && hitResult.getEntity() instanceof LivingEntity livingEntity) {
         this.playSound(SoundEvents.GLASS_BREAK);
         int inEtherTicks = ESDataAttachments.IN_ETHER_TICKS.getData(livingEntity);
         if (inEtherTicks < 400) {
            ESDataAttachments.IN_ETHER_TICKS.setData(livingEntity, Math.min(inEtherTicks + 200, 400));
         }
      }

      this.discard();
   }
}
