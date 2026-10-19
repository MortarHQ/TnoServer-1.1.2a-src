package com.finderfeed.raids_enhanced.content.entities.raid_blimp.raid_airship_parts;

import com.finderfeed.raids_enhanced.init.REEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;

public class RaidBlimpPart extends Entity {
   public static final int WOODEN_STICK = 1;
   public static final int PROPELLER = 2;
   public static final int GOLEM_PART = 3;
   public static EntityDataAccessor<Integer> PART_TYPE = SynchedEntityData.defineId(RaidBlimpPart.class, EntityDataSerializers.INT);
   public static EntityDataAccessor<Integer> LIFETIME = SynchedEntityData.defineId(RaidBlimpPart.class, EntityDataSerializers.INT);
   public int landedTime = -1;
   public int rotation = 0;
   public Vec3 lastDeltaMovement = Vec3.ZERO;

   public static void summon(Level level, Vec3 pos, Vec3 movement, int partType, int lifetime) {
      RaidBlimpPart part = new RaidBlimpPart(REEntities.RAID_AIRSHIP_PART.get(), level);
      part.setPos(pos);
      part.getEntityData().set(LIFETIME, lifetime);
      part.setDeltaMovement(movement);
      part.setPartType(partType);
      part.lastDeltaMovement = movement;
      level.addFreshEntity(part);
   }

   public RaidBlimpPart(EntityType<?> type, Level level) {
      super(type, level);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.tickCount > (Integer)this.getEntityData().get(LIFETIME)) {
            this.setRemoved(RemovalReason.DISCARDED);
         }
      } else if (!this.onGround()) {
         for (int i = 0; i < 3; i++) {
            this.level()
               .addParticle(
                  ParticleTypes.SMOKE,
                  true,
                  this.getX() + this.random.nextFloat() * 1.0F - 0.5,
                  this.getY() + this.random.nextFloat() * 1.0F - 0.5,
                  this.getZ() + this.random.nextFloat() * 1.0F - 0.5,
                  0.0,
                  0.0,
                  0.0
               );
            if (i == 1) {
               this.level()
                  .addParticle(
                     ParticleTypes.LARGE_SMOKE,
                     true,
                     this.getX() + this.random.nextFloat() * 1.0F - 0.5,
                     this.getY() + this.random.nextFloat() * 1.0F - 0.5,
                     this.getZ() + this.random.nextFloat() * 1.0F - 0.5,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }

      if (!this.getDeltaMovement().equals(Vec3.ZERO)) {
         this.lastDeltaMovement = this.getDeltaMovement();
         if (this.tickCount > 5) {
            ClipContext clipContext = new ClipContext(
               this.position(), this.position().add(this.getDeltaMovement()), Block.COLLIDER, Fluid.NONE, CollisionContext.empty()
            );
            BlockHitResult res = this.level().clip(clipContext);
            if (res.getType() != Type.MISS) {
               Vec3 location = res.getLocation();
               this.setOnGround(true);
               this.teleportTo(location.x, location.y, location.z);
               this.setDeltaMovement(Vec3.ZERO);
            }
         }
      }

      if (this.onGround()) {
         this.setNoGravity(true);
         this.setDeltaMovement(Vec3.ZERO);
         this.landedTime++;
      }

      if (this.landedTime == -1) {
         this.rotation++;
      }

      this.applyGravity();
      this.setPos(this.position().add(this.getDeltaMovement()));
   }

   public void setPartType(int partType) {
      this.getEntityData().set(PART_TYPE, partType);
   }

   public int getPartType() {
      return (Integer)this.getEntityData().get(PART_TYPE);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(PART_TYPE, 1);
      builder.define(LIFETIME, 800);
   }

   protected double getDefaultGravity() {
      return 0.08;
   }

   protected void readAdditionalSaveData(CompoundTag p_20052_) {
   }

   protected void addAdditionalSaveData(CompoundTag p_20139_) {
   }
}
