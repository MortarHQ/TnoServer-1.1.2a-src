package net.jadenxgamer.elysium_api.api.extensions;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface IElysiumBlockExtension {
   default boolean canStickToFace(BlockState selfState, BlockState otherState, Direction selfFace, Direction otherFace) {
      return selfState.canStickTo(otherState);
   }
}
