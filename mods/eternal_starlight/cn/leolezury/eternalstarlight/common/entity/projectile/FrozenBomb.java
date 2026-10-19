package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class FrozenBomb extends ThrowableItemProjectile {
   public FrozenBomb(EntityType<? extends FrozenBomb> entityType, Level level) {
      super(entityType, level);
   }

   public FrozenBomb(Level level, LivingEntity livingEntity) {
      super(ESEntities.FROZEN_BOMB.get(), livingEntity, level);
   }

   public FrozenBomb(Level level, double x, double y, double z) {
      super(ESEntities.FROZEN_BOMB.get(), x, y, z, level);
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      if (hitResult.getType() != Type.MISS && this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(
            new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
            this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            this.getY() + this.random.nextFloat() * this.getBbHeight(),
            this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            5,
            0.2,
            0.2,
            0.2,
            0.0
         );
         serverLevel.sendParticles(
            (SimpleParticleType)ESParticles.ASHEN_SNOW.get(),
            this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            this.getY() + this.random.nextFloat() * this.getBbHeight(),
            this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            5,
            0.2,
            0.2,
            0.2,
            0.0
         );

         for (int i = 0; i <= 10; i++) {
            serverLevel.sendParticles(
               (SimpleParticleType)ESParticles.ORBITAL_ASHEN_SNOW.get(),
               this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
               this.getY() + (this.random.nextFloat() - 0.5F) * this.getBbHeight() * 8.0F,
               this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
               5,
               0.2,
               1.5,
               0.2,
               0.0
            );
            ESPlatform.INSTANCE
               .sendToAllClients(
                  serverLevel,
                  new ParticlePacket(
                     ExplosionShockParticleOptions.FROZEN,
                     this.getX() + (this.random.nextFloat() - 0.5F) * this.getBbWidth() * 5.0F,
                     this.getY(),
                     this.getZ() + (this.random.nextFloat() - 0.5F) * this.getBbWidth() * 5.0F,
                     0.0,
                     1.0,
                     0.0
                  )
               );
         }

         serverLevel.sendParticles(
            ESExplosionParticleOptions.FROZEN,
            this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            this.getY() + this.random.nextFloat() * this.getBbHeight(),
            this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            10,
            1.5,
            1.5,
            1.5,
            0.0
         );
         serverLevel.sendParticles(
            ESExplosionParticleOptions.FROZEN_BLAST,
            this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            this.getY() + this.random.nextFloat() * this.getBbHeight(),
            this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            3,
            0.2,
            0.2,
            0.2,
            0.0
         );
         this.level()
            .explode(
               this,
               null,
               null,
               this.getX(),
               this.getY(),
               this.getZ(),
               3.0F,
               false,
               ExplosionInteraction.TNT,
               ESExplosionParticleOptions.FROZEN,
               ESExplosionParticleOptions.FROZEN,
               SoundEvents.GENERIC_EXPLODE
            );

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.0))) {
            if (ESEntityUtil.shouldHarm(this.getOwner(), entity)) {
               if (entity.canFreeze()) {
                  entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 100, 300));
               }

               entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60));
            }
         }

         ScreenShakeVfx.createInstance(this.level().dimension(), this.position(), 30.0F, 20, 0.2F, 0.24F, 4.0F, 5.0F).send(serverLevel);
         this.discard();
      }
   }

   @NotNull
   protected Item getDefaultItem() {
      return ESItems.FROZEN_BOMB.get();
   }
}
