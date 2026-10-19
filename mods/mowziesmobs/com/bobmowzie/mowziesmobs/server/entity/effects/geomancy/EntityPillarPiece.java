package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityPillarPiece extends Entity {
   private static final EntityDataAccessor<Optional<UUID>> PILLAR = SynchedEntityData.defineId(EntityPillarPiece.class, EntityDataSerializers.OPTIONAL_UUID);
   private static final EntityDataAccessor<Integer> TIER = SynchedEntityData.defineId(EntityPillarPiece.class, EntityDataSerializers.INT);
   private EntityPillar pillar;

   public EntityPillarPiece(EntityType<?> type, Level level) {
      super(type, level);
   }

   public EntityPillarPiece(EntityType<?> type, Level level, EntityPillar pillar, Vec3 position) {
      super(type, level);
      this.pillar = pillar;
      this.setTier(pillar.getTier());
      this.absMoveTo(position.x, position.y, position.z);
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean canCollideWith(Entity entity) {
      return !(entity instanceof EntityPillar) && !(entity instanceof EntityPillarPiece) ? super.canCollideWith(entity) : false;
   }

   public void tick() {
      if (!this.level().isClientSide()) {
         if (this.pillar == null) {
            this.pillar = this.getPillar();
            if (this.pillar == null) {
               this.remove(RemovalReason.DISCARDED);
               return;
            }
         }

         if (this.pillar.isRemoved()) {
            this.remove(RemovalReason.DISCARDED);
            return;
         }

         if (this.pillar.isFalling() && this.pillar.getHeight() + this.pillar.getY() < this.getY() + 1.0) {
            this.remove(RemovalReason.DISCARDED);
            return;
         }

         this.setTier(this.pillar.getTier());
      }

      super.tick();
      this.setBoundingBox(this.makeBoundingBox());
   }

   protected AABB makeBoundingBox() {
      float f = EntityPillar.SIZE_MAP.get(this.getTier()).intValue() / 2.0F;
      return new AABB(this.getX() - f, this.getY(), this.getZ() - f, this.getX() + f, this.getY() + 1.0, this.getZ() + f);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(PILLAR, Optional.empty());
      builder.define(TIER, 0);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      if (compound != null) {
         this.setPillarUUID(compound.getUUID("pillar"));
      }
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      if (this.pillar != null) {
         compound.putUUID("pillar", this.pillar.getUUID());
      }
   }

   public boolean displayFireAnimation() {
      return false;
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.BLOCK;
   }

   public boolean ignoreExplosion(Explosion explosion) {
      return true;
   }

   public Optional<UUID> getPillarUUID() {
      return (Optional<UUID>)this.getEntityData().get(PILLAR);
   }

   public void setPillarUUID(UUID uuid) {
      this.getEntityData().set(PILLAR, Optional.of(uuid));
   }

   public EntityPillar getPillar() {
      Optional<UUID> uuid = this.getPillarUUID();
      return uuid.isPresent() && !this.level().isClientSide ? (EntityPillar)((ServerLevel)this.level()).getEntity(uuid.get()) : null;
   }

   public EntityGeomancyBase.GeomancyTier getTier() {
      return EntityGeomancyBase.GeomancyTier.values()[this.entityData.get(TIER)];
   }

   public void setTier(EntityGeomancyBase.GeomancyTier size) {
      this.entityData.set(TIER, size.ordinal());
   }
}
