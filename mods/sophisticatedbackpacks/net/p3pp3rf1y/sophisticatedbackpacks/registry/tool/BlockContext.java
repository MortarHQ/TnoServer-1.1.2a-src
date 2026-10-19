package net.p3pp3rf1y.sophisticatedbackpacks.registry.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockContext {
   private final Level level;
   private final BlockState state;
   private final Block block;
   private final BlockPos pos;

   public BlockContext(Level level, BlockState state, Block block, BlockPos pos) {
      this.level = level;
      this.state = state;
      this.block = block;
      this.pos = pos;
   }

   public Level getLevel() {
      return this.level;
   }

   public BlockState getState() {
      return this.state;
   }

   public Block getBlock() {
      return this.block;
   }

   public BlockPos getPos() {
      return this.pos;
   }
}
