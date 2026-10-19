package com.mortal_cultivation.entity;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.entity.PartEntity;

public final class AzureMossQilinPart extends PartEntity<AzureMossQilinEntity> {
   private final String boneName;
   private EntityDimensions dimensions = EntityDimensions.scalable(0.1F, 0.1F);

   AzureMossQilinPart(AzureMossQilinEntity parent, String boneName) {
      super(parent);
      this.boneName = boneName;
      this.noPhysics = true;
   }

   public String boneName() {
      return this.boneName;
   }

   void updateBounds(AABB bounds) {
      this.xo = this.getX();
      this.yo = this.getY();
      this.zo = this.getZ();
      this.xOld = this.xo;
      this.yOld = this.yo;
      this.zOld = this.zo;
      this.dimensions = EntityDimensions.scalable((float)Math.max(bounds.getXsize(), bounds.getZsize()), (float)bounds.getYsize());
      this.refreshDimensions();
      this.setPos((bounds.minX + bounds.maxX) * 0.5, bounds.minY, (bounds.minZ + bounds.maxZ) * 0.5);
      this.setBoundingBox(bounds);
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
   }

   public boolean shouldBeSaved() {
      return false;
   }

   public boolean isPickable() {
      return ((AzureMossQilinEntity)this.getParent()).isAlive();
   }

   public boolean is(Entity entity) {
      return this == entity || this.getParent() == entity;
   }

   public EntityDimensions getDimensions(Pose pose) {
      return this.dimensions == null ? EntityDimensions.scalable(0.1F, 0.1F) : this.dimensions;
   }

   @Nullable
   public ItemStack getPickResult() {
      return ((AzureMossQilinEntity)this.getParent()).getPickResult();
   }

   public boolean hurt(DamageSource source, float amount) {
      return !this.isInvulnerableTo(source) && ((AzureMossQilinEntity)this.getParent()).hurt(source, amount);
   }
}
