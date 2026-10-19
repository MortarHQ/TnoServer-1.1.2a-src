package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class AshenSnowball extends ThrowableItemProjectile {
   public AshenSnowball(EntityType<? extends AshenSnowball> entityType, Level level) {
      super(entityType, level);
   }

   public AshenSnowball(Level level, LivingEntity livingEntity) {
      super(ESEntities.ASHEN_SNOWBALL.get(), livingEntity, level);
   }

   public AshenSnowball(Level level, double x, double y, double z) {
      super(ESEntities.ASHEN_SNOWBALL.get(), x, y, z, level);
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

         for (int i = 0; i <= 5; i++) {
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
         }

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.0))) {
            if (ESEntityUtil.shouldHarm(this.getOwner(), entity)) {
               entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60));
            }
         }

         this.discard();
      }
   }

   protected void onHitEntity(EntityHitResult hitResult) {
      super.onHitEntity(hitResult);
      Entity entity = hitResult.getEntity();
      float i = entity instanceof Blaze ? 5.0F : 1.5F;
      if (entity.hurt(this.damageSources().thrown(this, this.getOwner()), i) && entity instanceof LivingEntity living) {
         living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50));
      }
   }

   @NotNull
   protected Item getDefaultItem() {
      return ESItems.ASHEN_SNOWBALL.get();
   }
}
