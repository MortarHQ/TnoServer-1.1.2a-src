package com.chronodawn.blocks;

import com.chronodawn.compat.CompatBlockEntity;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BossRoomBoundaryMarkerBlockEntity extends CompatBlockEntity {
   private String markerType = "boss_room_min";
   private ResourceLocation replaceWith = BuiltInRegistries.BLOCK.getKey(Blocks.AIR);

   public BossRoomBoundaryMarkerBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)ModBlockEntities.BOSS_ROOM_BOUNDARY_MARKER.get(), pos, state);
   }

   public void setMarkerType(String markerType) {
      this.markerType = markerType;
      this.setChanged();
   }

   public String getMarkerType() {
      return this.markerType;
   }

   public boolean isMinMarker() {
      return "boss_room_min".equals(this.markerType);
   }

   public boolean isMaxMarker() {
      return "boss_room_max".equals(this.markerType);
   }

   public void setReplaceWith(ResourceLocation blockId) {
      this.replaceWith = blockId;
      this.setChanged();
   }

   public ResourceLocation getReplaceWith() {
      return this.replaceWith;
   }

   public BlockState getReplacementState() {
      Block block = (Block)BuiltInRegistries.BLOCK.get(this.replaceWith);
      return block != null ? block.defaultBlockState() : Blocks.AIR.defaultBlockState();
   }

   @Override
   public void saveData(CompoundTag tag) {
      tag.putString("MarkerType", this.markerType);
      tag.putString("ReplaceWith", this.replaceWith.toString());
   }

   @Override
   public void loadData(CompoundTag tag) {
      if (tag.contains("MarkerType")) {
         this.markerType = tag.getString("MarkerType");
      }

      if (tag.contains("ReplaceWith")) {
         this.replaceWith = CompatResourceLocation.parse(tag.getString("ReplaceWith"));
      }
   }
}
