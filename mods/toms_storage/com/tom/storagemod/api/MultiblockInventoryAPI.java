package com.tom.storagemod.api;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MultiblockInventoryAPI {
   public static final EventHandler<MultiblockInventoryAPI.MultiblockDetector> EVENT = new EventHandler<>(
      MultiblockInventoryAPI.MultiblockDetector.class, listeners -> (level, pos, state, extra) -> {
         for (MultiblockInventoryAPI.MultiblockDetector event : listeners) {
            event.detectMultiblocks(level, pos, state, extra);
         }
      }
   );

   @FunctionalInterface
   public interface MultiblockDetector {
      void detectMultiblocks(Level var1, BlockPos var2, BlockState var3, Consumer<BlockPos> var4);
   }
}
