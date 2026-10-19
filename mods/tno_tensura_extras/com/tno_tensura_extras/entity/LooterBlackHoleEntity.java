package com.tno_tensura_extras.entity;

import com.tno_tensura_extras.registry.ExtrasEntityTypes;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;

public final class LooterBlackHoleEntity extends Entity implements IEntityWithComplexSpawn {
   public static final int DEFAULT_LIFE = 48;
   public static final float DEFAULT_RADIUS = 1.75F;
   private int life = 48;
   private int age;
   private float radius = 1.75F;
   private UUID ownerId;

   public LooterBlackHoleEntity(EntityType<? extends LooterBlackHoleEntity> type, Level level) {
      super(type, level);
      this.noPhysics = true;
      this.setNoGravity(true);
      this.setInvulnerable(true);
   }

   public static LooterBlackHoleEntity spawn(ServerLevel level, Vec3 position, ServerPlayer owner) {
      LooterBlackHoleEntity portal = new LooterBlackHoleEntity(ExtrasEntityTypes.LOOTER_BLACK_HOLE.get(), level);
      portal.life = 48;
      portal.radius = 1.75F;
      portal.ownerId = owner == null ? null : owner.getUUID();
      portal.setPos(position);
      portal.setYRot(owner == null ? 0.0F : owner.getYRot());
      portal.setXRot(owner == null ? 0.0F : owner.getXRot());
      if (level.addFreshEntity(portal)) {
         return portal;
      }

      portal.discard();
      return null;
   }

   public void tick() {
      super.tick();
      this.age++;
      if (!this.level().isClientSide() && this.age >= this.life) {
         this.discard();
      }
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      tag.putInt("Life", this.life);
      tag.putInt("Age", this.age);
      tag.putFloat("Radius", this.radius);
      if (this.ownerId != null) {
         tag.putUUID("Owner", this.ownerId);
      }
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      this.life = Math.max(1, tag.getInt("Life"));
      this.age = Math.max(0, tag.getInt("Age"));
      this.radius = Math.max(0.1F, tag.getFloat("Radius"));
      this.ownerId = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
   }

   public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
      buffer.writeVarInt(this.life);
      buffer.writeVarInt(this.age);
      buffer.writeFloat(this.radius);
      buffer.writeBoolean(this.ownerId != null);
      if (this.ownerId != null) {
         buffer.writeUUID(this.ownerId);
      }
   }

   public void readSpawnData(RegistryFriendlyByteBuf buffer) {
      this.life = Math.max(1, buffer.readVarInt());
      this.age = Math.max(0, buffer.readVarInt());
      this.radius = Math.max(0.1F, buffer.readFloat());
      this.ownerId = buffer.readBoolean() ? buffer.readUUID() : null;
   }

   public int getAge() {
      return this.age;
   }

   public int getLife() {
      return this.life;
   }

   public float getRadius() {
      return this.radius;
   }

   public UUID getOwnerId() {
      return this.ownerId;
   }

   public float getAnimationProgress(float partialTick) {
      float current = this.age + partialTick;
      float opening = Math.min(1.0F, current / 10.0F);
      float closing = Math.min(1.0F, Math.max(0.0F, (this.life - current) / 12.0F));
      return Math.max(0.0F, Math.min(opening, closing));
   }

   public AABB getBoundingBoxForCulling() {
      double inflate = this.radius + 0.75;
      return this.getBoundingBox().inflate(inflate, inflate, inflate);
   }

   protected AABB makeBoundingBox() {
      double half = Math.max(0.2, this.radius);
      return new AABB(this.getX() - half, this.getY() - half, this.getZ() - half, this.getX() + half, this.getY() + half, this.getZ() + half);
   }

   public EntityDimensions getDimensions(Pose pose) {
      return this.getType().getDimensions().scale(Math.max(0.1F, this.radius));
   }

   public boolean isAttackable() {
      return false;
   }

   public boolean canUsePortal(boolean allowPassengers) {
      return false;
   }
}
