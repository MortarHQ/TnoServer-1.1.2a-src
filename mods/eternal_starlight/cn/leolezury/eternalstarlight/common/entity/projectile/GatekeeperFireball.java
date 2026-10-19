package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import java.util.UUID;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

public class GatekeeperFireball extends Fireball implements TrailOwner {
   private static final String TAG_TARGET = "target";
   private static final String TAG_SPAWNED_TICKS = "spawned_ticks";
   protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(GatekeeperFireball.class, EntityDataSerializers.INT);
   @Nullable
   private LivingEntity target;
   @Nullable
   private UUID targetId;

   public GatekeeperFireball(EntityType<? extends GatekeeperFireball> entityType, Level level) {
      super(entityType, level);
   }

   public GatekeeperFireball(Level level, LivingEntity livingEntity, Vec3 motion) {
      super(ESEntities.GATEKEEPER_FIREBALL.get(), livingEntity, motion, level);
   }

   public int getSpawnedTicks() {
      return (Integer)this.getEntityData().get(SPAWNED_TICKS);
   }

   public void setSpawnedTicks(int spawnedTicks) {
      this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SPAWNED_TICKS, 0);
   }

   public LivingEntity getTarget() {
      return this.target;
   }

   public void setTarget(LivingEntity target) {
      this.targetId = target.getUUID();
      this.target = target;
   }

   protected ParticleOptions getTrailParticle() {
      return ESSmokeParticleOptions.FLAME;
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isOnFire() {
      return false;
   }

   protected boolean shouldBurn() {
      return false;
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      if (this.level() instanceof ServerLevel serverLevel) {
         this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, false, ExplosionInteraction.NONE);
         serverLevel.sendParticles(
            ESExplosionParticleOptions.LAVA,
            this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            this.getY() + this.random.nextFloat() * this.getBbHeight(),
            this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
            10,
            1.5,
            1.5,
            1.5,
            0.0
         );
         this.discard();
      }
   }

   protected void onHitEntity(EntityHitResult entityHitResult) {
      super.onHitEntity(entityHitResult);
      if (!this.level().isClientSide) {
         Entity hitEntity = entityHitResult.getEntity();
         Entity owner = this.getOwner();
         DamageSource damageSource = this.damageSources().fireball(this, owner);
         hitEntity.hurt(damageSource, 8.0F);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.target == null && this.targetId != null && this.level() instanceof ServerLevel serverLevel) {
            if (serverLevel.getEntity(this.targetId) instanceof LivingEntity livingEntity) {
               this.target = livingEntity;
            }

            if (this.target == null) {
               this.targetId = null;
            }
         }

         this.setSpawnedTicks(this.getSpawnedTicks() + 1);
         if (this.getSpawnedTicks() == 60 && this.getTarget() != null) {
            Vec3 power = this.getTarget().position().subtract(this.position()).normalize().scale(0.4F);
            this.setDeltaMovement(power);
         }
      }

      if (this.getSpawnedTicks() < 60 && this.getOwner() != null) {
         Entity owner = this.getOwner();
         float yaw = ESMathUtil.positionToYaw(owner.position(), this.position());
         float pitch = ESMathUtil.positionToPitch(owner.position(), this.position());
         Vec3 newPos = ESMathUtil.rotationToPosition(owner.position(), this.distanceTo(owner), pitch, yaw + 5.0F);
         this.setPos(newPos);
      }
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
      return new TrailEffect(0.5F, 18.0F);
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
      return new Vector4f(0.98039216F, 0.5882353F, 0.019607844F, 1.5F);
   }

   @Override
   public boolean isTrailFullBright() {
      return true;
   }
}
