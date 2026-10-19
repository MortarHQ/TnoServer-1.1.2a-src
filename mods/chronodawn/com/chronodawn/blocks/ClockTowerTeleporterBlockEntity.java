package com.chronodawn.blocks;

import com.chronodawn.compat.CompatBlockEntity;
import com.chronodawn.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ClockTowerTeleporterBlockEntity extends CompatBlockEntity {
   private BlockPos targetPos = null;

   public ClockTowerTeleporterBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)ModBlockEntities.CLOCK_TOWER_TELEPORTER.get(), pos, state);
   }

   public void setTargetPos(BlockPos pos) {
      this.targetPos = pos;
      this.setChanged();
   }

   public BlockPos getTargetPos() {
      return this.targetPos;
   }

   @Override
   public void saveData(CompoundTag tag) {
      if (this.targetPos != null) {
         tag.putInt("TargetX", this.targetPos.getX());
         tag.putInt("TargetY", this.targetPos.getY());
         tag.putInt("TargetZ", this.targetPos.getZ());
      }
   }

   @Override
   public void loadData(CompoundTag tag) {
      if (tag.contains("TargetX")) {
         this.targetPos = new BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"));
      }
   }
}
