package com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class BlockSnapshot {
   private final int stateId;
   private final ResourceLocation blockId;
   private final CompoundTag blockEntityTag;
   private final ResourceLocation dimensionId;
   private final BlockPos pos;
   private final Direction face;
   private final Vec3 hitLocation;
   private final boolean inside;

   public BlockSnapshot(
      int stateId,
      ResourceLocation blockId,
      CompoundTag blockEntityTag,
      ResourceLocation dimensionId,
      BlockPos pos,
      Direction face,
      Vec3 hitLocation,
      boolean inside
   ) {
      this.stateId = stateId;
      this.blockId = blockId;
      this.blockEntityTag = blockEntityTag;
      this.dimensionId = dimensionId;
      this.pos = pos;
      this.face = face;
      this.hitLocation = hitLocation;
      this.inside = inside;
   }

   public int getStateId() {
      return this.stateId;
   }

   public ResourceLocation getBlockId() {
      return this.blockId;
   }

   public CompoundTag getBlockEntityTag() {
      return this.blockEntityTag;
   }

   public ResourceLocation getDimensionId() {
      return this.dimensionId;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public Direction getFace() {
      return this.face;
   }

   public Vec3 getHitLocation() {
      return this.hitLocation;
   }

   public boolean isInside() {
      return this.inside;
   }

   public void writeToBuf(RegistryFriendlyByteBuf buf) {
      buf.writeVarInt(this.stateId);
      buf.writeResourceLocation(this.blockId);
      buf.writeBoolean(this.blockEntityTag != null);
      if (this.blockEntityTag != null) {
         buf.writeNbt(this.blockEntityTag);
      }

      buf.writeResourceLocation(this.dimensionId);
      buf.writeBlockPos(this.pos);
      buf.writeEnum(this.face);
      buf.writeDouble(this.hitLocation.x);
      buf.writeDouble(this.hitLocation.y);
      buf.writeDouble(this.hitLocation.z);
      buf.writeBoolean(this.inside);
   }

   public static BlockSnapshot fromBuf(RegistryFriendlyByteBuf buf) {
      int stateId = buf.readVarInt();
      ResourceLocation blockId = buf.readResourceLocation();
      CompoundTag blockEntityTag = null;
      if (buf.readBoolean()) {
         blockEntityTag = buf.readNbt();
      }

      ResourceLocation dimensionId = buf.readResourceLocation();
      BlockPos pos = buf.readBlockPos();
      Direction face = (Direction)buf.readEnum(Direction.class);
      Vec3 hitLocation = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
      boolean inside = buf.readBoolean();
      return new BlockSnapshot(stateId, blockId, blockEntityTag, dimensionId, pos, face, hitLocation, inside);
   }
}
