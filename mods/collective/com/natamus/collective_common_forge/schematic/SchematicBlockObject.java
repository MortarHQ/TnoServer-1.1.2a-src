package com.natamus.collective_common_forge.schematic;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SchematicBlockObject {
   private final BlockPos position;
   private final BlockState state;

   public SchematicBlockObject(BlockPos position, BlockState state) {
      this.position = position;
      this.state = state;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public BlockState getState() {
      return this.state;
   }

   public BlockPos getPositionWithOffset(int x, int y, int z) {
      return new BlockPos(x + this.position.getX(), y + this.position.getY(), z + this.position.getZ());
   }
}
