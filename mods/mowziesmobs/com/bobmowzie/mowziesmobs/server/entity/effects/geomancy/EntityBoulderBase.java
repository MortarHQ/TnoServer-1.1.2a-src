package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.HashMap;
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
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class EntityBoulderBase extends EntityGeomancyBase {
   public BlockState storedBlock;
   public float animationOffset = 0.0F;
   public EntityGeomancyBase.GeomancyTier boulderSize = EntityGeomancyBase.GeomancyTier.SMALL;
   protected int finishedRisingTick = 4;
   public int risingTick = 0;
   private static final EntityDataAccessor<Integer> RISING_TICK = SynchedEntityData.defineId(EntityBoulderBase.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.defineId(EntityBoulderBase.class, EntityDataSerializers.BOOLEAN);
   public static final HashMap<EntityGeomancyBase.GeomancyTier, EntityDimensions> SIZE_MAP = new HashMap<>();

   public EntityBoulderBase(EntityType<? extends EntityBoulderBase> type, Level world) {
      super(type, world);
      this.finishedRisingTick = 4;
      this.animationOffset = this.random.nextFloat() * 8.0F;
      this.setTier(EntityGeomancyBase.GeomancyTier.SMALL);
      this.setBoundingBox(this.makeBoundingBox());
   }

   public EntityBoulderBase(
      EntityType<? extends EntityBoulderBase> type, Level world, LivingEntity caster, BlockState blockState, BlockPos pos, EntityGeomancyBase.GeomancyTier tier
   ) {
      super(type, world, caster, blockState, pos);
      this.finishedRisingTick = 4;
      this.animationOffset = this.random.nextFloat() * 8.0F;
      this.setTier(tier);
      this.setSizeParams();
      this.setBoundingBox(this.makeBoundingBox());
   }

   @Override
   public boolean canBeCollidedWith() {
      return this.isActive();
   }

   public boolean checkCanSpawn() {
      return !this.level().getEntitiesOfClass(EntityBoulderBase.class, this.getBoundingBox().deflate(0.01)).isEmpty()
         ? false
         : this.level().noCollision(this, this.getBoundingBox().deflate(0.01));
   }

   public void setSizeParams() {
      EntityGeomancyBase.GeomancyTier size = this.getTier();
      if (size == EntityGeomancyBase.GeomancyTier.MEDIUM) {
         this.finishedRisingTick = 8;
      } else if (size == EntityGeomancyBase.GeomancyTier.LARGE) {
         this.finishedRisingTick = 12;
      } else if (size == EntityGeomancyBase.GeomancyTier.HUGE) {
         this.finishedRisingTick = 90;
      }
   }

   @NotNull
   protected AABB makeBoundingBox() {
      EntityDimensions dim = SIZE_MAP.get(this.getTier());
      this.dimensions = dim;
      return dim.makeBoundingBox(this.position());
   }

   public void activate() {
      this.getEntityData().set(ACTIVE, true);
   }

   public boolean isActive() {
      return (Boolean)this.getEntityData().get(ACTIVE);
   }

   public boolean isFinishedRising() {
      return this.risingTick >= this.finishedRisingTick;
   }

   protected void doPopupEntities() {
      for (Entity entity : this.level().getEntities(this, this.getBoundingBox())) {
         if (entity.isPickable() && !(entity instanceof EntityBoulderBase) && (!(this.getCaster() instanceof EntitySculptor) || this.getCaster() != entity)) {
            if (this.boulderSize != EntityGeomancyBase.GeomancyTier.HUGE) {
               entity.move(MoverType.SHULKER_BOX, new Vec3(0.0, 2.0 * Math.pow(2.0, -this.risingTick * (0.6 - 0.1 * this.boulderSize.ordinal())), 0.0));
            } else {
               entity.move(MoverType.SHULKER_BOX, new Vec3(0.0, 0.6F, 0.0));
            }
         }
      }
   }

   @Override
   public void tick() {
      if (this.firstTick) {
         this.setSizeParams();
         this.boulderSize = this.getTier();
         this.risingTick = this.getRisingTickData();
      }

      if (this.storedBlock == null) {
         this.storedBlock = this.getBlock();
      }

      super.tick();
      this.setBoundingBox(this.makeBoundingBox());
      this.move(MoverType.SELF, this.getDeltaMovement());
      if (this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE && this.risingTick < this.finishedRisingTick) {
         float f = this.getBbWidth() / 2.0F;
         AABB aabb = new AABB(
            this.getX() - f,
            this.getY() - 0.5,
            this.getZ() - f,
            this.getX() + f,
            this.getY() + Math.min((float)this.risingTick / this.finishedRisingTick * 3.5F, 3.5F),
            this.getZ() + f
         );
         this.setBoundingBox(aabb);
      }

      if (this.isActive() && this.risingTick < this.finishedRisingTick + 2) {
         this.doPopupEntities();
      }

      if (this.risingTick == 1) {
         for (int i = 0; i < 20.0F * this.getBbWidth(); i++) {
            Vec3 particlePos = new Vec3(this.random.nextFloat() * 1.3 * this.getBbWidth(), 0.0, 0.0);
            particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            this.level()
               .addParticle(
                  new BlockParticleOption(ParticleTypes.BLOCK, this.storedBlock),
                  this.getX() + particlePos.x,
                  this.getY() - 1.0,
                  this.getZ() + particlePos.z,
                  particlePos.x,
                  2.0,
                  particlePos.z
               );
         }

         if (this.boulderSize == EntityGeomancyBase.GeomancyTier.SMALL) {
            this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_SMALL_CRASH.get(), 1.5F, 1.3F);
            this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 1.0F);
         } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.MEDIUM) {
            this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_MEDIUM_2.get(), 1.5F, 1.5F);
            this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.8F);
         } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.LARGE) {
            this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_MEDIUM_1.get(), 1.5F, 0.9F);
            this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 1.5F, 1.5F);
            EntityCameraShake.cameraShake(this.level(), this.position(), 10.0F, 0.05F, 0, 20);
         } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE) {
            this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 2.0F, 0.5F);
            this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_RUMBLE_1.get(), 2.0F, 0.8F);
            EntityCameraShake.cameraShake(this.level(), this.position(), 15.0F, 0.05F, 50, 30);
         }

         if (this.level().isClientSide) {
            AdvancedParticleBase.spawnAlwaysVisibleParticle(
               this.level(),
               ParticleHandler.RING2,
               64.0,
               this.getX(),
               this.getY() - 0.9F,
               this.getZ(),
               0.0,
               0.0,
               0.0,
               false,
               0.0,
               Math.PI / 2,
               0.0,
               0.0,
               3.5,
               0.83F,
               1.0,
               0.39F,
               1.0,
               1.0,
               (int)(5.0F + 2.0F * this.getBbWidth()),
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     ParticleComponent.KeyTrack.startAndEnd(0.0F, (1.0F + 0.5F * this.getBbWidth()) * 10.0F),
                     false
                  )
               }
            );
         }
      }

      if (this.risingTick == 30 && this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_RUMBLE_2.get(), 2.0F, 0.7F);
      }

      int dripTick = this.risingTick - 2;
      if (this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE) {
         dripTick -= 20;
      }

      int dripNumber = (int)(this.getBbWidth() * 6.0F * Math.pow(1.03 + 0.04 / this.getBbWidth(), -dripTick));
      if (dripNumber >= 1 && dripTick > 0) {
         dripNumber = (int)(dripNumber * this.random.nextFloat());

         for (int i = 0; i < dripNumber; i++) {
            Vec3 particlePos = new Vec3(this.random.nextFloat() * 0.6 * this.getBbWidth(), 0.0, 0.0);
            particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            float offsetY;
            if (this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE && this.risingTick < this.finishedRisingTick) {
               offsetY = this.random.nextFloat() * (this.getBbHeight() - 1.0F)
                  - this.getBbHeight() * (this.finishedRisingTick - this.risingTick) / this.finishedRisingTick;
            } else {
               offsetY = this.random.nextFloat() * (this.getBbHeight() - 1.0F);
            }

            this.level()
               .addParticle(
                  new BlockParticleOption(ParticleTypes.BLOCK, this.storedBlock),
                  this.getX() + particlePos.x,
                  this.getY() + offsetY,
                  this.getZ() + particlePos.z,
                  0.0,
                  -1.0,
                  0.0
               );
         }
      }

      if (this.isActive()) {
         this.risingTick++;
      }
   }

   @Override
   public void explode() {
      if (this.isActive()) {
         super.explode();
      } else {
         this.discard();
      }
   }

   public void remove(RemovalReason p_146834_) {
      super.remove(p_146834_);
      if (this.getCaster() instanceof EntitySculptor sculptor) {
         sculptor.boulders.remove(this);
      }
   }

   public int getRisingTickData() {
      return (Integer)this.getEntityData().get(RISING_TICK);
   }

   public void setRisingTickData(int risingTick) {
      this.getEntityData().set(RISING_TICK, risingTick);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("risingTick", this.risingTick);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setRisingTickData(compound.getInt("risingTick"));
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(RISING_TICK, 0);
      builder.define(ACTIVE, false);
   }

   static {
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.NONE, EntityDimensions.scalable(1.0F, 1.0F));
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.SMALL, EntityDimensions.scalable(1.0F, 1.0F));
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.MEDIUM, EntityDimensions.scalable(2.0F, 1.5F));
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.LARGE, EntityDimensions.scalable(3.0F, 2.5F));
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.HUGE, EntityDimensions.scalable(4.0F, 3.5F));
   }
}
