package net.p3pp3rf1y.sophisticatedbackpacks.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockToolSwapUpgrade {
   default boolean canProcessBlockInteract() {
      return true;
   }

   boolean onBlockInteract(Level var1, BlockPos var2, BlockState var3, Player var4);
}
