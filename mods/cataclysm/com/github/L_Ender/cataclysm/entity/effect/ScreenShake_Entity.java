package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ScreenShake_Entity extends Entity {
   private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(ScreenShake_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> MAGNITUDE = SynchedEntityData.defineId(ScreenShake_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(ScreenShake_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> FADE_DURATION = SynchedEntityData.defineId(ScreenShake_Entity.class, EntityDataSerializers.INT);

   public ScreenShake_Entity(EntityType<?> type, Level world) {
      super(type, world);
   }

   public ScreenShake_Entity(Level world, Vec3 position, float radius, float magnitude, int duration, int fadeDuration) {
      super((EntityType)ModEntities.SCREEN_SHAKE.get(), world);
      this.setRadius(radius);
      this.setMagnitude(magnitude);
      this.setDuration(duration);
      this.setFadeDuration(fadeDuration);
      this.setPos(position.x, position.y, position.z);
   }

   @OnlyIn(Dist.CLIENT)
   public float getShakeAmount(Player player, float delta) {
      float ticksDelta = this.tickCount + delta;
      float timeFrac = 1.0F - (ticksDelta - this.getDuration()) / (this.getFadeDuration() + 1.0F);
      float baseAmount = ticksDelta < this.getDuration() ? this.getMagnitude() : timeFrac * timeFrac * this.getMagnitude();
      Vec3 playerPos = player.getEyePosition(delta);
      float distFrac = (float)(1.0 - Mth.clamp(this.position().distanceTo(playerPos) / this.getRadius(), 0.0, 1.0));
      return baseAmount * distFrac * distFrac;
   }

   public void tick() {
      super.tick();
      if (this.tickCount > this.getDuration() + this.getFadeDuration()) {
         this.discard();
      }
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(RADIUS, 10.0F);
      p_326229_.define(MAGNITUDE, 1.0F);
      p_326229_.define(DURATION, 0);
      p_326229_.define(FADE_DURATION, 5);
   }

   public float getRadius() {
      return (Float)this.entityData.get(RADIUS);
   }

   public void setRadius(float radius) {
      this.entityData.set(RADIUS, radius);
   }

   public float getMagnitude() {
      return (Float)this.entityData.get(MAGNITUDE);
   }

   public void setMagnitude(float magnitude) {
      this.entityData.set(MAGNITUDE, magnitude);
   }

   public int getDuration() {
      return (Integer)this.entityData.get(DURATION);
   }

   public void setDuration(int duration) {
      this.entityData.set(DURATION, duration);
   }

   public int getFadeDuration() {
      return (Integer)this.entityData.get(FADE_DURATION);
   }

   public void setFadeDuration(int fadeDuration) {
      this.entityData.set(FADE_DURATION, fadeDuration);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setRadius(compound.getFloat("radius"));
      this.setMagnitude(compound.getFloat("magnitude"));
      this.setDuration(compound.getInt("duration"));
      this.setFadeDuration(compound.getInt("fade_duration"));
      this.tickCount = compound.getInt("ticks_existed");
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putFloat("radius", this.getRadius());
      compound.putFloat("magnitude", this.getMagnitude());
      compound.putInt("duration", this.getDuration());
      compound.putInt("fade_duration", this.getFadeDuration());
      compound.putInt("ticks_existed", this.tickCount);
   }

   public static void ScreenShake(Level world, Vec3 position, float radius, float magnitude, int duration, int fadeDuration) {
      if (!world.isClientSide) {
         ScreenShake_Entity ScreenShake = new ScreenShake_Entity(world, position, radius, magnitude, duration, fadeDuration);
         world.addFreshEntity(ScreenShake);
      }
   }
}
