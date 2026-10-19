package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class EntityUmvuthanaFollower<L extends LivingEntity> extends EntityUmvuthana {
   protected static final Optional<UUID> ABSENT_LEADER = Optional.empty();
   private static final EntityDataAccessor<Optional<UUID>> LEADER = SynchedEntityData.defineId(
      EntityUmvuthanaFollower.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private final Class<L> leaderClass;
   public int index;
   protected L leader;
   public boolean shouldSetDead;

   public EntityUmvuthanaFollower(EntityType<? extends EntityUmvuthanaFollower> type, Level world, Class<L> leaderClass) {
      this(type, world, leaderClass, null);
   }

   public EntityUmvuthanaFollower(EntityType<? extends EntityUmvuthanaFollower> type, Level world, Class<L> leaderClass, L leader) {
      super(type, world);
      this.leaderClass = leaderClass;
      if (leader != null) {
         this.setLeaderUUID(leader.getUUID());
      }

      this.shouldSetDead = false;
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(LEADER, ABSENT_LEADER);
   }

   public Optional<UUID> getLeaderUUID() {
      return (Optional<UUID>)this.getEntityData().get(LEADER);
   }

   public void setLeaderUUID(UUID uuid) {
      this.setLeaderUUID(Optional.of(uuid));
   }

   public void setLeaderUUID(Optional<UUID> uuid) {
      this.getEntityData().set(LEADER, uuid);
   }

   public ItemStack getPickedResult(HitResult target) {
      return new ItemStack((ItemLike)ItemHandler.UMVUTHANA_SPAWN_EGG.get());
   }

   @Override
   public void tick() {
      super.tick();
      if (this.leader == null && this.getLeaderUUID().isPresent()) {
         this.leader = this.getLeader();
         if (this.leader != null) {
            this.addAsPackMember();
         }
      }

      if (this.shouldSetDead) {
         this.discard();
      }
   }

   @Override
   protected Vec3 updateCirclingPosition(float radius, float speed) {
      LivingEntity target = this.getTarget();
      return this.leader != null && target != null
         ? this.circleEntityPosition(
            target, radius, speed, true, this.getGroupCircleTick(), (float)((this.index + 1) * (Math.PI * 2) / (this.getPackSize() + 1))
         )
         : super.updateCirclingPosition(radius, speed);
   }

   @Override
   public void die(DamageSource source) {
      super.die(source);
      if (this.leader != null) {
         this.removeAsPackMember();
      }
   }

   public void setShouldSetDead() {
      this.shouldSetDead = true;
   }

   public void remove(RemovalReason reason) {
      if (this.leader != null) {
         this.removeAsPackMember();
      }

      super.remove(reason);
   }

   public L getLeader() {
      Optional<UUID> uuid = this.getLeaderUUID();
      if (uuid.isPresent()) {
         for (L entity : this.level().getEntitiesOfClass(this.leaderClass, this.getBoundingBox().inflate(32.0, 32.0, 32.0))) {
            if (uuid.get().equals(entity.getUUID())) {
               return entity;
            }
         }
      }

      return null;
   }

   public boolean requiresCustomPersistence() {
      return this.leader != null;
   }

   protected abstract int getGroupCircleTick();

   protected abstract int getPackSize();

   protected abstract void addAsPackMember();

   protected abstract void removeAsPackMember();

   @Override
   public int getAttackChance() {
      return this.getPackSize() < 0 ? super.getAttackChance() : 8 * this.getPackSize() + 40;
   }

   @Override
   public int getAttackCooldown() {
      return this.getPackSize() < 0 ? super.getAttackCooldown() : 8 * this.getPackSize() + 40;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      Optional<UUID> leader = this.getLeaderUUID();
      if (leader.isPresent()) {
         compound.putString("leaderUUID", leader.get().toString());
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      String uuid = compound.getString("leaderUUID");
      if (uuid.isEmpty()) {
         this.setLeaderUUID(ABSENT_LEADER);
      } else {
         this.setLeaderUUID(UUID.fromString(uuid));
      }
   }
}
