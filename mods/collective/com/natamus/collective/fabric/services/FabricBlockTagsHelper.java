package com.natamus.collective.fabric.services;

import com.natamus.collective_common_fabric.services.helpers.BlockTagsHelper;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.class_2680;

public class FabricBlockTagsHelper implements BlockTagsHelper {
   @Override
   public boolean isOre(class_2680 blockState) {
      return this.isOre(blockState, false);
   }

   @Override
   public boolean isOre(class_2680 blockState, boolean fuzzyCheck) {
      if (fuzzyCheck) {
         String rawName = blockState.method_26204().method_9518().toString();
         if (rawName.contains("_ore'") || rawName.contains("_ore_")) {
            return true;
         }
      }

      return blockState.method_26164(ConventionalBlockTags.ORES);
   }
}
