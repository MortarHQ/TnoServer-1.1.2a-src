package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class SonarBomb extends ThrowableItemProjectile {
   public SonarBomb(EntityType<? extends SonarBomb> entityType, Level level) {
      super(entityType, level);
   }

   public SonarBomb(Level level, LivingEntity livingEntity) {
      super(ESEntities.SONAR_BOMB.get(), livingEntity, level);
   }

   public SonarBomb(Level level, double x, double y, double z) {
      super(ESEntities.SONAR_BOMB.get(), x, y, z, level);
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
            ParticleTypes.EXPLOSION,
            this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            this.getY() + this.random.nextFloat() * this.getBbHeight(),
            this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            3,
            0.2,
            0.2,
            0.2,
            0.0
         );
         serverLevel.sendParticles(
            ParticleTypes.SMOKE,
            this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            this.getY() + this.random.nextFloat() * this.getBbHeight(),
            this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            2,
            0.2,
            0.2,
            0.2,
            0.0
         );
         serverLevel.sendParticles(
            ParticleTypes.WHITE_SMOKE,
            this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            this.getY() + this.random.nextFloat() * this.getBbHeight(),
            this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            2,
            0.2,
            0.2,
            0.2,
            0.0
         );

         for (LivingEntity livingEntity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(20.0))) {
            if (ESEntityUtil.shouldHarm(this.getOwner(), livingEntity)) {
               livingEntity.hurt(
                  this.level().damageSources().mobProjectile(this, this.getOwner() instanceof LivingEntity owner ? owner : null),
                  livingEntity.getType().is(ESTags.EntityTypes.VULNERABLE_TO_SONAR_BOMB) ? 20.0F : 5.0F
               );
               Vec3 delta = livingEntity.position().add(0.0, livingEntity.getBbHeight() / 2.0F, 0.0).subtract(this.position()).normalize().scale(1.1);
               ESPlatform.INSTANCE
                  .sendToAllClients(
                     serverLevel,
                     new ParticlePacket(
                        (ParticleOptions)ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), this.getX(), this.getY(), this.getZ(), delta.x, delta.y, delta.z
                     )
                  );
            }
         }

         this.playSound((SoundEvent)SoundEvents.GENERIC_EXPLODE.value());
         this.playSound(ESSoundEvents.SONAR_BOMB_EXPLODE.get());
         ScreenShakeVfx.createInstance(this.level().dimension(), this.position(), 30.0F, 30, 0.15F, 0.24F, 4.0F, 5.0F).send(serverLevel);
         this.discard();
      }
   }

   @NotNull
   protected Item getDefaultItem() {
      return ESItems.SONAR_BOMB.get();
   }
}
