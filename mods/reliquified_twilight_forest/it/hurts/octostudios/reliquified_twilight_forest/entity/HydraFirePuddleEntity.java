package it.hurts.octostudios.reliquified_twilight_forest.entity;

import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HydraFirePuddleEntity extends Entity implements TraceableEntity {
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(HydraFirePuddleEntity.class, EntityDataSerializers.FLOAT);
   public static final ParticleOptions BORDER_PARTICLES = ParticleUtils.constructSimpleSpark(new Color(255, 150, 0, 150), 0.25F, 10, 0.87F);
   public static final ParticleOptions INSIDE_PARTICLES = ParticleUtils.constructSimpleSpark(new Color(175, 65, 0, 75), 0.4F, 30, 0.925F);
   private int lifetime = 200;
   @Nullable
   private UUID ownerUUID;
   @Nullable
   private Entity cachedOwner;

   public void setDamage(float damage) {
      this.getEntityData().set(DAMAGE, damage);
   }

   public float getDamage() {
      return (Float)this.getEntityData().get(DAMAGE);
   }

   public HydraFirePuddleEntity(EntityType<? extends Entity> entityType, Level level) {
      super(entityType, level);
   }

   public void setOwner(@Nullable Entity owner) {
      if (owner != null) {
         this.ownerUUID = owner.getUUID();
         this.cachedOwner = owner;
      }
   }

   @Nullable
   public Entity getOwner() {
      if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
         return this.cachedOwner;
      } else if (this.ownerUUID != null && this.level() instanceof ServerLevel serverlevel) {
         this.cachedOwner = serverlevel.getEntity(this.ownerUUID);
         return this.cachedOwner;
      } else {
         return null;
      }
   }

   public void tick() {
      this.refreshDimensions();
      if (this.level().isClientSide) {
         float time = this.tickCount % 40 + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);

         for (int i = 0; i < 360; i += 20) {
            Vec3 direction = new Vec3(1.0, 0.0, 0.0).yRot((float)Math.toRadians(i + time * 2.0F));
            Vec3 pos = this.position().add(direction.scale(this.getBbWidth() / 2.0F));
            Vec3 randomPos = new Vec3(this.getRandomX(0.5), this.position().y, this.getRandomZ(0.5));
            this.level().addParticle(BORDER_PARTICLES, pos.x, pos.y + 0.05, pos.z, 0.0, 0.0, 0.0);
            if (i % 40 == 0 && randomPos.distanceTo(this.position()) < this.getBbWidth() / 2.0F) {
               this.level().addParticle(INSIDE_PARTICLES, randomPos.x, randomPos.y + 0.05, randomPos.z, 0.0, 0.0, 0.0);
            }
         }

         if (this.random.nextFloat() < 0.08F) {
            Vec3 randomPos = new Vec3(this.getRandomX(0.5), this.position().y, this.getRandomZ(0.5));
            if (randomPos.distanceTo(this.position()) < this.getBbWidth() / 2.0F) {
               this.level().addParticle(ParticleTypes.LAVA, randomPos.x, randomPos.y + 0.05, randomPos.z, 0.05, 0.05, 0.05);
            }
         }
      } else if (this.getLifetime() <= 0) {
         this.discard();
      } else {
         List<LivingEntity> toHurt = this.level()
            .getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.0, 0.1, 0.0))
            .stream()
            .filter(entity -> !entity.equals(this.getOwner()) && !EntityUtils.isAlliedTo(this.getOwner(), entity) && entity.isAlive())
            .toList();
         toHurt.forEach(entity -> {
            entity.hurt(this.level().damageSources().source(DamageTypes.IN_FIRE, this.getOwner(), this), this.getDamage());
            entity.setRemainingFireTicks(100);
         });
         if (this.isAlive()) {
            int i = this.getLifetime();
            if (i > 0) {
               this.setLifetime(--i);
            }
         }
      }
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DAMAGE, 1.0F);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setLifetime(compound.getInt("lifetime"));
      this.setDamage(compound.getFloat("damage"));
      if (compound.hasUUID("Owner")) {
         this.ownerUUID = compound.getUUID("Owner");
         this.cachedOwner = null;
      }
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("lifetime", this.getLifetime());
      compound.putFloat("damage", this.getDamage());
      if (this.ownerUUID != null) {
         compound.putUUID("Owner", this.ownerUUID);
      }
   }

   public EntityDimensions getDimensions(Pose pose) {
      return EntityDimensions.scalable(4.0F + Mth.sin(this.tickCount / 10.0F) / 4.0F, 0.3F);
   }

   public int getLifetime() {
      return this.lifetime;
   }

   public void setLifetime(int lifetime) {
      this.lifetime = lifetime;
   }
}
