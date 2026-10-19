package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class HydraHeadEntity extends MultipartPartEntity {
   public int headIndex;
   public HydraEntity hydra;
   private boolean neck;

   public HydraHeadEntity(EntityType<?> t, Level world) {
      super(t, world);
   }

   public HydraHeadEntity(HydraEntity entity, float radius, float angle, float y, float width, float height, float damageMulti, int headIndex, boolean neck) {
      super((EntityType<?>)IafEntities.HYDRA_MULTIPART.get(), entity, radius, angle, y, width, height, damageMulti);
      this.headIndex = headIndex;
      this.neck = neck;
      this.hydra = entity;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.hydra != null
         && this.hydra.getSeveredHead() != -1
         && this.neck
         && !GorgonEntity.isStoneMob(this.hydra)
         && (this.hydra.getSeveredHead() == this.headIndex || this.level().isClientSide)) {
         for (int k = 0; k < 5; k++) {
            double d2 = 0.4;
            double d0 = 0.1;
            double d1 = 0.1;
            this.level()
               .addParticle(
                  (ParticleOptions)IafParticles.BLOOD.get(),
                  this.getX() + this.random.nextFloat() * this.getBbWidth() - this.getBbWidth() * 0.5,
                  this.getY() - 0.5,
                  this.getZ() + this.random.nextFloat() * this.getBbWidth() - this.getBbWidth() * 0.5,
                  d2,
                  d0,
                  d1
               );
         }
      }
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      Entity parent = this.getParent();
      if (parent instanceof HydraEntity h) {
         h.onHitHead(damage, this.headIndex);
         return h.hurt(source, damage);
      } else {
         return parent != null && parent.hurt(source, damage);
      }
   }
}
