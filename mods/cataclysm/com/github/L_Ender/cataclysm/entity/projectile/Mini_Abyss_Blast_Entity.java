package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.client.particle.Options.LightningParticleOptions;
import com.github.L_Ender.cataclysm.client.tool.ControlledAnimation;
import com.github.L_Ender.cataclysm.entity.Pet.The_Baby_Leviathan_Entity;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class Mini_Abyss_Blast_Entity extends Entity {
   public static final double RADIUS = 15.0;
   public LivingEntity caster;
   public double endPosX;
   public double endPosY;
   public double endPosZ;
   public double collidePosX;
   public double collidePosY;
   public double collidePosZ;
   public double prevCollidePosX;
   public double prevCollidePosY;
   public double prevCollidePosZ;
   public float renderYaw;
   public float renderPitch;
   public ControlledAnimation appear = new ControlledAnimation(3);
   public boolean on = true;
   public Direction blockSide = null;
   private static final EntityDataAccessor<Float> YAW = SynchedEntityData.defineId(Mini_Abyss_Blast_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(Mini_Abyss_Blast_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Mini_Abyss_Blast_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(Mini_Abyss_Blast_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Float> BEAMDIRECTION = SynchedEntityData.defineId(Mini_Abyss_Blast_Entity.class, EntityDataSerializers.FLOAT);
   public float prevYaw;
   public float prevPitch;
   @OnlyIn(Dist.CLIENT)
   private Vec3[] attractorPos;

   public Mini_Abyss_Blast_Entity(EntityType<? extends Mini_Abyss_Blast_Entity> type, Level world) {
      super(type, world);
      this.noCulling = true;
      if (world.isClientSide) {
         this.attractorPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }
   }

   public Mini_Abyss_Blast_Entity(
      EntityType<? extends Mini_Abyss_Blast_Entity> type,
      Level world,
      LivingEntity caster,
      double x,
      double y,
      double z,
      float yaw,
      float pitch,
      int duration,
      float direction
   ) {
      this(type, world);
      this.caster = caster;
      this.setYaw(yaw);
      this.setPitch(pitch);
      this.setDuration(duration);
      this.setBeamDirection(direction);
      this.setPos(x, y, z);
      this.calculateEndPos();
      if (!world.isClientSide) {
         this.setCasterID(caster.getId());
      }
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public void tick() {
      super.tick();
      this.prevCollidePosX = this.collidePosX;
      this.prevCollidePosY = this.collidePosY;
      this.prevCollidePosZ = this.collidePosZ;
      this.prevYaw = this.renderYaw;
      this.prevPitch = this.renderPitch;
      this.xo = this.getX();
      this.yo = this.getY();
      this.zo = this.getZ();
      if (this.tickCount == 1 && this.level().isClientSide) {
         this.caster = (LivingEntity)this.level().getEntity(this.getCasterID());
      }

      if (!this.level().isClientSide && this.caster instanceof The_Baby_Leviathan_Entity) {
         this.updateWithHarbinger();
      }

      if (this.caster != null) {
         this.renderYaw = (float)((this.caster.yHeadRot + this.getBeamDirection()) * Math.PI / 180.0);
         this.renderPitch = (float)(-this.caster.getXRot() * Math.PI / 180.0);
      }

      if (!this.on && this.appear.getTimer() == 0) {
         this.discard();
      }

      if (this.on && this.tickCount > 20) {
         this.appear.increaseTimer();
      } else {
         this.appear.decreaseTimer();
      }

      if (this.caster != null && !this.caster.isAlive()) {
         this.discard();
      }

      if (this.tickCount > 20) {
         this.calculateEndPos();
         List<LivingEntity> hit = this.raytraceEntities(
               this.level(), new Vec3(this.getX(), this.getY(), this.getZ()), new Vec3(this.endPosX, this.endPosY, this.endPosZ)
            )
            .entities;
         if (this.blockSide != null) {
            this.spawnExplosionParticles(3);
         }

         if (!this.level().isClientSide) {
            for (LivingEntity target : hit) {
               if (this.caster != null && !this.caster.isAlliedTo(target) && target != this.caster) {
                  target.hurt(CMDamageTypes.causeDeathLaserDamage(this, this.caster), 5.0F);
               }
            }
         }
      }

      if (this.tickCount - 20 > this.getDuration()) {
         this.on = false;
      }
   }

   private void spawnExplosionParticles(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 1.0F;
         float yaw = (float)(this.random.nextFloat() * 2.0F * Math.PI);
         float motionY = this.random.nextFloat() * 0.08F;
         float motionX = 1.0F * Mth.cos(yaw);
         float motionZ = 1.0F * Mth.sin(yaw);
         this.level()
            .addParticle(new LightningParticleOptions(102, 26, 204), this.collidePosX, this.collidePosY + 0.1, this.collidePosZ, motionX, motionY, motionZ);
      }
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(YAW, 0.0F);
      p_326229_.define(PITCH, 0.0F);
      p_326229_.define(DURATION, 0);
      p_326229_.define(CASTER, -1);
      p_326229_.define(BEAMDIRECTION, 90.0F);
   }

   public float getYaw() {
      return (Float)this.entityData.get(YAW);
   }

   public void setYaw(float yaw) {
      this.entityData.set(YAW, yaw);
   }

   public float getPitch() {
      return (Float)this.entityData.get(PITCH);
   }

   public void setPitch(float pitch) {
      this.entityData.set(PITCH, pitch);
   }

   public int getDuration() {
      return (Integer)this.entityData.get(DURATION);
   }

   public void setDuration(int duration) {
      this.entityData.set(DURATION, duration);
   }

   public float getBeamDirection() {
      return (Float)this.entityData.get(BEAMDIRECTION);
   }

   public void setBeamDirection(float beamDirection) {
      this.entityData.set(BEAMDIRECTION, beamDirection);
   }

   public int getCasterID() {
      return (Integer)this.entityData.get(CASTER);
   }

   public void setCasterID(int id) {
      this.entityData.set(CASTER, id);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setYaw(compound.getFloat("Yaw"));
      this.setPitch(compound.getFloat("Pitch"));
      this.setDuration(compound.getInt("Duration"));
      this.setBeamDirection(compound.getFloat("BeamDirection"));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putFloat("Yaw", this.getYaw());
      compound.putFloat("Pitch", this.getPitch());
      compound.putInt("Duration", this.getDuration());
      compound.putFloat("BeamDirection", this.getBeamDirection());
   }

   private void calculateEndPos() {
      if (this.level().isClientSide()) {
         this.endPosX = this.getX() + 15.0 * Math.cos(this.renderYaw) * Math.cos(this.renderPitch);
         this.endPosZ = this.getZ() + 15.0 * Math.sin(this.renderYaw) * Math.cos(this.renderPitch);
         this.endPosY = this.getY() + 15.0 * Math.sin(this.renderPitch);
      } else {
         this.endPosX = this.getX() + 15.0 * Math.cos(this.getYaw()) * Math.cos(this.getPitch());
         this.endPosZ = this.getZ() + 15.0 * Math.sin(this.getYaw()) * Math.cos(this.getPitch());
         this.endPosY = this.getY() + 15.0 * Math.sin(this.getPitch());
      }
   }

   public Mini_Abyss_Blast_Entity.LaserbeamHitResult raytraceEntities(Level world, Vec3 from, Vec3 to) {
      Mini_Abyss_Blast_Entity.LaserbeamHitResult result = new Mini_Abyss_Blast_Entity.LaserbeamHitResult();
      result.setBlockHit(world.clip(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this)));
      if (result.blockHit != null) {
         Vec3 hitVec = result.blockHit.getLocation();
         this.collidePosX = hitVec.x;
         this.collidePosY = hitVec.y;
         this.collidePosZ = hitVec.z;
         this.blockSide = result.blockHit.getDirection();
      } else {
         this.collidePosX = this.endPosX;
         this.collidePosY = this.endPosY;
         this.collidePosZ = this.endPosZ;
         this.blockSide = null;
      }

      for (LivingEntity entity : world.getEntitiesOfClass(
         LivingEntity.class,
         new AABB(
               Math.min(this.getX(), this.collidePosX),
               Math.min(this.getY(), this.collidePosY),
               Math.min(this.getZ(), this.collidePosZ),
               Math.max(this.getX(), this.collidePosX),
               Math.max(this.getY(), this.collidePosY),
               Math.max(this.getZ(), this.collidePosZ)
            )
            .inflate(2.0, 2.0, 2.0)
      )) {
         if (entity != this.caster) {
            float pad = entity.getPickRadius() + 0.15F;
            AABB aabb = entity.getBoundingBox().inflate(pad, pad, pad);
            Optional<Vec3> hit = aabb.clip(from, to);
            if (aabb.contains(from)) {
               result.addEntityHit(entity);
            } else if (hit.isPresent()) {
               result.addEntityHit(entity);
            }
         }
      }

      return result;
   }

   public void push(Entity entityIn) {
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean shouldRender(double p_37588_, double p_37589_, double p_37590_) {
      return true;
   }

   public boolean shouldRenderAtSqrDistance(double p_36837_) {
      double d0 = this.getBoundingBox().getSize() * 10.0;
      if (Double.isNaN(d0)) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      return p_36837_ < d0 * d0;
   }

   private void updateWithHarbinger() {
      this.setYaw((float)((this.caster.yHeadRot + this.getBeamDirection()) * Math.PI / 180.0));
      this.setPitch((float)(-this.caster.getXRot() * Math.PI / 180.0));
      float f = -Mth.sin(this.caster.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.caster.getXRot() * (float) (Math.PI / 180.0));
      float f2 = Mth.cos(this.caster.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.caster.getXRot() * (float) (Math.PI / 180.0));
      this.setPos(this.caster.getX(), this.caster.getY() + 0.125, this.caster.getZ());
   }

   public static class LaserbeamHitResult {
      private BlockHitResult blockHit;
      private final List<LivingEntity> entities = new ArrayList<>();

      public BlockHitResult getBlockHit() {
         return this.blockHit;
      }

      public void setBlockHit(HitResult rayTraceResult) {
         if (rayTraceResult.getType() == Type.BLOCK) {
            this.blockHit = (BlockHitResult)rayTraceResult;
         }
      }

      public void addEntityHit(LivingEntity entity) {
         this.entities.add(entity);
      }
   }
}
