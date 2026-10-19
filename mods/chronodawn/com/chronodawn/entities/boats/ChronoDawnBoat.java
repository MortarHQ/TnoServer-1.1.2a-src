package com.chronodawn.entities.boats;

import com.chronodawn.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Boat.Type;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ChronoDawnBoat extends Boat {
   private static final EntityDataAccessor<Integer> DATA_CHRONO_DAWN_TYPE = SynchedEntityData.defineId(ChronoDawnBoat.class, EntityDataSerializers.INT);

   public ChronoDawnBoat(EntityType<? extends Boat> entityType, Level level) {
      super(entityType, level);
   }

   public ChronoDawnBoat(Level level, double x, double y, double z) {
      this((EntityType<? extends Boat>)ModEntities.CHRONO_DAWN_BOAT.get(), level);
      this.setPos(x, y, z);
      this.xo = x;
      this.yo = y;
      this.zo = z;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_CHRONO_DAWN_TYPE, ChronoDawnBoatType.TIME_WOOD.ordinal());
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putString("ChronoDawnType", this.getChronoDawnBoatType().getName());
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("ChronoDawnType")) {
         this.setChronoDawnBoatType(ChronoDawnBoatType.byName(compound.getString("ChronoDawnType")));
      }
   }

   public void setChronoDawnBoatType(ChronoDawnBoatType type) {
      this.entityData.set(DATA_CHRONO_DAWN_TYPE, type.ordinal());
   }

   public ChronoDawnBoatType getChronoDawnBoatType() {
      return ChronoDawnBoatType.byId((Integer)this.entityData.get(DATA_CHRONO_DAWN_TYPE));
   }

   public Item getDropItem() {
      return this.getChronoDawnBoatType().getBoatItem();
   }

   public Type getVariant() {
      return Type.OAK;
   }

   public void setVariant(Type type) {
   }
}
