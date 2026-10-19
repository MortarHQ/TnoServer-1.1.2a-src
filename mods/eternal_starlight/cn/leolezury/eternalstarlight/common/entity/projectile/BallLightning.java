package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.joml.Vector4f;

public class BallLightning extends ThrowableProjectile implements TrailOwner {
   private static final String TAG_TARGET = "target";
   private static final String TAG_SPAWNED_TICKS = "spawned_ticks";
   protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(BallLightning.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(BallLightning.class, EntityDataSerializers.INT);
   private Entity target;
   private UUID targetId;

   public int getSpawnedTicks() {
      return (Integer)this.getEntityData().get(SPAWNED_TICKS);
   }

   public void setSpawnedTicks(int spawnedTicks) {
      this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
   }

   public void setTargetId(int targetId) {
      this.getEntityData().set(TARGET_ID, targetId);
   }

   public int getTargetId() {
      return (Integer)this.getEntityData().get(TARGET_ID);
   }

   public BallLightning(EntityType<? extends BallLightning> entityType, Level level) {
      super(entityType, level);
   }

   public BallLightning(Level level, LivingEntity livingEntity) {
      super(ESEntities.BALL_LIGHTNING.get(), livingEntity, level);
   }

   public BallLightning(Level level, double x, double y, double z) {
      super(ESEntities.BALL_LIGHTNING.get(), x, y, z, level);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(SPAWNED_TICKS, 0).define(TARGET_ID, -1);
   }

   public Entity getTarget() {
      return this.target;
   }

   public void setTarget(Entity target) {
      this.targetId = target.getUUID();
      this.target = target;
      if (!this.level().isClientSide) {
         this.setTargetId(target.getId());
      }
   }

   public void tick() {
      Vec3 movement = this.getDeltaMovement();
      super.tick();
      this.setDeltaMovement(this.getDeltaMovement().normalize().scale(movement.length()));
      if (!this.level().isClientSide) {
         Entity owner = this.getOwner();
         if (owner != null && owner.distanceTo(this) > 32.0F) {
            this.explodeAndDiscard();
            this.discard();
         }

         if (this.target != null && this.target.distanceTo(this) > 16.0F) {
            this.target = null;
            this.targetId = null;
         }

         if (this.target == null && this.targetId != null && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(this.targetId);
            if (entity != null) {
               this.target = entity;
            }

            if (this.target == null) {
               this.targetId = null;
            }
         }

         if (this.target != null) {
            this.setTargetId(this.target.getId());
            ESEntityUtil.RaytraceResult result = ESEntityUtil.raytrace(
               this.level(),
               CollisionContext.of(this),
               this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0),
               this.target.position().add(0.0, this.target.getBbHeight() / 2.0F, 0.0)
            );

            for (Entity entity : result.entities()) {
               if (entity instanceof LivingEntity
                  && ESEntityUtil.shouldHarm(this.getOwner(), entity)
                  && entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.ELECTRIC_SHOCK, this, this.getOwner()), 8.0F)) {
                  entity.igniteForSeconds(2.0F);
               }
            }
         }

         if (this.getSpawnedTicks() > 600) {
            this.explodeAndDiscard();
            this.discard();
         }

         this.setSpawnedTicks(this.getSpawnedTicks() + 1);
      } else {
         this.noCulling = true;
      }
   }

   public boolean shouldRender(double x, double y, double z) {
      return this.shouldRenderAtSqrDistance(this.distanceToSqr(x, y, z) / 16.0);
   }

   protected void applyGravity() {
   }

   public boolean isOnFire() {
      return false;
   }

   protected void onHitBlock(BlockHitResult hitResult) {
      super.onHitBlock(hitResult);
      switch (hitResult.getDirection().getAxis()) {
         case X:
            this.setDeltaMovement(this.getDeltaMovement().multiply(-1.0, 1.0, 1.0));
            break;
         case Y:
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, -1.0, 1.0));
            break;
         case Z:
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 1.0, -1.0));
      }
   }

   private void explodeAndDiscard() {
      this.playSound((SoundEvent)SoundEvents.GENERIC_EXPLODE.value());
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(
            ESExplosionParticleOptions.ENERGY,
            this.getX(),
            this.getY() + this.getBbHeight() / 2.0F,
            this.getZ(),
            20,
            this.getBbWidth() / 2.0F,
            this.getBbHeight() / 2.0F,
            this.getBbWidth() / 2.0F,
            0.0
         );

         for (int i = 0; i < 20; i++) {
            Vec3 speed = new Vec3(
                  (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                  this.random.nextFloat() * 0.05F,
                  (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
               )
               .normalize();
            ESPlatform.INSTANCE
               .sendToAllClients(
                  serverLevel,
                  new ParticlePacket(
                     ExplosionShockParticleOptions.ENERGY,
                     this.position().x + speed.x * 1.2,
                     this.position().y + speed.y * 1.2,
                     this.position().z + speed.z * 1.2,
                     speed.x,
                     speed.y,
                     speed.z
                  )
               );
         }
      }

      for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0))) {
         if (ESEntityUtil.shouldHarm(this.getOwner(), entity)) {
            if (this.getOwner() instanceof Player) {
               entity.invulnerableTime = 0;
            }

            entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.ENERGIZED_FLAME, this, this.getOwner()), 8.0F);
         }
      }

      this.discard();
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.hasUUID("target")) {
         this.targetId = compoundTag.getUUID("target");
      }

      this.setSpawnedTicks(compoundTag.getInt("spawned_ticks"));
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      if (this.target != null) {
         compoundTag.putUUID("target", this.target.getUUID());
      }

      compoundTag.putInt("spawned_ticks", this.getSpawnedTicks());
   }

   @Override
   public TrailEffect createNewTrail() {
      return new TrailEffect(0.125F, 10.0F);
   }

   @Override
   public void updateTrail(TrailEffect effect) {
      Vec3 oldPos = new Vec3(this.xOld, this.yOld, this.zOld);
      effect.update(oldPos.add(0.0, this.getBbHeight() / 2.0F, 0.0));
      if (this.isRemoved()) {
         effect.setLength(Math.max(effect.getLength() - 0.5F, 0.0F));
      }
   }

   @Override
   public Vector4f getTrailColor() {
      return new Vector4f(0.5019608F, 1.0F, 1.0F, 2.0F);
   }
}
