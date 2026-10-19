package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class ThrownStarfire extends ThrowableItemProjectile {
   public ThrownStarfire(EntityType<? extends ThrownStarfire> entityType, Level level) {
      super(entityType, level);
   }

   public ThrownStarfire(Level level, LivingEntity livingEntity) {
      super(ESEntities.STARFIRE.get(), livingEntity, level);
   }

   public ThrownStarfire(Level level, double x, double y, double z) {
      super(ESEntities.STARFIRE.get(), x, y, z, level);
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      if (hitResult.getType() != Type.MISS) {
         if (this.level() instanceof ServerLevel serverLevel) {
            createExplosionParticles(serverLevel, this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0), 12, 1.0);

            for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.0))) {
               if (ESEntityUtil.shouldHarm(this.getOwner(), entity)) {
                  entity.addEffect(new MobEffectInstance(ESMobEffects.STARFIRE.asHolder(), 200));
                  createExplosionParticles(serverLevel, entity.position().add(0.0, entity.getBbHeight() / 2.0F, 0.0), 10, 0.25);
               }
            }
         }

         this.playSound(ESSoundEvents.STARFIRE_WHOOSH.get());
         this.discard();
      }
   }

   protected void onHitBlock(BlockHitResult hitResult) {
      super.onHitBlock(hitResult);
      if (hitResult.getType() != Type.MISS && this.level().getBlockState(hitResult.getBlockPos()).is(BlockTags.SAND)) {
         this.level().setBlockAndUpdate(hitResult.getBlockPos(), ESBlocks.RAW_FLOWGLAZE.get().defaultBlockState());
      }
   }

   public static void createExplosionParticles(ServerLevel serverLevel, Vec3 vec3, int count, double scale) {
      for (int i = 0; i < count; i++) {
         serverLevel.sendParticles(
            (SimpleParticleType)ESParticles.STARFIRE_EXPLOSION.get(),
            vec3.x,
            vec3.y,
            vec3.z,
            5,
            0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale,
            0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale,
            0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale,
            0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale
         );
         serverLevel.sendParticles(
            (SimpleParticleType)ESParticles.STARFIRE_EXPLOSION_SMALL.get(),
            vec3.x,
            vec3.y,
            vec3.z,
            5,
            0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale,
            0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale,
            0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale,
            0.4 * (serverLevel.getRandom().nextFloat() - 0.5) * scale
         );
      }
   }

   @NotNull
   protected Item getDefaultItem() {
      return ESItems.STARFIRE.get();
   }
}
