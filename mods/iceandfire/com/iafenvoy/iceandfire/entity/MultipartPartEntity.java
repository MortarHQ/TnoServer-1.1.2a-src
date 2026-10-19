package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.network.payload.MultipartInteractC2SPayload;
import dev.architectury.networking.NetworkManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class MultipartPartEntity extends Entity implements OwnableEntity {
   private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID = SynchedEntityData.defineId(
      MultipartPartEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private static final EntityDataAccessor<Float> SCALE_WIDTH = SynchedEntityData.defineId(MultipartPartEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> SCALE_HEIGHT = SynchedEntityData.defineId(MultipartPartEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> PART_YAW = SynchedEntityData.defineId(MultipartPartEntity.class, EntityDataSerializers.FLOAT);
   public EntityDimensions multipartSize;
   protected float radius;
   protected float angleYaw;
   protected float offsetY;
   protected float damageMultiplier;

   protected MultipartPartEntity(EntityType<?> t, Level world) {
      super(t, world);
      this.multipartSize = t.getDimensions();
   }

   protected MultipartPartEntity(EntityType<?> t, Entity parent, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
      super(t, parent.level());
      this.setParent(parent);
      this.setScaleX(sizeX);
      this.setScaleY(sizeY);
      this.radius = radius;
      this.angleYaw = (angleYaw + 90.0F) * (float) (Math.PI / 180.0);
      this.offsetY = offsetY;
      this.damageMultiplier = damageMultiplier;
   }

   public static Builder bakeAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2.0).add(Attributes.MOVEMENT_SPEED, 0.1);
   }

   public static boolean sharesRider(Entity parent, Entity entityIn) {
      for (Entity entity : parent.getPassengers()) {
         if (entity.equals(entityIn)) {
            return true;
         }

         if (sharesRider(entity, entityIn)) {
            return true;
         }
      }

      return false;
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
   }

   protected void doWaterSplashEffect() {
   }

   public EntityDimensions getDimensions(Pose poseIn) {
      return EntityDimensions.scalable(this.getScaleX(), this.getScaleY());
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      builder.define(PARENT_UUID, Optional.empty());
      builder.define(SCALE_WIDTH, 0.5F);
      builder.define(SCALE_HEIGHT, 0.5F);
      builder.define(PART_YAW, 0.0F);
   }

   public UUID getParentId() {
      return (UUID)((Optional)this.entityData.get(PARENT_UUID)).orElse(null);
   }

   public void setParentId(UUID uniqueId) {
      this.entityData.set(PARENT_UUID, Optional.ofNullable(uniqueId));
   }

   private float getScaleX() {
      return (Float)this.entityData.get(SCALE_WIDTH);
   }

   protected void setScaleX(float scale) {
      this.entityData.set(SCALE_WIDTH, scale);
   }

   private float getScaleY() {
      return (Float)this.entityData.get(SCALE_HEIGHT);
   }

   protected void setScaleY(float scale) {
      this.entityData.set(SCALE_HEIGHT, scale);
   }

   public float getPartYaw() {
      return (Float)this.entityData.get(PART_YAW);
   }

   private void setPartYaw(float yaw) {
      this.entityData.set(PART_YAW, yaw % 360.0F);
   }

   public void tick() {
      this.wasTouchingWater = false;
      if (this.tickCount > 10) {
         Entity parent = this.getParent();
         this.refreshDimensions();
         if (parent != null && !this.level().isClientSide) {
            float renderYawOffset = parent.getYRot();
            if (parent instanceof LivingEntity living) {
               renderYawOffset = living.yBodyRot;
            }

            if (this.isSlowFollow()) {
               this.setPos(
                  parent.xo + this.radius * Mth.cos((float)(renderYawOffset * (Math.PI / 180.0) + this.angleYaw)),
                  parent.yo + this.offsetY,
                  parent.zo + this.radius * Mth.sin((float)(renderYawOffset * (Math.PI / 180.0) + this.angleYaw))
               );
               double d0 = parent.getX() - this.getX();
               double d1 = parent.getY() - this.getY();
               double d2 = parent.getZ() - this.getZ();
               float f2 = -((float)(Mth.atan2(d1, Mth.sqrt((float)(d0 * d0 + d2 * d2))) * 180.0F / (float)Math.PI));
               this.setXRot(this.limitAngle(this.getXRot(), f2));
               this.markHurt();
               this.setYRot(renderYawOffset);
               this.setPartYaw(this.getYRot());
               if (!this.level().isClientSide) {
                  this.collideWithNearbyEntities();
               }
            } else {
               this.setPos(
                  parent.getX() + this.radius * Mth.cos((float)(renderYawOffset * (Math.PI / 180.0) + this.angleYaw)),
                  parent.getY() + this.offsetY,
                  parent.getZ() + this.radius * Mth.sin((float)(renderYawOffset * (Math.PI / 180.0) + this.angleYaw))
               );
               this.markHurt();
            }

            if (!this.level().isClientSide) {
               this.collideWithNearbyEntities();
            }

            if (parent.isRemoved() && !this.level().isClientSide) {
               this.remove(RemovalReason.DISCARDED);
            }
         } else if (this.tickCount > 20 && !this.level().isClientSide) {
            this.remove(RemovalReason.DISCARDED);
         }
      }

      super.tick();
   }

   protected boolean isSlowFollow() {
      return false;
   }

   protected float limitAngle(float sourceAngle, float targetAngle) {
      float f = Mth.wrapDegrees(targetAngle - sourceAngle);
      if (f > 5.0F) {
         f = 5.0F;
      }

      if (f < -5.0F) {
         f = -5.0F;
      }

      float f1 = sourceAngle + f;
      if (f1 < 0.0F) {
         f1 += 360.0F;
      } else if (f1 > 360.0F) {
         f1 -= 360.0F;
      }

      return f1;
   }

   public void remove(RemovalReason reason) {
      super.remove(RemovalReason.DISCARDED);
   }

   public Entity getParent() {
      UUID id = this.getParentId();
      return id != null && this.level() instanceof ServerLevel serverLevel ? serverLevel.getEntity(id) : null;
   }

   public void setParent(Entity entity) {
      this.setParentId(entity.getUUID());
   }

   public boolean is(Entity entity) {
      return this == entity || this.getParent() == entity;
   }

   public boolean isPickable() {
      return true;
   }

   public void collideWithNearbyEntities() {
      List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(0.2F, 0.0, 0.2F));
      Entity parent = this.getParent();
      if (parent != null) {
         entities.stream()
            .filter(entity -> entity != parent && !sharesRider(parent, entity) && !(entity instanceof MultipartPartEntity) && entity.isPushable())
            .forEach(entity -> entity.push(parent));
      }
   }

   public boolean hurt(DamageSource source, float damage) {
      Entity parent = this.getParent();
      if (this.level().isClientSide && this.getParentId() != null && source.getEntity() instanceof Player) {
         NetworkManager.sendToServer(new MultipartInteractC2SPayload(this.getParentId(), damage * this.damageMultiplier));
      }

      return parent != null && parent.hurt(source, damage * this.damageMultiplier);
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return source.is(DamageTypes.FALL)
         || source.is(DamageTypes.DROWN)
         || source.is(DamageTypes.IN_WALL)
         || source.is(DamageTypes.FALLING_BLOCK)
         || source.is(DamageTypes.LAVA)
         || source.is(DamageTypeTags.IS_FIRE)
         || super.isInvulnerableTo(source);
   }

   public boolean shouldContinuePersisting() {
      return this.level() != null || this.isRemoved();
   }

   public void copyPosition(Entity entity) {
      super.copyPosition(entity);
      this.setDeltaMovement(entity.getDeltaMovement());
   }

   @Nullable
   public UUID getOwnerUUID() {
      return this.getParent() instanceof OwnableEntity tameable ? tameable.getOwnerUUID() : null;
   }

   public InteractionResult interactAt(Player player, Vec3 hitPos, InteractionHand hand) {
      Entity parent = this.getParent();
      if (this.level().isClientSide && this.getParentId() != null) {
         NetworkManager.sendToServer(new MultipartInteractC2SPayload(this.getParentId(), 0.0F));
         return InteractionResult.SUCCESS;
      } else {
         return parent != null ? parent.interactAt(player, hitPos, hand) : InteractionResult.PASS;
      }
   }
}
