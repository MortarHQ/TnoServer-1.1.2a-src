package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.client.particle.Options.LightningParticleOptions;
import com.github.L_Ender.cataclysm.client.tool.ControlledAnimation;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.EventHooks;

public class Portal_Abyss_Blast_Entity extends Entity {
   public static final double RADIUS = 50.0;
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
   private static final EntityDataAccessor<Float> YAW = SynchedEntityData.defineId(Portal_Abyss_Blast_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(Portal_Abyss_Blast_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Portal_Abyss_Blast_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(Portal_Abyss_Blast_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Float> BEAMDIRECTION = SynchedEntityData.defineId(Portal_Abyss_Blast_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Portal_Abyss_Blast_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> HPDAMAGE = SynchedEntityData.defineId(Portal_Abyss_Blast_Entity.class, EntityDataSerializers.FLOAT);
   public float prevYaw;
   public float prevPitch;

   public Portal_Abyss_Blast_Entity(EntityType<? extends Portal_Abyss_Blast_Entity> type, Level world) {
      super(type, world);
      this.noCulling = true;
   }

   public Portal_Abyss_Blast_Entity(
      EntityType<? extends Portal_Abyss_Blast_Entity> type,
      Level world,
      LivingEntity caster,
      double x,
      double y,
      double z,
      float yaw,
      float pitch,
      int duration,
      float direction,
      float damage,
      float Hpdamage
   ) {
      this(type, world);
      this.caster = caster;
      this.setYaw(yaw);
      this.setPitch(pitch);
      this.setDuration(duration);
      this.setBeamDirection(direction);
      this.setPos(x, y, z);
      this.setDamage(damage);
      this.setHpDamage(Hpdamage);
      this.calculateEndPos();
      if (!world.isClientSide) {
         this.setCasterID(caster.getId());
      }
   }

   public Portal_Abyss_Blast_Entity(
      EntityType<? extends Portal_Abyss_Blast_Entity> type,
      Level world,
      double x,
      double y,
      double z,
      float yaw,
      float pitch,
      int duration,
      float direction,
      float damage,
      float Hpdamage
   ) {
      this(type, world);
      this.setYaw(yaw);
      this.setPitch(pitch);
      this.setDuration(duration);
      this.setBeamDirection(direction);
      this.setPos(x, y, z);
      this.setDamage(damage);
      this.setHpDamage(Hpdamage);
      this.calculateEndPos();
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
      this.renderYaw = this.getYaw();
      this.renderPitch = this.getPitch();
      this.xo = this.getX();
      this.yo = this.getY();
      this.zo = this.getZ();
      if (this.tickCount == 1 && this.level().isClientSide) {
         this.caster = (LivingEntity)this.level().getEntity(this.getCasterID());
      }

      if (!this.on && this.appear.getTimer() == 0) {
         this.discard();
      }

      if (this.on && this.tickCount > 20) {
         this.appear.increaseTimer();
      } else {
         this.appear.decreaseTimer();
      }

      if (this.tickCount == 20) {
         this.level().playSound((Player)null, this, (SoundEvent)ModSounds.PORTAL_ABYSS_BLAST.get(), SoundSource.HOSTILE, 0.5F, 1.0F);
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
            if (!this.level().isClientSide) {
               for (BlockPos pos : BlockPos.betweenClosed(
                  Mth.floor(this.collidePosX - 0.5),
                  Mth.floor(this.collidePosY - 0.5),
                  Mth.floor(this.collidePosZ - 0.5),
                  Mth.floor(this.collidePosX + 0.5),
                  Mth.floor(this.collidePosY + 0.5),
                  Mth.floor(this.collidePosZ + 0.5)
               )) {
                  BlockState block = this.level().getBlockState(pos);
                  if (!block.isAir() && !block.is(ModTag.LEVIATHAN_IMMUNE) && EventHooks.canEntityGrief(this.level(), this)) {
                     this.level().destroyBlock(pos, false);
                  }
               }
            }
         }

         if (!this.level().isClientSide) {
            for (LivingEntity target : hit) {
               if (this.caster != null && !this.caster.isAlliedTo(target) && target != this.caster) {
                  boolean flag = target.hurt(
                     CMDamageTypes.causeDeathLaserDamage(this, this.caster),
                     (float)(this.getDamage() + Math.min(this.getDamage(), target.getMaxHealth() * this.getHpDamage() * 0.01))
                  );
                  if (flag) {
                     MobEffectInstance effectinstance1 = target.getEffect(ModEffect.EFFECTABYSSAL_BURN);
                     int i = 1;
                     if (effectinstance1 != null) {
                        i += effectinstance1.getAmplifier();
                        target.removeEffectNoUpdate(ModEffect.EFFECTABYSSAL_BURN);
                     } else {
                        i--;
                     }

                     i = Mth.clamp(i, 0, 3);
                     MobEffectInstance effectinstance = new MobEffectInstance(ModEffect.EFFECTABYSSAL_BURN, 160, i, false, true, true);
                     target.addEffect(effectinstance);
                  }
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
      p_326229_.define(DAMAGE, 0.0F);
      p_326229_.define(HPDAMAGE, 0.0F);
   }

   public float getDamage() {
      return (Float)this.entityData.get(DAMAGE);
   }

   public void setDamage(float damage) {
      this.entityData.set(DAMAGE, damage);
   }

   public float getHpDamage() {
      return (Float)this.entityData.get(HPDAMAGE);
   }

   public void setHpDamage(float damage) {
      this.entityData.set(HPDAMAGE, damage);
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

   public int getCasterID() {
      return (Integer)this.entityData.get(CASTER);
   }

   public void setCasterID(int id) {
      this.entityData.set(CASTER, id);
   }

   private void calculateEndPos() {
      if (this.level().isClientSide()) {
         this.endPosX = this.getX() + 50.0 * Math.cos(this.renderYaw) * Math.cos(this.renderPitch);
         this.endPosZ = this.getZ() + 50.0 * Math.sin(this.renderYaw) * Math.cos(this.renderPitch);
         this.endPosY = this.getY() + 50.0 * Math.sin(this.renderPitch);
      } else {
         this.endPosX = this.getX() + 50.0 * Math.cos(this.getYaw()) * Math.cos(this.getPitch());
         this.endPosZ = this.getZ() + 50.0 * Math.sin(this.getYaw()) * Math.cos(this.getPitch());
         this.endPosY = this.getY() + 50.0 * Math.sin(this.getPitch());
      }
   }

   public Portal_Abyss_Blast_Entity.LaserbeamHitResult raytraceEntities(Level world, Vec3 from, Vec3 to) {
      Portal_Abyss_Blast_Entity.LaserbeamHitResult result = new Portal_Abyss_Blast_Entity.LaserbeamHitResult();
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
            .inflate(1.0, 1.0, 1.0)
      )) {
         if (entity != this.caster) {
            float pad = entity.getPickRadius() + 1.3F;
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

   public boolean shouldRenderAtSqrDistance(double p_36837_) {
      double d0 = this.getBoundingBox().getSize() * 10.0;
      if (Double.isNaN(d0)) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      return p_36837_ < d0 * d0;
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
