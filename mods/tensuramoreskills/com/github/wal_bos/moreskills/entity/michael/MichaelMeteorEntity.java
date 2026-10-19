package com.github.wal_bos.moreskills.entity.michael;

import com.github.wal_bos.moreskills.net.msg.MichaelShifuruMeteorVfxPayload;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import java.util.UUID;
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
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;

public class MichaelMeteorEntity extends Entity {
   private static final EntityDataAccessor<Boolean> DATA_BIG = SynchedEntityData.defineId(MichaelMeteorEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(MichaelMeteorEntity.class, EntityDataSerializers.FLOAT);
   private UUID ownerUuid;
   private float gravityDamage = 120.0F;
   private float spaceDamage = 120.0F;
   private Vec3 target = Vec3.ZERO;

   public MichaelMeteorEntity(EntityType<? extends MichaelMeteorEntity> type, Level level) {
      super(type, level);
      this.noPhysics = false;
   }

   public void setup(LivingEntity owner, double x, double y, double z, float gravityDamage, float spaceDamage, float radius, boolean big) {
      Vec3 start = new Vec3(x, y, z);
      this.setup(owner, start, start.add(0.0, -80.0, 0.0), gravityDamage, spaceDamage, radius, big);
   }

   public void setup(LivingEntity owner, Vec3 start, Vec3 target, float gravityDamage, float spaceDamage, float radius, boolean big) {
      this.ownerUuid = owner.getUUID();
      this.gravityDamage = gravityDamage;
      this.spaceDamage = spaceDamage;
      this.target = this.resolveGroundTarget(target);
      this.entityData.set(DATA_RADIUS, radius);
      this.entityData.set(DATA_BIG, big);
      this.setPos(start.x, start.y, start.z);
      Vec3 dir = this.target.subtract(start);
      if (dir.lengthSqr() < 0.001) {
         dir = new Vec3(0.0, -1.0, 0.0);
      }

      dir = dir.normalize();
      this.setDeltaMovement(dir.scale(big ? 0.48 : 0.78));
   }

   public void tick() {
      super.tick();
      Vec3 current = this.position();
      Vec3 motion = this.getDeltaMovement();
      if (this.target != Vec3.ZERO && this.tickCount < 900) {
         Vec3 desired = this.target.subtract(current);
         if (desired.lengthSqr() > 0.001) {
            double speed = this.isBigMeteor() ? 0.48 : 0.78;
            motion = motion.scale(0.82).add(desired.normalize().scale(speed * 0.18));
         }
      }

      motion = motion.add(0.0, this.isBigMeteor() ? -0.0032 : -0.0062, 0.0);
      this.setDeltaMovement(motion);
      if (this.level() instanceof ServerLevel server) {
         Vec3 next = current.add(motion);
         BlockHitResult hit = server.clip(new ClipContext(current, next, Block.COLLIDER, Fluid.NONE, CollisionContext.empty()));
         if (hit.getType() == Type.BLOCK) {
            Vec3 impact = hit.getLocation();
            this.setPos(impact.x, impact.y + 0.05, impact.z);
            this.explode();
            return;
         }

         if (next.y <= this.level().getMinBuildHeight() + 1.0) {
            this.setPos(next.x, next.y, next.z);
            this.explode();
            return;
         }
      }

      this.move(MoverType.SELF, this.getDeltaMovement());
      if (this.level() instanceof ServerLevel && this.onGround()) {
         this.explode();
      } else {
         if (this.level() instanceof ServerLevel && this.tickCount > (this.isBigMeteor() ? 2600 : 1800)) {
            this.discard();
         }
      }
   }

   private Vec3 resolveGroundTarget(Vec3 target) {
      if (this.level() instanceof ServerLevel server) {
         double var11 = Math.min(server.getMaxBuildHeight() - 2.0, Math.max(target.y + 180.0, server.getMaxBuildHeight() - 4.0));
         double bottom = server.getMinBuildHeight() + 1.0;
         Vec3 start = new Vec3(target.x, var11, target.z);
         Vec3 end = new Vec3(target.x, bottom, target.z);
         BlockHitResult hit = server.clip(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, CollisionContext.empty()));
         if (hit.getType() == Type.BLOCK) {
            Vec3 p = hit.getLocation();
            return new Vec3(target.x, p.y + 0.1, target.z);
         } else {
            return target;
         }
      } else {
         return target;
      }
   }

   private void explode() {
      if (!(this.level() instanceof ServerLevel server)) {
         this.discard();
      } else {
         LivingEntity var15 = this.owner();
         double radius = this.getMeteorRadius();
         double damageRadius = this.isBigMeteor() ? radius : radius * 2.0;
         AABB box = this.getBoundingBox().inflate(damageRadius);

         for (LivingEntity target : server.getEntitiesOfClass(LivingEntity.class, box, LivingEntity::isAlive)) {
            if (var15 == null || target != var15 && !target.isAlliedTo(var15) && !var15.isAlliedTo(target)) {
               double distance = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0).distanceTo(this.position());
               float falloff = (float)Math.max(0.25, 1.0 - distance / Math.max(1.0, damageRadius));
               DamageSource gravity = var15 == null
                  ? this.damageSources().magic()
                  : TensuraDamageTypes.getEntityDamageSource(this.level(), TensuraDamageTypes.GRAVITY_EXPLODE, var15);
               DamageSource space = var15 == null
                  ? this.damageSources().magic()
                  : TensuraDamageTypes.getEntityDamageSource(this.level(), TensuraDamageTypes.SPACE_ELEMENTAL, var15);
               target.hurt(gravity, this.gravityDamage * falloff);
               target.hurt(space, this.spaceDamage * falloff);
            }
         }

         MichaelShifuruMeteorVfxPayload.sendImpact(server, this.position(), (float)radius, this.isBigMeteor());
         this.discard();
      }
   }

   public boolean isBigMeteor() {
      return (Boolean)this.entityData.get(DATA_BIG);
   }

   public float getMeteorRadius() {
      return (Float)this.entityData.get(DATA_RADIUS);
   }

   private LivingEntity owner() {
      if (this.ownerUuid != null && this.level() instanceof ServerLevel server) {
         return server.getEntity(this.ownerUuid) instanceof LivingEntity living ? living : null;
      } else {
         return null;
      }
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      double range = this.isBigMeteor() ? 16384.0 : 8192.0;
      return distance < range * range;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_BIG, false);
      builder.define(DATA_RADIUS, 3.5F);
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      if (tag.hasUUID("Owner")) {
         this.ownerUuid = tag.getUUID("Owner");
      }

      this.gravityDamage = tag.getFloat("GravityDamage");
      this.spaceDamage = tag.getFloat("SpaceDamage");
      this.entityData.set(DATA_RADIUS, tag.getFloat("Radius"));
      this.entityData.set(DATA_BIG, tag.getBoolean("Big"));
      if (tag.contains("TargetX")) {
         this.target = new Vec3(tag.getDouble("TargetX"), tag.getDouble("TargetY"), tag.getDouble("TargetZ"));
      }
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      if (this.ownerUuid != null) {
         tag.putUUID("Owner", this.ownerUuid);
      }

      tag.putFloat("GravityDamage", this.gravityDamage);
      tag.putFloat("SpaceDamage", this.spaceDamage);
      tag.putFloat("Radius", this.getMeteorRadius());
      tag.putBoolean("Big", this.isBigMeteor());
      tag.putDouble("TargetX", this.target.x);
      tag.putDouble("TargetY", this.target.y);
      tag.putDouble("TargetZ", this.target.z);
   }
}
