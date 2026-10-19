package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityFissurePiece extends Entity {
   public static final float PIECE_SIZE = 2.0F;
   private static final EntityDataAccessor<Integer> GROW_TICK = SynchedEntityData.defineId(EntityFissurePiece.class, EntityDataSerializers.INT);
   @Nullable
   private EntityFissure owner;
   @Nullable
   private UUID ownerUUID;

   public EntityFissurePiece(EntityType<?> type, Level level) {
      super(type, level);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide() && (this.getOwner() == null || this.getOwner().isRemoved())) {
         this.discard();
      }

      if (!this.level().isClientSide() && this.getGrowTick() < EntityFissure.TICKS_PER_PIECE && this.owner != null && this.owner.isTravelling()) {
         this.getEntityData().set(GROW_TICK, this.getGrowTick() + 1);
      }
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(GROW_TICK, 0);
   }

   public void setOwner(@Nullable EntityFissure owner) {
      this.owner = owner;
      this.ownerUUID = owner == null ? null : owner.getUUID();
   }

   @Nullable
   public EntityFissure getOwner() {
      if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
         if (entity instanceof EntityFissure) {
            this.owner = (EntityFissure)entity;
         }
      }

      return this.owner;
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      if (compound.hasUUID("Owner")) {
         this.ownerUUID = compound.getUUID("Owner");
      }

      this.getEntityData().set(GROW_TICK, compound.getInt("growTick"));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      if (this.ownerUUID != null) {
         compound.putUUID("Owner", this.ownerUUID);
      }

      compound.putInt("growTick", this.getGrowTick());
   }

   public int getGrowTick() {
      return (Integer)this.getEntityData().get(GROW_TICK);
   }
}
