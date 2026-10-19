package dev.ftb.mods.ftbultimine.integration.ezcrystals.neoforge;

import dev.ftb.mods.ftbezcrystals.FTBEZCrystals;
import dev.ftb.mods.ftbultimine.api.rightclick.RightClickHandler;
import dev.ftb.mods.ftbultimine.api.shape.ShapeContext;
import dev.ftb.mods.ftbultimine.config.FTBUltimineServerConfig;
import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

public enum EZCrystalsHandler implements RightClickHandler {
   INSTANCE;

   @Override
   public int handleRightClickBlock(ShapeContext shapeContext, InteractionHand hand, Collection<BlockPos> positions) {
      if (!(Boolean)FTBUltimineServerConfig.RIGHT_CLICK_CRYSTALS.get()) {
         return 0;
      }

      ServerPlayer player = shapeContext.player();
      int harvested = 0;

      for (BlockPos pos : positions) {
         if (FTBEZCrystals.harvestCrystal(player.level(), pos, player.getItemInHand(hand), player)) {
            harvested++;
         }
      }

      return harvested;
   }
}
