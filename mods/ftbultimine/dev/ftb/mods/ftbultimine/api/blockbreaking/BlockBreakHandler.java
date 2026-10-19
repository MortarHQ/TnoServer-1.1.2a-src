package dev.ftb.mods.ftbultimine.api.blockbreaking;

import dev.ftb.mods.ftbultimine.api.shape.Shape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@FunctionalInterface
public interface BlockBreakHandler {
   BlockBreakHandler.Result breakBlock(Player var1, BlockPos var2, BlockState var3, Shape var4, BlockHitResult var5);

   default void postBreak(Player player) {
   }

   enum Result {
      SUCCESS,
      PASS,
      FAIL;
   }
}
