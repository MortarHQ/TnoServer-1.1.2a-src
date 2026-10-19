package com.chronodawn.blocks;

import com.chronodawn.compat.CompatBlockEntity;
import com.chronodawn.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BossRoomDoorBlockEntity extends CompatBlockEntity {
   private String doorType = "entrance";

   public BossRoomDoorBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)ModBlockEntities.BOSS_ROOM_DOOR.get(), pos, state);
   }

   public void setDoorType(String doorType) {
      this.doorType = doorType;
      this.setChanged();
   }

   public String getDoorType() {
      return this.doorType;
   }

   public boolean isBossRoomDoor() {
      return "boss_room".equals(this.doorType);
   }

   public boolean isGuardianVaultDoor() {
      return "guardian_vault".equals(this.doorType);
   }

   public boolean isClockworkDepthsDoor() {
      return "clockwork_depths".equals(this.doorType);
   }

   public boolean isPhantomTowerDoor() {
      return "phantom_tower".equals(this.doorType);
   }

   public boolean isEntropyCryptDoor() {
      return "entropy_crypt".equals(this.doorType);
   }

   @Override
   public void saveData(CompoundTag tag) {
      tag.putString("DoorType", this.doorType);
   }

   @Override
   public void loadData(CompoundTag tag) {
      if (tag.contains("DoorType")) {
         this.doorType = tag.getString("DoorType");
      }
   }
}
