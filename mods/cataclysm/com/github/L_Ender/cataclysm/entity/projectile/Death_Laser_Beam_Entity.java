package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.blocks.EMP_Block;
import com.github.L_Ender.cataclysm.client.particle.Options.LightningParticleOptions;
import com.github.L_Ender.cataclysm.client.tool.ControlledAnimation;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.The_Prowler_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.EventHooks;

public class Death_Laser_Beam_Entity extends Entity {
   public static final double RADIUS = 30.0;
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
   private static final EntityDataAccessor<Float> YAW = SynchedEntityData.defineId(Death_Laser_Beam_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(Death_Laser_Beam_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Death_Laser_Beam_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(Death_Laser_Beam_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> HEAD = SynchedEntityData.defineId(Death_Laser_Beam_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> FIRE = SynchedEntityData.defineId(Death_Laser_Beam_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Death_Laser_Beam_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> HPDAMAGE = SynchedEntityData.defineId(Death_Laser_Beam_Entity.class, EntityDataSerializers.FLOAT);
   public float prevYaw;
   public float prevPitch;
   @OnlyIn(Dist.CLIENT)
   private Vec3[] attractorPos;

   public Death_Laser_Beam_Entity(EntityType<? extends Death_Laser_Beam_Entity> type, Level world) {
      super(type, world);
      this.noCulling = true;
      if (world.isClientSide) {
         this.attractorPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }
   }

   public Death_Laser_Beam_Entity(
      EntityType<? extends Death_Laser_Beam_Entity> type,
      Level world,
      LivingEntity caster,
      double x,
      double y,
      double z,
      float yaw,
      float pitch,
      int duration,
      float damage,
      float Hpdamage
   ) {
      this(type, world);
      this.caster = caster;
      this.setYaw(yaw);
      this.setPitch(pitch);
      this.setDuration(duration);
      this.setPos(x, y, z);
      this.setDamage(damage);
      this.setHpDamage(Hpdamage);
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

      if (!this.level().isClientSide) {
         if (this.caster instanceof The_Harbinger_Entity) {
            this.updateWithHarbinger();
         }

         if (this.caster instanceof The_Prowler_Entity) {
            this.updateWithProwler();
         }
      }

      if (this.caster != null) {
         this.renderYaw = (float)((this.caster.yHeadRot + 90.0) * Math.PI / 180.0);
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
               this.level(), new Vec3(this.getX(), this.getY(), this.getZ()), new Vec3(this.endPosX, this.endPosY, this.endPosZ), false, true, true
            )
            .entities;
         if (this.blockSide != null) {
            this.spawnExplosionParticles(5);
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
                  if (!block.isAir() && block.is(ModTag.CM_GLASS) && EventHooks.canEntityGrief(this.level(), this)) {
                     this.level().destroyBlock(pos, true);
                  }
               }

               for (BlockPos pos : BlockPos.betweenClosed(
                  Mth.floor(this.collidePosX - 2.5),
                  Mth.floor(this.collidePosY - 2.5),
                  Mth.floor(this.collidePosZ - 2.5),
                  Mth.floor(this.collidePosX + 2.5),
                  Mth.floor(this.collidePosY + 2.5),
                  Mth.floor(this.collidePosZ + 2.5)
               )) {
                  BlockState block = this.level().getBlockState(pos);
                  if (block.is((Block)ModBlocks.EMP.get()) && !(Boolean)block.getValue(EMP_Block.POWERED) && (Boolean)block.getValue(EMP_Block.OVERLOAD)) {
                     this.level().setBlockAndUpdate(pos, (BlockState)block.setValue(EMP_Block.OVERLOAD, false));
                  }
               }

               if (this.getFire()) {
                  BlockPos blockpos1 = BlockPos.containing(this.collidePosX, this.collidePosY, this.collidePosZ);
                  if (CMCommonConfig.Harbinger.ignoreMobGriefing) {
                     if (this.level().isEmptyBlock(blockpos1)) {
                        this.level().setBlockAndUpdate(blockpos1, BaseFireBlock.getState(this.level(), blockpos1));
                     }
                  } else if (EventHooks.canEntityGrief(this.level(), this) && this.level().isEmptyBlock(blockpos1)) {
                     this.level().setBlockAndUpdate(blockpos1, BaseFireBlock.getState(this.level(), blockpos1));
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
                  if (this.getFire() && flag) {
                     target.igniteForSeconds(5.0F);
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
         float velocity = 1.5F;
         float yaw = (float)(this.random.nextFloat() * 2.0F * Math.PI);
         float motionY = this.random.nextFloat() * 0.8F;
         float motionX = 1.5F * Mth.cos(yaw);
         float motionZ = 1.5F * Mth.sin(yaw);
         this.level()
            .addParticle(new LightningParticleOptions(255, 26, 0), this.collidePosX, this.collidePosY + 0.1, this.collidePosZ, motionX, motionY, motionZ);
      }
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(YAW, 0.0F);
      p_326229_.define(PITCH, 0.0F);
      p_326229_.define(DURATION, 0);
      p_326229_.define(CASTER, -1);
      p_326229_.define(HEAD, 0);
      p_326229_.define(FIRE, false);
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

   public int getHead() {
      return (Integer)this.entityData.get(HEAD);
   }

   public void setHead(int head) {
      this.entityData.set(HEAD, head);
   }

   public int getCasterID() {
      return (Integer)this.entityData.get(CASTER);
   }

   public void setCasterID(int id) {
      this.entityData.set(CASTER, id);
   }

   public boolean getFire() {
      return (Boolean)this.entityData.get(FIRE);
   }

   public void setFire(boolean fire) {
      this.entityData.set(FIRE, fire);
   }

   protected void readAdditionalSaveData(CompoundTag nbt) {
   }

   protected void addAdditionalSaveData(CompoundTag nbt) {
   }

   private void calculateEndPos() {
      if (this.level().isClientSide()) {
         this.endPosX = this.getX() + 30.0 * Math.cos(this.renderYaw) * Math.cos(this.renderPitch);
         this.endPosZ = this.getZ() + 30.0 * Math.sin(this.renderYaw) * Math.cos(this.renderPitch);
         this.endPosY = this.getY() + 30.0 * Math.sin(this.renderPitch);
      } else {
         this.endPosX = this.getX() + 30.0 * Math.cos(this.getYaw()) * Math.cos(this.getPitch());
         this.endPosZ = this.getZ() + 30.0 * Math.sin(this.getYaw()) * Math.cos(this.getPitch());
         this.endPosY = this.getY() + 30.0 * Math.sin(this.getPitch());
      }
   }

   public Death_Laser_Beam_Entity.LaserbeamHitResult raytraceEntities(
      Level world, Vec3 from, Vec3 to, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock
   ) {
      Death_Laser_Beam_Entity.LaserbeamHitResult result = new Death_Laser_Beam_Entity.LaserbeamHitResult();
      result.setBlockHit(world.clip(new ClipContext(from, to, net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, this)));
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
            float pad = entity.getPickRadius() + 0.5F;
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

   public boolean shouldRenderAtSqrDistance(double distance) {
      return distance < 1024.0;
   }

   private void updateWithHarbinger() {
      this.setYaw((float)((this.caster.yHeadRot + 90.0F) * Math.PI / 180.0));
      this.setPitch((float)(-this.caster.getXRot() * Math.PI / 180.0));
      this.setPos(this.caster.getX(), this.caster.getY() + 2.7, this.caster.getZ());
   }

   private void updateWithProwler() {
      this.setYaw((float)((this.caster.yHeadRot + 90.0F) * Math.PI / 180.0));
      this.setPitch((float)(-this.caster.getXRot() * Math.PI / 180.0));
      this.setPos(this.caster.getX(), this.caster.getY() + 1.8, this.caster.getZ());
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
