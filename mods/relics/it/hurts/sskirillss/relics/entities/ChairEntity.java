package it.hurts.sskirillss.relics.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ChairEntity extends Entity {
   public ChairEntity(EntityType<?> pEntityType, Level level) {
      super(pEntityType, level);
      this.noPhysics = true;
   }

   public void tick() {
      if (this.getPassengers().isEmpty()) {
         this.discard();
      }
   }

   public Vec3 getPassengerRidingPosition(Entity pEntity) {
      return super.getPassengerRidingPosition(pEntity).add(0.0, -0.5, 0.0);
   }

   protected void defineSynchedData(Builder pBuilder) {
   }

   protected void readAdditionalSaveData(CompoundTag pCompound) {
   }

   protected void addAdditionalSaveData(CompoundTag pCompound) {
   }
}
