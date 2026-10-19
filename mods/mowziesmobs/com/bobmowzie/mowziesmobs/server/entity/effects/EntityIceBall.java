package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.particle.ParticleCloud;
import com.bobmowzie.mowziesmobs.client.particle.ParticleRing;
import com.bobmowzie.mowziesmobs.client.particle.ParticleSnowFlake;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenData;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import java.util.List;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityIceBall extends EntityMagicEffect {
   public EntityIceBall(Level world) {
      super((EntityType<? extends EntityMagicEffect>)EntityHandler.ICE_BALL.get(), world);
   }

   public EntityIceBall(EntityType<? extends EntityIceBall> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityIceBall(EntityType<? extends EntityIceBall> type, Level worldIn, LivingEntity caster) {
      super(type, worldIn, caster);
   }

   @Override
   public void tick() {
      super.tick();
      this.move(MoverType.SELF, this.getDeltaMovement());
      if (this.tickCount == 1 && this.level().isClientSide) {
         MMCommon.PROXY.playIceBreathSound(this);
      }

      List<Entity> entitiesHit = this.getEntitiesNearby(2.0);
      if (!entitiesHit.isEmpty()) {
         for (Entity entity : entitiesHit) {
            if (!(entity instanceof ItemEntity)
               && entity != this.getCaster()
               && !(entity instanceof ItemEntity)
               && !entity.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
               && !(entity instanceof EnderDragon)
               && entity.hurt(
                  this.damageSources().freeze(), 3.0F * ((Double)ConfigHandler.COMMON.MOBS.FROSTMAW.combatConfig.attackMultiplier.get()).floatValue()
               )
               && entity instanceof LivingEntity) {
               DataHandler.<FrozenData>getData(entity, DataHandler.FROZEN_DATA).addFreezeProgress((LivingEntity)entity, 1.0F);
            }
         }
      }

      if (!this.level().noCollision(this, this.getBoundingBox().inflate(0.15))) {
         this.explode();
      }

      if (this.level().isClientSide) {
         float scale = 2.0F;
         double x = this.getX();
         double y = this.getY() + this.getBbHeight() / 2.0F;
         double z = this.getZ();
         double motionX = this.getDeltaMovement().x;
         double motionY = this.getDeltaMovement().y;
         double motionZ = this.getDeltaMovement().z;

         for (int i = 0; i < 4; i++) {
            double xSpeed = scale * 0.01 * (this.random.nextFloat() * 2.0F - 1.0F);
            double ySpeed = scale * 0.01 * (this.random.nextFloat() * 2.0F - 1.0F);
            double zSpeed = scale * 0.01 * (this.random.nextFloat() * 2.0F - 1.0F);
            float value = this.random.nextFloat() * 0.15F;
            this.level()
               .addParticle(
                  new ParticleCloud.Data(
                     0.75F + value, 0.75F + value, 1.0F, scale * (10.0F + this.random.nextFloat() * 20.0F), 20, ParticleCloud.EnumCloudBehavior.SHRINK, 1.0F
                  ),
                  x + xSpeed,
                  y + ySpeed,
                  z + zSpeed,
                  xSpeed,
                  ySpeed,
                  zSpeed
               );
         }

         for (int i = 0; i < 1; i++) {
            double xSpeed = scale * 0.01 * (this.random.nextFloat() * 2.0F - 1.0F);
            double ySpeed = scale * 0.01 * (this.random.nextFloat() * 2.0F - 1.0F);
            double zSpeed = scale * 0.01 * (this.random.nextFloat() * 2.0F - 1.0F);
            this.level()
               .addParticle(
                  new ParticleCloud.Data(1.0F, 1.0F, 1.0F, scale * (5.0F + this.random.nextFloat() * 10.0F), 40, ParticleCloud.EnumCloudBehavior.SHRINK, 1.0F),
                  x,
                  y,
                  z,
                  xSpeed,
                  ySpeed,
                  zSpeed
               );
         }

         for (int i = 0; i < 5; i++) {
            double xSpeed = scale * 0.05 * (this.random.nextFloat() * 2.0F - 1.0F);
            double ySpeed = scale * 0.05 * (this.random.nextFloat() * 2.0F - 1.0F);
            double zSpeed = scale * 0.05 * (this.random.nextFloat() * 2.0F - 1.0F);
            this.level()
               .addParticle(
                  new ParticleSnowFlake.Data(40.0F, false),
                  x - 20.0 * xSpeed + motionX,
                  y - 20.0 * ySpeed + motionY,
                  z - 20.0 * zSpeed + motionZ,
                  xSpeed,
                  ySpeed,
                  zSpeed
               );
         }

         float yaw = (float)Math.atan2(motionX, motionZ);
         float pitch = (float)(Math.acos(motionY / Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ)) + (Math.PI / 2));
         if (this.tickCount % 3 == 0) {
            this.level()
               .addParticle(
                  new ParticleRing.Data(yaw, pitch, 40.0F, 0.9F, 0.9F, 1.0F, 0.4F, (int)(scale * 16.0F), false, ParticleRing.EnumRingBehavior.GROW_THEN_SHRINK),
                  x + 1.5 * motionX,
                  y + 1.5 * motionY,
                  z + 1.5 * motionZ,
                  0.0,
                  0.0,
                  0.0
               );
         }

         if (this.tickCount == 1) {
            this.level()
               .addParticle(
                  new ParticleRing.Data(yaw, pitch, 20.0F, 0.9F, 0.9F, 1.0F, 0.4F, (int)(scale * 16.0F), false, ParticleRing.EnumRingBehavior.GROW),
                  x,
                  y,
                  z,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      if (this.tickCount > 50) {
         this.discard();
      }
   }

   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      this.setDeltaMovement(x * velocity, y * velocity, z * velocity);
   }

   private void explode() {
      if (this.level().isClientSide) {
         for (int i = 0; i < 8; i++) {
            Vec3 particlePos = new Vec3(this.random.nextFloat() * 0.3, 0.0, 0.0);
            particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            float value = this.random.nextFloat() * 0.15F;
            this.level()
               .addParticle(
                  new ParticleCloud.Data(
                     0.75F + value, 0.75F + value, 1.0F, 10.0F + this.random.nextFloat() * 20.0F, 40, ParticleCloud.EnumCloudBehavior.GROW, 1.0F
                  ),
                  this.getX() + particlePos.x,
                  this.getY() + particlePos.y,
                  this.getZ() + particlePos.z,
                  particlePos.x,
                  particlePos.y,
                  particlePos.z
               );
         }

         for (int i = 0; i < 10; i++) {
            Vec3 particlePos = new Vec3(this.random.nextFloat() * 0.3, 0.0, 0.0);
            particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            this.level()
               .addParticle(
                  new ParticleSnowFlake.Data(40.0F, false),
                  this.getX() + particlePos.x,
                  this.getY() + particlePos.y,
                  this.getZ() + particlePos.z,
                  particlePos.x,
                  particlePos.y,
                  particlePos.z
               );
         }
      }

      this.discard();
   }
}
