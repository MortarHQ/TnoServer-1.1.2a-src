package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.particle.AdvancedTerrainParticle;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.sound.IGeomancyRumbler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.bluff.EntityBluff;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityMagicEffect;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class EntityFissure extends Projectile implements IGeomancyRumbler {
   public static int TICKS_PER_PIECE = 5;
   private static final EntityDataAccessor<Boolean> TRAVELLING = SynchedEntityData.defineId(EntityFissure.class, EntityDataSerializers.BOOLEAN);
   private int despawnTimer = 0;

   public EntityFissure(EntityType<? extends EntityFissure> type, Level worldIn) {
      super(type, worldIn);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(TRAVELLING, true);
   }

   public boolean isTravelling() {
      return (Boolean)this.getEntityData().get(TRAVELLING);
   }

   public void setTravelling(boolean travelling) {
      this.getEntityData().set(TRAVELLING, travelling);
   }

   public void shoot(double vx, double vz) {
      float speed = 2.0F / TICKS_PER_PIECE;
      Vec3 vec3 = new Vec3(vx, 0.0, vz).normalize().scale(speed);
      this.setDeltaMovement(vec3);
      this.setYRot(-((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI)));
      this.yRotO = this.getYRot();
   }

   public void tick() {
      super.tick();
      if (this.isTravelling()) {
         this.stepForwardTrace();
      }

      if (this.tickCount > 60 && this.isTravelling()) {
         this.spawnSpike();
      }

      if (this.despawnTimer > 0) {
         this.despawnTimer--;
         if (this.despawnTimer == 1) {
            this.discard();
         }
      }

      if (!this.level().isClientSide()) {
         if (this.isTravelling() && this.tickCount % TICKS_PER_PIECE == 1.0F) {
            EntityFissurePiece piece = new EntityFissurePiece((EntityType<?>)EntityHandler.FISSURE_PIECE.get(), this.level());
            piece.setPos(this.position().add(this.getDeltaMovement().scale(TICKS_PER_PIECE / 3.0F)));
            piece.setYRot(this.getYRot());
            piece.setOwner(this);
            this.level().addFreshEntity(piece);
         }

         if (this.isTravelling()
            && !this.level()
               .getEntities(this, this.getBoundingBox().inflate(0.3), e -> e.canBeHitByProjectile() && e != this.getOwner() && !(e instanceof EntityBluff))
               .isEmpty()) {
            this.spawnSpike();
         }
      } else {
         if (this.tickCount == 1) {
            MMCommon.PROXY.playGeomancyRumbleSound(this);
         }

         if (this.isTravelling()) {
            BlockState blockBeneath = this.level().getBlockState(this.getOnPos());

            for (int i = 0; i < 10; i++) {
               Vec3 offset = new Vec3(this.random.nextFloat() * 0.45, 0.0, 0.0).yRot(this.random.nextFloat() * (float) Math.PI * 2.0F);
               Vec3 vel = offset.normalize()
                  .scale(this.random.nextGaussian() * 0.12)
                  .yRot(this.random.nextFloat() * 0.2F - 0.1F)
                  .add(0.0, this.random.nextDouble() * 0.25 + 0.02, 0.0)
                  .add(this.getDeltaMovement().scale(0.4));
               AdvancedTerrainParticle.spawnTerrainParticle(
                  this.level(),
                  ParticleHandler.TERRAIN,
                  this.getX() + offset.x,
                  this.getY(),
                  this.getZ() + offset.z,
                  vel.x,
                  vel.y,
                  vel.z,
                  0.0,
                  0.4F + this.random.nextGaussian() * 0.3,
                  0.94F,
                  20.0F + this.random.nextFloat() * 5.0F,
                  blockBeneath,
                  new ParticleComponent[]{new ParticleComponent.Gravity(1.0F)}
               );
            }
         }
      }
   }

   public void stepForwardTrace() {
      Vec3 forwardPos = this.position().add(this.getDeltaMovement());
      Vec3 startPos = forwardPos.add(0.0, 1.1, 0.0);
      Vec3 endPos = forwardPos.add(0.0, -1.1, 0.0);
      BlockHitResult result = this.level().clip(new ClipContext(startPos, endPos, Block.COLLIDER, Fluid.NONE, this));
      if (result.getType() == Type.BLOCK && !result.isInside()) {
         this.setPos(result.getLocation());
         if (this.level() instanceof ServerLevel) {
            ((ServerLevel)this.level()).getChunkSource().broadcast(this, new ClientboundTeleportEntityPacket(this));
         }
      } else {
         this.spawnSpike();
      }
   }

   private void spawnSpike() {
      if (this.isTravelling()) {
         if (!this.level().isClientSide()) {
            BlockState state = this.level().getBlockState(this.getOnPos());
            if (!EffectGeomancy.isBlockUseable(state)) {
               state = Blocks.DIRT.defaultBlockState();
            }

            EntityEarthSpike spike = new EntityEarthSpike(
               (EntityType<? extends EntityMagicEffect>)EntityHandler.EARTH_SPIKE.get(), this.level(), (LivingEntity)this.getOwner(), state
            );
            spike.setPos(this.position());
            spike.setYRot(this.getYRot());
            this.level().addFreshEntity(spike);
         }

         this.setTravelling(false);
         this.despawnTimer = 180;
         this.setDeltaMovement(0.0, 0.0, 0.0);
      }
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putShort("despawnTimer", (short)this.despawnTimer);
      compound.putBoolean("travelling", this.isTravelling());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.despawnTimer = compound.getShort("despawnTimer");
      this.setTravelling(compound.getBoolean("travelling"));
   }

   @Override
   public boolean isRumbling() {
      return this.isTravelling();
   }

   @Override
   public boolean isFinishedRumbling() {
      return !this.isTravelling();
   }

   @Override
   public float getRumblerX() {
      return (float)this.getX();
   }

   @Override
   public float getRumblerY() {
      return (float)this.getY();
   }

   @Override
   public float getRumblerZ() {
      return (float)this.getZ();
   }

   @Override
   public float getRumblePitch() {
      return 1.3F;
   }

   @Override
   public float getRumbleVolume() {
      return 0.5F;
   }
}
