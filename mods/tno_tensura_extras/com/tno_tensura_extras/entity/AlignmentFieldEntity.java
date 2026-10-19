package com.tno_tensura_extras.entity;

import com.tno_tensura_extras.util.AlignmentFieldManager;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AlignmentFieldEntity extends Entity {
   public static final int FADE_TICKS = 30;
   public static final float BASE_ALPHA = 0.5F;
   private static final float MAX_HALF_HEIGHT = 64.0F;
   private static final float MIN_HALF_HEIGHT = 6.0F;
   private static final int EFFECT_INTERVAL = 5;
   private static final int DAMAGE_INTERVAL = 20;
   private static final float UNDEAD_DAMAGE_PER_SECOND = 40.0F;
   private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(AlignmentFieldEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> FADE = SynchedEntityData.defineId(AlignmentFieldEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(AlignmentFieldEntity.class, EntityDataSerializers.INT);
   private int keepAlive;
   private float renderRadius = -1.0F;
   private float previousRenderRadius = -1.0F;

   public AlignmentFieldEntity(EntityType<? extends AlignmentFieldEntity> entityType, Level level) {
      super(entityType, level);
      this.noPhysics = true;
      this.noCulling = true;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(RADIUS, 1.0F);
      builder.define(FADE, 0);
      builder.define(OWNER_ID, -1);
   }

   public float getRadius() {
      return (Float)this.entityData.get(RADIUS);
   }

   public void setRadius(float radius) {
      this.entityData.set(RADIUS, radius);
   }

   public float getHalfHeight() {
      return halfHeightFor(this.getRadius());
   }

   private static float halfHeightFor(float radius) {
      return Math.min(64.0F, Math.max(6.0F, radius));
   }

   public float getRenderRadius(float partialTick) {
      return this.previousRenderRadius < 0.0F ? this.getRadius() : this.previousRenderRadius + (this.renderRadius - this.previousRenderRadius) * partialTick;
   }

   public float getRenderHalfHeight(float partialTick) {
      return halfHeightFor(this.getRenderRadius(partialTick));
   }

   public boolean isFading() {
      return (Integer)this.entityData.get(FADE) > 0;
   }

   public void startFading() {
      if (!this.isFading()) {
         this.entityData.set(FADE, 30);
      }
   }

   public float getFadeProgress(float partialTick) {
      int fade = (Integer)this.entityData.get(FADE);
      return fade <= 0 ? 1.0F : Math.max(0.0F, (fade - partialTick) / 30.0F);
   }

   public void setOwner(@Nullable Entity owner) {
      this.entityData.set(OWNER_ID, owner == null ? -1 : owner.getId());
   }

   @Nullable
   public Entity getOwner() {
      int id = (Integer)this.entityData.get(OWNER_ID);
      return id < 0 ? null : this.level().getEntity(id);
   }

   public void keepAlive() {
      this.keepAlive = 10;
   }

   public boolean isBlocking() {
      return !this.isFading() && !this.isRemoved() && this.getRadius() > 0.0F;
   }

   public boolean contains(Vec3 point) {
      double dx = point.x - this.getX();
      double dz = point.z - this.getZ();
      float radius = this.getRadius();
      if (dx * dx + dz * dz > radius * radius) {
         return false;
      }

      float halfHeight = this.getHalfHeight();
      return point.y >= this.getY() - halfHeight && point.y <= this.getY() + halfHeight;
   }

   public void onAddedToLevel() {
      super.onAddedToLevel();
      AlignmentFieldManager.register(this);
   }

   public void onRemovedFromLevel() {
      super.onRemovedFromLevel();
      AlignmentFieldManager.unregister(this);
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         this.updateRenderRadius();
      }

      int fade = (Integer)this.entityData.get(FADE);
      if (fade > 0) {
         if (!this.level().isClientSide()) {
            if (fade <= 1) {
               this.discard();
               return;
            }

            this.entityData.set(FADE, fade - 1);
         }
      } else if (!this.level().isClientSide()) {
         if (--this.keepAlive <= 0) {
            this.startFading();
         } else {
            this.applyFieldEffects();
         }
      }
   }

   private void updateRenderRadius() {
      float target = this.getRadius();
      if (this.renderRadius < 0.0F) {
         this.renderRadius = target;
         this.previousRenderRadius = target;
      } else {
         this.previousRenderRadius = this.renderRadius;
         this.renderRadius = this.renderRadius + (target - this.renderRadius) * 0.35F;
      }
   }

   private void applyFieldEffects() {
      boolean effectTick = this.tickCount % 5 == 0;
      boolean damageTick = this.tickCount % 20 == 0;
      if (effectTick || damageTick) {
         Entity owner = this.getOwner();

         for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, this.getAffectedArea())) {
            if (target.isAlive()
               && !target.isSpectator()
               && this.contains(target.getBoundingBox().getCenter())
               && !(target instanceof Player player && player.isCreative())) {
               if (effectTick) {
                  target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_MAGIC), 10, 0, true, false, true), owner);
               }

               if (damageTick && target.getType().is(EntityTypeTags.UNDEAD)) {
                  target.hurt(this.damageSources().indirectMagic(this, owner), 40.0F);
               }
            }
         }
      }
   }

   public AABB getAffectedArea() {
      float radius = this.getRadius();
      float halfHeight = this.getHalfHeight();
      return new AABB(
         this.getX() - radius, this.getY() - halfHeight, this.getZ() - radius, this.getX() + radius, this.getY() + halfHeight, this.getZ() + radius
      );
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      return true;
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      this.setRadius(tag.getFloat("Radius"));
      this.entityData.set(FADE, tag.getInt("Fade"));
      this.keepAlive = tag.getInt("KeepAlive");
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      tag.putFloat("Radius", this.getRadius());
      tag.putInt("Fade", (Integer)this.entityData.get(FADE));
      tag.putInt("KeepAlive", this.keepAlive);
   }
}
