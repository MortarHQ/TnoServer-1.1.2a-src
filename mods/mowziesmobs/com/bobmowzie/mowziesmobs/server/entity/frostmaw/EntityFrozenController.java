package com.bobmowzie.mowziesmobs.server.entity.frostmaw;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;

public class EntityFrozenController extends Entity {
   public EntityFrozenController(EntityType<? extends EntityFrozenController> type, Level world) {
      super(type, world);
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide && this.tickCount >= 70 && !this.isVehicle()) {
         this.discard();
      }
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public boolean canRiderInteract() {
      return false;
   }

   public Vec3 getPassengerRidingPosition(Entity entity) {
      return this.position();
   }

   public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider) {
      return true;
   }

   public void positionRider(Entity passenger, MoveFunction moveFunction) {
      if (this.hasPassenger(passenger)) {
         if (passenger instanceof Player) {
            passenger.setPos(this.getX(), this.getY(), this.getZ());
         } else {
            passenger.absMoveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
         }
      }
   }
}
