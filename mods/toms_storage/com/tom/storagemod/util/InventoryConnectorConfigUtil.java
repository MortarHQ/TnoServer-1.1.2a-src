package com.tom.storagemod.util;

import com.tom.storagemod.Config;
import com.tom.storagemod.StorageTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class InventoryConnectorConfigUtil {
   public static boolean canConnect(BlockState blockState) {
      if (blockState.is(StorageTags.INV_CONNECTOR_SKIP)) {
         return false;
      }

      Block block = blockState.getBlock();
      if (Config.get().getBlockedBlocks().contains(block)) {
         return false;
      }

      if (!Config.get().getBlockedMods().isEmpty()) {
         String modid = block.builtInRegistryHolder().key().location().getNamespace();
         if (Config.get().getBlockedMods().contains(modid)) {
            return false;
         }
      }

      return true;
   }
}
