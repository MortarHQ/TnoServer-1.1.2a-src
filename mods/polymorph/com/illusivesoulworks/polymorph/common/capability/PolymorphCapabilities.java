package com.illusivesoulworks.polymorph.common.capability;

import com.illusivesoulworks.polymorph.api.common.capability.IBlockEntityRecipeData;
import com.illusivesoulworks.polymorph.api.common.capability.IPlayerRecipeData;
import com.illusivesoulworks.polymorph.platform.Services;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PolymorphCapabilities {
   public static IPlayerRecipeData getRecipeData(Player player) {
      return Services.PLATFORM.getRecipeData(player);
   }

   public static IBlockEntityRecipeData getRecipeData(BlockEntity blockEntity) {
      return Services.PLATFORM.getRecipeData(blockEntity);
   }
}
