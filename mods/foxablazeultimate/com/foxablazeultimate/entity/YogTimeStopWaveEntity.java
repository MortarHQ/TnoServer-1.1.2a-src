package com.foxablazeultimate.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class YogTimeStopWaveEntity extends Entity {
   public static final int LIFETIME_TICKS = 10;
   public static final float MAX_RADIUS = 32.0F;
   private static final EntityDataAccessor<Boolean> CONTRACTING = SynchedEntityData.defineId(YogTimeStopWaveEntity.class, EntityDataSerializers.BOOLEAN);

   public YogTimeStopWaveEntity(EntityType<? extends YogTimeStopWaveEntity> type, Level level) {
      super(type, level);
      this.noPhysics = true;
      this.noCulling = true;
   }

   protected void defineSynchedData(@NotNull Builder builder) {
      builder.define(CONTRACTING, false);
   }

   public void initialize(Vec3 centre, boolean contracting) {
      this.setPos(centre);
      this.entityData.set(CONTRACTING, contracting);
      this.setDeltaMovement(Vec3.ZERO);
   }

   public boolean isContracting() {
      return (Boolean)this.entityData.get(CONTRACTING);
   }

   public void tick() {
      super.tick();
      if (this.tickCount >= 10) {
         this.discard();
      }
   }

   public boolean shouldBeSaved() {
      return false;
   }

   protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
      this.entityData.set(CONTRACTING, tag.getBoolean("Contracting"));
   }

   protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
      tag.putBoolean("Contracting", this.isContracting());
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity);
   }
}
