package com.benji.netherman.common.entity;

import com.benji.netherman.config.AzazelConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AzazelSpikeEntity extends Projectile implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private int lifeTicks = 0;
   public int animState = 0;

   public AzazelSpikeEntity(EntityType<? extends Projectile> type, Level level) {
      super(type, level);
      this.setNoGravity(true);
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void tick() {
      super.tick();
      this.lifeTicks++;
      if (this.lifeTicks == 20) {
         this.animState = 1;
      }

      if (this.lifeTicks >= 40) {
         if (this.level() instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY(), this.getZ(), 10, 0.2, 0.2, 0.2, 0.0);
         }

         this.discard();
      } else {
         if (this.lifeTicks < 25 && !this.level().isClientSide() && this.tickCount % 5 == 0) {
            AABB hitbox = this.getBoundingBox().inflate(0.2);
            float spikeDamage = ((Double)AzazelConfig.HUMAN_SPIKE_DAMAGE.get()).floatValue();
            int witherDuration = (Integer)AzazelConfig.HUMAN_SPIKE_WITHER_DURATION.get();

            for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, hitbox)) {
               if (target != this.getOwner() && !(target instanceof AzazelHumanEntity)) {
                  target.hurt(this.damageSources().magic(), spikeDamage);
                  if (witherDuration > 0) {
                     target.addEffect(new MobEffectInstance(MobEffects.WITHER, witherDuration, 1));
                  }
               }
            }
         }

         if (this.lifeTicks == 1 && this.level() instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY(), this.getZ(), 15, 0.5, 0.0, 0.5, 0.0);
         }
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(
            this,
            "controller",
            0,
            event -> this.animState == 0
               ? event.setAndContinue(RawAnimation.begin().thenPlay("spawn"))
               : event.setAndContinue(RawAnimation.begin().thenPlay("despawn"))
         )
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
