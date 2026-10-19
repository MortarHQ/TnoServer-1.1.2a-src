package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleVanillaCloudExtended;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.naga.EntityNaga;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.List;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityPoisonBall extends EntityMagicEffect {
   private static final byte EXPLOSION_PARTICLES_ID = 69;
   public static float GRAVITY = 0.05F;
   public double prevMotionX;
   public double prevMotionY;
   public double prevMotionZ;

   public EntityPoisonBall(EntityType<? extends EntityPoisonBall> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityPoisonBall(EntityType<? extends EntityPoisonBall> type, Level worldIn, LivingEntity caster) {
      super(type, worldIn, caster);
   }

   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      this.setDeltaMovement(x * velocity, y * velocity, z * velocity);
   }

   @Override
   public void tick() {
      this.prevMotionX = this.getDeltaMovement().x;
      this.prevMotionY = this.getDeltaMovement().y;
      this.prevMotionZ = this.getDeltaMovement().z;
      super.tick();
      this.setDeltaMovement(this.getDeltaMovement().subtract(0.0, GRAVITY, 0.0));
      this.move(MoverType.SELF, this.getDeltaMovement());
      this.setYRot(-((float)Mth.atan2(this.getDeltaMovement().x, this.getDeltaMovement().z)) * (180.0F / (float)Math.PI));
      List<Entity> entitiesHit = this.getEntitiesNearby(1.0);
      if (!entitiesHit.isEmpty()) {
         for (Entity entity : entitiesHit) {
            if (!(entity instanceof ItemEntity)
               && entity != this.getCaster()
               && !(entity instanceof EntityNaga)
               && !(entity instanceof ItemEntity)
               && entity.hurt(
                  this.damageSources().indirectMagic(this, this.getCaster()),
                  3.0F * ((Double)ConfigHandler.COMMON.MOBS.NAGA.combatConfig.attackMultiplier.get()).floatValue()
               )
               && entity instanceof LivingEntity) {
               ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.POISON, 80, 1, false, true));
            }
         }
      }

      if (!this.level().noCollision(this, this.getBoundingBox().inflate(0.1))) {
         this.explode();
      }

      if (this.level().isClientSide) {
         float scale = 1.0F;
         int steps = 4;
         double motionX = this.getDeltaMovement().x;
         double motionY = this.getDeltaMovement().y;
         double motionZ = this.getDeltaMovement().z;

         for (int step = 0; step < steps; step++) {
            double x = this.xo + step * (this.getX() - this.xo) / steps;
            double y = this.yo + step * (this.getY() - this.yo) / steps + this.getBbHeight() / 2.0F;
            double z = this.zo + step * (this.getZ() - this.zo) / steps;

            for (int i = 0; i < 1; i++) {
               double xSpeed = scale * 0.02 * (this.random.nextFloat() * 2.0F - 1.0F);
               double ySpeed = scale * 0.02 * (this.random.nextFloat() * 2.0F - 1.0F);
               double zSpeed = scale * 0.02 * (this.random.nextFloat() * 2.0F - 1.0F);
               double value = this.random.nextFloat() * 0.1F;
               double life = this.random.nextFloat() * 10.0F + 15.0F;
               ParticleVanillaCloudExtended.spawnVanillaCloud(
                  this.level(),
                  x - motionX * 0.5,
                  y - motionY * 0.5,
                  z - motionZ * 0.5,
                  xSpeed,
                  ySpeed,
                  zSpeed,
                  scale,
                  0.25 + value,
                  0.75 + value,
                  0.25 + value,
                  0.99,
                  life
               );
            }

            for (int i = 0; i < 2; i++) {
               double xSpeed = scale * 0.06 * (this.random.nextFloat() * 2.0F - 1.0F);
               double ySpeed = scale * 0.06 * (this.random.nextFloat() * 2.0F - 1.0F);
               double zSpeed = scale * 0.06 * (this.random.nextFloat() * 2.0F - 1.0F);
               double value = this.random.nextFloat() * 0.1F;
               double life = this.random.nextFloat() * 5.0F + 10.0F;
               AdvancedParticleBase.spawnParticle(
                  this.level(),
                  ParticleHandler.PIXEL,
                  x + xSpeed - motionX * 0.5,
                  y + ySpeed - motionY * 0.5,
                  z + zSpeed - motionZ * 0.5,
                  xSpeed,
                  ySpeed,
                  zSpeed,
                  true,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  scale * 3.0F,
                  0.07 + value,
                  0.25 + value,
                  0.07 + value,
                  1.0,
                  0.99,
                  life * 0.9,
                  false,
                  true
               );
            }

            for (int i = 0; i < 1; i++) {
               if (this.random.nextFloat() < 0.9F) {
                  double xSpeed = scale * 0.06 * (this.random.nextFloat() * 2.0F - 1.0F);
                  double ySpeed = scale * 0.06 * (this.random.nextFloat() * 2.0F - 1.0F);
                  double zSpeed = scale * 0.06 * (this.random.nextFloat() * 2.0F - 1.0F);
                  double value = this.random.nextFloat() * 0.1F;
                  double life = this.random.nextFloat() * 5.0F + 10.0F;
                  AdvancedParticleBase.spawnParticle(
                     this.level(),
                     ParticleHandler.BUBBLE,
                     x - motionX * 0.5,
                     y - motionY * 0.5,
                     z - motionZ * 0.5,
                     xSpeed,
                     ySpeed,
                     zSpeed,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     3.0,
                     0.25 + value,
                     0.75 + value,
                     0.25 + value,
                     1.0,
                     0.85,
                     life,
                     false,
                     true
                  );
               }
            }
         }
      }

      if (this.tickCount > 50) {
         this.discard();
      }
   }

   private void explode() {
      this.level().broadcastEntityEvent(this, (byte)69);
      this.playSound((SoundEvent)MMSounds.ENTITY_NAGA_ACID_HIT.get(), 1.0F, 1.0F);
      List<Entity> entitiesHit = this.getEntitiesNearby(2.0);
      if (!entitiesHit.isEmpty()) {
         for (Entity entity : entitiesHit) {
            if (entity != this.getCaster()
               && !(entity instanceof EntityNaga)
               && entity.hurt(
                  this.damageSources().indirectMagic(this, this.getCaster()),
                  3.0F * ((Double)ConfigHandler.COMMON.MOBS.NAGA.combatConfig.attackMultiplier.get()).floatValue()
               )
               && entity instanceof LivingEntity livingEntity) {
               livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0, false, true));
            }
         }
      }

      this.discard();
   }

   private void spawnExplosionParticles() {
      if (this.level().isClientSide) {
         float explodeSpeed = 3.5F;

         for (int i = 0; i < 26; i++) {
            Vec3 particlePos = new Vec3(this.random.nextFloat() * 0.25, 0.0, 0.0);
            particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            double value = this.random.nextFloat() * 0.1F;
            double life = this.random.nextFloat() * 17.0F + 30.0F;
            ParticleVanillaCloudExtended.spawnVanillaCloud(
               this.level(),
               this.getX(),
               this.getY(),
               this.getZ(),
               particlePos.x * explodeSpeed,
               particlePos.y * explodeSpeed,
               particlePos.z * explodeSpeed,
               1.0,
               0.25 + value,
               0.75 + value,
               0.25 + value,
               0.6,
               life
            );
         }

         for (int i = 0; i < 26; i++) {
            Vec3 particlePos = new Vec3(this.random.nextFloat() * 0.25, 0.0, 0.0);
            particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            double value = this.random.nextFloat() * 0.1F;
            double life = this.random.nextFloat() * 5.0F + 10.0F;
            AdvancedParticleBase.spawnParticle(
               this.level(),
               ParticleHandler.PIXEL,
               this.getX() + particlePos.x,
               this.getY() + particlePos.y,
               this.getZ() + particlePos.z,
               particlePos.x * explodeSpeed,
               particlePos.y * explodeSpeed,
               particlePos.z * explodeSpeed,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               3.0,
               0.07 + value,
               0.25 + value,
               0.07 + value,
               1.0,
               0.6,
               life * 0.95,
               false,
               true
            );
         }

         for (int i = 0; i < 23; i++) {
            Vec3 particlePos = new Vec3(this.random.nextFloat() * 0.25, 0.0, 0.0);
            particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            double value = this.random.nextFloat() * 0.1F;
            double life = this.random.nextFloat() * 10.0F + 20.0F;
            AdvancedParticleBase.spawnParticle(
               this.level(),
               ParticleHandler.BUBBLE,
               this.getX() + particlePos.x,
               this.getY() + particlePos.y,
               this.getZ() + particlePos.z,
               particlePos.x * explodeSpeed,
               particlePos.y * explodeSpeed,
               particlePos.z * explodeSpeed,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               3.0,
               0.25 + value,
               0.75 + value,
               0.25 + value,
               1.0,
               0.6,
               life * 0.95,
               false,
               true
            );
         }
      }
   }

   public void handleEntityEvent(byte id) {
      if (id == 69) {
         this.spawnExplosionParticles();
      } else {
         super.handleEntityEvent(id);
      }
   }
}
