package com.natamus.collective.forge.services;

import com.natamus.collective_common_forge.services.helpers.BlockTagsHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags.Blocks;

public class ForgeBlockTagsHelper implements BlockTagsHelper {
   @Override
   public boolean isOre(BlockState blockState) {
      return this.isOre(blockState, false);
   }

   @Override
   public boolean isOre(BlockState blockState, boolean fuzzyCheck) {
      if (fuzzyCheck) {
         String rawName = blockState.getBlock().getName().toString();
         if (rawName.contains("_ore'") || rawName.contains("_ore_")) {
            return true;
         }
      }

      return blockState.is(Blocks.ORES);
   }
}
