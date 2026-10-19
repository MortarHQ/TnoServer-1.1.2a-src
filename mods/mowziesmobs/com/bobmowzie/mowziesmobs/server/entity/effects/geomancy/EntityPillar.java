package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.sound.IGeomancyRumbler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityMagicEffect;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class EntityPillar extends EntityGeomancyBase implements IGeomancyRumbler {
   private static final EntityDataAccessor<Float> HEIGHT = SynchedEntityData.defineId(EntityPillar.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Boolean> RISING = SynchedEntityData.defineId(EntityPillar.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> FALLING = SynchedEntityData.defineId(EntityPillar.class, EntityDataSerializers.BOOLEAN);
   public static final float RISING_SPEED = 0.25F;
   public float prevPrevHeight = 0.0F;
   public float prevHeight = 0.0F;
   public List<Entity> popUpEntities = Collections.emptyList();
   public static final HashMap<EntityGeomancyBase.GeomancyTier, Integer> SIZE_MAP = new HashMap<>();
   private EntityPillarPiece currentPiece;

   public EntityPillar(EntityType<? extends EntityMagicEffect> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityPillar(EntityType<? extends EntityPillar> type, Level world, LivingEntity caster, BlockState blockState, BlockPos pos) {
      super(type, world, caster, blockState, pos);
      this.setDeathTime(300);
   }

   public boolean checkCanSpawn() {
      return !this.level().getEntitiesOfClass(EntityPillar.class, this.getBoundingBox().deflate(0.01)).isEmpty()
         ? false
         : this.level().noCollision(this, this.getBoundingBox().deflate(0.01));
   }

   public boolean canCollideWith(Entity p_20303_) {
      return false;
   }

   @Override
   public boolean canBeCollidedWith() {
      return false;
   }

   @Override
   public void tick() {
      this.prevPrevHeight = this.prevHeight;
      this.prevHeight = this.getHeight();
      if (this.firstTick) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK_LARGE_1.get(), 2.0F, 1.0F);
         if (!this.isFalling()) {
            this.startRising();
         }

         if (this.level().isClientSide()) {
            MMCommon.PROXY.playGeomancyRumbleSound(this);
         }
      }

      if (!this.level().isClientSide()) {
         if (this.isRising()) {
            float height = this.getHeight();
            if (height == 0.0) {
               this.currentPiece = new EntityPillarPiece(
                  (EntityType<?>)EntityHandler.PILLAR_PIECE.get(), this.level(), this, new Vec3(this.getX(), this.getY() - 1.0, this.getZ())
               );
               this.level().addFreshEntity(this.currentPiece);
            }

            height += 0.25F;
            this.setHeight(height);
            if (Math.floor(height) > Math.floor(this.prevHeight)) {
               this.currentPiece = new EntityPillarPiece(
                  (EntityType<?>)EntityHandler.PILLAR_PIECE.get(),
                  this.level(),
                  this,
                  new Vec3(this.getX(), this.getY() + Math.floor(height) - 1.0, this.getZ())
               );
               this.level().addFreshEntity(this.currentPiece);
            }

            if (!(this.getCaster() instanceof EntitySculptor)) {
               for (EntityBoulderProjectile boulder : this.level().getEntitiesOfClass(EntityBoulderProjectile.class, this.getBoundingBox().deflate(0.1F))) {
                  if (!boulder.isTravelling() && boulder.getTier().ordinal() > this.getTier().ordinal()) {
                     this.setTier(boulder.getTier());
                     boulder.explode();
                  }
               }
            }
         } else if (this.isFalling()) {
            float height = this.getHeight();
            height -= 0.25F;
            this.setHeight(height);
            if (height <= 0.0) {
               this.remove(RemovalReason.DISCARDED);
            }
         }
      }

      this.setBoundingBox(this.makeBoundingBox());
      if (this.isRising()) {
         AABB popUpBounds = this.getBoundingBox().deflate(0.1F).inflate(0.0, 1.0, 0.0);
         this.popUpEntities = this.level().getEntities(this, popUpBounds);

         for (Entity entity : this.popUpEntities) {
            if (entity.isPickable() && !(entity instanceof EntityBoulderBase) && !(entity instanceof EntityPillar) && !(entity instanceof EntityPillarPiece)) {
               double belowAmount = entity.getY() - (this.getY() + this.getHeight());
               if (belowAmount < 0.0) {
                  entity.move(MoverType.PISTON, new Vec3(0.0, -belowAmount, 0.0));
               } else {
                  entity.move(MoverType.PISTON, new Vec3(0.0, 0.1, 0.0));
               }
            }
         }
      }

      super.tick();
      if (!this.level().isClientSide() && (this.getCaster() == null || this.getCaster().isRemoved() || this.getCaster().getHealth() <= 0.0)) {
         this.explode();
      }
   }

   protected AABB makeBoundingBox() {
      if (this.tickCount <= 1) {
         return super.makeBoundingBox();
      }

      float f = SIZE_MAP.get(this.getTier()).intValue() / 2.0F - 0.05F;
      return new AABB(this.getX() - f, this.getY(), this.getZ() - f, this.getX() + f, this.getY() + this.getHeight() - 0.05F, this.getZ() + f);
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(HEIGHT, 0.0F);
      builder.define(RISING, true);
      builder.define(FALLING, false);
   }

   public float getHeight() {
      return (Float)this.getEntityData().get(HEIGHT);
   }

   public void setHeight(float height) {
      this.getEntityData().set(HEIGHT, height);
   }

   public void stopRising() {
      this.getEntityData().set(RISING, false);
      this.setBoundingBox(this.makeBoundingBox());
      this.currentPiece = new EntityPillarPiece(
         (EntityType<?>)EntityHandler.PILLAR_PIECE.get(), this.level(), this, new Vec3(this.getX(), this.getY() + this.getHeight() - 1.0, this.getZ())
      );
      this.level().addFreshEntity(this.currentPiece);
   }

   public boolean isRising() {
      return (Boolean)this.getEntityData().get(RISING);
   }

   public void startFalling() {
      this.getEntityData().set(RISING, false);
      this.getEntityData().set(FALLING, true);
   }

   public void startRising() {
      this.getEntityData().set(RISING, true);
      this.getEntityData().set(FALLING, false);
   }

   public boolean isFalling() {
      return (Boolean)this.getEntityData().get(FALLING);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putFloat("height", this.getHeight());
      compound.putBoolean("rising", this.isRising());
      compound.putBoolean("falling", this.isFalling());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setHeight(compound.getFloat("height"));
      this.getEntityData().set(RISING, compound.getBoolean("rising"));
      this.getEntityData().set(FALLING, compound.getBoolean("falling"));
   }

   @Override
   public boolean doRemoveTimer() {
      return !(this.getCaster() instanceof EntitySculptor);
   }

   @Override
   public boolean isRumbling() {
      return !this.isRemoved() && (this.isFalling() || this.isRising());
   }

   @Override
   public boolean isFinishedRumbling() {
      return this.isRemoved();
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
   protected void explode() {
      super.explode();

      for (int i = 0; i < Math.min((this.getTier().ordinal() + 1) * this.getHeight() * 0.25, 30.0); i++) {
         Vec3 particlePos = new Vec3(this.random.nextFloat() * this.getTier().ordinal() + 0.1, 0.0, 0.0);
         particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
         particlePos = particlePos.add(new Vec3(0.0, this.getHeight() * this.random.nextFloat(), 0.0));
         EntityFallingBlock fallingBlock = new EntityFallingBlock((EntityType<?>)EntityHandler.FALLING_BLOCK.get(), this.level(), 70, this.getBlock());
         fallingBlock.setPos(this.getX() + particlePos.x, this.getY() + 0.5 + particlePos.y, this.getZ() + particlePos.z);
         particlePos = particlePos.normalize();
         fallingBlock.setDeltaMovement((float)particlePos.x, 0.2F + this.random.nextFloat() * 0.6F, (float)particlePos.z);
         this.level().addFreshEntity(fallingBlock);
      }
   }

   @Override
   protected void spawnExplosionParticles() {
      float width = this.getTier().ordinal() + 1;

      for (int i = 0; i < 10.0F * width * this.getHeight(); i++) {
         Vec3 particlePos = new Vec3(this.random.nextFloat() * 0.7 * width, 0.0, 0.0);
         particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
         particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
         particlePos = particlePos.add(0.0, this.getHeight() * this.random.nextFloat(), 0.0);
         Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
         boolean overrideLimiter = camera.getPosition().distanceToSqr(this.getX(), this.getY(), this.getZ()) < 4096.0;
         this.level()
            .addAlwaysVisibleParticle(
               new BlockParticleOption(ParticleTypes.BLOCK, this.getBlock()),
               overrideLimiter,
               this.getX() + particlePos.x,
               this.getY() + 0.5 + particlePos.y,
               this.getZ() + particlePos.z,
               particlePos.x,
               particlePos.y,
               particlePos.z
            );
      }
   }

   @Override
   protected float fallingBlockCountMultiplier() {
      return 0.0F;
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   static {
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.NONE, 1);
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.SMALL, 2);
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.MEDIUM, 3);
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.LARGE, 4);
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.HUGE, 5);
   }

   public static class EntityPillarSculptor extends EntityPillar {
      public EntityPillarSculptor(EntityType<? extends EntityPillar.EntityPillarSculptor> type, Level worldIn) {
         super(type, worldIn);
      }

      public EntityPillarSculptor(
         EntityType<? extends EntityPillar.EntityPillarSculptor> type, Level world, LivingEntity caster, BlockState blockState, BlockPos pos
      ) {
         super(type, world, caster, blockState, pos);
         this.setDeathTime(300);
      }

      public double getDesiredHeight() {
         return EntitySculptor.TEST_HEIGHT;
      }

      @Override
      public void tick() {
         if (this.getCaster() instanceof EntitySculptor) {
            EntitySculptor sculptor = (EntitySculptor)this.getCaster();
            if (sculptor.getPillar() == null) {
               sculptor.setPillar(this);
            }

            if (this.level().getBlockState(this.getCaster().blockPosition().above((int)(this.getCaster().getBbHeight() + 1.0F))).blocksMotion()) {
               this.stopRising();
            }

            if (!this.level().isClientSide() && !sculptor.isFighting() && !sculptor.isTesting()) {
               this.startFalling();
            }
         }

         super.tick();
         if (this.getHeight() >= this.getDesiredHeight() && this.isRising()) {
            this.stopRising();
         }
      }

      @Override
      public void stopRising() {
         super.stopRising();
         if (this.getCaster() instanceof EntitySculptor sculptor) {
            sculptor.setPos(this.position().add(0.0, this.getHeight(), 0.0));
         }
      }
   }
}
