package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleOrb;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoRenderPlayer;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerData;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.damage.DamageUtil;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
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
import org.jetbrains.annotations.NotNull;

public class EntitySolarBeam extends Entity {
   public static final double RADIUS_UMVUTHI = 30.0;
   public static final double RADIUS_PLAYER = 20.0;
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
   private static final EntityDataAccessor<Float> YAW = SynchedEntityData.defineId(EntitySolarBeam.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(EntitySolarBeam.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(EntitySolarBeam.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> HAS_PLAYER = SynchedEntityData.defineId(EntitySolarBeam.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(EntitySolarBeam.class, EntityDataSerializers.INT);
   public float prevYaw;
   public float prevPitch;
   private Vec3[] attractorPos;
   private boolean didRaytrace;

   public EntitySolarBeam(EntityType<? extends EntitySolarBeam> type, Level world) {
      super(type, world);
      this.noCulling = true;
      if (world.isClientSide) {
         this.attractorPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }
   }

   public EntitySolarBeam(
      EntityType<? extends EntitySolarBeam> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch, int duration
   ) {
      this(type, world);
      this.caster = caster;
      this.setYaw(yaw);
      this.setPitch(pitch);
      this.setDuration(duration);
      this.setPos(x, y, z);
      this.calculateEndPos();
      MMCommon.PROXY.playSolarBeamSound(this);
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
         if (this.getHasPlayer()) {
            this.updateWithPlayer();
         } else if (this.caster instanceof EntityUmvuthi) {
            this.updateWithUmvuthi();
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

      if (this.level().isClientSide && this.tickCount <= 10 && this.caster != null) {
         int particleCount = 8;

         while (--particleCount != 0) {
            double radius = 2.0F * this.caster.getBbWidth();
            double yaw = this.random.nextFloat() * 2.0F * Math.PI;
            double pitch = this.random.nextFloat() * 2.0F * Math.PI;
            double ox = radius * Math.sin(yaw) * Math.sin(pitch);
            double oy = radius * Math.cos(pitch);
            double oz = radius * Math.cos(yaw) * Math.sin(pitch);
            double rootX = this.caster.getX();
            double rootY = this.caster.getY() + this.caster.getBbHeight() / 2.0F + 0.3F;
            double rootZ = this.caster.getZ();
            if (this.getHasPlayer()) {
               if (this.caster instanceof Player
                  && (this.caster != MMCommon.PROXY.getLocalPlayer() || Minecraft.getInstance().options.getCameraType() != CameraType.FIRST_PERSON)) {
                  GeckoPlayer geckoPlayer = GeckoPlayer.getGeckoPlayer((Player)this.caster, GeckoPlayer.Perspective.THIRD_PERSON);
                  if (geckoPlayer != null) {
                     GeckoRenderPlayer renderPlayer = (GeckoRenderPlayer)geckoPlayer.getPlayerRenderer();
                     if (renderPlayer.betweenHandsPos != null) {
                        rootX += renderPlayer.betweenHandsPos.x();
                        rootY += renderPlayer.betweenHandsPos.y();
                        rootZ += renderPlayer.betweenHandsPos.z();
                     }
                  }
               }

               this.attractorPos[0] = new Vec3(rootX, rootY, rootZ);
            } else if (this.caster instanceof EntityUmvuthi umvuthi) {
               if (umvuthi.headPos != null && umvuthi.headPos[0] != null) {
                  this.attractorPos[0] = ((EntityUmvuthi)this.caster).headPos[0];
                  rootX = this.attractorPos[0].x();
                  rootY = this.attractorPos[0].y();
                  rootZ = this.attractorPos[0].z();
               }
            } else {
               this.attractorPos[0] = new Vec3(rootX, rootY, rootZ);
            }

            AdvancedParticleBase.spawnParticle(
               this.level(),
               ParticleHandler.ORB2,
               rootX + ox,
               rootY + oy,
               rootZ + oz,
               0.0,
               0.0,
               0.0,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               5.0,
               1.0,
               1.0,
               1.0,
               1.0,
               1.0,
               7.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.Attractor(this.attractorPos, 1.7F, 0.0F, ParticleComponent.Attractor.EnumAttractorBehavior.EXPONENTIAL),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                     new ParticleComponent.KeyTrack(new float[]{0.0F, 0.8F}, new float[]{0.0F, 1.0F}),
                     false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     new ParticleComponent.KeyTrack(new float[]{3.0F, 6.0F}, new float[]{0.0F, 1.0F}),
                     false
                  )
               }
            );
         }
      }

      if (this.tickCount > 20) {
         this.calculateEndPos();
         List<Entity> hit = this.raytraceEntities(
               this.level(), new Vec3(this.getX(), this.getY(), this.getZ()), new Vec3(this.endPosX, this.endPosY, this.endPosZ), false, true, true
            )
            .entities;
         if (this.blockSide != null) {
            this.spawnExplosionParticles(2);
         }

         if (!this.level().isClientSide) {
            for (Entity target : hit) {
               if (!(target instanceof ItemEntity) && (!(this.caster instanceof EntityUmvuthi) || !(target instanceof LeaderSunstrikeImmune))) {
                  float damageFire = 1.0F;
                  float damageMob = 1.5F;
                  if (this.caster instanceof EntityUmvuthi) {
                     damageFire = (float)(damageFire * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
                     damageMob = (float)(damageMob * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
                  }

                  if (this.caster instanceof Player) {
                     damageFire = (float)(
                        damageFire * ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get() * 0.75)
                     );
                     damageMob = (float)(damageMob * ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get() * 0.75));
                  }

                  if (target instanceof LivingEntity) {
                     DamageUtil.dealMixedDamage(
                        (LivingEntity)target, this.damageSources().mobProjectile(this, this.caster), damageMob, this.damageSources().onFire(), damageFire
                     );
                  } else {
                     target.hurt(this.damageSources().mobProjectile(this, this.caster), damageMob);
                  }
               }
            }
         } else if (this.tickCount - 15 < this.getDuration()) {
            int particleCount = 4;

            while (particleCount-- > 0) {
               double radius = 1.0;
               double yaw = (float)(this.random.nextFloat() * 2.0F * Math.PI);
               double pitch = (float)(this.random.nextFloat() * 2.0F * Math.PI);
               double ox = (float)(radius * Math.sin(yaw) * Math.sin(pitch));
               double oy = (float)(radius * Math.cos(pitch));
               double oz = (float)(radius * Math.cos(yaw) * Math.sin(pitch));
               double o2x = (float)(-1.0 * Math.cos(this.getYaw()) * Math.cos(this.getPitch()));
               double o2y = (float)(-1.0 * Math.sin(this.getPitch()));
               double o2z = (float)(-1.0 * Math.sin(this.getYaw()) * Math.cos(this.getPitch()));
               this.level()
                  .addParticle(
                     ParticleOrb.Data.create(
                        (float)(this.collidePosX + o2x + ox), (float)(this.collidePosY + o2y + oy), (float)(this.collidePosZ + o2z + oz), 15.0F
                     ),
                     this.getX() + o2x + ox,
                     this.getY() + o2y + oy,
                     this.getZ() + o2z + oz,
                     0.0,
                     0.0,
                     0.0
                  );
            }

            particleCount = 4;

            while (particleCount-- > 0) {
               double radius = 2.0;
               double yaw = this.random.nextFloat() * 2.0F * Math.PI;
               double pitch = this.random.nextFloat() * 2.0F * Math.PI;
               double ox = radius * Math.sin(yaw) * Math.sin(pitch);
               double oy = radius * Math.cos(pitch);
               double oz = radius * Math.cos(yaw) * Math.sin(pitch);
               double o2x = -1.0 * Math.cos(this.getYaw()) * Math.cos(this.getPitch());
               double o2y = -1.0 * Math.sin(this.getPitch());
               double o2z = -1.0 * Math.sin(this.getYaw()) * Math.cos(this.getPitch());
               this.level()
                  .addParticle(
                     ParticleOrb.Data.create(
                        (float)(this.collidePosX + o2x + ox), (float)(this.collidePosY + o2y + oy), (float)(this.collidePosZ + o2z + oz), 20.0F
                     ),
                     this.collidePosX + o2x,
                     this.collidePosY + o2y,
                     this.collidePosZ + o2z,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }

      if (this.tickCount - 20 > this.getDuration()) {
         this.on = false;
      }
   }

   private void spawnExplosionParticles(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 0.1F;
         float yaw = (float)(this.random.nextFloat() * 2.0F * Math.PI);
         float motionY = this.random.nextFloat() * 0.08F;
         float motionX = 0.1F * Mth.cos(yaw);
         float motionZ = 0.1F * Mth.sin(yaw);
         this.level().addParticle(ParticleTypes.FLAME, this.collidePosX, this.collidePosY + 0.1, this.collidePosZ, motionX, motionY, motionZ);
      }

      for (int i = 0; i < amount / 2; i++) {
         this.level().addParticle(ParticleTypes.LAVA, this.collidePosX, this.collidePosY + 0.1, this.collidePosZ, 0.0, 0.0, 0.0);
      }
   }

   protected void defineSynchedData(@NotNull Builder builder) {
      builder.define(YAW, 0.0F);
      builder.define(PITCH, 0.0F);
      builder.define(DURATION, 0);
      builder.define(HAS_PLAYER, false);
      builder.define(CASTER, -1);
   }

   public float getYaw() {
      return (Float)this.getEntityData().get(YAW);
   }

   public void setYaw(float yaw) {
      this.getEntityData().set(YAW, yaw);
   }

   public float getPitch() {
      return (Float)this.getEntityData().get(PITCH);
   }

   public void setPitch(float pitch) {
      this.getEntityData().set(PITCH, pitch);
   }

   public int getDuration() {
      return (Integer)this.getEntityData().get(DURATION);
   }

   public void setDuration(int duration) {
      this.getEntityData().set(DURATION, duration);
   }

   public boolean getHasPlayer() {
      return (Boolean)this.getEntityData().get(HAS_PLAYER);
   }

   public void setHasPlayer(boolean player) {
      this.getEntityData().set(HAS_PLAYER, player);
   }

   public int getCasterID() {
      return (Integer)this.getEntityData().get(CASTER);
   }

   public void setCasterID(int id) {
      this.getEntityData().set(CASTER, id);
   }

   protected void readAdditionalSaveData(CompoundTag nbt) {
   }

   protected void addAdditionalSaveData(CompoundTag nbt) {
   }

   private void calculateEndPos() {
      double radius = this.caster instanceof EntityUmvuthi ? 30.0 : 20.0;
      if (this.level().isClientSide()) {
         this.endPosX = this.getX() + radius * Math.cos(this.renderYaw) * Math.cos(this.renderPitch);
         this.endPosZ = this.getZ() + radius * Math.sin(this.renderYaw) * Math.cos(this.renderPitch);
         this.endPosY = this.getY() + radius * Math.sin(this.renderPitch);
      } else {
         this.endPosX = this.getX() + radius * Math.cos(this.getYaw()) * Math.cos(this.getPitch());
         this.endPosZ = this.getZ() + radius * Math.sin(this.getYaw()) * Math.cos(this.getPitch());
         this.endPosY = this.getY() + radius * Math.sin(this.getPitch());
      }
   }

   public boolean hasDoneRaytrace() {
      return this.didRaytrace;
   }

   public EntitySolarBeam.SolarbeamHitResult raytraceEntities(
      Level world, Vec3 from, Vec3 to, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock
   ) {
      this.didRaytrace = true;
      EntitySolarBeam.SolarbeamHitResult result = new EntitySolarBeam.SolarbeamHitResult();
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

      for (Entity entity : world.getEntitiesOfClass(
         Entity.class,
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

   public boolean isPickable() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      return distance < 1024.0;
   }

   private void updateWithPlayer() {
      this.setYaw((float)((this.caster.yHeadRot + 90.0F) * Math.PI / 180.0));
      this.setPitch((float)(-this.caster.getXRot() * Math.PI / 180.0));
      Vec3 vecOffset = this.caster.getLookAngle().normalize().scale(1.0);
      this.setPos(this.caster.getX() + vecOffset.x(), this.caster.getY() + 1.2F + vecOffset.y(), this.caster.getZ() + vecOffset.z());
   }

   private void updateWithUmvuthi() {
      this.setYaw((float)((this.caster.yHeadRot + 90.0F) * Math.PI / 180.0));
      this.setPitch((float)(-this.caster.getXRot() * Math.PI / 180.0));
      Vec3 vecOffset1 = new Vec3(0.0, 0.0, 0.6).yRot((float)Math.toRadians(-this.caster.getYRot()));
      Vec3 vecOffset2 = new Vec3(1.2, 0.0, 0.0).yRot(-this.getYaw()).xRot(this.getPitch());
      this.setPos(
         this.caster.getX() + vecOffset1.x() + vecOffset2.x(),
         this.caster.getY() + 1.5 + vecOffset1.y() + vecOffset2.y(),
         this.caster.getZ() + vecOffset1.z() + vecOffset2.z()
      );
   }

   public void remove(RemovalReason reason) {
      super.remove(reason);
      if (this.caster instanceof Player) {
         DataHandler.<PlayerData>getData(this.caster, DataHandler.PLAYER_DATA).setUsingSolarBeam(false);
      }
   }

   public static class SolarbeamHitResult {
      private BlockHitResult blockHit;
      private final List<Entity> entities = new ArrayList<>();

      public BlockHitResult getBlockHit() {
         return this.blockHit;
      }

      public void setBlockHit(HitResult rayTraceResult) {
         if (rayTraceResult.getType() == Type.BLOCK) {
            this.blockHit = (BlockHitResult)rayTraceResult;
         }
      }

      public void addEntityHit(Entity entity) {
         this.entities.add(entity);
      }
   }
}
