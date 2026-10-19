package net.jadenxgamer.elysium_api.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;

public class BlockOnPlaceEvent extends Event {
   private final BlockState state;
   private final Level level;
   private final BlockPos pos;
   private final BlockState oldState;
   private final boolean movedByPiston;

   public BlockOnPlaceEvent(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
      this.state = state;
      this.level = level;
      this.pos = pos;
      this.oldState = oldState;
      this.movedByPiston = movedByPiston;
   }

   public BlockState getState() {
      return this.state;
   }

   public Level getLevel() {
      return this.level;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public BlockState getOldState() {
      return this.oldState;
   }

   public boolean isMovedByPiston() {
      return this.movedByPiston;
   }
}
