package com.github.b4ndithelps.tennogamenolife.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DragonRoarCircle extends Entity implements GeoEntity {
   private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> LIFE = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> VFX_TYPE = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> VFX_SEED = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> VFX_DURATION = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> VFX_SECOND_PHASE_DURATION = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Long> VFX_START_GAME_TIME = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.LONG);
   private static final EntityDataAccessor<Float> VFX_START_HEIGHT = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> VFX_ORIGIN_OFFSET_X = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> VFX_ORIGIN_OFFSET_Y = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> VFX_ORIGIN_OFFSET_Z = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> FOLLOW_TARGET_ID = SynchedEntityData.defineId(DragonRoarCircle.class, EntityDataSerializers.INT);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final RawAnimation APPEAR_ANIM = RawAnimation.begin().thenPlay("animation");
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
   private static final RawAnimation DISAPPEAR_ANIM = RawAnimation.begin().thenPlay("disappear");
   private int tickCount = 0;
   private boolean appearAnimFinished = false;
   private boolean isDisappearing = false;
   private static final int DISAPPEAR_DURATION = 3;

   public DragonRoarCircle(EntityType<?> entityType, Level level) {
      super(entityType, level);
      this.noPhysics = true;
      this.noCulling = true;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(SIZE, 16.0F);
      builder.define(LIFE, 120);
      builder.define(VFX_TYPE, 0);
      builder.define(VFX_SEED, 0);
      builder.define(VFX_DURATION, 100);
      builder.define(VFX_SECOND_PHASE_DURATION, 0);
      builder.define(VFX_START_GAME_TIME, 0L);
      builder.define(VFX_START_HEIGHT, 200.0F);
      builder.define(VFX_ORIGIN_OFFSET_X, 0.0F);
      builder.define(VFX_ORIGIN_OFFSET_Y, 200.0F);
      builder.define(VFX_ORIGIN_OFFSET_Z, 0.0F);
      builder.define(FOLLOW_TARGET_ID, -1);
   }

   public void setSize(float size) {
      this.entityData.set(SIZE, size);
   }

   public float getSize() {
      return (Float)this.entityData.get(SIZE);
   }

   public void setLife(int life) {
      this.entityData.set(LIFE, life);
   }

   public int getLife() {
      return (Integer)this.entityData.get(LIFE);
   }

   public void increaseLife(int amount) {
      this.setLife(this.getLife() + amount);
   }

   public void setStrikeVfx(boolean strikeVfx) {
      this.entityData.set(VFX_TYPE, strikeVfx ? 1 : 0);
      this.tickCount = 0;
   }

   public boolean isStrikeVfx() {
      return (Integer)this.entityData.get(VFX_TYPE) == 1;
   }

   public void setExpansionVfx() {
      this.entityData.set(VFX_TYPE, 2);
      this.tickCount = 0;
   }

   public boolean isExpansionVfx() {
      return (Integer)this.entityData.get(VFX_TYPE) == 2;
   }

   public void setBarrierSphereVfx() {
      this.entityData.set(VFX_TYPE, 3);
      this.tickCount = 0;
   }

   public boolean isBarrierSphereVfx() {
      return (Integer)this.entityData.get(VFX_TYPE) == 3;
   }

   public void setGodlySmiteChargeVfx() {
      this.entityData.set(VFX_TYPE, 4);
      this.tickCount = 0;
   }

   public boolean isGodlySmiteChargeVfx() {
      return (Integer)this.entityData.get(VFX_TYPE) == 4;
   }

   public void setGodlySmiteStrikeVfx() {
      this.entityData.set(VFX_TYPE, 5);
      this.tickCount = 0;
   }

   public boolean isGodlySmiteStrikeVfx() {
      return (Integer)this.entityData.get(VFX_TYPE) == 5;
   }

   public void setFollowTargetId(int entityId) {
      this.entityData.set(FOLLOW_TARGET_ID, entityId);
   }

   public int getFollowTargetId() {
      return (Integer)this.entityData.get(FOLLOW_TARGET_ID);
   }

   public void setVfxSeed(int seed) {
      this.entityData.set(VFX_SEED, seed);
   }

   public int getVfxSeed() {
      return (Integer)this.entityData.get(VFX_SEED);
   }

   public void setVfxDuration(int duration) {
      this.entityData.set(VFX_DURATION, duration);
   }

   public int getVfxDuration() {
      return (Integer)this.entityData.get(VFX_DURATION);
   }

   public void setVfxSecondPhaseDuration(int duration) {
      this.entityData.set(VFX_SECOND_PHASE_DURATION, duration);
   }

   public int getVfxSecondPhaseDuration() {
      return (Integer)this.entityData.get(VFX_SECOND_PHASE_DURATION);
   }

   public void setVfxStartGameTime(long gameTime) {
      this.entityData.set(VFX_START_GAME_TIME, gameTime);
   }

   public long getVfxStartGameTime() {
      return (Long)this.entityData.get(VFX_START_GAME_TIME);
   }

   public void setVfxStartHeight(float height) {
      this.entityData.set(VFX_START_HEIGHT, height);
   }

   public float getVfxStartHeight() {
      return (Float)this.entityData.get(VFX_START_HEIGHT);
   }

   public void setVfxOriginOffset(Vec3 offset) {
      this.entityData.set(VFX_ORIGIN_OFFSET_X, (float)offset.x);
      this.entityData.set(VFX_ORIGIN_OFFSET_Y, (float)offset.y);
      this.entityData.set(VFX_ORIGIN_OFFSET_Z, (float)offset.z);
   }

   public Vec3 getVfxOriginOffset() {
      return new Vec3(
         ((Float)this.entityData.get(VFX_ORIGIN_OFFSET_X)).floatValue(),
         ((Float)this.entityData.get(VFX_ORIGIN_OFFSET_Y)).floatValue(),
         ((Float)this.entityData.get(VFX_ORIGIN_OFFSET_Z)).floatValue()
      );
   }

   public int getVisualAge() {
      return this.tickCount;
   }

   public void tick() {
      super.tick();
      int followTargetId = this.getFollowTargetId();
      if (followTargetId >= 0) {
         Entity target = this.level().getEntity(followTargetId);
         if (target != null) {
            Vec3 center = target.getBoundingBox().getCenter();
            this.setPos(center.x, center.y, center.z);
            this.yRotO = target.yRotO;
            this.setYRot(target.getYRot());
         } else if (!this.level().isClientSide()) {
            this.discard();
            return;
         }
      }

      this.tickCount++;
      if (this.tickCount >= 3 && !this.appearAnimFinished) {
         this.appearAnimFinished = true;
      }

      int life = this.getLife();
      if (life > 0) {
         this.setLife(life - 1);
      } else if (!this.isDisappearing) {
         this.isDisappearing = true;
         this.tickCount = 0;
      }

      if (this.isDisappearing && this.tickCount >= 3) {
         this.discard();
      }
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      return distance < 65536.0;
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      if (tag.contains("Size")) {
         this.setSize(tag.getFloat("Size"));
      }

      if (tag.contains("Life")) {
         this.setLife(tag.getInt("Life"));
      }

      if (tag.contains("TickCount")) {
         this.tickCount = tag.getInt("TickCount");
      }

      if (tag.contains("AppearAnimFinished")) {
         this.appearAnimFinished = tag.getBoolean("AppearAnimFinished");
      }

      if (tag.contains("IsDisappearing")) {
         this.isDisappearing = tag.getBoolean("IsDisappearing");
      }

      if (tag.contains("VfxType")) {
         this.entityData.set(VFX_TYPE, tag.getInt("VfxType"));
      } else {
         this.entityData.set(VFX_TYPE, tag.getBoolean("StrikeVfx") ? 1 : 0);
      }

      this.setVfxSeed(tag.getInt("VfxSeed"));
      if (tag.contains("VfxDuration")) {
         this.setVfxDuration(tag.getInt("VfxDuration"));
      }

      if (tag.contains("VfxSecondPhaseDuration")) {
         this.setVfxSecondPhaseDuration(tag.getInt("VfxSecondPhaseDuration"));
      }

      if (tag.contains("VfxStartGameTime")) {
         this.setVfxStartGameTime(tag.getLong("VfxStartGameTime"));
      }

      if (tag.contains("VfxStartHeight")) {
         this.setVfxStartHeight(tag.getFloat("VfxStartHeight"));
      }

      if (tag.contains("VfxOriginOffsetX")) {
         this.setVfxOriginOffset(new Vec3(tag.getFloat("VfxOriginOffsetX"), tag.getFloat("VfxOriginOffsetY"), tag.getFloat("VfxOriginOffsetZ")));
      }

      if (tag.contains("FollowTargetId")) {
         this.setFollowTargetId(tag.getInt("FollowTargetId"));
      }
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      tag.putFloat("Size", this.getSize());
      tag.putInt("Life", this.getLife());
      tag.putInt("TickCount", this.tickCount);
      tag.putBoolean("AppearAnimFinished", this.appearAnimFinished);
      tag.putBoolean("IsDisappearing", this.isDisappearing);
      tag.putInt("VfxType", (Integer)this.entityData.get(VFX_TYPE));
      tag.putBoolean("StrikeVfx", this.isStrikeVfx());
      tag.putInt("VfxSeed", this.getVfxSeed());
      tag.putInt("VfxDuration", this.getVfxDuration());
      tag.putInt("VfxSecondPhaseDuration", this.getVfxSecondPhaseDuration());
      tag.putLong("VfxStartGameTime", this.getVfxStartGameTime());
      tag.putFloat("VfxStartHeight", this.getVfxStartHeight());
      Vec3 originOffset = this.getVfxOriginOffset();
      tag.putFloat("VfxOriginOffsetX", (float)originOffset.x);
      tag.putFloat("VfxOriginOffsetY", (float)originOffset.y);
      tag.putFloat("VfxOriginOffsetZ", (float)originOffset.z);
      tag.putInt("FollowTargetId", this.getFollowTargetId());
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, this::predicate));
   }

   private PlayState predicate(AnimationState<DragonRoarCircle> state) {
      if (this.isDisappearing) {
         state.getController().setAnimation(DISAPPEAR_ANIM);
         return PlayState.CONTINUE;
      } else if (!this.appearAnimFinished) {
         state.getController().setAnimation(APPEAR_ANIM);
         return PlayState.CONTINUE;
      } else {
         state.getController().setAnimation(IDLE_ANIM);
         return PlayState.CONTINUE;
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
